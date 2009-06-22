package org.kuali.student.lum.lu.ui.main.client;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationComposite;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUDictionaryManager;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMMainEntryPoint implements EntryPoint{

    ApplicationComposite app = new ApplicationComposite();
    private final LUMApplicationManager manager = new LUMApplicationManager();

    @Override
    public void onModuleLoad() {
        final ApplicationContext context = new ApplicationContext();
        Application.setApplicationContext(context);
        
        try {
            loadMessages(context);            
            loadDictionary();

            app.setContent(manager);
            RootPanel.get().add(app);
            if(manager.getCurrentView() == null)
                manager.showDefaultView();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    private void loadMessages(final ApplicationContext context) throws SerializationException {
        MessageList messageList =  getMsgSerializedObject( "i18nMessages");
        context.addMessages(messageList.getMessages());
    }

    @SuppressWarnings("unchecked")
    public  <T> T getMsgSerializedObject( String name ) throws SerializationException
    {
        String serialized = getString( name );
        SerializationStreamFactory ssf = GWT.create( MessagesService.class); // magic
        return (T)ssf.createStreamReader( serialized ).readObject();
    } 

    private void loadDictionary() {

        try {
            ObjectStructure structure =  getDictSerializedObject( "dictionaryData");
            LUDictionaryManager.getInstance().loadStructure(structure);

        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    public  <T> T getDictSerializedObject( String name ) throws SerializationException
    {
        String serialized = getString( name );
        SerializationStreamFactory ssf = GWT.create( LuRpcService.class); // magic
        return (T)ssf.createStreamReader( serialized ).readObject();
    }
    public  native String getString(String name) /*-{
        return eval("$wnd."+name);
    }-*/;

}
