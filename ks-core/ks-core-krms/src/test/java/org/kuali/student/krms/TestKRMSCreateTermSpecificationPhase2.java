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
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition.Builder;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.*;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class TestKRMSCreateTermSpecificationPhase2 extends KSKRMSUploadTestCase {

	public TestKRMSCreateTermSpecificationPhase2() {
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
		functionBoService = KrmsRepositoryServiceLocator.getBean("functionRepositoryService");

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
	public void createAllKRMSTermSpecificationsPhase1() {
		String nameSpace = KSNAMESPACE;
//		// Create all the terms specifications...
//		TermSpecificationDefinition termSpec = null;
//		termSpec = createKRMSTermSpecification(nameSpace,
//				KSKRMSConstants.TERM_SPEC_ORG_NUMBER,
//				KSKRMSConstants.ORG_NUMBER_DESCR, String.class.getCanonicalName());
//		// NOTE the term resolver must be defined as a spring bean under the name given here.
//		createKRMSTermResolver(KSKRMSConstants.TERM_SPEC_RESOLVER_ORG_NUMBER, termSpec);
		
		
	}

	private TermSpecificationDefinition createKRMSTermSpecification(
			String nameSpace, String termSpecName, String descr, String termType) {
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
		return termSpec;
	}

	@Test
	public void createAllKRMSTermDefinitions() {
		String nameSpace = KSNAMESPACE;
		// Create all the terms...
		// createNumberOfCreditsTermDefinition(nameSpace);
		//
		List<TermParameterDefinition.Builder> termParameters = new ArrayList<TermParameterDefinition.Builder>();
		
        setupTermParameters(termParameters, KSKRMSConstants.ORG_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_ORG_NUMBER,
				KSKRMSConstants.TERM_DEPT_NUMBER, null);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.ORG_KEY_TYPE_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_ORG_NUMBER,
				KSKRMSConstants.TERM_ADMIN_ORG_NUMBER, null);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_COMPLETED_COURSE_NUMBER, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_COMPLETED_COURSE_CODE, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_COMPLETED_COURSE_SET, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_COMPLETED_EFFECTIVE_DATE_FROM, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_COMPLETED_EFFECTIVE_DATE_TO, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_COMPLETED_LEARNING_OBJ_DESCR, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_COURSE_NUMBER_RANGE, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_COURSE_NUMBER_SUBJECT_CODE, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");		
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_SET, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_COURSE, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_COURSES, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");		
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_EFFECTIVE_DATE_FROM, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");		
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_EFFECTIVE_DATE_TO, termParameters);
		
		termParameters.clear();
        setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        setupTermParameters(termParameters, KSKRMSConstants.TERM_ID_TERM_PROPERTY, "???termParameterValue");		
		createKRMSTermDefinition(nameSpace, KSKRMSConstants.TERM_SPEC_COURSE,
				KSKRMSConstants.TERM_ENROLLED_LEARNING_OBJ_DESCR, termParameters);
		
		
        // Term Parameter Example
		//List<TermParameterDefinition.Builder> termParameters = new ArrayList<TermParameterDefinition.Builder>();
        //setupTermParameters(termParameters, KSKRMSConstants.PERSON_ID_TERM_PROPERTY, "???termParameterValue");
        //setupTermParameters(termParameters, KSKRMSConstants.COURSE_CODE_TERM_PROPERTY, "???termParameterValue");
        //
	}

	private void setupTermParameters(
			List<TermParameterDefinition.Builder> termParameters, String termParamName, String termParamValue) {
		TermParameterDefinition.Builder termParamBuilder2 =
            TermParameterDefinition.Builder.create(null, null, termParamName, termParamValue);
        termParameters.add(termParamBuilder2);
	}

	private void createKRMSTermDefinition(String nameSpace,
			String termSpecName, String termName, List<TermParameterDefinition.Builder> termParameters) {

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
//        // Term Param
//		List<TermParameterDefinition.Builder> termParameters = new ArrayList<TermParameterDefinition.Builder>();
//        TermParameterDefinition.Builder termParamBuilder2 =
//            TermParameterDefinition.Builder.create(null, null, "testParamName", "testParamValue");
//        termParameters.add(termParamBuilder2);

		TermDefinition.Builder termDefBuilder = TermDefinition.Builder.create(
				null, termSpecDefBuilder, termParameters);
		
		termDefBuilder.setDescription(termName);

		TermDefinition termDefinition = termDefBuilder.build();

		termDefinition = termBoService.createTerm(termDefinition);

	}

	private KrmsTypeDefinition getKSKRMSType(String nameSpace, String typeName, String typeServiceName) {
		KrmsTypeDefinition krmsAgendaType = krmsTypeRepository
				.getTypeByName(nameSpace, typeName);

		if (krmsAgendaType == null) {

			KrmsTypeDefinition.Builder krmsAgendaTypeDefinition = KrmsTypeDefinition.Builder
					.create(typeName, nameSpace);
			krmsAgendaTypeDefinition.setServiceName(typeServiceName);

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
			
			krmsAgendaType = krmsTypeRepository
					.createKrmsType(krmsAgendaTypeDefinition.build());
			return krmsAgendaType;
		}
		
		return krmsAgendaType;
	}
	
	// @Test
	public void TestCreateType() {
		getKSKRMSType(KSNAMESPACE, KSKRMSConstants.KS_AGENDA_TYPE, "testAgendaTypeService");
	}

	protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
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
	
	// @Test
	public void testTermLookup() {
		
		TermDefinition term = krmsTermLookup(KSKRMSConstants.TERM_APPROVED_COURSE);
		assertNotNull(term);
	}
	
	public void createKRMSTermResolver(String termResolverName, TermSpecificationDefinition termSpecDefinition) {
		// KrmsType for TermResolver
		KrmsTypeDefinition krmsTermResolverTypeDefinition = getKSKRMSType(KSKRMSConstants.KSNAMESPACE, KSKRMSConstants.KS_TERM_RESOLVER_TYPE, "ksKRMSTermResolverTypeService");

        // TermResolver
		TermResolverDefinition termResolverDef =
			TermResolverDefinition.Builder.create(null, KSKRMSConstants.KSNAMESPACE, termResolverName, krmsTermResolverTypeDefinition.getId(),
					TermSpecificationDefinition.Builder.create(termSpecDefinition),
					null,
					null,
					null).build();
		termResolverDef = termBoService.createTermResolver(termResolverDef);
	}
	
}