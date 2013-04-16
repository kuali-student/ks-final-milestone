package org.kuali.student.r2.core.class1.atp.dao;

import java.util.List;

import javax.persistence.Query;

import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpMilestoneRelationEntity;

public class AtpMilestoneRelationDao extends GenericEntityDao<AtpMilestoneRelationEntity> {

    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByMilestoneId(String milestoneId) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.milestoneId=:milestoneId")
                .setParameter("milestoneId", milestoneId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByAtpId(String atpId) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.atpId=:atpId")
                .setParameter("atpId", atpId).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<String> getIdsByAtpAndMilestone(String atpId, String milestoneId) {
        Query query = em
                .createQuery("select amRel.id from AtpMilestoneRelationEntity amRel where amRel.atpId=:atpId and amRel.milestoneId=:milestoneId");
        query.setParameter("atpId", atpId);
        query.setParameter("milestoneId", milestoneId);
        return query.getResultList();
    }

}
