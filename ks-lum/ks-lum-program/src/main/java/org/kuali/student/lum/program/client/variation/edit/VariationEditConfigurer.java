package org.kuali.student.lum.program.client.variation.edit;

import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.*;

import com.google.gwt.core.client.GWT;

/**
 * @author Igor
 */
public class VariationEditConfigurer extends AbstractProgramConfigurer {

    public VariationEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ProgramInformationEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ManagingBodiesEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CatalogInformationEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ProgramRequirementsEditConfiguration.class)); 
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(LearningObjectivesEditConfiguration.class));
    }
}