package org.kuali.student.lum.program.client.configuration;

import com.google.gwt.user.client.ui.HTML;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.program.client.ProgramConfigurer;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.configuration.base.AbstractConfiguration;
import org.kuali.student.lum.program.client.configuration.base.EditableConfiguration;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class ProgramInformationConfiguration extends AbstractConfiguration<ProgramConfigurer> implements EditableConfiguration<ProgramConfigurer> {

    private VerticalSectionView showView;

    private VerticalSectionView editView;

    public ProgramInformationConfiguration() {
    }

    @Override
    public View getEditView() {
        if (editView == null) {
            createEditView();
        }
        return editView;
    }

    @Override
    public View getView() {
        if (showView == null) {
            createShowView();
        }
        return showView;
    }

    private void createEditView() {
        editView = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, ProgramProperties.get().program_menu_sections_programDetails(), ProgramConstants.PROGRAM_MODEL_ID);
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
        editView.addSection(section);
    }

    private void createShowView() {
        showView = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programDetails(), ProgramConstants.PROGRAM_MODEL_ID);
        HorizontalSection section = new HorizontalSection();
        configurer.addField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_programCode()), new KSLabel());
        configurer.addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_shortTitle()), new KSLabel());
        configurer.addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_longTitle()), new KSLabel());
        configurer.addField(section, ProgramConstants.PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_programLevel()), new KSLabel());
        configurer.addField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_programClassification()), new KSLabel());
        configurer.addField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()), new KSLabel());
        configurer.addField(section, ProgramConstants.FULL_PART_TIME, new MessageKeyInfo(ProgramProperties.get().programInformation_fullPartTime()), new KSLabel());
        configurer.addField(section, ProgramConstants.DURATION, new MessageKeyInfo(ProgramProperties.get().programInformation_duration()), new KSLabel());
        configurer.addField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_programTranscript()), new KSLabel());
        configurer.addField(section, ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_programDiploma()), new KSLabel());
        configurer.addField(section, ProgramConstants.CIP_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_cipCode()), new KSLabel());
        configurer.addField(section, ProgramConstants.HEGIS_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_hegisCode()), new KSLabel());
        configurer.addField(section, ProgramConstants.CREDENTIAL_PROGRAM, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()), new KSLabel());
        configurer.addField(section, ProgramConstants.SPECIALIZATION_REQUIRED, new MessageKeyInfo(ProgramProperties.get().programInformation_specializationRequired()), new KSLabel());
        configurer.addField(section, ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location()), new KSLabel());
        configurer.addField(section, ProgramConstants.ACCREDITING_AGENCY, new MessageKeyInfo(ProgramProperties.get().programInformation_accreditingAgency()), new KSLabel());
        configurer.addField(section, ProgramConstants.INSTITUTION, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()), new KSLabel());
        configurer.addField(section, ProgramConstants.DESCRIPTION, new MessageKeyInfo(ProgramProperties.get().programInformation_programDescription()), new HTML());
        configurer.addField(section, ProgramConstants.MORE_INFORMATION, new MessageKeyInfo(ProgramProperties.get().programInformation_moreInformation()), new KSLabel());
        showView.addSection(section);
    }
}
