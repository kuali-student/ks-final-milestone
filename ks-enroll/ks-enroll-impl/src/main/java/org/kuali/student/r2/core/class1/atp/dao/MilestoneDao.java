package org.kuali.student.r2.core.class1.atp.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.MilestoneEntity;

public class MilestoneDao extends GenericEntityDao<MilestoneEntity> {

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByMilestoneTypeId(String milestoneType) {
        return em.createQuery("from MilestoneEntity m where m.atpType=:mstoneType")
                .setParameter("mstoneType", milestoneType).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDateRange(Date startRange, Date endRange) {
        return em
                .createQuery(
                        "from MilestoneEntity m where m.startDate between :startRange and :endRange or m.endDate between :startRange and :endRange")
                .setParameter("startRange", startRange).setParameter("endRange", endRange).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDateRangeAndType(Date startRange, Date endRange, String milestoneType) {
        Query query = em
                .createQuery("from MilestoneEntity m where (m.startDate between :startRange and :endRange or m.endDate between :startRange and :endRange) and (m.milestoneType=:mstoneType)");
        query.setParameter("startRange", startRange);
        query.setParameter("endRange", endRange);
        query.setParameter("mstoneType", milestoneType);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByAtp(String atpId) {
        return em.createQuery("select m.milestone from AtpMilestoneRelationEntity m where m.atp.id = :atpId order by m.milestone.startDate")
                .setParameter("atpId", atpId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByTypeForAtp(String atpId, String milestoneType) {
        return em
                .createQuery(
                        "select m.milestone from AtpMilestoneRelationEntity m where m.atp.id = :atpId and m.milestone.atpType = :milestoneType")
                .setParameter("atpId", atpId).setParameter("milestoneType", milestoneType).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDatesForAtp(String atpId, Date startDate, Date endDate) {
        return em
                .createQuery(
                        "select m.milestone from AtpMilestoneRelationEntity m where m.atp.id = :atpId and m.milestone.startDate = :startDate and m.milestone.endDate = :endDate")
                .
                setParameter("atpId", atpId).
                setParameter("startDate", startDate).
                setParameter("endDate", endDate).
                getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getImpactedMilestones(String milestoneId) {
        return em.createQuery("from MilestoneEntity m where m.relativeAnchorMilestone.id=:milestoneId")
                .setParameter("milestoneId", milestoneId).getResultList();
    }
}
