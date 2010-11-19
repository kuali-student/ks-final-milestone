package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;

/**
 * @author Igor
 */
public class CredentialViewConfigurer extends AbstractProgramConfigurer {

    public CredentialViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(CredentialInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(CredentialManagingBodiesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(CredentialCatalogDetailsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new CredentialRequirementsViewConfiguration());
        programSectionConfigManager.registerConfiguration(CredentialLearningObjectivesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new CredentialViewAllSectionsConfiguration());
    }
}
