/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;

import com.boha.golfpractice.data.Club;
import com.boha.golfpractice.data.Coach;
import com.boha.golfpractice.data.ShotShape;
import com.boha.golfpractice.dto.ClubDTO;
import com.boha.golfpractice.dto.CoachDTO;
import com.boha.golfpractice.dto.ShotShapeDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import java.util.List;
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
    
    public ResponseDTO register(CoachDTO p) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Coach c = new Coach();
            c.setFirstName(p.getFirstName());
            c.setLastName(p.getLastName());
            c.setCellphone(p.getCellphone());
            c.setEmail(p.getEmail());
            c.setPin(p.getPin());
            em.persist(c);
            em.flush();
            resp = getClubsAndShotShape();
            resp.getCoachList().add(new CoachDTO(c));
            
           
        } catch (PersistenceException e) {
            resp.setStatusCode(StatusCode.ERROR_REGISTRATION);
            resp.setMessage(StatusCode.ERROR_REGISTRATION_MSG);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
    public ResponseDTO signIn(String email, String password) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("Coach.signIn",Coach.class);
            q.setParameter("email", email);
            q.setParameter("pin", password);
            q.setMaxResults(1);
            List<Coach> list = q.getResultList();
            if (list.isEmpty()) {
                resp.setStatusCode(StatusCode.ERROR_SIGN_IN);
                resp.setMessage(StatusCode.ERROR_SIGN_IN_MSG);
                return resp;
                      
            }
            resp = getClubsAndShotShape();
            resp.getCoachList().add(new CoachDTO(list.get(0)));
            
            resp.setMessage("Coach signed in");
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
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
    public ResponseDTO getCoachData(Integer coachID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            resp = getClubsAndShotShape();
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
    public ResponseDTO addPlayer(Integer playerID, Integer coachID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
            
}
