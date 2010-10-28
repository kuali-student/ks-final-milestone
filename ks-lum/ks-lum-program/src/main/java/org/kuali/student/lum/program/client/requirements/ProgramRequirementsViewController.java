package org.kuali.student.lum.program.client.requirements;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.YesNoCancelGroup;
import org.kuali.student.lum.program.client.ProgramConstants;

public class ProgramRequirementsViewController extends BasicLayout {

    public enum ProgramRequirementsViews {
        PREVIEW,
        MANAGE
    }

    public static final String PROGRAM_RULES_MODEL_ID = "programRulesModelId";
    private ProgramRequirementsSummaryView preview;

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

        //no name for the view so that breadcrumbs do not extra link
        preview = new ProgramRequirementsSummaryView(this, ProgramRequirementsViews.PREVIEW, (isReadOnly ? "Program Requirements" : ""), ProgramConstants.PROGRAM_MODEL_ID, isReadOnly);
        super.addView(preview);

        if (!isReadOnly) {
            ProgramRequirementsManageView manageView = new ProgramRequirementsManageView(this, ProgramRequirementsViews.MANAGE, "Add and Combine Rules", PROGRAM_RULES_MODEL_ID);
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
                if (getCurrentView() instanceof ProgramRequirementsManageView) {
                    okToChange.exec(true);
                    return;
                }

                //reload rules if user is going to Program Sections page
          /*      if (viewChangingTo.name().equals(ProgramSections.SUMMARY.name()))
                {
                    preview.resetRules();
                    okToChange.exec(true);
                    return;
                } */

                //no dialog if user is going to the Manage Rule page
                if (viewChangingTo.name().equals(ProgramRequirementsViews.MANAGE.name()))
                {
                    //TODO show dialog if user clicks on a menu from Manage Rules page
                    okToChange.exec(true);
                    return;
                }

                //user is moving to another program section and no changes were made to the rules so allow it to happen
                if (!((SectionView) getCurrentView()).isDirty()) {
                    okToChange.exec(true);
                    return;
                }

                //user is moving to another program section and rules have been changed, user needs to either save rules or abondon changes before proceeding
                ButtonGroup<ButtonEnumerations.YesNoCancelEnum> buttonGroup = new YesNoCancelGroup();
                final ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum> dialog =
                        new ButtonMessageDialog<ButtonEnumerations.YesNoCancelEnum>("Warning", "You may have unsaved changes.  Save changes?", buttonGroup);
                buttonGroup.addCallback(new Callback<ButtonEnumerations.YesNoCancelEnum>() {

                    @Override
                    public void exec(ButtonEnumerations.YesNoCancelEnum result) {
                        switch (result) {
                            case YES:
                                dialog.hide();
                                preview.storeRules(new Callback<Boolean>() {
                                    @Override
                                    public void exec(Boolean result) {
                                        okToChange.exec(true);                                        
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
    public void beforeShow(final Callback<Boolean> onReadyCallback) {
       // preview.resetRules();
        showDefaultView(onReadyCallback);
    }

    public ProgramRequirementsSummaryView getProgramRequirementsView() {
        return preview;
    }
}