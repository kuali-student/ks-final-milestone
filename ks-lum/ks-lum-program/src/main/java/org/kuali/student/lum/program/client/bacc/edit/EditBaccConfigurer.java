package org.kuali.student.lum.program.client.bacc.edit;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.edit.SupportingDocsEditConfiguration;

/**
 * @author Igor
 */
public class EditBaccConfigurer extends AbstractProgramConfigurer {

    public EditBaccConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccInformationEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccManagingBodiesEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccCatalogDetailsEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccRequirementsEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccLearningObjectivesEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SupportingDocsEditConfiguration.class));
    }
}
