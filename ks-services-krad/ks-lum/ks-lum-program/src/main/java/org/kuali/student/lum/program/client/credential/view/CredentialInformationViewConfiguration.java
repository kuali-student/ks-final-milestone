package org.kuali.student.lum.program.client.credential.view;

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
import org.kuali.student.lum.program.client.credential.CredentialEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CredentialInformationViewConfiguration extends AbstractSectionConfiguration {

    public static CredentialInformationViewConfiguration create() {
        CredentialInformationViewConfiguration instance = new CredentialInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID));
        return instance;
    }

    public static CredentialInformationViewConfiguration createSpecial() {
        CredentialInformationViewConfiguration instance = new CredentialInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID, new CredentialEditableHeader(ProgramProperties.get().program_menu_sections_programInformation(), ProgramSections.PROGRAM_DETAILS_EDIT)));
        return instance;
    }

    private CredentialInformationViewConfiguration(SectionView sectionView) {
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
        section.addSection(createOtherInformationSection());
        rootSection.addSection(section);
    }

    private TableSection createIdentifyingDetailsSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_identifyingDetails()));
        configurer.addReadOnlyField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()));
        configurer.addReadOnlyField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        return section;
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_programTitle()));
        configurer.addReadOnlyField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addReadOnlyField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_dates()));
        configurer.addReadOnlyField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        return section;
    }

    private TableSection createOtherInformationSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addReadOnlyField(section, ProgramConstants.INSTITUTION + "/" + ProgramConstants.ORG_ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        return section;
    }

    public VerticalSection createActivateProgramSection(){
        VerticalSection section = new VerticalSection(SectionTitle.generateH2Title(ProgramProperties.get().programInformation_activateProgram()));
        section.setInstructions("<br>" + ProgramProperties.get().programInformation_activateInstructions() + "<br><br>");
        configurer.addField(section, ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addField(section, ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        return section;
    }    
}
