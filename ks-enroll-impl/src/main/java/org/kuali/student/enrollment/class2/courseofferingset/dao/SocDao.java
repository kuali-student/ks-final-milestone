package org.kuali.student.enrollment.class2.courseofferingset.dao;


import java.util.List;

import org.kuali.student.enrollment.class2.courseofferingset.model.SocEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class SocDao extends GenericEntityDao<SocEntity> {

    @SuppressWarnings("unchecked")
    public List<SocEntity> getBySocTypeId(String socType) {
        return (List<SocEntity>) em.createNamedQuery("Soc.getSocsBySocTypeId").setParameter("socType", socType).getResultList();
    }

    public List<String> getSocIdsByType(String socType) {
        return (List<String>) em.createNamedQuery("Soc.getSocIdsByType").setParameter("socType", socType).getResultList();
    }


    public List<String> getSocIdsByTerm(String termId) {
        return (List<String>) em.createNamedQuery("Soc.getSocIdsByTerm").setParameter("termId", termId).getResultList();
    }

    public List<SocEntity> getByTerm(String termId) {
        return (List<SocEntity>) em.createNamedQuery("Soc.getSocsByTerm").setParameter("termId", termId).getResultList();

    }
    public List<SocEntity> getByTermAndSubjectArea(String termId, String subjectArea) {
        return (List<SocEntity>) em.createNamedQuery("Soc.getSocsByTermAndSubjectArea").setParameter("termId", termId).setParameter("subjectArea", subjectArea).getResultList();

    }

    public List<String> getSocIdsByTermAndSubjectArea(String termId, String subjectArea) {
        return (List<String>) em.createNamedQuery("Soc.getSocIdsByTermAndSubjectArea").setParameter("termId", termId).setParameter("subjectArea", subjectArea).getResultList();

    }
    public List<SocEntity> getByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId) {
        return (List<SocEntity>) em.createNamedQuery("Soc.getSocsByTermAndUnitsContentOwner").setParameter("termId", termId).setParameter("unitsContentOwnerId", unitsContentOwnerId).getResultList();

    }

    public List<String> getSocIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId) {
        return (List<String>) em.createNamedQuery("Soc.getSocIdsByTermAndUnitsContentOwner").setParameter("termId", termId).setParameter("unitsContentOwnerId", unitsContentOwnerId).getResultList();

    }

    // Although this doesn't look at the SocEntity, it is related to the getCourseOfferingIdsBySoc which looks at the
    // LuiEntity
    public Long countLuisByTypeForTermId(String luiTypeId, String termId) {
       String query = "SELECT COUNT(*) FROM LuiEntity lui " +
                "WHERE lui.luiType = :luiTypeId AND lui.atpId = :termId";
        return (Long) em.createQuery(query).setParameter("luiTypeId", luiTypeId).setParameter("termId", termId).getSingleResult();
    }
}
