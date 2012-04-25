package org.kuali.student.lum.lu.ui.course.client.requirements;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.YesNoCancelGroup;

import com.google.gwt.core.client.GWT;

public class CourseRequirementsViewController extends BasicLayout {

    public enum CourseRequirementsViews {
        PREVIEW,
        MANAGE
    }

    public static final String COURSE_RULES_MODEL_ID = "courseRulesModelId";
    public static final String COURSE_PROPOSAL_MODEL = "courseProposalModel";    

    private CourseRequirementsSummaryView preview;

    public CourseRequirementsViewController(Controller controller, String name, Enum<?> viewType, boolean isReadOnly, boolean showSaveButtons) {
		super(CourseRequirementsViewController.class.getName());
		super.setController(controller);
		super.setName(name);
		super.setViewEnum(viewType);
        super.setDefaultModelId(COURSE_RULES_MODEL_ID);
        super.setParentController(controller);

		this.setDefaultView(CourseRequirementsViews.PREVIEW);

        //not used
        super.registerModel(COURSE_RULES_MODEL_ID, new ModelProvider<DataModel>() {
            @Override
            public void requestModel(final ModelRequestCallback<DataModel> callback) {
                callback.onModelReady(new DataModel());
            }
        });

        //no name for the view so that breadcrumbs do not extra link
        preview = GWT.create(CourseRequirementsSummaryView.class);
        preview.init(
                this,
                CourseRequirementsViews.PREVIEW,
                (isReadOnly ? "Course Requirements" : ""),
                COURSE_PROPOSAL_MODEL,
                (controller instanceof HasRequirements ? ((HasRequirements) controller).getReqDataModel()
                        : new CourseRequirementsDataModel(this)),
                isReadOnly, showSaveButtons);
        super.addView(preview);

        if (!isReadOnly) {
            CourseRequirementsManageView manageView = GWT.create(CourseRequirementsManageView.class);
            manageView.init(this, CourseRequirementsViews.MANAGE, "Add and Combine Rules", COURSE_RULES_MODEL_ID);
            super.addView(manageView);
        }
    }

    @Override
    public void updateModel() {
        preview.updateModel();    
    }

    @Override
    public void beforeViewChange(final Enum<?> viewChangingTo, final Callback<Boolean> okToChange) {

        if (getCurrentView() == null) {
            okToChange.exec(true);
            return;
        }

        super.beforeViewChange(viewChangingTo, new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {

                //no dialog if user clicks on the 'Save' button on Manage Rule page
                if (getCurrentView() instanceof CourseRequirementsManageView) {
                    okToChange.exec(true);
                    return;
                }

                //no dialog if user is going to the Manage Rule page
                if (viewChangingTo.name().equals(CourseRequirementsViewController.CourseRequirementsViews.MANAGE.name()))
                {
                    //TODO show dialog if user clicks on a menu from Manage Rules page
                    okToChange.exec(true);
                    return;
                }

                //user is moving to another course proposal section and no changes were made to the rules so allow it to happen
                if (!((SectionView) getCurrentView()).isDirty()) {
                    okToChange.exec(true);
                    return;
                }

                //user is moving to another course proposal section and rules have been changed, user needs to either save rules or abondon changes before proceeding
                ButtonGroup<ButtonEnumerations.YesNoCancelEnum> buttonGroup = new YesNoCancelGroup();
                final ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum> dialog =
                        new ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum>("Warning", "You may have unsaved changes.  Save changes?", buttonGroup);
                buttonGroup.addCallback(new Callback<ButtonEnumerations.YesNoCancelEnum>() {

                    @Override
                    public void exec(ButtonEnumerations.YesNoCancelEnum result) {
                        switch (result) {
                            case YES:
                                dialog.hide();
                                preview.storeRules(true, new Callback<Boolean>() {
                                    @Override
                                    public void exec(Boolean result) {
                                        okToChange.exec(result);
                                    }
                                });
                                break;
                            case NO:
                                dialog.hide();
                                preview.revertRuleChanges();                                
                                okToChange.exec(true);
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
        });
    }
    
    @Override
	public void beforeShow(final Callback<Boolean> onReadyCallback){
		showDefaultView(onReadyCallback);
	}
    
    public void storeRules(Callback<Boolean> callback){
    	preview.storeRules(true, callback);
    }
}