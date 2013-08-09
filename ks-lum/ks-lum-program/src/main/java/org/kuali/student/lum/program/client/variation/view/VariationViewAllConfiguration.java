package org.kuali.student.lum.program.client.variation.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.view.CatalogInformationViewConfiguration;
import org.kuali.student.lum.program.client.major.view.LearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ProgramRequirementsViewConfiguration;

/**
 * @author Igor
 */
public class VariationViewAllConfiguration extends AbstractControllerConfiguration {

    public VariationViewAllConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_VIEWALL), ProgramConstants.PROGRAM_MODEL_ID, false);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.registerConfiguration(VariationInformationViewConfiguration.create(configurer));
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.create(configurer));
        configurationManager.registerConfiguration(CatalogInformationViewConfiguration.create(configurer));
        configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration(configurer, false));
        configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.create(configurer));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
    }
}
