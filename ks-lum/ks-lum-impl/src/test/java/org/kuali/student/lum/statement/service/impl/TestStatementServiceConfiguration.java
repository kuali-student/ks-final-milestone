package org.kuali.student.lum.statement.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractTransactionalDaoTest;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.r1.core.statement.dao.StatementDao;
import org.kuali.student.r1.core.statement.entity.NlUsageType;
import org.kuali.student.r1.core.statement.entity.OrderedReqComponentType;
import org.kuali.student.r1.core.statement.entity.OrderedStatementType;
import org.kuali.student.r1.core.statement.entity.ReqComponentFieldType;
import org.kuali.student.r1.core.statement.entity.ReqComponentType;
import org.kuali.student.r1.core.statement.entity.ReqComponentTypeNLTemplate;
import org.kuali.student.r1.core.statement.entity.StatementType;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.constants.StatementServiceConstants;

import java.util.List;

@PersistenceFileLocation("classpath:META-INF/statement-persistence.xml")
public class TestStatementServiceConfiguration extends AbstractTransactionalDaoTest {
    @Dao(value = "org.kuali.student.r1.core.statement.dao.impl.StatementDaoImpl", testSqlFile = "classpath:ks-statement.sql")
    public StatementDao dao;

        private boolean containsStatementType(List<OrderedStatementType> typeList, String id) {
            for(OrderedStatementType type : typeList) {
                if(type.getChildStatementType().getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }

        @Test
        public void testStatementTypeCount() {
            List<StatementType> statementTypes = this.dao.find(StatementType.class);
            Assert.assertEquals(15, statementTypes.size());
        }

        @Test
        public void testValidStatementSubTypesForCourse() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course");
            List<OrderedStatementType> subTypes = statementType.getAllowedStatementTypes();
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.enrollmentEligibility"));
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.creditConstraints"));
        }

        @Test
        public void testValidStatementSubTypesForEnrollmentEligibility() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.enrollmentEligibility");
            List<OrderedStatementType> subTypes = statementType.getAllowedStatementTypes();
            Assert.assertTrue(containsStatementType(subTypes, StatementServiceConstants.PREREQUISITE_STATEMENT_TYPE));
            Assert.assertTrue(containsStatementType(subTypes, StatementServiceConstants.COREQUISITE_STATEMENT_TYPE));
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.recommendedPreparation"));
            Assert.assertTrue(containsStatementType(subTypes, StatementServiceConstants.ANTIREQUISITE_STATEMENT_TYPE));
        }

        @Test
        public void testValidStatementSubTypesForCreditConstraints() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.creditConstraints");
            List<OrderedStatementType> subTypes = statementType.getAllowedStatementTypes();
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.credit.restriction"));
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.course.credit.repeatable"));
        }

        @Test
        public void testValidStatementSubTypesForProgram() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program");
            List<OrderedStatementType> subTypes = statementType.getAllowedStatementTypes();
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.program.entrance"));
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.program.satisfactoryProgress"));
            Assert.assertTrue(containsStatementType(subTypes, "kuali.statement.type.program.completion"));
        }

        @Test
        public void testStatementSubTypesForCourseStatementType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course");
            Assert.assertEquals(2, statementType.getAllowedStatementTypes().size());
        }

        @Test
        public void testStatementSubTypesForProgramStatementType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program");
            Assert.assertEquals(3, statementType.getAllowedStatementTypes().size());
        }

        @Test
        public void testReqComponentTypeCount() {
            List<ReqComponentType> reqComponentTypes = this.dao.find(ReqComponentType.class);
            Assert.assertEquals(42, reqComponentTypes.size());
        }

        @Test
        public void testReqComponentFieldTypeCount() {
            List<ReqComponentFieldType> reqComponentFieldTypes = this.dao.find(ReqComponentFieldType.class);
            Assert.assertEquals(18, reqComponentFieldTypes.size());
        }

        @Test
        public void testReqComponentTypeNLTemplateCount() {
            List<ReqComponentTypeNLTemplate> reqComponentTypeNLTemplates = this.dao.find(ReqComponentTypeNLTemplate.class);
            Assert.assertEquals(168, reqComponentTypeNLTemplates.size());
        }

        @Test
        public void testNlUsageTypeCount() {
            List<NlUsageType> nlUsageTypes = this.dao.find(NlUsageType.class);
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
            Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.RULE"));
            Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.RULE.PREVIEW"));
            Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.RULE.EXAMPLE"));
            Assert.assertTrue(containsNlUsageType(nlUsageTypes, "KUALI.RULE.COMPOSITION"));
        }

        private boolean containsReqComponentType(List<OrderedReqComponentType> typeList, String id) {
            for(OrderedReqComponentType type : typeList) {
                if(type.getReqComponentType().getId().equals(id)) {
                    return true;
                }
            }
            return false;
        }

        @Test
        public void testReqComponentTypesForAntireqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, StatementServiceConstants.ANTIREQUISITE_STATEMENT_TYPE);
            Assert.assertEquals(5, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForAntireqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, StatementServiceConstants.ANTIREQUISITE_STATEMENT_TYPE);
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertEquals("kuali.reqComponent.type.course.notcompleted", types.get(0).getReqComponentType().getId());
            Assert.assertEquals("kuali.reqComponent.type.course.courseset.completed.none", types.get(1).getReqComponentType().getId());
            Assert.assertEquals("kuali.reqComponent.type.course.courseset.credits.completed.none", types.get(2).getReqComponentType().getId());
            Assert.assertEquals("kuali.reqComponent.type.course.courseset.credits.completed.max", types.get(3).getReqComponentType().getId());
            Assert.assertEquals("kuali.reqComponent.type.course.test.score.max", types.get(4).getReqComponentType().getId());
        }

        @Test
        public void testReqComponentTypesForPrereqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.prereq");
            Assert.assertEquals(13, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForPrereqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.prereq");
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertTrue(containsReqComponentType(types, StatementServiceConstants.ALL_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE));
            Assert.assertTrue(containsReqComponentType(types, StatementServiceConstants.N_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.credits.completed.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.gpa.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.grade.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.grade.max"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.permission.org.required"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.permission.instructor.required"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.nof.grade.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.admitted.org.duration"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.org.credits.completed.min"));
        }

        @Test
        public void testReqComponentTypesForCoreqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, StatementServiceConstants.COREQUISITE_STATEMENT_TYPE);
            Assert.assertEquals(2, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForCoreqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, StatementServiceConstants.COREQUISITE_STATEMENT_TYPE);
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            //Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.courseList.coreq.all"));
            //Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.courseList.coreq.oneof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.enrolled.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.enrolled"));
        }

        @Test
        public void testReqComponentTypesForStudentEligibilityType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.academicReadiness.studentEligibility");
            Assert.assertEquals(4, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForStudentEligibilityType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, StatementServiceConstants.PREREQUISITE_STATEMENT_TYPE);
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            //Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.programList.enroll.oneof"));
            //Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.programList.enroll.none"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.notadmitted.org.duration"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.org.program.admitted"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.notadmitted"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.admitted"));
        }

        @Test
        public void testReqComponentTypesForStudentEligibilityPreReqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, StatementServiceConstants.PREREQUISITE_STATEMENT_TYPE);
            Assert.assertEquals(17, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForStudentEligibilityPreReqType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, StatementServiceConstants.PREREQUISITE_STATEMENT_TYPE);
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            //Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.programList.enroll.oneof"));
            //Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.programList.enroll.none"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.notadmitted.org.duration"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.org.program.admitted"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.notadmitted"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.admitted"));
            Assert.assertTrue(containsReqComponentType(types, StatementServiceConstants.COMPLETED_COURSE_REQ_COM_TYPE));
            Assert.assertTrue(containsReqComponentType(types, StatementServiceConstants.ALL_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE));
            Assert.assertTrue(containsReqComponentType(types, StatementServiceConstants.N_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.credits.completed.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.gpa.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.grade.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.grade.max"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.permission.org.required"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.permission.instructor.required"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.nof.grade.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.program.admitted.org.duration"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.org.credits.completed.min"));
        }

        @Test
        public void testReqComponentTypesForRepeatableForCreditType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.credit.repeatable");
            Assert.assertEquals(1, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForRepeatableForCreditType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.credit.repeatable");
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.credits.repeat.max"));
        }

        @Test
        public void testReqComponentTypesForRecommendedPreparationType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.recommendedPreparation");
            Assert.assertEquals(2, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForRecommendedPreparationType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.recommendedPreparation");
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.org.credits.completed.min"));
            Assert.assertTrue(containsReqComponentType(types, StatementServiceConstants.COMPLETED_COURSE_REQ_COM_TYPE));
        }

        @Test
        public void testReqComponentTypesForCoursesThatRestrictCreditType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.credit.restriction");
            Assert.assertEquals(2, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForCoursesThatRestrictCreditType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.course.credit.restriction");
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.completed.none"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.notcompleted"));
        }

        @Test
        public void testReqComponentTypesForProgramSatisfactoryProgressType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.satisfactoryProgress");
            Assert.assertEquals(7, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForProgramSatisfactoryProgressType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.satisfactoryProgress");
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.admitted.credits"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.credits.max"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.completion.duration"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.candidate.status.duration"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.nof.grade.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.cumulative.gpa.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.duration.gpa.min"));
        }

        @Test
        public void testReqComponentTypesForProgramEntranceType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.entrance");
            Assert.assertEquals(19, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForProgramEntranceType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.entrance");
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.notcompleted.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.all"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.coursecompleted.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.gpa.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.test.score.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.admitted.credits"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.minor.admitted.classstanding"));
        }

        @Test
        public void testReqComponentTypesForProgramCompletionType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.completion");
            Assert.assertEquals(18, statementType.getAllowedReqComponentTypes().size());
        }

        @Test
        public void testValidReqComponentTypesForProgramCompletionType() throws DoesNotExistException {
            StatementType statementType = this.dao.fetch(StatementType.class, "kuali.statement.type.program.completion");
            List<OrderedReqComponentType> types = statementType.getAllowedReqComponentTypes();
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.completed.all"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.programset.coursecompleted.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.course.courseset.gpa.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.credits.min"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.residence.credits.final"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.enrolled.credits.final"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.completion.duration.afterentry"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.minors.nof"));
            Assert.assertTrue(containsReqComponentType(types, "kuali.reqComponent.type.program.cumulative.gpa.min"));
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
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_course_courseset_completed_none() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.completed.none");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_reqCompFieldType_cluSet_id() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, StatementServiceConstants.ALL_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE);
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_reqCompFieldType_cluSet_id() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, StatementServiceConstants.ALL_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE);
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_completed_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, StatementServiceConstants.N_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE);
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_completed_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, StatementServiceConstants.N_OF_REQUIRED_COURSES_COMPLETED_REQ_COM_TYPE);
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_enrolled_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.enrolled.nof");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_enrolled_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.enrolled.nof");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.nof");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.nof");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_none() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.none");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_none() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.none");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.max");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_credits_completed_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.credits.completed.max");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_gpa_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.gpa.min");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_gpa_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.gpa.min");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.gpa"));
        }

        /*@Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_courseList_coreq_all() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.courseList.coreq.all");
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
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_programList_enroll_none() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.programList.enroll.none");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.cluSet.id"));
        }*/

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_grade_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.min");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_grade_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.min");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.grade.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_grade_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.max");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_grade_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.grade.max");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.grade.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_permission_org_required() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.org.required");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_permission_org_required() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.org.required");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.org.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_permission_instructor_required() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.instructor.required");
            Assert.assertTrue(reqComponentType.getReqCompFieldTypes().isEmpty());
        }

        /*@Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_permission_instructor_required() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.permission.instructor.required");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.person.id"));
        }*/

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_test_score_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.min");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_test_score_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.min");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.test.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.test.score"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_test_score_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.max");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_test_score_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.test.score.max");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.test.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.test.score"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_course_courseset_nof_grade_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.nof.grade.min");
            Assert.assertEquals(3, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_course_courseset_nof_grade_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.courseset.nof.grade.min");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.grade.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_program_admitted_org_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.admitted.org.duration");
            Assert.assertEquals(5, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_program_admitted_org_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.admitted.org.duration");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.org.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.duration"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_program_notadmitted_org_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.notadmitted.org.duration");
            Assert.assertEquals(5, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_program_notadmitted_org_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.notadmitted.org.duration");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.org.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.duration"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_org_program_admitted() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.org.program.admitted");
            Assert.assertTrue(reqComponentType.getReqCompFieldTypes().isEmpty());
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_program_notadmitted() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.notadmitted");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_program_notadmitted() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.notadmitted");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_program_admitted() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.admitted");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_program_admitted() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.program.admitted");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_credits_repeat_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.credits.repeat.max");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_credits_repeat_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.credits.repeat.max");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_org_credits_completed_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.org.credits.completed.min");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_org_credits_completed_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.org.credits.completed.min");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.org.id"));
        }


        /* Programs */


        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_completed_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.nof");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_completed_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.nof");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_notcompleted_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.notcompleted.nof");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_notcompleted_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.notcompleted.nof");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_completed_all() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.all");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_completed_all() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.completed.all");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_programset_coursecompleted_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.coursecompleted.nof");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_programset_coursecompleted_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.programset.coursecompleted.nof");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.cluSet.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_admitted_credits() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.admitted.credits");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_admitted_credits() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.admitted.credits");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_credits_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.min");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_credits_min() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.min");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_credits_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.max");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_credits_max() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.credits.max");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_completion_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_completion_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.duration"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_candidate_status_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.candidate.status.duration");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_candidate_status_duration() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.candidate.status.duration");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.duration"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqCompType_program_completion_duration_afterentry() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration.afterentry");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqCompType_program_completion_duration_afterentry() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.completion.duration.afterentry");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.duration"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.durationType.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_program_residence_credits_final() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.residence.credits.final");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_program_residence_credits_final() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.residence.credits.final");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.clu.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_program_enrolled_credits_final() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.enrolled.credits.final");
            Assert.assertEquals(2, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_program_enrolled_credits_final() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.enrolled.credits.final");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.program.clu.id"));
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_program_minors_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.minors.nof");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_program_minors_nof() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.minors.nof");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.value.positive.integer"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_program_minor_admitted_classstanding() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.program.minor.admitted.classstanding");
            Assert.assertTrue(reqComponentType.getReqCompFieldTypes().isEmpty());
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_completed() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, StatementServiceConstants.COMPLETED_COURSE_REQ_COM_TYPE);
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_completed() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, StatementServiceConstants.COMPLETED_COURSE_REQ_COM_TYPE);
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.clu.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_enrolled() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.enrolled");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_enrolled() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.enrolled");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.clu.id"));
        }

        @Test
        public void testReqComponentFieldTypeCountForReqComponentType_kuali_reqComponent_type_course_notcompleted() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.notcompleted");
            Assert.assertEquals(1, reqComponentType.getReqCompFieldTypes().size());
        }

        @Test
        public void testReqComponentFieldTypeForReqComponentType_kuali_reqComponent_type_course_notcompleted() throws DoesNotExistException {
            ReqComponentType reqComponentType = this.dao.fetch(ReqComponentType.class, "kuali.reqComponent.type.course.notcompleted");
            List<ReqComponentFieldType> types = reqComponentType.getReqCompFieldTypes();
            Assert.assertTrue(containsReqComponentFieldType(types, "kuali.reqComponent.field.type.course.clu.id"));
        }
    }
