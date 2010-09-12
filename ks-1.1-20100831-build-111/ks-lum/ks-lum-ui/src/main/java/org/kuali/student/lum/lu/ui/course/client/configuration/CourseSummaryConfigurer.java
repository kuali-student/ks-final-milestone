package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptorReadOnly;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldBlock;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableFieldRow;
import org.kuali.student.common.ui.client.widgets.table.summary.SummaryTableSection;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowEnhancedController;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseConfigurer.CourseSections;

import com.google.gwt.user.client.ui.Widget;

public class CourseSummaryConfigurer {
    protected String type = "course";
    protected String state = "draft";
    protected String groupName;
    protected DataModelDefinition modelDefinition;
    
    public static final String COURSE = "";
    public static final String COURSE_TITLE = "courseTitle";
    public static final String PROPOSAL_TITLE = "proposalTitle";
    public static final String TRANSCRIPT_TITLE = "transcriptTitle";
    public static final String CLU_PROPOSAL_MODEL = "cluProposalModel";
    public CourseSummaryConfigurer(String type, String state,
            String groupName, DataModelDefinition modelDefinition) {
        this.type = type;
        this.state = state;
        this.groupName = groupName;
        this.modelDefinition = modelDefinition;
    }

    protected VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CLU_PROPOSAL_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        return section;
    }
    protected String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }
    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(groupName, type, state, labelKey);
    }
    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey) {
        return getFieldRow(fieldKey, messageKey, null,null, null);
    }
    protected SummaryTableFieldRow getFieldRow(String fieldKey, MessageKeyInfo messageKey, Widget widget, Widget widget2, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);

        FieldDescriptorReadOnly fd = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        FieldDescriptorReadOnly fd2 = new FieldDescriptorReadOnly(path.toString(), messageKey, meta);
        if (widget2 != null) {
            fd2.setFieldWidget(widget2);
        }
        SummaryTableFieldRow fieldRow = new SummaryTableFieldRow(fd,fd2);

        return fieldRow;
    }
    public VerticalSectionView generateSummarySection(WorkflowEnhancedController wf){
        SummaryTableSection tableSection = new SummaryTableSection((Controller)wf);
      //  VerticalSectionView section = initSectionView(CourseSections.COURSE_INFO, );

        SummaryTableFieldBlock block = new SummaryTableFieldBlock();
        block.addSummaryTableFieldRow(getFieldRow(PROPOSAL_TITLE, generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUConstants.COURSE_TITLE_LABEL_KEY)));
        block.addSummaryTableFieldRow(getFieldRow(COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUConstants.SHORT_TITLE_LABEL_KEY)));
        
        block.setTitle(getLabel(LUConstants.INFORMATION_LABEL_KEY));
        tableSection.addSummaryTableFieldBlock(block);
        
        VerticalSectionView verticalSection = new VerticalSectionView(CourseSections.SUMMARY, getLabel(LUConstants.SUMMARY_LABEL_KEY), CourseConfigurer.CLU_PROPOSAL_MODEL);
        
        verticalSection.addWidget(wf.getWfUtilities().getWorkflowActionsWidget());
        verticalSection.addSection(tableSection);
        verticalSection.addWidget(wf.getWfUtilities().getWorkflowActionsWidget());
        return verticalSection;        
   
    }
}
