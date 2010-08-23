package org.kuali.student.lum.program.client.edit;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.framework.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ProgramInformationEditConfiguration extends AbstractSectionConfiguration<ProgramEditConfigurer> {

    public ProgramInformationEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID);
    }

    @Override
    protected void buildLayout() {
        HorizontalSection horizontalSection = new HorizontalSection();
        horizontalSection.addSection(createLeftSection());
        horizontalSection.addSection(createRightSection());
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

    private VerticalSection createRightSection() {
        VerticalSection section = new VerticalSection();
        section.addSection(createReadOnlySection());
        return section;
    }

    private VerticalSection createKeyProgramInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().program_menu_sections_programInformation()));
        configurer.addField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification()));
        // configurer.addField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        return section;
    }

    private VerticalSection createProgramTitleSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_title()));
        configurer.addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        configurer.addField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript()));
        configurer.addField(section, ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_titleDiploma()));
        return section;
    }

    private VerticalSection createDatesSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_dates()));
        configurer.addField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addField(section, ProgramConstants.ADMIT_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_admitTerm()));
        configurer.addField(section, ProgramConstants.ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addField(section, ProgramConstants.END_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        configurer.addField(section, ProgramConstants.APPROVAL_DATE, new MessageKeyInfo(ProgramProperties.get().programInformation_approvalDate()));
        return section;
    }

    private VerticalSection createOtherInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addField(section, ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location()));
        configurer.addField(section, ProgramConstants.CIP_2000, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2000()));
        configurer.addField(section, ProgramConstants.CIP_2010, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2010()));
        return section;
    }

    private VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection();
        // configurer.addReadOnlyField(section, ProgramConstants.INSTITUTION, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        // configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()));
        return section;
    }
}
