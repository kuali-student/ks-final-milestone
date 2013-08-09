package org.kuali.student.lum.program.client.requirements;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayout;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations;
import org.kuali.student.common.ui.client.widgets.dialog.ButtonMessageDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.field.layout.button.YesNoCancelGroup;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.widgets.EditableHeader;

import com.google.gwt.event.shared.HandlerManager;

public class ProgramRequirementsViewController extends BasicLayout {

    public enum ProgramRequirementsViews {
        PREVIEW,
        MANAGE
    }

    public static final String PROGRAM_RULES_MODEL_ID = "programRulesModelId";
    private ProgramRequirementsSummaryView preview;
    boolean reloadFlag;

    public ProgramRequirementsViewController(Controller controller, HandlerManager eventBus, String name, Enum<?> viewType, boolean isReadOnly, EditableHeader header, boolean reloadFlag){
        this(controller, eventBus, name, viewType, isReadOnly, header);
        this.reloadFlag = reloadFlag;
    }

    public ProgramRequirementsViewController(Controller controller, HandlerManager eventBus, String name, Enum<?> viewType, boolean isReadOnly, EditableHeader header) {
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
        String previewTitle = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS_REQUIREMENTS);
        if (isReadOnly && (header != null)) {
            preview = new ProgramRequirementsSummaryView(this, eventBus, ProgramRequirementsViews.PREVIEW, "", ProgramConstants.PROGRAM_MODEL_ID, isReadOnly, header);                                                            
        } else {
            preview = new ProgramRequirementsSummaryView(this, eventBus, ProgramRequirementsViews.PREVIEW, (isReadOnly ? previewTitle : ""), ProgramConstants.PROGRAM_MODEL_ID, isReadOnly);
        }
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

                //moving from PREVIEW to MANAGE page
                //no dialog if user is going to the Manage Rule page
                if (viewChangingTo.name().equals(ProgramRequirementsViews.MANAGE.name()))
                {
                    okToChange.exec(true);
                    return;
                }

                //moving from MANAGE to PREVIEW page
                //no dialog if user clicks on the 'Save' or cancel button on the Manage Rule page
                if (getCurrentView() instanceof ProgramRequirementsManageView) {
                    okToChange.exec(true);
                    return;
                }

                //moving from MANAGE to other page
                //TODO show dialog if user clicks on a menu from Manage Rules page                

                //moving from other page to PREVIEW page
                if (viewChangingTo.name().equals(ProgramRequirementsViews.PREVIEW.name())) {
                    preview.getRules().setupRules(ProgramRequirementsViewController.this, ProgramConstants.PROGRAM_MODEL_ID, new Callback<Boolean>() {
                        @Override
                        public void exec(Boolean result) {
                            okToChange.exec(result);
                            return;
                        }
                    });
                    return;
                }

                //moving from PREVIEW to other page (rules have NOT changed)
                //user is moving to another program section and no changes were made to the rules so allow it to happen
                if (!((SectionView) getCurrentView()).isDirty()) {
                    okToChange.exec(true);
                    return;
                }

                //moving from PREVIEW to other page (rules have changed)
                //user is moving to another program section and rules have been changed, user needs to either save rules or abandon changes before proceeding
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
                                //preview.revertRuleChanges();     //TODO put back if we will NOT reset rules every time user comes to PREVIEW page above...
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
        showDefaultView(onReadyCallback);
    }

    public ProgramRequirementsSummaryView getProgramRequirementsView() {
        return preview;
    }
    
    protected String getLabel(String messageKey) {
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, messageKey);
    }
}