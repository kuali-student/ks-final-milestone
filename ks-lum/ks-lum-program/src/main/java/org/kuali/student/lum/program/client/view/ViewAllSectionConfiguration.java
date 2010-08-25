package org.kuali.student.lum.program.client.view;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.framework.Configuration;
import org.kuali.student.lum.program.client.framework.ConfigurationManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ViewAllSectionConfiguration extends AbstractSectionConfiguration<ProgramViewConfigurer> {

    public ViewAllSectionConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, ProgramProperties.get().program_menu_sections_viewAll(), ProgramConstants.PROGRAM_MODEL_ID, false);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager<ProgramViewConfigurer> configurationManager = new ConfigurationManager<ProgramViewConfigurer>(configurer);
        configurationManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(ProgramInformationViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(ManagingBodiesViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(SpecializationsViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(CatalogInformationViewConfiguration.class));
        //configurationManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(ProgramRequirementsEditConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(LearningObjectivesViewConfiguration.class));
        for (Configuration<ProgramViewConfigurer> configuration : configurationManager.getConfigurations()) {
            rootSection.addSection((Section) configuration.getView());
        }
    }
}
