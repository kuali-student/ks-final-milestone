package org.kuali.student.enrollment.class2.courseofferingset.dao;


import java.util.List;

import org.kuali.student.enrollment.class2.courseofferingset.model.SocEntity;
import org.kuali.student.enrollment.dao.GenericEntityDao;

public class SocDao extends GenericEntityDao<SocEntity> {

    @SuppressWarnings("unchecked")
    public List<SocEntity> getBySocTypeId(String socType) {
        return em.createQuery("from SocEntity a where a.socType=:socType").setParameter("socType", socType)
                .getResultList();
    }

    public List<SocEntity> getByTerm(String termId) {
        return em.createQuery("from SocEntity a where a.termId = :termId")
                .setParameter("termId", termId).getResultList();
    }
    public List<SocEntity> getByTermAndSubjectArea(String termId, String subjectArea) {
        return em.createQuery("from SocEntity a where a.termId = :termId and a.subjectArea = :subjectArea")
                .setParameter("termId", termId)
                .setParameter("subjectArea", subjectArea).getResultList();
    }

    public List<SocEntity> getByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId) {
        return em.createQuery("from SocEntity a where a.termId = :termId and a.unitsContentOwnerId = :unitsContentOwnerId")
                .setParameter("termId", termId)
                .setParameter("unitsContentOwnerId", unitsContentOwnerId).getResultList();
    }

}
