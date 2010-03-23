package org.kuali.student.lum.lu.ui.course.client.widgets;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.ToolView;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.AbstractSimpleModel;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent.Action;
import org.kuali.student.common.ui.client.mvc.history.HistoryStackFrame;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.table.SimpleWidgetTable;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.lum.lu.dto.workflow.WorkflowPersonInfo;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.WorkflowToolRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.WorkflowToolRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollaboratorTool extends Composite implements ToolView{
    private WorkflowToolRpcServiceAsync workflowRpcServiceAsync = GWT.create(WorkflowToolRpcService.class);
    //Move methods called from this to workflow rpc^
    CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
    private Metadata workflowAttrMeta = null;

    private Enum<?> viewEnum;
    private String viewName;
    private Controller controller;
    private String dataId = null;
    private String workflowId = null;
    
    private FlowPanel layout = new FlowPanel();
    private final GroupSection section;
    private FieldDescriptor person;
    private FieldDescriptor permissions;
    private KSLinkButton addButton = new KSLinkButton("Add Person", ButtonStyle.PRIMARY);
    private SimpleWidgetTable table;
    private VerticalSection tableSection;
    
    //Todo MESSAGES
    private final String VIEW = "View";
    private final String COMMENT_VIEW = "Comment, View";
    private final String EDIT_COMMENT_VIEW = "Edit, Comment, View";
    
    public class PermissionList extends KSDropDown{
        public PermissionList(){
        	this.setBlankFirstItem(false);
            SimpleListItems permissionListItems = new SimpleListItems();
            permissionListItems.addItem("Co-Author", EDIT_COMMENT_VIEW);
            permissionListItems.addItem("Commentor", COMMENT_VIEW);
            permissionListItems.addItem("Viewer", VIEW);

            super.setListItems(permissionListItems);
            
            this.selectItem("Viewer");
        }
    }
    
    private PermissionList permissionList = new PermissionList();
    
	public static class CollaboratorModel extends AbstractSimpleModel {
		private String dataId;

		public String getDataId() {
			return dataId;
		}
		
		public void setDataId(String dataId) {
			this.dataId = dataId;
			super.fireChangeEvent(Action.UPDATE);
		}
	}
    
    public CollaboratorTool(Enum<?> viewEnum, String viewName, SectionTitle title){
    	if(title != null){
    		section = new GroupSection(title);
    	}
    	else{
    		section = new GroupSection();
    	}
        this.viewEnum = viewEnum;
        this.viewName = viewName;
        List<String> columns = new ArrayList<String>();
        columns.add("Name");
        columns.add("Permissions");
        columns.add("Workflow Action");
        columns.add("Request Status");
        
        //TODO add this in later
        //columns.add("Remove Person");
        table = new SimpleWidgetTable(columns);
    	
		workflowRpcServiceAsync.getMetadata("workflow", null, new AsyncCallback<Metadata>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("error getting meta", caught);
			}

			@Override
			public void onSuccess(Metadata result) {
				CollaboratorTool.this.workflowAttrMeta = result;
				createAddCollabSection();
				//createTableSection();
			}
		});
		
		
		layout.add(section);
		layout.add(addButton);
		addButton.addStyleName("ks-section-widget");
		addButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				//DataModel data = new DataModel();
				String personId = null;
				if(person != null){
					Widget w = person.getFieldWidget();
					if(w instanceof KSPicker){
						personId = (String)(((KSPicker) w).getValue().get());
					}
				}
				String permission = "";
				if(permissions != null){
					permission = permissionList.getSelectedItem();
				}
				//TODO last 2 are hardcoded, dont know what to do here
				CollaboratorTool.this.addCollaborator(personId, permission, true, "");
				
			}
		});

		layout.add(createTableSection());
		this.initWidget(layout);
    }

    
	@Override
	public boolean beforeHide() {
		return true;
	}

	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		section.redraw();
		controller.requestModel(CollaboratorModel.class, new ModelRequestCallback<CollaboratorModel>(){

			@Override
			public void onModelReady(CollaboratorModel model) {
				if(model.getDataId() != null && !model.getDataId().equals("")){
					dataId = model.getDataId();
					if(workflowId == null){
						cluProposalRpcServiceAsync.getWorkflowIdFromDataId(dataId, new AsyncCallback<String>(){
							@Override
							public void onFailure(Throwable caught) {
								//Window.alert("Getting workflowId failed");
								workflowId = null;
								refreshCollaboratorTable();
								onReadyCallback.exec(true);
							}
	
							@Override
							public void onSuccess(String result) {
								//Window.alert("Getting workflowId succeeded");
								workflowId=result;
								refreshCollaboratorTable();
								onReadyCallback.exec(true);
							}


						});
					}
					else{
						refreshCollaboratorTable();
						onReadyCallback.exec(true);
					}
					//get collaborators here
					
				}
				else{
					//alter interface to reflect that they havent saved yet
					onReadyCallback.exec(true);
				}
				
				
			}

			@Override
			public void onRequestFail(Throwable cause) {
				// TODO Auto-generated method stub
				onReadyCallback.exec(true);
			}
		});
		
/*		workflowRpcServiceAsync.getMetadata("workflow", null, new AsyncCallback<Metadata>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("error getting meta", caught);
			}

			@Override
			public void onSuccess(Metadata result) {
				// TODO Auto-generated method stub
				System.out.println("got here!!!");
				Metadata personIdMeta = result
				FieldDescriptor fd = new FieldDescriptor(null, "Person", personIdMeta);
				section.addField(fd);
				//fd.getWidgetBinding().getV
				//addField(section, "workflow/personId", "Person");
				onReadyCallback.exec(true);
			}
		});*/
		
	}
	
	private void createAddCollabSection(){
		Metadata personIdMeta = CollaboratorTool.this.workflowAttrMeta.getProperties().get("personId");
		person = new FieldDescriptor(null, "Person", personIdMeta);
		Metadata typeMeta = CollaboratorTool.this.workflowAttrMeta.getProperties().get("collaboratorType");
		permissions = new FieldDescriptor(null, "Permissions", typeMeta);
		permissions.setFieldWidget(permissionList);
		section.addField(person);
		section.addField(permissions);
		//if submitted(?) then show workflow action request here
		
	}
	
	private Widget createTableSection(){
		tableSection = new VerticalSection(SectionTitle.generateH3Title("Added People"));
		tableSection.addWidget(table);
		
		//tab
		//tableSection.addWidget(new Label("No data"));
		return tableSection;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Controller getController() {
		return controller;
	}

	@Override
	public void updateModel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collectHistory(HistoryStackFrame frame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHistoryEvent(HistoryStackFrame frame) {
		// TODO Auto-generated method stub
		
	}
	

    /**
     * @see org.kuali.student.common.ui.client.mvc.View#getName()
     */
    @Override
    public String getName() {
        return this.viewName;
    }

    public Enum<?> getViewEnum() {
        return viewEnum;
    }
    
    public void setController(Controller controller){
        this.controller = controller;
    }
    
    private void addCollaborator(final String recipientPrincipalId, final String selectedPermission, boolean participationRequired, String respondBy){
    	if(workflowId==null){
            //GWT.log("Collaborators called with "+ricePersonLookupUrl, null);
    		Window.alert("Workflow must be started before Collaborators can be added");
    	}else{
    		//TODO put real title in
    		workflowRpcServiceAsync.addCollaborator(workflowId, dataId, "title here", recipientPrincipalId, selectedPermission, participationRequired, respondBy, new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					Window.alert("Could not add Collaborator");
					GWT.log("could not add collaborator", caught);
				}

				public void onSuccess(Boolean result) {
					Widget w = person.getFieldWidget();
					if(w instanceof KSPicker){
						((KSPicker)w).setValue("");
					}
					//permissionList.clear();
					//permissionList.selectItem("Viewer");
					//Add to the list and no refresh even though we should because rice has a timing issue
/*					if("Co-Author".equals(collabType)){
						coAuthorUserIds.add(new KSLabel(recipientPrincipalId+" was added"));
						coAuthorsLabel.setText("Co-Authors ("+coAuthorUserIds.getWidgetCount()+")");
					}else if("Commentor".equals(collabType)){
						commentorUserIds.add(new KSLabel(recipientPrincipalId+" was added"));
						commentorsLabel.setText("Commentors ("+commentorUserIds.getWidgetCount()+")");
					}else if("Viewer".equals(collabType)){
						viewersUserIds.add(new KSLabel(recipientPrincipalId+" was added"));
						viewersLabel.setText("Viewers ("+viewersUserIds.getWidgetCount()+")");
					}else if("Delegate".equals(collabType)){
						delegatesUserIds.add(new KSLabel(recipientPrincipalId+" was added"));
						delegatesLabel.setText("Delegates ("+delegatesUserIds.getWidgetCount()+")");
					}*/
					refreshCollaboratorTable();
				}

	    	});
    	}
    }
    
	public void refreshCollaboratorTable() {
		if(workflowId!=null){
			workflowRpcServiceAsync.getCollaborators(workflowId, new AsyncCallback<List<WorkflowPersonInfo>>(){
				public void onFailure(Throwable caught) {
					Window.alert("Getting Collaborators failed");
				}
				public void onSuccess(List<WorkflowPersonInfo> result) {
					
					int numberCollabs = 0;
					table.clear();
					
					for(WorkflowPersonInfo person: result){
						List<Widget> rowWidgets = new ArrayList<Widget>();
						if(person.getFirstName() != null && person.getLastName() != null){
							rowWidgets.add(new KSLabel(person.getFirstName() + " " + person.getLastName()));
						}
						else{
							rowWidgets.add(new KSLabel(person.getPrincipalId()));
						}
						
						String permString = "";
						int count = 0;
						for(String perm: person.getPermList()){
							if(perm != null){
								if(count > 0){
									permString = permString + ", " + perm;
								}
								else{
									permString = permString + perm;
								}
								count++;
							}
						}
						rowWidgets.add(new KSLabel(permString));
						
						String actionString = "";
						count = 0;
						for(String action: person.getActionList()){
							if(action != null){
								if(count > 0){
									actionString = actionString + ", " + action;
								}
								else{
									actionString = actionString + action;
								}
								count++;
							}
						}
						rowWidgets.add(new KSLabel(actionString));
						if(numberCollabs > 0){
							tableSection.setSectionTitle(SectionTitle.generateH3Title("Added People (" + numberCollabs + ")"));
						}
						rowWidgets.add(new KSLabel(person.getActionRequestStatus()));
						//TODO add back in when we have remove
/*
						if (person.isCanRevokeRequest()) {
							KSLinkButton remove = new KSLinkButton("X", ButtonStyle.DELETE);
							remove.addClickHandler(new ClickHandler() {
	
								@Override
								public void onClick(ClickEvent event) {
									Boolean ok = Window.confirm("Are you sure you want to revoke workflow request " + id + "?");
									if (ok) {
										// delete and refresh
									}
								}
							});
							rowWidgets.add(remove);
						}
						else {
							rowWidgets.add(new KSLabel(""));
						}
*/
						table.addRow(rowWidgets);
						
						numberCollabs++;
						
						
					}
					
				}
	        });
    	}
		
	}


	@Override
	public KSImage getImage() {
		return Theme.INSTANCE.getCommonImages().getPersonIcon();
	}
	

}
