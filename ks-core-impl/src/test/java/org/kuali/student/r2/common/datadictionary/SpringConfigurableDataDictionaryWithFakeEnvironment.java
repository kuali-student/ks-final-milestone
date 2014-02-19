/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.common.datadictionary;

import org.kuali.rice.core.api.CoreApiServiceLocator;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.config.property.ConfigurationService;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.resourceloader.ResourceLoader;
import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.core.framework.resourceloader.BaseResourceLoader;
import org.kuali.rice.core.framework.resourceloader.SimpleServiceLocator;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.kuali.rice.krad.datadictionary.DataDictionary;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.KualiModuleService;
import org.kuali.rice.krad.service.impl.DataDictionaryServiceImpl;
import org.kuali.rice.krad.uif.service.impl.UifDefaultingServiceImpl;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.class1.type.service.impl.TypeServiceMockImpl;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class allows us to parse data dictionary files along with creating a fake environment
 * for {@link GlobalResourceLoader}.
 *
 * @author Kuali Student Team
 */
public class SpringConfigurableDataDictionaryWithFakeEnvironment extends DataDictionary implements ApplicationContextAware {
    private static final String MOCK_APP_ID = "mock-app.id";
    private ApplicationContext applicationContext;

    public void init() {
        Config config = new JAXBConfigImpl();
        config.putProperty(CoreConstants.Config.APPLICATION_ID, MOCK_APP_ID);
        config.putProperty("application.version", "1.0");
        config.putProperty("rice.version", "1.0");
        ConfigContext.init(config);
        SimpleServiceLocator serviceLocator = new SimpleServiceLocator();

        ConfigurationService configurationService = new ConfigurationService() {
            @Override public String getPropertyValueAsString(String key) { return "{0} message"; }
            @Override public boolean getPropertyValueAsBoolean(String key) { return false; }
            @Override public Map<String, String> getAllProperties() { return null; }
        };
        serviceLocator.addService(new QName(CoreApiServiceLocator.KUALI_CONFIGURATION_SERVICE), configurationService);

        KualiModuleService moduleService = (KualiModuleService) applicationContext.getBean(KRADServiceLocatorWeb.KUALI_MODULE_SERVICE);
        serviceLocator.addService(new QName(KRADServiceLocatorWeb.KUALI_MODULE_SERVICE), moduleService);

        serviceLocator.addService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART), new TypeServiceMockImpl());

        DataDictionaryServiceImpl dataDictionaryService = new DataDictionaryServiceImpl();
        dataDictionaryService.setKualiConfigurationService(configurationService);
        List<String> locations = Arrays.asList(
                "classpath:org/kuali/rice/krad/uif/UifControlDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifConfigurationDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifWidgetDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifFieldDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifElementDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifHeaderFooterDefinitions.xml",
                "classpath:org/kuali/rice/krad/datadictionary/DataDictionaryBaseTypes.xml",
                "classpath:org/kuali/rice/krad/uif/UifGroupDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifLayoutManagerDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifMenuDefinitions.xml",
                "classpath:org/kuali/rice/krad/uif/UifActionDefinitions.xml");
        try {
            dataDictionaryService.addDataDictionaryLocations("SOME-NAMESPACE", locations);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serviceLocator.addService(new QName(KRADServiceLocatorWeb.DATA_DICTIONARY_SERVICE), dataDictionaryService);

        UifDefaultingServiceImpl uifDefaultingService = new UifDefaultingServiceImpl();
        uifDefaultingService.setDataDictionaryService(dataDictionaryService);
        serviceLocator.addService(new QName(KRADServiceLocatorWeb.UIF_DEFAULTING_SERVICE), uifDefaultingService);

        ResourceLoader resourceLoader =
                new BaseResourceLoader(
                        new QName(MOCK_APP_ID, RiceConstants.DEFAULT_ROOT_RESOURCE_LOADER_NAME), serviceLocator);

        try {
            GlobalResourceLoader.stop();
            GlobalResourceLoader.addResourceLoader(resourceLoader);
            GlobalResourceLoader.start();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing GRL", e);
        }

        dataDictionaryService.getDataDictionary().parseDataDictionaryConfigurationFiles(false);
        super.parseDataDictionaryConfigurationFiles(false);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}