package org.kuali.student.lum.program.client.major.edit;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.view.*;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class MajorSummaryConfiguration extends AbstractControllerConfiguration {

    public MajorSummaryConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().program_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        rootSection.addWidget(new SummaryActionPanel());
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
        configurationManager.registerConfiguration(GWT.<Configuration>create(MajorInformationViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(ManagingBodiesViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(SpecializationsViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(CatalogInformationViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(ProgramRequirementsViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(LearningObjectivesViewConfiguration.class));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
        rootSection.addWidget(new SummaryActionPanel());
    }
}
