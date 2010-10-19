package org.kuali.student.lum.program.client.variation.view;

import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.*;

import com.google.gwt.core.client.GWT;

public class VariationViewConfigurer extends AbstractProgramConfigurer {

    public VariationViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(new VariationInformationViewConfiguration());
        programSectionConfigManager.registerConfiguration(new ManagingBodiesViewConfiguration());
        programSectionConfigManager.registerConfiguration(new CatalogInformationViewConfiguration());
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsViewConfiguration());
        programSectionConfigManager.registerConfiguration(new LearningObjectivesViewConfiguration());
        programSectionConfigManager.registerConfiguration(new SupportingDocsViewConfiguration());
        programSectionConfigManager.registerConfiguration(new VariationViewAllConfiguration());
    }
}
