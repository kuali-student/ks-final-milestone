/**
 * Copyright 2005-2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.exam.service.impl;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.Context;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslatorImpl;
import org.kuali.rice.krms.impl.repository.ActionBoService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.KrmsTypeRepositoryServiceImpl;
import org.kuali.rice.krms.impl.repository.mock.KrmsTypeRepositoryServiceMockImpl;
import org.kuali.rice.krms.impl.type.KrmsTypeResolverImpl;
import org.kuali.rice.test.SQLDataLoader;
import org.kuali.student.common.util.krms.ManualContextProvider;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.process.krms.KSKRMSTestCase;
import org.kuali.student.r2.core.process.krms.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.scheduling.SchedulingServiceDataLoader;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * This test is used to test the Exam Matrix Execution.
 *
 * @Author: SW Genis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:exam-matrix-poc-context.xml"})
@Ignore
public class TestExamMatrixExecution extends KSKRMSTestCase {

    @Resource(name = "courseOfferingService")
    protected CourseOfferingService courseOfferingService;
    @Resource(name = "courseService")
    protected CourseService courseService;
    @Resource(name = "stateService")
    protected StateService stateService;
    @Resource(name = "luiService")
    protected LuiService luiService;
    @Resource(name = "acalService")
    protected AcademicCalendarService acalService;
    @Resource(name = "atpService")
    protected AtpService atpService;
    @Resource(name = "lrcService")
    protected LRCService lrcService;
    @Resource(name = "schedulingService")
    protected SchedulingService schedulingService;
    @Resource(name = "schedulingServiceDataLoader")
    private SchedulingServiceDataLoader schedulingServiceDataLoader;
    @Resource(name = "courseOfferingDataLoader")
    private CourseOfferingServiceTestDataLoader courseOfferingDataLoader;

    public static String principalId = "123";
    public ContextInfo callContext = new ContextInfo();

    @Before
    public void setUp() throws Exception {
        super.setUp();

        callContext.setPrincipalId(principalId);

        if(schedulingServiceDataLoader!=null){
            schedulingServiceDataLoader.loadData();
            courseOfferingDataLoader.loadTerms();
            loadActivityOfferingData();
        }
    }

    @Override
    protected void loadSuiteTestData() throws Exception {
        new SQLDataLoader("classpath:ks-krms-exam-matrix.sql", "/").runSql();
    }

    @Test
    public void testExamMatrixExecution() {
        //testTermResolver();
        //testRuleExecution();
    }

    private void testRuleExecution() {

        RuleDefinition ruleDefinition = KrmsRepositoryServiceLocator.getRuleBoService().getRuleByRuleId("KS-KRMS-RULE-12037");
        Rule rule = KrmsRepositoryServiceLocator.getKrmsRepositoryToEngineTranslator().translateRuleDefinition(ruleDefinition);
        BasicAgendaTree agendaTree = new BasicAgendaTree(new BasicAgendaTreeEntry(rule));
        Agenda agenda = new BasicAgenda(Collections.singletonMap(AgendaDefinition.Constants.EVENT, "Exam Matrix"), agendaTree);

        List<TermResolver<?>> termResolvers = new ArrayList<TermResolver<?>>();
        termResolvers.add(getMatchingTimeSlotTermResolver());

        ProviderBasedEngine engine = new ProviderBasedEngine();
        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        engine.setContextProvider(new ManualContextProvider(context));

        Map<String, String> contextQualifiers = Collections.singletonMap(RulesExecutionConstants.DOCTYPE_CONTEXT_QUALIFIER, RulesExecutionConstants.STUDENT_ELIGIBILITY_DOCTYPE);
        Map<String, String> agendaQualifiers = Collections.singletonMap(AgendaDefinition.Constants.EVENT, "Exam Matrix");
        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(new DateTime(), contextQualifiers, agendaQualifiers);

        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, callContext);
        executionFacts.put(KSKRMSServiceConstants.TERM_PREREQUISITE_AO_ID, "AO1");

        ExecutionOptions executionOptions = new ExecutionOptions();
        executionOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
        executionOptions.setFlag(ExecutionFlag.EVALUATE_ALL_PROPOSITIONS, true);

        engine.execute(selectionCriteria, executionFacts, executionOptions);
    }

    private void testTermResolver() {
        Map<String, Object> resolvedPrereqs = new HashMap<String, Object>();
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO, callContext);
        resolvedPrereqs.put(KSKRMSServiceConstants.TERM_PREREQUISITE_AO_ID, "AO1");

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING, "MWF");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START, "45000000");
        parameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_END, "49500000");

        Boolean result = getMatchingTimeSlotTermResolver().resolve(resolvedPrereqs, parameters);
        assertTrue(result);
    }

    private MatchingTimeSlotTermResolver getMatchingTimeSlotTermResolver() {
        MatchingTimeSlotTermResolver termResolver = new MatchingTimeSlotTermResolver();
        termResolver.setCourseOfferingService(courseOfferingService);
        termResolver.setSchedulingService(schedulingService);
        return termResolver;
    }

    private void loadActivityOfferingData() throws Exception {
        CourseInfo c1 = createCanonicalCourse("C1", "ENG101", "English 101");
        CourseOfferingInfo co1 = createCourseOffering("CO1", c1, "termId");
        FormatOfferingInfo fo1 = createFormatOffering("FO1", co1);

        createActivityOffering("AO1", co1, fo1, "testScheduleId1");
        createActivityOffering("AO2", co1, fo1, "testScheduleId2");
        createActivityOffering("AO3", co1, fo1, "testScheduleId3");
    }

    private CourseInfo createCanonicalCourse(String id, String code, String title) throws Exception {
        CourseInfo course = new CourseInfo();
        course.setId(id);
        course.setCode(code);
        course.setCourseTitle(title);
        ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
        rvg.setKey(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_0);
        course.getCreditOptions().add(rvg);
        return courseService.createCourse(course, callContext);
    }

    private CourseOfferingInfo createCourseOffering(String id, CourseInfo course, String termId) throws Exception {
        CourseOfferingInfo co = CourseOfferingServiceTestDataUtils.createCourseOffering(course, termId);
        List<String> optionKeys = new ArrayList<String>();
        co.setId(id);
        return courseOfferingService.createCourseOffering(co.getCourseId(), co.getTermId(),
                co.getTypeKey(), co, optionKeys, callContext);
    }

    private FormatOfferingInfo createFormatOffering(String id, CourseOfferingInfo co) throws Exception {
        FormatOfferingInfo fo = CourseOfferingServiceTestDataUtils.createFormatOffering(co.getId(), "formatId", co.getTermId(),
                "formatName", LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        fo.setId(id);
        return courseOfferingService.createFormatOffering(fo.getCourseOfferingId(), fo.getFormatId(), fo.getTypeKey(), fo, callContext);
    }

    private ActivityOfferingInfo createActivityOffering(String id, CourseOfferingInfo co, FormatOfferingInfo fo, String scheduleId) throws Exception {
        List<OfferingInstructorInfo> instructors = new ArrayList<OfferingInstructorInfo>();
        instructors.add(CourseOfferingServiceTestDataUtils.createInstructor("p1", "Instructor", 100.00F));

        ActivityOfferingInfo ao = CourseOfferingServiceTestDataUtils.createActivityOffering(fo.getTermId(),
                co, fo.getId(), generateScheduleIdList(scheduleId), "activityId", "activityName", "activityCode", "activityOfferingTypeKey", instructors);
        ao.setId(id);
        return courseOfferingService.createActivityOffering(ao.getFormatOfferingId(), ao.getActivityId(), ao.getTypeKey(), ao, callContext);
    }

    private List<String> generateScheduleIdList(String... ids) {
        List<String> scheduleIds = new ArrayList<String>();
        for(String id : ids) {
            scheduleIds.add(id);
        }
        return scheduleIds;
    }

}