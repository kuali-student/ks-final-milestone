package org.kuali.student.lum.program.client.core.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.view.CoreCatalogInformationViewConfiguration;
import org.kuali.student.lum.program.client.core.view.CoreInformationViewConfiguration;
import org.kuali.student.lum.program.client.core.view.CoreLearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.core.view.CoreManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

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
        configurationManager.registerConfiguration(CoreInformationViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CoreManagingBodiesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CoreCatalogInformationViewConfiguration.createSpecial());
        //configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration());
        configurationManager.registerConfiguration(CoreLearningObjectivesViewConfiguration.createSpecial());
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
    }
}