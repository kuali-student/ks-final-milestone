package org.kuali.student.lum.program.client.core.view;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.view.ProgramRequirementsViewConfiguration;
import org.kuali.student.lum.program.client.view.SupportingDocsViewConfiguration;

/**
 * @author Igor
 */
public class CoreViewAllConfiguration extends AbstractSectionConfiguration {

    private ProgramController viewController;

    public CoreViewAllConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.VIEW_ALL, ProgramProperties.get().program_menu_sections_viewAll(), ProgramConstants.PROGRAM_MODEL_ID, false);
    }

    @Override
    protected void buildLayout() {
        ConfigurationManager configurationManager = new ConfigurationManager(configurer);
        configurationManager.registerConfiguration(GWT.<Configuration>create(CoreInformationViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(CoreManagingBodiesViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(CoreCatalogInformationViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(CoreRequirementsViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(CoreLearningObjectivesViewConfiguration.class));
        configurationManager.registerConfiguration(GWT.<Configuration>create(SupportingDocsViewConfiguration.class));
        for (Configuration configuration : configurationManager.getConfigurations()) {
            if (configuration instanceof ProgramRequirementsViewConfiguration) {
                ((ProgramRequirementsViewConfiguration) configuration).setViewController(viewController);
            }
            rootSection.addSection((Section) configuration.getView());
        }
    }

    public void setViewController(ProgramController viewController) {
        this.viewController = viewController;
    }
}