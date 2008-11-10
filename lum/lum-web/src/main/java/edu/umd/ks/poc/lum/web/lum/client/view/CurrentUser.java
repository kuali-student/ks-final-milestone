package edu.umd.ks.poc.lum.web.lum.client.view;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import edu.umd.ks.poc.lum.web.core.client.Authorization;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityRpcService;


public class CurrentUser extends HorizontalPanel{

    boolean loaded = false;

    Label lbl    = new Label("Current User:");
    Label lblCurrUser    = new Label("Not Logged In");

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


    @Override
    protected void onLoad() {
        super.onLoad();

        if(!loaded){
            loaded = true;
            this.onModuleLoad();
        }
    }

    protected void onModuleLoad(){
        this.add(lbl);
        this.add(lblCurrUser);

        this.loadCurrentUser();
        GlobalEventDispatcher.getInstance().addListener(AuthChangeEvent.class, new MVCEventListener(){
            public void onEvent(Class<? extends MVCEvent> event, Object data) {

                loadCurrentUser();
            }});
    }

    public void loadCurrentUser(){

        WorkflowUtilityRpcService.Util.getInstance().getUser(new AsyncCallback<String>(){
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
            public void onSuccess(String result) {

                lblCurrUser.setText(result);
                Authorization.setUser(result);
            }

        });
    }
}
