package org.kuali.student.myplan.dao;

import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.myplan.academicplan.dao.LearningPlanDao;
import org.kuali.student.myplan.academicplan.dao.PlanItemDao;
import org.kuali.student.myplan.academicplan.dao.PlanItemTypeDao;
import org.kuali.student.myplan.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.myplan.academicplan.model.LearningPlanEntity;
import org.kuali.student.myplan.academicplan.model.PlanItemEntity;
import org.kuali.student.myplan.academicplan.model.PlanItemTypeEntity;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@PersistenceFileLocation("classpath:META-INF/lp-persistence.xml")
public class PlanItemDaoTest extends AbstractTransactionalDaoTest {

    @Dao(value = "org.kuali.student.myplan.academicplan.dao.PlanItemDao", testSqlFile = "classpath:learning_plan.sql")
	private PlanItemDao planItemDao;

    @Dao(value = "org.kuali.student.myplan.academicplan.dao.PlanItemTypeDao")
    private PlanItemTypeDao planItemTypeDao;

    @Dao(value = "org.kuali.student.myplan.academicplan.dao.LearningPlanDao")
	private LearningPlanDao learningPlanDao;
    
    @Test
    public void testGetAllLearningPlanItems() {
        List<PlanItemEntity> objs = planItemDao.findAll();
        assertEquals(16, objs.size());
    }

    @Test
    public void testGetPlanItemsByType() {

        String planId = "lp1";

        List<PlanItemEntity> planItems = planItemDao.getLearningPlanItems(planId, AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST);
        assertEquals(4, planItems.size());
        for (PlanItemEntity pie : planItems) {
            assertEquals(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST, pie.getLearningPlanItemType().getId());
            assertEquals("student1", pie.getLearningPlan().getStudentId());
        }

        planItems = planItemDao.getLearningPlanItems(planId, AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED);
        assertEquals(3, planItems.size());
        for (PlanItemEntity pie : planItems) {
            assertEquals(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED, pie.getLearningPlanItemType().getId());
            assertEquals("student1", pie.getLearningPlan().getStudentId());
        }
        planItems = planItemDao.getLearningPlanItems(planId, AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP);
        assertEquals(1, planItems.size());
        for (PlanItemEntity pie : planItems) {
            assertEquals(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP, pie.getLearningPlanItemType().getId());
            assertEquals("student1", pie.getLearningPlan().getStudentId());
        }
    }

    @Test
    public void testGetPlanItemsByCourse() {
        String planId = "lp1";
        String refObjectId = "006476b5-18d8-4830-bbb6-2bb9e79600fb";
        String refObjectType1 = "kuali.lu.type.CreditCourse";
        String refObjectType2 = "kuali.lu.type.NonCreditCourse";

        List<PlanItemEntity> planItems = planItemDao.getLearningPlanItemsByRefObjectId(planId, refObjectId, refObjectType1);
        assertEquals(1, planItems.size());
        assertEquals(refObjectId, planItems.get(0).getRefObjectId());
        assertEquals(refObjectType1, planItems.get(0).getRefObjectTypeKey());

        planItems = planItemDao.getLearningPlanItemsByRefObjectId(planId, refObjectId, refObjectType2);
        assertEquals(1, planItems.size());
        assertEquals(refObjectId, planItems.get(0).getRefObjectId());
        assertEquals(refObjectType2, planItems.get(0).getRefObjectTypeKey());
    }

    @Test
    public void testGetLearningPlanItemById() {
        String id = "lp1-i1";
        PlanItemEntity pie = planItemDao.find(id);
        assertNotNull(pie);
        assertEquals(id, pie.getId());
        assertEquals(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST, pie.getLearningPlanItemType().getId());
        assertEquals("lp1", pie.getLearningPlan().getId());
    }

    @Test
    public void testCheckAtpIdsSingle() {
        String id = "lp1-i4";
        PlanItemEntity pie = planItemDao.find(id);
        assertNotNull(pie);
        assertEquals(id, pie.getId());
        assertEquals(1, pie.getPlanPeriods().size());
        assertTrue(pie.getPlanPeriods().contains("testTermId3"));
    }

    @Test
    public void testCheckAtpIdsMultiple() {
        String id = "lp1-i6";
        PlanItemEntity pie = planItemDao.find(id);
        assertNotNull(pie);
        assertEquals(id, pie.getId());
        assertEquals(3, pie.getPlanPeriods().size());
        Set<String> atpIds = pie.getPlanPeriods();

        assertTrue(pie.getPlanPeriods().contains("testTermId1"));
        assertTrue(pie.getPlanPeriods().contains("testTermId2"));
        assertTrue(pie.getPlanPeriods().contains("testTermId3"));
    }

    @Test
    public void testCheckAtpNoIds() {
        String id = "lp1-i1";
        PlanItemEntity pie = planItemDao.find(id);
        assertNotNull(pie);
        assertEquals(id, pie.getId());
        assertNotNull(pie.getPlanPeriods());
        assertEquals(0, pie.getPlanPeriods().size());
    }

    @Test
    public void testSavePlanItem_wishlist() {
        LearningPlanEntity learningPlanEntity = learningPlanDao.find("lp1");
        PlanItemTypeEntity planItemTypeEntity = planItemTypeDao.find(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_WISHLIST);

        PlanItemEntity pie = new PlanItemEntity();
        String id = UUIDHelper.genStringUUID();
        pie.setId(id);
        pie.setLearningPlan(learningPlanEntity);
        pie.setLearningPlanItemType(planItemTypeEntity);
        pie.setRefObjectId("02711400-c66d-4ecb-aca5-565118f167cf");
        pie.setRefObjectTypeKey("kuali.lu.type.CreditCourse");
        pie.setCreateId("student1");
        pie.setCreateTime(new Date());

        planItemDao.persist(pie);

        PlanItemEntity newPie = planItemDao.find(id);
        assertNotNull(newPie);
        assertEquals(id, newPie.getId());
        assertEquals(learningPlanEntity.getId(), newPie.getLearningPlan().getId());
        assertEquals(planItemTypeEntity.getId(), newPie.getLearningPlanItemType().getId());
        assertEquals(pie.getRefObjectId(), newPie.getRefObjectId());
        assertEquals(pie.getRefObjectTypeKey(), newPie.getRefObjectTypeKey());
    }

     @Test
    public void testSavePlanItem_planned() {
        LearningPlanEntity learningPlanEntity = learningPlanDao.find("lp1");
        PlanItemTypeEntity planItemTypeEntity = planItemTypeDao.find(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED);

        PlanItemEntity pie = new PlanItemEntity();
        String id = UUIDHelper.genStringUUID();
        pie.setId(id);
        pie.setLearningPlan(learningPlanEntity);
        pie.setLearningPlanItemType(planItemTypeEntity);
        pie.setRefObjectId("02711400-c66d-4ecb-aca5-565118f167cf");
        pie.setRefObjectTypeKey("kuali.lu.type.CreditCourse");
        pie.setCreateId("student1");
        pie.setCreateTime(new Date());

        Set<String> atps = new HashSet<String>();
        atps.add("atp1");
        atps.add("atp2");
        pie.setPlanPeriods(atps);

        planItemDao.persist(pie);

        PlanItemEntity newPie = planItemDao.find(id);
        assertNotNull(newPie);
        assertEquals(id, newPie.getId());
        assertEquals(learningPlanEntity.getId(), newPie.getLearningPlan().getId());
        assertEquals(planItemTypeEntity.getId(), newPie.getLearningPlanItemType().getId());
        assertEquals(pie.getRefObjectId(), newPie.getRefObjectId());
        assertEquals(pie.getRefObjectTypeKey(), newPie.getRefObjectTypeKey());

        atps = newPie.getPlanPeriods();
        assertNotNull(atps);
        assertEquals(2, atps.size());
        assertTrue(atps.contains("atp1"));
        assertTrue(atps.contains("atp2"));
    }
    @Test
    public void testSavePlanItem_backup() {
        LearningPlanEntity learningPlanEntity = learningPlanDao.find("lp1");
        PlanItemTypeEntity planItemTypeEntity = planItemTypeDao.find(AcademicPlanServiceConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP);

        PlanItemEntity pie = new PlanItemEntity();
        String id = UUIDHelper.genStringUUID();
        pie.setId(id);
        pie.setLearningPlan(learningPlanEntity);
        pie.setLearningPlanItemType(planItemTypeEntity);
        pie.setRefObjectId("02711400-c66d-4ecb-aca5-565118f167cf");
        pie.setRefObjectTypeKey("kuali.lu.type.CreditCourse");
        pie.setCreateId("student1");
        pie.setCreateTime(new Date());

        Set<String> atps = new HashSet<String>();
        atps.add("atp1");
        atps.add("atp2");
        pie.setPlanPeriods(atps);

        planItemDao.persist(pie);

        PlanItemEntity newPie = planItemDao.find(id);
        assertNotNull(newPie);
        assertEquals(id, newPie.getId());
        assertEquals(learningPlanEntity.getId(), newPie.getLearningPlan().getId());
        assertEquals(planItemTypeEntity.getId(), newPie.getLearningPlanItemType().getId());
        assertEquals(pie.getRefObjectId(), newPie.getRefObjectId());
        assertEquals(pie.getRefObjectTypeKey(), newPie.getRefObjectTypeKey());

        atps = newPie.getPlanPeriods();
        assertNotNull(atps);
        assertEquals(2, atps.size());
        assertTrue(atps.contains("atp1"));
        assertTrue(atps.contains("atp2"));
    }
}
