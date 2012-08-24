package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.major.view.*;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class MajorSummaryConfiguration extends AbstractControllerConfiguration {

    public MajorSummaryConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SUMMARY), ProgramConstants.PROGRAM_MODEL_ID, true);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager();
    	MajorKeyProgramInfoViewConfiguration majorInfoViewConfig = MajorKeyProgramInfoViewConfiguration.createSpecial(configurer, controller);
        configurationManager.registerConfiguration(majorInfoViewConfig);
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.createSpecial(configurer, controller));
        configurationManager.registerConfiguration(SpecializationsViewConfiguration.createSpecial(configurer, controller));
        configurationManager.registerConfiguration(CatalogInformationViewConfiguration.createSpecial(configurer, controller));
        configurationManager.registerConfiguration(new ProgramRequirementsViewConfiguration(configurer, controller,true));
        configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.createSpecial(configurer, controller));
        configurationManager.registerConfiguration(SupportingDocsViewConfiguration.createSpecial(configurer, controller));

        rootSection.addWidget(new SummaryActionPanel(majorInfoViewConfig.createActivateProgramSection(), MajorManager.getEventBus()));        
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(controller);
            }
            rootSection.addSection((Section) configuration.getView());
        }        
        rootSection.addWidget(new SummaryActionPanel(majorInfoViewConfig.createActivateProgramSection(), MajorManager.getEventBus()));
    }
}
