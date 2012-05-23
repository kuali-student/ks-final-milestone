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
public class TestKRMSCreateAgendasPhase1 extends KRMSTestCase {

	public TestKRMSCreateAgendasPhase1() {
		super();
		this.setClearTables(false);
	}

	static final String KSNAMESPACE = "KR-RULE-TEST";
	protected ContextBoService contextRepository;
	protected KrmsTypeRepositoryService krmsTypeRepository;
	private AgendaBoService agendaBoService;
	private RuleBoService ruleBoService;
	private FunctionBoServiceImpl functionBoService;

	// // Services needed for creation:
	private TermBoService termBoService;
	private SpringResourceLoader krmsTestResourceLoader;
	// Agendas
	Map<String, ContextDefinition> phase1ContextDefs = new HashMap<String, ContextDefinition>();
	KrmsTypeDefinition krmsTypeDefinition;
	ContextDefinition contextStudElig;
	static final String AGENDA1 = "Must have successfully completed <course>";
	static final String AGENDA2 = "Must have successfully completed all courses from <courses>";
	static final String EARTHQUAKE_EVENT = "Earthquake";
	public static final String CAMPUS_CODE_TERM_NAME = "campusCodeTermSpec";
	static final String BOOL1 = "bool1";
	static final String BOOL2 = "bool2";
	static final String PREREQ_TERM_NAME = "prereqTermSpec";
	private static final String AGENDA11 = "Must have earned a minimum cumulative GPA of <GPA>";
	private static final String AGENDA4 = "xxxMust have successfully completed a minimum of <n> courses from <courses>";

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

	// @Test
	public void createRuleDefinitions() {

		// RuleDefinition ruleDefinition =
		// createRuleDefinition1(contextRepository.getContextByNameAndNamespace(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY,
		// KSNAMESPACE),
		// AGENDA1, KSNAMESPACE, KSKRMSConstants.TERM_APPROVED_COURSE);
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSE).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111", PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);
	}

	// @Test
	public void TestBuildProposition() {

		// RuleDefinition ruleDefinition =
		// createRuleDefinition1(contextRepository.getContextByNameAndNamespace(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY,
		// KSNAMESPACE),
		// AGENDA1, KSNAMESPACE, KSKRMSConstants.TERM_APPROVED_COURSE);
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSE).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111", PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);
	}

	// @Test
	public void createAllAgendasPhase1() {
		// Creating all the agendas

		AgendaDefinition agendaDef = createKRMSAgendaDefinition(AGENDA1,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE));

	}

	@Test
	public void createAllAgendasAndRulesPhase1() {
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();
		// Creating all the agendas
		propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSE).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111", PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);

		createAgendaAndRuleAndPropositions(AGENDA1,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition);

		// Change specifically to the rule being created...
		propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSES).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111, MATH211",
				PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		proposition = buildKRMSProposition(propositionsMap);
		createAgendaAndRuleAndPropositions(AGENDA2,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition);

		// Change specifically to the rule being created...
		propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_NUMBER_OF_CREDITS).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("13", PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition1 = buildKRMSProposition(propositionsMap);
		propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSES).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111, MATH140, MATH141",
				PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition2 = buildKRMSProposition(propositionsMap);
		createAgendaAndRuleAndPropositions(AGENDA4,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition1, proposition2);

	}

	private void createAgenda1(String agendaName,
			ContextDefinition contextDefinition, String eventName,
			String nameSpace) {
		// Create Agenda...
		AgendaDefinition agendaDef = createKRMSAgendaDefinition(agendaName,
				contextDefinition);

		AgendaItemDefinition.Builder agendaItemBuilder1 = AgendaItemDefinition.Builder
				.create(null, agendaDef.getId());

		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSE).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111", PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);

		String ruleDefinitionID = createKRMSRuleDefinition(nameSpace,
				agendaName + "::Rule1", contextDefinition, LogicalOperator.OR,
				proposition).getId();

		agendaItemBuilder1.setRuleId(ruleDefinitionID);
		AgendaItemDefinition agendaItem1 = agendaBoService
				.createAgendaItem(agendaItemBuilder1.build());
		//
		AgendaDefinition.Builder agendaDefBuilder1 = AgendaDefinition.Builder
				.create(agendaDef);
		// agendaDefBuilder1.setAttributes(Collections.singletonMap("Event",
		// eventName));
		agendaDefBuilder1.setFirstItemId(agendaItem1.getId());
		agendaDef = agendaDefBuilder1.build();
		//
		agendaBoService.updateAgenda(agendaDef);

	}

	private void createAgenda2(String agendaName,
			ContextDefinition contextDefinition, String eventName,
			String nameSpace) {
		// Create Agenda...
		AgendaDefinition agendaDef = createKRMSAgendaDefinition(agendaName,
				contextDefinition);

		AgendaItemDefinition.Builder agendaItemBuilder1 = AgendaItemDefinition.Builder
				.create(null, agendaDef.getId());

		// Change specifically to the rule being created...
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSES).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111, MATH211",
				PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);

		String ruleDefinitionID = createKRMSRuleDefinition(nameSpace,
				agendaName + "::Rule1", contextDefinition, LogicalOperator.OR,
				proposition).getId();

		agendaItemBuilder1.setRuleId(ruleDefinitionID);

		//
		// AgendaItemDefinition.Builder agendaItemBuilder2 =
		// AgendaItemDefinition.Builder.create(null, agendaDef.getId());
		// agendaItemBuilder1.setAlways(agendaItemBuilder2);
		// agendaItemBuilder2.setRuleId(createRuleDefinition2(contextDefinition,
		// agendaName, nameSpace).getId());
		//
		// AgendaItemDefinition.Builder agendaItemBuilder3 =
		// AgendaItemDefinition.Builder.create(null, agendaDef.getId());
		// agendaItemBuilder2.setAlways(agendaItemBuilder3);
		// agendaItemBuilder3.setRuleId(createRuleDefinition3(contextDefinition,
		// agendaName, nameSpace).getId());
		//
		// AgendaItemDefinition.Builder agendaItemBuilder4 =
		// AgendaItemDefinition.Builder.create(null, agendaDef.getId());
		// agendaItemBuilder3.setAlways(agendaItemBuilder4);
		// agendaItemBuilder4.setRuleId(createRuleDefinition4(contextDefinition,
		// agendaName, nameSpace).getId());

		// String these puppies together. Kind of a PITA because you need the
		// id from the next item before you insert the previous one
		// AgendaItemDefinition agendaItem4 =
		// agendaBoService.createAgendaItem(agendaItemBuilder4.build());
		// agendaItemBuilder3.setAlwaysId(agendaItem4.getId());
		// AgendaItemDefinition agendaItem3 =
		// agendaBoService.createAgendaItem(agendaItemBuilder3.build());
		// agendaItemBuilder2.setAlwaysId(agendaItem3.getId());
		// AgendaItemDefinition agendaItem2 =
		// agendaBoService.createAgendaItem(agendaItemBuilder2.build());
		// agendaItemBuilder1.setAlwaysId(agendaItem2.getId());
		AgendaItemDefinition agendaItem1 = agendaBoService
				.createAgendaItem(agendaItemBuilder1.build());
		//
		AgendaDefinition.Builder agendaDefBuilder1 = AgendaDefinition.Builder
				.create(agendaDef);
		// agendaDefBuilder1.setAttributes(Collections.singletonMap("Event",
		// eventName));
		agendaDefBuilder1.setFirstItemId(agendaItem1.getId());
		agendaDef = agendaDefBuilder1.build();
		//
		agendaBoService.updateAgenda(agendaDef);

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
		PropositionParametersBuilder params1 = new PropositionParametersBuilder();
		Set<Entry<String, PropositionParameterType>> allValues = propositionsMap
				.entrySet();
		for (Entry<String, PropositionParameterType> entry : allValues) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			params1.add(entry.getKey(), entry.getValue());
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

			StringBuilder propositionNameBuilder = new StringBuilder("Proposition");

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

	// private TermDefinition createTermDefinition(String termName,
	// Class termValueType, ContextDefinition contextDefinition) {
	//
	// // this may be called more than once, we only want to create one though
	// Map<String, String> queryArgs = new HashMap<String, String>();
	// queryArgs.put("specification.namespace",
	// contextDefinition.getNamespace());
	// queryArgs.put("specification.name", termName);
	// TermBo termBo = getBoService()
	// .findByPrimaryKey(TermBo.class, queryArgs);
	// if (termBo != null) {
	// return TermBo.to(termBo);
	// }
	//
	// // campusCode TermSpec
	// TermSpecificationDefinition termSpec =
	// TermSpecificationDefinition.Builder
	// .create(null, termName, contextDefinition.getNamespace(),
	// termValueType.getCanonicalName()).build();
	//
	// termSpec = termBoService.createTermSpecification(termSpec);
	//
	// // Term 1
	// TermDefinition termDefinition = TermDefinition.Builder.create(null,
	// TermSpecificationDefinition.Builder.create(termSpec), null)
	// .build();
	// termDefinition = termBoService.createTermDefinition(termDefinition);
	//
	// return termDefinition;
	// }

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

	// private RuleDefinition createRuleDefinition2(ContextDefinition
	// contextDefinition, String agendaName, String nameSpace) {
	//
	// PropositionParametersBuilder params1 = new
	// PropositionParametersBuilder();
	// params1.add(createTermDefinition2(contextDefinition, nameSpace).getId(),
	// PropositionParameterType.TERM);
	// params1.add("RESULT1", PropositionParameterType.CONSTANT);
	// params1.add("=", PropositionParameterType.OPERATOR);
	//
	// PropositionParametersBuilder params2 = new
	// PropositionParametersBuilder();
	// params2.add(createTermDefinition2(contextDefinition, nameSpace).getId(),
	// PropositionParameterType.TERM);
	// params2.add("NotGonnaBeEqual", PropositionParameterType.CONSTANT);
	// params2.add("=", PropositionParameterType.OPERATOR);
	//
	// return createKRMSRuleDefinition(nameSpace, agendaName+"::Rule2",
	// contextDefinition, LogicalOperator.AND, params1, params2);
	// }

	// private RuleDefinition createRuleDefinition3(ContextDefinition
	// contextDefinition, String agendaName, String nameSpace) {
	//
	// FunctionDefinition gcdFunction =
	// functionBoService.getFunctionByNameAndNamespace("gcd",
	// contextDefinition.getNamespace());
	//
	// if (null == gcdFunction) {
	// // better configure a custom fuction for this
	// // KrmsType for custom function
	// KrmsTypeDefinition.Builder krmsFunctionTypeDefnBuilder =
	// KrmsTypeDefinition.Builder.create("KrmsTestFunctionType", nameSpace);
	// krmsFunctionTypeDefnBuilder.setServiceName("testFunctionTypeService");
	// KrmsTypeDefinition krmsFunctionTypeDefinition =
	// krmsTypeRepository.createKrmsType(krmsFunctionTypeDefnBuilder.build());
	//
	// FunctionDefinition.Builder functionBuilder =
	// FunctionDefinition.Builder.create(contextDefinition.getNamespace(),
	// "gcd", Integer.class.getName(), krmsFunctionTypeDefinition.getId());
	//
	// functionBuilder.getParameters().add(FunctionParameterDefinition.Builder.create("arg0",
	// Integer.class.getName(), 0));
	// functionBuilder.getParameters().add(FunctionParameterDefinition.Builder.create("arg1",
	// Integer.class.getName(), 1));
	// functionBuilder.setReturnType(Integer.class.getName());
	//
	// gcdFunction = functionBoService.createFunction(functionBuilder.build());
	// }
	//
	// PropositionParametersBuilder params = new PropositionParametersBuilder();
	//
	// // leverage our stack based evaluation in reverse polish notation
	// params.add("1024", PropositionParameterType.CONSTANT);
	// params.add("768", PropositionParameterType.CONSTANT);
	// params.add(gcdFunction.getId(), PropositionParameterType.FUNCTION); //
	// this should evaluate first: gcd(1024, 768)
	// params.add("256", PropositionParameterType.CONSTANT);
	// params.add("=", PropositionParameterType.OPERATOR); // this should
	// evaluate second: gcdResult == 256
	//
	// return createKRMSRuleDefinition(nameSpace, agendaName+"::Rule3",
	// contextDefinition, null, params);
	// }

	// private RuleDefinition createRuleDefinition4(ContextDefinition
	// contextDefinition, String agendaName, String nameSpace) {
	//
	// PropositionParametersBuilder params1 = new
	// PropositionParametersBuilder();
	// params1.add(createTermDefinition(BOOL1, Boolean.class,
	// contextDefinition).getId(), PropositionParameterType.TERM);
	// params1.add(createTermDefinition(BOOL2, Boolean.class,
	// contextDefinition).getId(), PropositionParameterType.TERM);
	// params1.add("=", PropositionParameterType.OPERATOR);
	//
	// PropositionParametersBuilder params2 = new
	// PropositionParametersBuilder();
	// params2.add(createTermDefinition(BOOL2, Boolean.class,
	// contextDefinition).getId(), PropositionParameterType.TERM);
	// params2.add(createTermDefinition(BOOL1, Boolean.class,
	// contextDefinition).getId(), PropositionParameterType.TERM);
	// params2.add("=", PropositionParameterType.OPERATOR);
	//
	// return createKRMSRuleDefinition(nameSpace, agendaName+"::Rule4",
	// contextDefinition, LogicalOperator.AND, params1, params2);
	// }

	// private TermDefinition createTermDefinition2(ContextDefinition
	// contextDefinition, String nameSpace) {
	//
	// Map<String, String> queryArgs = new HashMap<String, String>();
	// queryArgs.put("specification.namespace",
	// contextDefinition.getNamespace());
	// queryArgs.put("specification.name", "outputTermSpec");
	// TermBo result = getBoService().findByPrimaryKey(TermBo.class, queryArgs);
	// if (result != null) return TermBo.to(result);
	//
	// // output TermSpec
	// TermSpecificationDefinition outputTermSpec =
	// TermSpecificationDefinition.Builder.create(null, "outputTermSpec",
	// contextDefinition.getNamespace(),
	// "java.lang.String").build();
	// outputTermSpec = termBoService.createTermSpecification(outputTermSpec);
	//
	// // prereq TermSpec
	// TermSpecificationDefinition prereqTermSpec =
	// TermSpecificationDefinition.Builder.create(null, PREREQ_TERM_NAME,
	// contextDefinition.getNamespace(),
	// "java.lang.String").build();
	// prereqTermSpec = termBoService.createTermSpecification(prereqTermSpec);
	//
	// // Term Param
	// TermParameterDefinition.Builder termParamBuilder2 =
	// TermParameterDefinition.Builder.create(null, null, "testParamName",
	// "testParamValue");
	//
	// // Term
	// TermDefinition termDefinition2 =
	// TermDefinition.Builder.create(null,
	// TermSpecificationDefinition.Builder.create(outputTermSpec),
	// Collections.singletonList(termParamBuilder2)).build();
	// termDefinition2 = termBoService.createTermDefinition(termDefinition2);
	//
	// // KrmsType for TermResolver
	// KrmsTypeDefinition.Builder krmsTermResolverTypeDefnBuilder =
	// KrmsTypeDefinition.Builder.create("KrmsTestResolverType", nameSpace);
	// krmsTermResolverTypeDefnBuilder.setServiceName("testTermResolverTypeService");
	//
	// KrmsTypeDefinition krmsTermResolverTypeDefinition =
	// krmsTypeRepository.createKrmsType(krmsTermResolverTypeDefnBuilder.build());
	//
	// // TermResolver
	// TermResolverDefinition termResolverDef =
	// TermResolverDefinition.Builder.create(null,
	// contextDefinition.getNamespace(), "testResolver1",
	// krmsTermResolverTypeDefinition.getId(),
	// TermSpecificationDefinition.Builder.create(outputTermSpec),
	// Collections.singleton(TermSpecificationDefinition.Builder.create(prereqTermSpec)),
	// null,
	// Collections.singleton("testParamName")).build();
	// termResolverDef = termBoService.createTermResolver(termResolverDef);
	//
	// return termDefinition2;
	// }

	// methods used
	private KrmsTypeDefinition getKSKRMSType(String nameSpace) {
		KrmsTypeDefinition krmsContextTypeDefinition = krmsTypeRepository
				.getTypeByName(nameSpace, KSKRMSConstants.CONTEXT_TYPE_COURSE);

		if (krmsContextTypeDefinition == null) {

			KrmsTypeDefinition.Builder krmsContextTypeDefnBuilder = KrmsTypeDefinition.Builder
					.create(KSKRMSConstants.CONTEXT_TYPE_COURSE, nameSpace);
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