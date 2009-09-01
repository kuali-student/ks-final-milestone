package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.RowDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.ui.client.configurable.mvc.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * An vertical layout implementation of SectionView. Fields to this section view
 * will be added in a vertical layout. 
 * 
 * @author Kuali Student Team
 *
 */
public class CustomSectionView extends SectionView {
    
    protected final VerticalPanel panel = new VerticalPanel();
	private boolean loaded = false;
	CourseReqManager childController;
	
	private Class<? extends ModelDTO> modelDTOType;
	
	private Model<ModelDTO> model = null;
		
	public CustomSectionView(Enum<?> viewEnum, String name, Class<? extends ModelDTO> modelDTOType) {	    
		super(viewEnum, name);
	    super.initWidget(panel);
	    this.modelDTOType = modelDTOType; 
	}
		
	public void beforeShow(){	
		
		if (loaded == false) {
			childController = new CourseReqManager(panel);
		}
		
		//Request model and redraw view if model changed
	    getController().requestModel(modelDTOType, new ModelRequestCallback<ModelDTO>(){
            public void onModelReady(Model<ModelDTO> m) {
                //if (model != m){
                    model = m;
                    redraw();
                //}                    
            }

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get model");
            }
            
        });		
		
        if (childController.getCurrentView() == null){
            childController.showDefaultView();
        }
        
        loaded = true;
	}
	
	public void clear(){
	}

    @Override	
	public void redraw(){
		super.updateView(model.get());
	}			

	@Override
	public void updateModel() {
	}

	@Override
	public void validate(Callback<ErrorLevel> callback) {
		// TODO Auto-generated method stub
		
	}
}
