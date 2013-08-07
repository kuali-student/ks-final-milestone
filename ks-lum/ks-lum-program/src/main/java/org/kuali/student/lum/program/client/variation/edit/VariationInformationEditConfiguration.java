package org.kuali.student.lum.program.client.variation.edit;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class VariationInformationEditConfiguration extends AbstractSectionConfiguration {

    public VariationInformationEditConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_EDIT, getLabel(
                ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION), ProgramConstants.PROGRAM_MODEL_ID);
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
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS)));
        configurer.addField(section, ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        configurer.addField(section, ProgramConstants.PROGRAM_CLASSIFICATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CLASSIFICATION));
        configurer.addField(section, ProgramConstants.DEGREE_TYPE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_DEGREETYPE));
        return section;
    }

    private VerticalSection createProgramTitleSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_PROGRAMTITLE)));
        configurer.addField(section, ProgramConstants.LONG_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEFULL));
        configurer.addField(section, ProgramConstants.SHORT_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLESHORT));
        configurer.addField(section, ProgramConstants.TRANSCRIPT, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLETRANSCRIPT));
        configurer.addField(section, ProgramConstants.DIPLOMA, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEDIPLOMA)).setWidgetBinding(new DiplomaBinding());
        return section;
    }

    private VerticalSection createDatesSection() {
        VerticalSection section = new VerticalSection(SectionTitle.generateH3Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_DATES)));
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
        return section;
    }

    private VerticalSection createReadOnlySection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CREDENTIALPROGRAM));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LEVEL));
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
		private boolean isEmpty(String value){
			return value == null || (value != null && "".equals(value));
		}

		@Override
		public void setModelValue(KSTextBox widget, DataModel model, String path) {
			String 	diplomaTitle = 	widget.getText();
			if(diplomaTitle != null)
				model.set(QueryPath.concat(null, "/" + ProgramConstants.DIPLOMA), diplomaTitle);
		}

		@Override
		public void setWidgetValue(KSTextBox widget, DataModel model, String path) {
			String diplomaTitle = model.get("/" + ProgramConstants.DIPLOMA);
			if(isEmpty(diplomaTitle)){
				String programTitle = model.get("/" + ProgramConstants.LONG_TITLE);
				if (!isEmpty(programTitle))
					widget.setText(programTitle);
			}
			else
				widget.setText(diplomaTitle);
		}
	}
}
