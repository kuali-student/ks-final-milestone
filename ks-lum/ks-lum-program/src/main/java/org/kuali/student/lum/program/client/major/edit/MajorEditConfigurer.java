package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;

import com.google.gwt.core.client.GWT;

/**
 * @author Igor
 */
public class MajorEditConfigurer extends AbstractProgramConfigurer {

    public MajorEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(MajorInformationEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ManagingBodiesEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SpecializationsEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CatalogInformationEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ProgramRequirementsEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(LearningObjectivesEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SupportingDocsEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(MajorSummaryConfiguration.class));
    }
}
