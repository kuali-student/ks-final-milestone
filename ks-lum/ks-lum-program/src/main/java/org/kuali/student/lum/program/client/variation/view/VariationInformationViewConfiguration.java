package org.kuali.student.lum.program.client.variation.view;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;

/**
 * @author Igor
 */
public class VariationInformationViewConfiguration extends AbstractSectionConfiguration {

    public static VariationInformationViewConfiguration create(Configurer configurer) {
        return new VariationInformationViewConfiguration(configurer, false);
    }

    public static VariationInformationViewConfiguration createSpecial(Configurer configurer) {
        return new VariationInformationViewConfiguration(configurer, true);
    }

    private VariationInformationViewConfiguration(Configurer configurer, boolean isSpecial) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION);
        if (!isSpecial){
            this.rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, 
                    title, ProgramConstants.PROGRAM_MODEL_ID);
        } else {
            this.rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, 
                    title, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, ProgramSections.PROGRAM_DETAILS_EDIT));
        }
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
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS)));
        configurer.addReadOnlyField(section, ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_RUNTIME_PROGRAM_LEVEL, generateMessageInfo(
                ProgramMsgConstants.PROGRAMINFORMATION_LEVEL));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CREDENTIALPROGRAM));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_CLASSIFICATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CLASSIFICATION));
        configurer.addReadOnlyField(section, ProgramConstants.DEGREE_TYPE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_DEGREETYPE));
        return section;
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_PROGRAMTITLE)));
        configurer.addReadOnlyField(section, ProgramConstants.LONG_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEFULL));
        configurer.addReadOnlyField(section, ProgramConstants.SHORT_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLESHORT));
        configurer.addReadOnlyField(section, ProgramConstants.TRANSCRIPT, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLETRANSCRIPT));
        configurer.addReadOnlyField(section, ProgramConstants.DIPLOMA, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEDIPLOMA));
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_DATES)));
        configurer.addReadOnlyField(section, ProgramConstants.START_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_STARTTERM));
        configurer.addReadOnlyField(section, ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ADMITTERM));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_APPROVAL_DATE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_APPROVALDATE));
        return section;
    }

    private TableSection createOtherInformationSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_OTHERINFORMATION)));
        configurer.addReadOnlyField(section, ProgramConstants.LOCATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LOCATION));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2000, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2000));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2010, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2010));
        configurer.addReadOnlyField(section, ProgramConstants.HEGIS_CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_HEGIS));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION));
        return section;
    }

}
