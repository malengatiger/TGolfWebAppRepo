/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;

import com.boha.golfpractice.data.Coach;
import com.boha.golfpractice.data.GolfCourse;
import com.boha.golfpractice.data.Hole;
import com.boha.golfpractice.dto.CoachDTO;
import com.boha.golfpractice.dto.GolfCourseDTO;
import com.boha.golfpractice.dto.HoleDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import static com.boha.golfpractice.util.PlayerUtil.log;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aubreymalabie
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DataUtil {
    @PersistenceContext
    EntityManager em;
    static final Logger log = Logger.getLogger(DataUtil.class.getSimpleName());
    public ResponseDTO getAllCoaches() throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("Coach.findAll",Coach.class);
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

    public ResponseDTO addGolfCourse(GolfCourseDTO gc) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            GolfCourse x = new GolfCourse();
            x.setCellphone(gc.getCellphone());
            x.setEmail(gc.getEmail());
            x.setGolfCourseName(gc.getGolfCourseName());
            x.setLatitude(gc.getLatitude());
            x.setLongitude(gc.getLongitude());
            em.persist(x);
            em.flush();
            resp.getGolfCourseList().add(new GolfCourseDTO(x));
            log.log(Level.SEVERE, "Golf course added: {0}", gc.getGolfCourseName());
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
    public ResponseDTO addHole(HoleDTO hole) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Hole h = new Hole();
            h.setGolfCourse(em.find(GolfCourse.class, hole.getGolfCourseID()));
            h.setHoleNumber(hole.getHoleNumber());
            h.setLengthFromBlack(hole.getLengthFromBlack());
            h.setLengthFromRed(hole.getLengthFromRed());
            h.setLengthFromWhite(hole.getLengthFromWhite());
            h.setLengthFromBlue(hole.getLengthFromBlue());
            h.setPar(hole.getPar());
            em.persist(h);
            em.flush();
            resp.getHoleList().add(new HoleDTO(h));
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
    public ResponseDTO getGolfCourseData(Integer golfCourseID) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
    public ResponseDTO getAllGolfCourses() throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            Query q = em.createNamedQuery("GolfCourse.findAll",GolfCourse.class);
            List<GolfCourse> gList = q.getResultList();
            for (GolfCourse gc : gList) {
                resp.getGolfCourseList().add(new GolfCourseDTO(gc));
            }
            log.log(Level.INFO, "All golf courses: {0}", resp.getGolfCourseList().size());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
    public ResponseDTO findGolfCourses(String name) throws DataException {
        ResponseDTO resp = new ResponseDTO();
        try {
            
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed",e);
            throw new DataException();
        }
        
        return resp;
    }
}
