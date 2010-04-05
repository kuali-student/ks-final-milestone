/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.event.ValidateResultEvent;
import org.kuali.student.common.ui.client.event.ValidateResultHandler;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.client.ui.Widget;

public abstract class LayoutController extends Controller implements ConfigurableLayout {
    private LayoutController parentLayoutController= null; 
  
	protected final HashMap<String, View> sectionViewMap = new HashMap<String, View>();	
	
    public LayoutController(String controllerId){
        super(controllerId);
		addApplicationEventHandler(ValidateResultEvent.TYPE, new ValidateResultHandler() {
            @Override
            public void onValidateResult(ValidateResultEvent event) {
               List<ValidationResultContainer> list = event.getValidationResult();
               LayoutController.this.processValidationResults(list);
            }
        });
    }
    
    public void processValidationResults(List<ValidationResultContainer> list){
    	Collection<View> sections = sectionViewMap.values();
        for(View v: sections){
     	   if(v instanceof org.kuali.student.common.ui.client.configurable.mvc.views.SectionView){
     		   ((org.kuali.student.common.ui.client.configurable.mvc.views.SectionView) v).processValidationResults(list);
     	   }
        }
    }
    
    public ErrorLevel checkForErrors(List<ValidationResultContainer> list){
		ErrorLevel errorLevel = ErrorLevel.OK;
		
		for(ValidationResultContainer vr: list){
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
    
    public void setParentLayout(LayoutController controller) {
        parentLayoutController = controller;
    }
    
    public LayoutController getParentLayout() {
        if (parentLayoutController == null) {
            parentLayoutController = LayoutController.findParentLayout(this);
        }
        return parentLayoutController;
    }
    
	/**
 	 * Check to see if current/all section(s) is valid (ie. does not contain any errors)
 	 * 
	 * @param validationResults List of validation results for the layouts model.
	 * @param checkCurrentSectionOnly true if errors should be checked on current section only, false if all sections should be checked 
	 * @return true if the specified sections (all or current) has any validation errors
	 */
	public abstract boolean isValid(List<ValidationResultContainer> validationResults, boolean checkCurrentSectionOnly);
}
