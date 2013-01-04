package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CredentialViewAllSectionsConfiguration extends AbstractControllerConfiguration {

    public CredentialViewAllSectionsConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, ProgramProperties.get().program_menu_sections_viewAll(), ProgramConstants.PROGRAM_MODEL_ID, false);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
        configurationManager.registerConfiguration(CredentialInformationViewConfiguration.create());
        configurationManager.registerConfiguration(CredentialManagingBodiesViewConfiguration.create());
        configurationManager.registerConfiguration(CredentialCatalogDetailsViewConfiguration.create());
        configurationManager.registerConfiguration(new CredentialRequirementsViewConfiguration(false));
        configurationManager.registerConfiguration(CredentialLearningObjectivesViewConfiguration.create());
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
    }
}