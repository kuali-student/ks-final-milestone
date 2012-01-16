package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentList;
import org.kuali.student.core.document.ui.client.widgets.documenttool.DocumentListBinding;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
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
public class SupportingDocsViewConfiguration extends AbstractSectionConfiguration {

	private Controller controller = null;

    public static SupportingDocsViewConfiguration create(Configurer configurer){
        return new SupportingDocsViewConfiguration(configurer);
    }

    public static SupportingDocsViewConfiguration createSpecial(Configurer configurer, Controller controller) {
        return new SupportingDocsViewConfiguration(configurer, controller);
    }

    private SupportingDocsViewConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SUPPORTINGDOCUMENTS);
        rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID);
    }

    private SupportingDocsViewConfiguration(Configurer configurer, Controller controller) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_SUPPORTINGDOCUMENTS);
        rootSection = new VerticalSectionView(ProgramSections.SUPPORTING_DOCUMENTS_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, 
                ProgramSections.SUPPORTING_DOCUMENTS_EDIT));
        this.controller = controller;
    }

    @Override
    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController) 
       		rootSection.addSection(createSupportingDocsSectionEdit());       		
      	else
      		configurer.addReadOnlyField(rootSection, ProgramConstants.ID, new MessageKeyInfo(""), new DocumentList(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE, false, false)).setWidgetBinding(new DocumentListBinding("id"));
    }

    // Side-by-side comparison (when controller is not null)  
    private SummaryTableSection createSupportingDocsSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createSupportingDocsSectionEditBlock());

        return section;
    }

    public SummaryTableFieldBlock createSupportingDocsSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();

        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.ID, new MessageKeyInfo(""), new DocumentList(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE, false, false),
        		new DocumentList(LUUIConstants.REF_DOC_RELATION_PROPOSAL_TYPE, false, false), null, new DocumentListBinding("id"), false));        

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
