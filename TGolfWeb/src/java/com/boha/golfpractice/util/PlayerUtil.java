/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;

import com.boha.golfpractice.data.Club;
import com.boha.golfpractice.data.ClubUsed;
import com.boha.golfpractice.data.Coach;
import com.boha.golfpractice.data.CoachPlayer;
import com.boha.golfpractice.data.GolfCourse;
import com.boha.golfpractice.data.Hole;
import com.boha.golfpractice.data.HoleStat;
import com.boha.golfpractice.data.Player;
import com.boha.golfpractice.data.PracticeSession;
import com.boha.golfpractice.data.ShotShape;
import com.boha.golfpractice.data.VideoUpload;
import com.boha.golfpractice.dto.ClubDTO;
import com.boha.golfpractice.dto.ClubUsedDTO;
import com.boha.golfpractice.dto.CoachDTO;
import com.boha.golfpractice.dto.HoleStatDTO;
import com.boha.golfpractice.dto.PlayerDTO;
import com.boha.golfpractice.dto.PracticeSessionDTO;
import com.boha.golfpractice.dto.ShotShapeDTO;
import com.boha.golfpractice.dto.VideoUploadDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.joda.time.DateTime;

/**
 *
 * @author aubreymalabie
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PlayerUtil {

    @PersistenceContext
    EntityManager em;
    static final Logger log = Logger.getLogger(PlayerUtil.class.getSimpleName());

    public ResponseDTO update(PlayerDTO p) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Player c = em.find(Player.class, p.getPlayerID());
            if (p.getFirstName() != null) {
                c.setFirstName(p.getFirstName());
            }
            if (p.getLastName() != null) {
                c.setLastName(p.getLastName());
            }
            if (p.getCellphone() != null) {
                c.setCellphone(p.getCellphone());
            }
            if (p.getEmail() != null) {
                c.setEmail(p.getEmail());
            }
            if (p.getPin() != null) {
                c.setPin(p.getPin());
            }
            em.merge(c);
            resp = getClubsAndShotShape();
            resp.getPlayerList().add(new PlayerDTO(c));

            log.log(Level.SEVERE, "Player registered: {0}", c.getEmail());

        } catch (PersistenceException e) {
            resp.setStatusCode(StatusCode.ERROR_REGISTRATION);
            resp.setMessage(StatusCode.ERROR_REGISTRATION_MSG);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO getClubsAndShotShape() {
        ResponseDTO resp = new ResponseDTO();
        Query q = em.createNamedQuery("Club.findAll", Club.class);
        List<Club> cList = q.getResultList();
        for (Club club : cList) {
            resp.getClubList().add(new ClubDTO(club));
        }
        q = em.createNamedQuery("ShotShape.findAll", ShotShape.class);
        List<ShotShape> ssList = q.getResultList();
        for (ShotShape club : ssList) {
            resp.getShotShapeList().add(new ShotShapeDTO(club));
        }

        return resp;
    }

    public ResponseDTO signIn(String email, String password, Boolean isExisting) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            resp = getClubsAndShotShape();
            Query q = em.createNamedQuery("Player.signIn", Player.class);
            q.setParameter("email", email);
            q.setParameter("pin", password);
            q.setMaxResults(1);
            List<Player> list = q.getResultList();
            if (list.isEmpty()) {
                if (isExisting == false) {
                    Player m = new Player();
                    m.setEmail(email);
                    m.setPin(password);
                    m.setDateRegistered(new Date());
                    em.persist(m);
                    return resp;
                } else {
                    resp.setStatusCode(StatusCode.ERROR_SIGN_IN);
                    resp.setMessage(StatusCode.ERROR_SIGN_IN_MSG);
                    return resp;
                }

            } else {
                PlayerDTO p = new PlayerDTO(list.get(0));
                resp.getPlayerList().add(p);
                resp.setPracticeSessionList(getPlayerData(p.getPlayerID()).getPracticeSessionList());
            }

            resp.setMessage("Player signed in");
            log.log(Level.INFO, "Player signed in");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO editPlayer(PlayerDTO player, Integer coachID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Player m = new Player();
            if (player.getPlayerID() != null) {
                m = em.find(Player.class, player.getPlayerID());
            }
            if (player.getFirstName() != null) {
                m.setFirstName(player.getFirstName());
            }
            if (player.getLastName() != null) {
                m.setLastName(player.getLastName());
            }
            if (player.getCellphone() != null) {
                m.setCellphone(player.getCellphone());
            }
            if (player.getEmail() != null) {
                m.setEmail(player.getEmail());
            }
            if (player.getGender() != null) {
                m.setGender(player.getGender());
            }
            if (player.getPhotoUrl() != null) {
                m.setPhotoUrl(player.getPhotoUrl());
            }

            if (player.getPlayerID() != null) {
                em.merge(m);
            } else {
                m.setPin(getRandomPin());
                m.setDateRegistered(new Date());
                em.persist(m);
                em.flush();
            }
            resp.getPlayerList().add(new PlayerDTO(m));
            if (coachID != null) {
                addCoach(m.getPlayerID(), coachID);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to add player", e);
            throw new DataException();
        }
        resp.setMessage("Player added or updated: " + player.getFirstName());
        return resp;
    }

    public String getRandomPin() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(System.currentTimeMillis());
        int x = rand.nextInt(9);
        if (x == 0) {
            x = 3;
        }
        sb.append(x);
        sb.append(rand.nextInt(9));
        sb.append(rand.nextInt(9));
        sb.append(rand.nextInt(9));
        sb.append(rand.nextInt(9));
        sb.append(rand.nextInt(9));
        return sb.toString();
    }

    public ResponseDTO getPlayerData(Integer playerID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            closePracticeSessions(playerID);
            resp = getClubsAndShotShape();
            Query q = em.createNamedQuery("PracticeSession.findByPlayer", PracticeSession.class);
            q.setParameter("playerID", playerID);
            List<PracticeSession> sList = q.getResultList();
            log.log(Level.INFO, "Practice Sessions found: {0}", sList.size());

            q = em.createNamedQuery("HoleStat.findByPlayer", HoleStat.class);
            q.setParameter("playerID", playerID);
            List<HoleStat> hList = q.getResultList();
            log.log(Level.INFO, "HoleStats found: {0}", hList.size());

            q = em.createNamedQuery("ClubUsed.findbyPlayer", ClubUsed.class);
            q.setParameter("playerID", playerID);
            List<ClubUsed> clubList = q.getResultList();
            log.log(Level.INFO, "ClubsUsed found: {0}", clubList.size());

            for (PracticeSession session : sList) {
                PracticeSessionDTO dto = new PracticeSessionDTO(session);
                for (HoleStat hs : hList) {
                    if (Objects.equals(hs.getPracticeSession().getPracticeSessionID(), dto.getPracticeSessionID())) {
                        HoleStatDTO hDto = new HoleStatDTO(hs);
                        for (ClubUsed clubUsed : clubList) {
                            if (Objects.equals(clubUsed.getHoleStat().getHoleStatID(), hDto.getHoleStatID())) {
                                hDto.getClubUsedList().add(new ClubUsedDTO(clubUsed));
                            }
                        }
                        dto.getHoleStatList().add(hDto);
                    }
                }
                resp.getPracticeSessionList().add(dto);

            }
            resp.setCoachList(getPlayerCoaches(playerID).getCoachList());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        resp.setMessage("Player's PracticeSessions found: " + resp.getPracticeSessionList().size());
        return resp;
    }

    public ResponseDTO editPracticeSession(PracticeSessionDTO ps) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            PracticeSession s = new PracticeSession();
            if (ps.getPracticeSessionID() != null) {
                s = em.find(PracticeSession.class, ps.getPracticeSessionID());
            }
            s.setGolfCourse(em.find(GolfCourse.class, ps.getGolfCourseID()));
            if (ps.getPlayerID() != null) {
                s.setPlayer(em.find(Player.class, ps.getPlayerID()));
            }
            if (ps.getCoachID() != null) {
                s.setCoach(em.find(Coach.class, ps.getCoachID()));
            }
            s.setNumberOfHoles(ps.getNumberOfHoles());
            s.setOverPar(ps.getOverPar());
            s.setPar(ps.getPar());
            s.setSessionDate(new Date(ps.getSessionDate()));
            s.setTotalStrokes(ps.getTotalStrokes());
            s.setUnderPar(ps.getUnderPar());
            s.setTotalMistakes(ps.getTotalMistakes());

            if (ps.getPracticeSessionID() == null) {
                em.persist(s);
                em.flush();
                log.log(Level.INFO, "####### Practice session has been persisted");

            } else {
                em.merge(s);
                log.log(Level.WARNING, "####### Practice session has been merged");
            }
            if (!ps.getHoleStatList().isEmpty()) {
                for (HoleStatDTO hs : ps.getHoleStatList()) {
                    hs.setPracticeSessionID(s.getPracticeSessionID());
                }
            }
            PracticeSessionDTO m = new PracticeSessionDTO(s);
            m.setHoleStatList(editHoleStatList(ps.getHoleStatList()));

            resp.getPracticeSessionList().add(m);
            resp.setMessage("PracticeSession added or updated");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO addVideo(VideoUploadDTO hs) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            VideoUpload vid = new VideoUpload();
            if (hs.getPracticeSessionID() != null) {
                vid.setPracticeSession(em.find(PracticeSession.class, hs.getPracticeSessionID()));
            }
            if (hs.getPlayerID() != null) {
                vid.setPlayer(em.find(Player.class, hs.getPlayerID()));
            }

            vid.setDateTaken(new Date(hs.getDateTaken()));
            vid.setUrl(hs.getUrl());
            vid.setYouTubeID(hs.getYouTubeID());
            em.persist(hs);
            em.flush();
            resp.getVideoUploadList().add(new VideoUploadDTO(vid));
            resp.setMessage("Video metadata uploaded to DB");
            log.log(Level.INFO, "*** Video added ");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    private List<HoleStatDTO> editHoleStatList(List<HoleStatDTO> list) throws DataException {
        List<HoleStatDTO> holeStatList = new ArrayList<>();
        for (HoleStatDTO hs : list) {
            holeStatList.add(editHoleStat(hs));
        }
        return holeStatList;
    }

    private HoleStatDTO editHoleStat(HoleStatDTO hs) throws DataException {
        HoleStatDTO dto = new HoleStatDTO();
        try {
            HoleStat holeStat = new HoleStat();
            if (hs.getHoleStatID() != null) {
                holeStat = em.find(HoleStat.class, hs.getHoleStatID());
            } else {
                holeStat.setPracticeSession(em.find(PracticeSession.class, hs.getPracticeSessionID()));
                holeStat.setHole(em.find(Hole.class, hs.getHole().getHoleID()));
            }

            holeStat.setDistanceToPin(hs.getDistanceToPin());
            holeStat.setFairwayBunkerHit(hs.getFairwayBunkerHit());
            holeStat.setFairwayHit(hs.getFairwayHit());
            holeStat.setGreenInRegulation(hs.getGreenInRegulation());
            holeStat.setGreensideBunkerHit(hs.getGreensideBunkerHit());

            holeStat.setInRough(hs.getInRough());
            holeStat.setInWater(hs.getInWater());
            holeStat.setNumberOfPutts(hs.getNumberOfPutts());
            holeStat.setOutOfBounds(hs.getOutOfBounds());
            holeStat.setRemarks(hs.getRemarks());
            holeStat.setScore(hs.getScore());
            holeStat.setLengthOfPutt(hs.getLengthOfPutt());

            holeStat.setMistakes(hs.getMistakes());
            if (hs.getHoleStatID() == null) {
                em.persist(holeStat);
                em.flush();
                log.log(Level.INFO, "*** holeStat persisted OK, holeNumber: {0}", holeStat.getHole().getHoleNumber());
            } else {
                em.merge(holeStat);
                log.log(Level.WARNING, "*** holeStat merged OK, holeNumber: {0}", holeStat.getHole().getHoleNumber());
            }
            dto = new HoleStatDTO(holeStat);
            if (hs.getClubUsedList() != null) {
                log.log(Level.INFO, "*** clubs used: {0}", hs.getClubUsedList().size());
                for (ClubUsedDTO cu : hs.getClubUsedList()) {
                    cu.setHoleStatID(holeStat.getHoleStatID());
                }
                dto.setClubUsedList(editClubsUsed(hs.getClubUsedList()));
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return dto;
    }

    private List<ClubUsedDTO> editClubsUsed(List<ClubUsedDTO> list) throws DataException {
        List<ClubUsedDTO> clubUsedList = new ArrayList<>();
        try {
            for (ClubUsedDTO cu : list) {
                ClubUsed clubUsed = new ClubUsed();
                if (cu.getClubUsedID() != null) {
                    clubUsed = em.find(ClubUsed.class, cu.getClubUsedID());
                } else {
                    clubUsed.setHoleStat(em.find(HoleStat.class, cu.getHoleStatID()));
                }
                clubUsed.setClub(em.find(Club.class, cu.getClub().getClubID()));
                clubUsed.setShotShape(em.find(ShotShape.class, cu.getShotShape().getShotShapeID()));
                if (cu.getClubUsedID() == null) {
                    em.persist(clubUsed);
                    em.flush();
                    log.log(Level.INFO, "&&&& ClubUsed persisted: {0}", cu.getClub().getClubName());
                } else {
                    em.merge(clubUsed);
                    log.log(Level.INFO, "--------------------- ClubUsed merged: {0}", cu.getClub().getClubName());
                }

                clubUsedList.add(new ClubUsedDTO(clubUsed));

            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return clubUsedList;
    }

    public ResponseDTO addCoach(Integer playerID, Integer coachID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("CoachPlayer.findCoachesByPlayer", Coach.class);
            q.setParameter("playerID", playerID);
            List<Coach> cList = q.getResultList();
            boolean isFound = false;

            for (Coach coach : cList) {
                if (Objects.equals(coach.getCoachID(), coachID)) {
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                resp.setMessage("Coach already exists for Player");
                return resp;
            }
            CoachPlayer cp = new CoachPlayer();
            cp.setCoach(em.find(Coach.class, coachID));
            cp.setPlayer(em.find(Player.class, playerID));
            cp.setDateRegistered(new Date());
            em.persist(cp);
            resp.setMessage("Coach added for Player");

        } catch (PersistenceException e) {
            resp.setStatusCode(StatusCode.ERROR_DATABASE);
            resp.setMessage("Coach already exists for Player");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO getPlayerCoaches(Integer playerID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("CoachPlayer.findCoachesByPlayer", Coach.class);
            q.setParameter("playerID", playerID);
            List<Coach> list = q.getResultList();
            for (Coach coach : list) {
                resp.getCoachList().add(new CoachDTO(coach));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return resp;
    }
    private static final long ONE_DAY = 1000 * 60 * 60 * 24;

    private void closePracticeSessions(Integer playerID) throws DataException {
        try {
            Query q = em.createNamedQuery("PracticeSession.findOpenByPlayer", PracticeSession.class);
            q.setParameter("playerID", playerID);
            List<PracticeSession> list = q.getResultList();
            long date = new Date().getTime();

            for (PracticeSession ps : list) {
                long then = ps.getSessionDate().getTime();
                if (date - then > ONE_DAY) {
                    ps.setClosed(Boolean.TRUE);
                    em.merge(ps);
                    em.flush();
                    log.log(Level.SEVERE, "Practice Session has been closed");
                }
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

    }

    public ResponseDTO closePracticeSession(Integer practiceSessionID, Integer playerID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            PracticeSession ps = em.find(PracticeSession.class, practiceSessionID);
            ps.setClosed(Boolean.TRUE);
            em.merge(ps);
            em.flush();

            log.log(Level.INFO, "Practice Session closed");

            resp.setPracticeSessionList(getPlayerData(playerID).getPracticeSessionList());
            resp.setMessage("Practice Session closed");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return resp;
    }

    public ResponseDTO getPlayerSessionsWithinDays(Integer playerID, Integer days) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            if (days == null) {
                days = 90;
            }
            DateTime now = new DateTime();
            DateTime then = now.minusDays(days);

            Query q = em.createNamedQuery("PracticeSession.getPlayerSessionsInPeriod", PracticeSession.class);
            q.setParameter("playerID", playerID);
            q.setParameter("fromDate", then.toDate());
            q.setParameter("toDate", now.toDate());
            List<PracticeSession> list = q.getResultList();
            for (PracticeSession ps : list) {
                resp.getPracticeSessionList().add(new PracticeSessionDTO(ps));
            }
            resp.setMessage("PracticeSessions in period listed");

        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            throw new DataException();
        }
        return resp;
    }
}
