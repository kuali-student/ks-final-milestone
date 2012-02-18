package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;

/**
 * @author Igor
 */
public class CoreViewConfigurer extends AbstractProgramConfigurer {

    public CoreViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(CoreInformationViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(CoreManagingBodiesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(CoreCatalogInformationViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new CoreRequirementsViewConfiguration(this, false));        
        programSectionConfigManager.registerConfiguration(CoreLearningObjectivesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new CoreViewAllConfiguration(this));
    }
}
