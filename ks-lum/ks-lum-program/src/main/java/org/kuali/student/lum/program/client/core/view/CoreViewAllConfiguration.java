package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CoreViewAllConfiguration extends AbstractSectionConfiguration {

    private ProgramController viewController;

    public CoreViewAllConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, ProgramProperties.get().program_menu_sections_viewAll(), ProgramConstants.PROGRAM_MODEL_ID, false);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
        configurationManager.registerConfiguration(CoreInformationViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CoreManagingBodiesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CoreCatalogInformationViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(new CoreRequirementsViewConfiguration());
        configurationManager.registerConfiguration(CoreLearningObjectivesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(SupportingDocsViewConfiguration.createSpecial());
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(viewController);
            }
            rootSection.addSection((Section) configuration.getView());
        }
    }
}