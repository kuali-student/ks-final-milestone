package org.kuali.student.lum.program.client.bacc.view;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.bacc.BaccEditableHeader;
import org.kuali.student.lum.program.client.bacc.CredentialManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class BaccInformationViewConfiguration extends AbstractSectionConfiguration {

    public static BaccInformationViewConfiguration create() {
        BaccInformationViewConfiguration instance = new BaccInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID));
        return instance;
    }

    public static BaccInformationViewConfiguration createSpecial() {
        BaccInformationViewConfiguration instance = new BaccInformationViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID, new BaccEditableHeader(ProgramProperties.get().program_menu_sections_programInformation(), ProgramSections.PROGRAM_DETAILS_EDIT)));
        return instance;
    }

    private BaccInformationViewConfiguration(SectionView sectionView) {
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
        section.addSection(createDegreeTypeSection());
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
        configurer.addReadOnlyField(section, ProgramConstants.INSTITUTION + "/" + ProgramConstants.ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        return section;
    }

    private Section createDegreeTypeSection() {

        Metadata metadata = configurer.getModelDefinition().getMetadata(QueryPath.concat(ProgramConstants.DEGREE_TYPE));
        MultiplicityConfiguration config = new MultiplicityConfiguration(MultiplicityConfiguration.MultiplicityType.TABLE, MultiplicityConfiguration.StyleType.BORDERLESS_TABLE, metadata);
        config.setShowHeaders(false);
        config.setUpdateable(false);

        config.setParent(ProgramConstants.DEGREE_TYPE, ProgramProperties.get().programInformation_degreeType(), null, metadata);

        MultiplicitySection section = new MultiplicitySection(config);

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
