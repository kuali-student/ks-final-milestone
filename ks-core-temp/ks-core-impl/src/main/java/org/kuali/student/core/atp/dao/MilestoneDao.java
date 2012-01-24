package org.kuali.student.core.atp.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.kuali.student.core.generic.dao.GenericEntityDao;
import org.kuali.student.core.atp.model.MilestoneEntity;

public class MilestoneDao extends GenericEntityDao<MilestoneEntity> {

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByMilestoneTypeId(String milestoneTypeId) {
        return em.createQuery("from MilestoneEntity m where m.atpType.id=:mstoneTypeId").setParameter("mstoneTypeId", milestoneTypeId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDateRange(Date startRange, Date endRange) {
        return em.createQuery("from MilestoneEntity m where m.startDate between :startRange and :endRange or m.endDate between :startRange and :endRange").setParameter("startRange", startRange).setParameter("endRange", endRange).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDateRangeAndType(Date startRange, Date endRange, String milestoneTypeId) {
        Query query = em.createQuery("from MilestoneEntity m where (m.startDate between :startRange and :endRange or m.endDate between :startRange and :endRange) and (m.milestoneType.id=:mstoneTypeId)");
        query.setParameter("startRange", startRange);
        query.setParameter("endRange", endRange);
        query.setParameter("mstoneTypeId", milestoneTypeId);
        
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByAtp(String atpId) {
        return em.createQuery("select m.milestone from AtpMilestoneRelationEntity m where m.atp.id = :atpId").setParameter("atpId", atpId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByTypeForAtp(String atpId,String milestoneType) {
        return em.createQuery("select m.milestone from AtpMilestoneRelationEntity m where m.atp.id = :atpId and m.milestone.atpType = :milestoneType").setParameter("atpId", atpId).setParameter("milestoneType", milestoneType).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getByDatesForAtp(String atpId,Date startDate,Date endDate) {
        return em.createQuery("select m.milestone from AtpMilestoneRelationEntity m where m.atp.id = :atpId and m.milestone.startDate = :startDate and m.milestone.endDate = :endDate").
                  setParameter("atpId", atpId).
                  setParameter("startDate", startDate).
                  setParameter("endDate", endDate).
                  getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<MilestoneEntity> getImpactedMilestones(String milestoneId) {
        return em.createQuery("from MilestoneEntity m where m.relativeAnchorMilestone.id=:milestoneId").setParameter("milestoneId", milestoneId).getResultList();
    }
}
