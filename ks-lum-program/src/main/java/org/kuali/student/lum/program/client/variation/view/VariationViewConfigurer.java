package org.kuali.student.lum.program.client.variation.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.*;

public class VariationViewConfigurer extends AbstractProgramConfigurer {

    public VariationViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(VariationInformationViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(ManagingBodiesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(CatalogInformationViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsViewConfiguration(this, false));
        programSectionConfigManager.registerConfiguration(LearningObjectivesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new VariationViewAllConfiguration(this));
    }
}
