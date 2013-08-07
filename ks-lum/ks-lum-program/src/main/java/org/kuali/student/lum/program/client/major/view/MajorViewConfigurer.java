package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;

/**
 * This class represents configuration for  {@link MajorViewController}.
 *
 * @author Igor
 */
public class MajorViewConfigurer extends AbstractProgramConfigurer {

    public MajorViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(MajorKeyProgramInfoViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(ManagingBodiesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(SpecializationsViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(CatalogInformationViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsViewConfiguration(this, false));
        programSectionConfigManager.registerConfiguration(LearningObjectivesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new ViewAllSectionConfiguration(this));
    }
}
