package org.kuali.student.ap.academicplan.dao;

import java.util.List;

import javax.persistence.Query;

import org.kuali.student.ap.academicplan.model.PlanItemEntity;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.r2.common.dao.GenericEntityDao;

/**
 *  DAO for working with learning plan items.
 */
public class PlanItemDao extends GenericEntityDao<PlanItemEntity> {
    /**
     * Get all plan items for a particular learning plan.
     */
    @SuppressWarnings("unchecked")
	public List<PlanItemEntity> getLearningPlanItems(String learningPlanId) {
        Query query = em.createNamedQuery("LearningPlanItem.getPlanItems");
        query.setParameter("learningPlanId", learningPlanId);
        return query.getResultList();
    }

    /**
     * Get all plan items for a particular learning plan by type.
     */
    @SuppressWarnings("unchecked")
	public List<PlanItemEntity> getLearningPlanItems(String learningPlanId, String learningPlanItemType) {
		Query query = em.createNamedQuery("LearningPlanItem.getPlanItemsByType");
		query.setParameter("learningPlanId", learningPlanId);
        query.setParameter("learningPlanItemType", learningPlanItemType);
		return query.getResultList();
	}

    /**
     * Get all plan items for a particular learning plan by refObjectId.
     */
    @SuppressWarnings("unchecked")
	public List<PlanItemEntity> getLearningPlanItemsByRefObjectId(String learningPlanId, String refObjectId, String refObjectTypeKey) {
        Query query = em.createNamedQuery("LearningPlanItem.getPlanItemsByRefObjectId");
		query.setParameter("learningPlanId", learningPlanId);
        query.setParameter("refObjectId", refObjectId);
        query.setParameter("refObjectTypeKey", refObjectTypeKey);
		return query.getResultList();
    }

    /**
     * Get all plan items for a particular learning plan by category.
     */
    @SuppressWarnings("unchecked")
    public List<PlanItemEntity> getLearningPlanItems(String learningPlanId, AcademicPlanServiceConstants.ItemCategory category) {
        Query query = em.createNamedQuery("LearningPlanItem.getPlanItemsByCategory");
        query.setParameter("learningPlanId", learningPlanId);
        query.setParameter("category", category.toString());
        return query.getResultList();
    }


}
