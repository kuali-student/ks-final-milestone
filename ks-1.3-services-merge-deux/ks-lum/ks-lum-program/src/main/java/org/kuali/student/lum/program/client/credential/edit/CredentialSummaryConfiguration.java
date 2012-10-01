package org.kuali.student.lum.program.client.credential.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialManager;
import org.kuali.student.lum.program.client.credential.view.*;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class CredentialSummaryConfiguration extends AbstractControllerConfiguration {

    public CredentialSummaryConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().program_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
    	CredentialInformationViewConfiguration credentialInfoViewConfig = CredentialInformationViewConfiguration.createSpecial();
        configurationManager.registerConfiguration(credentialInfoViewConfig);
        configurationManager.registerConfiguration(CredentialManagingBodiesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CredentialCatalogDetailsViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(new CredentialRequirementsViewConfiguration(true));
        configurationManager.registerConfiguration(CredentialLearningObjectivesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CredentialDocsViewConfiguration.createSpecial());
        
        rootSection.addWidget(new SummaryActionPanel(credentialInfoViewConfig.createActivateProgramSection(), CredentialManager.getEventBus()));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
        rootSection.addWidget(new SummaryActionPanel(credentialInfoViewConfig.createActivateProgramSection(), CredentialManager.getEventBus()));
    }
}