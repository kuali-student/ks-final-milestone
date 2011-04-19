package org.kuali.student.lum.program.client.variation.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.*;

public class VariationViewConfigurer extends AbstractProgramConfigurer {

    public VariationViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(VariationInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(ManagingBodiesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(CatalogInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsViewConfiguration(false));
        programSectionConfigManager.registerConfiguration(LearningObjectivesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new VariationViewAllConfiguration());
    }
}
