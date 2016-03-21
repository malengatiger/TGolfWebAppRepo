/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boha.golfpractice.util;

import com.boha.golfpractice.data.GolfCourse;
import com.boha.golfpractice.data.Hole;
import java.util.List;
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
public class GenerateHoles {

    @PersistenceContext
    EntityManager em;

    public void generate() {
        long start = System.currentTimeMillis();
        Query q = em.createNamedQuery("GolfCourse.findAll", GolfCourse.class);
        List<GolfCourse> list = q.getResultList();
        int count = 0, hasHoles = 0;
        for (GolfCourse g : list) {
            if (g.getHoleList().isEmpty()) {
                count++;
                for (int i = 0; i < 18; i++) {
                    Hole h = new Hole();
                    h.setGolfCourse(g);
                    h.setHoleNumber(i + 1);
                    h.setPar(4);
                    h.setLengthFromBlack(0);
                    h.setLengthFromBlue(0);
                    h.setLengthFromRed(0);
                    h.setLengthFromWhite(0);
                    em.persist(h);
                    System.out.println("Hole " + (i + 1) + " added to " + g.getGolfCourseName());
                } 
            } else {
                hasHoles++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("GenerateHoles complete, elapsed: " + (end - start) / 1000 + " seconds, generated: " + count
        + " hasHoles: " + hasHoles);
    }
}
