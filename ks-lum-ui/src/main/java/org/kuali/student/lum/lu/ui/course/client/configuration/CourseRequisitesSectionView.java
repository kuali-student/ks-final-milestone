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

package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * Displays Rules pages within this section, allowing end user to navigate between rules screens
 * without using or affecting menu on the left.  
 * 
 * @author Kuali Student Team
 *
 */
public class CourseRequisitesSectionView extends SectionView {
    
    protected final VerticalPanel panel = new VerticalPanel();
	private boolean loaded = false;
	CourseReqManager childController;	//controls the display of all rules related pages
    private DataModel model;
    private String modelId;
    private List<Metadata> searchLookupData;

	public CourseRequisitesSectionView(Enum<?> viewEnum, String name, String modelId, List<Metadata> searchLookupData ) {	    
		super(viewEnum, name);
		layout = new VerticalFieldLayout();
		layout.add(panel);
        this.add(layout);
        this.modelId = modelId;	
        this.searchLookupData = searchLookupData;
	}
	
    @Override
	public void beforeShow(final Callback<Boolean> onReadyCallback){	
		
		if (loaded == false) {
			childController = new CourseReqManager(panel);
			childController.setParentController(getController());
			childController.setFieldsWithLookup(searchLookupData);
		}
				
        if (childController.getCurrentView() == null){
            childController.showDefaultView(onReadyCallback);
        } else {
        	onReadyCallback.exec(true);
        }
        
        loaded = true;
        
        //Request model and redraw view
        getController().requestModel(modelId, new ModelRequestCallback<DataModel>(){

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model: " + getName());
                onReadyCallback.exec(false);
            }

            @Override
            public void onModelReady(DataModel m) {
                model = m;
                CourseRequisitesSectionView.this.updateWidgetData(m);
                onReadyCallback.exec(true);
            }
            
        });        
	}
	
	public void clear(){
	}		

	@Override
	public void updateModel() {
		//don't save application state if no state changed i.e. the view was not even loaded
		if (childController != null) {
			childController.saveApplicationState(model);
		}
	}

	@Override
	public ErrorLevel processValidationResults(List<ValidationResultInfo> results) {
		return ErrorLevel.OK;
	}

}
