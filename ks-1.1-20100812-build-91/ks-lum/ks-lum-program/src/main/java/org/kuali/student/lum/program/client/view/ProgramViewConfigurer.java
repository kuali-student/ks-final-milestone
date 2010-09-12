package org.kuali.student.lum.program.client.view;

import com.google.gwt.core.client.GWT;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.ProgramController;
import org.kuali.student.lum.program.client.configuration.*;
import org.kuali.student.lum.program.client.configuration.base.Configuration;
import org.kuali.student.lum.program.client.configuration.base.ConfigurationManager;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;

/**
 * This class represents configuration for  {@link org.kuali.student.lum.program.client.view.ProgramViewController}.
 *
 * @author Igor
 */
public class ProgramViewConfigurer extends AbstractProgramConfigurer<ProgramViewConfigurer> {

    public ProgramViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager<ProgramViewConfigurer>(this);
        programSectionConfigManager.registerConfiguration(GWT.<EditableConfiguration<ProgramViewConfigurer>>create(ProgramInformationViewConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(SpecializationsConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(RequirementsConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(ManagingBodiesConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(CatalogInformationConfiguration.class));
        programSectionConfigManager.registerConfiguration(GWT.<Configuration<ProgramViewConfigurer>>create(LearningObjectivesConfiguration.class));
    }
}
