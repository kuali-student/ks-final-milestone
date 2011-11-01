package org.kuali.student.enrollment.class1.lpr.dao;


import java.util.List;

import org.kuali.student.enrollment.class1.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;


public class LprDao extends GenericEntityDao<LuiPersonRelationEntity> {

    @SuppressWarnings({"unchecked"})
    public List<LuiPersonRelationEntity> getByLuiId(String luiId) {
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }

    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId) {
        return em.createQuery("select lpr.id from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId").setParameter("personId", personId).setParameter("luiId", luiId).getResultList();
    }

    public List<LuiPersonRelationEntity> getLprsByPersonAndType(String personId,String typeKey){
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.personRelationType.id=:typeKey").setParameter("personId", personId).setParameter("typeKey", typeKey).getResultList();
    }
    
    
    public List<LuiPersonRelationEntity> getLprsByPerson(String personId){
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.personId=:personId").setParameter("personId", personId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<String> getPersonIdsByLui(String luiId, String typeKey, String stateKey){
    	return em.createQuery("select lpr.personId from LuiPersonRelationEntity lpr where lpr.luiId=:luiId and lpr.personRelationType.id=:typeKey and lpr.personRelationState.id=:stateKey")
    		.setParameter("luiId", luiId)
    		.setParameter("typeKey", typeKey)
    		.setParameter("stateKey", stateKey)
    		.getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<LuiPersonRelationEntity> getLprByLuiAndPerson(String personId, String luiId) {
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId")
        .setParameter("personId", personId)
        .setParameter("luiId", luiId)
        .getResultList();
    }

}
