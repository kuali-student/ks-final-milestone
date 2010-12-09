/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.ViewLayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.event.SectionUpdateEvent;
import org.kuali.student.common.ui.client.event.SectionUpdateHandler;
import org.kuali.student.common.ui.client.event.ValidateRequestEvent;
import org.kuali.student.common.ui.client.event.ValidateRequestHandler;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.FieldElement;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The LayoutController is a central piece of the UIF.  This controller is also itself a view.
 * As such, LayoutControllers can also have other LayoutControllers as their views.  
 * 
 * @see Controller
 * @author Kuali Student Team
 *
 */
public abstract class LayoutController extends Controller implements ViewLayoutController, View {

	protected Map<Enum<?>, View> viewMap = new LinkedHashMap<Enum<?>, View>();
	protected Map<String, Enum<?>> viewEnumMap = new HashMap<String, Enum<?>>();
	protected Enum<?> defaultView;
	
	protected String name;
	protected Enum<?> viewType;

    protected View startPopupView;
    protected KSLightBox startViewWindow;
	
    /**
     * Constructor
     * Sets up event handlers fro section update events and validation request events.
     * @param controllerId - not used
     */
    public LayoutController(String controllerId){
        super(controllerId);
        //Global section update Event handling
		addApplicationEventHandler(SectionUpdateEvent.TYPE, new SectionUpdateHandler(){

			@Override
			public void onSectionUpdate(final SectionUpdateEvent event) {
				LayoutController.this.requestModel(new ModelRequestCallback<DataModel>(){

					@Override
					public void onRequestFail(Throwable cause) {
						GWT.log("Unable to retrieve model for section update", cause);
					}

					@Override
					public void onModelReady(DataModel model) {
						event.getSection().updateModel(model);
						event.getSection().updateWidgetData(model);
						
					}
				});
				
			}
		});
		//Global validation Event handling
        addApplicationEventHandler(ValidateRequestEvent.TYPE, new ValidateRequestHandler() {

            @Override
            public void onValidateRequest(final ValidateRequestEvent event) {
            	FieldDescriptor originatingField = event.getFieldDescriptor();
            	String modelId = null;
            	if (originatingField != null) {
            		modelId = originatingField.getModelId();
            	}
            	if (modelId == null) {
            		requestModel(new ModelRequestCallback<DataModel>() {
            			@Override
            			public void onModelReady(DataModel model) {
            				validate(model, event);
            			}

            			@Override
            			public void onRequestFail(Throwable cause) {
            				GWT.log("Unable to retrieve model for validation", cause);
            			}

            		});
            	} else {
            		requestModel(modelId, new ModelRequestCallback<DataModel>() {
            			@Override
            			public void onModelReady(DataModel model) {
            				validate(model, event);
            			}

            			@Override
            			public void onRequestFail(Throwable cause) {
            				GWT.log("Unable to retrieve model for validation", cause);
            			}

            		});
            	}
            }

        });
    }
    
    private void validate(DataModel model, final ValidateRequestEvent event) {
    	if(event.validateSingleField()){
    		model.validateField(event.getFieldDescriptor(), new Callback<List<ValidationResultInfo>>() {
                @Override
                public void exec(List<ValidationResultInfo> result) {
                	if(event.getFieldDescriptor() != null){
                		//We dont need to traverse since it is single field, so don't do isValid call here
                		//instead add the error messages directly
                		FieldElement element = event.getFieldDescriptor().getFieldElement();
                		if(element != null){
	                		element.clearValidationPanel();
	                		for(int i = 0; i < result.size(); i++){
	                    		ValidationResultInfo vr = result.get(i);
	                    		if(vr.getElement().equals(event.getFieldDescriptor().getFieldKey()) 
	                    				&& event.getFieldDescriptor().hasHadFocus()){
	    							element.processValidationResult(vr);
	                    		}
	                    	}
                		}
                	}
                	
                }
    		});
    	}
    	else{
            model.validate(new Callback<List<ValidationResultInfo>>() {
                @Override
                public void exec(List<ValidationResultInfo> result) {
                    isValid(result, false, true);
                }
            });
    	}
    }
    
    /**
     * Check to see if the list of validation results have an error.
     * @param list
     * @return
     */
    public ErrorLevel checkForErrors(List<ValidationResultInfo> list){
		ErrorLevel errorLevel = ErrorLevel.OK;
		
		for(ValidationResultInfo vr: list){
			if(vr.getErrorLevel().getLevel() > errorLevel.getLevel()){
				errorLevel = vr.getErrorLevel();
			}
			if(errorLevel.equals(ErrorLevel.ERROR)){
				break;
			}
		}
    	
    	return errorLevel;
    	
    }
    
    /**
     * Finds the first parent LayoutController of this LayoutController, returns null if this
     * is the top level LayoutController.
     * @param w
     * @return
     */
    public static LayoutController findParentLayout(Widget w){
        LayoutController result = null;
        while (true) {
            if (w == null) {
                break;
            } else if (w instanceof HasLayoutController) {
            	result = ((HasLayoutController)w).getLayoutController();
            	if (result != null) {
            		break;
            	}
            } else if (w instanceof LayoutController) {
                result = (LayoutController) w;
                break;
            }
            w = w.getParent();
            
        }
        return result;
    }
    
	/**
	 * @see org.kuali.student.common.ui.client.configurable.mvc.layouts.ViewLayoutController#addStartViewPopup(org.kuali.student.common.ui.client.mvc.View)
	 */
	public void addStartViewPopup(final View view){
	    startPopupView = view;
	    if(startViewWindow == null){
	    	startViewWindow = new KSLightBox();
	    }

	    FlowPanel panel = new FlowPanel();
	    panel.add(view.asWidget());
	    KSButton save = new KSButton("Save",new ClickHandler(){
            public void onClick(ClickEvent event) {
                view.updateModel();
                SaveActionEvent saveActionEvent = new SaveActionEvent(true);

                saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                    public void onActionComplete(ActionEvent action) {
                        startViewWindow.hide();
                    }
                });
                

                fireApplicationEvent(saveActionEvent);
            }
	    });
	    startViewWindow.addButton(save);
	    
	    KSButton cancel = new KSButton("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {
                startViewWindow.hide();
            }
	    });
	    startViewWindow.addButton(cancel);

	    if(view instanceof SectionView){
	    	((SectionView) view).setController(this);
	    }
	    startViewWindow.setWidget(panel);
	}
	
    /**
     * @return true if the start popup is showing
     */
    public boolean isStartViewShowing(){
        if(startViewWindow == null){
            return false;
        }
    	return startViewWindow.isShowing();
    }

    public View getStartPopupView(){
        return startPopupView;
    }
    
    public void showStartPopup(final Callback<Boolean> onReadyCallback){
        startPopupView.beforeShow(new Callback<Boolean>() {
			@Override
			public void exec(Boolean result) {
				if (result) {
					startViewWindow.show();
				}
				onReadyCallback.exec(result);
			}
        });
    }
    
    public KSLightBox getStartPopup(){
        return startViewWindow;
    }


    /*New methods*/
	
	/**
	 * @see org.kuali.student.common.ui.client.configurable.mvc.layouts.ViewLayoutController#addView(org.kuali.student.common.ui.client.mvc.View)
	 */
	public void addView(View view){
		viewMap.put(view.getViewEnum(), view);
		viewEnumMap.put(view.getViewEnum().toString(), view.getViewEnum());
		if(view instanceof SectionView){
			((SectionView) view).setController(this);
		}
	}
	
	/**
	 * @see org.kuali.student.common.ui.client.configurable.mvc.layouts.ViewLayoutController#setDefaultView(java.lang.Enum)
	 */
	public <V extends Enum<?>> void setDefaultView(V viewType){
		this.defaultView = viewType;
	}
	
	public Enum<?> getDefaultView(){
		return this.defaultView;
	}
	
	/**
	 * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
	 */
	public abstract void updateModel();
	
	/**
	 * Update the model with a single views information
	 * @param viewType
	 */
	public void updateModelFromView(Enum<?> viewType){
		View v = viewMap.get(viewType);
		if(v != null){
			v.updateModel();
		}
	}
	
	/**
	 * Update a the model from the view that is currently being shown by this controller
	 */
	public void updateModelFromCurrentView(){
        if(this.getCurrentView() != null){
		    this.getCurrentView().updateModel();
        }
	}

	@Override
	public <V extends Enum<?>> void getView(V viewType, Callback<View> callback) {
		callback.exec(viewMap.get(viewType));
	}

	@Override
	public Enum<?> getViewEnumValue(String enumValue) {
		return viewEnumMap.get(enumValue);
	}

	@Override
	public void showDefaultView(final Callback<Boolean> onReadyCallback) {
		HistoryManager.setLogNavigationHistory(false);
		//turn of history support for default showing until view is ready
		if(defaultView != null){
			showView(defaultView, onReadyCallback);
		}
		else if(!viewMap.isEmpty()){		
			if(defaultView == null){
				showView(viewMap.entrySet().iterator().next().getKey(), onReadyCallback);
			}	
		}
		
	}
	
	/**
	 * Show the view that was the first one added and will likely be the first one in a layout's menu, for
	 * example.  Note that this is different than show default view.
	 * 
	 * @param onReadyCallback
	 */
	public void showFirstView(Callback<Boolean> onReadyCallback){
		HistoryManager.setLogNavigationHistory(false);
		if(!viewMap.isEmpty()){	
			showView(viewMap.entrySet().iterator().next().getKey(), onReadyCallback);
		}
		else{
			showDefaultView(onReadyCallback);
		}
	}
	
	/**
 	 * Check to see if current/all section(s) is valid (ie. does not contain any errors)
 	 *
	 * @param validationResults List of validation results for the layouts model.
	 * @param checkCurrentSectionOnly true if errors should be checked on current section only, false if all sections should be checked
	 * @return true if the specified sections (all or current) has any validation errors
	 */
	public boolean isValid(List<ValidationResultInfo> validationResults, boolean checkCurrentSectionOnly){
		return isValid(validationResults, checkCurrentSectionOnly, true);
	}
	
	/**
	 * @see LayoutController#isValid(List, boolean)
	 * @param validationResults
	 * @param checkCurrentSectionOnly
	 * @param allFields
	 * @return
	 */
	public boolean isValid(List<ValidationResultInfo> validationResults, boolean checkCurrentSectionOnly, boolean allFields){
		boolean isValid = true;

		if (checkCurrentSectionOnly){
			//Check for validation errors on the currently displayed section only
	    	View v = getCurrentView();
	        if(v instanceof Section){
	        	isValid = isValid(validationResults, (Section)v, allFields);
	    	}
	     	if(this.isStartViewShowing()){
	     		if(startPopupView instanceof Section){
	     			isValid = isValid(validationResults, ((Section) startPopupView), allFields) && isValid;
	     		}
	     	}
		} else {
			//Check for validation errors on all sections
			String errorSections = "";
			StringBuilder errorSectionsbuffer = new StringBuilder();
			errorSectionsbuffer.append(errorSections);
			for (Entry<Enum<?>, View> entry:viewMap.entrySet()) {
				View v = entry.getValue();
				if (v instanceof Section){
					if (!isValid(validationResults, (Section)v, allFields)){
						isValid = false;
						errorSectionsbuffer.append(((SectionView)v).getName() + ", ");
					}
				}
			}
	     	if(this.isStartViewShowing()){
	     		if(startPopupView instanceof Section){
	     			isValid = isValid(validationResults, ((Section) startPopupView), allFields) && isValid;
	     		}
	     	}
			errorSections = errorSectionsbuffer.toString();
			if (!errorSections.isEmpty()){
				errorSections = errorSections.substring(0, errorSections.length()-2);
				//container.addMessage("Following section(s) has errors & must be corrected: " + errorSections);
			}
		}

		return isValid;
	}

	private boolean isValid(List<ValidationResultInfo> validationResults, Section section, boolean allFields){
		ErrorLevel status;
		if(allFields){
			section.setFieldHasHadFocusFlags(true);
			status = section.processValidationResults(validationResults);
		}
		else{
			status = section.processValidationResults(validationResults, false);
		}

		return (status != ErrorLevel.ERROR);
	}
	
	/**
	 * This particular implementation of beforeViewChange checks to see if all its view contains a Controller
	 * and if it does checks with that controller to see if it is ok to change the view.  OkToChange callback
	 * will be exec with true if the view is allowed to be changed at this time.  This method can be overriden
	 * to provide additional functionality to stop a view from being changed when there is some additional
	 * processing that needs to occur in the ui before the view changes.
	 * 
	 * @see org.kuali.student.common.ui.client.mvc.Controller#beforeViewChange(java.lang.Enum, org.kuali.student.common.ui.client.mvc.Callback)
	 */
	@Override
	public void beforeViewChange(Enum<?> viewChangingTo, Callback<Boolean> okToChange) {
		if(this.getCurrentView() instanceof Controller){
			((Controller)this.getCurrentView()).beforeViewChange(viewChangingTo, okToChange);
		}
		else{
			okToChange.exec(true);
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public boolean beforeHide() {
		return true;
	}

	/**
	 * Default implementation does nothing on before show.  Override to do other things before THIS view is
	 * shown.
	 * @see org.kuali.student.common.ui.client.mvc.View#beforeShow(org.kuali.student.common.ui.client.mvc.Callback)
	 */
	@Override
	public void beforeShow(Callback<Boolean> onReadyCallback) {
		onReadyCallback.exec(true);
	}

	@Override
	public Controller getController() {
		return parentController;
	}

	@Override
	public String getName() {
		if(name == null && viewType != null){
			return viewType.toString();
		}
		else{
			return name;
		}
	}

	@Override
	public Enum<?> getViewEnum() {
		return viewType;
	}
	
	public void setViewEnum(Enum<?> viewType){
		this.viewType= viewType;
	}
	
	/**
	 * Sets the name of this LayoutController.  This name is used in the breadcrumb and window's title.
	 * Setting the name to the empty string will omit the breadcrumb - this is sometimes desired.
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	public void setController(Controller controller){
		parentController = controller;
	}
	
	@Override
	public void collectBreadcrumbNames(List<String> names) {
		names.add(this.getName());
		if(this.getCurrentView() != null){
			this.getCurrentView().collectBreadcrumbNames(names);
		}
	}
	
	@Override
	public void clear() {
		
	}
	
}
