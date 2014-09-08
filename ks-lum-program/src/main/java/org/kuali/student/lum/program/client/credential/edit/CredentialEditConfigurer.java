package org.kuali.student.lum.program.client.credential.edit;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.LearningObjectivesEditConfiguration;

/**
 * @author Igor
 */
public class CredentialEditConfigurer extends AbstractProgramConfigurer {

    public CredentialEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(new CredentialInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CredentialManagingBodiesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CredentialCatalogDetailsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CredentialRequirementsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CredentialDocsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CredentialSummaryConfiguration(this));
    }
}
