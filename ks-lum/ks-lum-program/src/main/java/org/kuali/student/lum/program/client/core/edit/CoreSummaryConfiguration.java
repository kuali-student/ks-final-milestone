package org.kuali.student.lum.program.client.core.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreManager;
import org.kuali.student.lum.program.client.core.view.*;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class CoreSummaryConfiguration extends AbstractControllerConfiguration {

    public CoreSummaryConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SUMMARY), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager();
    	CoreInformationViewConfiguration coreInfoViewConfig = CoreInformationViewConfiguration.createSpecial(configurer);
        configurationManager.registerConfiguration(coreInfoViewConfig);
        configurationManager.registerConfiguration(CoreManagingBodiesViewConfiguration.createSpecial(configurer));
        configurationManager.registerConfiguration(CoreCatalogInformationViewConfiguration.createSpecial(configurer));
        configurationManager.registerConfiguration(new CoreRequirementsViewConfiguration(configurer, true));
        configurationManager.registerConfiguration(CoreLearningObjectivesViewConfiguration.createSpecial(configurer));
        configurationManager.registerConfiguration(CoreDocsViewConfiguration.createSpecial(configurer));

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