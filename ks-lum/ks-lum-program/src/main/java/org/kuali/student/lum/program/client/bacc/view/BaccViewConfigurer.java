package org.kuali.student.lum.program.client.bacc.view;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.SupportingDocsViewConfiguration;

/**
 * @author Igor
 */
public class BaccViewConfigurer extends AbstractProgramConfigurer {

    public BaccViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(BaccInformationViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(BaccManagingBodiesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(BaccCatalogDetailsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new BaccRequirementsViewConfiguration());
        programSectionConfigManager.registerConfiguration(BaccLearningObjectivesViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(SupportingDocsViewConfiguration.create());
        programSectionConfigManager.registerConfiguration(new BaccViewAllSectionsConfiguration());
    }
}
