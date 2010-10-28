package org.kuali.student.lum.program.client.variation.view;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.view.CatalogInformationViewConfiguration;
import org.kuali.student.lum.program.client.major.view.LearningObjectivesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ManagingBodiesViewConfiguration;
import org.kuali.student.lum.program.client.major.view.ProgramRequirementsViewConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class VariationViewAllConfiguration extends AbstractSectionConfiguration {

    public VariationViewAllConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, ProgramProperties.get().program_menu_sections_viewAll(), ProgramConstants.PROGRAM_MODEL_ID, false);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
        configurationManager.registerConfiguration(VariationInformationViewConfiguration.create());
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.create());
        configurationManager.registerConfiguration(CatalogInformationViewConfiguration.create());
        configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration());
        configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.create());
        for (Configuration configuration : configurationManager.getConfigurations()) {
            rootSection.addSection((Section) configuration.getView());
        }
    }
}
