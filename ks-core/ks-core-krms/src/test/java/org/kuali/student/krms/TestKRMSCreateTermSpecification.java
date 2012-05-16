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
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.impl.repository.ActionBoService;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.FunctionBoServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBo;
import org.kuali.rice.krms.impl.repository.TermBoService;
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

@BaselineMode(Mode.CLEAR_DB)
public class TestKRMSCreateTermSpecification extends KRMSTestCase {

	static final String KSNAMESPACE = "KR-RULE-TEST";


	// // Services needed for creation:
	private TermBoService termBoService;
	private SpringResourceLoader krmsTestResourceLoader;

	@Before
	public void setup() {
		getLoadApplicationLifecycle();
		termBoService = KrmsRepositoryServiceLocator.getTermBoService();
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
	public void createAllCMTermSpecificationsPhase1() {
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
	public void createAllLumTermDefinitions() {
		String nameSpace = KSNAMESPACE;
		// Create all the terms...
		//createNumberOfCreditsTermDefinition(nameSpace);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE, KSKRMSConstants.TERM_APPROVED_COURSE);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE, KSKRMSConstants.TERM_APPROVED_COURSES);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_COURSE_NUMBER_RANGE);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_SUBJECT_CODE, KSKRMSConstants.TERM_SUBJECT_CODE);
		//createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants., KSKRMSConstants.TERM_COURSE_SET);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_DATE, KSKRMSConstants.TERM_DATE_EFFECTIVE_FROM);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_DATE, KSKRMSConstants.TERM_DATE_EFFECTIVE_TO);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_GPA, KSKRMSConstants.TERM_GPA);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_GRADE, KSKRMSConstants.TERM_GRADE);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_GRADE_TYPE, KSKRMSConstants.TERM_GRADE_TYPE);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_LEARNING_OBJECTIVES, KSKRMSConstants.TERM_LEARNING_OBJ_DESCRIPTION);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_NUMBER_OF_COURSES);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_NUMBER_OF_CREDITS);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_CREDITS, KSKRMSConstants.TERM_PROPOSED_COURSE);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE_NUMBER, KSKRMSConstants.TERM_PROPOSED_COURSES);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_CREDITS, KSKRMSConstants.TERM_SCORE);
		createDeptOrgNumberTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE, KSKRMSConstants.TERM_TEST);
		//createProposedCourseTermDefinition(nameSpace);
		//createApprovedCourseTermDefinition(nameSpace);
	}

	private void createDeptOrgNumberTermDefinition(String nameSpace, String termSpecName, String termName ) {

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
	
	
	
    protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
	}

}