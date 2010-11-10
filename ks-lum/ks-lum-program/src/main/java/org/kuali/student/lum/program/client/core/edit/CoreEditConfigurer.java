package org.kuali.student.lum.program.client.core.edit;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;

/**
 * @author Igor
 */
public class CoreEditConfigurer extends AbstractProgramConfigurer {

    public CoreEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(new CoreInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreManagingBodiesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreCatalogInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreRequirementsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreLeaningObjectivesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new CoreSummaryConfiguration());
    }
}
