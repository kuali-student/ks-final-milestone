package org.kuali.student.poc.learningunit.luipersonrelation.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.kuali.student.poc.learningunit.luipersonrelation.dao.LuiPersonRelationDAO;
import org.kuali.student.poc.learningunit.luipersonrelation.entity.LuiPersonRelation;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto.RelationStateInfo;

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
        return true;
    }

    @Override
    public LuiPersonRelation lookupLuiPersonRelation(String luiPersonRelationId) {
        return em.find(LuiPersonRelation.class, luiPersonRelationId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LuiPersonRelation> findLuiPersonRelationsByPerson(String personId, String luiRelationType, String relationState) {
        Query query = em.createNamedQuery("LuiPersonRelation.findByPerson");
        query.setParameter("personId", personId);
        query.setParameter("luiPersonRelationType", luiRelationType);
        query.setParameter("relationState", relationState);        
        
        List<LuiPersonRelation> luiPersonRelations = query.getResultList();

        return luiPersonRelations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LuiPersonRelation> findLuiPersonRelationsByLui(String luiId, String luiRelationType, String relationState) {
        Query query = em.createNamedQuery("LuiPersonRelation.findByLui");
        query.setParameter("luiId", luiId);
        query.setParameter("luiPersonRelationType", luiRelationType);
        query.setParameter("relationState", relationState);
        
        List<LuiPersonRelation> luiPersonRelations = query.getResultList();
        
        return luiPersonRelations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LuiPersonRelation> findLuiPersonRelations(String personId, String luiId, String luiRelationType, String relationState) {
        Query query = em.createQuery(
             "SELECT lpr FROM LuiPersonRelation lpr WHERE lpr.personId = :personId AND lpr.luiId = :luiId " +
              "AND lpr.luiPersonRelationType LIKE :luiPersonRelationType " +
              "AND lpr.relationState LIKE :relationState");                
        query.setParameter("personId", personId);
        query.setParameter("luiId", luiId);
        query.setParameter("luiPersonRelationType", luiRelationType);
        query.setParameter("relationState", relationState);
        
        List<LuiPersonRelation> luiPersonRelations = query.getResultList();
        
        return luiPersonRelations;
    }

    @Override
    public LuiPersonRelation updateLuiPersonRelation(LuiPersonRelation luiPersonRelation) {
        return em.merge(luiPersonRelation);
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(String luiId, String personId, String luiPersonRelationType, String relationState, Date effectiveStartDate, Date effectiveEndDate) {
        //There has *got* to be a better way to do this
        String queryString = "SELECT id FROM LuiPersonRelation lpr WHERE ";
        if(luiId != null)
            queryString += "lpr.luiId = :luiId AND ";
        if(personId != null)
            queryString += "lpr.personId = :personId AND ";
        if(luiPersonRelationType != null)
            queryString += "lpr.luiPersonRelationType = :luiPersonRelationType AND ";
        if(relationState != null)
            queryString += "lpr.relationState = :relationState AND ";
        if(effectiveEndDate != null)
            queryString += "lpr.effectiveEndDate = :effectiveEndDate AND ";
        if(effectiveStartDate != null)
            queryString += "lpr.effectiveStartDate = :effectiveStartDate AND ";
        if(queryString.endsWith("WHERE "))
            queryString = queryString.substring(0, queryString.length()-6);
        else
            queryString = queryString.substring(0, queryString.length()-4);
        Query query = em.createQuery(queryString);
        if(luiId != null)
            query.setParameter("luiId", luiId);
        if(personId != null)
            query.setParameter("personId", personId);
        if(luiPersonRelationType != null)
            query.setParameter("luiPersonRelationType", luiPersonRelationType);
        if(relationState != null)
            query.setParameter("relationState", relationState);
        if(effectiveEndDate != null)
            query.setParameter("effectiveEndDate", effectiveEndDate, TemporalType.TIMESTAMP);
        if(effectiveStartDate != null)
            query.setParameter("effectiveStartDate", effectiveStartDate, TemporalType.TIMESTAMP);
        
        return query.getResultList();
    }

    @Override
    public List<LuiPersonRelation> searchForLuiPersonRelations(String luiId, String personId, String luiPersonRelationType, String relationState, Date effectiveStartDate, Date effectiveEndDate) {
        //There has *got* to be a better way to do this
        String queryString = "SELECT lpr FROM LuiPersonRelation lpr WHERE ";
        if(luiId != null)
            queryString += "lpr.luiId = :luiId AND ";
        if(personId != null)
            queryString += "lpr.personId = :personId AND ";
        if(luiPersonRelationType != null)
            queryString += "lpr.luiPersonRelationType = :luiPersonRelationType AND ";
        if(relationState != null)
            queryString += "lpr.relationState = :relationState AND ";
        if(effectiveEndDate != null)
            queryString += "lpr.effectiveEndDate = :effectiveEndDate AND ";
        if(effectiveStartDate != null)
            queryString += "lpr.effectiveStartDate = :effectiveStartDate AND ";
        if(queryString.endsWith("WHERE "))
            queryString = queryString.substring(0, queryString.length()-6);
        else
            queryString = queryString.substring(0, queryString.length()-4);
        Query query = em.createQuery(queryString);
        if(luiId != null)
            query.setParameter("luiId", luiId);
        if(personId != null)
            query.setParameter("personId", personId);
        if(luiPersonRelationType != null)
            query.setParameter("luiPersonRelationType", luiPersonRelationType);
        if(relationState != null)
            query.setParameter("relationState", relationState);
        if(effectiveEndDate != null)
            query.setParameter("effectiveEndDate", effectiveEndDate, TemporalType.TIMESTAMP);
        if(effectiveStartDate != null)
            query.setParameter("effectiveStartDate", effectiveStartDate, TemporalType.TIMESTAMP);
        
        return query.getResultList();
    }

     
}
