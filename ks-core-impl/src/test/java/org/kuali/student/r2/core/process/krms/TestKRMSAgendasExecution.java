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
package org.kuali.student.r2.core.process.krms;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.*;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.framework.engine.*;
import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.expression.ComparisonOperatorServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.common.util.krms.ManualContextProvider;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.process.krms.evaluator.KRMSEvaluator;
import org.kuali.student.r2.core.process.krms.sample.ActionMock;
import org.kuali.student.r2.core.process.krms.sample.TermResolverMock;

import java.util.*;

import static junit.framework.Assert.assertNotNull;

/**
 * This test is used to test the Rice Krms integration for KS.
 *
 * This Test class needs a physical database to execute and is therefore @Ignored.
 * This is only used in development until a proper solution is found to make this
 * part of the CI environment.
 *
 * @Author: SW Genis
 */
@Ignore
public class TestKRMSAgendasExecution extends KSKRMSTestCase {

    private KRMSEvaluator evaluator;

    private List<TermResolver<?>> termResolvers;

    private static final Term permissionTerm = new Term("orgPermissionMock");
    private static final Term numberOfCoursesTerm0 = new Term("numberOfCoursesMock0");
    private static final Term numberOfCoursesTerm2 = new Term("numberOfCoursesMock2");

    @Before
    public void setUp() throws Exception {
        super.setUp();

        evaluator = new KRMSEvaluator() {
        };

    }

    @Test
    public void testKRMSEvaluator() {

        termResolvers = new ArrayList<TermResolver<?>>();
        termResolvers.add(new TermResolverMock<Boolean>("orgPermissionMock", Boolean.TRUE));
        termResolvers.add(new TermResolverMock<Integer>("numberOfCoursesMock0", 0));
        termResolvers.add(new TermResolverMock<Integer>("numberOfCoursesMock2", 2));

        evaluator.setTermResolvers(termResolvers);

        ComparisonOperator operatorGreaterThan = ComparisonOperator.GREATER_THAN;
        operatorGreaterThan.setComparisonOperatorService(ComparisonOperatorServiceImpl.getInstance());

        ComparisonOperator operatorEquals = ComparisonOperator.EQUALS;
        operatorEquals.setComparisonOperatorService(ComparisonOperatorServiceImpl.getInstance());

        Proposition oneCourseFromList0 = new ComparableTermBasedProposition(operatorGreaterThan, numberOfCoursesTerm0, 1);
        Proposition oneCourseFromList2 = new ComparableTermBasedProposition(operatorGreaterThan, numberOfCoursesTerm2, 1);
        Proposition permFromDept = new ComparableTermBasedProposition(ComparisonOperator.EQUALS, permissionTerm, Boolean.TRUE);

        ActionMock.resetActionsFired();

        Rule rule1 = new BasicRule("GEOG123 Prerequisites", oneCourseFromList0, Collections.<Action>singletonList(new ActionMock("a1")));
        Rule rule2 = new BasicRule("GEOG123 Prerequisites", oneCourseFromList2, Collections.<Action>singletonList(new ActionMock("a2")));
        Rule rule3 = new BasicRule("GEOG123 Corequisites", permFromDept, Collections.<Action>singletonList(new ActionMock("a3")));

        AgendaTreeEntry entry1 = new BasicAgendaTreeEntry(rule1);
        AgendaTreeEntry entry2 = new BasicAgendaTreeEntry(rule2);
        AgendaTreeEntry entry3 = new BasicAgendaTreeEntry(rule3);
        BasicAgendaTree agendaTree = new BasicAgendaTree(entry1, entry2, entry3);

        Agenda agenda = new BasicAgenda(Collections.singletonMap(AgendaDefinition.Constants.EVENT, "GEOG123 Course Requirements"), agendaTree);

        evaluator.evaluateAgenda(agenda, this.buildExecutionFacts(), Collections.singletonMap(AgendaDefinition.Constants.EVENT, "GEOG123 Course Requirements"));

        Assert.assertFalse(ActionMock.actionFired("a1"));
        Assert.assertTrue(ActionMock.actionFired("a2"));
        Assert.assertTrue(ActionMock.actionFired("a3"));
    }

    @Test
    public void testKRMSEvaluatorWithAgendaService() {

        termResolvers = new ArrayList<TermResolver<?>>();
        termResolvers.add(new OrganizationalPermissionTermResolver());
        termResolvers.add(new NumberOfCoursesInListTermResolver());

        evaluator.setTermResolvers(termResolvers);

        AgendaDefinition agendaDefinition = KrmsRepositoryServiceLocator.getAgendaBoService().getAgendaByAgendaId("20000");
        assertNotNull(agendaDefinition);

        AgendaTree agendaTree = KrmsRepositoryServiceLocator.getKrmsRepositoryToEngineTranslator().translateAgendaDefinitionToAgendaTree(agendaDefinition);
        Agenda agenda = new BasicAgenda(Collections.singletonMap(AgendaDefinition.Constants.EVENT, "GEOG123 Course Requirements"), agendaTree);

        EngineResults results = evaluator.evaluateAgenda(agenda, this.buildExecutionFacts(), Collections.singletonMap(AgendaDefinition.Constants.EVENT, "GEOG123 Course Requirements"));

        for (ResultEvent resultEvent : results.getAllResults()){
            System.out.println(resultEvent);
        }
        //List<ValidationResultInfo> validationResults = evaluator.buildValidationResultsFromEngineResults(results);
    }

    private Map<String, Object> buildExecutionFacts() {
        Map<String, Object> executionFacts = new HashMap<String, Object>();
        executionFacts.put(RulesExecutionConstants.STUDENT_ID_TERM_NAME, "2016");
        return executionFacts;
    }

    public class OrganizationalPermissionTermResolver implements TermResolver<Boolean> {

        @Override
        public Set<String> getPrerequisites() {
            return Collections.singleton(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        }

        @Override
        public String getOutput() {
            return RulesExecutionConstants.ORG_PERMISSION_TERM_NAME;
        }

        @Override
        public Set<String> getParameterNames() {
            return Collections.singleton(RulesExecutionConstants.ORGANIZATION_ID_PROPERTY);
        }

        @Override
        public int getCost() {
            return 0;
        }

        @Override
        public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

            String studentId = (String) resolvedPrereqs.get(RulesExecutionConstants.STUDENT_ID_TERM_NAME);

            if (studentId.equals("2106")){
                return true;
            }

            return false;
        }
    }

    public class NumberOfCoursesInListTermResolver implements TermResolver<Integer> {

        @Override
        public Set<String> getPrerequisites() {
            return Collections.singleton(RulesExecutionConstants.STUDENT_ID_TERM_NAME);
        }

        @Override
        public String getOutput() {
            return RulesExecutionConstants.NR_OF_COMPLETED_COURSES_TERM_NAME;
        }

        @Override
        public Set<String> getParameterNames() {
            return Collections.singleton(RulesExecutionConstants.REQUIRED_COURSES_PROPERTY);
        }

        @Override
        public int getCost() {
            return 0;
        }

        @Override
        public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

            String studentId = (String) resolvedPrereqs.get(RulesExecutionConstants.STUDENT_ID_TERM_NAME);

            if (studentId.equals("2106")){
                return 1;
            }

            return 0;
        }
    }

}