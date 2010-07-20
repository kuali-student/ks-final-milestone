package org.kuali.student.lum.program.client;

import java.util.ArrayList;

import org.kuali.student.lum.program.client.configuration.CatalogInformationConfiguration;
import org.kuali.student.lum.program.client.configuration.LearningObjectivesConfiguration;
import org.kuali.student.lum.program.client.configuration.ManagingBodiesConfiguration;
import org.kuali.student.lum.program.client.configuration.ProgramInformationConfiguration;
import org.kuali.student.lum.program.client.configuration.RequirementsConfiguration;
import org.kuali.student.lum.program.client.configuration.SpecializationsConfiguration;
import org.kuali.student.lum.program.client.configuration.base.Configuration;
import org.kuali.student.lum.program.client.configuration.base.ConfigurationManager;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import com.google.gwt.core.client.GWT;

/**
 * This class represents configuration for  {@link org.kuali.student.lum.program.client.ProgramController}.
 *
 * @author Igor
 */
public class ProgramConfigurer extends AbstractProgramConfigurer {

    private ProgramController controller;

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
    public void configure(ProgramController controller) {
        this.controller = controller;
        controller.setContentTitle("Biology");
        configureProgramSections();
    }

    /**
     * Configures menu for Program Sections and Sections itself
     */
    private void configureProgramSections() {
        String programSectionLabel = ProgramProperties.get().program_menu_sections();
        controller.addMenu(programSectionLabel);
        ArrayList<Configuration<ProgramConfigurer>> configurations = programSectionConfigManager.getConfigurations();
        for (Configuration<ProgramConfigurer> configuration : configurations) {
            if (configuration instanceof EditableConfiguration) {
                EditableConfiguration editableConfiguration = (EditableConfiguration) configuration;
                controller.addMenuItem(programSectionLabel, editableConfiguration.getView(), editableConfiguration.getEditView());
            } else {
                controller.addMenuItem(programSectionLabel, configuration.getView());
            }
        }
    }

    //TODO: this method will be removed once the real data will be loaded.
    public String getData(String key) {
        return controller.getProgramModel().get(key);
    }
}
