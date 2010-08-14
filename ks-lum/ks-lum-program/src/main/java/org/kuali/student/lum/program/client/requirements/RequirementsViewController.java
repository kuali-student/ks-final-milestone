package org.kuali.student.lum.program.client.requirements;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.YesNoCancelGroup;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

public class RequirementsViewController extends BasicLayout {

    public enum ProgramRequirementsViews {
        VIEW,
        MANAGE
    }

    public static final String PROGRAM_RULES_MODEL_ID = "programRulesModelId";

    //SectionTitle.generateH3Title(ProgramProperties.get().programInformation_otherInformation()

    public RequirementsViewController(Controller controller, String name, Enum<?> viewType) {
		super(RequirementsViewController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
		this.setDefaultView(ProgramRequirementsViews.VIEW);

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

        //TODO remove after testing done
        StatementTreeViewInfo stmtTreeInfo = new StatementTreeViewInfo();

        List<StatementTreeViewInfo> subTrees = new ArrayList<StatementTreeViewInfo>();
        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTrees.add(subTree1);
        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTrees.add(subTree2);
        stmtTreeInfo.setStatements(subTrees);
        stmtTreeInfo.setOperator(StatementOperatorTypeKey.AND);
        stmtTreeInfo.setType("kuali.luStatementType.prereqAcademicReadiness");
        subTree1.setType("kuali.luStatementType.prereqAcademicReadiness");
        subTree2.setType("kuali.luStatementType.prereqAcademicReadiness");        

        // set reqComps for sub-tree 1
        subTree1.setId("STMT-TV-2");
        subTree1.setStatements(null);
        ReqComponentInfo reqComp1 = new ReqComponentInfo();
        reqComp1.setId("REQCOMP-TV-1");
        reqComp1.setNaturalLanguageTranslation("Student must have completed all of MATH 152, MATH 180");
        ReqComponentInfo reqComp2 = new ReqComponentInfo();
        reqComp2.setId("REQCOMP-TV-2");
        reqComp2.setNaturalLanguageTranslation("Student needs a minimum GPA of 3.5 in MATH 152, MATH 180");
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
        reqComp3.setNaturalLanguageTranslation("Student must have completed 1 of MATH 152, MATH 180");
        ReqComponentInfo reqComp4 = new ReqComponentInfo();
        reqComp4.setId("REQCOMP-TV-4");
        reqComp4.setNaturalLanguageTranslation("Student needs a minimum GPA of 4.0 in MATH 152, MATH 180");
        List<ReqComponentInfo> reqComponents2 = new ArrayList<ReqComponentInfo>();
        reqComponents2.add(reqComp3);
        reqComponents2.add(reqComp4);
        subTree2.setReqComponents(reqComponents2);
        subTree2.setNaturalLanguageTranslation("Student must have completed 1 of MATH 152, MATH 180 " +
                "and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180");
        subTree2.setOperator(StatementOperatorTypeKey.OR);

       stmtTreeInfo.setNaturalLanguageTranslation(
               "(Student must have completed all of MATH 152, MATH 180 and Student needs a minimum GPA of 3.5 in MATH 152, MATH 180) " +
        		"or (Student must have completed 1 of MATH 152, MATH 180 and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180)");        
       stmtTreeInfo.setOperator(StatementOperatorTypeKey.AND);

        //no name for the view so that breadcrumbs do not extra link
        ProgramRequirementsSummaryView view = new ProgramRequirementsSummaryView(this, ProgramRequirementsViews.VIEW, "", PROGRAM_RULES_MODEL_ID, stmtTreeInfo);              
        super.addView(view);
        ProgramRequirementsManageView viewRequirementsManage = new ProgramRequirementsManageView(this, ProgramRequirementsViews.MANAGE, "Add and Combine Rules", PROGRAM_RULES_MODEL_ID, stmtTreeInfo);
        super.addView(viewRequirementsManage);
             
    }

    @Override
    public void beforeViewChange(final Callback<Boolean> okToChange) {

        if (getCurrentView() == null) {
            okToChange.exec(true);
            return;
        }

        //We do this check here because theoretically the subcontroller views
        //will display their own messages to the user to give them a reason why the view
        //change has been cancelled, otherwise continue to check for reasons not to change
        //with this controller
        super.beforeViewChange(new Callback<Boolean>(){

            @Override
            public void exec(Boolean result) {
                if(result){
//TODO check if view (manage view - check if dirty to show dialog); clear dirty flag                    
                    if(getCurrentView() instanceof ProgramRequirementsManageView && ((SectionView)getCurrentView()).isDirty()){
                        ButtonGroup<ButtonEnumerations.YesNoCancelEnum> buttonGroup = new YesNoCancelGroup();
                        final ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum> dialog = new ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum>("Warning", "You may have unsaved changes.  Save changes?", buttonGroup);
                        buttonGroup.addCallback(new Callback<ButtonEnumerations.YesNoCancelEnum>(){

                            @Override
                            public void exec(ButtonEnumerations.YesNoCancelEnum result) {
                                switch(result){
                                    case YES:
                                        SaveActionEvent e = new SaveActionEvent();
                                        fireApplicationEvent(e);
                                        if(e.isSaveSuccessful()){
                                            okToChange.exec(true);
                                        }
                                        else{
                                            okToChange.exec(false);
                                        }
                                        dialog.hide();
                                        break;
                                    case NO:
                                        //Force a model request from server
                                        /*
                                        getCurrentModel(new ModelRequestCallback<DataModel>(){

                                            @Override
                                            public void onModelReady(DataModel model) {
                                                if (getCurrentView()instanceof Section){
                                                    ((Section) getCurrentView()).resetFieldInteractionFlags();
                                                }
                                                okToChange.exec(true);
                                                dialog.hide();
                                            }

                                            @Override
                                            public void onRequestFail(Throwable cause) {
                                                //TODO Is this correct... do we want to stop view change if we can't restore the data?  Possibly traps the user
                                                //if we don't it messes up saves, possibly warn the user that it failed and continue?
                                                okToChange.exec(false);
                                                dialog.hide();
                                                GWT.log("Unable to retrieve model for data restore on view change with no save", cause);
                                            }},
                                            NO_OP_CALLBACK);  */

                                        break;
                                    case CANCEL:
                                        okToChange.exec(false);
                                        dialog.hide();
                                        break;
                                }
                            }
                        });
                        dialog.show();
                    }
                    else{
                        okToChange.exec(true);
                    }
                }
                else{
                    okToChange.exec(false);
                }
            }
        });
    }


    protected String getLabel(String labelKey) {
        //return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
        return "Completion Requirements";
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
}