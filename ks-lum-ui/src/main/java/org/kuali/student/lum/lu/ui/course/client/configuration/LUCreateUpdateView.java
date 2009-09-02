package org.kuali.student.lum.lu.ui.course.client.configuration;



import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.history.KSHistory;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;

public class LUCreateUpdateView extends ViewComposite {
    private final SimplePanel panel = new SimplePanel();
    private DefaultCreateUpdateLayout<CluProposal> layout;
    private String luType;
    private String luState;
    private String id = null;
    private boolean isCreate = false;
//    private KSButton wfCompleteButton;
    private KSButton wfApproveButton;
    private KSButton wfDisApproveButton;
    private KSButton wfAcknowledgeButton;
    private KSButton wfStartWorkflowButton;
    
    CluProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CluProposalRpcService.class);
    
    public LUCreateUpdateView(Controller controller, String type, String state) {
        super(controller, "LuCreateView");
        init(controller, type, state, true);
    }

    public LUCreateUpdateView(Controller controller, String type, String state, boolean isCreate) {
        super(controller, (isCreate?"LuCreateView":"LuUpdateView"));
        init(controller, type, state, isCreate);
    }

    private void init(Controller controller, String type, String state, boolean isCreate) {	   	
        this.luType = type;
        this.luState = state;
        this.isCreate = isCreate;
        initWidget(panel);

        LULayoutFactory factory = new LULayoutFactory();

        DefaultCreateUpdateLayout<CluProposal> defaultLayout = (DefaultCreateUpdateLayout<CluProposal>)factory.getLayout(type, state);
//      history = new KSHistory(getController(), layout);
        defaultLayout.addCancelSectionHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {                
                LUCreateUpdateView.this.getController().showView(LUMViews.HOME_MENU);
            }            
        });     

        defaultLayout.setShowStartSectionEnabled(isCreate);

        this.layout = defaultLayout;
        
//        if (isCreate){
//            //Add workflow buttons and workflow event handlers
//
//            controller.addApplicationEventHandler(WorkflowActionEvent.TYPE, 
//                    new WorkflowActionHandler(){
//
//                        @Override
//                        public void doWorkflowAction(WorkflowActionEvent event) {
//                            Window.alert("Approving workflow");
//                            event.setEventState(EventState.SUCCESS);
//                        }                
//                }
//            );
//        }
    }

    @Override
    public boolean beforeHide() {
        // TODO check if there are unsaved changes
        return true;
    }

    @Override
    public void beforeShow() {
        IncrementalCommand command = new IncrementalCommand() {

            @Override
            public boolean execute() {
                if(layout == null) {
                    return true; //wait for stupid thing to load
                }
                panel.setWidget(layout);
                if (id == null){
                    CluProposal cluProposal = new CluProposal();
                    CluInfo cluInfo = new CluInfo();
                    cluInfo.setState(luState);
                    cluInfo.setType(luType);
                    
                    cluProposal.setCluInfo(cluInfo); 
                    
                	wfStartWorkflowButton = new KSButton("Submit", new ClickHandler(){
                		public void onClick(ClickEvent event) {
                			CluInfo cluInfo = layout.getObject().getCluInfo();
                			if(cluInfo==null||cluInfo.getPrimaryAdminOrg()==null||cluInfo.getPrimaryAdminOrg().getOrgId()==null){
                				Window.alert("Administering Organization must be entered and saved before workflow can be started.");
                			}else{
	                			cluProposalRpcServiceAsync.startProposalWorkflow(layout.getObject(), new AsyncCallback<CluProposal>(){
									public void onFailure(
											Throwable caught) {
										Window.alert("Error starting Proposal workflow");
									}
									public void onSuccess(
											CluProposal result) {
										Window.alert("Proposal has been routed to workflow");
										layout.getObject().setWorkflowId(result.getWorkflowId());
										layout.removeButton(wfStartWorkflowButton);
										layout.refresh();
									}
								});
                			}
                		}
                	});
                	layout.addButton(wfStartWorkflowButton);
                    
                    layout.setObject(cluProposal);                    
                    layout.render();
                } else {
                    //FIXME: This should get proposal instead of CluInfo
                	//Load an existing clu
                	if(id.length()==36){
                		cluProposalRpcServiceAsync.getProposal(id, new AsyncCallback<CluProposal>(){
							public void onFailure(Throwable caught) {
								//TODO Error msg
							}

							public void onSuccess(CluProposal cluProposal) {
			                    layout.setObject(cluProposal);
			                    layout.render();
							}	
                		});
                	}else{
                		//convert from docId to cluid if the id is not 36 chars
                		cluProposalRpcServiceAsync.getCluProposalFromWorkflowId(id, new AsyncCallback<CluProposal>(){
							public void onFailure(Throwable caught) {
								Window.alert("GetCluProposal Failed");
							}

							public void onSuccess(final CluProposal cluProposal) {
			                    
								layout.setObject(cluProposal);
			                    //Load up workflow action buttons here
			                    cluProposalRpcServiceAsync.getActionsRequested(cluProposal, new AsyncCallback<String>(){
									public void onFailure(Throwable caught) {
										Window.alert("GetActionResquested Failed");
									}
									public void onSuccess(String result) {
										// check for custom KS "S" code for is submit required
										if(result!=null&&result.contains("S")){
						                	wfStartWorkflowButton = new KSButton("Submit", new ClickHandler(){
						                		public void onClick(ClickEvent event) {
						                			CluInfo cluInfo = layout.getObject().getCluInfo();
						                			if(cluInfo==null||cluInfo.getPrimaryAdminOrg()==null||cluInfo.getPrimaryAdminOrg().getOrgId()==null){
						                				Window.alert("Administering Organization must be entered and saved before workflow can be started.");
						                			}else{
							                			cluProposalRpcServiceAsync.startProposalWorkflow(layout.getObject(), new AsyncCallback<CluProposal>(){
															public void onFailure(
																	Throwable caught) {
																Window.alert("Error starting Proposal workflow");
															}
															public void onSuccess(
																	CluProposal result) {
																Window.alert("Proposal has been routed to workflow");
																layout.getObject().setWorkflowId(result.getWorkflowId());
																layout.removeButton(wfStartWorkflowButton);
																layout.refresh();
															}
														});
						                			}
						                		}
						                	});
						                	layout.addButton(wfStartWorkflowButton);
										}
										// if 'completion' is request code use
//										else if(result!=null&&result.contains("C")){
//											wfCompleteButton = new KSButton("COMPLETE", new ClickHandler(){
//												public void onClick(ClickEvent event) {
//													cluProposalRpcServiceAsync.completeProposal(cluProposal, new AsyncCallback<Boolean>(){
//														public void onFailure(
//																Throwable caught) {
//															Window.alert("Error completing Proposal");
//														}
//														public void onSuccess(
//																Boolean result) {
//															Window.alert("Proposal was completed");
//															layout.removeButton(wfCompleteButton);
//															layout.removeButton(wfDisApproveButton);
//														}
//														
//													});
//												}        
//											});
//											wfDisApproveButton = new KSButton("DISAPPROVE", new ClickHandler(){
//										        public void onClick(ClickEvent event) {
//													cluProposalRpcServiceAsync.disapproveProposal(cluProposal, new AsyncCallback<Boolean>(){
//														public void onFailure(
//																Throwable caught) {
//															Window.alert("Error disapproving Proposal");
//														}
//														public void onSuccess(
//																Boolean result) {
//															Window.alert("Proposal was disapproved");
//															layout.removeButton(wfCompleteButton);
//															layout.removeButton(wfDisApproveButton);
//														}
//														
//													});
//										        }        
//										    });
//							            
//											layout.addButton(wfCompleteButton);
//											layout.addButton(wfDisApproveButton);
//										}
										// if complete is requested then approve will be by default
										// ignore approve at this point
//										else if(result!=null&&result.contains("A")){
										else if(result!=null&&(result.contains("A")||result.contains("C"))){
											wfApproveButton = new KSButton("APPROVE", new ClickHandler(){
												public void onClick(ClickEvent event) {
													cluProposalRpcServiceAsync.approveProposal(cluProposal, new AsyncCallback<Boolean>(){
														public void onFailure(
																Throwable caught) {
															Window.alert("Error approving Proposal");
														}
														public void onSuccess(
																Boolean result) {
															Window.alert("Proposal was approved");
															layout.removeButton(wfApproveButton);
															layout.removeButton(wfDisApproveButton);
														}
														
													});
												}        
											});
											wfDisApproveButton = new KSButton("DISAPPROVE", new ClickHandler(){
										        public void onClick(ClickEvent event) {
													cluProposalRpcServiceAsync.disapproveProposal(cluProposal, new AsyncCallback<Boolean>(){
														public void onFailure(
																Throwable caught) {
															Window.alert("Error disapproving Proposal");
														}
														public void onSuccess(
																Boolean result) {
															Window.alert("Proposal was disapproved");
															layout.removeButton(wfApproveButton);
															layout.removeButton(wfDisApproveButton);
														}
														
													});
										        }        
										    });
							            
											layout.addButton(wfApproveButton);
											layout.addButton(wfDisApproveButton);
										}
										if(result!=null&&result.contains("K")){
											wfAcknowledgeButton= new KSButton("ACKNOWLEDGE", new ClickHandler(){
										        public void onClick(ClickEvent event) {
													cluProposalRpcServiceAsync.acknowledgeProposal(cluProposal, new AsyncCallback<Boolean>(){
														public void onFailure(
																Throwable caught) {
															Window.alert("Error acknowledging Proposal");
														}
														public void onSuccess(
																Boolean result) {
															Window.alert("Proposal was acknowledged");
															layout.removeButton(wfAcknowledgeButton);
														}
														
													});
										        }        
										    });
											layout.addButton(wfAcknowledgeButton);
										}
									}
			                    });
			                    layout.render();
							}	
                		});
                	}
                }

                return false;
            }

        };
        if(command.execute()) //only scheduling it if I must
            DeferredCommand.addCommand(command);

    }

    @Override
    public Controller getController() {
        return Controller.findController(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    public ConfigurableLayout<CluProposal> getLayout() {
        return layout;
    }

    
    public void setId(String id){
        this.id = id;
    }
    
    public void addLayoutToHistory(final KSHistory history, final LUMViews create_course) {
        IncrementalCommand command = new IncrementalCommand() {

            @Override
            public boolean execute() {
                if(layout == null) {
                    return true; //wait for stupid thing to load
                }
                history.addLayoutToView(create_course, layout);
                DefaultCreateUpdateLayout<CluProposal> l = null;
                boolean showStart = true;
                if(layout instanceof DefaultCreateUpdateLayout) {
                    l = (DefaultCreateUpdateLayout<CluProposal>)layout;
                    showStart = l.isShowStartSectionEnabled();
                    l.setShowStartSectionEnabled(false);//TODO this needs to be figured out
                }
                History.fireCurrentHistoryState();
                if(l != null)
                    l.setShowStartSectionEnabled(showStart);
                return false;
            }

        };
        if(command.execute()) //only scheduling it if I must
            DeferredCommand.addCommand(command);
    }
}
