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
package org.kuali.student.r2.core.process.krms;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.impl.repository.TermResolverBo;
import org.kuali.rice.test.BaselineTestCase;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;
import org.kuali.rice.test.RiceTestCase;
import org.kuali.rice.test.SQLDataLoader;
import org.springframework.core.io.DefaultResourceLoader;

import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@BaselineMode(Mode.ROLLBACK_CLEAR_DB)
public abstract class KSKRMSTestCase extends RiceTestCase {

    private static final String KRMS_MODULE_NAME = "krms";

    private SpringResourceLoader krmsTestResourceLoader;

    public KSKRMSTestCase() {
        super();
    }

    @Override
    protected List<Lifecycle> getSuiteLifecycles() {
        this.setClearTables(false);
        List<Lifecycle> suiteLifecycles = super.getSuiteLifecycles();
        return suiteLifecycles;
    }

    @Override
    protected void loadSuiteTestData() throws Exception {
        new SQLDataLoader("classpath:ks-krms.sql", "/").runSql();
    }

    /**
     * Returns the location of the test harness spring beans context file.
     * Subclasses may override to specify a different location.
     * @return the location of the test harness spring beans context file.
     */
    protected List<String> getTestHarnessSpringBeansLocation() {
        return Collections.singletonList("classpath:KRMSTestHarnessSpringBeans.xml");
    }

    protected List<Lifecycle> getPerTestLifecycles() {
        return new LinkedList<Lifecycle>();
    }

    /**
     * @see org.kuali.rice.test.RiceTestCase#getModuleName()
     */
    @Override
    protected String getModuleName() {
        return KRMS_MODULE_NAME;
    }

    protected TermResolverDefinition krmsTermResolverLookup(String termResolverName) {
        // this may be called more than once, we only want to create one though
        //Map<String, String> queryArgs = new HashMap<String, String>();
        //queryArgs.put("nm", termResolverName);
        //TermResolverBo termBo = getBoService().findByPrimaryKey(TermResolverBo.class, queryArgs);
        //if (termBo != null) {
        //    return TermResolverBo.to(termBo);
        //}
        return null;
    }
}