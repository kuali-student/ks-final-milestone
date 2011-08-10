package org.kuali.student.lum.program.client.major.proposal;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.major.edit.MajorKeyProgramInfoEditConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

public class MajorProposalKeyProgramInfoEditConfiguration extends MajorKeyProgramInfoEditConfiguration {

    
	/**
	 * Override to configuration so institution, credential program, and level fields are not 
	 * editable and contained in read only gray box.
	 */
	@Override
	protected VerticalSection createKeyProgramInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_identifyingDetails()));
        //KSLAB-2175 - it makes this readOnlySelection box shift under the line drawn by this section heading... Nice  to have JIRA
        VerticalSection s1 = new VerticalSection();
        HorizontalSection s2 = new HorizontalSection();
        
        configurer.addField(s1, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addField(s1, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification()));
        configurer.addField(s1, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        s2.addSection(s1);
        s2.addSection(createReadOnlySection());
        section.addSection(s2);
        return section;
    }	
	
    protected VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection();
        section.addStyleName("readOnlySection");
        section.addStyleName("readOnlyNeedsToBeOnTheRight");
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()));
        return section;
    }

}
