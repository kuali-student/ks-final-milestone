package org.kuali.student.lum.program.client.edit;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.configuration.base.Configuration;
import org.kuali.student.lum.program.client.configuration.base.ConfigurationManager;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;

/**
 * @author Igor
 */
public class ProgramEditConfigurer extends AbstractProgramConfigurer<ProgramEditConfigurer> {

    public ProgramEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager<ProgramEditConfigurer>(this);
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<ProgramEditConfigurer>>create(ProgramInformationEditConfiguration.class));
        //programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(SpecializationsConfiguration.class));
        //programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(RequirementsConfiguration.class));
        //programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(ManagingBodiesConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<ProgramEditConfigurer>>create(CatalogInformationEditConfiguration.class));
        //programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramEditConfigurer>>create(LearningObjectivesConfiguration.class));
    }
}
