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

import java.util.Collection;
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
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.event.ValidateResultHandler;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class LayoutController extends Controller implements ViewLayoutController, View {

	protected Map<Enum<?>, View> viewMap = new LinkedHashMap<Enum<?>, View>();
	protected Map<String, Enum<?>> viewEnumMap = new HashMap<String, Enum<?>>();
	protected Enum<?> defaultView;
	
	protected String name;
	protected Enum<?> viewType;
	protected Controller parentController;
	
    protected View startPopupView;
    protected KSLightBox startViewWindow;
	
    public LayoutController(String controllerId){
        super(controllerId);
		addApplicationEventHandler(ValidateResultEvent.TYPE, new ValidateResultHandler() {
            @Override
            public void onValidateResult(ValidateResultEvent event) {
               List<ValidationResultInfo> list = event.getValidationResult();
               LayoutController.this.processValidationResults(list);
            }
        });
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
    }
    
    public void processValidationResults(List<ValidationResultInfo> list){
    	//Collection<View> sections = sectionViewMap.values();
    	Collection<View> sections = viewMap.values();
        for(View v: sections){
     	   if(v instanceof org.kuali.student.common.ui.client.configurable.mvc.views.SectionView){
     		   ((org.kuali.student.common.ui.client.configurable.mvc.views.SectionView) v).processValidationResults(list);
     	   }
        }
    }
    
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
    
	public void addStartViewPopup(final View view){
	    startPopupView = view;
	    if(startViewWindow == null){
	    	startViewWindow = new KSLightBox();
	    }

	    HorizontalPanel buttonPanel = new HorizontalPanel();

	    FlowPanel panel = new FlowPanel();
	    panel.add(view.asWidget());
	    buttonPanel.add(new KSButton("Save",new ClickHandler(){
            public void onClick(ClickEvent event) {
                view.updateModel();
                SaveActionEvent saveActionEvent = new SaveActionEvent();

                saveActionEvent.setActionCompleteCallback(new ActionCompleteCallback(){
                    public void onActionComplete(ActionEvent action) {
                        startViewWindow.hide();
                    }
                });

                fireApplicationEvent(saveActionEvent);
            }
	    }));
	    buttonPanel.add(new KSButton("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {
                startViewWindow.hide();
            }
	    }));

	    panel.add(buttonPanel);
	    //TODO setController should be a method on all Views
	    if(view instanceof SectionView){
	    	((SectionView) view).setController(this);
	    }
	    startViewWindow.setWidget(panel);
	}
	
    public boolean isStartViewShowing(){
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


    /*New methods*/
	
	public void addView(View view){
		viewMap.put(view.getViewEnum(), view);
		viewEnumMap.put(view.getViewEnum().toString(), view.getViewEnum());
		if(view instanceof SectionView){
			((SectionView) view).setController(this);
		}
		else if(view instanceof ToolView){
			((ToolView) view).setController(this);
		}
	}
	
	public <V extends Enum<?>> void setDefaultView(V viewType){
		this.defaultView = viewType;
	}
	
	public abstract void updateModel();
	
	public void updateModelFromView(Enum<?> viewType){
		View v = viewMap.get(viewType);
		if(v != null){
			v.updateModel();
		}
	}
	
	public void updateModelFromCurrentView(){
		this.getCurrentView().updateModel();
	}


	@Override
	protected <V extends Enum<?>> View getView(V viewType) {
		return viewMap.get(viewType);
	}

	@Override
	public Enum<?> getViewEnumValue(String enumValue) {
		return viewEnumMap.get(enumValue);
	}

	//TODO remove this method from controller hierarchy?  its not used
	@Override
	public Class<? extends Enum<?>> getViewsEnum() {
		return null;
	}

	@Override
	public void showDefaultView(Callback<Boolean> onReadyCallback) {
		if(!viewMap.isEmpty()){		
			if(defaultView == null){
				showView(viewMap.entrySet().iterator().next().getKey(), onReadyCallback);
			}
			else{
				showView(defaultView, onReadyCallback);
			}
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
		boolean isValid = true;

		if (checkCurrentSectionOnly){
			//Check for validation errors on the currently displayed section only
	    	//if(this.isStartSectionShowing()){
	    		//isValid = isValid(validationResults, getStartSection());
	    	//} else {
	    		View v = getCurrentView();
	        	if(v instanceof Section){
	        		isValid = isValid(validationResults, (Section)v);
	        	//}
	    	}
		} else {
			//Check for validation errors on all sections
			//container.clearMessages();
			String errorSections = "";
			StringBuilder errorSectionsbuffer = new StringBuilder();
			errorSectionsbuffer.append(errorSections);
			for (Entry<Enum<?>, View> entry:viewMap.entrySet()) {
				View v = entry.getValue();
				if (v instanceof Section){
					if (!isValid(validationResults, (Section)v)){
						isValid = false;
						errorSectionsbuffer.append(((SectionView)v).getName() + ", ");
					}
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

	private boolean isValid(List<ValidationResultInfo> validationResults, Section section){
		section.setFieldHasHadFocusFlags(true);
		ErrorLevel status = section.processValidationResults(validationResults);

		return (status != ErrorLevel.ERROR);
	}
	
	@Override
	public void beforeViewChange(Callback<Boolean> okToChange) {
		if(this.getCurrentView() instanceof Controller){
			((Controller)this.getCurrentView()).beforeViewChange(okToChange);
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
