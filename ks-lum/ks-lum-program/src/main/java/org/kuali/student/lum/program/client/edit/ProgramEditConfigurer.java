package org.kuali.student.lum.program.client.edit;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.framework.Configuration;
import org.kuali.student.lum.program.client.framework.ConfigurationManager;

/**
 * @author Igor
 */
public class ProgramEditConfigurer extends AbstractProgramConfigurer {

    public ProgramEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ProgramInformationEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(ManagingBodiesEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(SpecializationsEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(CatalogInformationEditConfiguration.class));
        //programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(ProgramRequirementsViewConfiguration.class));  //TODO change to Edit
        programSectionConfigManager.registerConfiguration(GWT.<Configuration>create(LearningObjectivesEditConfiguration.class));
    }
}
