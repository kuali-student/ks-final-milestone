package org.kuali.student.poc.learningunit.luipersonrelation.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        em.remove(lookupLuiPersonRelation(luiPersonRelationId));
        return false;
    }

    @Override
    public LuiPersonRelation lookupLuiPersonRelation(String luiPersonRelationId) {
        return em.find(LuiPersonRelation.class, luiPersonRelationId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LuiPersonRelation> findLuiPersonRelationByPerson(String personId, String luiRelationType) {
        Query query = em.createNamedQuery("LuiPersonRelation.findByPerson");
        query.setParameter("personId", personId);
        query.setParameter("luiRelationType", luiRelationType);
        List<LuiPersonRelation> luiPersonRelations = query.getResultList();

        return luiPersonRelations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LuiPersonRelation> findLuiPersonRelationByLui(String luiId, String luiRelationType) {
        Query query = em.createNamedQuery("LuiPersonRelation.findByPerson");
        query.setParameter("luiId", luiId);
        query.setParameter("luiRelationType", luiId);
        List<LuiPersonRelation> luiPersonRelations = query.getResultList();
        
        return luiPersonRelations;
    }


     
}
