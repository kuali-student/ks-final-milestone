package org.kuali.student.lum.lu.ui.main.client;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMMainEntryPoint implements EntryPoint{
       
    private final LUMApplicationManager manager = new LUMApplicationManager();
    
    @Override
    public void onModuleLoad() {
        final ApplicationContext context = new ApplicationContext();
        Application.setApplicationContext(context);
        MessagesService.Util.getInstance("MessageService").getMessages("en", "common", new AsyncCallback<MessageList>(){

            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load messages", caught);
            }

            public void onSuccess(MessageList result) {
                context.addMessages(result.getMessages());
                RootPanel.get().add(manager);
                manager.showDefaultView();
            }           
        });
    }

}
