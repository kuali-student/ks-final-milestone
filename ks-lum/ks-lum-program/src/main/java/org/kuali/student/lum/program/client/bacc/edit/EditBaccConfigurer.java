package org.kuali.student.lum.program.client.bacc.edit;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.SupportingDocsEditConfiguration;

/**
 * @author Igor
 */
public class EditBaccConfigurer extends AbstractProgramConfigurer {

    public EditBaccConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(new BaccInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new BaccManagingBodiesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new BaccCatalogDetailsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new BaccRequirementsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new BaccLearningObjectivesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new SupportingDocsEditConfiguration());
    }
}
