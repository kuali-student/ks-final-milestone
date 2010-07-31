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
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.course");
    	List<StatementType> subTypes = statementType.getAllowedStatementTypes();
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.luStatementType.prereqAcademicReadiness"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.luStatementType.coreqAcademicReadiness"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.luStatementType.enrollAcademicReadiness"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.luStatementType.antireqAcademicReadiness"));
    }

    @Test
    public void testValidStatementSubTypesForProgram() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.program");
    	List<StatementType> subTypes = statementType.getAllowedStatementTypes();
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.luStatementType.programEntrance"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.luStatementType.programSatisfactoryProgress"));
    	Assert.assertTrue(containsStatementType(subTypes, "kuali.luStatementType.programCompletion"));
    }

    @Test
    public void testStatementSubTypesForCourseStatementType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.course");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(4, statementType.getAllowedStatementTypes().size());
    }

    @Test
    public void testStatementSubTypesForProgramStatementType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.program");
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
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.antireqAcademicReadiness");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(4, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForAntireqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.antireqAcademicReadiness");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.credits.completed.none"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.credits.completed.max"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.completed.none"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.test.score.max"));
    }

    @Test
    public void testReqComponentTypesForPrereqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.prereqAcademicReadiness");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(10, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForPrereqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.prereqAcademicReadiness");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.completed.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.credits.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.gpa.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.grade.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.grade.max"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.permission.org.required"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.permission.instructor.required"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.nof.grade.min"));
    }

    @Test
    public void testReqComponentTypesForCoreqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.coreqAcademicReadiness");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(3, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForCoreqAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.coreqAcademicReadiness");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.courseList.coreq.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.courseList.coreq.oneof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.enrolled.nof"));
    }

    @Test
    public void testReqComponentTypesForEnrollAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.enrollAcademicReadiness");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(2, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForEnrollAcademicReadinessType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.enrollAcademicReadiness");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.programList.enroll.oneof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.programList.enroll.none"));
    }

    @Test
    public void testReqComponentTypesForProgramSatisfactoryProgressType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.programSatisfactoryProgress");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(6, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForProgramSatisfactoryProgressType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.programSatisfactoryProgress");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.admitted.credits"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.credits.max"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.completion.duration"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.candidate.status.duration"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.nof.grade.min"));
    }

    @Test
    public void testReqComponentTypesForProgramEntranceType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.programEntrance");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(7, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForProgramEntranceType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.programEntrance");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.programset.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.programset.notcompleted.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.programset.completed.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.programset.coursecompleted.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.gpa.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.admitted.credits"));
    }

    @Test
    public void testReqComponentTypesForProgramCompletionType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.programCompletion");
    	Assert.assertNotNull(statementType);
    	Assert.assertEquals(7, statementType.getAllowedReqComponentTypes().size());
    }

    @Test
    public void testValidReqComponentTypesForProgramCompletionType() throws DoesNotExistException {
    	StatementType statementType = this.dao.fetch(StatementType.class, "kuali.luStatementType.programCompletion");
    	List<ReqComponentType> types = statementType.getAllowedReqComponentTypes();
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.programset.completed.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.programset.completed.all"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.programset.coursecompleted.nof"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.courseset.gpa.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.credits.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.course.test.score.min"));
    	Assert.assertTrue(containsReqComponentType(types, "kuali.reqCompType.program.credits.max"));
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
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.completed.none");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_course_courseset_completed_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.completed.none");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_reqCompFieldType_cluSet_id() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.completed.all");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_reqCompFieldType_cluSet_id() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.completed.all");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.completed.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.completed.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_enrolled_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.enrolled.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_enrolled_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.enrolled.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.credits.completed.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.credits.completed.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.credits.completed.none");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.credits.completed.none");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.credits.completed.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.credits.completed.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_gpa_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.gpa.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_gpa_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.gpa.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.gpa"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_courseList_coreq_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.courseList.coreq.all");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_courseList_coreq_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.courseList.coreq.all");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_courseList_coreq_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.courseList.coreq.oneof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_courseList_coreq_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.courseList.coreq.oneof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_programList_enroll_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.programList.enroll.oneof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_programList_enroll_oneof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.programList.enroll.oneof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_programList_enroll_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.programList.enroll.none");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_programList_enroll_none() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.programList.enroll.none");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.grade.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(3, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.grade.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.grade"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.gradeType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_grade_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.grade.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(3, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_grade_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.grade.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.grade"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.gradeType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_permission_org_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.permission.org.required");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_permission_org_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.permission.org.required");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.orgid"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_permission_instructor_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.permission.instructor.required");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_permission_instructor_required() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.permission.instructor.required");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.personid"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_test_score_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.test.score.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_test_score_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.test.score.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.test.score"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_test_score_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.test.score.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_test_score_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.test.score.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.test.score"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_nof_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.nof.grade.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(4, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_nof_grade_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.course.courseset.nof.grade.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.grade"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.gradeType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.completed.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_completed_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.completed.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_notcompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.notcompleted.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_notcompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.notcompleted.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_completed_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.completed.all");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_completed_all() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.completed.all");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_coursecompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.coursecompleted.nof");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_coursecompleted_nof() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.programset.coursecompleted.nof");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.cluSet.id"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_admitted_credits() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.admitted.credits");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_admitted_credits() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.admitted.credits");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_credits_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.credits.min");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_credits_min() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.credits.min");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_credits_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.credits.max");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_credits_max() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.credits.max");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_completion_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.completion.duration");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_completion_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.completion.duration");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.durationType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_candidate_status_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.candidate.status.duration");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_candidate_status_duration() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.candidate.status.duration");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.durationType"));
    }

    @Test
    public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_completion_duration_afterentry() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.completion.duration.afterentry");
    	Assert.assertNotNull(reqComponentType);
    	Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
    }

    @Test
    public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_completion_duration_afterentry() throws DoesNotExistException {
    	ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqCompType.program.completion.duration.afterentry");
    	List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.requiredCount"));
    	Assert.assertTrue(containsReqComponentFieldType(types, "reqCompFieldType.durationType"));
    }
}
