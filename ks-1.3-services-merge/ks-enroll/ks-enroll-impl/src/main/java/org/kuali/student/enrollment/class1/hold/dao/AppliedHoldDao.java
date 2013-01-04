package org.kuali.student.enrollment.class1.hold.dao;

import java.util.List;
import javax.persistence.Query;
import org.kuali.student.enrollment.class1.hold.model.AppliedHoldEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class AppliedHoldDao
        extends GenericEntityDao<AppliedHoldEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("AppliedHoldEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }

    public List<String> getIdsByIssue(String holdIssueId) {
        Query query = em.createNamedQuery("AppliedHoldEntity.getIdsByIssue");
        query.setParameter("holdIssueId", holdIssueId);
        return query.getResultList();
    }

    public List<AppliedHoldEntity> getByPerson(String personId) {
        Query query = em.createNamedQuery("AppliedHoldEntity.getByPerson");
        query.setParameter("personId", personId);
        return query.getResultList();
    }

    public List<AppliedHoldEntity> getByPersonAndState(String personId, String stateKey) {
        Query query = em.createNamedQuery("AppliedHoldEntity.getByPersonAndState");
        query.setParameter("personId", personId);
        query.setParameter("stateKey", stateKey);
        return query.getResultList();
    }
    
    
    public List<AppliedHoldEntity> getByIssueAndPerson(String holdIssueId, String personId) {
        Query query = em.createNamedQuery("AppliedHoldEntity.getByIssueAndPerson");
        query.setParameter("holdIssueId", holdIssueId);
        query.setParameter("personId", personId);
        return query.getResultList();
    } 
    public List<AppliedHoldEntity> getByIssuePersonAndState(String holdIssueId, String personId, String stateKey) {
        Query query = em.createNamedQuery("AppliedHoldEntity.getByIssuePersonAndState");
        query.setParameter("holdIssueId", holdIssueId);
        query.setParameter("personId", personId);
        query.setParameter("stateKey", stateKey);
        return query.getResultList();
    }
}
