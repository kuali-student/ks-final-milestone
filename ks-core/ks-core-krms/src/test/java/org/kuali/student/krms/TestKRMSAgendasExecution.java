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
import org.apache.commons.lang.StringUtils;
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
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.type.ValidationActionTypeService;
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

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

@BaselineMode(Mode.NONE)
public class TestKRMSAgendasExecution extends KRMSTestCase {

	public TestKRMSAgendasExecution() {
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

	protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
	}
	
	@Test
	public void testHello() {
			System.out.println("Test hello");
	}
	
	@Test
	public void testAllRulesKSAgenda() {

//		Rule rule1 = new BasicRule("r1", trueProp, Collections.<Action>singletonList(new ActionMock("a1")));
//		Rule rule2 = new BasicRule("r2", falseProp, Collections.<Action>singletonList(new ActionMock("a2")));
//		Rule rule3 = new BasicRule("r3", trueProp, Collections.<Action>singletonList(new ActionMock("a3")));
		
//		AgendaTreeEntry entry1 = new BasicAgendaTreeEntry(rule1);
//		AgendaTreeEntry entry2 = new BasicAgendaTreeEntry(rule2);
//		AgendaTreeEntry entry3 = new BasicAgendaTreeEntry(rule3);
//		BasicAgendaTree agendaTree = new BasicAgendaTree(entry1, entry2, entry3);
		
		ContextDefinition contextDef = getKRMSContext(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY);
		AgendaDefinition adgendaDef = getKRMSAgenda(KSKRMSConstants.AGENDA1, contextDef);
		assertNotNull(contextDef);
		assertNotNull(adgendaDef);
//		Agenda agenda = new BasicAgenda(Collections.singletonMap(AgendaDefinition.Constants.EVENT, "test"), agendaTree);
		
//		execute(agenda);

//		assertTrue(ActionMock.actionFired("a1"));
//		assertFalse(ActionMock.actionFired("a2"));
//		assertTrue(ActionMock.actionFired("a3"));
	}
	
	private AgendaDefinition getKRMSAgenda(String agendaName, ContextDefinition contextDef) {
		AgendaDefinition agendaDef = agendaBoService
				.getAgendaByNameAndContextId(agendaName,
						contextDef.getId());
		return agendaDef;
	}

	private ContextDefinition getKRMSContext(String context) {
		return contextRepository.getContextByNameAndNamespace(
				context, KSKRMSConstants.KSNAMESPACE);
	}
	
	//
    public void populateContextQualifiers(Map<String, String> qualifiers) {
        qualifiers.put("namespaceCode", KSKRMSConstants.KSNAMESPACE);
        qualifiers.put("name", KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY);
    }
    
    public void populateAgendaQualifiers(Map<String,String> agendaQualifiers) {
    	agendaQualifiers.put("name", KSKRMSConstants.AGENDA1);  // specify a single agenda by name
	}
    
    public void addFacts(Facts.Builder factsBuilder) {
//        AwardFactBuilderService fbService = KraServiceLocator.getService(AwardFactBuilderService.class);
//        fbService.addFacts(factsBuilder, this);
    }
	//
    
    @Test
	public void loadAndisRuleValid() {
//		KrmsRulesContext rulesContext;
        // TODO : this cached map should only be set up if it is empty
        Map <String, Boolean> ruleResults = new HashMap<String, Boolean>();
        
        // TODO : may need a ruleid -> name map
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        populateContextQualifiers(contextQualifiers);

        Map<String,String> agendaQualifiers = new HashMap<String,String>();
        // TODO : "name" qualifier should be working after 2.1.  currently, there is a bug.
        // TODO : may create a kcrulecontext which will extend krmsrulecontext.   the kcrulecontexgt can have 'getnamespace'
        String namespace = null;
        String contextKey = null;
//        if (rulesContext instanceof ProposalDevelopmentDocument) {
//            namespace = Constants.MODULE_NAMESPACE_PROPOSAL_DEVELOPMENT;
//            contextKey = ((ProposalDevelopmentDocument)rulesContext).getDevelopmentProposal().getProposalNumber()+"-0";
//        } else {
//            namespace = Constants.MODULE_NAMESPACE_PROTOCOL;
//            contextKey = ((ProtocolDocument)rulesContext).getProtocol().getProtocolNumber()+"-"+((ProtocolDocument)rulesContext).getProtocol().getSequenceNumber();
//        }
//        agendaQualifiers.put("name", getAgendaName(Constants.MODULE_NAMESPACE_PROPOSAL_DEVELOPMENT));  // specify a single agenda by name
//        populateAgendaQualifiers(agendaQualifiers);
       
        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers, agendaQualifiers);

        Facts.Builder factsBuilder = Facts.Builder.create();
        // not sure about this addfacts.  there are many  
        addFacts(factsBuilder);

        ExecutionOptions xOptions = new ExecutionOptions();
        xOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);

        EngineResults results = KrmsApiServiceLocator.getEngine().execute(selectionCriteria, factsBuilder.build(), xOptions);
        
        String errors = (String) results.getAttribute(ValidationActionTypeService.VALIDATIONS_ACTION_ATTRIBUTE);
        boolean isValid = false;
        if (results.getResultsOfType(ResultEvent.RULE_EVALUATED) != null && results.getResultsOfType(ResultEvent.RULE_EVALUATED).size() > 0) {
//            String ruleName = KrmsApiServiceLocator.getRuleRepositoryService().getRule(ruleId).getName();  
            for (ResultEvent resultEvent : results.getResultsOfType(ResultEvent.RULE_EVALUATED)) {
                ruleResults.put(((BasicRule)resultEvent.getSource()).getName(), resultEvent.getResult());
                System.out.println(resultEvent.getDescription() + ' ' + resultEvent.getResult());
//                if (StringUtils.equals(ruleName, ((BasicRule)resultEvent.getSource()).getName())) {
//                    isValid = resultEvent.getResult();
//                }
            }
//            ResultEvent resultEvent = results.getResultsOfType(ResultEvent.RULE_EVALUATED).get(0);
//            // can't access 'proposition of basicrule
//            // ((BasicRule)resultEvent.getSource()).
//            isValid = results.getResultsOfType(ResultEvent.RULE_EVALUATED).get(0).getResult();
        }
//        if (StringUtils.isBlank(errors)) {
            
//            String[] errorArray = StringUtils.split(errors, ",");
//            return Arrays.asList(errorArray);
//        }
//        return StringUtils.isNotBlank(errors);
        // use session to cache the evaluation results for now
//        GlobalVariables.getUserSession().addObject(namespace + "-" + contextKey + "-ruleresults", ruleResults);
//        GlobalVariables.getUserSession().addObject(namespace + "-" + contextKey + "-rulereferenced", new HashMap<String, Boolean>());
//        return isValid;
    }
}