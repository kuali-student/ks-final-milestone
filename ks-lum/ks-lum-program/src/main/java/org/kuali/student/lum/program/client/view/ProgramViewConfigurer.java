package org.kuali.student.lum.program.client.view;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.configuration.base.Configuration;
import org.kuali.student.lum.program.client.configuration.base.ConfigurationManager;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;
import org.kuali.student.lum.program.client.edit.ProgramRequirementsEditConfiguration;

/**
 * This class represents configuration for  {@link org.kuali.student.lum.program.client.view.ProgramViewController}.
 *
 * @author Igor
 */
public class ProgramViewConfigurer extends AbstractProgramConfigurer<ProgramViewConfigurer> {

    public ProgramViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager<ProgramViewConfigurer>(this);
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<ProgramViewConfigurer>>create(ProgramInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(ManagingBodiesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(SpecializationsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<ProgramViewConfigurer>>create(CatalogInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(ProgramRequirementsEditConfiguration.class));  //TODO change to View
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(LearningObjectivesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(ViewAllSectionConfiguration.class));
    }
}
