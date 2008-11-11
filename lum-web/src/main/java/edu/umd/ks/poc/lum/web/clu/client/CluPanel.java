package edu.umd.ks.poc.lum.web.clu.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.widgets.DateBox;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.Status;
import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.clu.client.FindClusPanel.CluDetailsEvent;
import edu.umd.ks.poc.lum.web.core.client.Authorization;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;
import edu.umd.ks.poc.lum.web.lu.server.gwt.LuRpcServiceGwt;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;

public class CluPanel extends Composite{
    public static class CreateCluEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(CreateCluEvent.class, new CreateCluEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(CreateCluEvent.class);
        }
    }
    public static class LuTypesRequestEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypesRequestEvent.class, new LuTypesRequestEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypesRequestEvent.class);
        }
    }
    public static class LuTypesResponseEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypesResponseEvent.class, new LuTypesResponseEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypesResponseEvent.class);
        }
    }
    public static class LuTypeSelectEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypeSelectEvent.class, new LuTypeSelectEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypeSelectEvent.class);
        }
    }

    static {
        new CreateCluEvent();
        new LuTypesRequestEvent();
        new LuTypesResponseEvent();
        new LuTypeSelectEvent();
    }


    boolean loaded = false;

    VerticalPanel root = new VerticalPanel();
    FlexTable       fTable  = null;
    ListBox       luTypes = null;

    LuTypeInfo selectedTypeInfo = null;

    TextBox cluLongName = null;
    TextBox cluShortName = null;
    TextArea cluDesc = null;

    Widget startDate = null;
    Widget endDate = null;

    TextArea    createComment = null;
    ListBox adminDepartment = null;
    Label adminDepartmentLabel = null;
    SimplePanel attributes = new SimplePanel();
	private Map<String,List<String>> attributeMap = null;
    String cluId = null;
    String status;
    String routeId = null;
    Label statusLabel = new Label();
    public CluPanel(String cluId, String routeId) {
        super();
        this.cluId = cluId;
        this.routeId = routeId;
        initWidget(root);
    }


    protected void onLoad() {
        super.onLoad();

        if(!loaded){
            loaded = true;

            fTable = new FlexTable();

            this.luTypes = new ListBox();

            this.cluLongName = new TextBox();
            this.cluShortName = new TextBox();
            this.cluDesc = new TextArea();

            this.startDate = new DateBox();
            this.endDate = new DateBox();

            this.createComment = new TextArea();
            this.adminDepartment = new ListBox();
            this.adminDepartmentLabel = new Label();



            if(cluId==null){
                luTypes.addChangeListener(new ChangeListener(){
    				public void onChange(Widget sender) {
    					GlobalEventDispatcher.getInstance().fireEvent(LuTypeSelectEvent.class, luTypes.getValue(luTypes.getSelectedIndex()));
    				}
                });
                //Load the department codes
				adminDepartment.addItem("Select", "");
				adminDepartment.setSelectedIndex(0);
                populateDepartmentCodes();
            }



            int i = 0;
            if(cluId==null){
	            fTable.setWidget(i, 0, new Label("Lu Type"));
	            fTable.setWidget(i++, 1, luTypes);
            }
            fTable.setWidget(i, 0, new Label("Status"));
            fTable.setWidget(i++, 1, statusLabel);
            fTable.setWidget(i, 0, new Label("Short Name"));
            fTable.setWidget(i++, 1, cluShortName);
            fTable.setWidget(i, 0, new Label("Long Name"));
            fTable.setWidget(i++, 1, cluLongName);
            fTable.setWidget(i, 0, new Label("Description"));
            fTable.setWidget(i++, 1, cluDesc);
            fTable.setWidget(i, 0, new Label("Start Date"));
            fTable.setWidget(i++, 1, startDate);
            fTable.setWidget(i, 0, new Label("End Date"));
            fTable.setWidget(i++, 1, endDate);

            fTable.setWidget(i, 0, new Label("Comment"));
            fTable.setWidget(i++, 1, createComment);

            if(cluId==null){
            	fTable.setWidget(i, 0, new Label("Admin Department"));
            	fTable.setWidget(i++, 1, adminDepartment);
            }else{
            	fTable.setWidget(i, 0, new Label("Admin Department"));
            	fTable.setWidget(i++, 1, adminDepartmentLabel);
            }

            /*
            Button createButton = new Button("Create");
            createButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					GlobalEventDispatcher.getInstance().fireEvent(CreateCluEvent.class, getCluInfo());
				}
            });

            fTable.setWidget(i, 0, createButton);
*/
            root.add(fTable);
            root.add(attributes);
            this.addListeners();

            if(cluId!=null){
//            	createButton.setText("Update");
            	//populateCluInfo();
            }


        }
        if(cluId==null){
        	GlobalEventDispatcher.getInstance().fireEvent(LuTypesRequestEvent.class);
        }
    }

	/**
     * @return the cluId
     */
    public String getCluId() {
        return cluId;
    }


    /**
     * @return the routeId
     */
    public String getRouteId() {
        return routeId;
    }


    private void populateDepartmentCodes() {
        ScatRpcService.Util.getInstance().searchScats("DEPARTMENTS", new AsyncCallback<List<ScatTableInfo>>(){
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<ScatTableInfo> scatTables) {
				if(scatTables==null||scatTables.size()!=1){
					Window.alert("Scat Table DEPARTMENTS was not found");
				}
				ScatRpcService.Util.getInstance().findScats(scatTables.get(0).getTableId(), new AsyncCallback<List<ScatInfo>>(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
					public void onSuccess(List<ScatInfo> scatInfos) {
						//Add the admins for each department
						int i = adminDepartment.getItemCount();
						for(ScatInfo scat:scatInfos){
							adminDepartment.addItem(scat.getShortDesc(),scat.getCode());
			            	i++;
						}
					}
				});
			}
        });
	}


	public void populateCluInfo() {
		try {
			LuRpcService.Util.getInstance().fetchClu(cluId,
					new AsyncCallback<CluInfo>() {
						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}

						public void onSuccess(CluInfo clu) {
							DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy");
							cluShortName.setText(clu.getCluShortName());
							cluLongName.setText(clu.getCluLongName());
							cluDesc.setText(clu.getDescription());
							try{
								((HasText)startDate).setText(fmt.format(clu.getEffectiveStartDate()));
								((HasText)endDate).setText(fmt.format(clu.getEffectiveEndDate()));
							}catch(Exception e){

							}
							//createComment.setVisible(false);
							//Get the attribute types and fire the lu selected event
							attributeMap = clu.getAttributes();
							status=clu.getStatus();
							statusLabel.setText(getStatusCode(status)+"("+status+")");
							adminDepartmentLabel.setText(clu.getAdminDepartment());
//							for(int i=0;i<adminDepartment.getItemCount();i++){
//								if(adminDepartment.getValue(i).equals(clu.getAdminDepartment())){
//									adminDepartment.setItemSelected(i, true);
//								}
//							}
							if(status!=null&&("F".equals(status.substring(0, 1))||"D".equals(status.substring(0, 1)))){
								//FIXME startdate and enddate need to be un editable
								((HasText)startDate).setText(fmt.format(clu.getEffectiveStartDate()));
								((HasText)endDate).setText(fmt.format(clu.getEffectiveEndDate()));
								cluShortName.setReadOnly(true);
								cluLongName.setReadOnly(true);
								cluDesc.setReadOnly(true);
							}

			            	if(routeId!=null&&"R".equals(status.substring(0, 1))){
			            		//root.add(getActionsWidget(routeId, cluId)); //Removed, a better way to get the workflow routeId should be used
			            	}
		   					GlobalEventDispatcher.getInstance().fireEvent(LuTypeSelectEvent.class, clu.getLuTypeId());
						}



					});
		} catch (SerializableException e) {
			Window.alert(e.getMessage());
		}

	}


	/**
     * @return the status
     */
    public String getStatus() {
        return status;
    }


    private void doCreateNewLayout() {
		// TODO Auto-generated method stub

	}
	private String getStatusCode(String status) {
		if("R".equals(status.substring(0,1))){
			return "Enroute";
		}
		if("F".equals(status.substring(0,1))){
			return "Approved";
		}
		if("D".equals(status.substring(0,1))){
			return "Disapproved";
		}
		return "Unknown";
	}

	public CluInfo getCluInfo() {
		DateTimeFormat fmt = DateTimeFormat.getFormat("MM/dd/yyyy");

		CluInfo cluInfo = new CluInfo();
		cluInfo.setCluId(cluId);
		if(cluId==null){
			cluInfo.setLuTypeId(luTypes.getValue(luTypes.getSelectedIndex()));
			cluInfo.setAdminDepartment(adminDepartment.getValue(adminDepartment.getSelectedIndex()));
		}else{
			cluInfo.setAdminDepartment(adminDepartmentLabel.getText());
		}
		cluInfo.setCluLongName(cluLongName.getText());
		cluInfo.setCluShortName(cluShortName.getText());
		cluInfo.setDescription(cluDesc.getText());
		try{
			cluInfo.setEffectiveStartDate(fmt.parse(((HasText)startDate).getText()));
		}catch(Exception e){
			cluInfo.setEffectiveStartDate(new Date());
		}
		try{
			cluInfo.setEffectiveEndDate(fmt.parse(((HasText)endDate).getText()));
		}catch(Exception e){
			cluInfo.setEffectiveEndDate(new Date());
		}
		cluInfo.setStatus(status);
		cluInfo.setAttributes(((DynamicAttributePanel)attributes.getWidget()).getAttributesMap());
		cluInfo.setCreateUserComment(createComment.getText());
		return cluInfo;
	}

    protected void addListeners(){
        GlobalEventDispatcher.getInstance().addListener(LuTypesResponseEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                final List<LuTypeInfo> infoList = (List<LuTypeInfo>)data;
                luTypes.clear();
                luTypes.addItem("Please Select");
                luTypes.setSelectedIndex(0);
                int i = 1;
                for(LuTypeInfo info : infoList){
                    luTypes.addItem(info.getDescription(), info.getLuTypeKey());
                    if(selectedTypeInfo != null && selectedTypeInfo.getLuAttributeTypeIds().equals(info.getLuTypeKey())){
                        luTypes.setItemSelected(i, true);
                    }
                    i++;
                }

            }
        });
        GlobalEventDispatcher.getInstance().addListener(LuTypeSelectEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
            	//Load the Lu Attribute Types and attach a panel.
            	LuRpcServiceGwt.Util.getInstance().findLuAttributeTypesForLuTypeId((String)data, new AsyncCallback<List<LuAttributeTypeInfo>>(){

					public void onFailure(Throwable caught) {

					}

					public void onSuccess(List<LuAttributeTypeInfo> result) {
						boolean editable = true;
						if(status!=null&&("F".equals(status.substring(0, 1))||"D".equals(status.substring(0, 1)))){
							editable=false;
						}
						attributes.setWidget(new DynamicAttributePanel(editable, result,attributeMap));
					}

            	});
            }
        });
    }

    public Widget getActionsWidget(final String routeId, final String cluId) {
		HorizontalPanel actions = new HorizontalPanel();

		Button approveButton = new Button("Approve");
		approveButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				LuRpcService.Util.getInstance().approveClu(Authorization.getUser(), routeId, cluId, createComment.getText(), new AsyncCallback<Status>(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Status result) {
						//Reload the clu
						CluWorkflowComposite cwc = new CluWorkflowComposite();
						cwc.setCluId(cluId);
						//cwc.setRouteDocId(routeId);
						GlobalEventDispatcher.getInstance().fireEvent(CluDetailsEvent.class, cwc);
					}
				});
			}
		});
		actions.add(approveButton);

		Button disapproveButton = new Button("Disapprove");
		disapproveButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				LuRpcService.Util.getInstance().disapproveClu(Authorization.getUser(), routeId, cluId, createComment.getText(), new AsyncCallback<Status>(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Status result) {
						//Reload the clu
						CluWorkflowComposite cwc = new CluWorkflowComposite();
						cwc.setCluId(cluId);
						//cwc.setRouteDocId(routeId);
						GlobalEventDispatcher.getInstance().fireEvent(CluDetailsEvent.class, cwc);
					}
				});
			}
		});
		actions.add(disapproveButton);

		return actions;
	}
}
