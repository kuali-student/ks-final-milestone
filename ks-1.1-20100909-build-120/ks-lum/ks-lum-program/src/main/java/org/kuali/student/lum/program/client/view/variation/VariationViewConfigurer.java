package org.kuali.student.lum.program.client.view.variation;

import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.framework.Configuration;
import org.kuali.student.lum.program.client.framework.ConfigurationManager;
import org.kuali.student.lum.program.client.framework.EditableConfiguration;
import org.kuali.student.lum.program.client.view.CatalogInformationViewConfiguration;
import org.kuali.student.lum.program.client.view.LearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.view.ManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.view.ProgramInformationViewConfiguration;
import org.kuali.student.lum.program.client.view.ProgramViewConfigurer;
import org.kuali.student.lum.program.client.view.SpecializationsViewConfiguration;
import org.kuali.student.lum.program.client.view.ViewAllSectionConfiguration;

import com.google.gwt.core.client.GWT;

public class VariationViewConfigurer extends AbstractProgramConfigurer<VariationViewConfigurer>{
	
    public VariationViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager<VariationViewConfigurer>(this);
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<VariationViewConfigurer>>create(VariationInformationViewConfiguration.class));
//        programSectionConfigManager.registerConfiguration(GWT.<Configuration<VariationViewConfigurer>>create(ManagingBodiesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<VariationViewConfigurer>>create(VariationCatalogInformationViewConfiguration.class));
//        programSectionConfigManager.registerConfiguration(GWT.<Configuration<VariationViewConfigurer>>create(ProgramRequirementsEditConfiguration.class));  //TODO change to View
//        programSectionConfigManager.registerConfiguration(GWT.<Configuration<VariationViewConfigurer>>create(LearningObjectivesViewConfiguration.class));
//        programSectionConfigManager.registerConfiguration(GWT.<Configuration<VariationViewConfigurer>>create(ViewAllSectionConfiguration.class));
    }
}
