package com.boha.golfpractice.util;

import com.boha.golfpractice.transfer.RequestDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author aubreyM
 */
public class TrafficCop {

    public static ResponseDTO processRequest(RequestDTO req,
            DataUtil dataUtil, PlayerUtil playerUtil,
            CoachUtil coachUtil, GolfCourseWorkerBee workerBee)
            throws DataException, IOException, Exception {
        long start = System.currentTimeMillis();
        ResponseDTO resp = new ResponseDTO();
        resp.setStatusCode(0);

        switch (req.getRequestType()) {
            case RequestDTO.REGISTER_COACH:
                resp = coachUtil.register(req.getCoach());
                break;
            case RequestDTO.REGISTER_PLAYER:
                resp = playerUtil.register(req.getPlayer());
                break;
            case RequestDTO.SIGN_IN_COACH:
                resp = coachUtil.signIn(req.getEmail(), req.getPassword());
                break;
            case RequestDTO.SIGN_IN_PLAYER:
                resp = playerUtil.signIn(req.getEmail(), req.getPassword());
                break;
            case RequestDTO.ADD_GOLF_COURSE:
                resp = dataUtil.addGolfCourse(req.getGolfCourse());
                break;
            case RequestDTO.ADD_HOLE:
                resp = dataUtil.addHole(req.getHole());
                break;
            case RequestDTO.ADD_PRACTICE_SESSION:
                resp = playerUtil.addPracticeSession(req.getPracticeSession());
                break;
            
            case RequestDTO.ADD_HOLE_STAT:
                resp = playerUtil.addHoleStat(req.getHoleStat());
                break;
            case RequestDTO.GET_COACH_DATA:
                resp = coachUtil.getCoachData(req.getCoachID());
                break;
            case RequestDTO.GET_PLAYER_DATA:
                resp = playerUtil.getPlayerData(req.getPlayerID());
                break;
            case RequestDTO.GET_ALL_GOLF_COURSES:
                resp = dataUtil.getAllGolfCourses();
                break;
            case RequestDTO.GET_ALL_COACHES:
                resp = dataUtil.getAllCoaches();
                break;
                case RequestDTO.CLOSE_PRACTICE_SESSION:
                resp = playerUtil.closePracticeSession(req.getPracticeSessionID(),req.getPlayerID());
                break;
            case RequestDTO.GET_GOLF_COURSES_BY_LOCATION:
                resp = workerBee.getGolfCoursesWithinRadius(
                        req.getLatitude(), req.getLongitude(), req.getRadius(),
                        GolfCourseWorkerBee.KILOMETRES);
                break;

            default:
                resp.setStatusCode(ServerStatus.ERROR_UNKNOWN_REQUEST);
                resp.setMessage(ServerStatus.getMessage(resp.getStatusCode()));
                break;
        }

        long end = System.currentTimeMillis();
        double elapsed = Elapsed.getElapsed(start, end);
        resp.setElapsedSeconds(elapsed);
        //logger.log(Level.INFO, "******* request elapsed time: {0} seconds", elapsed);
        return resp;
    }

    static final Logger logger = Logger.getLogger(TrafficCop.class.getSimpleName());
}
