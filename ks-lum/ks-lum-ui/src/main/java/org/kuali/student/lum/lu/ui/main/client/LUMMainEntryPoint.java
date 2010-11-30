/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.ui.main.client;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbManager;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.MessagesRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcServiceAsync;
import org.kuali.student.common.ui.client.util.BrowserUtils;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.ApplicationPanel;
import org.kuali.student.common.ui.client.widgets.KSFooter;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.ui.main.client.controllers.ApplicationController;
import org.kuali.student.lum.lu.ui.main.client.theme.LumTheme;
import org.kuali.student.lum.lu.ui.main.client.widgets.ApplicationHeader;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;

public class LUMMainEntryPoint implements EntryPoint{

    private ApplicationController manager = null;
    private AppLocations locations = new AppLocations();
    @Override
    public void onModuleLoad() {
        final ApplicationContext context = Application.getApplicationContext();

        final String injectString = LumTheme.INSTANCE.getLumCss().getCssString();
        StyleInjector.injectStylesheet(injectString);   

        try {
            loadMessages(context);                    
            loadApp(context);
        } catch (Exception e) {
            GWT.log("Error loading entrypoint", e);
        } 
    }

    private void initScreen(){
        manager = new ApplicationController("KualiStudent", new ApplicationHeader());
        WindowTitleUtils.setApplicationTitle(Application.getApplicationContext().getMessage("applicationName"));
        ApplicationPanel.get().add(manager);
        ApplicationPanel.get().add(new KSFooter());
        HistoryManager.bind(manager, locations);
        BreadcrumbManager.bind(manager);
        HistoryManager.processWindowLocation();
        if(manager.getCurrentView() == null)
            manager.showDefaultView(Controller.NO_OP_CALLBACK);
    }
    
    private void loadMessages(final ApplicationContext context) throws SerializationException {
        MessageList commonMessageList =  getMsgSerializedObject("commonMessages" );
        MessageList lumMessageList =  getMsgSerializedObject("luMessages" );
        context.addMessages(commonMessageList.getMessages());
        context.addMessages(lumMessageList.getMessages());
    }

    @SuppressWarnings("unchecked")
    public  <T> T getMsgSerializedObject(String key ) throws SerializationException
    {
        String serialized = BrowserUtils.getString( key );
        SerializationStreamFactory ssf = GWT.create( MessagesRpcService.class); // magic
        SerializationStreamReader ssr = ssf.createStreamReader( serialized );
        T ret = (T)ssr.readObject();
        return ret;
    } 
      
    public void loadApp(final ApplicationContext context){
        SecurityRpcServiceAsync securityRpc = GWT.create(SecurityRpcService.class);
        
        securityRpc.getPrincipalUsername(new KSAsyncCallback<String>(){
            public void handleFailure(Throwable caught) {
                context.setUserId("Unknown");
                initScreen();
            }

            @Override
            public void onSuccess(String principalId) {
                context.setUserId(principalId);
                initScreen();
            }            
        });
    }

}
