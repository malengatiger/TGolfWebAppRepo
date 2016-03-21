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
import com.boha.golfpractice.dto.ClubDTO;
import com.boha.golfpractice.dto.ClubUsedDTO;
import com.boha.golfpractice.dto.CoachDTO;
import com.boha.golfpractice.dto.HoleStatDTO;
import com.boha.golfpractice.dto.PlayerDTO;
import com.boha.golfpractice.dto.PracticeSessionDTO;
import com.boha.golfpractice.dto.ShotShapeDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import static com.boha.golfpractice.util.CoachUtil.log;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.joda.time.DateTimeUtils;

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

    public ResponseDTO register(PlayerDTO p) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Player c = new Player();
            c.setFirstName(p.getFirstName());
            c.setLastName(p.getLastName());
            c.setCellphone(p.getCellphone());
            c.setEmail(p.getEmail());
            c.setPin(p.getPin());
            c.setDateRegistered(new Date());
            em.persist(c);
            em.flush();
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

    public ResponseDTO signIn(String email, String password) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("Player.signIn", Player.class);
            q.setParameter("email", email);
            q.setParameter("pin", password);
            q.setMaxResults(1);
            List<Player> list = q.getResultList();
            if (list.isEmpty()) {
                PlayerDTO m = new PlayerDTO();
                m.setEmail(email);
                m.setPin(password);
                resp = register(m);
                return resp;

            }
            resp = getClubsAndShotShape();
            resp.getPlayerList().add(new PlayerDTO(list.get(0)));
            resp.setPracticeSessionList(getPlayerData(list.get(0).getPlayerID()).getPracticeSessionList());

            resp.setMessage("Player signed in");
            log.log(Level.INFO, "Player signed in");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
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

        return resp;
    }

    public ResponseDTO addPracticeSession(PracticeSessionDTO ps) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            PracticeSession s = new PracticeSession();
            if (ps.getPracticeSessionID() != null) {
                s = em.find(PracticeSession.class, ps.getPracticeSessionID());
            }
            s.setGolfCourse(em.find(GolfCourse.class, ps.getGolfCourseID()));
            s.setPlayer(em.find(Player.class, ps.getPlayerID()));
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
            } else {
                em.merge(s);
            }
            ResponseDTO w = null;
            if (!ps.getHoleStatList().isEmpty()) {
                for (HoleStatDTO hs : ps.getHoleStatList()) {
                    hs.setPracticeSessionID(s.getPracticeSessionID());
                }
                w = addHoleStatList(ps.getHoleStatList());
            }
            PracticeSessionDTO m = new PracticeSessionDTO(s);
            if (w != null) {
                m.setHoleStatList(w.getHoleStatList());
            }
            resp.getPracticeSessionList().add(m);
            resp.setMessage("PracticeSession added or updated");
            log.log(Level.INFO, "####### Practice session has been added or updated");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO addHoleStatList(List<HoleStatDTO> list) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        for (HoleStatDTO hs : list) {
            ResponseDTO w = addHoleStat(hs);
            resp.getHoleStatList().add(w.getHoleStatList().get(0));
        }
        return resp;
    }

    public ResponseDTO addHoleStat(HoleStatDTO hs) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            HoleStat holeStat = new HoleStat();
            if (hs.getHoleStatID() != null) {
                holeStat = em.find(HoleStat.class, hs.getHoleStatID());
            }
            holeStat.setPracticeSession(em.find(PracticeSession.class, hs.getPracticeSessionID()));
            holeStat.setDistanceToPin(hs.getDistanceToPin());
            holeStat.setFairwayBunkerHit(hs.getFairwayBunkerHit());
            holeStat.setFairwayHit(hs.getFairwayHit());
            holeStat.setGreenInRegulation(hs.getGreenInRegulation());
            holeStat.setGreensideBunkerHit(hs.getGreensideBunkerHit());
            holeStat.setHole(em.find(Hole.class, hs.getHole().getHoleID()));
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
            } else {
                em.merge(holeStat);
            }
            if (hs.getClubUsedList() != null) {
                for (ClubUsedDTO cu : hs.getClubUsedList()) {
                    cu.setHoleStatID(holeStat.getHoleStatID());
                }
                addClubsUsed(hs.getClubUsedList());
            }
            resp.getHoleStatList().add(new HoleStatDTO(holeStat));
            log.log(Level.INFO, "*** HoleStat added or updated, holeNumber: {0}", hs.getHole().getHoleNumber());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO addClubsUsed(List<ClubUsedDTO> list) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            for (ClubUsedDTO cu : list) {
                ClubUsed clubUsed = new ClubUsed();
                if (cu.getClubUsedID() != null) {
                    clubUsed = em.find(ClubUsed.class, cu.getClubUsedID());
                }
                clubUsed.setHoleStat(em.find(HoleStat.class, cu.getHoleStatID()));
                clubUsed.setClub(em.find(Club.class, cu.getClub().getClubID()));
                clubUsed.setShotShape(em.find(ShotShape.class, cu.getShotShape().getShotShapeID()));
                if (cu.getClubUsedID() == null) {
                    em.persist(clubUsed);
                    em.flush();
                } else {
                    em.merge(clubUsed);
                }

                log.log(Level.INFO, "&&&& ClubUsed added or updated: {0}", cu.getClub().getClubName());
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }
        return resp;
    }

    public ResponseDTO addCoach(Integer playerID, Integer coachID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
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
}
