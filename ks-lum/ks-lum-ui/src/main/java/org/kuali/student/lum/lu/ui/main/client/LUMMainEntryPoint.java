/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import org.kuali.student.common.ui.client.application.ApplicationComposite;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.dictionary.DictionaryLoader;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.service.MessagesRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcServiceAsync;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;
import org.kuali.student.lum.lu.ui.main.client.theme.LumTheme;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMMainEntryPoint implements EntryPoint{

    ApplicationComposite app;
    private LUMApplicationManager manager = null;


    @Override
    public void onModuleLoad() {
        final ApplicationContext context = new ApplicationContext();
        Application.setApplicationContext(context);

        final String injectString = LumTheme.INSTANCE.getLumCss().getCssString();
        StyleInjector.injectStylesheet(injectString);   

        try {
            loadMessages(context);            
            
            //loadDictionary();          
            
            loadApp(context);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    private void loadDictionary() {
        DictionaryLoader.loadDictionary("luTypes");
        DictionaryLoader.loadDictionary("loTypes");
    }

    private void initScreen(){
        app = new ApplicationComposite();
        manager = new LUMApplicationManager();
        app.setContent(manager);
        RootPanel.get().add(app);
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
        String serialized = getString( key );
        SerializationStreamFactory ssf = GWT.create( MessagesRpcService.class); // magic
        SerializationStreamReader ssr = ssf.createStreamReader( serialized );
        T ret = (T)ssr.readObject();
        return ret;
    } 
   
    public  native String getString(String name) /*-{
        return eval("$wnd."+name);
    }-*/;
    
    public void loadApp(final ApplicationContext context){
        SecurityRpcServiceAsync securityRpc = GWT.create(SecurityRpcService.class);
        
        securityRpc.getPrincipalUsername(new AsyncCallback<String>(){
            public void onFailure(Throwable caught) {
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

/*    public String getCssString(){
        String injectString = "";
         for(ResourcePrototype r: LumResources.INSTANCE.getResources()){
             if(r instanceof CssResource){
                 if(((CssResource)r).getText() != null){
                     injectString = injectString + "\n" + (((CssResource)r).getText());
                 }
             }
         }
         return injectString;
    }*/
    
    

}
