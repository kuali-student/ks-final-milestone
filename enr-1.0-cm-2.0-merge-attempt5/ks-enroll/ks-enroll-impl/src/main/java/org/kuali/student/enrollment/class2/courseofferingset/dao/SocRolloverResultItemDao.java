package org.kuali.student.enrollment.class2.courseofferingset.dao;

import java.util.List;

import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultItemEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class SocRolloverResultItemDao extends GenericEntityDao<SocRolloverResultItemEntity> {

    @SuppressWarnings("unchecked")
    public List<SocRolloverResultItemEntity> getBySocTypeId(String socType) {
        return (List<SocRolloverResultItemEntity>) em.createNamedQuery("SocRorItem.getSocRorItemsBySocType").setParameter("socType", socType).getResultList();

    }

    public List<SocRolloverResultItemEntity> getBySocRolloverResultId(String socRolloverResultId) {
        return (List<SocRolloverResultItemEntity>) em.createNamedQuery("SocRorItem.getSocRorItemsBySocRorId").setParameter("socRolloverResultId", socRolloverResultId).getResultList();

    }

    public List<SocRolloverResultItemEntity> getBySocRolloverResultIdAndSourceCourseOfferingId(String socRolloverResultId, String sourceCourseOfferingId) {
        return (List<SocRolloverResultItemEntity>) em.createNamedQuery("SocRorItem.getSocRorItemsBySocRorIdAndSourceCourseOfferingId").setParameter("socRolloverResultId", socRolloverResultId).setParameter("sourceCourseOfferingId", sourceCourseOfferingId).getResultList();

    }

    public List<SocRolloverResultItemEntity> getBySocRolloverResultIdAndTargetCourseOfferingId(String socRolloverResultId, String targetCourseOfferingId) {
        return (List<SocRolloverResultItemEntity>) em.createNamedQuery("SocRorItem.getSocRorItemsBySocRorIdAndTargetCourseOfferingId").setParameter("socRolloverResultId", socRolloverResultId).setParameter("targetCourseOfferingId", targetCourseOfferingId).getResultList();

    }
}
