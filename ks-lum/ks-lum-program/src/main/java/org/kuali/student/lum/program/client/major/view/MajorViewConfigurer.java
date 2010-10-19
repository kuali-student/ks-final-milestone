package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;

import com.google.gwt.core.client.GWT;

/**
 * This class represents configuration for  {@link MajorViewController}.
 *
 * @author Igor
 */
public class MajorViewConfigurer extends AbstractProgramConfigurer {

    public MajorViewConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(new MajorInformationViewConfiguration());
        programSectionConfigManager.registerConfiguration(new ManagingBodiesViewConfiguration());
        programSectionConfigManager.registerConfiguration(new SpecializationsViewConfiguration());
        programSectionConfigManager.registerConfiguration(new CatalogInformationViewConfiguration());
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsViewConfiguration());
        programSectionConfigManager.registerConfiguration(new LearningObjectivesViewConfiguration());
        programSectionConfigManager.registerConfiguration(new SupportingDocsViewConfiguration());
        programSectionConfigManager.registerConfiguration(new ViewAllSectionConfiguration());
    }
}
