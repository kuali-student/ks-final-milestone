package org.kuali.student.lum.program.client.view;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ProgramInformationViewConfiguration extends AbstractSectionConfiguration {

    public ProgramInformationViewConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID);
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
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification()));
        configurer.addReadOnlyField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        return section;
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_programTitle()));
        configurer.addReadOnlyField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addReadOnlyField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        configurer.addReadOnlyField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript()));
        configurer.addReadOnlyField(section, ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_titleDiploma()));
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_dates()));
        configurer.addReadOnlyField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_admitTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_APPROVAL_DATE, new MessageKeyInfo(ProgramProperties.get().programInformation_approvalDate()));
        return section;
    }

    private TableSection createOtherInformationSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addReadOnlyField(section, ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location()));
        // configurer.addReadOnlyField(section, ProgramConstants.ACCREDITING_AGENCY, new MessageKeyInfo(ProgramProperties.get().programInformation_accreditation()));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2000, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2000()));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2010, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2010()));
        configurer.addReadOnlyField(section, ProgramConstants.HEGIS_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_hegis()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.INSTITUTION + "/" + ProgramConstants.ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        return section;
    }
}
