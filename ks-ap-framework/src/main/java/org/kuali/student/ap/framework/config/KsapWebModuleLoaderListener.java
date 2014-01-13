package org.kuali.student.ap.framework.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.ConfigurationException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.framework.config.module.ModuleConfigurer;
import org.kuali.rice.core.framework.config.module.WebModuleConfiguration;

/**
 * @deprecated TODO: KSAP-26 Move KsapAbstractModuleConfigurer to Rice.
 */
public class KsapWebModuleLoaderListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(KsapWebModuleLoaderListener.class);
    private static final QName RESOURCE_LOADER_NAME = new QName("org.kuali.student.ap.framework.config", "KsapWebModuleResourceLoader");

    @Override
    public void contextInitialized(ServletContextEvent event) {
        List<String> springFileLocations = getSpringFileLocations();
        if (CollectionUtils.isNotEmpty(springFileLocations)) {
            LOG.info("Initializing " + getClass().getSimpleName() + " with spring files: " + springFileLocations);
            org.kuali.rice.core.framework.resourceloader.SpringResourceLoader resourceLoader = new org.kuali.rice.core.framework.resourceloader.SpringResourceLoader(RESOURCE_LOADER_NAME, springFileLocations, event.getServletContext());
            try {
                resourceLoader.start();
            } catch (Exception e) {
                throw new ConfigurationException("Failed to load web module spring configuration", e);
            }
            GlobalResourceLoader.addResourceLoader(resourceLoader);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // shutdown of resource loaders is coordinated by the GRL
    }

    protected List<String> getSpringFileLocations() {
        List<String> springFileLocations = new ArrayList<String>();
        // loop over the installed modules, adding their web module configuration spring files
        final Collection<Object> riceModules = AbstractKsapModuleConfigurer.getCurrentContextConfigurers();
        Object[] tempModules = new Object[riceModules.size()];
        tempModules = riceModules.toArray();

        for(int i = 0; i < tempModules.length; i++){
            try{
                AbstractKsapModuleConfigurer module = (AbstractKsapModuleConfigurer) tempModules[i];
                if ( module.shouldRenderWebInterface() ) {
                    WebModuleConfiguration webModuleConfiguration = module.getWebModuleConfiguration();
                    if (webModuleConfiguration == null) {
                        throw new ConfigurationException("Attempting to load WebModuleConfiguration for module '" + module.getModuleName() + "' but no configuration was provided!");
                    }
                    List<String> webModuleSpringFiles = webModuleConfiguration.getWebSpringFiles();
                    if (CollectionUtils.isNotEmpty(webModuleSpringFiles)) {
                        springFileLocations.addAll(webModuleSpringFiles);
                    }
                }
            }catch(Exception e){
                ModuleConfigurer module = (ModuleConfigurer) tempModules[i];
                if ( module.shouldRenderWebInterface() ) {
                    WebModuleConfiguration webModuleConfiguration = module.getWebModuleConfiguration();
                    if (webModuleConfiguration == null) {
                        throw new ConfigurationException("Attempting to load WebModuleConfiguration for module '" + module.getModuleName() + "' but no configuration was provided!");
                    }
                    List<String> webModuleSpringFiles = webModuleConfiguration.getWebSpringFiles();
                    if (CollectionUtils.isNotEmpty(webModuleSpringFiles)) {
                        springFileLocations.addAll(webModuleSpringFiles);
                    }
                }
            }
        }

        return springFileLocations;
    }

}
