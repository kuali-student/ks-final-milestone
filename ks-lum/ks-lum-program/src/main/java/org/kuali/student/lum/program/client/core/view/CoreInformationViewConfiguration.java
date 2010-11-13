package org.kuali.student.lum.program.client.core.view;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.core.CoreEditableHeader;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

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
        section.addSection(createOtherInformationSection());
        rootSection.addSection(section);
    }

    private TableSection createIdentifyingDetailsSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_identifyingDetails()));
        configurer.addReadOnlyField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()));
        return section;
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

    private TableSection createOtherInformationSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.INSTITUTION + "/" + ProgramConstants.ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        return section;
    }
}