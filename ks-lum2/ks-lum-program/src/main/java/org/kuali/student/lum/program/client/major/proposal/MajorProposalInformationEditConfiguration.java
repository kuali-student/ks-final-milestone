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
public class MajorProposalInformationEditConfiguration extends AbstractSectionConfiguration {

   
    public MajorProposalInformationEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_PROPOSAL_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROPOSALINFORMATION), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title("Proposal Information"));  //TODO: get title from ProgramProperties
        configurer.addField(section, ProgramConstants.PROPOSAL_TITLE_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUPROGRAMTITLE));        
        configurer.addField(section, ProgramConstants.PROPOSAL_TYPE_OF_MODIFICATON_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUMODIFICATIONTYPE));        
        configurer.addField(section, ProgramConstants.PROPOSAL_ABSTRACT_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUABSTRACTTYPE));
        configurer.addField(section, ProgramConstants.PROPOSAL_RATIONALE_PATH, generateMessageInfo(ProgramMsgConstants.PROPOSALINFORMATION_CLUPROPOSALRATIONALE));
        rootSection.addSection(section);     
     }
 
}
