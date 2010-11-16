package org.kuali.student.lum.program.client.bacc.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.bacc.CredentialManager;
import org.kuali.student.lum.program.client.bacc.view.BaccCatalogDetailsViewConfiguration;
import org.kuali.student.lum.program.client.bacc.view.BaccDocsViewConfiguration;
import org.kuali.student.lum.program.client.bacc.view.BaccInformationViewConfiguration;
import org.kuali.student.lum.program.client.bacc.view.BaccLearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.bacc.view.BaccRequirementsViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class BaccSummaryConfiguration extends AbstractControllerConfiguration {

    public BaccSummaryConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().program_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
    	BaccInformationViewConfiguration baccInfoViewConfig = BaccInformationViewConfiguration.createSpecial();
        configurationManager.registerConfiguration(baccInfoViewConfig);    	
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(BaccCatalogDetailsViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(new BaccRequirementsViewConfiguration());
        configurationManager.registerConfiguration(BaccLearningObjectivesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(BaccDocsViewConfiguration.createSpecial());
        
        rootSection.addWidget(new SummaryActionPanel(baccInfoViewConfig.createActivateProgramSection(), CredentialManager.getEventBus()));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
        rootSection.addWidget(new SummaryActionPanel(baccInfoViewConfig.createActivateProgramSection(), CredentialManager.getEventBus()));
    }
}