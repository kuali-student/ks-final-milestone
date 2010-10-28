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
        programSectionConfigManager.registerConfiguration(MajorInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(ManagingBodiesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(SpecializationsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(CatalogInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsViewConfiguration());
        programSectionConfigManager.registerConfiguration(LearningObjectivesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new ViewAllSectionConfiguration());
    }
}
