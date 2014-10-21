package org.kuali.student.enrollment.class1.lpr.dao;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kuali.student.enrollment.class1.lpr.model.LprEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;


public class LprDao extends GenericEntityDao<LprEntity> {

    @SuppressWarnings({"unchecked"})
    public List<LprEntity> getByLuiId(String luiId) {
        return em.createQuery("from LprEntity lpr where lpr.luiId=:luiId").setParameter("luiId", luiId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> getLprIdsByLuiAndPerson(String personId, String luiId) {
        return em.createQuery("select lpr.id from LprEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId").setParameter("personId", personId).setParameter("luiId", luiId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<LprEntity> getLprsByPersonAndType(String personId,String typeKey){
        return em.createQuery("from LprEntity lpr where lpr.personId=:personId and lpr.personRelationTypeId=:typeKey").setParameter("personId", personId).setParameter("typeKey", typeKey).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<LprEntity> getLprsByMasterLprId(String masterLprId){
        return em.createQuery("from LprEntity lpr where lpr.masterLprId=:masterLprId").setParameter("masterLprId", masterLprId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<LprEntity> getLprsByPerson(String personId){
        return em.createQuery("from LprEntity lpr where lpr.personId=:personId").setParameter("personId", personId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> getPersonIdsByLui(String luiId, String typeKey, String stateKey){
        return em.createQuery("select lpr.personId from LprEntity lpr where lpr.luiId=:luiId and lpr.personRelationTypeId=:typeKey and lpr.personRelationStateId=:stateKey")
                .setParameter("luiId", luiId)
                .setParameter("typeKey", typeKey)
                .setParameter("stateKey", stateKey)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<LprEntity> getLprByLuiAndPerson(String personId, String luiId) {
        return em.createQuery("from LprEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId")
                .setParameter("personId", personId)
                .setParameter("luiId", luiId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<LprEntity> getLprsByLuiPersonAndState(String personId, String luiId, String stateKey) {
        return em.createQuery("from LprEntity lpr where lpr.personId=:personId and lpr.luiId=:luiId and lpr.personRelationStateId=:stateKey")
                .setParameter("personId", personId)
                .setParameter("luiId", luiId)
                .setParameter("stateKey", stateKey)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<LprEntity> getLprsByLuiAndType(String luiId, String lprTypeKey) {
        return em.createQuery("from LprEntity lpr where lpr.luiId=:luiId and lpr.personRelationTypeId=:lprTypeKey").setParameter("luiId", luiId).setParameter("lprTypeKey", lprTypeKey).getResultList();
    }

    public List<String> getLuiIdsByPersonAndTypeAndState(String personId, String luiPersonRelationType, String relationState) {
        return em.createQuery("SELECT lpr.luiId FROM LprEntity lpr WHERE lpr.personId = :personId AND lpr.personRelationTypeId = :luiPersonRelationType AND lpr.personRelationStateId = :relationState")
                .setParameter("personId", personId)
                .setParameter("luiPersonRelationType", luiPersonRelationType)
                .setParameter("relationState", relationState)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<LprEntity> getLprsByLuis(List<String> luiIds) {
        Set<String> luiIdSet = new HashSet<String>(luiIds.size());
        // remove duplicates from the key list
        luiIdSet.addAll(luiIds);
        return (List<LprEntity>) em.createNamedQuery("Lpr.getLprsByLuis").setParameter("luiIds", luiIdSet).getResultList();
    }


    public List<LprEntity> getLprsByPersonAndAtp(String personId, String atpId) {
        return (List<LprEntity>) em.createNamedQuery("Lpr.getLprsByPersonAndAtp").setParameter("personId", personId).setParameter("atpId", atpId).getResultList();
    }
}
