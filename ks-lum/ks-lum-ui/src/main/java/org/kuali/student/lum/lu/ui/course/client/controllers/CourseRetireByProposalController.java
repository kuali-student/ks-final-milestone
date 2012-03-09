/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.controllers;

import java.util.List;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.core.workflow.ui.client.widgets.WorkflowUtilities;
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseAdminRetireConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseProposalConfigurer;
import org.kuali.student.lum.lu.ui.course.client.configuration.CourseRetireByProposalConfigurer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * New controller for Faculty Retire Course screen.  
 * Overrides getRPC() to return CourseRetireByProposalRpc
 * Overrides init to use CourseRetireByProposalConfigurer
 * @author mike
 *
 */

public class CourseRetireByProposalController extends CourseProposalController {


	public CourseRetireByProposalController() {
		super(); 
	}

	
	 /**
	  *  Override the intitailzeController method to use CourseRetireByProposalConfigurer 
     */			
		@Override
		protected void initializeController() {
	   		
			super.cfg = GWT.create(CourseRetireByProposalConfigurer.class);
	   		cfg.setState(DtoConstants.STATE_RETIRED.toUpperCase());
	   		super.setDefaultModelId(cfg.getModelId());
	   		super.registerModelsAndHandlers();
	   	//	super.addStyleName("courseProposal");  	   		   		
	   		currentDocType = LUConstants.PROPOSAL_TYPE_COURSE_CREATE_ADMIN;	  //FIXME replace with new WF doc type when ready.  		
        //  setViewContext(getViewContext());
    }
	
	
	 
	@Override
    protected  BaseDataOrchestrationRpcServiceAsync getCourseProposalRpcService(){
    	return cluProposalRpcServiceAsync;  //FIXME replace with new data service when ready
    }
    
	
}
