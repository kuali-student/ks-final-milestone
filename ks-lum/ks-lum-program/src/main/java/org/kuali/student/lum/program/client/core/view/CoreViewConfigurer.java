package org.kuali.student.lum.program.client.core.view;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;

/**
 * @author Igor
 */
public class CoreViewConfigurer extends AbstractProgramConfigurer {

    public CoreViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CoreInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CoreManagingBodiesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CoreCatalogInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CoreLearningObjectivesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SupportingDocsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CoreViewAllConfiguration.class));
    }
}
