package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorManager;
import org.kuali.student.lum.program.client.major.view.*;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.SummaryActionPanel;

/**
 * @author Igor
 */
public class MajorSummaryConfiguration extends AbstractControllerConfiguration {

    private ProgramRequirementsViewConfiguration requirementsConfiguration;

    public MajorSummaryConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.SUMMARY, ProgramProperties.get().program_menu_sections_summary(), ProgramConstants.PROGRAM_MODEL_ID, true)/*{
            @Override
            public void beforeShow(Callback<Boolean> onReadyCallback) {
                requirementsConfiguration.getProgReqcontroller().updateModel();
                super.beforeShow(onReadyCallback);
            }
        }*/;
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
        requirementsConfiguration = new ProgramRequirementsViewConfiguration(true, true);
    	MajorInformationViewConfiguration majorInfoViewConfig = MajorInformationViewConfiguration.createSpecial();
        configurationManager.registerConfiguration(majorInfoViewConfig);
        configurationManager.registerConfiguration(ManagingBodiesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(SpecializationsViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(CatalogInformationViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(requirementsConfiguration);
        configurationManager.registerConfiguration(LearningObjectivesViewConfiguration.createSpecial());
        configurationManager.registerConfiguration(SupportingDocsViewConfiguration.createSpecial());

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
