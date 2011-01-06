package org.kuali.student.lum.program.client.variation.edit;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.*;

/**
 * @author Igor
 */
public class VariationEditConfigurer extends AbstractProgramConfigurer {

private int row;

 public int getRow () {
  return row;
 }

 public void setRow (int row) {
  this.row = row;
 }


    public VariationEditConfigurer() {
        programSectionConfigManager = new ConfigurationManager(this);
        programSectionConfigManager.registerConfiguration(new VariationInformationEditConfiguration());
        programSectionConfigManager.registerConfiguration(new ManagingBodiesEditConfiguration("variations" + "/" + row));
        programSectionConfigManager.registerConfiguration(new CatalogInformationEditConfiguration("variations" + "/" + row));
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration());
        programSectionConfigManager.registerConfiguration(new SupportingDocsEditConfiguration());
        programSectionConfigManager.registerConfiguration(new VariationSummaryConfiguration());
    }
}