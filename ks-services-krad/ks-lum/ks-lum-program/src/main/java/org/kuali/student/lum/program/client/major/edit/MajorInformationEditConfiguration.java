package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class MajorInformationEditConfiguration extends AbstractSectionConfiguration {

    public MajorInformationEditConfiguration() {
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
        section.addStyleName("readOnlySection");
        section.addSection(createReadOnlySection());
        return section;
    }

    private VerticalSection createKeyProgramInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_identifyingDetails()));
        configurer.addField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification()));
        configurer.addField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        return section;
    }

    private VerticalSection createProgramTitleSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_programTitle()));
        configurer.addField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        configurer.addField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript()));
        configurer.addField(section, ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_titleDiploma())).setWidgetBinding(new DiplomaBinding());
        return section;
    }

    private VerticalSection createDatesSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_dates()));
        configurer.addField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addField(section, ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_admitTerm()));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        return section;
    }

    private VerticalSection createOtherInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addField(section, ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location()));
        Widget cip2000Picker = configureSearch(ProgramConstants.CIP_2000);
        configurer.addField(section, ProgramConstants.CIP_2000, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2000()), cip2000Picker);
        Widget cip2010Picker = configureSearch(ProgramConstants.CIP_2010);
        configurer.addField(section, ProgramConstants.CIP_2010, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2010()), cip2010Picker);
        configurer.addField(section, ProgramConstants.HEGIS_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_hegis()));
        section.addSection(createAccreditingAgenciesSection());
        return section;
    }

    private VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()));
        return section;
    }

    private Section createAccreditingAgenciesSection() {

        Metadata metadata = configurer.getModelDefinition().getMetadata(QueryPath.concat(ProgramConstants.ACCREDITING_AGENCY));
        MultiplicityConfiguration config = new MultiplicityConfiguration(MultiplicityConfiguration.MultiplicityType.GROUP, MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP, metadata);
        config.setAddItemLabel(ProgramProperties.get().programInformation_addAccreditation());
        config.setUpdateable(true);
        config.setItemLabel(ProgramProperties.get().programInformation_accreditation());

        config.setParent(ProgramConstants.ACCREDITING_AGENCY, ProgramProperties.get().programInformation_accreditations(), null, metadata);

        Metadata orgMetadata = configurer.getModelDefinition().getMetadata(QueryPath.concat(ProgramConstants.ACCREDITING_AGENCY, QueryPath.getWildCard(), ProgramConstants.ORG_ID));
        config.addField(ProgramConstants.ORG_ID, null, ProgramConstants.ACCREDITING_AGENCY, orgMetadata);

        MultiplicitySection section = new MultiplicitySection(config);

        return section;
    }

	private Widget configureSearch(String fieldKey) {	    
		Widget searchWidget;
		QueryPath path = QueryPath.concat(null, fieldKey);
		Metadata meta = configurer.getModelDefinition().getMetadata(path);
	        
		searchWidget = new KSPicker(meta.getInitialLookup(), meta.getAdditionalLookups());
		SearchPanel panel = ((KSPicker) searchWidget).getSearchPanel();
        if (panel != null) {
            panel.setMutipleSelect(false);
        }
        
		return searchWidget;
	}
	
    public class DiplomaBinding extends ModelWidgetBindingSupport<KSTextBox> {
        private boolean isEmpty(String value) {
            return value == null || value.isEmpty();
        }

        @Override
        public void setModelValue(KSTextBox widget, DataModel model, String path) {
            String diplomaTitle = widget.getText();
            if (diplomaTitle != null)
                model.set(QueryPath.concat(null, "/" + ProgramConstants.DIPLOMA), diplomaTitle);
        }

        @Override
        public void setWidgetValue(KSTextBox widget, DataModel model, String path) {
            String diplomaTitle = model.get("/" + ProgramConstants.DIPLOMA);
            if (isEmpty(diplomaTitle)) {
                String programTitle = model.get("/" + ProgramConstants.LONG_TITLE);
                if (!isEmpty(programTitle)){
                    widget.setText(programTitle);
                }else{
                    widget.setText("");
                }
            } else
                widget.setText(diplomaTitle);
        }
    }
}
