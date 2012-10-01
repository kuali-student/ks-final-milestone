package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;

/**
 * @author Igor
 */
public class MajorEditConfigurer extends AbstractProgramConfigurer {

    public MajorEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(new MajorKeyProgramInfoEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new ManagingBodiesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new SpecializationsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CatalogInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new SupportingDocsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new MajorSummaryConfiguration(this));
    }
}
