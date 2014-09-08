package org.kuali.student.lum.program.client.core.edit;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.LearningObjectivesEditConfiguration;

/**
 * @author Igor
 */
public class CoreEditConfigurer extends AbstractProgramConfigurer {

    public CoreEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(new CoreInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CoreManagingBodiesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CoreCatalogInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CoreRequirementsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CoreDocsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CoreSummaryConfiguration(this));
    }
}
