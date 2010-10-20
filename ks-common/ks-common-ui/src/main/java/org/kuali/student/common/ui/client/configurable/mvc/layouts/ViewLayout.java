package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

public abstract class ViewLayout extends Controller{

	public Map<Enum<?>, View> viewMap = new LinkedHashMap<Enum<?>, View>();
	public Map<String, Enum<?>> viewEnumMap = new HashMap<String, Enum<?>>();
	public Enum<?> defaultView;
	
	public ViewLayout(String controllerId) {
		super(controllerId);
	}
	
	public void addView(View view){
		viewMap.put(view.getViewEnum(), view);
		viewEnumMap.put(view.getViewEnum().toString(), view.getViewEnum());
	}
	
	public <V extends Enum<?>> void setDefaultView(V viewType){
		this.defaultView = viewType;
	}

	@Override
	protected <V extends Enum<?>> void getView(V viewType, Callback<View> callback) {
		callback.exec(viewMap.get(viewType));
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
}
