package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ProgramInformationViewConfiguration extends AbstractConfiguration<ProgramViewConfigurer> {

    private VerticalSectionView showViewSection;

    @Override
    public View getView() {
        createShowView();
        return showViewSection;
    }

    private void createEditView() {
        /*  editView = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, ProgramProperties.get().program_menu_sections_programDetails(), ProgramConstants.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        configurer.addField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_programCode()));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_shortTitle()));
        configurer.addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_longTitle()));
        configurer.addField(section, ProgramConstants.PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_programLevel()));
        configurer.addField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_programClassification()));
        configurer.addField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        configurer.addField(section, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().programInformation_fullPartTime()));
        configurer.addField(section, ProgramConstants.DURATION, new MessageKeyInfo(ProgramProperties.get().programInformation_duration()));
        configurer.addField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_programTranscript()));
        configurer.addField(section, ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_programDiploma()));
        configurer.addField(section, ProgramConstants.CIP_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_cipCode()));
        configurer.addField(section, ProgramConstants.HEGIS_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_hegisCode()));
        configurer.addField(section, ProgramConstants.CREDENTIAL_PROGRAM, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()));
        configurer.addField(section, ProgramConstants.SPECIALIZATION_REQUIRED, new MessageKeyInfo(ProgramProperties.get().programInformation_specializationRequired()));
        configurer.addField(section, ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location()));
        configurer.addField(section, ProgramConstants.ACCREDITING_AGENCY, new MessageKeyInfo(ProgramProperties.get().programInformation_accreditingAgency()));
        configurer.addField(section, ProgramConstants.INSTITUTION, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        configurer.addField(section, ProgramConstants.DESCRIPTION, new MessageKeyInfo(ProgramProperties.get().programInformation_programDescription()));
        configurer.addField(section, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().programInformation_moreInformation()));
        editView.addSection(section);*/
    }

    private void createShowView() {
        showViewSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        TableSection identifyingDetailsSection = createIdentifyingDetailsSection();
        TableSection programTitleSection = createProgramTitleSection();
        TableSection datesSection = createDatesSection();
        TableSection otherInformationSection = createOtherInformationSection();
        section.addSection(identifyingDetailsSection);
        section.addSection(programTitleSection);
        section.nextRow();
        section.addSection(datesSection);
        section.addSection(otherInformationSection);
        showViewSection.addSection(section);
    }

    private TableSection createIdentifyingDetailsSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_identifyingDetails()));
        configurer.addField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()), new KSLabel());
        configurer.addField(section, ProgramConstants.PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()), new KSLabel());
        configurer.addField(section, ProgramConstants.CREDENTIAL_PROGRAM, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()), new KSLabel());
        configurer.addField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification()), new KSLabel());
        configurer.addField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()), new KSLabel());
        return section;
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_programTitle()));
        configurer.addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()), new KSLabel());
        configurer.addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()), new KSLabel());
        configurer.addField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript()), new KSLabel());
        configurer.addField(section, ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_titleDiploma()), new KSLabel());
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_dates()));
        configurer.addReadOnlyField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.ADMIT_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_admitTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.APPROVAL_DATE, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        return section;
    }

    private TableSection createOtherInformationSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addReadOnlyField(section, ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location()));
        //configurer.addReadOnlyField(section, ProgramConstants.ACCREDITING_AGENCY, new MessageKeyInfo(ProgramProperties.get().programInformation_accreditation()));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2000, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2000()));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2010, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2010()));
        configurer.addReadOnlyField(section, ProgramConstants.HEGIS_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_hegis()));
        configurer.addReadOnlyField(section, ProgramConstants.INSTITUTION, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        return section;
    }
}
