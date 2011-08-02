package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class MajorProposalChangeImpactEditConfiguration extends AbstractSectionConfiguration {

   
    public MajorProposalChangeImpactEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_EDIT, ProgramProperties.get().program_menu_sections_proposalChangeImpact(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title("Change Impact"));  //TODO: get title from ProgramProperties
  
        // TODO: Move keys to ProgramConstants
        section = new VerticalSection(SectionTitle.generateH3Title("Change Impact"));  //TODO: get title from ProgramProperties
        configurer.addField(section, "proposal/relatedCourseChanges", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluRelatedCourseChangesType()));        
        //configurer.addField(section, ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluModificationType()));
        configurer.addField(section, "proposal/impactedUnits", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedUnitsType()));        
        configurer.addField(section, "proposal/impactedArticulationTransferPrograms", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedArticulationTransferProgramsType()));
        configurer.addField(section, "proposal/studentTransitionPlans", new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluStudentTransitionPlansType()));
        rootSection.addSection(section);     
         
     }
 
}
