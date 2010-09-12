package org.kuali.student.lum.program.client.view;

import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.framework.ConfigurationManager;
import org.kuali.student.lum.program.client.framework.ConfigurationRegistry;

/**
 * @author Igor
 */
public class ViewAllSectionConfiguration extends AbstractSectionConfiguration<ProgramViewConfigurer> {

    public ViewAllSectionConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, "View All", ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager<ProgramViewConfigurer> configurationManager = ConfigurationRegistry.get(ProgramViewConfigurer.class.getName());
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(configurationManager.getConfiguration(ProgramInformationViewConfiguration.class.getName()).asWidget());
        verticalPanel.add(configurationManager.getConfiguration(ManagingBodiesViewConfiguration.class.getName()).asWidget());
        verticalPanel.add(configurationManager.getConfiguration(SpecializationsViewConfiguration.class.getName()).asWidget());
        verticalPanel.add(configurationManager.getConfiguration(CatalogInformationViewConfiguration.class.getName()).asWidget());
        //rootSection.addWidget(configurationManager.getConfiguration(ProgramRequirementsViewConfiguration.class.getName()).asWidget());
        verticalPanel.add(configurationManager.getConfiguration(LearningObjectivesViewConfiguration.class.getName()).asWidget());
        rootSection.addWidget(verticalPanel);
    }
}
