package org.kuali.student.enrollment.class1.lrr.dao;

import org.kuali.student.enrollment.class1.lrr.model.LearningResultRecordEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

import java.util.List;

public class LrrDao extends GenericEntityDao<LearningResultRecordEntity> {

    public List<LearningResultRecordEntity> getLearningResultRecordsForLprIds(List<String> lprIds) {
        return em.createQuery("from LearningResultRecordEntity lrr where lrr.lprId in :lprIds")
                .setParameter("lprIds", lprIds)
                .getResultList();
    }

    public List<LearningResultRecordEntity> getLearningResultRecordsForLpr(String lprId) {
        return em.createQuery("from LearningResultRecordEntity lrr where lrr.lprId = :lprId")
                .setParameter("lprId", lprId)
                .getResultList();
    }

}
