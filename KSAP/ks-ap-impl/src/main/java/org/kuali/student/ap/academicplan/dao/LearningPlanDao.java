package org.kuali.student.ap.academicplan.dao;

import java.util.List;

import org.kuali.student.ap.academicplan.model.LearningPlanEntity;
import org.kuali.student.r2.common.dao.GenericEntityDao;

public class LearningPlanDao extends GenericEntityDao<LearningPlanEntity> {

    @SuppressWarnings("unchecked")
	public List<LearningPlanEntity> getLearningPlans(String studentId) {
        return em.createQuery("select lp from LearningPlanEntity lp where lp.studentId =:studentId")
                .setParameter("studentId", studentId).getResultList();
    }

    @SuppressWarnings("unchecked")
	public List<LearningPlanEntity> getLearningPlansByType(String studentId, String typeId) {
        return em.createQuery("select lp from LearningPlanEntity lp where lp.studentId =:studentId and lp.typeId =:typeId")
                .setParameter("studentId", studentId).setParameter("typeId", typeId).getResultList();
    }
    
}
