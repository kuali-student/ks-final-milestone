package org.kuali.student.lum.program.client;

import org.kuali.student.lum.program.client.configuration.*;
import org.kuali.student.lum.program.client.configuration.base.Configuration;
import org.kuali.student.lum.program.client.configuration.base.ConfigurationManager;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import java.util.ArrayList;

/**
 * @author Igor
 */
public class ProgramConfigurer extends AbstractProgramConfigurer {

    private ProgramController controller;

    private ConfigurationManager<ProgramConfigurer> configurationManager;

    public ProgramConfigurer() {
        configurationManager = new ConfigurationManager<ProgramConfigurer>(this);
        configurationManager.registerEditableConfiguration(ProgramInformationConfiguration.class);
        configurationManager.registerConfiguration(SpecializationsConfiguration.class);
        configurationManager.registerConfiguration(RequirementsConfiguration.class);
        configurationManager.registerConfiguration(ManagingBodiesConfiguration.class);
        configurationManager.registerConfiguration(CatalogInformationConfiguration.class);
        configurationManager.registerConfiguration(LearningObjectivesConfiguration.class);
    }

    @Override
    public void configure(ProgramController controller) {
        this.controller = controller;
        controller.setContentTitle("Biology");
        configureProgramSections();
    }

    private void configureProgramSections() {
        String programSectionLabel = ProgramProperties.get().program_menu_sections();
        controller.addMenu(programSectionLabel);
        ArrayList<Configuration<ProgramConfigurer>> configurations = configurationManager.getConfigurations();
        for (Configuration<ProgramConfigurer> configuration : configurations) {
            if (configuration instanceof EditableConfiguration) {
                EditableConfiguration editableConfiguration = (EditableConfiguration) configuration;
                controller.addMenuItem(programSectionLabel, editableConfiguration.getView(), editableConfiguration.getEditView());
            } else {
                controller.addMenuItem(programSectionLabel, configuration.getView());
            }
        }
    }

    public String getData(String key) {
        return controller.getProgramModel().get(key);
    }
}
