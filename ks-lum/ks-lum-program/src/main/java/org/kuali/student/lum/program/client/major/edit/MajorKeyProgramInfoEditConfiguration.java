package org.kuali.student.lum.program.client.major.edit;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSCharCount;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class MajorKeyProgramInfoEditConfiguration extends AbstractSectionConfiguration {

    public MajorKeyProgramInfoEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION), ProgramConstants.PROGRAM_MODEL_ID);
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

    protected VerticalSection createKeyProgramInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS)));
        //KSLAB-2175 - it makes this readOnlySelection box shift under the line drawn by this section heading... Nice  to have JIRA
        VerticalSection s1 = new VerticalSection();
        HorizontalSection s2 = new HorizontalSection();
        
        configurer.addField(s1, ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        configurer.addField(s1, ProgramConstants.CREDENTIAL_PROGRAM_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CREDENTIALPROGRAM));
        configurer.addField(s1, ProgramConstants.PROGRAM_CLASSIFICATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CLASSIFICATION));
        configurer.addField(s1, ProgramConstants.DEGREE_TYPE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_DEGREETYPE));
        s2.addSection(s1);
        s2.addSection(createReadOnlySection());
        section.addSection(s2);
        return section;
    }

    protected VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection();
        section.addStyleName("readOnlySection");
        section.addStyleName("readOnlyNeedsToBeOnTheRight");
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LEVEL));
        return section;
    }
    
    private VerticalSection createProgramTitleSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_PROGRAMTITLE)));


        configurer.addField(section, ProgramConstants.LONG_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEFULL));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLESHORT), new KSCharCount(configurer.getModelDefinition().getMetadata(QueryPath.parse(ProgramConstants.SHORT_TITLE))));
        configurer.addField(section, ProgramConstants.TRANSCRIPT, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLETRANSCRIPT), new KSCharCount(configurer.getModelDefinition().getMetadata(QueryPath.parse(ProgramConstants.TRANSCRIPT))));
        configurer.addField(section, ProgramConstants.DIPLOMA, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEDIPLOMA)).setWidgetBinding(new DiplomaBinding());

        return section;
    }

    private VerticalSection createDatesSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_DATES)));
        //Add this field and hide it so it is available for cross field validation 
        FieldDescriptor fd = configurer.addField(section,ProgramConstants.PROPOSAL_PREV_START_TERM_PATH, generateMessageInfo(ProgramMsgConstants.MAJORDISCIPLINE_PREVSTARTTERM));
        fd.getFieldWidget().setVisible(false);
        fd.hideLabel();
        
        configurer.addField(section, ProgramConstants.START_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_STARTTERM));
        configurer.addField(section, ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ADMITTERM));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM));
        configurer.addField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM));
        return section;
    }

    private VerticalSection createOtherInformationSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_OTHERINFORMATION)));
        configurer.addField(section, ProgramConstants.LOCATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LOCATION));
        Widget cip2000Picker = configureSearch(ProgramConstants.CIP_2000);
        configurer.addField(section, ProgramConstants.CIP_2000, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2000), cip2000Picker);
        Widget cip2010Picker = configureSearch(ProgramConstants.CIP_2010);
        configurer.addField(section, ProgramConstants.CIP_2010, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2010), cip2010Picker);
        configurer.addField(section, ProgramConstants.HEGIS_CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_HEGIS));
        section.addSection(createAccreditingAgenciesSection());
        return section;
    }

    private Section createAccreditingAgenciesSection() {

        Metadata metadata = configurer.getModelDefinition().getMetadata(QueryPath.concat(ProgramConstants.ACCREDITING_AGENCY));
        MultiplicityConfiguration config = new MultiplicityConfiguration(MultiplicityConfiguration.MultiplicityType.GROUP, MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP, metadata);
        config.setAddItemLabel(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ADDACCREDITATION));
        config.setUpdateable(true);
        config.setItemLabel(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACCREDITATION));

        config.setParent(ProgramConstants.ACCREDITING_AGENCY, getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACCREDITATIONS), null, metadata);

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
	
    public class DiplomaBinding extends ModelWidgetBindingSupport<HasText> {
        private boolean isEmpty(String value) {
            return value == null || value.isEmpty();
        }

        @Override
        public void setModelValue(HasText widget, DataModel model, String path) {
            String diplomaTitle = widget.getText();
            if (diplomaTitle != null)
                model.set(QueryPath.concat(null, "/" + ProgramConstants.DIPLOMA), diplomaTitle);
        }

        @Override
        public void setWidgetValue(HasText widget, DataModel model, String path) {
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
