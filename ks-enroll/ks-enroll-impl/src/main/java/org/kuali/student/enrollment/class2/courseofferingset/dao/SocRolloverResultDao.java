package org.kuali.student.enrollment.class2.courseofferingset.dao;

import java.util.List;

import org.kuali.student.enrollment.class2.courseofferingset.model.SocRolloverResultEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class SocRolloverResultDao extends GenericEntityDao<SocRolloverResultEntity> {

    @SuppressWarnings("unchecked")
    public List<SocRolloverResultEntity> getBySocRorTypeId(String socRorType) {
        return em.createQuery("from SocRolloverResultEntity a where a.socRorType=:socRorType").setParameter("socRorType",
                socRorType).getResultList();
    }

    public List<SocRolloverResultEntity> getBySourceSocId(String sourceSocId) {
        return em.createQuery("from SocRolloverResultEntity a where a.sourceSocId = :sourceSocId").setParameter("sourceSocId",
                sourceSocId).getResultList();
    }

    public List<SocRolloverResultEntity> getByTargetSocId(String targetSocId) {
        return em.createQuery("from SocRolloverResultEntity a where a.targetSocId = :targetSocId").setParameter("targetSocId",
                targetSocId).getResultList();
    }
}
