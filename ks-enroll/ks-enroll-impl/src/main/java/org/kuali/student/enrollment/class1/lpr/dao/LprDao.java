package org.kuali.student.enrollment.class1.lpr.dao;


import java.util.List;

import org.kuali.student.enrollment.class1.lpr.model.LprEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;


public class LprDao extends GenericEntityDao<LprEntity> {

    @SuppressWarnings({"unchecked"})
    public List<LprEntity> getByLuiId(String luiId) {
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }

    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId) {
        return em.createQuery("select lpr.id from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId").setParameter("personId", personId).setParameter("luiId", luiId).getResultList();
    }

    public List<LprEntity> getLprsByPersonAndType(String personId,String typeKey){
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.personRelationTypeId=:typeKey").setParameter("personId", personId).setParameter("typeKey", typeKey).getResultList();
    }
    
    
    public List<LprEntity> getLprsByPerson(String personId){
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.personId=:personId").setParameter("personId", personId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<String> getPersonIdsByLui(String luiId, String typeKey, String stateKey){
    	return em.createQuery("select lpr.personId from LuiPersonRelationEntity lpr where lpr.luiId=:luiId and lpr.personRelationTypeId=:typeKey and lpr.personRelationStateId=:stateKey")
    		.setParameter("luiId", luiId)
    		.setParameter("typeKey", typeKey)
    		.setParameter("stateKey", stateKey)
    		.getResultList();
    }
    
	public List<LprEntity> getLprByLuiAndPerson(String personId, String luiId) {
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId")
        .setParameter("personId", personId)
        .setParameter("luiId", luiId)
        .getResultList();
    }

    public List<LprEntity> getLprsByLuiPersonAndState(String personId, String luiId, String stateKey) {
        return em.createQuery("from LuiPersonRelationEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId and lpr.personRelationStateId=:stateKey")
                .setParameter("personId", personId)
                .setParameter("luiId", luiId)
                .setParameter("stateKey", stateKey)
                .getResultList();
    }
    
}
