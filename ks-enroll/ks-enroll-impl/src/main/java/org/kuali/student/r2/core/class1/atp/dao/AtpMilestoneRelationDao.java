package org.kuali.student.r2.core.class1.atp.dao;

import java.util.List;

import org.kuali.student.enrollment.dao.GenericEntityDao;
import org.kuali.student.r2.core.class1.atp.model.AtpMilestoneRelationEntity;

public class AtpMilestoneRelationDao extends GenericEntityDao<AtpMilestoneRelationEntity>{

    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByTypeId(String atpMilestoneRelationTypeId) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.atpMilestoneRelationType.id=:typeId").setParameter("typeId", atpMilestoneRelationTypeId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByMilestoneId(String milestoneId) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.milestone.id=:milestoneId").setParameter("milestoneId", milestoneId).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<AtpMilestoneRelationEntity> getByAtpId(String atpId) {
        return em.createQuery("from AtpMilestoneRelationEntity amRel where amRel.atp.id=:atpId").setParameter("atpId", atpId).getResultList();
    }
}
