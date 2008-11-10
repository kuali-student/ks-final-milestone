package edu.umd.ks.poc.lum.web.lum.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityRpcService;

public class SwitchUserPanel extends FormPanel {

    HorizontalPanel   fPanel = new HorizontalPanel();
    Label       lblBackDoor = new Label("Backdoor Id:");
    TextBox     tBox        = new TextBox();

    Button      login       = new Button("Login");
    Button      logout       = new Button("Logout");
    Hidden      hidden      = new Hidden();

    boolean     loaded      = false;

    /**
     * This overridden method ...
     *
     * @see com.google.gwt.user.client.ui.Panel#onLoad()
     */
    @Override
    protected void onLoad() {

        super.onLoad();

        if(!loaded){
            loaded = true;

//            this.setAction("/lum-umd/en/Portal.do");
//            this.setMethod(FormPanel.METHOD_POST);
//

            tBox.setName("backdoorId");

//            hidden.setName("methodToCall");
//            hidden.setValue("login");

            login.addClickListener(new ClickListener(){
                public void onClick(Widget sender) {
                    hidden.setValue("login");
                    WorkflowUtilityRpcService.Util.getInstance().switchUser(tBox.getText(), new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}
						public void onSuccess(String result) {
		                    GlobalEventDispatcher.getInstance().fireEvent(CurrentUser.AuthChangeEvent.class);
		                }
                    	
                    });
                }});

            logout.addClickListener(new ClickListener(){
                public void onClick(Widget sender) {
                    WorkflowUtilityRpcService.Util.getInstance().switchUser(null, new AsyncCallback<String>(){

						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}
						public void onSuccess(String result) {
		                    GlobalEventDispatcher.getInstance().fireEvent(CurrentUser.AuthChangeEvent.class);
		                }
                    	
                    });

                }});
//            this.addFormHandler(new FormHandler(){
//                public void onSubmit(FormSubmitEvent event) {
//                }
//
//
//                public void onSubmitComplete(FormSubmitCompleteEvent event) {
//                    GlobalEventDispatcher.getInstance().fireEvent(CurrentUser.AuthChangeEvent.class);
//                }});


//            fPanel.add(hidden);
//            fPanel.add(lblBackDoor);
            fPanel.add(tBox);
            fPanel.add(login);
            fPanel.add(logout);
            this.add(fPanel);

        }
    }



}
