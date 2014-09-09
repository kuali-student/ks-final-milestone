package org.kuali.student.r2.core.class1.atp.dao;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.MilestoneEntity;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class MilestoneDao extends GenericEntityDao<MilestoneEntity> {
    
    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByMilestoneTypeId(String milestoneType) {
        return em.createQuery("from MilestoneEntity m where m.atpType=:mstoneType").setParameter("mstoneType", milestoneType).getResultList();
    }
    private static final String OVERLAP_LOGIC = "m.startDate between :startRange and :endRange or :startRange between m.startDate and m.endDate";

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDateRange(Date startRange, Date endRange) {
        // For the logic to this query see https://wiki.kuali.org/display/STUDENT/Storing+and+Querying+Milestone+Dates
        return em.createQuery(
                "from MilestoneEntity m where " + OVERLAP_LOGIC).setParameter("startRange", startRange).setParameter("endRange", endRange).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDateRangeAndType(Date startRange, Date endRange, String milestoneType) {
        Query query = em.createQuery("from MilestoneEntity m where m.milestoneType=:mstoneType and (" + OVERLAP_LOGIC + ")");
        query.setParameter("startRange", startRange);
        query.setParameter("endRange", endRange);
        query.setParameter("mstoneType", milestoneType);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> getIdsByAtp(String atpId) {
        Query query = em.createQuery("select m.milestoneId from AtpMilestoneRelationEntity m where m.atpId = :atpId");
        query.setParameter("atpId", atpId);
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDatesForAtp(String atpId, Date startRange, Date endRange) {
        return em.createQuery(
                "select m.milestoneId from AtpMilestoneRelationEntity m where m.atpId = :atpId and (" + OVERLAP_LOGIC + ")").
                setParameter("atpId", atpId).
                setParameter("startRange", startRange).
                setParameter("startRange", endRange).
                getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getImpactedMilestones(String milestoneId) {
        return em.createQuery("from MilestoneEntity m where m.relativeAnchorMilestoneId=:milestoneId").
                setParameter("milestoneId", milestoneId).
                getResultList();
    }

    public List<MilestoneEntity> getByMilestonesByAtp(String atpId) {
        return em.createQuery("select m from MilestoneEntity m, AtpMilestoneRelationEntity mre where " +
                "mre.atpId=:mreAtpId and " +
                "m.id = mre.milestoneId ").
                setParameter("mreAtpId", atpId).getResultList();
    }

    public List<MilestoneEntity> getMilestonesByTypeForAtp(String atpId, String milestoneType) {
        return em.createQuery("select m from MilestoneEntity m, AtpMilestoneRelationEntity mre where m.atpType=:mstoneType and " +
                "mre.atpId=:mreAtpId and " +
                "m.id = mre.milestoneId ").
                setParameter("mstoneType", milestoneType).
                setParameter("mreAtpId", atpId).getResultList();
    }
}
