package org.kuali.student.lum.program.client.core.edit;

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
public class CoreInformationEditConfiguration  extends AbstractSectionConfiguration {

    public CoreInformationEditConfiguration() {
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
        return section;
    }

    private VerticalSection createRightSection() {
        VerticalSection section = new VerticalSection();
        section.addStyleName("readOnlySection");
        section.addSection(createReadOnlySection());
        return section;
    }

    private VerticalSection createKeyProgramInformationSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification()));
        return section;
    }

    private VerticalSection createProgramTitleSection() {
        VerticalSection section = new VerticalSection();
        configurer.addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        configurer.addField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript()));
        return section;
    }

    private VerticalSection createDatesSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_dates()));
        configurer.addField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        return section;
    }

    private VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.INSTITUTION + "/" + ProgramConstants.ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()));
        return section;
    }
}