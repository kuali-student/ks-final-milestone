package org.kuali.student.poc.learningunit.luipersonrelation.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.poc.learningunit.luipersonrelation.dao.LuiPersonRelationDAO;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;

public class LuiPersonRelationDAOImpl implements LuiPersonRelationDAO {

    @PersistenceContext
    private EntityManager em;
    
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public LuiPersonRelation createLuiPersonRelation(LuiPersonRelation luiPersonRelation) {
        em.persist(luiPersonRelation);
        return luiPersonRelation;
    }

    @Override
    public boolean deleteLuiPersonRelation(String luiPersonRelationId) {
        // TODO Auto-generated method stub
        return false;
    }

}
