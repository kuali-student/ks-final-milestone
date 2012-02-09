package org.kuali.student.lum.program.client.variation.edit;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasCrossConstraints;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramSections;
import org.kuali.student.lum.program.client.events.ChangeViewEvent;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.SpecializationCreatedEvent;
import org.kuali.student.lum.program.client.events.SpecializationSaveEvent;
import org.kuali.student.lum.program.client.events.SpecializationUpdateEvent;
import org.kuali.student.lum.program.client.events.StoreSpecRequirementIDsEvent;
import org.kuali.student.lum.program.client.major.MajorController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;
import org.kuali.student.lum.program.client.variation.VariationController;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;

/**
 * @author Igor
 */
public class VariationEditController extends VariationController {

    private final KSButton saveButton = new KSButton(getLabel(ProgramMsgConstants.COMMON_SAVE));
    private final KSButton cancelButton = new KSButton(getLabel(ProgramMsgConstants.COMMON_CANCEL), KSButtonAbstract.ButtonStyle.ANCHOR_LARGE_CENTERED);

    private String currentId;

    public VariationEditController(DataModel programModel, ViewContext viewContext, HandlerManager eventBus, MajorController majorController) {
        super(programModel, viewContext, eventBus, majorController);
        configurer = GWT.create(VariationEditConfigurer.class);
        sideBar.setState(ProgramSideBar.State.EDIT);
        if (getStringProperty(ProgramConstants.ID) != null) {
            setDefaultView(ProgramSections.SUMMARY);
        }
        initHandlers();
    }

    private void initHandlers() {
        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doSave();
            }
        });
        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doCancel();
            }
        });
        ModelLoadedEvent.Handler modelLoadedHandler = new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                DataModel dataModel = event.getModel();
                Data variationMap = dataModel.get(ProgramConstants.VARIATIONS);
                if (variationMap != null) {
                    int row = 0;
                    for (Data.Property property : variationMap) {
                        final Data variationData = property.getValue();
                        if (variationData.get(ProgramConstants.ID).equals(currentId)) {
                            programModel.setRoot(variationData);
                            ProgramRegistry.setData(variationData);
                            ProgramRegistry.setRow(row);
                            setContentTitle(getProgramName());
                            row++;
                            return;
                        }
                    }
                }
            }
        };
        ProgramRegistry.addHandler(ModelLoadedEvent.TYPE, modelLoadedHandler);
        eventBus.addHandler(ModelLoadedEvent.TYPE, modelLoadedHandler);

        ChangeViewEvent.Handler changeViewHandler = new ChangeViewEvent.Handler() {
            @Override
            public void onEvent(ChangeViewEvent event) {
                Enum<?> viewToken = event.getViewToken();
                if (!viewToken.name().equals(ProgramSections.SPECIALIZATIONS_EDIT.name())) {
                    showView(viewToken);
                }
            }
        };
        ProgramRegistry.addHandler(ChangeViewEvent.TYPE, changeViewHandler);
        eventBus.addHandler(ChangeViewEvent.TYPE, changeViewHandler);
        eventBus.addHandler(SpecializationCreatedEvent.TYPE, new SpecializationCreatedEvent.Handler() {

            @Override
            public void onEvent(SpecializationCreatedEvent event) {
                programModel.getRoot().set(ProgramConstants.ID, event.getSpecializationId());
                showWarnings();
            }
        });

        eventBus.addHandler(SpecializationUpdateEvent.TYPE, new SpecializationUpdateEvent.Handler() {
            @Override
            public void onEvent(SpecializationUpdateEvent event) {
                // update our model to the updated specialization

                // gotta find it first
                String currSpecializationId = programModel.getRoot().get(ProgramConstants.ID);
                for (Data.Property prop : event.getSpecializations()) {
                    String specId = (String) ((Data) prop.getValue()).get(ProgramConstants.ID);

                    if (null != specId && specId.equals(currSpecializationId) && prop.getValueType().equals(Data.class)) {
                        // found it
                        programModel.setRoot((Data) prop.getValue());
                        showView(getCurrentViewEnum());
                    }
                }
                
                //update with any new warnings that exist on specialization
                showWarnings();
            }
        });

        StoreSpecRequirementIDsEvent.Handler requirementsHandler = new StoreSpecRequirementIDsEvent.Handler() {
            @Override
            public void onEvent(StoreSpecRequirementIDsEvent event) {
                final String programId = event.getProgramId();
                final List<String> ids = event.getProgramRequirementIds();

                requestModel(new ModelRequestCallback<DataModel>() {
                    @Override
                    public void onModelReady(final DataModel model) {
                        Data programRequirements = null;

                        // find the specialization that we need to update
                        //for (Data.Property property : model.getRoot()) {
                        Data variationData = model.getRoot();
                        if (variationData.get(ProgramConstants.ID).equals(programId)) {
                            variationData.set(ProgramConstants.PROGRAM_REQUIREMENTS, new Data());
                            programRequirements = variationData.get(ProgramConstants.PROGRAM_REQUIREMENTS);
                            // break;
                        }
                        // }

                        if (programRequirements == null) {
                            Window.alert("Cannot find program requirements in data model.");
                            GWT.log("Cannot find program requirements in data model", null);
                            return;
                        }

                        for (String id : ids) {
                            programRequirements.add(id);
                        }
                        doSave();
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                        GWT.log("Unable to retrieve model for validation and save", cause);
                    }

                });
            }
        };
        ProgramRegistry.addHandler(StoreSpecRequirementIDsEvent.TYPE, requirementsHandler);
        eventBus.addHandler(StoreSpecRequirementIDsEvent.TYPE, requirementsHandler);
    }

    @Override
    protected void fireUpdateEvent(Callback<Boolean> okToChange) {
        doSave(okToChange);
    }

    private void doSave(final Callback<Boolean> okToChange) {
        requestModel(new ModelRequestCallback<DataModel>() {
            @Override
            public void onModelReady(final DataModel model) {
                VariationEditController.this.updateModelFromCurrentView();
                model.setParentPath(ProgramConstants.VARIATIONS + "/" + ProgramRegistry.getRow());
                model.validate(new Callback<List<ValidationResultInfo>>() {
                    @Override
                    public void exec(List<ValidationResultInfo> results) {
                        boolean isSectionValid = isValid(results, true);
                        if (isSectionValid) {
                            saveData(model);
                            okToChange.exec(true);
                        } else {
                            okToChange.exec(false);
                            KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
                        }
                    }
                });
            }

            @Override
            public void onRequestFail(Throwable cause) {
                GWT.log("Unable to retrieve model for validation and save", cause);
            }

        });
    }

    @Override
    protected void configureView() {
        super.configureView();
        if (!initialized) {
            List<Enum<?>> excludedViews = new ArrayList<Enum<?>>();
            excludedViews.add(ProgramSections.PROGRAM_REQUIREMENTS_EDIT);
            excludedViews.add(ProgramSections.SUPPORTING_DOCUMENTS_EDIT);
            excludedViews.add(ProgramSections.SUMMARY);
            addCommonButton(getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS), saveButton, excludedViews);
            addCommonButton(getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS), cancelButton, excludedViews);
            initialized = true;
        }

    }

    @Override
    protected void resetModel() {
        currentId = getStringProperty(ProgramConstants.ID);
        programModel.resetRoot();
    }

    private void doCancel() {
       navigateToParent(ProgramSections.SUMMARY);
    }

    @Override
    protected void doSave() {
        doSave(NO_OP_CALLBACK);
    }

    private void saveData(DataModel model) {
        currentId = model.get(ProgramConstants.ID);
        eventBus.fireEvent(new SpecializationSaveEvent(model.getRoot()));
        setContentTitle(getProgramName());
        setName(getProgramName());
        resetFieldInteractionFlag();
    }

    @Override
    protected void navigateToParent() {
        navigateToParent(ProgramSections.SPECIALIZATIONS_EDIT);
    }

    private void navigateToParent(ProgramSections parentSection) {
    	String appLoc = "";
    	
    	if(!(majorController instanceof MajorProposalController))
    		appLoc = AppLocations.Locations.EDIT_PROGRAM_SPEC.getLocation();
    	else
    		appLoc = AppLocations.Locations.PROGRAM_PROPOSAL.getLocation();
        String path = HistoryManager.appendContext(appLoc, getViewContext()) + "/" + parentSection;
        HistoryManager.navigate(path);
    }

    
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
    	//clear all cross constraints that start with variations
    	Application.getApplicationContext().clearCrossConstraintsWithStartingPath(null,ProgramConstants.VARIATIONS);
    	
    	//Set the context parent path so the proper mapping is retained 
    	String newParentPath = ProgramConstants.VARIATIONS+"/"+org.kuali.student.lum.program.client.ProgramRegistry.getRow()+"/";
    	Application.getApplicationContext().setParentPath(newParentPath);
		
    	//This callback restricts values displayed in widget (eg. dropdowns, pickers) based on a cross field selection
    	//and updates the warning messages displayed for the variation. A callback is used since we need the parent 
    	//ProgramController to finish configuring the view before proceeding.
		Callback<Boolean> finalizeVariationView = new Callback<Boolean>(){
			public void exec(Boolean result) {
		        //Update widgets with constraints
				for(HasCrossConstraints crossConstraint:Application.getApplicationContext().getCrossConstraints(null)){
		        	crossConstraint.reprocessWithUpdatedConstraints();
		        }

		        onReadyCallback.exec(result);
			}
        };
		super.beforeShow(finalizeVariationView);
	}

	//Before show is called before the model is bound to the widgets. We need to update cross constraints after widget binding
	//This gets called twice which is not optimal
	@Override
	public <V extends Enum<?>> void showView(V viewType,
			final Callback<Boolean> onReadyCallback) {
		Callback<Boolean> updateCrossConstraintsCallback = new Callback<Boolean>(){
			public void exec(Boolean result) {
				onReadyCallback.exec(result);
		        showWarnings();	
			}
        };
		super.showView(viewType, updateCrossConstraintsCallback);
	}
	
}
