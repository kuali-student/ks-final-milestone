package org.kuali.student.lum.program.client.credential.edit;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class CredentialInformationEditConfiguration extends AbstractSectionConfiguration {

    public CredentialInformationEditConfiguration() {
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID);
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
        configurer.addField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        return section;
    }

    private VerticalSection createProgramTitleSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        return section;
    }

    private VerticalSection createDatesSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_dates()));
        configurer.addField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        return section;
    }

    private VerticalSection createOtherInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addField(section, ProgramConstants.INSTITUTION + "/" + ProgramConstants.ORG_ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        return section;
    }
}
