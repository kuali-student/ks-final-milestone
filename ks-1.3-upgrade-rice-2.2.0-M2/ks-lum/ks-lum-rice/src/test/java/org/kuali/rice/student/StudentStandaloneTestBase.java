/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.rice.student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.kew.api.WorkflowRuntimeException;
import org.kuali.rice.kew.batch.KEWXmlDataLoader;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.test.BaselineTestCase;
import org.kuali.rice.test.SQLDataLoader;

/**
 * @author delyea
 *
 */
public class StudentStandaloneTestBase extends BaselineTestCase {

    private static final String MODULE_NAME = "standalone";

    public StudentStandaloneTestBase() {
        super(MODULE_NAME);
    }

    /* (non-Javadoc)
     * @see org.kuali.rice.test.RiceTestCase#getLoadApplicationLifecycle()
     */
    @Override
    protected Lifecycle getLoadApplicationLifecycle() {
        SpringResourceLoader springResourceLoader = new SpringResourceLoader(new QName("StudentStandaloneTestResourceLoader"), "classpath:StandaloneTestSpringBeans.xml", null);
        springResourceLoader.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
        return springResourceLoader;
    }

	/**
	 * Override the standard per-test lifecycles to prepend ClearDatabaseLifecycle and ClearCacheLifecycle
	 * @see org.kuali.rice.test.RiceTestCase#getPerTestLifecycles()
	 */
	@Override
	protected List<Lifecycle> getPerTestLifecycles() {
		List<Lifecycle> lifecycles = super.getPerTestLifecycles();
//		lifecycles.add(new KEWXmlDataLoaderLifecycle("file:" + getBaseDir() + "/../src/main/config/workflowXml/searchAttributes.xml"));
//		lifecycles.add(new KEWXmlDataLoaderLifecycle("file:" + getBaseDir() + "/../src/main/config/workflowXml/documentType.xml"));
		lifecycles.add(new ClearCacheLifecycle());
		return lifecycles;
	}
	
	public class ClearCacheLifecycle extends BaseLifecycle {
		public void stop() throws Exception {
                    // TODO: RICE-R2.0 UPGRADE - caching currently removed from rice.  Will likely be added in
//			KimApiServiceLocator.getIdentityService().flushAllCaches();
                    // TODO: RICE-R2.0 UPGRADE - not sure flushInternalRoleCache is really the replacmenet for flushRoleCache - see previous TODO
//                        KimApiServiceLocator.getRoleService().flushInternalRoleCache();
//			KimApiServiceLocator.getRoleService().flushRoleCaches();
			super.stop();
		}
	}
	
	// below method to be removed when moving to Rice 1.0.1
	@Override
	protected void loadSuiteTestData() throws Exception {
		new SQLDataLoader(getKNSDefaultSuiteTestData(), "/").runSql();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(getKIMDataLoadOrderFile()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!StringUtils.isBlank(line)) {
					new SQLDataLoader(getKIMSqlFileBaseLocation() + "/" + line, "/").runSql();
				}
			}
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		
		new SQLDataLoader("file:" + getBaseDir() + "/src/main/config/sql/kim.sql", ";").runSql();
	}
	
    protected String getKNSDefaultSuiteTestData() {
        return "file:" + getBaseDir() + "/src/test/config/data/DefaultSuiteTestDataKNS.sql";
    }

    protected String getKIMDataLoadOrderFile() {
        return getBaseDir() + "/src/test/config/data/KIMDataLoadOrder.txt";
    }

    protected String getKIMSqlFileBaseLocation() {
        return "file:" + getBaseDir() + "/src/test/config/data";
    }

	protected void loadXmlFile(String fileName) {
		try {
			KEWXmlDataLoader.loadXmlClassLoaderResource(getClass(), fileName);
		} catch (Exception e) {
			throw new WorkflowRuntimeException(e);
		}
	}

	protected void loadXmlFileFromFileSystem(String fileName) {
		try {
			KEWXmlDataLoader.loadXmlFile(fileName);
		} catch (Exception e) {
			throw new WorkflowRuntimeException(e);
		}
	}

}
