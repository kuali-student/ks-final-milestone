package org.kuali.student.lum.lu.ui.main.client;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMMainEntryPoint implements EntryPoint{

    private final LUMApplicationManager manager = new LUMApplicationManager();

    @Override
    public void onModuleLoad() {
        final ApplicationContext context = new ApplicationContext();
        Application.setApplicationContext(context);

        try {
            MessageList messageList =  getSerializedObject( "i18nMessages");
            context.addMessages(messageList.getMessages());

            RootPanel.get().add(manager);
            if(manager.getCurrentView() == null)
                manager.showDefaultView();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    @SuppressWarnings("unchecked")
    public  <T> T getSerializedObject( String name ) throws SerializationException
    {
        String serialized = getString( name );
        SerializationStreamFactory ssf = GWT.create( MessagesService.class); // magic
        return (T)ssf.createStreamReader( serialized ).readObject();
    }
    public  native String getString(String name) /*-{
        return eval("$wnd."+name);
    }-*/;

}
