package org.kuali.student.lum.lu.ui.course.client.requirements;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ContinueCancelGroup;
import org.kuali.student.lum.program.client.ProgramConstants;

public class CourseRequirementsViewController extends BasicLayout {

    public enum CourseRequirementsViews {
        PREVIEW,
        MANAGE
    }

    public static final String COURSE_RULES_MODEL_ID = "courseRulesModelId";
    private CourseRequirementsSummaryView preview;
    private boolean isReadOnly;

    public CourseRequirementsViewController(Controller controller, String name, Enum<?> viewType, boolean isReadOnly) {
		super(CourseRequirementsViewController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
        super.setDefaultModelId(ProgramConstants.PROGRAM_MODEL_ID);
        super.setParentController(controller);
        this.isReadOnly = isReadOnly;

		this.setDefaultView(CourseRequirementsViews.PREVIEW);

        //not used
        super.registerModel(COURSE_RULES_MODEL_ID, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                callback.onModelReady(new DataModel());
            }
        });

        //no name for the view so that breadcrumbs do not extra link
        preview = new CourseRequirementsSummaryView(this, CourseRequirementsViews.PREVIEW, (isReadOnly ? "Course Requirements" : ""), ProgramConstants.PROGRAM_MODEL_ID,
                                                new CourseRequirementsDataModel(this), isReadOnly);
        super.addView(preview);

        if (!isReadOnly) {
            CourseRequirementsManageView manageView = new CourseRequirementsManageView(this, CourseRequirementsViews.MANAGE,
                                                "Add and Combine Rules", COURSE_RULES_MODEL_ID); //, CourseRequirementsViews.PREVIEW);
            super.addView(manageView);
        }
    }

    @Override
    public void updateModel() {
        preview.updateModel();    
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