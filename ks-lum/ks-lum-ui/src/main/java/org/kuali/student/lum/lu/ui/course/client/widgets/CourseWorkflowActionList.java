package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.StylishDropDown;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.constants.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CourseWorkflowActionList extends StylishDropDown {
    private static final String MSG_GROUP = "course";
    
	private static final BlockingTask processingTask = new BlockingTask("Processing State Change....");
	private static final CourseRpcServiceAsync courseServiceAsync = GWT.create(CourseRpcService.class);    

    private KSMenuItemData modifyCourseActionItem;
	private KSMenuItemData activateCourseActionItem;
	private KSMenuItemData inactivateCourseActionItem;
	private KSMenuItemData retireCourseActionItem;
	private KSMenuItemData copyCourseActionItem;
	
	private final KSLightBox activateDialog = new KSLightBox();
	private VerticalSection activateSection = new VerticalSection();
    
    private boolean isCurrentVersion;
    private Boolean isInitialized = false;
    private String courseId;
       
    
    // Storing this list at multiple layers: here and in StylishDropDown.menu.items.  We need it here to test for empty
    private List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
    
    public CourseWorkflowActionList(String label) {
    	super(label);
    	
    	this.setVisible(false);
        this.addStyleName("KS-Workflow-DropDown");
    	
    }

	public CourseWorkflowActionList(String label, final ViewContext viewContext, final String modifyPath, DataModel model, final Callback<String> stateChangeCallback) {
    	super(label);
        
    	this.setVisible(false);
        this.addStyleName("KS-Workflow-DropDown");
        
        init(viewContext, modifyPath, model, stateChangeCallback);
	}
	
	public void init (final ViewContext viewContext, final String modifyPath, final DataModel model, final Callback<String> stateChangeCallback) {

		if (!this.isInitialized) {
	    	buildActivateDialog(stateChangeCallback);
	    	
	    	this.isCurrentVersion = true;
	    	
	    	// TODO: use messages

	    	// FIXME: This should check permissions for admin functionality rather than just check if admin user
            if ("admin".equals(Application.getApplicationContext().getUserId())){
		    	//Admin users have the option to make modifications to the course administratively or via the
            	//curriculum review process. Clicking on the "Modify Course" item will present the user with
            	//a modify dialog to allow them to choose the method of modification.
            	modifyCourseActionItem = new KSMenuItemData(this.getMessage("cluModifyItem"), new ClickHandler(){
		
					@Override
					public void onClick(ClickEvent event) {
				    	buildModifyDialog(viewContext, modifyPath, model);
					}
				});
            } else {
            	//Non-admin users are only allowed to make modifications via proposal curriculum review process.
            	//Clicking the "Modify Course" item will simply navigate user directly to modify course proposal screen.
		    	modifyCourseActionItem = new KSMenuItemData(this.getMessage("cluModifyItem"), new ClickHandler(){
		    		
					@Override
					public void onClick(ClickEvent event) {
						doModifyActionItem(viewContext, modifyPath, model);
					}
				});            	
            }
            
	    	copyCourseActionItem = new KSMenuItemData(this.getMessage("cluCopyItem"), new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
			    	if(hasCourseId(viewContext)){
			    		viewContext.setId((String)model.get(CreditCourseConstants.ID));
						viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
						viewContext.getAttributes().remove(StudentIdentityConstants.DOCUMENT_TYPE_NAME);
			        }
					HistoryManager.navigate(modifyPath, viewContext);
				}
			});
	    	activateCourseActionItem = new KSMenuItemData(this.getMessage("cluActivateItem"), new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
					showStateDialog(DtoConstants.STATE_ACTIVE);
				}
			});
	    	inactivateCourseActionItem = new KSMenuItemData(this.getMessage("cluInactivateItem") + " (Not Yet Implemented)", new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
						// TODO: Inactivate
				}
			});
	    	retireCourseActionItem = new KSMenuItemData(this.getMessage("cluRetireItem"), new ClickHandler(){
	
				@Override
				public void onClick(ClickEvent event) {
			    	if(hasCourseId(viewContext)){
						viewContext.setId(courseId);
						viewContext.setIdType(IdType.OBJECT_ID);
			        }
					
			    	Application.navigate(AppLocations.Locations.COURSE_RETIRE.getLocation(), viewContext);			    	
				}
			});
		}
		
		this.isInitialized = true;
    }
    
    private void doModifyActionItem(ViewContext viewContext, String modifyPath, DataModel model){
    	if(hasCourseId(viewContext)){
			viewContext.setId(getCourseVersionIndId(model));
			viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
            viewContext.setAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_MODIFY);
        }

		HistoryManager.navigate(modifyPath, viewContext);
    }
    
	private void showStateDialog(String newState) {
    	if (newState.equals(DtoConstants.STATE_RETIRED)) {
    		// TODO: create Retire dialog
    	} else if (newState.equals(DtoConstants.STATE_ACTIVE)) {     		
    		// TODO: use message e.g. activateCurrentInstr, activateModificationInstr    		
    		activateSection.setInstructions(getInstructions(newState));    				
        	activateDialog.show();
    	} else if (newState.equals(DtoConstants.STATE_INACTIVE)) {
    		// TODO: create Inactivate dialog
    	}
    	
    }
        
    private String getInstructions(String newState) {    	
    	if (isCurrentVersion){
    		// TODO: message
    		return "Activating this course makes it viewable and available for scheduling.";
    	} else { 
    		// TODO: message
    		return "Activate this course makes it viewable and available for scheduling. The previous version will be inactivated, and available for reference in the version history.";
    	}
    }
    
    private void buildActivateDialog(final Callback<String> stateChangeCallback){
	    FlowPanel panel = new FlowPanel();
	    
	    activateDialog.setMaxHeight(200);
	    activateDialog.setMaxWidth(200);
	    
	    // TODO: use messages
	    activateSection = new VerticalSection(SectionTitle.generateH2Title("Activate Course"));
	    
        panel.add((Widget)activateSection);
	    
	    KSButton activate = new KSButton("Activate",new ClickHandler(){
            public void onClick(ClickEvent event) {
                //activateSection.updateModel(cluModel);
                //set previous active to superseded
                //set this version to active
                setCourseState(courseId, DtoConstants.STATE_ACTIVE, stateChangeCallback);
                activateDialog.hide();                
            }
	    });
	    activateDialog.addButton(activate);
	    
	    KSButton cancel = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){
            public void onClick(ClickEvent event) {
                activateDialog.hide();
            }
	    });
	    activateDialog.addButton(cancel);

	    activateDialog.setWidget(panel);
    }
    
    private void buildModifyDialog(final ViewContext viewContext, final String modifyPath, final DataModel model){
    	final KSLightBox modifyDialog = new KSLightBox();
    	
    	//Create a dialog for course selection
    	modifyDialog.setTitle((getMessage("modifyCourse")));

        final VerticalPanel layout = new VerticalPanel();
        layout.addStyleName("ks-form-module-fields");
                
        final KSButton continueButton = new KSButton(getMessage("continue"));
        
        modifyDialog.addButton(continueButton);
        Anchor cancelLink = new Anchor("Cancel");
        cancelLink.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				modifyDialog.hide();
			}
        });
        modifyDialog.addButton(cancelLink);
        
        HorizontalPanel titlePanel = new HorizontalPanel();
        KSLabel titleLabel = new KSLabel(getMessage("modifyCourseSubTitle"));
        titleLabel.addStyleName("bold");
        titlePanel.add(titleLabel);
        
        layout.add(titlePanel);
        
        final KSRadioButton radioOptionModifyNoVersion = new KSRadioButton("modifyCreditCourseButtonGroup", getMessage("modifyCourseNoVersion"));
        final KSRadioButton radioOptionModifyWithVersion = new KSRadioButton("modifyCreditCourseButtonGroup", getMessage("modifyCourseWithVersion"));
        final KSCheckBox curriculumReviewOption = new KSCheckBox(getMessage("useCurriculumReview"));
        
        radioOptionModifyNoVersion.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(event.getValue()){
	            	curriculumReviewOption.setEnabled(false);
	            	curriculumReviewOption.setValue(false);
				}
			}
        });
        radioOptionModifyNoVersion.setValue(true);
    	curriculumReviewOption.setEnabled(false);
        
        radioOptionModifyWithVersion.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(event.getValue()){
	            	curriculumReviewOption.setEnabled(true);
				}
			}
        });
        
        continueButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (radioOptionModifyNoVersion.getValue()){
					viewContext.setId(courseId);
					viewContext.setIdType(IdType.OBJECT_ID);
					Application.navigate(AppLocations.Locations.COURSE_ADMIN_NO_VERSION.getLocation(), viewContext);
				} else if (radioOptionModifyWithVersion.getValue() && curriculumReviewOption.getValue()){
					doModifyActionItem(viewContext, modifyPath, model);
				} else if (radioOptionModifyWithVersion.getValue()){
			    	if(hasCourseId(viewContext)){
						viewContext.setId(getCourseVersionIndId(model));
						viewContext.setIdType(IdType.COPY_OF_OBJECT_ID);
			            //FIXME: This needs to use a new workflow document type for admin modify with version
						viewContext.setAttribute(StudentIdentityConstants.DOCUMENT_TYPE_NAME, LUConstants.PROPOSAL_TYPE_COURSE_MODIFY_ADMIN);
			        }

			    	Application.navigate(AppLocations.Locations.COURSE_ADMIN.getLocation(), viewContext);
				}	
		    	modifyDialog.hide();
			}        	
        });
        
        //Check that this is the latest version with an async call and only show modify with version options if it is the latest
       
        layout.add(radioOptionModifyNoVersion);
        if(isCurrentVersion){
        	layout.add(radioOptionModifyWithVersion);
            layout.add(curriculumReviewOption);
        }
        modifyDialog.setWidget(layout);
        modifyDialog.show();
    }
        
    // TODO: add Retire and Inactivate Dialogs
    
	/**
	 * Use this method to call the {@link CourseRpcServiceAsync#changeState(String, String, com.google.gwt.user.client.rpc.AsyncCallback)
	 * method, which will handle all related change state operations required for the course depending on the state being set.  
	 *  
	 * @param courseId
	 * @param newState
	 * @param stateChangeCallback The callback to execute to indicate if the state change call was successful.
	 */
    public static void setCourseState(final String courseId, final String newState, final Callback<String> stateChangeCallback) {
    	KSBlockingProgressIndicator.addTask(processingTask);
    	
    	courseServiceAsync.changeState(courseId, newState, new KSAsyncCallback<StatusInfo>() {
    		
    		@Override
 	        public void handleFailure(Throwable caught) {
 	            Window.alert("Error Updating State: "+caught.getMessage());
 	            KSBlockingProgressIndicator.removeTask(processingTask);
 	            stateChangeCallback.exec(null);
 	        }
 	
 	        @Override
 	        public void onSuccess(StatusInfo result) { 	        	
 	        	KSBlockingProgressIndicator.removeTask(processingTask);
 	        	stateChangeCallback.exec(newState);
 	        }
    	});
    	
    }
    
    
	/**
	 *  This depends heavily on {@link CourseStateUtil#setCourseState(String, String, Callback)}. 
	 *  Changes here will affect assumptions made there.
 	 *
	 */
    public void updateCourseActionItems(final DataModel cluModel) {
    	
		//First we need the model
		String courseVerIndId = getCourseVersionIndId(cluModel);
		Long courseVersionSequence = getCourseVersionSequenceNumber(cluModel);
		//Do an async check if this is the current version
		if(courseVerIndId==null){
			isCurrentVersion = true;
			doUpdateCourseActionItems(cluModel);
		}else{
			courseServiceAsync.isLatestVersion(courseVerIndId, courseVersionSequence, new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					KSNotifier.add(new KSNotification("Error determining latest version of course", false, 5000));
				}
				
				public void onSuccess(Boolean result) {
					isCurrentVersion = result;
					doUpdateCourseActionItems(cluModel);
				}
			});
		}
    }
    
	private void doUpdateCourseActionItems(DataModel cluModel) {
		
    	String cluState = cluModel.get("state");
    	courseId = cluModel.get(CreditCourseConstants.ID);
    	
    	items.clear();      
    	
    	if (cluState.equals(DtoConstants.STATE_APPROVED)) {
    		items.add(modifyCourseActionItem);
    		items.add(activateCourseActionItem);
    		if (isCurrentVersion) {
    			items.add(retireCourseActionItem);
    		}
    	} else if (cluState.equals(DtoConstants.STATE_ACTIVE)) {
    		items.add(modifyCourseActionItem);
    		items.add(inactivateCourseActionItem);
    		items.add(retireCourseActionItem);
    	} else if (cluState.equals(DtoConstants.STATE_INACTIVE)) {
    		items.add(activateCourseActionItem);
    	}
    	
		items.add(copyCourseActionItem);

    	setItems(items);
		
    	CourseWorkflowActionList.this.setEnabled(true);
		if(CourseWorkflowActionList.this.isEmpty()) {
			CourseWorkflowActionList.this.setVisible(false);
		}
		else{
			CourseWorkflowActionList.this.setVisible(true);
		}
	}
	
    public boolean isEmpty() {
    	return (items.size() == 0);
    }
    
    public String getMessage(String courseMessageKey) {
    	String msg = Application.getApplicationContext().getMessage(MSG_GROUP, courseMessageKey);
    	if (msg == null) {
    		msg = courseMessageKey;
    	}
    	return msg;
    }


    /** 
     * This checks the ViewContext passed in to determine if it contains a non-empty courseId
     * 
     * @return  true if view context contains non-empty courseId, false otherwise
     */
    private boolean hasCourseId(ViewContext viewContext){
    	return viewContext != null && viewContext.getId() != null && !viewContext.getId().isEmpty();
    }

    /**
     * Retrieves the version independent from the course data model
     * 
     * @param courseModel
     * @return version independent id of course
     */
    private String getCourseVersionIndId(DataModel courseModel){
    	return (String)courseModel.get(CreditCourseConstants.VERSION_INFO + QueryPath.getPathSeparator() + CreditCourseConstants.VERSION_IND_ID);    	
    }
    
    /**
     * Retrieves the version sequence number from the course data model
     * 
     * @param courseModel
     * @return version sequence number id of course
     */
    private Long getCourseVersionSequenceNumber(DataModel courseModel){
   		return (Long)courseModel.get(CreditCourseConstants.VERSION_INFO + QueryPath.getPathSeparator() + CreditCourseConstants.VERSION_SEQ_NUMBER);
    }
}
