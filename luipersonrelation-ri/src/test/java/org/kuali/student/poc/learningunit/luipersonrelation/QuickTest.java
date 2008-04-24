package org.kuali.student.poc.learningunit.luipersonrelation;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public class QuickTest {

    @Test
    public void test() {
        //yes yes, stole from dan.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HibernatePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
//        LuiPersonRelationType lprt = new LuiPersonRelationType();
//        lprt.setLuiPersonRelations(new HashSet<LuiPersonRelation>());
//        lprt.setLuTypes(new HashSet<LuTypeShadow>());
//        lprt.setRelationStates(new HashSet<RelationState>());
//        
//        RelationState rs = new RelationState();
//        rs.setLuTypes(new HashSet<LuTypeShadow>());
//        rs.setLuiPersonRelations(new HashSet<LuiPersonRelation>());
//        rs.setLuiPersonRelationTypes(new HashSet<LuiPersonRelationType>());
        
        LuiPersonRelation lpr = new LuiPersonRelation();
        lpr.setEffectiveEndDate(new Date());
        lpr.setEffectiveStartDate(new Date());
        lpr.setLuiId("anything1");
//        lpr.setLuiPersonRelationType(lprt);
        lpr.setLuiPersonRelationType("anything3");
        lpr.setPersonId("anything2");
//        lpr.setRelationState(rs);
        lpr.setRelationState("anything4");
        
//        lprt.getRelationStates().add(rs);
//        rs.getLuiPersonRelationTypes().add(lprt);
//        
//        lprt.getLuiPersonRelations().add(lpr);
//        rs.getLuiPersonRelations().add(lpr);
//        
//        LuTypeShadow lts = new LuTypeShadow();
//        lts.setLuiPersonRelationTypes(new HashSet<LuiPersonRelationType>());
//        lts.setLuTypeId("anything3");
//        lts.setRelationStates(new HashSet<RelationState>());
//        
//        lts.getLuiPersonRelationTypes().add(lprt);
//        lts.getRelationStates().add(rs);
//        lprt.getLuTypes().add(lts);
//        rs.getLuTypes().add(lts);
//        
//        em.persist(lts);
//        em.persist(lprt);
//        em.persist(rs);
        em.persist(lpr);
        tx.commit();
        
//        String id = lprt.getId();
//        LuiPersonRelationType nlpr = em.find(LuiPersonRelationType.class, id);
        Assert.assertEquals("anything1", lpr.getLuiId());
        Assert.assertEquals("anything2", lpr.getPersonId());
    }
}
