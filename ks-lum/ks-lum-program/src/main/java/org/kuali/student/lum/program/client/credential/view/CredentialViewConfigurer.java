package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;

/**
 * @author Igor
 */
public class CredentialViewConfigurer extends AbstractProgramConfigurer {

    public CredentialViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(CredentialInformationViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(CredentialManagingBodiesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(CredentialCatalogDetailsViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new CredentialRequirementsViewConfiguration(this, false));
        programSectionConfigManager.registerConfiguration(CredentialLearningObjectivesViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create(this));
        programSectionConfigManager.registerConfiguration(new CredentialViewAllSectionsConfiguration(this));
    }
}
