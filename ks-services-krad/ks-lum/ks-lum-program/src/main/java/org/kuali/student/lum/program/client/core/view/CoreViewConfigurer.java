package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;

/**
 * @author Igor
 */
public class CoreViewConfigurer extends AbstractProgramConfigurer {

    public CoreViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(CoreInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(CoreManagingBodiesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(CoreCatalogInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new CoreRequirementsViewConfiguration(false));        
        programSectionConfigManager.registerConfiguration(CoreLearningObjectivesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new CoreViewAllConfiguration());
    }
}
