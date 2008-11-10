package edu.umd.ks.poc.lum.web.lum.client.view;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.core.client.Authorization;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.core.client.Authorization.User;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityRpcService;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;

public class AuthWidget extends Composite {
	HorizontalPanel root = new HorizontalPanel();
	boolean loaded = false;
    ListBox users=new ListBox();
    Label currentUser = new Label();
	public AuthWidget() {
		super.initWidget(root);
	}
	/*
    public static class AuthChangeEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(AuthChangeEvent.class, new AuthChangeEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(AuthChangeEvent.class);
        }
    }
    static {
        new AuthChangeEvent();
    }
    */
    public void refreshUserList(){
    	users.clear();
        int i = 0;
        for(User value:User.values()){
        	users.addItem(value.toString());
        	if(Authorization.getUser().equals(value.toString())){
        		users.setSelectedIndex(i);
        	}
        	i++;
        }

        users.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				Authorization.setUser(users.getValue(users.getSelectedIndex()));
				GlobalEventDispatcher.getInstance().fireEvent(CurrentUser.AuthChangeEvent.class);
			}
        });

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
						int i = users.getItemCount();
						for(ScatInfo scat:scatInfos){
			            	users.addItem(scat.getCode()+"Admin");
			            	if(Authorization.getUser().equals(scat.getCode()+"Admin")){
			            		users.setSelectedIndex(i);
			            	}
			            	i++;
						}
					}
				});
			}
        });
        WorkflowUtilityRpcService.Util.getInstance().getUser(new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(String result) {
				currentUser.setText(result);
			}

        });
    }

	protected void onLoad() {
        if (!loaded) {
            loaded = true;
            root.add(new Label("Current User: "));
            root.add(users);
            refreshUserList();

            Button refreshButton = new Button("Reload Users");
            refreshButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
		            refreshUserList();
				}
            });
            root.add(refreshButton);
            root.add(currentUser);
        }
    }
}
