package org.kuali.student.lum.program.client.bacc.view;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.view.*;

/**
 * @author Igor
 */
public class ViewBaccConfigurer extends AbstractProgramConfigurer {

    public ViewBaccConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccManagingBodiesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccCatalogDetailsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccRequirementsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccLearningObjectivesViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SupportingDocsViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(BaccViewAllSectionsConfiguration.class));
    }
}
