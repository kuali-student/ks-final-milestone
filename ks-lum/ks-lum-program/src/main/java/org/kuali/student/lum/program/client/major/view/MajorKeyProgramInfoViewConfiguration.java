package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ListToTextBinding;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.TableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
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
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class MajorKeyProgramInfoViewConfiguration extends AbstractSectionConfiguration {

	private Controller controller = null;

    public static MajorKeyProgramInfoViewConfiguration create() {
        MajorKeyProgramInfoViewConfiguration instance = new MajorKeyProgramInfoViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID));
        return instance;
    }

    public static MajorKeyProgramInfoViewConfiguration createSpecial(Controller controller) {
        MajorKeyProgramInfoViewConfiguration instance = new MajorKeyProgramInfoViewConfiguration(new VerticalSectionView(ProgramSections.PROGRAM_DETAILS_VIEW, ProgramProperties.get().program_menu_sections_programInformation(), ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(ProgramProperties.get().program_menu_sections_programInformation(), ProgramSections.PROGRAM_DETAILS_EDIT)), controller);
        return instance;
    }

    private MajorKeyProgramInfoViewConfiguration(SectionView sectionView) {
        rootSection = sectionView;
        rootSection.addStyleName("programInformationView");
    }

    private MajorKeyProgramInfoViewConfiguration(SectionView sectionView, Controller controller) {
        rootSection = sectionView;
        rootSection.addStyleName("programInformationView");
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController) 
    	{
    		VerticalSection section = new VerticalSection();
    		section.addSection(createIdentifyingDetailsSectionEdit());
    		section.addSection(createProgramTitleSectionEdit());
    		section.addSection(createDatesSectionEdit());
    		section.addSection(createOtherInformationSectionEdit());
            rootSection.addSection(section);    		
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
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_identifyingDetails()));
        configurer.addReadOnlyField(section, ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram()));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification()));
        configurer.addReadOnlyField(section, ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType()));
        return section;
    }

    private TableSection createProgramTitleSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_programTitle()));
        configurer.addReadOnlyField(section, ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull()));
        configurer.addReadOnlyField(section, ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort()));
        configurer.addReadOnlyField(section, ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript()));
        configurer.addReadOnlyField(section, ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_titleDiploma()));
        return section;
    }

    private TableSection createDatesSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_dates()));
        //Add this field and hide it so it is available for cross field validation 
        FieldDescriptor fd = configurer.addField(section,ProgramConstants.PREV_START_TERM, new MessageKeyInfo(ProgramProperties.get().majorDiscipline_prevStartTerm()));
        fd.getFieldWidget().setVisible(false);
        fd.hideLabel();
        configurer.addReadOnlyField(section, ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_admitTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm()));
        configurer.addReadOnlyField(section, ProgramConstants.PROGRAM_APPROVAL_DATE, new MessageKeyInfo(ProgramProperties.get().programInformation_approvalDate()));
        return section;
    }

    private TableSection createOtherInformationSection() {
        TableSection section = new TableSection(SectionTitle.generateH4Title(ProgramProperties.get().programInformation_otherInformation()));
        configurer.addReadOnlyField(section, ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location()));
        FieldDescriptor fd = configurer.addReadOnlyField(section, ProgramConstants.ACCREDITING_AGENCY, new MessageKeyInfo(ProgramProperties.get().programInformation_accreditation()));
        fd.setWidgetBinding(new ListToTextBinding(ProgramConstants.ACCREDITING_AGENCY_ORG_ID_TRANSLATION));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2000, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2000()));
        configurer.addReadOnlyField(section, ProgramConstants.CIP_2010, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2010()));
        configurer.addReadOnlyField(section, ProgramConstants.HEGIS_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_hegis()));
        configurer.addReadOnlyField(section, ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution()));
        return section;
    }

    public VerticalSection createActivateProgramSection(){
        final VerticalSection section = new VerticalSection(SectionTitle.generateH2Title(ProgramProperties.get().programInformation_activateProgram()));
        section.setInstructions("<br>" + ProgramProperties.get().programInformation_activateInstructions() + "<br><br>");
        controller.requestModel(new ModelRequestCallback<DataModel>(){
			public void onModelReady(final DataModel model) {
				//Add previous end dates and update cross constraints
				WorkflowUtilities.updateCrossField(configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm())), model);
				WorkflowUtilities.updateCrossField(configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm())), model);
				WorkflowUtilities.updateCrossField(configurer.addField(section, "proposal/"+ProgramConstants.PREV_END_INST_ADMIN_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm())), model);
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

  	@SuppressWarnings("unchecked")
  	public SummaryTableFieldBlock createIdentifyingDetailsSectionBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(ProgramProperties.get().programInformation_identifyingDetails());
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_code())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CREDENTIAL_PROGRAM_LEVEL, new MessageKeyInfo(ProgramProperties.get().programInformation_level())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME, new MessageKeyInfo(ProgramProperties.get().programInformation_credentialProgram())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROGRAM_CLASSIFICATION, new MessageKeyInfo(ProgramProperties.get().programInformation_classification())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DEGREE_TYPE, new MessageKeyInfo(ProgramProperties.get().programInformation_degreeType())));

  		return block;
  	}
    
    private SummaryTableSection createProgramTitleSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createProgramTitleSectionEditBlock());

        return section;
    }

  	@SuppressWarnings("unchecked")
  	public SummaryTableFieldBlock createProgramTitleSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(ProgramProperties.get().programInformation_programTitle());
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.LONG_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleFull())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.SHORT_TITLE, new MessageKeyInfo(ProgramProperties.get().programInformation_titleShort())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.TRANSCRIPT, new MessageKeyInfo(ProgramProperties.get().programInformation_titleTranscript())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DIPLOMA, new MessageKeyInfo(ProgramProperties.get().programInformation_titleDiploma())));

  		return block;
  	}
  	
    private SummaryTableSection createDatesSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createDatesSectionEditBlock());

        return section;
    }

  	@SuppressWarnings("unchecked")
  	public SummaryTableFieldBlock createDatesSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(ProgramProperties.get().programInformation_dates());
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.START_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_startTerm())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.END_INSTITUTIONAL_ADMIT_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_admitTerm())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.END_PROGRAM_ENTRY_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_entryTerm())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.END_PROGRAM_ENROLL_TERM, new MessageKeyInfo(ProgramProperties.get().programInformation_enrollTerm())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.PROGRAM_APPROVAL_DATE, new MessageKeyInfo(ProgramProperties.get().programInformation_approvalDate())));
 
  		return block;
  	}

    private SummaryTableSection createOtherInformationSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createOtherInformationSectionEditBlock());

        return section;
    }

  	@SuppressWarnings("unchecked")
  	public SummaryTableFieldBlock createOtherInformationSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.setTitle(ProgramProperties.get().programInformation_otherInformation());
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.LOCATION, new MessageKeyInfo(ProgramProperties.get().programInformation_location())));
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.ACCREDITING_AGENCY,
        		new MessageKeyInfo(ProgramProperties.get().programInformation_accreditation()), null,
                null, null, new ListToTextBinding(ProgramConstants.ACCREDITING_AGENCY_ORG_ID_TRANSLATION), false));        
        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CIP_2000, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2000())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CIP_2010, new MessageKeyInfo(ProgramProperties.get().programInformation_cip2010())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.HEGIS_CODE, new MessageKeyInfo(ProgramProperties.get().programInformation_hegis())));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID, new MessageKeyInfo(ProgramProperties.get().programInformation_institution())));
   		
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
