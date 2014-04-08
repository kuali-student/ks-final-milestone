package org.kuali.student.ap.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.ap.academicplan.dao.LearningPlanDao;
import org.kuali.student.ap.academicplan.model.LearningPlanEntity;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;

@PersistenceFileLocation("classpath:META-INF/lp-persistence.xml")
public class LearningPlanDaoTest extends AbstractTransactionalDaoTest {
                                                                          // "classpath:ks-atp.sql,learning_plan.sql"
    @Dao(value = "org.kuali.student.ap.academicplan.dao.LearningPlanDao", testSqlFile = "classpath:learning_plan.sql")
	private LearningPlanDao learningPlanDao;

    @Test
    public void testGetAllLearningPlans() {
        List<LearningPlanEntity> obj = learningPlanDao.findAll();
        assertEquals(4, obj.size());
    }

    @Test
    public void testGetLearningPlanById() {
        String id = "lp1";
        LearningPlanEntity lp = learningPlanDao.find(id);
        assertNotNull(lp);
        assertEquals(id, lp.getId());
        assertEquals("student1", lp.getStudentId());

        assertEquals("Student 1 Learning Plan 1", lp.getDescrPlain());
    }

	@Test
    public void testSaveLearningPlan() {
        String studentId = "new-student";

        LearningPlanEntity learningPlanEntity = new LearningPlanEntity();
        String id = UUIDHelper.genStringUUID();
        learningPlanEntity.setId(id);
        learningPlanEntity.setDescrFormatted("<span>New Plan</span>");
        learningPlanEntity.setDescrPlain("New Plan");
        learningPlanEntity.setTypeId(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN);
        learningPlanEntity.setStudentId(studentId);
        learningPlanEntity.setCreateId(studentId);
        Date now = new Date();
        learningPlanEntity.setCreateTime(now);

        assertEquals(4, learningPlanDao.findAll().size());

        learningPlanDao.persist(learningPlanEntity);

        assertEquals(5, learningPlanDao.findAll().size());

        LearningPlanEntity lpe = learningPlanDao.find(id);
        assertEquals(learningPlanEntity.getId(), lpe.getId());
        assertEquals(learningPlanEntity.getDescrPlain(), lpe.getDescrPlain());
        assertEquals(learningPlanEntity.getDescrFormatted(), lpe.getDescrFormatted());
        assertEquals(learningPlanEntity.getStudentId(), lpe.getStudentId());
        assertEquals(learningPlanEntity.getCreateId(), lpe.getCreateId());
        assertEquals(learningPlanEntity.getCreateTime(), lpe.getCreateTime());
        assertEquals(learningPlanEntity.getTypeId(),lpe.getTypeId());
    }

	@Test
    public void testGetLearningPlansByStudentId() {
        String studentId = "student1";
        List<LearningPlanEntity> planEntities = learningPlanDao.getLearningPlans(studentId);

        assertEquals(2, planEntities.size());

        for (LearningPlanEntity lpe : planEntities) {
            assertEquals(studentId, lpe.getStudentId());
        }
    }

	@Test
    public void testGetLearningPlansByStudentIdAndType() {
        String studentId = "student1";
        List<LearningPlanEntity> planEntities =
                learningPlanDao.getLearningPlansByType(studentId,
                        AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN);

        assertEquals(2, planEntities.size());

        for (LearningPlanEntity lpe : planEntities) {
            assertEquals(studentId, lpe.getStudentId());
            assertEquals(AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN, lpe.getTypeId());
        }
    }
}
