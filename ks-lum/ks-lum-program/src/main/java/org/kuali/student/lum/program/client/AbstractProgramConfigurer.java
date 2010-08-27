package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.lum.program.client.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.framework.Configuration;
import org.kuali.student.lum.program.client.framework.ConfigurationManager;
import org.kuali.student.lum.program.client.framework.ConfigurationRegistry;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import java.util.ArrayList;

/**
 * @author Igor
 */
public abstract class AbstractProgramConfigurer<T extends Configurer> extends Configurer {

    private ProgramController viewController;

    protected ConfigurationManager<T> programSectionConfigManager;

    public void configure(ProgramController viewController) {
        this.viewController = viewController;
        configureProgramSections();
    }

    /**
     * Configures menu for Program Sections
     */
    private void configureProgramSections() {
        String programSectionLabel = ProgramProperties.get().program_menu_sections();
        viewController.addMenu(programSectionLabel);
        ArrayList<Configuration<T>> configurations = programSectionConfigManager.getConfigurations();
        for (Configuration<T> configuration : configurations) {
            if (configuration instanceof ProgramRequirementsEditConfiguration) {
                ((ProgramRequirementsEditConfiguration) configuration).setViewController(viewController);
            }
            viewController.addMenuItem(programSectionLabel, configuration.getView());
        }
    }
}
