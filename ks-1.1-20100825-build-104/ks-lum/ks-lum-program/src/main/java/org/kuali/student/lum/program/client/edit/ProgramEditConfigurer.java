package org.kuali.student.lum.program.client.edit;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.framework.Configuration;
import org.kuali.student.lum.program.client.framework.ConfigurationManager;
import org.kuali.student.lum.program.client.framework.ConfigurationRegistry;

/**
 * @author Igor
 */
public class ProgramEditConfigurer extends AbstractProgramConfigurer<ProgramEditConfigurer> {

    public ProgramEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager<ProgramEditConfigurer>(this);
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(ProgramInformationEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(ManagingBodiesEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(SpecializationsEditConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(CatalogInformationEditConfiguration.class));
        //programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(ProgramRequirementsViewConfiguration.class));  //TODO change to Edit
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(LearningObjectivesEditConfiguration.class));
        ConfigurationRegistry.register(this.getClass().getName(), programSectionConfigManager);
    }
}
