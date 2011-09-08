package org.kuali.student.lum.program.client.major.proposal;

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
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().program_menu_sections_proposalChangeImpact()));
        configurer.addField(section, ProgramConstants.PROPOSAL_RELATED_COURSE_CHANGES_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluRelatedCourseChangesType()));        
        configurer.addField(section, ProgramConstants.PROPOSAL_IMPACTED_UNITS_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedUnitsType()));        
        configurer.addField(section, ProgramConstants.PROPOSAL_IMPACTED_ARTICULATION_TRANSFER_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluImpactedArticulationTransferProgramsType()));
        configurer.addField(section, ProgramConstants.PROPOSAL_STUDENT_TRANSITION_PLANS_PATH, new MessageKeyInfo(ProgramProperties.get().proposalInformation_cluStudentTransitionPlansType()));
        rootSection.addSection(section);     
     }
    
 }
