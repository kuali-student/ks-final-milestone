package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelChangeEvent;
import org.kuali.student.common.ui.client.mvc.ModelChangeHandler;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcService;
import org.kuali.student.common.ui.client.service.ServerPropertiesRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Collaborators extends Composite implements HasWorkflowId{

    CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    ServerPropertiesRpcServiceAsync serverProperties = GWT.create(ServerPropertiesRpcService.class);

	Model<CluProposalModelDTO> cluProposalWorkflowModel=null;
    
//    private static final String ricePersonLookupUrl = "http://localhost:8081/ks-rice-dev/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.bo.impl.PersonImpl&docFormKey=88888888&returnLocation="+GWT.getHostPageBaseURL()+"sendResponse.html&hideReturnLink=false";
    private static final String urlParams = "?methodToCall=start&businessObjectClassName=org.kuali.rice.kim.bo.impl.PersonImpl&docFormKey=88888888&returnLocation="+GWT.getHostPageBaseURL()+"sendResponse.html&hideReturnLink=false";
    private String ricePersonLookupUrl = "http://localhost:8081/ks-rice-dev/kr/lookup.do" + urlParams;

	private String workflowId;

	private VerticalPanel collaboratorPanel = new VerticalPanel();
	private KSTextBox userIdField = new KSTextBox();
	private KSTextBox respondByField = new KSTextBox();
	private KSDropDown roleField = new KSDropDown();
	private KSDropDown adhocRequests = new KSDropDown();
	
    KSLightBox searchUserDialog = new KSLightBox();
    KSRadioButtonList participationField = new KSRadioButtonList("Participation");

    private VerticalPanel currentCollaborators = new VerticalPanel();

    private KSLabel coAuthorsLabel = new KSLabel();
    private KSLabel commentorsLabel = new KSLabel();
    private KSLabel viewersLabel = new KSLabel();
    private KSLabel delegatesLabel = new KSLabel();

	private VerticalPanel coAuthorUserIds = new VerticalPanel();
	private VerticalPanel commentorUserIds = new VerticalPanel();
	private VerticalPanel viewersUserIds = new VerticalPanel();
	private VerticalPanel delegatesUserIds = new VerticalPanel();

    final KRUserSearchIFrame userSearch = new KRUserSearchIFrame();
	
	public Collaborators(){
		super();
	    init();
    }
    private void init(){
        serverProperties.get("ks.rice.personLookup.serviceAddress", new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable caught) { //ignoring, we'll use the default
            	userSearch.setUrl(ricePersonLookupUrl);
            }

            @Override
            public void onSuccess(String result) {
                GWT.log("ServerProperties fetched for ks.rice.personLookup.serviceAddress: "+result, null);
                if(result != null){
                    ricePersonLookupUrl = result + urlParams;
                }
            	userSearch.setUrl(ricePersonLookupUrl);
            }

        });
        super.initWidget(collaboratorPanel);

        //Setup the collaborator type dropdown
        roleField.setBlankFirstItem(false);
        SimpleListItems roleListItems = new SimpleListItems();
        roleListItems.addItem("Co-Author", "Co-Author");
        roleListItems.addItem("Commentor", "Commentor");
        roleListItems.addItem("Viewer", "Viewer");
        roleListItems.addItem("Delegate", "Delegate");
        roleField.setListItems(roleListItems);
        
        //Setup list for Adhoc requests.
        adhocRequests.setBlankFirstItem(false);
        SimpleListItems adhoclist = new SimpleListItems();
        adhoclist.addItem("FYI", "FYI");
        adhoclist.addItem("Approve","Approve");
        adhoclist.addItem("Acknowledge","Acknowledge");
        adhocRequests.setListItems(adhoclist);
        
        //Setup the participation radio buttons
        participationField.setColumnSize(2);
        SimpleListItems participationListItems = new SimpleListItems();
        participationListItems.addItem("Required", "Required");
        participationListItems.addItem("Optional", "Optional");
        participationField.setListItems(participationListItems);

        //Setup the person search in an IFrame

        userSearch.addSelectionHandler(new SelectionHandler<String>(){
			public void onSelection(SelectionEvent<String> event) {
				userIdField.setValue(event.getSelectedItem());
				searchUserDialog.hide();
			}
        });

        userSearch.setSize("600px", "500px");

        VerticalPanel userLookupPanel = new VerticalPanel();

		userLookupPanel.add(userSearch);

		KSButton closeSearchButton = new KSButton("Cancel");
		closeSearchButton.addClickHandler(new ClickHandler(){
        	public void onClick(ClickEvent event) {
        		searchUserDialog.hide();
        	}
        });

		userLookupPanel.add(closeSearchButton);

		searchUserDialog.setWidget(userLookupPanel);

        //Create the button that opens the search dialog
		Hyperlink searchForPeopleLink = new Hyperlink("Advance Search","AdvancePeopleSearch");
        searchForPeopleLink.addClickHandler(new ClickHandler(){
        	public void onClick(ClickEvent event) {
        		userSearch.setUrl(ricePersonLookupUrl);
        		searchUserDialog.show();
        	}
        });

        //Create the button that invites collaborators
        KSButton inviteCollabButton = new KSButton("Invite Collaborators");
        inviteCollabButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				addCollaborator(userIdField.getValue(), roleField.getSelectedItem(), "Required".equals(participationField.getSelectedItem()), respondByField.getValue());
			}
        });

        // Create the button that invites FYIs
        KSButton adhocButton = new KSButton("Send");
        adhocButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                adhoc(userIdField.getValue(),adhocRequests.getSelectedItem(), "annotation");
            }
        });

        //Assemble the layout
        collaboratorPanel.add(new KSLabel("Collaborators & Delegates"));

        FlexTable table = new FlexTable();
        table.getFlexCellFormatter().setColSpan(6, 0, 6);
        table.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
//        table.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
    	table.setWidget(0, 0, new KSLabel("Name"));
    	table.setWidget(0, 1, new KSLabel("Role"));
    	table.setWidget(0, 2, new KSLabel("Participation"));
    	table.setWidget(0, 3, new KSLabel("Respond by"));
    	table.setWidget(1, 0, userIdField);
    	table.setWidget(1, 1, roleField);
    	table.setWidget(1, 2, participationField);
    	table.setWidget(1, 3, respondByField);
    	table.setWidget(2, 0, searchForPeopleLink);
        table.setWidget(3, 0, inviteCollabButton);
    	table.setWidget(4, 0, new KSLabel("AdHoc Request"));
    	table.setWidget(5, 0, adhocRequests);
    	table.setWidget(5, 1, adhocButton);
    	


        collaboratorPanel.add(table);

    	//Add the list of current collaborators
        currentCollaborators.add(coAuthorsLabel);
    	currentCollaborators.add(new KSLabel("Co-Author can make edits to the proposal directly."));
    	currentCollaborators.add(coAuthorUserIds);

    	currentCollaborators.add(commentorsLabel);
    	currentCollaborators.add(new KSLabel("Commentors can read and write commonts for the proposal."));
    	currentCollaborators.add(commentorUserIds);

    	currentCollaborators.add(viewersLabel);
    	currentCollaborators.add(new KSLabel("Viewers may see the document but not edit it."));
    	currentCollaborators.add(viewersUserIds);

    	currentCollaborators.add(delegatesLabel);
    	currentCollaborators.add(new KSLabel("Delegates may make edits to the proposal and submit."));
    	currentCollaborators.add(delegatesUserIds);

    	coAuthorsLabel.setText("Co-Authors (0)");
    	commentorsLabel.setText("Commentors (0)");
    	viewersLabel.setText("Viewers (0)");
    	delegatesLabel.setText("Delegates (0)");

        collaboratorPanel.add(currentCollaborators);
        
        collaboratorPanel.add(new WorkflowButtonsWidget());
    }


	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	protected void onLoad() {

		super.onLoad();
		
		if(null==cluProposalWorkflowModel){
			
			//Get the Model from the controller and register a model change handler when the workflow model is updated
			Controller.findController(this).requestModel(CluProposalModelDTO.class, new ModelRequestCallback<CluProposalModelDTO>(){
			
				@Override
				public void onModelReady(Model<CluProposalModelDTO> model) {
					
					//After we get the model update immediately
					cluProposalWorkflowModel = model;
					updateWorkflowId(cluProposalWorkflowModel.get());
					
					//Add a change listener for when the model changes
					model.addModelChangeHandler(new ModelChangeHandler<CluProposalModelDTO>(){
						@Override
						public void onModelChange(ModelChangeEvent<CluProposalModelDTO> event) {
							updateWorkflowId(event.getValue());
						}
					});
				}
	
				@Override
				public void onRequestFail(Throwable cause) {
					Window.alert("Model Request Failed. "+cause.getMessage());
				}
			});
		}else{
			updateWorkflowId(cluProposalWorkflowModel.get());
		}
	}
    
	private void updateWorkflowId(CluProposalModelDTO model){
		StringType cluIdType = (StringType)model.get("id");
		String cluId = null==cluIdType?"":cluIdType.get();

		cluProposalRpcServiceAsync.getWorkflowIdFromCluId(cluId, new AsyncCallback<String>(){
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
				workflowId=result;
			}
		});
	}
	
    public void refreshCollaboratorList(){
    	if(workflowId!=null){
	        cluProposalRpcServiceAsync.getCollaborators(workflowId, new AsyncCallback<HashMap<String,ArrayList<String>>>(){
				public void onFailure(Throwable caught) {
				}
				public void onSuccess(HashMap<String,ArrayList<String>> result) {
					coAuthorUserIds.clear();
					if(result.containsKey("Co-Author")){
						for(String id:result.get("Co-Author")){
							coAuthorUserIds.add(new KSLabel(id));
						}
						coAuthorsLabel.setText("Co-Authors ("+coAuthorUserIds.getWidgetCount()+")");
					}

					commentorUserIds.clear();
					if(result.containsKey("Commentor")){
						for(String id:result.get("Commentor")){
							commentorUserIds.add(new KSLabel(id));
						}
						commentorsLabel.setText("Commentors ("+commentorUserIds.getWidgetCount()+")");
					}

					viewersUserIds.clear();
					if(result.containsKey("Viewer")){
						for(String id:result.get("Viewer")){
							viewersUserIds.add(new KSLabel(id));
						}
						viewersLabel.setText("Viewers ("+viewersUserIds.getWidgetCount()+")");
					}

					delegatesUserIds.clear();
					if(result.containsKey("Delegate")){
						for(String id:result.get("Delegate")){
							delegatesUserIds.add(new KSLabel(id));
						}
						delegatesLabel.setText("Delegates ("+delegatesUserIds.getWidgetCount()+")");
					}
				}
	        });
    	}
    }

    private void addCollaborator(final String recipientPrincipalId, final String collabType, boolean participationRequired, String respondBy){
    	if(workflowId==null){
            GWT.log("Collaborators called with "+ricePersonLookupUrl, null);
    		Window.alert("Workflow must be started before Collaborators can be added");
    	}else{
	    	cluProposalRpcServiceAsync.addCollaborator(workflowId, recipientPrincipalId,collabType,participationRequired, respondBy, new AsyncCallback<Boolean>(){
				public void onFailure(Throwable caught) {
					Window.alert("Could not add Collaborator");
				}

				public void onSuccess(Boolean result) {
					userIdField.setValue("");
					//Add to the list and no refresh even though we should because rice has a timing issue
					if("Co-Author".equals(collabType)){
						coAuthorUserIds.add(new KSLabel(recipientPrincipalId));
						coAuthorsLabel.setText("Co-Authors ("+coAuthorUserIds.getWidgetCount()+")");
					}else if("Commentor".equals(collabType)){
						commentorUserIds.add(new KSLabel(recipientPrincipalId));
						commentorsLabel.setText("Commentors ("+commentorUserIds.getWidgetCount()+")");
					}else if("Viewer".equals(collabType)){
						viewersUserIds.add(new KSLabel(recipientPrincipalId));
						viewersLabel.setText("Viewers ("+viewersUserIds.getWidgetCount()+")");
					}else if("Delegate".equals(collabType)){
						delegatesUserIds.add(new KSLabel(recipientPrincipalId));
						delegatesLabel.setText("Delegates ("+delegatesUserIds.getWidgetCount()+")");
					}
					//refreshCollaboratorList();
				}

	    	});
    	}
    }

    private void adhoc(final String recipientPrincipalId, final String requestType, final String annotation) {
        if (workflowId == null) {
            GWT.log("Fyis called with " + ricePersonLookupUrl, null);
            Window.alert("Workflow must be started before Adhocs can be sent");
        } else {
            cluProposalRpcServiceAsync.adhocRequest(workflowId, recipientPrincipalId,requestType,
                    annotation,
                    new AsyncCallback<Boolean>() {
                        public void onFailure(Throwable caught) {
                            Window.alert("Adhoc request failed");
                        }

                        public void onSuccess(Boolean result) {
                            userIdField.setValue("");
                            Window.alert(requestType + " sent to user " + recipientPrincipalId);
                        }

                    });
        }
    }

	@Override
	public String getWorkflowId() {
		return workflowId;
	}
	@Override
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}



}
