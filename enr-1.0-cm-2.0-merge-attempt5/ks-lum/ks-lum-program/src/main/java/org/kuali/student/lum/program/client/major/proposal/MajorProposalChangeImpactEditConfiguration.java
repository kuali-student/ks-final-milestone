package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

/**
 * @author Igor
 */
public class MajorProposalChangeImpactEditConfiguration extends AbstractSectionConfiguration {

   
    public MajorProposalChangeImpactEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_CHANGE_IMPACT_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROPOSALCHANGEIMPACT), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROPOSALCHANGEIMPACT)));
        configurer.addField(section, ProgramConstants.PROPOSAL_RELATED_COURSE_CHANGES_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLURELATEDCOURSECHANGESTYPE));        
        configurer.addField(section, ProgramConstants.PROPOSAL_IMPACTED_UNITS_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUIMPACTEDUNITSTYPE));        
        configurer.addField(section, ProgramConstants.PROPOSAL_IMPACTED_ARTICULATION_TRANSFER_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUIMPACTEDARTICULATIONTRANSFERPROGRAMSTYPE));
        configurer.addField(section, ProgramConstants.PROPOSAL_STUDENT_TRANSITION_PLANS_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUSTUDENTTRANSITIONPLANSTYPE));
        rootSection.addSection(section);     
     }
    
 }
