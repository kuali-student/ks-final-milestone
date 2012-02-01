package org.kuali.student.r2.core.class1.atp.dao;

import java.util.List;

import javax.persistence.Query;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpMilestoneRelationEntity;

public class AtpMilestoneRelationDao extends GenericEntityDao<AtpMilestoneRelationEntity> {

    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByTypeId(String atpMilestoneRelationType) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.atpType=:atpType")
                .setParameter("atpType", atpMilestoneRelationType).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByMilestoneId(String milestoneId) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.milestone.id=:milestoneId")
                .setParameter("milestoneId", milestoneId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByAtpId(String atpId) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.atp.id=:atpId")
                .setParameter("atpId", atpId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByAtpAndMilestone(String atpId, String milestoneId) {
        Query query = em
                .createQuery("from AtpMilestoneRelationEntity amRel where amRel.atp.id=:atpId and amRel.milestone.id=:milestoneId");
        query.setParameter("atpId", atpId);
        query.setParameter("milestoneId", milestoneId);
        return query.getResultList();
    }

}
