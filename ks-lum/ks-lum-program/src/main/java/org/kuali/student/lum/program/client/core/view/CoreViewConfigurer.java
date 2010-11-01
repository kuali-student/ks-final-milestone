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
        programSectionConfigManager.registerConfiguration(new CoreInformationViewConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreManagingBodiesViewConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreCatalogInformationViewConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreLearningObjectivesViewConfiguration());
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new CoreViewAllConfiguration());
    }
}
