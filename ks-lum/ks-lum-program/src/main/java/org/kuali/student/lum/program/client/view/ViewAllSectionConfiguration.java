package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
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
        //verticalPanel.add(configurationManager.getConfiguration(ManagingBodiesViewConfiguration.class.getName()).asWidget());
        //verticalPanel.add(configurationManager.getConfiguration(SpecializationsViewConfiguration.class.getName()).asWidget());
        rootSection.addSection((Section) configurationManager.getConfiguration(ProgramSections.CATALOG_INFO_VIEW).asWidget());
        //rootSection.addWidget(configurationManager.getConfiguration(ProgramRequirementsViewConfiguration.class.getName()).asWidget());
        //verticalPanel.add(configurationManager.getConfiguration(LearningObjectivesViewConfiguration.class.getName()).asWidget());
        //rootSection.addSection(ConfigurationRegistry.getRootSection());
    }
}
