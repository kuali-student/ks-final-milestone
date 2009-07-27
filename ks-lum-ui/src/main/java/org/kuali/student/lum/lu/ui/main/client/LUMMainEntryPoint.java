package org.kuali.student.lum.lu.ui.main.client;


import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationComposite;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcServiceAsync;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUDictionaryHelper;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUDictionaryManager;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMMainEntryPoint implements EntryPoint{

    ApplicationComposite app;
    private LUMApplicationManager manager = null;


    @Override
    public void onModuleLoad() {
        final ApplicationContext context = new ApplicationContext();
        Application.setApplicationContext(context);

        final String injectString = this.getCssString();
        StyleInjector.injectStylesheet(injectString);   

        try {
            loadMessages(context);            
            loadDictionary();
            loadApp(context);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    private void initScreen(){
        app = new ApplicationComposite();
        manager = new LUMApplicationManager();
        app.setContent(manager);
        RootPanel.get().add(app);
        if(manager.getCurrentView() == null)
            manager.showDefaultView();        
    }
    private void loadMessages(final ApplicationContext context) throws SerializationException {
        MessageList messageList =  getMsgSerializedObject( );
        context.addMessages(messageList.getMessages());
    }

    @SuppressWarnings("unchecked")
    public  <T> T getMsgSerializedObject( ) throws SerializationException
    {
        String serialized = getString( "i18nMessages" );
        SerializationStreamFactory ssf = GWT.create( MessagesService.class); // magic
        return (T)ssf.createStreamReader( serialized ).readObject();
    } 

    private void loadDictionary() {

        try {
            GWT.log("Dictionary load starts", null);
            List<String> types = getDictSerializedObject("objectTypes");
            for (String key: types) {
                ObjectStructure structure =  getDictSerializedObject( LUDictionaryHelper.buildJavaScriptKey(key));
                LUDictionaryManager.getInstance().loadStructure(structure);
            }
            logDictionaryLoad();
            GWT.log("Dictionary load ends", null);
        } catch (SerializationException e) {
            GWT.log("loadDictionary failed " ,  e);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public  <T> T getDictSerializedObject(String key  ) throws SerializationException
    {
        SerializationStreamFactory ssf = GWT.create( LuRpcService.class); // magic
        String serialized = getString( key );
        return (T)ssf.createStreamReader( serialized ).readObject();
    }
    
    public  native String getString(String name) /*-{
        return eval("$wnd."+name);
    }-*/;
    
    public void loadApp(final ApplicationContext context){
        SecurityRpcServiceAsync securityRpc = GWT.create(SecurityRpcService.class);
        
        securityRpc.getPrincipalUsername(new AsyncCallback<String>(){
            public void onFailure(Throwable caught) {
                context.setUserId("Unknown");
            }

            @Override
            public void onSuccess(String principalId) {
                context.setUserId(principalId);
                initScreen();
            }            
        });
    }

    public String getCssString(){
        String injectString = "";
         for(ResourcePrototype r: LumResources.INSTANCE.getResources()){
             if(r instanceof CssResource){
                 if(((CssResource)r).getText() != null){
                     injectString = injectString + "\n" + (((CssResource)r).getText());
                 }
             }
         }
         return injectString;
    }
    
    private void logDictionaryLoad() {
        for (String s : LUDictionaryManager.getInstance().getTypes()) {
            GWT.log("Loaded OBJECT TYPE: " + s, null);
//            String objectKey = s.substring(0, s.indexOf(":"));
//            String type = s.substring(s.indexOf(":")+1, s.lastIndexOf(":"));
//            String state = s.substring(s.lastIndexOf(":")+1);
//            Map<String, Field> fields = LUDictionaryManager.getInstance().getFields(objectKey, type, state);
//            for (String f : fields.keySet()) {
//                GWT.log("Contains field: " + f, null);
//                
//            }
        }
    }
    

}
