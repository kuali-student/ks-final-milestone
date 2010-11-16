package org.kuali.student.lum.program.client.core.view;

import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.CredentialProgramsBinding;

/**
 * @author Igor
 */
public class CoreInformationViewConfiguration extends AbstractSectionConfiguration {

    public static CoreInformationViewConfiguration create() {
        CoreInformationViewConfiguration instance = new CoreInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID));
        return instance;
    }

    public static CoreInformationViewConfiguration createSpecial() {
        CoreInformationViewConfiguration instance = new CoreInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID, new CoreEditableHeader(ProgramProperties.get().program_menu_sections_programInformation(), ProgramSections.PROGRAM_DETAILS_EDIT)));
        return instance;
    }

    private CoreInformationViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
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
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_identifyingDetails()));
        configurer.addReadOnlyField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        addCredentialPrograms(section);
        return section;
    }

    private void addCredentialPrograms(TableSection section) {
        FieldDescriptor fieldDescriptor = configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAMS, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()), new VerticalPanel());
        fieldDescriptor.setWidgetBinding(new CredentialProgramsBinding());
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_programTitle()));
        configurer.addReadOnlyField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addReadOnlyField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        configurer.addReadOnlyField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript()));
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_dates()));
        configurer.addReadOnlyField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_APPROVAL_DATE, new MessageKeyInfo(ProgramProperties.get().programInformation_approvalDate()));
        return section;
    }

    public VerticalSection createActivateProgramSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH2Title(ProgramProperties.get().programInformation_activateProgram()));
        section.setInstructions("<br>" + ProgramProperties.get().programInformation_activateInstructions() + "<br><br>");
        configurer.addField(section, ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addField(section, ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        return section;
    }

}