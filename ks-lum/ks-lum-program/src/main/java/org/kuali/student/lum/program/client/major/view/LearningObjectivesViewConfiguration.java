package org.kuali.student.lum.program.client.major.view;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.lo.TreeStringBinding;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.major.MajorEditableHeader;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class LearningObjectivesViewConfiguration extends AbstractSectionConfiguration {

	private Controller controller = null;

    public static LearningObjectivesViewConfiguration create(Configurer configurer) {
        return new LearningObjectivesViewConfiguration(configurer);
    }

    public static LearningObjectivesViewConfiguration createSpecial(Configurer configurer, Controller controller) {
        return new LearningObjectivesViewConfiguration(configurer, controller);
    }

    private LearningObjectivesViewConfiguration(Configurer configurer) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_LEARNINGOBJECTIVES);
        rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID);
    }

    private LearningObjectivesViewConfiguration(Configurer configurer, Controller controller) {
        this.setConfigurer(configurer);
        String title = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_LEARNINGOBJECTIVES);
        rootSection = new VerticalSectionView(ProgramSections.LEARNING_OBJECTIVES_VIEW, title, ProgramConstants.PROGRAM_MODEL_ID, new MajorEditableHeader(title, ProgramSections.LEARNING_OBJECTIVES_EDIT));
       	this.controller = controller;        
    }

    protected void buildLayout() {
    	if (controller instanceof MajorProposalController || controller instanceof MajorEditController) 
       		rootSection.addSection(createLearningObjectivesSectionEdit());       		
    	else	
    		configurer.addReadOnlyField(rootSection, ProgramConstants.LEARNING_OBJECTIVES, new MessageKeyInfo(""), new KSListPanel()).setWidgetBinding(new TreeStringBinding());       	
    }
    
    // Side-by-side comparison (when controller is not null)  
    private SummaryTableSection createLearningObjectivesSectionEdit() { 
        SummaryTableSection section = GWT.create(SummaryTableSection.class);
        section.init((Controller) controller);
      	section.setEditable(false);
      	section.addSummaryTableFieldBlock(createLearningObjectivesSectionEditBlock());

        return section;
    }

    public SummaryTableFieldBlock createLearningObjectivesSectionEditBlock() {
  		SummaryTableFieldBlock block = new SummaryTableFieldBlock();

        block.addSummaryTableFieldRow(getFieldRow(ProgramConstants.LEARNING_OBJECTIVES, new MessageKeyInfo(""), new KSListPanel(),
        		new KSListPanel(), null, new TreeStringBinding(), false));        

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
