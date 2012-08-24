package org.kuali.student.lum.program.client.variation.edit;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.CatalogInformationEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.LearningObjectivesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ManagingBodiesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.SupportingDocsEditConfiguration;

/**
 * @author Igor
 */
public class VariationEditConfigurer extends AbstractProgramConfigurer {

    public VariationEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager();
        programSectionConfigManager.registerConfiguration(new VariationInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new ManagingBodiesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CatalogInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new SupportingDocsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new VariationSummaryConfiguration(this));
    }
}