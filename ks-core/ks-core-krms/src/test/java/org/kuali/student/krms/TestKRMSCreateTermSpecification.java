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

import javax.xml.namespace.QName;

import static org.junit.Assert.*;

@BaselineMode(Mode.NONE)
public class TestKRMSCreateTermSpecification extends KRMSTestCase {

	static final String KSNAMESPACE = "KR-RULE-TEST";
	protected ContextBoService contextRepository;
    protected KrmsTypeRepositoryService krmsTypeRepository;
    private AgendaBoService agendaBoService;


	// // Services needed for creation:
	private TermBoService termBoService;
	private SpringResourceLoader krmsTestResourceLoader;
// Agendas
	Map<String, ContextDefinition> phase1ContextDefs = new HashMap<String, ContextDefinition>();
	KrmsTypeDefinition krmsTypeDefinition;
	ContextDefinition contextStudElig;
	 static final String AGENDA1 = "Must have successfully completed <course>";
	 static final String EARTHQUAKE_EVENT = "Earthquake";
	@Before
	public void setup() {
		getLoadApplicationLifecycle();
		termBoService = KrmsRepositoryServiceLocator.getTermBoService();
		agendaBoService = KrmsRepositoryServiceLocator.getAgendaBoService();
		contextRepository = KrmsRepositoryServiceLocator.getContextBoService();
	}

	@Override
	protected void loadSuiteTestData() throws Exception {
		// Do nothing
	}

	@Override
	protected List<String> getPerTestTablesNotToClear() {
		List<String> tablesNotToClear = super.getPerTestTablesNotToClear();
        tablesNotToClear.add("KRMS_.*");
		return tablesNotToClear;
	}
	
	@Override
	protected Lifecycle getLoadApplicationLifecycle() {
	    if (krmsTestResourceLoader == null) {
	        krmsTestResourceLoader = new SpringResourceLoader(new QName("KRMSTestHarnessApplicationResourceLoader"), "classpath:KRMSTestHarnessSpringBeans.xml", null);
	        krmsTestResourceLoader.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
	        getTestHarnessSpringResourceLoader().addResourceLoader(krmsTestResourceLoader);
	    }
    	return krmsTestResourceLoader;
	}
	@Test
	public void createAllKRMSTermSpecificationsPhase1() {
		String nameSpace = KSNAMESPACE;
		// Create all the terms specifications...
		TermSpecificationDefinition termSpec = null;
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_CREDITS, KSKRMSConstants.CREDITS_DESCR,String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_ORG_NUMBER, KSKRMSConstants.ORG_NUMBER_DESCR,String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE, KSKRMSConstants.COURSE_DESCR,String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.COURSE_NUMBER_DESCR, "int");
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_DATE,KSKRMSConstants.DATE_DESCR, "Java.util.Date");
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_GPA, KSKRMSConstants.GPA_DESCR, String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_GRADE, KSKRMSConstants.GRADE_DESCR, String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_GRADE_TYPE, KSKRMSConstants.GRADE_TYPE_DESCR, String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_LEARNING_OBJECTIVES,KSKRMSConstants.LEARNING_OBJECTIVES_DESCR, String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_SUBJECT_CODE,KSKRMSConstants.SUBJECT_CODE_DESCR, String.class.getCanonicalName());
		 termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_TEXT,KSKRMSConstants.TEXT_DESCR, String.class.getCanonicalName());
			
	}

	private TermSpecificationDefinition createKRMSTermSpecification(String nameSpace, String termSpecName, String descr, String termType) {
		Map<String, String> queryArgs = new HashMap<String, String>();
		queryArgs.put("namespace", nameSpace);
		queryArgs.put("name", termSpecName);
		TermSpecificationBo termSpecBo = getBoService().findByPrimaryKey(
				TermSpecificationBo.class, queryArgs);
		
		// TODO Figure out how to set the Description
		TermSpecificationDefinition termSpec = null;
		if (termSpecBo == null) {
			
			Builder termSpecDefBuilder = TermSpecificationDefinition.Builder
					.create(null, termSpecName, nameSpace, termType);
			termSpecDefBuilder.setDescription(descr);
			
			termSpec = termSpecDefBuilder.build();

			termSpec = termBoService.createTermSpecification(termSpec);

		} else {
			termSpec = termSpecBo.to(termSpecBo);
		}
		System.out.println("Elmien :     " + termSpec.getDescription() + "     " + termSpec.getName());
		return termSpec;
	}
	
	@Test
	public void createAllKRMSTermDefinitions(){
		String nameSpace = KSNAMESPACE;
		// Create all the terms...
		//createNumberOfCreditsTermDefinition(nameSpace);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE, KSKRMSConstants.TERM_APPROVED_COURSE);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE, KSKRMSConstants.TERM_APPROVED_COURSES);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_COURSE_NUMBER_RANGE);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_SUBJECT_CODE, KSKRMSConstants.TERM_SUBJECT_CODE);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_TEXT, KSKRMSConstants.TERM_COURSE_SET);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_DATE, KSKRMSConstants.TERM_DATE_EFFECTIVE_FROM);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_DATE, KSKRMSConstants.TERM_DATE_EFFECTIVE_TO);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_GPA, KSKRMSConstants.TERM_GPA);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_GRADE, KSKRMSConstants.TERM_GRADE);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_GRADE_TYPE, KSKRMSConstants.TERM_GRADE_TYPE);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_LEARNING_OBJECTIVES, KSKRMSConstants.TERM_LEARNING_OBJ_DESCRIPTION);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_NUMBER_OF_COURSES);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_NUMBER_OF_CREDITS);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_CREDITS, KSKRMSConstants.TERM_PROPOSED_COURSE);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_PROPOSED_COURSES);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_CREDITS, KSKRMSConstants.TERM_SCORE);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE, KSKRMSConstants.TERM_TEST);
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_TEXT, KSKRMSConstants.TERM_FREE_TEXT);
		//createProposedCourseTermDefinition(nameSpace);
		//createApprovedCourseTermDefinition(nameSpace);
	}

	private void createKRMSTermDefinition(String nameSpace, String termSpecName, String termName ) {

		Map<String, String> queryArgs = new HashMap<String, String>();
		queryArgs.put("namespace", nameSpace);
		queryArgs.put("name", termSpecName);
		String a = queryArgs.get("name");
		TermSpecificationBo termSpecBo = getBoService().findByPrimaryKey(
				TermSpecificationBo.class, queryArgs);
		//
		TermSpecificationDefinition termSpec = null;
		
		termSpec = termSpecBo.to(termSpecBo);
		
		Builder termSpecDefBuilder = TermSpecificationDefinition.Builder
				.create(termSpec);			
		TermDefinition.Builder termDefBuilder = TermDefinition.Builder.create(
				null, termSpecDefBuilder, null);
		termDefBuilder.setDescription(termName);
		
		TermDefinition termDefinition = termDefBuilder.build();
		
		termDefinition = termBoService.createTermDefinition(termDefinition);

	}
	
	
	@Test
	public void createAllContexts() {
		String nameSpace = KSNAMESPACE;
		// Create all the contexts...
		krmsTypeDefinition = createContextType(nameSpace);
		createContext(nameSpace, KSKRMSConstants.CONTEXT_ANTI_REQUISITE, krmsTypeDefinition);
		createContext(nameSpace, KSKRMSConstants.CONTEXT_CORE_REQUISITE, krmsTypeDefinition);
		createContext(nameSpace, KSKRMSConstants.CONTEXT_COURSE_RESTRICTS, krmsTypeDefinition);
		createContext(nameSpace, KSKRMSConstants.CONTEXT_RECOMMENDED_PREPARATION, krmsTypeDefinition);
		createContext(nameSpace, KSKRMSConstants.CONTEXT_REPEATED_CREDITS, krmsTypeDefinition);
		contextStudElig = createContext(nameSpace, KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY, krmsTypeDefinition);
		
		// Creating all the agendas
		createAgendaDefinition(AGENDA1, phase1ContextDefs.get(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY), EARTHQUAKE_EVENT, KSNAMESPACE);
		
	}
	
	protected KrmsTypeDefinition createContextType(String nameSpace) {
        KrmsTypeDefinition.Builder krmsContextTypeDefnBuilder = KrmsTypeDefinition.Builder.create(KSKRMSConstants.CONTEXT_TYPE_COURSE, nameSpace);   
        krmsContextTypeDefnBuilder.setServiceName("myKSService");
        
        int contextAttrSequenceIndex = 0;
        List<KrmsTypeAttribute.Builder> contextAttributeBuilders = new ArrayList<KrmsTypeAttribute.Builder>();
//        for (KrmsAttributeDefinition attrDef : ksLumAttributeDefinitions.values()) {
//            contextAttributeBuilders.add(KrmsTypeAttribute.Builder.create(null, attrDef.getId(),
//                    contextAttrSequenceIndex));
//            contextAttrSequenceIndex += 1;
//			
//		}
        krmsContextTypeDefnBuilder.setAttributes(contextAttributeBuilders);
		krmsTypeRepository = KrmsRepositoryServiceLocator
				.getKrmsTypeRepositoryService();
        KrmsTypeDefinition krmsContextTypeDefinition = krmsContextTypeDefnBuilder.build();
        krmsContextTypeDefinition = krmsTypeRepository.createKrmsType(krmsContextTypeDefinition);
		return krmsContextTypeDefinition;
	}
	
	public ContextDefinition createContext(String nameSpace, String name,
			KrmsTypeDefinition krmsContextTypeDefinition) {
        ContextDefinition.Builder contextBuilder = ContextDefinition.Builder.create(nameSpace, name);
        contextBuilder.setTypeId(krmsContextTypeDefinition.getId());
        ContextDefinition contextDefinition = contextBuilder.build();
		
        contextDefinition = contextRepository.createContext(contextDefinition);
        phase1ContextDefs.put(name, contextDefinition);
		return contextDefinition;
	}
	
    protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
	}
    

    // @Test
    public void createAgendasPhase1() {
        // Create multiple agendas so that we can test selection
        createAgendaDefinition(AGENDA1, contextStudElig, EARTHQUAKE_EVENT, KSNAMESPACE);
	}
    
    private void createAgendaDefinition(String agendaName, ContextDefinition contextDefinition, String eventName, String nameSpace ) {
        AgendaDefinition agendaDef =
        AgendaDefinition.Builder.create(null, agendaName, krmsTypeDefinition.getId(), contextDefinition.getId()).build();
        agendaDef = agendaBoService.createAgenda(agendaDef);

//        AgendaItemDefinition.Builder agendaItemBuilder1 = AgendaItemDefinition.Builder.create(null, agendaDef.getId());
//        agendaItemBuilder1.setRuleId(createRuleDefinition1(contextDefinition, agendaName, nameSpace).getId());
//
//        AgendaItemDefinition.Builder agendaItemBuilder2 = AgendaItemDefinition.Builder.create(null, agendaDef.getId());
//        agendaItemBuilder1.setAlways(agendaItemBuilder2);
//        agendaItemBuilder2.setRuleId(createRuleDefinition2(contextDefinition, agendaName, nameSpace).getId());
//
//        AgendaItemDefinition.Builder agendaItemBuilder3 = AgendaItemDefinition.Builder.create(null, agendaDef.getId());
//        agendaItemBuilder2.setAlways(agendaItemBuilder3);
//        agendaItemBuilder3.setRuleId(createRuleDefinition3(contextDefinition, agendaName, nameSpace).getId());
//
//        AgendaItemDefinition.Builder agendaItemBuilder4 = AgendaItemDefinition.Builder.create(null, agendaDef.getId());
//        agendaItemBuilder3.setAlways(agendaItemBuilder4);
//        agendaItemBuilder4.setRuleId(createRuleDefinition4(contextDefinition, agendaName, nameSpace).getId());
//
//        // String these puppies together.  Kind of a PITA because you need the id from the next item before you insert the previous one
//        AgendaItemDefinition agendaItem4 = agendaBoService.createAgendaItem(agendaItemBuilder4.build());
//        agendaItemBuilder3.setAlwaysId(agendaItem4.getId());
//        AgendaItemDefinition agendaItem3 = agendaBoService.createAgendaItem(agendaItemBuilder3.build());
//        agendaItemBuilder2.setAlwaysId(agendaItem3.getId());
//        AgendaItemDefinition agendaItem2 = agendaBoService.createAgendaItem(agendaItemBuilder2.build());
//        agendaItemBuilder1.setAlwaysId(agendaItem2.getId());
//        AgendaItemDefinition agendaItem1 = agendaBoService.createAgendaItem(agendaItemBuilder1.build());
//
//        AgendaDefinition.Builder agendaDefBuilder1 = AgendaDefinition.Builder.create(agendaDef);
//        agendaDefBuilder1.setAttributes(Collections.singletonMap("Event", eventName));
//        agendaDefBuilder1.setFirstItemId(agendaItem1.getId());
//        agendaDef = agendaDefBuilder1.build();
//
//        agendaBoService.updateAgenda(agendaDef);
    }

}