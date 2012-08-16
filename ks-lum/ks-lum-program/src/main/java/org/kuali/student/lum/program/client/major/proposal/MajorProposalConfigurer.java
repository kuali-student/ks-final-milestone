package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.AbstractProgramConfigurer;
import org.kuali.student.lum.program.client.major.edit.CatalogInformationEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.CollaboratorsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.LearningObjectivesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ManagingBodiesEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.ProgramRequirementsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.SpecializationsEditConfiguration;
import org.kuali.student.lum.program.client.major.edit.SupportingDocsEditConfiguration;

/** 
 * @author Igor
 */
public class MajorProposalConfigurer extends AbstractProgramConfigurer {

	protected ConfigurationManager proposalSummarySectionConfigurer;
	
    public MajorProposalConfigurer() {

    	programSectionConfigManager = new ConfigurationManager();
		programSectionConfigManager.registerConfiguration(new MajorProposalInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new MajorProposalChangeImpactEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new MajorProposalKeyProgramInfoEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new ManagingBodiesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new SpecializationsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CatalogInformationEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new ProgramRequirementsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new LearningObjectivesEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new CollaboratorsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new SupportingDocsEditConfiguration(this));
        programSectionConfigManager.registerConfiguration(new MajorProposalSummaryConfiguration(this, true));
        
        
        proposalSummarySectionConfigurer = new ConfigurationManager();
        proposalSummarySectionConfigurer.registerConfiguration(new MajorProposalSummaryConfiguration(this, false));
    }
    
    @Override
    public ConfigurationManager getProgramSectionConfigManager() {
    	if (modelDefinition.getMetadata().isCanEdit()) {
    		return programSectionConfigManager;
    	} else {
    		return proposalSummarySectionConfigurer;    	
    	}
    }



}
