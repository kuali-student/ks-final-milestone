package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.major.edit.MajorKeyProgramInfoEditConfiguration;

public class MajorProposalKeyProgramInfoEditConfiguration extends MajorKeyProgramInfoEditConfiguration {

    
	public MajorProposalKeyProgramInfoEditConfiguration(Configurer configurer) {
        super(configurer);
    }

    /**
	 * Override to configuration so credential program field is editable. 
	 */
	@Override
	protected VerticalSection createKeyProgramInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS)));
        //KSLAB-2175 - it makes this readOnlySelection box shift under the line drawn by this section heading... Nice  to have JIRA
        VerticalSection s1 = new VerticalSection();
        HorizontalSection s2 = new HorizontalSection();
        
        configurer.addField(s1, ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        configurer.addField(s1, ProgramConstants.PROGRAM_CLASSIFICATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CLASSIFICATION));
        configurer.addField(s1, ProgramConstants.DEGREE_TYPE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_DEGREETYPE));
        s2.addSection(s1);
        s2.addSection(createReadOnlySection());
        section.addSection(s2);
        return section;
    }	
	
    protected VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection();
        section.addStyleName("readOnlySection");
        section.addStyleName("readOnlyNeedsToBeOnTheRight");
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CREDENTIALPROGRAM));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LEVEL));
        return section;
    }

}
