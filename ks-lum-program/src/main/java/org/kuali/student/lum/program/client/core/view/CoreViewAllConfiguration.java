package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

/**
 * @author Igor
 */
public class CoreViewAllConfiguration extends AbstractControllerConfiguration {

    public CoreViewAllConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_VIEWALL), ProgramConstants.PROGRAM_MODEL_ID, false);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.registerConfiguration(CoreInformationViewConfiguration.create(configurer));
        configurationManager.registerConfiguration(CoreManagingBodiesViewConfiguration.create(configurer));
        configurationManager.registerConfiguration(CoreCatalogInformationViewConfiguration.create(configurer));
        configurationManager.registerConfiguration(new CoreRequirementsViewConfiguration(configurer, false));
        configurationManager.registerConfiguration(CoreLearningObjectivesViewConfiguration.create(configurer));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
    }
}