package org.kuali.student.lum.lu.ui.course.client.requirements;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ContinueCancelGroup;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;


public class CourseRequirementsViewController extends BasicLayout {

    public enum CourseRequirementsViews {
        PREVIEW,
        MANAGE
    }

    //TODO remove after testing
    protected static final String TEMLATE_LANGUAGE = "en";
    protected static final String RULEEDIT_TEMLATE = "KUALI.RULE";
    protected static final String COMPOSITION_TEMLATE = "KUALI.COMPOSITION";

    public static final String COURSE_RULES_MODEL_ID = "courseRulesModelId";
    private CourseRequirementsSummaryView preview;
    private static CourseRequirementsDataModel dataInstance;

    public CourseRequirementsViewController(Controller controller, String name, Enum<?> viewType, boolean isReadOnly) {
		super(CourseRequirementsViewController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
        super.setDefaultModelId(COURSE_RULES_MODEL_ID);
        super.setParentController(controller);
        
		this.setDefaultView(CourseRequirementsViewController.CourseRequirementsViews.PREVIEW);

        //not used
        super.registerModel(COURSE_RULES_MODEL_ID, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                callback.onModelReady(new DataModel());
            }
        });

        if (dataInstance == null) {
             dataInstance = new CourseRequirementsDataModel(this);
        }

        //no name for the view so that breadcrumbs do not extra link
        preview = new CourseRequirementsSummaryView(this, CourseRequirementsViews.PREVIEW, (isReadOnly ? "Course Requirements" : ""), COURSE_RULES_MODEL_ID, dataInstance, isReadOnly);
        super.addView(preview);

        if (!isReadOnly) {
            CourseRequirementsManageView manageView = new CourseRequirementsManageView(this, CourseRequirementsViewController.CourseRequirementsViews.MANAGE, "Add and Combine Rules", COURSE_RULES_MODEL_ID);
            super.addView(manageView);
        }
    }

    @Override
    public void beforeViewChange(final Callback<Boolean> okToChange) {

        if (getCurrentView() == null) {
            okToChange.exec(true);
            return;
        }

        //We do this check here because theoretically the subcontroller views
        //will display their own messages to the user to give them a reason why the view
        //change has been cancelled, otherwise continue to check for reasons not to change with this controller
        super.beforeViewChange(new Callback<Boolean>(){

            @Override
            public void exec(Boolean result) {

                if (!result) {
                    okToChange.exec(false);
                    return;
                }

                if(!(getCurrentView() instanceof CourseRequirementsManageView)) {
                    okToChange.exec(true);
                    return;
                }

                //no dialog if user clicks on the 'Save' button
                if (((CourseRequirementsManageView)getCurrentView()).isUserClickedSaveButton()) {                       
                    okToChange.exec(true);
                    return;                    
                }

                //if user clicked on breadcrumbs, menu or cancel button of the manage screen and changes have been made to the rule on the manage screen...
               if (((SectionView)getCurrentView()).isDirty()) {
                    ButtonGroup<ButtonEnumerations.ContinueCancelEnum> buttonGroup = new ContinueCancelGroup();
                    final ButtonMessageDialog<ButtonEnumerations.ContinueCancelEnum> dialog =
                                new ButtonMessageDialog<ButtonEnumerations.ContinueCancelEnum>("Warning", "You have unsaved changes. Are you sure you want to proceed?", buttonGroup);
                    buttonGroup.addCallback(new Callback<ButtonEnumerations.ContinueCancelEnum>(){
                        @Override
                        public void exec(ButtonEnumerations.ContinueCancelEnum result) {
                            okToChange.exec(result == ButtonEnumerations.ContinueCancelEnum.CONTINUE);                            
                            dialog.hide();
                        }
                    });
                    dialog.show();
               } else {
                    okToChange.exec(true);
               }
            }
        });
    }
    
    @Override
	public void beforeShow(final Callback<Boolean> onReadyCallback){
        //TODO
	//	init(new Callback<Boolean>() {
	//		@Override
	//		public void exec(Boolean result) {
	//			if (result) {
					showDefaultView(onReadyCallback);
	//			} else {
	//				onReadyCallback.exec(false);
	//			}
	//		}
	//	});
	}

    public CourseRequirementsSummaryView getProgramRequirementsView() {
        return preview;
    }

    //TODO remove after testing done
    static public StatementTreeViewInfo getTestStatement() {

        StatementTreeViewInfo stmtTreeInfo = new StatementTreeViewInfo();
        stmtTreeInfo.setId("123");

        List<StatementTreeViewInfo> subTrees = new ArrayList<StatementTreeViewInfo>();
        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTrees.add(subTree1);
        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTrees.add(subTree2);
        stmtTreeInfo.setStatements(subTrees);
        stmtTreeInfo.setType("kuali.statement.type.course.academicReadiness.prereq");
        subTree1.setType("kuali.statement.type.course.academicReadiness.prereq");
        subTree2.setType("kuali.statement.type.course.academicReadiness.prereq");

        // set reqComps for sub-tree 1
        subTree1.setId("STMT-TV-2");
        subTree1.setStatements(null);
        ReqComponentInfo reqComp1 = new ReqComponentInfo();
        reqComp1.setId("REQCOMP-TV-1");
        reqComp1.setNaturalLanguageTranslation("Permission of English Department required");
        reqComp1.setType("course.permission.org.required ");
        ReqComponentInfo reqComp2 = new ReqComponentInfo();
        reqComp2.setId("REQCOMP-TV-2");
        reqComp2.setNaturalLanguageTranslation("May be repeated for a maximum of 3 credits");
        reqComp2.setType("course.credits.repeat.max ");
        List<ReqComponentInfo> reqComponents = new ArrayList<ReqComponentInfo>();
        reqComponents.add(reqComp1);
        reqComponents.add(reqComp2);
        subTree1.setReqComponents(reqComponents);
        subTree1.setNaturalLanguageTranslation("Permission of English Department required or May be repeated for a maximum of 3 credits");
        subTree1.setOperator(StatementOperatorTypeKey.OR);

        subTree2.setId("STMT-TV-3");
        subTree2.setStatements(null);
        ReqComponentInfo reqComp3 = new ReqComponentInfo();
        reqComp3.setId("REQCOMP-TV-3");
        reqComp3.setNaturalLanguageTranslation("Permission of Math Department required");
        reqComp3.setType("course.permission.org.required ");
        ReqComponentInfo reqComp4 = new ReqComponentInfo();
        reqComp4.setId("REQCOMP-TV-4");
        reqComp4.setNaturalLanguageTranslation("May be repeated for a maximum of 5 credits");
        reqComp4.setType("course.credits.repeat.max ");
        List<ReqComponentInfo> reqComponents2 = new ArrayList<ReqComponentInfo>();
        reqComponents2.add(reqComp3);
        reqComponents2.add(reqComp4);
        subTree2.setReqComponents(reqComponents2);
        subTree2.setNaturalLanguageTranslation("Permission of Math Department required and May be repeated for a maximum of 5 credits");
        subTree2.setOperator(StatementOperatorTypeKey.AND);

       stmtTreeInfo.setNaturalLanguageTranslation(
               "Permission of English Department required or May be repeated for a maximum of 3 credits " +
        		"and Permission of Math Department required and May be repeated for a maximum of 5 credits");
       stmtTreeInfo.setOperator(StatementOperatorTypeKey.AND);

        return stmtTreeInfo;
    }
}