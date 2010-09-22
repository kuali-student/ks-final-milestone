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
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

public class ProgramRequirementsViewController extends BasicLayout {

    public enum ProgramRequirementsViews {
        PREVIEW,
        MANAGE
    }

    //TODO remove after testing
    protected static final String TEMLATE_LANGUAGE = "en";
    protected static final String RULEEDIT_TEMLATE = "KUALI.RULE";
    protected static final String COMPOSITION_TEMLATE = "KUALI.COMPOSITION";    

    public static final String PROGRAM_RULES_MODEL_ID = "programRulesModelId";
    private ProgramRequirementsSummaryView preview;
    private static ProgramRequirementsDataModel dataInstance;

    public ProgramRequirementsViewController(Controller controller, String name, Enum<?> viewType, boolean isReadOnly) {
		super(ProgramRequirementsViewController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
        super.setDefaultModelId(PROGRAM_RULES_MODEL_ID);
        super.setParentController(controller);
        
		this.setDefaultView(ProgramRequirementsViews.PREVIEW);

        //not used
        super.registerModel(PROGRAM_RULES_MODEL_ID, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                callback.onModelReady(new DataModel());
            }
        });

        if (dataInstance == null) {
             dataInstance = new ProgramRequirementsDataModel(this);
        }

        //no name for the view so that breadcrumbs do not extra link
        preview = new ProgramRequirementsSummaryView(this, ProgramRequirementsViews.PREVIEW, (isReadOnly ? "Program Requirements" : ""), PROGRAM_RULES_MODEL_ID, dataInstance, isReadOnly);
        super.addView(preview);

        if (!isReadOnly) {
            ProgramRequirementsManageView manageView = new ProgramRequirementsManageView(this, ProgramRequirementsViews.MANAGE, "Add and Combine Rules", PROGRAM_RULES_MODEL_ID);
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

                if(!(getCurrentView() instanceof ProgramRequirementsManageView)) {
                    okToChange.exec(true);
                    return;
                }

                //no dialog if user clicks on the 'Save' button
                if (((ProgramRequirementsManageView)getCurrentView()).isUserClickedSaveButton()) {                       
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

    /*
    @Override
    public View getCurrentView() {
        return this;
    } */

    public ProgramRequirementsSummaryView getProgramRequirementsView() {
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
        stmtTreeInfo.setType("kuali.statement.type.program.entrance");
        subTree1.setType("kuali.statement.type.program.entrance");
        subTree2.setType("kuali.statement.type.program.entrance");

        // set reqComps for sub-tree 1
        subTree1.setId("STMT-TV-2");
        subTree1.setStatements(null);
        ReqComponentInfo reqComp1 = new ReqComponentInfo();
        reqComp1.setId("REQCOMP-TV-1");
        reqComp1.setNaturalLanguageTranslation("Must have successfully completed all of (Sociology and CORE Advanced Studies) programs");
        ReqComponentTypeInfo reqCompType = new ReqComponentTypeInfo();
        reqCompType.setId("kuali.reqComponent.type.program.programset.completed.all");
        reqComp1.setRequiredComponentType(reqCompType);
        ReqComponentInfo reqComp2 = new ReqComponentInfo();
        reqComp2.setId("REQCOMP-TV-2");
        reqComp2.setNaturalLanguageTranslation("Must have earned a minimum GPA of 2.00 in (MATH111, 140, 220, and STAT100)");
        ReqComponentTypeInfo reqCompType2 = new ReqComponentTypeInfo();
        reqCompType2.setId("kuali.reqComponent.type.course.courseset.gpa.min");
        reqComp2.setRequiredComponentType(reqCompType2);        
        List<ReqComponentInfo> reqComponents = new ArrayList<ReqComponentInfo>();
        reqComponents.add(reqComp1);
        reqComponents.add(reqComp2);
        subTree1.setReqComponents(reqComponents);
        subTree1.setNaturalLanguageTranslation("Must have successfully completed all of (Sociology and CORE Advanced Studies) programs " +
        		"and must have earned a minimum GPA of 2.00 in (MATH111, 140, 220, and STAT100)");
        subTree1.setOperator(StatementOperatorTypeKey.OR);

        subTree2.setId("STMT-TV-3");
        subTree2.setStatements(null);
        ReqComponentInfo reqComp3 = new ReqComponentInfo();
        reqComp3.setId("REQCOMP-TV-3");
        reqComp3.setNaturalLanguageTranslation("Must have successfully completed a minimum of 14 courses from ( Sociology and CORE Advanced Studies) programs");
        ReqComponentTypeInfo reqCompType3 = new ReqComponentTypeInfo();
        reqCompType3.setId("kuali.reqComponent.type.program.programset.coursecompleted.nof");
        reqComp3.setRequiredComponentType(reqCompType3);
        ReqComponentInfo reqComp4 = new ReqComponentInfo();
        reqComp4.setId("REQCOMP-TV-4");
        reqComp4.setNaturalLanguageTranslation("Must be admitted to program prior to earning 60 credits");
        ReqComponentTypeInfo reqCompType4 = new ReqComponentTypeInfo();
        reqCompType4.setId("kuali.reqComponent.type.program.admitted.credits");
        reqComp4.setRequiredComponentType(reqCompType4);
        List<ReqComponentInfo> reqComponents2 = new ArrayList<ReqComponentInfo>();
        reqComponents2.add(reqComp3);
        reqComponents2.add(reqComp4);
        subTree2.setReqComponents(reqComponents2);
        subTree2.setNaturalLanguageTranslation("Must have successfully completed a minimum of 14 courses from ( Sociology and CORE Advanced Studies) programs " +
                "and must be admitted to program prior to earning 60 credits");
        subTree2.setOperator(StatementOperatorTypeKey.AND);

       stmtTreeInfo.setNaturalLanguageTranslation(
               "(Student must have completed all of MATH 152, MATH 180 or Student needs a minimum GPA of 3.5 in MATH 152, MATH 180) " +
        		"and (Student must have completed 1 of MATH 152, MATH 180 and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180)");
       stmtTreeInfo.setOperator(StatementOperatorTypeKey.AND);

        return stmtTreeInfo;
    }
}