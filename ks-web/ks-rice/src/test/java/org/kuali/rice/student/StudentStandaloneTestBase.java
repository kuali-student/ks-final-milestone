/**
 * 
 */
package org.kuali.rice.student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.lifecycle.BaseLifecycle;
import org.kuali.rice.core.lifecycle.Lifecycle;
import org.kuali.rice.core.resourceloader.SpringResourceLoader;
import org.kuali.rice.kew.batch.KEWXmlDataLoader;
import org.kuali.rice.kew.exception.WorkflowRuntimeException;
import org.kuali.rice.kim.service.KIMServiceLocator;
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
        SpringResourceLoader springResourceLoader = new SpringResourceLoader(new QName("StudentStandaloneTestResourceLoader"), "classpath:StandaloneTestSpringBeans.xml");
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
			KIMServiceLocator.getIdentityManagementService().flushAllCaches();
			KIMServiceLocator.getRoleManagementService().flushRoleCaches();
			super.stop();
		}
	}
	
	// below method to be removed when moving to Rice 1.0.1
	@Override
	protected void loadSuiteTestData() throws Exception {
		new SQLDataLoader(getKNSDefaultSuiteTestData(), "/").runSql();
		BufferedReader reader = new BufferedReader(new FileReader(getKIMDataLoadOrderFile()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (!StringUtils.isBlank(line)) {
				new SQLDataLoader(getKIMSqlFileBaseLocation() + "/" + line, "/").runSql();
			}
		}
		new SQLDataLoader("file:" + getBaseDir() + "/../src/main/config/sql/kim.sql", ";").runSql();
	}
	
    protected String getKNSDefaultSuiteTestData() {
        return "file:" + getBaseDir() + "/../src/test/config/data/DefaultSuiteTestDataKNS.sql";
    }

    protected String getKIMDataLoadOrderFile() {
        return getBaseDir() + "/../src/test/config/data/KIMDataLoadOrder.txt";
    }

    protected String getKIMSqlFileBaseLocation() {
        return "file:" + getBaseDir() + "/../src/test/config/data";
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