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
package org.kuali.student.krms;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.kew.util.PerformanceLogger;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.KrmsApiServiceLocator;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.function.FunctionDefinition;
import org.kuali.rice.krms.api.repository.function.FunctionParameterDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition.Builder;
import org.kuali.rice.krms.api.repository.type.KrmsAttributeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeAttribute;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.ActionBoService;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.FunctionBoServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBo;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.impl.repository.TermBoServiceImpl;
import org.kuali.rice.krms.impl.repository.TermSpecificationBo;
import org.kuali.rice.krms.test.KRMSTestCase;
import org.kuali.rice.krms.test.KSLumAbstractBoTest;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;
import org.kuali.rice.test.ClearDatabaseLifecycle;
import org.kuali.rice.test.TransactionalLifecycle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.namespace.QName;

import static org.junit.Assert.*;

@BaselineMode(Mode.NONE)
public class TestKRMSCreateAgendasPhase2 extends KRMSTestCase {

	public TestKRMSCreateAgendasPhase2() {
		super();
		this.setClearTables(false);
	}

	static final String KSNAMESPACE = "KR-RULE-TEST";
	protected ContextBoService contextRepository;
	protected KrmsTypeRepositoryService krmsTypeRepository;
	private AgendaBoService agendaBoService;
	private RuleBoService ruleBoService;
	private FunctionBoServiceImpl functionBoService;

	// Services needed for creation:
	private TermBoService termBoService;
	private SpringResourceLoader krmsTestResourceLoader;
	// Needed for agendas
	Map<String, ContextDefinition> phase1ContextDefs = new HashMap<String, ContextDefinition>();
	KrmsTypeDefinition krmsTypeDefinition;
	ContextDefinition contextStudElig;
	static final String EARTHQUAKE_EVENT = "Earthquake";
	public static final String CAMPUS_CODE_TERM_NAME = "campusCodeTermSpec";
	static final String BOOL1 = "bool1";
	static final String BOOL2 = "bool2";
	static final String PREREQ_TERM_NAME = "prereqTermSpec";

	@Before
	public void setup() {
		getLoadApplicationLifecycle();
		termBoService = KrmsRepositoryServiceLocator.getTermBoService();
		agendaBoService = KrmsRepositoryServiceLocator.getAgendaBoService();
		contextRepository = KrmsRepositoryServiceLocator.getContextBoService();
		ruleBoService = KrmsRepositoryServiceLocator.getRuleBoService();
		krmsTypeRepository = KrmsRepositoryServiceLocator
				.getKrmsTypeRepositoryService();
		functionBoService = KrmsRepositoryServiceLocator
				.getBean("functionRepositoryService");

	}

	@Override
	protected void loadSuiteTestData() throws Exception {
		// Do nothing
	}

	@Override
	protected List<String> getPerTestTablesNotToClear() {
		List<String> tablesNotToClear = super.getPerTestTablesNotToClear();
		// tablesNotToClear.add("KRMS_.*");
		return tablesNotToClear;
	}

	@Override
	protected Lifecycle getLoadApplicationLifecycle() {
		if (krmsTestResourceLoader == null) {
			krmsTestResourceLoader = new SpringResourceLoader(new QName(
					"KRMSTestHarnessApplicationResourceLoader"),
					"classpath:KRMSTestHarnessSpringBeans.xml", null);
			krmsTestResourceLoader
					.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
			getTestHarnessSpringResourceLoader().addResourceLoader(
					krmsTestResourceLoader);
		}
		return krmsTestResourceLoader;
	}


	@Test
	public void createAllAgendasAndRulesPhase1() {
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();

		// Creating agenda 20 based on made up data
		PropositionParametersBuilder proposition20_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_CREDITS, "5", "=");
		PropositionParametersBuilder proposition20_2 = createProposition(
				KSKRMSConstants.TERM_DEPT_NUMBER, "1", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA20,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition20_1, proposition20_2);
		
		// Creating agenda 21 based on made up data
		PropositionParametersBuilder proposition21_1 = createProposition(
				KSKRMSConstants.TERM_ADMIN_ORG_NUMBER, "5", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA21,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition21_1);
		
		// Creating agenda 22 based on made up data
		PropositionParametersBuilder proposition22_1 = createProposition(
				KSKRMSConstants.TERM_TEST, "2nd", "=");
		PropositionParametersBuilder proposition22_2 = createProposition(
						KSKRMSConstants.TERM_SCORE, "5", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA22,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition22_1, proposition22_2);

		// Creating agenda 23 based on made up data
		PropositionParametersBuilder proposition24_1 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSE, "MATH111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1);
		
		// Creating agenda 24 based on made up data, many variations
		PropositionParametersBuilder proposition24_1_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_COURSES, "2", "=");
		PropositionParametersBuilder proposition24_1_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSES, "MATH111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1_1, proposition24_1_2);
		

		PropositionParametersBuilder proposition24_2_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_SET, "MATHS", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1_1, proposition24_2_2);
		

		PropositionParametersBuilder proposition24_3_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSE_NUMBER_RANGE, "111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1_1, proposition24_3_2);
		

		PropositionParametersBuilder proposition24_4_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSE_NUMBER_SUBJECT_CODE, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1_1, proposition24_4_2);
		

		PropositionParametersBuilder proposition24_5_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_EFFECTIVE_DATE_FROM, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1_1, proposition24_5_2);
				

		PropositionParametersBuilder proposition24_6_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_EFFECTIVE_DATE_TO, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1_1, proposition24_6_2);
		

		PropositionParametersBuilder proposition24_7_2 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_COURSES, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA24,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition24_1_1, proposition24_7_2);

		
		// Creating agenda 26 based on made up data
		PropositionParametersBuilder proposition26_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_CREDITS, "5", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA26,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition26_1);
		
		// Creating agenda 25 based on made up data
		PropositionParametersBuilder proposition25_1_1 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSES, "MATH111", "=");
		PropositionParametersBuilder proposition25_1_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSES, "MATH112", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA25,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition25_1_1, proposition25_1_2);
		
		PropositionParametersBuilder proposition25_2 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_SET, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA25,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition25_2);
		
		PropositionParametersBuilder proposition25_3 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSE_NUMBER_RANGE, "112", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA25,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition25_3);
		
		PropositionParametersBuilder proposition25_4 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_LEARNING_OBJ_DESCR, "Skill", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA25,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition25_4);
		
		PropositionParametersBuilder proposition25_5 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_EFFECTIVE_DATE_FROM, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA25,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition25_5);
		
		PropositionParametersBuilder proposition25_6 = createProposition(
				KSKRMSConstants.TERM_ENROLLED_COURSE_NUMBER_SUBJECT_CODE, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA25,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition25_6);
		
		// Creating agenda 27 based on made up data
		PropositionParametersBuilder proposition27_1 = createProposition(
				KSKRMSConstants.TERM_GPA, "5", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA27,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition27_1);
		
		
		// Creating agenda 28 based on made up data
		PropositionParametersBuilder proposition28_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "5", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA28,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition28_1);
		
		// Creating agenda 29 based on made up data
		PropositionParametersBuilder proposition29_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_CREDITS, "5", "=");
		PropositionParametersBuilder proposition29_2 = createProposition(
				KSKRMSConstants.TERM_DEPT_NUMBER, "5", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA29,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition29_1, proposition29_2);
		
		// Creating agenda 30 based on made up data
		PropositionParametersBuilder proposition30_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "MATH111", "=");
		PropositionParametersBuilder proposition30_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "MATH111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA30,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition30_1, proposition30_2);
		
		
		// Creating agenda 31 based on made up data
		PropositionParametersBuilder proposition31_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "AASP111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA31,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition31_1);
		
		PropositionParametersBuilder proposition31_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_NUMBER, "AASP111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA31,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition31_2);
		
		PropositionParametersBuilder proposition31_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_CODE, "AASP111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA31,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition31_3);
		
		PropositionParametersBuilder proposition31_4 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_SET, "AASP", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA31,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition31_4);
		
		PropositionParametersBuilder proposition31_5 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_EFFECTIVE_DATE_FROM, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA31,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition31_5);
		
		
		// Creating agenda 32 based on made up data
		PropositionParametersBuilder proposition32_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "AASP111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA32,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition31_1);
		
		PropositionParametersBuilder proposition32_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_NUMBER, "AASP111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA32,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition32_2);
		
		PropositionParametersBuilder proposition32_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_CODE, "AASP111", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA32,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition32_3);
		
		PropositionParametersBuilder proposition32_4 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_SET, "AASP", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA32,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition32_4);
		
		PropositionParametersBuilder proposition32_5 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_EFFECTIVE_DATE_FROM, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA32,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_CORE_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition32_5);
		
		// Creating agenda 33 based on made up data
		PropositionParametersBuilder proposition33_1_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "MATH111", "=");
		PropositionParametersBuilder proposition33_1_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "MATH112", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA33,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition25_1_1, proposition25_1_2);
		
		PropositionParametersBuilder proposition33_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_SET, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA33,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition33_2);
		
		PropositionParametersBuilder proposition33_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_NUMBER, "112", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA33,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition33_3);
		
		PropositionParametersBuilder proposition33_4 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_LEARNING_OBJ_DESCR, "Skill", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA33,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition33_4);
		
		PropositionParametersBuilder proposition33_5 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_EFFECTIVE_DATE_FROM, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA33,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition33_5);
		
		PropositionParametersBuilder proposition33_6 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_CODE, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA33,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition33_6);
		
		
		// Creating agenda 34 based on made up data
		PropositionParametersBuilder proposition34_1 = createProposition(
				KSKRMSConstants.TERM_SCORE, "5", "=");
		PropositionParametersBuilder proposition34_2 = createProposition(
				KSKRMSConstants.TERM_TEST, "3rd", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA34,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition34_1, proposition34_2);
		
		// Creating agenda 35 based on made up data
		PropositionParametersBuilder proposition35_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_CODE, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA35,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_COURSE_RESTRICTS, KSNAMESPACE),
				null, KSNAMESPACE, proposition35_1);
		
		// Creating agenda 36 based on made up data
		PropositionParametersBuilder proposition36_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "MATH112", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA36,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition36_1);
		
		PropositionParametersBuilder proposition36_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_SET, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA36,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition36_2);
		
		PropositionParametersBuilder proposition36_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_NUMBER, "112", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA36,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition36_3);
		
		PropositionParametersBuilder proposition36_4 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_LEARNING_OBJ_DESCR, "Skill", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA36,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition36_4);
		
		PropositionParametersBuilder proposition36_5 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_EFFECTIVE_DATE_FROM, "12/12/12", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA36,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition36_5);
		
		PropositionParametersBuilder proposition36_6 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE_CODE, "MATH", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA36,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_ANTI_REQUISITE, KSNAMESPACE),
				null, KSNAMESPACE, proposition36_6);
		
		// Creating agenda 37 based on made up data
		PropositionParametersBuilder proposition37_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_CREDITS, "5", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA37,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_COURSE_RESTRICTS, KSNAMESPACE),
				null, KSNAMESPACE, proposition37_1);

	}

	private PropositionParametersBuilder createProposition(String termUsed,
			String PropositionConstant, String Operator) {
		Map<String, PropositionParameterType> propositionsMap;
		propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(krmsTermLookup(termUsed).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put(PropositionConstant,
				PropositionParameterType.CONSTANT);
		propositionsMap.put(Operator, PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);
		return proposition;
	}

	private void createAgendaAndRuleAndPropositions(String agendaName,
			ContextDefinition contextDefinition, String eventName,
			String nameSpace, PropositionParametersBuilder... proposition) {
		// Create Agenda...
		AgendaDefinition agendaDef = createKRMSAgendaDefinition(agendaName,
				contextDefinition);

		// Create AgendaItems
		AgendaItemDefinition.Builder agendaItemBuilder1 = AgendaItemDefinition.Builder
				.create(null, agendaDef.getId());

		// Create Rules
		String ruleDefinitionID = createKRMSRuleDefinition(nameSpace,
				agendaName + "::Rule1", contextDefinition, LogicalOperator.OR,
				proposition).getId();

		agendaItemBuilder1.setRuleId(ruleDefinitionID);

		// Create Agenda Items on DB
		AgendaItemDefinition agendaItem1 = agendaBoService
				.createAgendaItem(agendaItemBuilder1.build());
		AgendaDefinition.Builder agendaDefBuilder1 = AgendaDefinition.Builder
				.create(agendaDef);
		// agendaDefBuilder1.setAttributes(Collections.singletonMap("Event",
		// eventName));
		agendaDefBuilder1.setFirstItemId(agendaItem1.getId());
		agendaDef = agendaDefBuilder1.build();
		// Update Agenda with the AgendaItems id...
		agendaBoService.updateAgenda(agendaDef);

	}

	private AgendaDefinition createKRMSAgendaDefinition(String agendaName,
			ContextDefinition contextDefinition) {
		AgendaDefinition agendaDef = agendaBoService
				.getAgendaByNameAndContextId(agendaName,
						contextDefinition.getId());
		if (krmsTypeDefinition == null) {
			krmsTypeDefinition = getKSKRMSType(KSNAMESPACE);
		}
		if (agendaDef == null) {
			agendaDef = AgendaDefinition.Builder.create(null, agendaName,
					krmsTypeDefinition.getId(), contextDefinition.getId())
					.build();
			agendaDef = agendaBoService.createAgenda(agendaDef);

		}
		return agendaDef;
	}

	private PropositionParametersBuilder buildKRMSProposition(
			Map<String, PropositionParameterType> propositionsMap) {
		int orderOfEntries = 0;
		PropositionParametersBuilder params1 = new PropositionParametersBuilder();
		Set<Entry<String, PropositionParameterType>> allValues = propositionsMap
				.entrySet();

		for (Entry<String, PropositionParameterType> entry : allValues) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			if (entry.getValue() == PropositionParameterType.TERM
					&& orderOfEntries == 0) {
				params1.add(entry.getKey(), entry.getValue());
				orderOfEntries = 1;
			} else if (entry.getValue() == PropositionParameterType.CONSTANT
					&& orderOfEntries == 1) {
				params1.add(entry.getKey(), entry.getValue());
				orderOfEntries = 2;
			} else if (entry.getValue() == PropositionParameterType.OPERATOR
					&& orderOfEntries == 2) {
				params1.add(entry.getKey(), entry.getValue());
				orderOfEntries = 3;
			}
		}
		if (orderOfEntries == 1) {
			for (Entry<String, PropositionParameterType> entry : allValues) {
				System.out.println(entry.getKey() + " " + entry.getValue());
				if (entry.getValue() == PropositionParameterType.CONSTANT
						&& orderOfEntries == 1) {
					params1.add(entry.getKey(), entry.getValue());
					orderOfEntries = 2;
				} else if (entry.getValue() == PropositionParameterType.OPERATOR
						&& orderOfEntries == 2) {
					params1.add(entry.getKey(), entry.getValue());
					orderOfEntries = 3;
				}
			}
		}
		if (orderOfEntries == 2) {
			for (Entry<String, PropositionParameterType> entry : allValues) {
				System.out.println(entry.getKey() + " " + entry.getValue());
				if (entry.getValue() == PropositionParameterType.OPERATOR
						&& orderOfEntries == 2) {
					params1.add(entry.getKey(), entry.getValue());
					orderOfEntries = 3;
				}
			}
		}
		return params1;
	}

	private PropositionDefinition.Builder createKRMSPropositionDefinition(
			String propDescription, PropositionParametersBuilder params,
			RuleDefinition parentRule) {
		// Proposition for rule 2
		PropositionDefinition.Builder propositionDefBuilder1 = PropositionDefinition.Builder
				.create(null, PropositionType.SIMPLE.getCode(),
						parentRule.getId(), null /*
												 * type code is only for custom
												 * props
												 */,
						Collections.<PropositionParameter.Builder> emptyList());
		propositionDefBuilder1.setDescription(propDescription);

		// PropositionParams for rule 2
		List<PropositionParameter.Builder> propositionParams1 = params.build();

		// set the parent proposition so the builder will not puke
		for (PropositionParameter.Builder propositionParamBuilder : propositionParams1) {
			propositionParamBuilder.setProposition(propositionDefBuilder1);
		}

		propositionDefBuilder1.setParameters(propositionParams1);

		return propositionDefBuilder1;
	}

	private RuleDefinition createKRMSRuleDefinition(String nameSpace,
			String ruleName, ContextDefinition contextDefinition,
			LogicalOperator operator, PropositionParametersBuilder... pbs) {
		// Create the Rule
		RuleDefinition.Builder ruleDefBuilder = RuleDefinition.Builder.create(
				null, ruleName, nameSpace, null, null);
		ruleDefBuilder.setDescription(ruleName + "::Description");

		//
		RuleDefinition ruleDef = ruleBoService.getRuleByNameAndNamespace(
				ruleName, KSNAMESPACE);
		//
		if (ruleDef == null) {
			ruleDef = ruleBoService.createRule(ruleDefBuilder.build());
		}

		// Create the proposition for the rule
		PropositionDefinition.Builder parentProposition = PropositionDefinition.Builder
				.create(null, PropositionType.COMPOUND.getCode(),
						ruleDef.getId(), null, null);
		parentProposition
				.setCompoundComponents(new ArrayList<PropositionDefinition.Builder>());

		if (operator != null) {
			parentProposition.setCompoundOpCode(operator.getCode());
		}

		ruleDefBuilder = RuleDefinition.Builder.create(ruleDef);

		//
		ArrayList<PropositionDefinition.Builder> newComponentList = new ArrayList<PropositionDefinition.Builder>();
		for (PropositionParametersBuilder params : pbs) {

			StringBuilder propositionNameBuilder = new StringBuilder(
					"Proposition");

			propositionNameBuilder.append("::");
			for (Object[] param : params.params) {
				propositionNameBuilder.append(param[0].toString());
				propositionNameBuilder.append("--");
			}

			PropositionDefinition.Builder propositionBuilder = createKRMSPropositionDefinition(
					propositionNameBuilder.toString(), params, ruleDef);

			if (pbs.length > 1) {
				// add it to the compound prop
				// List<org.kuali.rice.krms.api.repository.proposition.PropositionDefinition.Builder>
				// compoundComponents =
				// parentProposition.getCompoundComponents();
				newComponentList.add(propositionBuilder);
			} else {
				// if there is only one proposition to build, make it the parent
				parentProposition = propositionBuilder;
			}

		}
		if (newComponentList.size() > 0) {
			parentProposition.setCompoundComponents(newComponentList);

		}
		ruleDefBuilder.setProposition(parentProposition);
		ruleDef = ruleDefBuilder.build();
		ruleBoService.updateRule(ruleDef); // Creating the krms_prop_parm_t &
											// krms_prop_t

		// Action
		// ActionDefinition.Builder actionDefBuilder1 =
		// ActionDefinition.Builder.create(null, ruleName + "::TestAction",
		// nameSpace, createKrmsActionTypeDefinition(nameSpace).getId(),
		// ruleDef1.getId(), 1);
		// ActionDefinition actionDef1 =
		// actionBoService.createAction(actionDefBuilder1.build());

		return ruleDef;
	}

	private TermDefinition krmsTermLookup(String termName) {
		// this may be called more than once, we only want to create one though
		Map<String, String> queryArgs = new HashMap<String, String>();
		queryArgs.put("desc_txt", termName);
		TermBo termBo = getBoService()
				.findByPrimaryKey(TermBo.class, queryArgs);
		if (termBo != null) {
			return TermBo.to(termBo);
		}
		return null;
	}

	private static class PropositionParametersBuilder {

		// poor OOD but this is quick and dirty :-P
		private List<Object[]> params = new ArrayList<Object[]>();

		public PropositionParametersBuilder add(String value,
				PropositionParameterType type) {
			if (type == null)
				throw new IllegalArgumentException("type must not be null");
			params.add(new Object[] { value, type });
			return this;
		}

		public List<PropositionParameter.Builder> build() {
			int seqCounter = 0;

			List<PropositionParameter.Builder> results = new ArrayList<PropositionParameter.Builder>();

			for (Object[] param : params) {
				results.add(PropositionParameter.Builder.create(null, null,
						(String) param[0],
						((PropositionParameterType) param[1]).getCode(),
						seqCounter++));
			}

			return results;
		}
	}

	// methods used
	private KrmsTypeDefinition getKSKRMSType(String nameSpace) {
		KrmsTypeDefinition krmsContextTypeDefinition = krmsTypeRepository
				.getTypeByName(nameSpace, KSKRMSConstants.KS_AGENDA_TYPE);

		if (krmsContextTypeDefinition == null) {

			KrmsTypeDefinition.Builder krmsContextTypeDefnBuilder = KrmsTypeDefinition.Builder
					.create(KSKRMSConstants.KS_AGENDA_TYPE, nameSpace);
			krmsContextTypeDefnBuilder.setServiceName("myKSService");

			// TODO KSKRMS not sure where the Attributes fit in and how they
			// link up
			// int contextAttrSequenceIndex = 0;
			// List<KrmsTypeAttribute.Builder> contextAttributeBuilders = new
			// ArrayList<KrmsTypeAttribute.Builder>();
			// for (KrmsAttributeDefinition attrDef :
			// ksLumAttributeDefinitions.values()) {
			// contextAttributeBuilders.add(KrmsTypeAttribute.Builder.create(null,
			// attrDef.getId(),
			// contextAttrSequenceIndex));
			// contextAttrSequenceIndex += 1;
			//
			// }
			// krmsContextTypeDefnBuilder.setAttributes(contextAttributeBuilders);

			krmsContextTypeDefinition = krmsTypeRepository
					.createKrmsType(krmsContextTypeDefnBuilder.build());
			return krmsContextTypeDefinition;
		}

		return krmsContextTypeDefinition;
	}

	protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
	}
}