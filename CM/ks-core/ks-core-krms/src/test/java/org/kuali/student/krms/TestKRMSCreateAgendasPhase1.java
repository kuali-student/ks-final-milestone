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

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.*;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;

import javax.xml.namespace.QName;
import java.util.*;
import java.util.Map.Entry;

public class TestKRMSCreateAgendasPhase1 extends KSKRMSUploadTestCase {

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

	// @Test
	public void createRuleDefinitions() {

		// RuleDefinition ruleDefinition =
		// createRuleDefinition1(contextRepository.getContextByNameAndNamespace(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY,
		// KSNAMESPACE),
		// AGENDA1, KSNAMESPACE, KSKRMSConstants.TERM_COMPLETED_COURSE);
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_COMPLETED_COURSE).getId(),
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
		// AGENDA1, KSNAMESPACE, KSKRMSConstants.TERM_COMPLETED_COURSE);
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();
		propositionsMap.put(
				krmsTermLookup(KSKRMSConstants.TERM_COMPLETED_COURSE).getId(),
				PropositionParameterType.TERM);
		propositionsMap.put("MATH111", PropositionParameterType.CONSTANT);
		propositionsMap.put("=", PropositionParameterType.OPERATOR);
		PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);
	}

	@Test
	public void createAllAgendasAndRulesPhase1() {
		Map<String, PropositionParameterType> propositionsMap = new HashMap<String, PropositionParameterType>();

		// Creating agenda 1 based on CCJS357
		PropositionParametersBuilder proposition1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSE, "CCJS100", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA1,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition1);

		// Creating agenda 2 based on CCJS300
		PropositionParametersBuilder proposition2_1 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "CCJS100", "=");
		PropositionParametersBuilder proposition2_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "CCJS105", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA2,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition2_1, proposition2_2);

		// Creating agenda 3 based on CCJS300
		PropositionParametersBuilder proposition3_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_COURSES, "1", ">");
		PropositionParametersBuilder proposition3_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "EMGT230", "=");
		PropositionParametersBuilder proposition3_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "CCJS200", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA3,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition3_1, proposition3_2,
				proposition3_3);

		// Creating agenda 4 based on Made Up Data
		PropositionParametersBuilder proposition4_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_CREDITS, "13", ">");
		PropositionParametersBuilder proposition4_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "EMGT230", "=");
		PropositionParametersBuilder proposition4_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "CCJS200", "=");
		PropositionParametersBuilder proposition4_4 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "ECON321", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA4,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition4_1, proposition4_2,
				proposition4_3, proposition4_4);

		// Creating agenda 5 based on Made Up Data
		PropositionParametersBuilder proposition5_1 = createProposition(
				KSKRMSConstants.TERM_GPA, "9", ">");
		PropositionParametersBuilder proposition5_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "EMGT230", "=");
		PropositionParametersBuilder proposition5_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "CCJS200", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA5,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition5_1, proposition5_2,
				proposition5_3);

		// Creating agenda 6 based on Made Up Data
		PropositionParametersBuilder proposition6_1 = createProposition(
				KSKRMSConstants.TERM_GRADE_TYPE, "Letter", "=");
		PropositionParametersBuilder proposition6_2 = createProposition(
				KSKRMSConstants.TERM_GRADE, "C", ">");
		PropositionParametersBuilder proposition6_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "EMGT230", "=");
		PropositionParametersBuilder proposition6_4 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "CCJS200", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA6,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition6_1, proposition6_2,
				proposition6_3, proposition6_4);

		// Creating agenda 7 based on Made Up Data
		PropositionParametersBuilder proposition7_1 = createProposition(
				KSKRMSConstants.TERM_NUMBER_OF_COURSES, "1", ">");
		PropositionParametersBuilder proposition7_2 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "PSYC200", "=");
		PropositionParametersBuilder proposition7_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "SOCY201", "=");
		PropositionParametersBuilder proposition7_4 = createProposition(
				KSKRMSConstants.TERM_GRADE_TYPE, "Letter", "=");
		PropositionParametersBuilder proposition7_5 = createProposition(
				KSKRMSConstants.TERM_GRADE, "C", ">");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA7,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition7_1, proposition7_2,
				proposition7_3, proposition7_4, proposition7_5);

		// Creating agenda 8 based on Made Up Data
		PropositionParametersBuilder proposition8_1 = createProposition(
				KSKRMSConstants.TERM_GRADE_TYPE, "Letter", "=");
		PropositionParametersBuilder proposition8_2 = createProposition(
				KSKRMSConstants.TERM_GRADE, "C", ">");
		PropositionParametersBuilder proposition8_3 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "SOCY201", "=");
		PropositionParametersBuilder proposition8_4 = createProposition(
				KSKRMSConstants.TERM_COMPLETED_COURSES, "PSYC200", "=");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA8,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition8_1, proposition8_2,
				proposition8_3, proposition8_4);

		// Creating agenda 9 based on Made Up Data
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA9,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE);

		// Creating agenda 10 based on Made Up Data
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA10,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE);

		// Creating agenda 5 based on Made Up Data
		PropositionParametersBuilder proposition11 = createProposition(
				KSKRMSConstants.TERM_GPA, "9", ">");
		createAgendaAndRuleAndPropositions(KSKRMSConstants.AGENDA11,
				contextRepository.getContextByNameAndNamespace(
						KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, KSNAMESPACE),
				null, KSNAMESPACE, proposition11);

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

	public TermDefinition krmsTermLookup(String termName) {
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