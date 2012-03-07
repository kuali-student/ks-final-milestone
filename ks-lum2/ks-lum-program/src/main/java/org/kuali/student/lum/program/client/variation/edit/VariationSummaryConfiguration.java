package org.kuali.student.lum.program.client.variation.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.view.*;
import org.kuali.student.lum.program.client.variation.view.VariationInformationViewConfiguration;

/**
 * @author Igor
 */
public class VariationSummaryConfiguration extends AbstractControllerConfiguration {

    public VariationSummaryConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, getLabel(ProgramMsgConstants.VARIATION_SUMMARY), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.registerConfiguration(VariationInformationViewConfiguration.createSpecial(configurer));
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.createSpecial(configurer, controller));
        configurationManager.registerConfiguration(CatalogInformationViewConfiguration.createSpecial(configurer, controller));
        configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration(configurer, true));
        configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.createSpecial(configurer, controller));
        configurationManager.registerConfiguration(SupportingDocsViewConfiguration.createSpecial(configurer, controller));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }
    }
}

