package org.kuali.student.lum.program.client.credential.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialManager;
import org.kuali.student.lum.program.client.credential.view.*;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class CredentialSummaryConfiguration extends AbstractControllerConfiguration {

    public CredentialSummaryConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SUMMARY), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager();
    	CredentialInformationViewConfiguration credentialInfoViewConfig = CredentialInformationViewConfiguration.createSpecial(configurer);
        configurationManager.registerConfiguration(credentialInfoViewConfig);
        configurationManager.registerConfiguration(CredentialManagingBodiesViewConfiguration.createSpecial(configurer));
        configurationManager.registerConfiguration(CredentialCatalogDetailsViewConfiguration.createSpecial(configurer));
        configurationManager.registerConfiguration(new CredentialRequirementsViewConfiguration(configurer, true));
        configurationManager.registerConfiguration(CredentialLearningObjectivesViewConfiguration.createSpecial(configurer));
        configurationManager.registerConfiguration(CredentialDocsViewConfiguration.createSpecial(configurer));
        
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