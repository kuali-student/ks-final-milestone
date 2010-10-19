package org.kuali.student.lum.program.client.variation.edit;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.edit.*;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class VariationSummaryConfiguration extends AbstractControllerConfiguration {

    public VariationSummaryConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().variation_summary(), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        rootSection.addWidget(new SummaryActionPanel());
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
        configurationManager.registerConfiguration(GWT.<Configuration>create(VariationInformationEditConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(ManagingBodiesEditConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(CatalogInformationEditConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(ProgramRequirementsEditConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(LearningObjectivesEditConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(SupportingDocsEditConfiguration.class));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
        //rootSection.addWidget(new SummaryActionPanel());
    }
}

