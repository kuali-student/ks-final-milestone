/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.common.test.resourceloader;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.resourceloader.ServiceLocator;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.impl.KualiModuleServiceImpl;
import org.kuali.rice.krad.uif.service.impl.UifDefaultingServiceImpl;
import org.kuali.student.kim.permission.mock.IdentityServiceMockImpl;
import org.kuali.student.kim.permission.mock.RoleServiceMockImpl;
import org.springframework.context.ApplicationContext;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * This is a simple service locator class which piggy backs on the application context for returning services.
 * Certain special KRAD services are provided through static variables.
 *
 * @author haroon rafique
 */
public class SimpleSpringResourceLoader implements ServiceLocator {

    private static final ConfigurationService configurationService = new ConfigurationService() {
        @Override public String getPropertyValueAsString(String key) { return "{0} message"; }
        @Override public boolean getPropertyValueAsBoolean(String key) { return false; }
        @Override public Map<String, String> getAllProperties() { return null; }
    };

    private static final KualiModuleServiceImpl kualiModuleService = new KualiModuleServiceImpl();
    private static final UifDefaultingServiceImpl uifDefaultingService = new UifDefaultingServiceImpl();
    private static final IdentityService identityService = new IdentityServiceMockImpl();
    private static final RoleService roleService = new RoleServiceMockImpl();

    private ApplicationContext applicationContext;
    private DataDictionaryService dataDictionaryService;

    public SimpleSpringResourceLoader(ApplicationContext applicationContext, DataDictionaryService dataDictionaryService) {
        this.applicationContext = applicationContext;
        this.dataDictionaryService = dataDictionaryService;

        uifDefaultingService.setDataDictionaryService(dataDictionaryService);
        kualiModuleService.setApplicationContext(applicationContext);
    }

    public Object getService(QName qname) {
        if (qname == null || StringUtils.isEmpty(qname.toString())) {
            throw new IllegalArgumentException("The service name must be non-null.");
        }

        String qualifiedServiceName = qname.toString();

        if (CoreApiServiceLocator.KUALI_CONFIGURATION_SERVICE.equals(qualifiedServiceName)) {
            return configurationService;
        } else if (KRADServiceLocatorWeb.KUALI_MODULE_SERVICE.equals(qualifiedServiceName)) {
            return kualiModuleService;
        } else if (KRADServiceLocatorWeb.DATA_DICTIONARY_SERVICE.equals(qualifiedServiceName)) {
            return dataDictionaryService;
        } else if (KRADServiceLocatorWeb.UIF_DEFAULTING_SERVICE.equals(qualifiedServiceName)) {
            return uifDefaultingService;
        } else if ("{http://rice.kuali.org/kim/v2_0}identityService".equals(qualifiedServiceName)) {
            return identityService;
        } else if ("{http://rice.kuali.org/kim/v2_0}roleService".equals(qualifiedServiceName)) {
            return roleService;
        } else {
            String localBeanName = qname.getLocalPart();
            return applicationContext.getBean(localBeanName);
        }
    }

    @Override
    public String getContents(String indent, boolean servicePerLine) {
        return null;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public boolean isStarted() {
        return false;
    }
}