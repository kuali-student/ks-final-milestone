package org.kuali.student.enrollment.class1.hold.dao;

import java.util.List;
import javax.persistence.Query;
import org.kuali.student.enrollment.class1.hold.model.HoldEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class HoldDao
        extends GenericEntityDao<HoldEntity> {

    public List<String> getIdsByType(String type) {
        Query query = em.createNamedQuery("HoldEntity.getIdsByType");
        query.setParameter("type", type);
        return query.getResultList();
    }

    public List<String> getIdsByIssue(String issueId) {
        Query query = em.createNamedQuery("HoldEntity.getIdsByIssue");
        query.setParameter("issueId", issueId);
        return query.getResultList();
    }

    public List<HoldEntity> getByPerson(String personId) {
        Query query = em.createNamedQuery("HoldEntity.getByPerson");
        query.setParameter("personId", personId);
        return query.getResultList();
    }

    public List<HoldEntity> getByPersonAndState(String personId, String stateKey) {
        Query query = em.createNamedQuery("HoldEntity.getByPersonAndState");
        query.setParameter("personId", personId);
        query.setParameter("stateKey", stateKey);
        return query.getResultList();
    }
    
    
    public List<HoldEntity> getByIssueAndPerson(String personId, String issueId) {
        Query query = em.createNamedQuery("HoldEntity.getByIssueAndPerson");
        query.setParameter("issueId", issueId);
        query.setParameter("personId", personId);
        return query.getResultList();
    } 
    public List<HoldEntity> getByIssuePersonAndState(String personId, String issueId, String stateKey) {
        Query query = em.createNamedQuery("HoldEntity.getByIssuePersonAndState");
        query.setParameter("issueId", issueId);
        query.setParameter("personId", personId);
        query.setParameter("stateKey", stateKey);
        return query.getResultList();
    }
}
