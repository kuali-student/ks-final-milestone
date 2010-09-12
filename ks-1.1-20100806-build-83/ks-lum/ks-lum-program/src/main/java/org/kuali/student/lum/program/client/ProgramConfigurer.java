package org.kuali.student.lum.program.client;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.program.client.configuration.*;
import org.kuali.student.lum.program.client.configuration.base.Configuration;
import org.kuali.student.lum.program.client.configuration.base.ConfigurationManager;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import java.util.ArrayList;

/**
 * This class represents configuration for  {@link ProgramViewController}.
 *
 * @author Igor
 */
public class ProgramConfigurer extends AbstractProgramConfigurer {

    private ProgramController viewController;

    private final ConfigurationManager<ProgramConfigurer> programSectionConfigManager;

    public ProgramConfigurer() {
        programSectionConfigManager = new ConfigurationManager<ProgramConfigurer>(this);
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<ProgramConfigurer>>create(ProgramInformationConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramConfigurer>>create(SpecializationsConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramConfigurer>>create(RequirementsConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramConfigurer>>create(ManagingBodiesConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramConfigurer>>create(CatalogInformationConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramConfigurer>>create(LearningObjectivesConfiguration.class));
    }

    @Override
    public void configure(ProgramController viewController) {
        this.viewController = viewController;
        viewController.setContentTitle("Biology");
        configureProgramSections();
    }

    /**
     * Configures menu for Program Sections and Sections itself
     */
    private void configureProgramSections() {
        String programSectionLabel = ProgramProperties.get().program_menu_sections();
        viewController.addMenu(programSectionLabel);
        ArrayList<Configuration<ProgramConfigurer>> configurations = programSectionConfigManager.getConfigurations();
        for (Configuration<ProgramConfigurer> configuration : configurations) {
            viewController.addMenuItem(programSectionLabel, configuration.getView());
        }
    }
}
