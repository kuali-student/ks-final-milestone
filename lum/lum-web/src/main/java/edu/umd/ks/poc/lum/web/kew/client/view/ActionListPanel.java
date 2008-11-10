package edu.umd.ks.poc.lum.web.kew.client.view;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.Status;
import edu.umd.ks.poc.lum.web.clu.client.CluWorkflowComposite;
import edu.umd.ks.poc.lum.web.clu.client.FindClusPanel.CluDetailsEvent;
import edu.umd.ks.poc.lum.web.core.client.Authorization;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityRpcService;
import edu.umd.ks.poc.lum.web.lu.client.service.LuRpcService;

public class ActionListPanel extends Composite{

    public static class ActionListCluApproveEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(ActionListCluApproveEvent.class, new ActionListCluApproveEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(ActionListCluApproveEvent.class);
        }
    }
    public static class ActionListCluDisapproveEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(ActionListCluDisapproveEvent.class, new ActionListCluDisapproveEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(ActionListCluDisapproveEvent.class);
        }
    }
    static {
        new ActionListCluApproveEvent();
        new ActionListCluDisapproveEvent();
    }

    public static String ACTION_LIST = "actionList";

    VerticalPanel root = new VerticalPanel();

    boolean loaded = false;

    FlexTable actionList = new FlexTable();

    public ActionListPanel() {
        super.initWidget(root);
    }


    protected void onLoad() {
        super.onLoad();
        if(!loaded){
            loaded=true;
            root.add(actionList);
            WorkflowUtilityRpcService.Util.getInstance().getClusForUser(Authorization.getUser(), new AsyncCallback<List<CluInfo>>(){

				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				public void onSuccess(List<CluInfo> clus) {
					actionList.setWidget(0, 0, new Label("CluShortName"));
					actionList.setWidget(0, 1, new Label("Status"));
					actionList.setWidget(0, 2, new Label("Actions"));
					int i=1;
					for(CluInfo clu:clus){
						actionList.setWidget(i, 0, getCluLinkWidget(clu.getCluId(),clu.getCluShortName(),clu.getStatus().substring(1)));
						actionList.setWidget(i, 1, new Label(clu.getStatus().substring(0, 1)));
						actionList.setWidget(i, 2, getActionsWidget(clu.getStatus(),clu.getStatus().substring(1),clu.getCluId()));
						i++;
					}
				}

            });
        }
    }

	private Widget getCluLinkWidget(final String cluId,
			String cluShortName, final String routeDocId) {
		Hyperlink link = new Hyperlink(cluShortName,"cluId");
		link.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				CluWorkflowComposite cwc = new CluWorkflowComposite();
				cwc.setCluId(cluId);
				cwc.setRouteDocId(routeDocId);
				GlobalEventDispatcher.getInstance().fireEvent(CluDetailsEvent.class, cwc);
			}
		});
		return link;
	}

	private Widget getActionsWidget(String status, final String routeId, final String cluId) {
		HorizontalPanel actions = new HorizontalPanel();
		if("A".equals(status.substring(0, 1))){
			Button approveButton = new Button("Approve");
			approveButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					LuRpcService.Util.getInstance().approveClu(Authorization.getUser(), routeId, cluId, "Approved From Action List", new AsyncCallback<Status>(){
						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}

						public void onSuccess(Status result) {
							//Fire refresh
							GlobalEventDispatcher.getInstance().fireEvent(ActionListCluApproveEvent.class, ACTION_LIST);
						}
					});
				}
			});
			actions.add(approveButton);

			Button disapproveButton = new Button("Disapprove");
			disapproveButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					LuRpcService.Util.getInstance().disapproveClu(Authorization.getUser(), routeId, cluId, "Approved From Action List", new AsyncCallback<Status>(){
						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}

						public void onSuccess(Status result) {
							//Fire refresh
							GlobalEventDispatcher.getInstance().fireEvent(ActionListCluDisapproveEvent.class, ACTION_LIST);
						}
					});
				}
			});
			actions.add(disapproveButton);
		}
		return actions;
	}

	private void addListeners() {
		// TODO Auto-generated method stub

	}
}
