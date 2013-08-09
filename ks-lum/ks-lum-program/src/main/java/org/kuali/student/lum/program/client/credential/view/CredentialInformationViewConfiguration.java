package org.kuali.student.lum.program.client.credential.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;

/**
 * @author Igor
 */
public class CredentialInformationViewConfiguration extends AbstractSectionConfiguration {

    public static CredentialInformationViewConfiguration create(Configurer configurer) {
        return new CredentialInformationViewConfiguration(configurer, false);
    }

    public static CredentialInformationViewConfiguration createSpecial(Configurer configurer) {
        return new CredentialInformationViewConfiguration(configurer, true);
    }

    private CredentialInformationViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION);
        if (!isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, 
                title, ProgramConstants.PROGRAM_MODEL_ID);
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, title,
                    ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(title, ProgramSections.PROGRAM_DETAILS_EDIT));
        }
        rootSection.addStyleName("programInformationView");
    }

    @Override
    protected void buildLayout() {
        HorizontalSection section = new HorizontalSection();
        section.addSection(createIdentifyingDetailsSection());
        section.addSection(createProgramTitleSection());
        section.nextRow();
        section.addSection(createDatesSection());
        section.addSection(createOtherInformationSection());
        rootSection.addSection(section);
    }

    private TableSection createIdentifyingDetailsSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS)));
        configurer.addReadOnlyField(section, ProgramConstants.CODE,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_LEVEL,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_LEVEL));
        configurer.addReadOnlyField(section, ProgramConstants.DEGREE_TYPE,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_DEGREETYPE));
        return section;
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_PROGRAMTITLE)));
        configurer.addReadOnlyField(section, ProgramConstants.LONG_TITLE,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_TITLEFULL));
        configurer.addReadOnlyField(section, ProgramConstants.SHORT_TITLE,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_TITLESHORT));
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_DATES)));
        // Add this field and hide it so it is available for cross field validation
        FieldDescriptor fd = configurer.addField(section, ProgramConstants.PREV_START_TERM,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.MAJORDISCIPLINE_PREVSTARTTERM));
        fd.getFieldWidget().setVisible(false);
        fd.hideLabel();

        configurer.addReadOnlyField(section, ProgramConstants.START_TERM,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_STARTTERM));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM));
        return section;
    }

    private TableSection createOtherInformationSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_OTHERINFORMATION)));
        configurer.addReadOnlyField(section, ProgramConstants.INSTITUTION + "/" + ProgramConstants.ORG_ID,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION));
        return section;
    }

    public VerticalSection createActivateProgramSection() {
        final VerticalSection section = new VerticalSection(SectionTitle.generateH2Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACTIVATEPROGRAM)));
        section.setInstructions("<br>" + getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACTIVATEINSTRUCTIONS) + "<br><br>");
        configurer.addField(section, "proposal/" + ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM));
        configurer.addField(section, "proposal/" + ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM,
                new MessageKeyInfo(ProgramMsgConstants.PROGRAM_MSG_GROUP, "course", ProgramMsgConstants.STATUS_ACTIVE, ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM));
        return section;
    }
}
