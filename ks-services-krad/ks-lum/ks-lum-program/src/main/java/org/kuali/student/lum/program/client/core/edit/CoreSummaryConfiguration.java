package org.kuali.student.lum.program.client.core.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreManager;
import org.kuali.student.lum.program.client.core.view.*;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class CoreSummaryConfiguration extends AbstractControllerConfiguration {

    public CoreSummaryConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().program_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
    	CoreInformationViewConfiguration coreInfoViewConfig = CoreInformationViewConfiguration.createSpecial();
        configurationManager.registerConfiguration(coreInfoViewConfig);
        configurationManager.registerConfiguration(CoreManagingBodiesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CoreCatalogInformationViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(new CoreRequirementsViewConfiguration(true));
        configurationManager.registerConfiguration(CoreLearningObjectivesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CoreDocsViewConfiguration.createSpecial());

        rootSection.addWidget(new SummaryActionPanel(coreInfoViewConfig.createActivateProgramSection(), CoreManager.getEventBus()));        
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
        rootSection.addWidget(new SummaryActionPanel(coreInfoViewConfig.createActivateProgramSection(), CoreManager.getEventBus()));        
    }
}