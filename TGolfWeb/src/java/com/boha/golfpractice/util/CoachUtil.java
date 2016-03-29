/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;

import com.boha.golfpractice.data.Club;
import com.boha.golfpractice.data.Coach;
import com.boha.golfpractice.data.CoachPlayer;
import com.boha.golfpractice.data.Player;
import com.boha.golfpractice.data.PracticeSession;
import com.boha.golfpractice.data.ShotShape;
import com.boha.golfpractice.dto.ClubDTO;
import com.boha.golfpractice.dto.CoachDTO;
import com.boha.golfpractice.dto.PlayerDTO;
import com.boha.golfpractice.dto.ShotShapeDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import static com.boha.golfpractice.util.PlayerUtil.log;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author aubreymalabie
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoachUtil {

    @PersistenceContext
    EntityManager em;
    static final Logger log = Logger.getLogger(CoachUtil.class.getSimpleName());

    public ResponseDTO update(CoachDTO p) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Coach c = em.find(Coach.class, p.getCoachID());
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
            resp.getCoachList().add(new CoachDTO(c));

        } catch (PersistenceException e) {
            resp.setStatusCode(StatusCode.ERROR_REGISTRATION);
            resp.setMessage(StatusCode.ERROR_REGISTRATION_MSG);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO signIn(String email, String password, Boolean isExisting) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("Coach.signIn", Coach.class);
            q.setParameter("email", email);
            q.setParameter("pin", password);
            q.setMaxResults(1);
            List<Coach> list = q.getResultList();
            if (list.isEmpty()) {
                if (isExisting == false) {
                    Coach c = new Coach();
                    c.setEmail(email);
                    c.setPin(password);
                    c.setDateRegistered(new Date());
                    em.persist(c);
                    em.flush();

                    resp = getClubsAndShotShape();
                    resp.getCoachList().add(new CoachDTO(c));

                } else {
                    resp.setStatusCode(StatusCode.ERROR_SIGN_IN);
                    resp.setMessage(StatusCode.ERROR_SIGN_IN_MSG);
                    return resp;
                }
            } else {
                CoachDTO c = new CoachDTO(list.get(0));
                resp = getClubsAndShotShape();
                resp.getCoachList().add(c);
                resp.setPlayerList(getPlayers(c.getCoachID()).getPlayerList());

            }

            resp.setMessage("Coach signed in");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

    public ResponseDTO getPlayers(Integer coachID) {
        ResponseDTO resp = new ResponseDTO();
        Query q = em.createNamedQuery("CoachPlayer.findPlayersByCoach", Player.class);
        q.setParameter("coachID", coachID);
        List<Player> playerList = q.getResultList();
        List<Integer> IDs = new ArrayList<>();
        for (Player p : playerList) {
            IDs.add(p.getPlayerID());
        }
        for (Player p : playerList) {
            PlayerDTO dto = new PlayerDTO(p);
            resp.getPlayerList().add(dto);
        }
        resp.setMessage("Coach's player data retrieved, players: " + resp.getPlayerList().size());
        return resp;
    }

    public ResponseDTO editCoach(CoachDTO coach) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Coach m = new Coach();
            if (coach.getCoachID() != null) {
                m = em.find(Coach.class, coach.getCoachID());
            }

            if (coach.getFirstName() != null) {
                m.setFirstName(coach.getFirstName());
            }
            if (coach.getLastName() != null) {
                m.setLastName(coach.getLastName());
            }
            if (coach.getCellphone() != null) {
                m.setCellphone(coach.getCellphone());
            }
            if (coach.getEmail() != null) {
                m.setEmail(coach.getEmail());
            }
            if (coach.getPhotoUrl() != null) {
                m.setPhotoUrl(coach.getPhotoUrl());
            }

            if (coach.getCoachID() != null) {
                em.merge(m);
                em.flush();
            } else {
                m.setPin(getRandomPin());
                m.setDateRegistered(new Date());
                em.persist(m);
            }
            resp.getCoachList().add(new CoachDTO(m));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to add coach", e);
            throw new DataException();
        }
        resp.setMessage("Coach added or updated: " + coach.getFirstName());
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

    public ResponseDTO addPlayer(Integer playerID, Integer coachID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            CoachPlayer cp = new CoachPlayer();
            cp.setCoach(em.find(Coach.class, coachID));
            cp.setPlayer(em.find(Player.class, playerID));
            cp.setActiveFlag(Boolean.TRUE);
            em.persist(cp);
            resp.setMessage("Player added to Coach: " + cp.getCoach().getFirstName());
            log.log(Level.INFO, "Player added to coach: {0}", cp.getCoach().getFirstName());

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed", e);
            throw new DataException();
        }

        return resp;
    }

}
