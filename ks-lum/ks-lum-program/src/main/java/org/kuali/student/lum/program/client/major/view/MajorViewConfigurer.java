package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;

import com.google.gwt.core.client.GWT;

/**
 * This class represents configuration for  {@link MajorViewController}.
 *
 * @author Igor
 */
public class MajorViewConfigurer extends AbstractProgramConfigurer {

    public MajorViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(MajorInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ManagingBodiesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SpecializationsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CatalogInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ProgramRequirementsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(LearningObjectivesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SupportingDocsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ViewAllSectionConfiguration.class));
    }
}
