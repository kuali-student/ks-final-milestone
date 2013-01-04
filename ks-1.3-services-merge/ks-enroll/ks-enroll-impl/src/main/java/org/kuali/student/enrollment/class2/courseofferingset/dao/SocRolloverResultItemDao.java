package org.kuali.student.enrollment.class2.courseofferingset.dao;

import java.util.List;

import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultItemEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class SocRolloverResultItemDao extends GenericEntityDao<SocRolloverResultItemEntity> {

    @SuppressWarnings("unchecked")
    public List<SocRolloverResultItemEntity> getBySocTypeId(String socType) {
        return em.createQuery("from SocRolloverResultItemEntity a where a.socType=:socType").setParameter("socType", socType).getResultList();
    }

    public List<SocRolloverResultItemEntity> getBySocRolloverResultId(String socRolloverResultId) {
        return em.createQuery("from SocRolloverResultItemEntity a where a.socRolloverResultId = :socRolloverResultId").setParameter(
                "socRolloverResultId", socRolloverResultId).getResultList();
    }

    public List<SocRolloverResultItemEntity> getBySocRolloverResultIdAndSourceCourseOfferingId(String socRolloverResultId, String sourceCourseOfferingId) {
        return em.createQuery(
                "from SocEntity a where a.socRolloverResultId = :socRolloverResultId and a.sourceCourseOfferingId = :sourceCourseOfferingId").setParameter(
                "socRolloverResultId", socRolloverResultId).setParameter("sourceCourseOfferingId", sourceCourseOfferingId).getResultList();
    }

    public List<SocRolloverResultItemEntity> getBySocRolloverResultIdAndTargetCourseOfferingId(String socRolloverResultId, String targetCourseOfferingId) {
        return em.createQuery(
                "from SocEntity a where a.socRolloverResultId = :socRolloverResultId and a.targetCourseOfferingId = :targetCourseOfferingId").setParameter(
                "socRolloverResultId", socRolloverResultId).setParameter("targetCourseOfferingId", targetCourseOfferingId).getResultList();
    }
}
