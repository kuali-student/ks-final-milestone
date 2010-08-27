package org.kuali.student.lum.statement.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.statement.dao.StatementDao;
import org.kuali.student.core.statement.entity.NlUsageType;
import org.kuali.student.core.statement.entity.ReqComponentFieldType;
import org.kuali.student.core.statement.entity.ReqComponentType;
import org.kuali.student.core.statement.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.core.statement.entity.StatementType;

@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementServiceConfiguration extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.core.statement.dao.impl.StatementDaoImpl", testSqlFile = "classpath:ks-statement.sql")
    public StatementDao dao;

    private boolean containsStatementType(List<StatementType> typeList, String id) {
    	for(StatementType type : typeList) {
    		if(type.getId().equals(id)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Test
    public void testStatementTypeCount() {
    	List<StatementType> statementTypes = this.dao.find(StatementType.class);
    	Assert.assertNotNull(statementTypes);
    	Assert.assertEquals(9, statementTypes.size());
    }

    @Test
    public void testValidStatementSubTypesForCourse() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course");
    	List<StatementType> subTypes = statementType.getAllowedStatementTypes();
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.academicReadiness.prereq"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.academicReadiness.coreq"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.academicReadiness.studentEligibility"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.academicReadiness.antireq"));
    }

    @Test
    public void testValidStatementSubTypesForProgram() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program");
    	List<StatementType> subTypes = statementType.getAllowedStatementTypes();
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.program.entrance"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.program.satisfactoryProgress"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.program.completion"));
    }

    @Test
    public void testStatementSubTypesForCourseStatementType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(4, statementType.getAllowedStatementTypes().size());
    }

    @Test
    public void testStatementSubTypesForProgramStatementType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(3, statementType.getAllowedStatementTypes().size());
    }

    @Test
    public void testReqComponentTypeCount() {
    	List<ReqComponentType> reqComponentTypes = this.dao.find(ReqComponentType.class);
    	Assert.assertNotNull(reqComponentTypes);
    	Assert.assertEquals(29, reqComponentTypes.size());
    }

    @Test
    public void testReqComponentFieldTypeCount() {
    	List<ReqComponentFieldType> reqComponentFieldTypes = this.dao.find(ReqComponentFieldType.class);
    	Assert.assertNotNull(reqComponentFieldTypes);
    	Assert.assertEquals(12, reqComponentFieldTypes.size());
    }

    @Test
    public void testReqComponentTypeNLTemplateCount() {
    	List<ReqComponentTypeNLTemplate> reqComponentTypeNLTemplates = this.dao.find(ReqComponentTypeNLTemplate.class);
    	Assert.assertNotNull(reqComponentTypeNLTemplates);
    	Assert.assertEquals(87, reqComponentTypeNLTemplates.size());
    }

    @Test
    public void testNlUsageTypeCount() {
    	List<NlUsageType> nlUsageTypes = this.dao.find(NlUsageType.class);
    	Assert.assertNotNull(nlUsageTypes);
    	Assert.assertEquals(5, nlUsageTypes.size());
    }

    private boolean containsNlUsageType(List<NlUsageType> typeList, String id) {
    	for(NlUsageType type : typeList) {
    		if(type.getId().equals(id)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Test
    public void testValidNlUsageTypes() {
    	List<NlUsageType> nlUsageTypes = this.dao.find(NlUsageType.class);
    	Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.CATALOG"));
    	Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.EXAMPLE"));
    	Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.COMPOSITION"));
    	Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.RULEEDIT"));
    	Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.RULEVIEW"));
    }
    
    private boolean containsReqComponentType(List<ReqComponentType> typeList, String id) {
    	for(ReqComponentType type : typeList) {
    		if(type.getId().equals(id)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Test
    public void testReqComponentTypesForAntireqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.antireq");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(4, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForAntireqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.antireq");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.credits.completed.none"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.credits.completed.max"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.completed.none"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.max"));
    }

    @Test
    public void testReqComponentTypesForPrereqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.prereq");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(10, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForPrereqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.prereq");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.completed.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.credits.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.gpa.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.grade.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.grade.max"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.permission.org.required"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.permission.instructor.required"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.nof.grade.min"));
    }

    @Test
    public void testReqComponentTypesForCoreqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.coreq");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(3, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForCoreqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.coreq");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.courseList.coreq.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.courseList.coreq.oneof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.enrolled.nof"));
    }

    @Test
    public void testReqComponentTypesForEnrollAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.studentEligibility");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(2, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForEnrollAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.studentEligibility");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.programList.enroll.oneof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.programList.enroll.none"));
    }

    @Test
    public void testReqComponentTypesForProgramSatisfactoryProgressType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.satisfactoryProgress");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(6, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForProgramSatisfactoryProgressType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.satisfactoryProgress");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.admitted.credits"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.credits.max"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.completion.duration"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.candidate.status.duration"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.nof.grade.min"));
    }

    @Test
    public void testReqComponentTypesForProgramEntranceType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.entrance");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(7, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForProgramEntranceType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.entrance");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.notcompleted.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.coursecompleted.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.gpa.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.admitted.credits"));
    }

    @Test
    public void testReqComponentTypesForProgramCompletionType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.completion");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(7, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForProgramCompletionType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.completion");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.coursecompleted.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.gpa.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.credits.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.credits.max"));
    }
    
    private boolean containsReqComponentFieldType(List<ReqComponentFieldType> typeList, String id) {
    	for(ReqComponentFieldType type : typeList) {
    		if(type.getId().equals(id)) {
    			return true;
    		}
    	}
    	return false;
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_course_courseset_completed_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.completed.none");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_course_courseset_completed_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.completed.none");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_reqCompFieldType_cluSet_id() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.completed.all");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_reqCompFieldType_cluSet_id() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.completed.all");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.completed.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.completed.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_enrolled_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.enrolled.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_enrolled_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.enrolled.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.none");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.none");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_gpa_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.gpa.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_gpa_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.gpa.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.gpa"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_courseList_coreq_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.courseList.coreq.all");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_courseList_coreq_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.courseList.coreq.all");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_courseList_coreq_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.courseList.coreq.oneof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_courseList_coreq_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.courseList.coreq.oneof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_programList_enroll_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.programList.enroll.oneof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_programList_enroll_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.programList.enroll.oneof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_programList_enroll_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.programList.enroll.none");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_programList_enroll_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.programList.enroll.none");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(3, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.grade"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.gradeType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_grade_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(3, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_grade_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.grade"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.gradeType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_permission_org_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.org.required");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_permission_org_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.org.required");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.orgid"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_permission_instructor_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.instructor.required");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_permission_instructor_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.instructor.required");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.personid"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_test_score_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_test_score_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.test.score"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_test_score_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_test_score_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.test.score"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_nof_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.nof.grade.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(4, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_nof_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.nof.grade.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.grade"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.gradeType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_notcompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.notcompleted.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_notcompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.notcompleted.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_completed_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.all");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_completed_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.all");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_coursecompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.coursecompleted.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_coursecompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.coursecompleted.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_admitted_credits() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.admitted.credits");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_admitted_credits() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.admitted.credits");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_credits_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_credits_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_credits_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_credits_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_completion_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_completion_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_candidate_status_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.candidate.status.duration");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_candidate_status_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.candidate.status.duration");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_completion_duration_afterentry() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration.afterentry");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_completion_duration_afterentry() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration.afterentry");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType"));
    }
}
