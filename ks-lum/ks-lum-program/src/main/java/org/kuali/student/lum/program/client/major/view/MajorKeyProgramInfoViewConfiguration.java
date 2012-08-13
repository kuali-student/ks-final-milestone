package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ListToTextBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class MajorKeyProgramInfoViewConfiguration extends AbstractSectionConfiguration {

	private Controller controller = null;

    public static MajorKeyProgramInfoViewConfiguration create(Configurer configurer) {
        return new MajorKeyProgramInfoViewConfiguration(configurer);
    }

    public static MajorKeyProgramInfoViewConfiguration createSpecial(Configurer configurer, Controller controller) {
        return new MajorKeyProgramInfoViewConfiguration(configurer, controller);
    }

    private MajorKeyProgramInfoViewConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID);
        rootSection.addStyleName("programInformationView");
    }

    private MajorKeyProgramInfoViewConfiguration(Configurer configurer, Controller controller) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_PROGRAMINFORMATION);
        rootSection = new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, 
                new MajorEditableHeader(title, ProgramSections.PROGRAM_DETAILS_EDIT));
        rootSection.addStyleName("programInformationView");
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController) 
    	{
            rootSection.addSection(createIdentifyingDetailsSectionEdit());    		
            rootSection.addSection(createProgramTitleSectionEdit());    		
            rootSection.addSection(createDatesSectionEdit());    		
            rootSection.addSection(createOtherInformationSectionEdit());    		
    	} else
    	{
       		HorizontalSection section = new HorizontalSection();
       		section.addSection(createIdentifyingDetailsSection());
       		section.addSection(createProgramTitleSection());
       		section.nextRow();
       		section.addSection(createDatesSection());
       		section.addSection(createOtherInformationSection());
       		rootSection.addSection(section);
    	}
    }

    private TableSection createIdentifyingDetailsSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS)));
        configurer.addReadOnlyField(section, ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LEVEL));
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
        //Add this field and hide it so it is available for cross field validation 
        FieldDescriptor fd = configurer.addField(section,ProgramConstants.PREV_START_TERM, generateMessageInfo(ProgramMsgConstants.MAJORDISCIPLINE_PREVSTARTTERM));
        fd.getFieldWidget().setVisible(false);
        fd.hideLabel();
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
        FieldDescriptor fd = configurer.addReadOnlyField(section, ProgramConstants.ACCREDITING_AGENCY, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ACCREDITATION));
        fd.setWidgetBinding(new ListToTextBinding(ProgramConstants.ACCREDITING_AGENCY_ORG_ID_TRANSLATION));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2000, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2000));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2010, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2010));
        configurer.addReadOnlyField(section, ProgramConstants.HEGIS_CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_HEGIS));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION));
        return section;
    }

    public VerticalSection createActivateProgramSection(){
        final VerticalSection section = new VerticalSection(SectionTitle.generateH2Title(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACTIVATEPROGRAM)));
        section.setInstructions("<br>" + getLabel(ProgramMsgConstants.PROGRAMINFORMATION_ACTIVATEINSTRUCTIONS) + "<br><br>");
        controller.requestModel(new ModelRequestCallback<DataModel>(){
			public void onModelReady(final DataModel model) {
				//Add previous end dates and update cross constraints
				WorkflowUtilities.updateCrossField(configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM)), model);
				WorkflowUtilities.updateCrossField(configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM)), model);
				WorkflowUtilities.updateCrossField(configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_INST_ADMIN_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM)), model);
			}
			public void onRequestFail(Throwable cause) {
			}
        });
        return section;
    }

    // Side-by-side comparison (when controller is not null)  
    private SummaryTableSection createIdentifyingDetailsSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createIdentifyingDetailsSectionBlock());

        return section;
    }

  	public SummaryTableFieldBlock createIdentifyingDetailsSectionBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_IDENTIFYINGDETAILS));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CODE)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LEVEL)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CREDENTIALPROGRAM)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROGRAM_CLASSIFICATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CLASSIFICATION)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DEGREE_TYPE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_DEGREETYPE)));

  		return block;
  	}
    
    private SummaryTableSection createProgramTitleSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createProgramTitleSectionEditBlock());

        return section;
    }

  	public SummaryTableFieldBlock createProgramTitleSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_PROGRAMTITLE));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.LONG_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEFULL)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.SHORT_TITLE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLESHORT)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.TRANSCRIPT, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLETRANSCRIPT)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DIPLOMA, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_TITLEDIPLOMA)));

  		return block;
  	}
  	
    private SummaryTableSection createDatesSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createDatesSectionEditBlock());

        return section;
    }

  	public SummaryTableFieldBlock createDatesSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_DATES));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.START_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_STARTTERM)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ADMITTERM)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.END_PROGRAM_ENTRY_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENTRYTERM)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.END_PROGRAM_ENROLL_TERM, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ENROLLTERM)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROGRAM_APPROVAL_DATE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_APPROVALDATE)));
 
  		return block;
  	}

    private SummaryTableSection createOtherInformationSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createOtherInformationSectionEditBlock());

        return section;
    }

  	public SummaryTableFieldBlock createOtherInformationSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(getLabel(ProgramMsgConstants.PROGRAMINFORMATION_OTHERINFORMATION));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.LOCATION, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_LOCATION)));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.ACCREDITING_AGENCY,
        		generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_ACCREDITATION), null,
                null, null, new ListToTextBinding(ProgramConstants.ACCREDITING_AGENCY_ORG_ID_TRANSLATION), false));        
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CIP_2000, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2000)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CIP_2010, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_CIP2010)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.HEGIS_CODE, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_HEGIS)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, generateMessageInfo(ProgramMsgConstants.PROGRAMINFORMATION_INSTITUTION)));
   		
  		return block;
  	}
  	
  	protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey) {
  		return getFieldRow(fieldKey, messageKey, null, null, null, null, false);
  	}
     
  	protected SummaryTableFieldRow getFieldRow(String fieldKey,
  			MessageKeyInfo messageKey, Widget widget, Widget widget2,
  			String parentPath, ModelWidgetBinding<?> binding, boolean optional) 
  	{
  		QueryPath path = QueryPath.concat(parentPath, fieldKey);
  		Metadata meta = configurer.getModelDefinition().getMetadata(path);

  		FieldDescriptorReadOnly fd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
  		if (widget != null) {
  			fd.setFieldWidget(widget);
  		}
  		if (binding != null) {
  			fd.setWidgetBinding(binding);
  		}
  		fd.setOptional(optional);

  		FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
  		if (widget2 != null) {
  			fd2.setFieldWidget(widget2);
  		}
  		if (binding != null) {
  			fd2.setWidgetBinding(binding);
  		}
  		fd2.setOptional(optional);

  		SummaryTableFieldRow fieldRow = new SummaryTableFieldRow(fd, fd2);

  		return fieldRow;
  	}    
}
