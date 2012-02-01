package org.kuali.student.lum.program.client.core.view;

import com.google.gwt.user.client.ui.VerticalPanel;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;
import org.kuali.student.lum.program.client.core.CredentialProgramsBinding;

/**
 * @author Igor
 */
public class CoreInformationViewConfiguration extends AbstractSectionConfiguration {

    public static CoreInformationViewConfiguration create(Configurer configurer) {
        return new CoreInformationViewConfiguration(configurer, false);
    }

    public static CoreInformationViewConfiguration createSpecial(Configurer configurer) {
        return new CoreInformationViewConfiguration(configurer, true);
    }

    private CoreInformationViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION);
        if (!isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, 
                title, ProgramConstants.PROGRAM_MODEL_ID);
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, 
                    title, ProgramConstants.PROGRAM_MODEL_ID, new CoreEditableHeader(title, ProgramSections.PROGRAM_DETAILS_EDIT));
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
        rootSection.addSection(section);
    }

    private TableSection createIdentifyingDetailsSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS)));
        configurer.addReadOnlyField(section, ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        addCredentialPrograms(section);
        return section;
    }

    private void addCredentialPrograms(TableSection section) {
        FieldDescriptor fieldDescriptor = configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAMS, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CREDENTIALPROGRAM), new VerticalPanel());
        fieldDescriptor.setWidgetBinding(new CredentialProgramsBinding());
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_PROGRAMTITLE)));
        configurer.addReadOnlyField(section, ProgramConstants.LONG_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEFULL));
        configurer.addReadOnlyField(section, ProgramConstants.SHORT_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLESHORT));
        configurer.addReadOnlyField(section, ProgramConstants.TRANSCRIPT, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLETRANSCRIPT));
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_DATES)));
        //Add this field and hide it so it is available for cross field validation 
        FieldDescriptor fd = configurer.addField(section,ProgramConstants.PREV_START_TERM, generateMessageInfo(ProgramMsgConstants.MAJORDISCIPLINE_PREVSTARTTERM));
        fd.getFieldWidget().setVisible(false);
        fd.hideLabel();

        configurer.addReadOnlyField(section, ProgramConstants.START_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_STARTTERM));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_APPROVAL_DATE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_APPROVALDATE));
        return section;
    }

    public VerticalSection createActivateProgramSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH2Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACTIVATEPROGRAM)));
        section.setInstructions("<br>" + getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACTIVATEINSTRUCTIONS) + "<br><br>");
        configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM));
        configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM));
        return section;
    }

}