package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.CollapsableSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
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
public class ManagingBodiesViewConfiguration extends AbstractSectionConfiguration {

	private Controller controller = null;

    public static ManagingBodiesViewConfiguration create(Configurer configurer) {
        return new ManagingBodiesViewConfiguration(configurer);
    }

    public static ManagingBodiesViewConfiguration createSpecial(Configurer configurer, Controller controller) {
        return new ManagingBodiesViewConfiguration(configurer, controller);
    }

    private ManagingBodiesViewConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_MANAGINGBODIES);
        this.rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, title, 
                ProgramConstants.PROGRAM_MODEL_ID);
        
    }

    private ManagingBodiesViewConfiguration(Configurer configurer, Controller controller) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_MANAGINGBODIES);
        this.rootSection = new VerticalSectionView(ProgramSections.MANAGE_BODIES_VIEW, title, 
                ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, ProgramSections.MANAGE_BODIES_EDIT));
    	this.controller = controller;
    	
    }

    @Override
    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController) 
    	{
    		SummaryTableSection section = createMainSectionEdit(); 
    		CollapsableSection collapsableSection = createAdditionalSectionEdit();
            rootSection.addSection(section);
            rootSection.addSection(collapsableSection);
    	} else
    	{
    		VerticalSection section = createMainSection();
    		CollapsableSection collapsableSection = createAdditionalSection();
            rootSection.addSection(section);
            rootSection.addSection(collapsableSection);
    	}
    }

    private VerticalSection createMainSection() {
        VerticalSection section = new VerticalSection();
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTUNIT));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.STUDENT_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTUNIT));
        return section;
    }

    private CollapsableSection createAdditionalSection() {
        CollapsableSection section = new CollapsableSection(getLabel(ProgramMsgConstants.MANAGINGBODIES_SEEALL));
        configurer.addReadOnlyField(section, ProgramConstants.DEPLOYMENT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_DEPLOYMENTDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.DEPLOYMENT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_DEPLOYMENTUNIT));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_RESOURCES_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALRESOURCESDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_RESOURCES_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALRESOURCESUNIT));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_CONTROL_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALCONTROLDIVISION));
        configurer.addReadOnlyField(section, ProgramConstants.FINANCIAL_CONTROL_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALCONTROLUNIT));
        return section;
    }
    
    
    // Side-by-side comparison (when controller is not null)  
    private SummaryTableSection createMainSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createMainSectionEditBlock());

        return section;
    }

  	public SummaryTableFieldBlock createMainSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTDIVISION)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_CURRICULUMOVERSIGHTUNIT)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.STUDENT_OVERSIGHT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTDIVISION)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.STUDENT_OVERSIGHT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_STUDENTOVERSIGHTUNIT)));

  		return block;
  	}

    private CollapsableSection createAdditionalSectionEdit() { 
      	SummaryTableSection section = new SummaryTableSection((Controller) controller);     		
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createAdditionalSectionEditBlock());
      	
      	CollapsableSection collapsableSection = new CollapsableSection(getLabel(ProgramMsgConstants.MANAGINGBODIES_SEEALL));
      	collapsableSection.addSection(section);
      	
        return collapsableSection;
    }

  	public SummaryTableFieldBlock createAdditionalSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DEPLOYMENT_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_DEPLOYMENTDIVISION)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.DEPLOYMENT_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_DEPLOYMENTUNIT)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.FINANCIAL_RESOURCES_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALRESOURCESDIVISION)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.FINANCIAL_RESOURCES_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALRESOURCESUNIT)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.FINANCIAL_CONTROL_DIVISION, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALCONTROLDIVISION)));
  		block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.FINANCIAL_CONTROL_UNIT, generateMessageInfo(ProgramMsgConstants.MANAGINGBODIES_FINANCIALCONTROLUNIT)));

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
