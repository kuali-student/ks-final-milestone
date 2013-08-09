/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.security;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.KSLightBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This implements the SessionTimeoutHandler. The timeout is handled by displaying a spring
 * security login panel.  
 * 
 * @author Kuali Student Team
 *
 */
public class SpringSecurityLoginDialogHandler implements SessionTimeoutHandler{
	final static ApplicationContext context = Application.getApplicationContext();
	static  boolean CASrequiresAPageRefreshViaJavascript = Boolean.TRUE;
	public final String TIMEOUT_MSG = "Your session has timed out. Please login again.";
	
	protected KSLightBox lightbox;
    protected TextBox username;
    protected PasswordTextBox password;
    protected Label errorLabel;
    
  private native void reload() /*-{
    $wnd.location.reload();
   }-*/;

	@Override
	public boolean isSessionTimeout(Throwable error) {
        boolean InvocationException = error.toString().contains("com.google.gwt.user.client.rpc.InvocationException");
        boolean CAS = error.toString().contains(""); // The return login page will from cas because spring filter it
        boolean normalLogin =    error.toString().contains("Login"); // the return login will from normal spring Authentication

        //until I havent had a chance to see what the CAS loging looks like I am making it default that the javascript do
        // a page refresh rather than showing a login dialogbox. Thus we will be taken to login.jsp screen correlating with
        // the chosen filter {cas or normal spring form}

        if(CAS )
        {
            CASrequiresAPageRefreshViaJavascript = true;
        }

    	return CAS || normalLogin ;
    }
    
    @Override
	public void handleSessionTimeout() {
        if(!CASrequiresAPageRefreshViaJavascript){
    	if (lightbox == null){
    		createLoginPanel();
    	} else {
    		resetLoginPanel();
    	}
    	lightbox.setSize(460, 220);
        lightbox.show();
        }else{reload();}
	}

	private void createLoginPanel(){
		lightbox = new KSLightBox();
        VerticalPanel panel = new VerticalPanel();

        FlexTable table = new FlexTable();

        errorLabel = new Label();
        errorLabel.setText(TIMEOUT_MSG);
        errorLabel.setStyleName("KSError");
        
        username = new TextBox();
        username.setName("j_username");

        password = new PasswordTextBox();
        password.setName("j_password");
        
        table.setText(0, 0, "Username");
        table.setWidget(0, 1, username);

        table.setText(1,0, "Password");
        table.setWidget(1,1, password);
                             
        table.setWidget(2,0,(new Button("Login", new ClickHandler() {   
            public void onClick(ClickEvent event) {
                
                StringBuffer postData = new StringBuffer();
                postData.append(URL.encode("j_username")).append("=").append(username.getText());
                postData.append("&").append(URL.encode("j_password")).append("=").append(password.getText());
                
                RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, context.getApplicationContextUrl() + "/j_spring_security_check");
                builder.setHeader("Content-type", "application/x-www-form-urlencoded");

                try{
                    builder.sendRequest(postData.toString(), new RequestCallback(){

                        @Override
                        public void onError(Request req, Throwable caught) {
                        	lightbox.hide();
                        	KSErrorDialog.show(caught);
                        }

                        @Override
                        public void onResponseReceived(Request req, Response res) {
                            if (res.getStatusCode() == Response.SC_OK && !res.getText().contains("Bad credentials")){
                            	lightbox.hide();
                            } else {
                                errorLabel.setText("Your login attempt was not successful, try again.");
                            }
                        }});
                } catch (RequestException e) {
                	KSErrorDialog.show(e);
                }                
            }
        }
        )));
                
        panel.add(errorLabel);
        panel.add(table);
 
        panel.setStyleName("KSLoginPanel");
        lightbox.setWidget(panel);
    }
	
	private void resetLoginPanel(){
		username.setText("");
		password.setText("");
		errorLabel.setText(TIMEOUT_MSG);		
	}
    
}
