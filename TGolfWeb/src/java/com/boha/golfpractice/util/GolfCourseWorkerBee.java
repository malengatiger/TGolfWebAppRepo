/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;

/**
 *
 * @author aubreyM
 */


import com.boha.golfpractice.data.Hole;
import com.boha.golfpractice.dto.GolfCourseDTO;
import com.boha.golfpractice.dto.HoleDTO;
import com.boha.golfpractice.transfer.ResponseDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GolfCourseWorkerBee {

    @PersistenceContext
    EntityManager em;

    private PreparedStatement preparedStatement;
    private static final String SQL_STATEMENT = "select a.golfCourseID, a.golfCourseName, a.latitude, a.longitude, a.email, a.cellphone, "
            + "( ? * acos( cos( radians(?) ) * cos( radians( a.latitude) ) "
            + "* cos( radians( a.longitude ) - radians(?) ) + sin( radians(?) ) "
            + "* sin( radians( a.latitude ) ) ) ) "
            + "AS distance FROM golfCourse a HAVING distance < ? order by distance";

    public static final int KILOMETRES = 1, MILES = 2, PARM_KM = 6371, PARM_MILES = 3959;

    private Connection conn;

    public ResponseDTO getGolfCoursesWithinRadius(double latitude, double longitude,
            int radius, int type)
            throws Exception {
        long start = System.currentTimeMillis();
        if (conn == null || conn.isClosed()) {
            conn = em.unwrap(Connection.class);
            log.log(Level.INFO, "..........SQL Connection unwrapped from EntityManager");
        }
        if (preparedStatement == null || preparedStatement.isClosed()) {
            preparedStatement = conn.prepareStatement(SQL_STATEMENT);
            log.log(Level.INFO, "..........SQL Statement prepared from Connection");
        }
        switch (type) {
            case KILOMETRES:
                preparedStatement.setInt(1, PARM_KM);
                break;
            case MILES:
                preparedStatement.setInt(1, PARM_MILES);
                break;
            case 0:
                preparedStatement.setInt(1, PARM_KM);
                break;
        }
        ResultSet resultSet;
        preparedStatement.setDouble(2, latitude);
        preparedStatement.setDouble(3, longitude);
        preparedStatement.setDouble(4, latitude);
        preparedStatement.setInt(5, radius);
        resultSet = preparedStatement.executeQuery();
        long end = System.currentTimeMillis();
        
        log.log(Level.INFO, "AlertWorkerBee -  courses by radius elapsed: {0}", 
                new Object[]{Elapsed.getElapsed(start, end)});
        return buildCourseList(resultSet);

    }

    private ResponseDTO buildCourseList(ResultSet resultSet) throws SQLException {
        long start = System.currentTimeMillis();
        ResponseDTO resp = new ResponseDTO();
        Query q = em.createNamedQuery("Hole.findByCourse", Hole.class);
        while (resultSet.next()) {
            int id = resultSet.getInt("golfCourseID");
            String name = resultSet.getString("golfCourseName");
            String email = resultSet.getString("email");
            String cellphone = resultSet.getString("cellphone");
            Double lat = resultSet.getDouble("latitude");
            Double lng = resultSet.getDouble("longitude");
            GolfCourseDTO dto = new GolfCourseDTO();
            dto.setCellphone(cellphone);
            dto.setEmail(email);
            dto.setGolfCourseID(id);
            dto.setGolfCourseName(name);
            dto.setLatitude(lat);
            dto.setLongitude(lng);
            
            q.setParameter("golfCourseID", id);
            List<Hole> list = q.getResultList();
            if (!list.isEmpty()) {
                for (Hole hole : list) {
                    dto.getHoleList().add(new HoleDTO(hole));
                }
   
            }
                       
            resp.getGolfCourseList().add(dto);
            
        }
        long end = System.currentTimeMillis();
        log.log(Level.INFO, "GolfCourseWorkerBee -  courses by radius found: {0} elapsed: {1}", 
                new Object[]{resp.getGolfCourseList().size(), Elapsed.getElapsed(start, end)});
        resultSet.close();
        return resp;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public static final int ROWS_PER_PAGE = 100;
    static final Logger log = Logger.getLogger(GolfCourseWorkerBee.class.getName());
}
