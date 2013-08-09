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

import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.*;
import org.kuali.rice.test.BaselineTestCase;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BaselineMode(Mode.NONE)
public class KSKRMSUploadTestCase extends BaselineTestCase {

    private static final String KRMS_MODULE_NAME = "krms";

	protected ContextBoService contextRepository;
	protected KrmsTypeRepositoryService krmsTypeRepository;
	protected AgendaBoService agendaBoService;
	protected RuleBoService ruleBoService;

	// Services needed for creation:
	protected TermBoService termBoService;
	protected SpringResourceLoader krmsTestResourceLoader;

    private static String KRMS_TEST_SPRING_BEANS = "classpath:KRMSUploadHarnessSpringBeans.xml";

	public KSKRMSUploadTestCase() {
        super(KRMS_MODULE_NAME);
		this.setClearTables(false);
	}

    /**
     * Overridden to set dirty=true each time
     * @see org.kuali.rice.test.RiceTestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        getLoadApplicationLifecycle();
        termBoService = KrmsRepositoryServiceLocator.getTermBoService();
        agendaBoService = KrmsRepositoryServiceLocator.getAgendaBoService();
        contextRepository = KrmsRepositoryServiceLocator.getContextBoService();
        ruleBoService = KrmsRepositoryServiceLocator.getRuleBoService();
        krmsTypeRepository = KrmsRepositoryServiceLocator
                .getKrmsTypeRepositoryService();
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
            List<String> files = new ArrayList<String>();
            files.add(KRMS_TEST_SPRING_BEANS);
            if (this.getAdditionalSpringFile() != null){
                files.add(getAdditionalSpringFile());
            }
            
			krmsTestResourceLoader = new SpringResourceLoader(new QName(
					"KRMSTestHarnessApplicationResourceLoader"), files, null);
			krmsTestResourceLoader
					.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
			getTestHarnessSpringResourceLoader().addResourceLoader(
					krmsTestResourceLoader);
		}
		return krmsTestResourceLoader;
	}
    
    protected String getAdditionalSpringFile(){
        return null;
    }

	protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
	}
	
	//@Test
	/*public void testHello() {
			System.out.println("Test hello");
			assertNotNull(termBoService);
			assertNotNull(agendaBoService);
			assertNotNull(contextRepository);
			assertNotNull(ruleBoService);
			assertNotNull(krmsTypeRepository);
	}*/
	
	protected AgendaDefinition getKRMSAgenda(String agendaName, ContextDefinition contextDef) {
		AgendaDefinition agendaDef = agendaBoService
				.getAgendaByNameAndContextId(agendaName,
						contextDef.getId());
		return agendaDef;
	}

	//@Test
	/*public void testKRMSContext() {
		ContextDefinition cnt = getKRMSContext(KSKRMSConstants.CONTEXT_STUD_ELIGIBILITY);
		assertNotNull(cnt);
		System.out.println(cnt.getDescription());
		AgendaDefinition adef = getKRMSAgenda(KSKRMSConstants.AGENDA1, cnt);
		assertNotNull(adef);
		System.out.println(adef.getName());
	}*/

	protected ContextDefinition getKRMSContext(String context) {
		return contextRepository.getContextByNameAndNamespace(
				context, KSKRMSConstants.KSNAMESPACE);
	}
	
	protected TermDefinition krmsTermLookup(String termName) {
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
	
	protected TermResolverDefinition krmsTermResolverLookup(String termResolverName) {
		// this may be called more than once, we only want to create one though
		Map<String, String> queryArgs = new HashMap<String, String>();
		queryArgs.put("nm", termResolverName);
		TermResolverBo termBo = getBoService()
				.findByPrimaryKey(TermResolverBo.class, queryArgs);
		if (termBo != null) {
			return TermResolverBo.to(termBo);
		}
		return null;
	}
	
}