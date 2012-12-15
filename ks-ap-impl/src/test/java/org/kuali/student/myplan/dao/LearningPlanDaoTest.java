package org.kuali.student.myplan.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.myplan.academicplan.dao.LearningPlanDao;
import org.kuali.student.myplan.academicplan.dao.LearningPlanTypeDao;
import org.kuali.student.myplan.academicplan.model.LearningPlanEntity;
import org.kuali.student.myplan.academicplan.model.LearningPlanRichTextEntity;
import org.kuali.student.myplan.academicplan.model.LearningPlanTypeEntity;

@PersistenceFileLocation("classpath:META-INF/lp-persistence.xml")
public class LearningPlanDaoTest extends AbstractTransactionalDaoTest {
                                                                          // "classpath:ks-atp.sql,learning_plan.sql"
    @Dao(value = "org.kuali.student.myplan.academicplan.dao.LearningPlanDao", testSqlFile = "classpath:learning_plan.sql")
	private LearningPlanDao learningPlanDao;

    @Dao(value = "org.kuali.student.myplan.academicplan.dao.LearningPlanTypeDao")
	private LearningPlanTypeDao typeDao;

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

        assertEquals("Student 1 Learning Plan 1", lp.getDescr().getPlain());
    }

	@Test
    public void testSaveLearningPlan() {
        LearningPlanRichTextEntity lpDesc = new LearningPlanRichTextEntity();
        lpDesc.setFormatted("<span>New Plan</span>");
        lpDesc.setPlain("New Plan");

        LearningPlanTypeEntity learningPlanTypeEntity = typeDao.find("kuali.academicplan.type.plan");
        assertNotNull(learningPlanTypeEntity);

        String studentId = "new-student";

        LearningPlanEntity learningPlanEntity = new LearningPlanEntity();
        String id = UUIDHelper.genStringUUID();
        learningPlanEntity.setId(id);
        learningPlanEntity.setDescr(lpDesc);
        learningPlanEntity.setLearningPlanType(learningPlanTypeEntity);
        learningPlanEntity.setStudentId(studentId);
        learningPlanEntity.setCreateId(studentId);
        Date now = new Date();
        learningPlanEntity.setCreateTime(now);

        assertEquals(4, learningPlanDao.findAll().size());

        learningPlanDao.persist(learningPlanEntity);

        assertEquals(5, learningPlanDao.findAll().size());

        LearningPlanEntity lpe = learningPlanDao.find(id);
        assertEquals(learningPlanEntity.getId(), lpe.getId());
        assertEquals(learningPlanEntity.getDescr().getPlain(), lpe.getDescr().getPlain());
        assertEquals(learningPlanEntity.getDescr().getFormatted(), lpe.getDescr().getFormatted());
        assertEquals(learningPlanEntity.getStudentId(), lpe.getStudentId());
        assertEquals(learningPlanEntity.getCreateId(), lpe.getCreateId());
        assertEquals(learningPlanEntity.getCreateTime(), lpe.getCreateTime());
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
        String typeId = "kuali.academicplan.type.plan";
        List<LearningPlanEntity> planEntities = learningPlanDao.getLearningPlansByType(studentId, typeId);

        assertEquals(2, planEntities.size());

        for (LearningPlanEntity lpe : planEntities) {
            assertEquals(studentId, lpe.getStudentId());
            assertEquals(typeId, lpe.getLearningPlanType().getId());
        }
    }
}
