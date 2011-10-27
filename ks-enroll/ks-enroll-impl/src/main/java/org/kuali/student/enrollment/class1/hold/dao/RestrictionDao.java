package org.kuali.student.enrollment.class1.hold.dao;

import java.util.List;

import org.kuali.student.enrollment.class1.hold.model.RestrictionEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class RestrictionDao extends GenericEntityDao<RestrictionEntity>{

    @SuppressWarnings("unchecked")
    public List<RestrictionEntity> getByRestrictionTypeId(String restrictionTypeKey) {
        return em.createQuery("from RestrictionEntity r where r.restrictionType.id=:restrictionTypeId").setParameter("restrictionTypeId", restrictionTypeKey).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<RestrictionEntity> getByIssueId(String issueId) {
        return em.createQuery("select rel.restriction from IssueRestrictionRelationEntity rel where rel.issue.id=:issueId").setParameter("issueId", issueId).getResultList();
    }

}
