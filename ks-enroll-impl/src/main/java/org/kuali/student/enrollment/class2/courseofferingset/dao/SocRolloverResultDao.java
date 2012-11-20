package org.kuali.student.enrollment.class2.courseofferingset.dao;

import java.util.List;


import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class SocRolloverResultDao extends GenericEntityDao<SocRolloverResultEntity> {

    @SuppressWarnings("unchecked")
    public List<SocRolloverResultEntity> getBySocRorTypeId(String socRorType) {
        return (List<SocRolloverResultEntity>) em.createNamedQuery("SocRor.getSocRorsByType").setParameter("socRorType", socRorType).getResultList();

    }

    public List<SocRolloverResultEntity> getBySourceSocId(String sourceSocId) {
        return (List<SocRolloverResultEntity>) em.createNamedQuery("SocRor.getSocRorsBySourceSocId").setParameter("sourceSocId", sourceSocId).getResultList();

    }

    public List<String> getSocRolloverResultIdsBySourceSocId(String sourceSocId) {
        return (List<String>) em.createNamedQuery("SocRor.getSocRolloverResultIdsBySourceSocId").setParameter("sourceSocId", sourceSocId).getResultList();

    }

    public List<SocRolloverResultEntity> getByTargetSocId(String targetSocId) {
        return (List<SocRolloverResultEntity>) em.createNamedQuery("SocRor.getSocRorsByTargetSocId").setParameter("targetSocId", targetSocId).getResultList();

    }

    public List<String> getSocRolloverResultIdsByTargetSocId(String targetSocId) {
        return (List<String>) em.createNamedQuery("SocRor.getSocRolloverResultIdsByTargetSocId").setParameter("targetSocId", targetSocId).getResultList();

    }

}
