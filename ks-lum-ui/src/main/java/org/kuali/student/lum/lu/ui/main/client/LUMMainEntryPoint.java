package org.kuali.student.lum.lu.ui.main.client;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.messages.dto.MessageList;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUDictionaryManager;
import org.kuali.student.lum.lu.ui.course.client.configuration.LULayoutFactory;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class LUMMainEntryPoint implements EntryPoint{

  private final LUMApplicationManager manager = new LUMApplicationManager();
//    private LUMApplicationManager manager;

    private LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);


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
                if(manager.getCurrentView() == null)
                    manager.showDefaultView();

//              //  If msg load OK, load proposalInfo structure
//                luRpcServiceAsync.getObjectStructure(LUDictionaryManager.STRUCTURE_PROPOSAL_INFO, new AsyncCallback<ObjectStructure>(){
//                    public void onFailure(Throwable caught) {
//                        throw new RuntimeException("Unable to load proposalInfo object structure", caught);                
//                    }
//
//                    @Override
//                    public void onSuccess(ObjectStructure result) {
//                        LUDictionaryManager.getInstance().loadStructure(result);
//
//                        //  If proposal Info structure load OK, load cluInfo structure                      
//                        luRpcServiceAsync.getObjectStructure(LUDictionaryManager.STRUCTURE_CLU_INFO, new AsyncCallback<ObjectStructure>(){
//                            public void onFailure(Throwable caught) {
//                                throw new RuntimeException("Unable to load cluInfo object structure", caught);                
//                            }
//
//                            @Override
//                            public void onSuccess(ObjectStructure result) {
//                                LUDictionaryManager.getInstance().loadStructure(result);
//
//                                //  Now we can show the initial view
//                                manager = new LUMApplicationManager();
//                                RootPanel.get().add(manager);
//                                if(manager.getCurrentView() == null)
//                                    manager.showDefaultView();
//
//
//                            }
//                        }
//                        );
//
//
//                    }
//                }
//                );

            }           
        });
    }

}
