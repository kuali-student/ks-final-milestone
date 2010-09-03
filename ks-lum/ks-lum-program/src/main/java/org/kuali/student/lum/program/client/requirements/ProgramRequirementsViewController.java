package org.kuali.student.lum.program.client.requirements;

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

public class ProgramRequirementsViewController extends BasicLayout {

    public enum ProgramRequirementsViews {
        PREVIEW,
        MANAGE
    }

    public static final String PROGRAM_RULES_MODEL_ID = "programRulesModelId";

    public ProgramRequirementsViewController(Controller controller, String name, Enum<?> viewType) {
		super(ProgramRequirementsViewController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
		this.setDefaultView(ProgramRequirementsViews.PREVIEW);

        super.registerModel(PROGRAM_RULES_MODEL_ID, new ModelProvider<DataModel>() {

            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {

                //TODO: how do we store and retrieve rules?
                DataModel programModel = new DataModel();
                callback.onModelReady(programModel);

                /*
                if (modelRequestQueue == null) {
                    modelRequestQueue = new WorkQueue();
                }

                WorkQueue.WorkItem workItem = new WorkQueue.WorkItem() {
                    @Override
                    public void exec(Callback<Boolean> workCompleteCallback) {
                        if (programModel.getRoot() == null || programModel.getRoot().size() == 0) {
                            initModel(callback, workCompleteCallback);
                        } else {
                            callback.onModelReady(programModel);
                            workCompleteCallback.exec(true);
                        }
                    }

                };
                modelRequestQueue.submit(workItem); */
            }
        });

        //no name for the view so that breadcrumbs do not extra link
        List<String> programRequirements = null; // TODO retrieve a list of program requirements
        ProgramRequirementsSummaryView summaryView = new ProgramRequirementsSummaryView(this, ProgramRequirementsViews.PREVIEW, "", PROGRAM_RULES_MODEL_ID, programRequirements);
        super.addView(summaryView);
        
        ProgramRequirementsManageView manageView =
                new ProgramRequirementsManageView(this, ProgramRequirementsViews.MANAGE, "Add and Combine Rules", PROGRAM_RULES_MODEL_ID);
        super.addView(manageView);
             
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

                if(!(getCurrentView() instanceof ProgramRequirementsManageView)) {
                    okToChange.exec(true);
                    return;
                }

                //no dialog if user clicks on the 'Save' button
                if (((ProgramRequirementsManageView)getCurrentView()).isUserClickedSaveButton()) {
                    //((ProgramRequirementsManageView)getCurrentView()).getRuleTree();
                    //((ProgramRequirementsSummaryView)getCurrentView()).saveProgramRequirement();                            
                    okToChange.exec(true);
                    return;                    
                }

                //if user clicked on breadcrumbs, menu or cancel button and changes have been made to the rule on the manage screen...
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
                }
                okToChange.exec(true);
            }
        });
    }
    @Override
	public void beforeShow(final Callback<Boolean> onReadyCallback){
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

    //TODO remove after testing done
    public StatementTreeViewInfo getTestStatement() {

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
        reqComp1.setNaturalLanguageTranslation("Must have successfully completed all courses from MATH 152, MATH 180");
        reqComp1.setType("kuali.reqCompType.course.courseset.completed.all");
        ReqComponentInfo reqComp2 = new ReqComponentInfo();
        reqComp2.setId("REQCOMP-TV-2");
        reqComp2.setNaturalLanguageTranslation("Must have earned a minimum GPA of 3.5 in MATH 152, MATH 180");
        reqComp2.setType("kuali.reqCompType.course.courseset.gpa.min");
        List<ReqComponentInfo> reqComponents = new ArrayList<ReqComponentInfo>();
        reqComponents.add(reqComp1);
        reqComponents.add(reqComp2);
        subTree1.setReqComponents(reqComponents);
        subTree1.setNaturalLanguageTranslation("Student must have completed all of MATH 152, MATH 180 " +
        		"and Student needs a minimum GPA of 3.5 in MATH 152, MATH 180");
        subTree1.setOperator(StatementOperatorTypeKey.OR);

        subTree2.setId("STMT-TV-3");
        subTree2.setStatements(null);
        ReqComponentInfo reqComp3 = new ReqComponentInfo();
        reqComp3.setId("REQCOMP-TV-3");
        reqComp3.setNaturalLanguageTranslation("Must have successfully completed a minimum of 1 course from MATH 152, MATH 180");
        reqComp3.setType("kuali.reqCompType.course.courseset.completed.nof");
        ReqComponentInfo reqComp4 = new ReqComponentInfo();
        reqComp4.setId("REQCOMP-TV-4");
        reqComp4.setNaturalLanguageTranslation("Must have earned a minimum GPA of 4 in MATH 152, MATH 180");
        reqComp4.setType("kuali.reqCompType.course.courseset.gpa.min");
        List<ReqComponentInfo> reqComponents2 = new ArrayList<ReqComponentInfo>();
        reqComponents2.add(reqComp3);
        reqComponents2.add(reqComp4);
        subTree2.setReqComponents(reqComponents2);
        subTree2.setNaturalLanguageTranslation("Student must have completed 1 of MATH 152, MATH 180 " +
                "and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180");
        subTree2.setOperator(StatementOperatorTypeKey.AND);

       stmtTreeInfo.setNaturalLanguageTranslation(
               "(Student must have completed all of MATH 152, MATH 180 or Student needs a minimum GPA of 3.5 in MATH 152, MATH 180) " +
        		"and (Student must have completed 1 of MATH 152, MATH 180 and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180)");
       stmtTreeInfo.setOperator(StatementOperatorTypeKey.AND);

        return stmtTreeInfo;
    }
}