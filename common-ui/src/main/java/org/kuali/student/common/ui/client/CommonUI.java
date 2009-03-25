package org.kuali.student.common.ui.client;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.common.ui.client.widgets.KSResizablePanel;
import org.kuali.student.core.messages.dto.MessageList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;

public class CommonUI implements EntryPoint {
    ApplicationContext context;
    
	public void onModuleLoad() {
        context = new ApplicationContext();
        Application.setApplicationContext(context);
        MessagesService.Util.getInstance("MessageService").getMessages("en", "common", new AsyncCallback<MessageList>(){

            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load messages", caught);
            }

            public void onSuccess(MessageList result) {
                context.addMessages(result.getMessages());
            }           
        });

	
	}
	


}
