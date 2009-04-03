package org.kuali.student.ui.kitchensink.client;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.core.messages.dto.MessageList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class KitchenSinkEntryPoint implements EntryPoint {
    ApplicationContext context = new ApplicationContext();
    
    public void onModuleLoad() {
        Application.setApplicationContext(context);
        loadMessages();
        
        KitchenSinkMain ksm = new KitchenSinkMain();
        String exampleClass = Window.Location.getParameter("exampleClass");
        exampleClass = (exampleClass == null) ? "" : exampleClass.trim();
        if (exampleClass.equals("")) {
            String injectString = this.getCssString();
            StyleInjector.injectStylesheet(injectString);
            RootPanel.get().add(ksm);
        } else {
        try {
            KitchenSinkExample kse = ksm.getExample(exampleClass);
            LiveCSSComposite liveCSS = new LiveCSSComposite();
            liveCSS.setExample(kse);
            RootPanel.get().add(liveCSS);
        } catch (Exception e) {
            HTML message = new HTML("Unable to load class: " + exampleClass + "<br/>Reason:<br/>" + dumpException(e));
            RootPanel.get().add(message);
        }
        }
    }
    
    private String dumpException(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append("<br/>");
            sb.append(ste.toString());
        }
        return sb.toString();
    }
    
    private void loadMessages(){
        MessagesService.Util.getInstance("MessageService").getMessages("en", "common", new AsyncCallback<MessageList>(){

            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load messages", caught);
            }

            public void onSuccess(MessageList result) {
                context.addMessages(result.getMessages());
            }           
        });        
    }
    
    public String getCssString(){
        String injectString = "";
         for(ResourcePrototype r: KSCommonResources.INSTANCE.getResources()){
             if(r instanceof CssResource){
                 if(((CssResource)r).getText() != null){
                     injectString = injectString + "\n" + (((CssResource)r).getText());
                 }
             }
         }
         return injectString;
         
    }

}
