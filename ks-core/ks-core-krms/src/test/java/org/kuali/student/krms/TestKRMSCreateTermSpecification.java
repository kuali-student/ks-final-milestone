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

	private static final String TERM_SPEC_ORG_NUMBER = "Org Number";
	private static final String TERM_SPEC_COURSE = "Course";
	private static final String TERM_SPEC_COURSE_NUMBER = "Course Number";
	private static final String TERM_SPEC_DATE = "Date";
	private static final String TERM_SPEC_GPA = "GPA";
	private static final String TERM_SPEC_GRADE = "Grade";
	private static final String TERM_SPEC_GRADE_TYPE = "GradeType";
	private static final String TERM_SPEC_LEARNING_OBJECTIVES = "Learning Objectives";
	
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
		termSpec = createKRMSTermSpecification(nameSpace, KSKRMSConstants.TERM_SPEC_CREDITS);
		 termSpec = createKRMSTermSpecification(nameSpace, TERM_SPEC_ORG_NUMBER);
		 termSpec = createKRMSTermSpecification(nameSpace, TERM_SPEC_COURSE);
//		 termSpec = createKRMSTermSpecification(nameSpace, ???);
//		 termSpec = createKRMSTermSpecification(nameSpace, ???);
		
	}

	private TermSpecificationDefinition createKRMSTermSpecification(String nameSpace, String termSpecName) {
		Map<String, String> queryArgs = new HashMap<String, String>();
		queryArgs.put("namespace", nameSpace);
		queryArgs.put("name", termSpecName);
		TermSpecificationBo termSpecBo = getBoService().findByPrimaryKey(
				TermSpecificationBo.class, queryArgs);
		//
		// TODO Figure out how to set the Description
		TermSpecificationDefinition termSpec = null;
		if (termSpecBo == null) {
			 termSpec = TermSpecificationDefinition.Builder
					.create(null, termSpecName, nameSpace,
							String.class.getCanonicalName()).build();

			 
			termSpec = termBoService.createTermSpecification(termSpec);
		} else {
			termSpec = termSpecBo.to(termSpecBo);
		}
		return termSpec;
	}
	
    protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
	}

}