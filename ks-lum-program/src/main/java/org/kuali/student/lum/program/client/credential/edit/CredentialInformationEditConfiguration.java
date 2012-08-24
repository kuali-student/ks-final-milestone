package org.kuali.student.lum.program.client.credential.edit;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

/**
 * @author Igor
 */
public class CredentialInformationEditConfiguration extends AbstractSectionConfiguration {

    public CredentialInformationEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION), 
                ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        HorizontalSection horizontalSection = new HorizontalSection();
        horizontalSection.addSection(createLeftSection());
        rootSection.addSection(horizontalSection);
    }

    private VerticalSection createLeftSection() {
        VerticalSection section = new VerticalSection();
        section.addSection(createKeyProgramInformationSection());
        section.addSection(createProgramTitleSection());
        section.addSection(createDatesSection());
        section.addSection(createOtherInformationSection());
        return section;
    }

    private VerticalSection createKeyProgramInformationSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        configurer.addField(section, ProgramConstants.PROGRAM_LEVEL, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LEVEL));
        configurer.addField(section, ProgramConstants.DEGREE_TYPE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_DEGREETYPE));
        return section;
    }

    private VerticalSection createProgramTitleSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.LONG_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEFULL));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLESHORT));
        return section;
    }

    private VerticalSection createDatesSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_DATES)));
        //Add this field and hide it so it is available for cross field validation 
        FieldDescriptor fd = configurer.addField(section,ProgramConstants.PROPOSAL_PREV_START_TERM_PATH, generateMessageInfo(ProgramMsgConstants.MAJORDISCIPLINE_PREVSTARTTERM));
        fd.getFieldWidget().setVisible(false);
        fd.hideLabel();
        configurer.addField(section, ProgramConstants.START_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_STARTTERM));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM));
        return section;
    }

    private VerticalSection createOtherInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_OTHERINFORMATION)));
        configurer.addField(section, ProgramConstants.INSTITUTION + "/" + ProgramConstants.ORG_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION));
        return section;
    }
}
