package org.kuali.student.lum.program.client.view.variation;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.framework.Configuration;
import org.kuali.student.lum.program.client.framework.ConfigurationManager;
import org.kuali.student.lum.program.client.view.CatalogInformationViewConfiguration;
import org.kuali.student.lum.program.client.view.LearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.view.ManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.view.ProgramInformationViewConfiguration;

public class VariationViewConfigurer extends AbstractProgramConfigurer {

    public VariationViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ProgramInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ManagingBodiesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CatalogInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ProgramRequirementsEditConfiguration.class));  //TODO change to View
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(LearningObjectivesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(VariationViewAllConfiguration.class));
    }
}
