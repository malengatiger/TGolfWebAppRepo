/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;


import com.boha.golfpractice.data.GcmDevice;
import com.boha.golfpractice.dto.SimpleMessageDTO;
import com.boha.golfpractice.dto.SimpleMessageDestinationDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * Utility class for sending GCM messages
 *
 * @author aubreyM
 */
public class GoogleCloudMessageUtil {

    private static final int RETRIES = 5;

    public static final int GCM_MESSAGE_ERROR = 3, ALL_OK = 0, MAX_MESSAGES_IN_BATCH = 1000;
    static Gson gson = new Gson();
    static SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

    //TODO - think some more about messaging and notifications .... persist in database
    /**
     * Send a SimpleMessage to list of devices via Google Cloud Message servers
     *
     * @param em
     * @param simple
     * @return
     * @throws Exception
     * @throws DataException
     */
    public static ResponseDTO sendSimpleMessage(EntityManager em, SimpleMessageDTO simple) throws
            Exception, DataException {
        ResponseDTO resp = new ResponseDTO();

        simple.setMessageDate(new Date().getTime());
        List<String> registrationIDs = buildList(em, simple);

        if (registrationIDs.isEmpty()) {
            LOG.log(Level.SEVERE, "#### No gcm registrationIDs found ");
            resp.setMessage("No staff or monitors found or their devices are not registered");
            resp.setStatusCode(StatusCode.ERROR_GCM);
            return resp;
        }

        simple.setSimpleMessageDestinationList(null);
        String gcmMessage = gson.toJson(simple);

        List<GCMResult> gCMResults;
        Sender sender = new Sender(GoogleCloudMessagingRegistrar.API_KEY);
        Message message = new Message.Builder()
                .addData("simpleMessage", gcmMessage)
                .collapseKey("" + System.currentTimeMillis())
                .build();

        LOG.log(Level.INFO, "sendSimpleMessage: About to send message via GCM: {0}", registrationIDs.size());
        GCMResult gcmr = null;
        String rMsg;
        if (registrationIDs.size() == 1) {
            Result result = sender.send(message, registrationIDs.get(0), RETRIES);
            gcmr = handleResult(em, result);
            if (gcmr.isOK) {
                rMsg = "Google GCM - message has been sent to Google servers";
            } else {
                rMsg = "Google GCM - message has not been sent. Error occured";
                resp.setStatusCode(StatusCode.ERROR_GCM);
                resp.setMessage(rMsg);
            }
            resp.setMessage(rMsg);
            return resp;
        } else {
            gCMResults = sendBatches(em, sender, message, registrationIDs);
        }
        int errors = 0, kool = 0;
        for (GCMResult res : gCMResults) {
            if (res.isOK) {
                kool++;
            } else {
                errors++;
            }
        }

        LOG.log(Level.INFO, "GCM batch send results, success: {0} errors: {1}", new Object[]{kool, errors});
        resp.setMessage("GCM Simple messages sent: " + kool + " errors: " + errors);
        return resp;
    }

    /**
     * Send LocationTracker message to list of devices via Google Cloud Message
     *
     * @param em
     * @param track
     * @return
     * @throws Exception
     * @throws DataException
     */
   
    private static GCMResult handleResult(EntityManager em, Result result)
            throws Exception {
        GCMResult gcmr = new GCMResult();
        gcmr.batchNumber = 1;

        LOG.log(Level.INFO, "Handle result from Google GCM servers: {0}", result.toString());
        if (result.getErrorCodeName() != null) {
            gcmr.isOK = false;
            if (result.getErrorCodeName().equals(
                    Constants.ERROR_NOT_REGISTERED)) {
                // TODO remove the registration from the database *****
                LOG.log(Level.SEVERE, "#### GCM device not registered");
                return gcmr;
            }
            if (result.getErrorCodeName().equals(
                    Constants.ERROR_UNAVAILABLE)) {
                LOG.log(Level.SEVERE, "#### GCM servers not available");
                return gcmr;
            }
            LOG.log(Level.SEVERE, "#### GCM message send error : {0}",
                    result.getErrorCodeName());
//            addErrorStore(em,StatusCode.ERROR_GCM,"#### GCM message send error\nErrorCodeName: " + result.getErrorCodeName());
            return gcmr;
        }

        if (result.getMessageId() != null) {
            gcmr.isOK = true;
            gcmr.message = "Result messageID from GCM: " + result.getMessageId();
            LOG.log(Level.INFO, "Result messageID from GCM: {0}", result.getMessageId());
            if (result.getCanonicalRegistrationId() != null) {
                LOG.log(Level.INFO,
                        "### Google GCM - canonical registration id found, have to update gcmdevice db ...");
                //TODO update device registration id with canonical
                //EntityManager em = EMUtil.getEntityManager();

            }
        }
        return gcmr;
    }

    private static GCMResult handleMultiCastResult(EntityManager em, MulticastResult multiCastResult, int batchNumber)
            throws Exception {
        LOG.log(Level.INFO, "Handle result from Google GCM servers: {0}", multiCastResult.toString());

        GCMResult gcmr = new GCMResult();
        if (multiCastResult.getFailure() == 0
                && multiCastResult.getCanonicalIds() == 0) {
            gcmr.batchNumber = batchNumber;
            gcmr.isOK = true;
            gcmr.message = "Google Cloud messages sent OK";
            gcmr.numberOfMessages = multiCastResult.getTotal();
            LOG.log(Level.INFO, "### Google Cloud message send is OK, messages: {0}", multiCastResult.getTotal());
            return gcmr;
        }
        LOG.log(Level.INFO,
                "### Google GCM - iterating through multicast Result for errors...");
        for (Result result : multiCastResult.getResults()) {
            if (result.getErrorCodeName() != null) {
                gcmr.batchNumber = batchNumber;
                gcmr.isOK = false;
                gcmr.numberOfMessages = multiCastResult.getTotal();
                if (result.getErrorCodeName().equals(
                        Constants.ERROR_NOT_REGISTERED)) {
                    gcmr.message = "GCM device not registered";
                    LOG.log(Level.SEVERE, "#### GCM device not registered");
//                    addErrorStore(em,StatusCode.ERROR_GCM,"#### GCM device not registered");
                    return gcmr;
                }
                if (result.getErrorCodeName().equals(
                        Constants.ERROR_UNAVAILABLE)) {
                    gcmr.message = "GCM servers not available";
                    LOG.log(Level.SEVERE, "#### GCM servers not available");
//                    addErrorStore(em,StatusCode.ERROR_GCM,"#### GCM servers not available");
                    return gcmr;
                }
                gcmr.message = "GCM message send error: " + result.getErrorCodeName();
                LOG.log(Level.SEVERE, "#### GCM message send error : {0}",
                        result.getErrorCodeName());

//                addErrorStore(em,StatusCode.ERROR_GCM, "#### GCM message send error\nErrorCodeName: " + result.getErrorCodeName());
                return gcmr;
            }

            if (result.getMessageId() != null) {
                LOG.log(Level.INFO, "Result messageID from GCM: {0}", result.getMessageId());
                if (result.getCanonicalRegistrationId() != null) {
                    LOG.log(Level.INFO,
                            "### Google GCM - canonical registration id found, should update db ...");
                    //update device registration id - query by gcmdevice by reg id ???????????, 
                    //yeah, do this!!!!!!

                }
            }
        }
        return gcmr;
    }

    private static List<GCMResult> sendBatches(EntityManager em, Sender sender, Message message, List<String> registrationIDs) throws IOException, Exception {
        GCMResult gcmr = null;
        List<GCMResult> gCMResults = new ArrayList<>();
        if (registrationIDs.size() < MAX_MESSAGES_IN_BATCH) {
            MulticastResult multiCastResult = sender.send(
                    message, registrationIDs, RETRIES);
            gcmr = handleMultiCastResult(em, multiCastResult, 1);
            gCMResults.add(gcmr);
        } else {
            int batches = registrationIDs.size() / MAX_MESSAGES_IN_BATCH;
            int rem = registrationIDs.size() % MAX_MESSAGES_IN_BATCH;
            if (rem > 0) {
                batches++;
            }
            LOG.log(Level.OFF, "multiCast message batches: {0}", batches);
            int mainIndex = 0;
            for (int i = 0; i < batches; i++) {
                List<String> batch = new ArrayList<>();
                for (int j = 0; j < MAX_MESSAGES_IN_BATCH; j++) {
                    try {
                        batch.add(registrationIDs.get(mainIndex));
                        mainIndex++;
                    } catch (IndexOutOfBoundsException e) {
                    }

                }
                if (!batch.isEmpty()) {
                    MulticastResult multiCastResult = sender.send(
                            message, batch, RETRIES);
                    GCMResult xx = handleMultiCastResult(em, multiCastResult, (i + 1));
                    gCMResults.add(xx);
                    if (!xx.isOK) {
                        LOG.log(Level.OFF, "multiCast failed at batch: {0}", i);
                    } else {
                        LOG.log(Level.OFF, "multiCast batch sent OK: {0}", i);
                    }
                }
            }
        }
        return gCMResults;
    }

    /**
     * Build list of GCMRegistration ID's from list of staffID's and
     * monitorID's. Each monitor or staff has a list of GCMDevice's which
     * contain the registration id
     *
     * @param em
     * @param simple
     * @return
     */
    private static List<String> buildList(EntityManager em, SimpleMessageDTO simple) {
        List<String> registrationIDs = new ArrayList<>();
        if (simple.getSimpleMessageDestinationList() != null
                && !simple.getSimpleMessageDestinationList().isEmpty()) {
            Query q = em.createNamedQuery("GcmDevice.findByMonitorIDs", GcmDevice.class);
            List<Integer> mList = new ArrayList<>();
            for (SimpleMessageDestinationDTO dest : simple.getSimpleMessageDestinationList()) {
                if (dest.getMonitorID() != null) {
                    mList.add(dest.getMonitorID());
                }
            }
            if (!mList.isEmpty()) {
                q.setParameter("list", mList);
                List<GcmDevice> gList = q.getResultList();
                gList.stream().forEach((m) -> {
                    registrationIDs.add(m.getRegistrationID());
                });
            }
        }
        if (simple.getSimpleMessageDestinationList() != null
                && !simple.getSimpleMessageDestinationList().isEmpty()) {
            Query q = em.createNamedQuery("GcmDevice.findByStaffIDs", GcmDevice.class);
            List<Integer> mList = new ArrayList<>();
            for (SimpleMessageDestinationDTO dest : simple.getSimpleMessageDestinationList()) {
                if (dest.getStaffID() != null) {
                    mList.add(dest.getStaffID());
                }
            }
            if (!mList.isEmpty()) {
                q.setParameter("list", mList);
                List<GcmDevice> gList = q.getResultList();
                gList.stream().forEach((m) -> {
                    registrationIDs.add(m.getRegistrationID());
                });
            }
        }

        return registrationIDs;
    }
    static final Logger LOG = Logger.getLogger("CloudMsgUtil");

    private static class GCMResult {

        boolean isOK;
        int batchNumber, numberOfMessages;
        String message;
    }
}
