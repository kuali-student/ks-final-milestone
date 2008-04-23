package org.kuali.student.poc.learningunit.luipersonrelation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuTypeShadow;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelationType;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.RelationState;

public class QuickTest {

    @Test
    public void test() {
        //yes yes, stole from dan.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HibernatePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        LuiPersonRelationType lprt = new LuiPersonRelationType();
        lprt.setLuiPersonRelations(new HashSet<LuiPersonRelation>());
        lprt.setLuTypes(new HashSet<LuTypeShadow>());
        lprt.setRelationStates(new HashSet<RelationState>());
        
        RelationState rs = new RelationState();
        rs.setLuTypes(new HashSet<LuTypeShadow>());
        rs.setLuiPersonRelations(new HashSet<LuiPersonRelation>());
        rs.setLuiPersonRelationTypes(new HashSet<LuiPersonRelationType>());
        
        LuiPersonRelation lpr = new LuiPersonRelation();
        lpr.setEffectiveEndDate(new Date());
        lpr.setEffectiveStartDate(new Date());
        lpr.setLui("anything1");
        lpr.setLuiPersonRelationType(lprt);
        lpr.setPerson("anything2");
        lpr.setRelationState(rs);
        
        lprt.getRelationStates().add(rs);
        rs.getLuiPersonRelationTypes().add(lprt);
        
        lprt.getLuiPersonRelations().add(lpr);
        rs.getLuiPersonRelations().add(lpr);
        
        LuTypeShadow lts = new LuTypeShadow();
        lts.setLuiPersonRelationTypes(new HashSet<LuiPersonRelationType>());
        lts.setLuTypeId("anything3");
        lts.setRelationStates(new HashSet<RelationState>());
        
        lts.getLuiPersonRelationTypes().add(lprt);
        lts.getRelationStates().add(rs);
        lprt.getLuTypes().add(lts);
        rs.getLuTypes().add(lts);
        
        em.persist(lts);
        em.persist(lprt);
        em.persist(rs);
        tx.commit();
        
        String id = lprt.getId();
        LuiPersonRelationType nlpr = em.find(LuiPersonRelationType.class, id);
        Assert.assertEquals(id, nlpr.getId());
        List<LuiPersonRelation> lprs = new ArrayList<LuiPersonRelation>(nlpr.getLuiPersonRelations());
        Assert.assertEquals(lpr.getLui(), lprs.get(0).getLui());
        Assert.assertEquals(lpr.getPerson(), lprs.get(0).getPerson());
    }
}
