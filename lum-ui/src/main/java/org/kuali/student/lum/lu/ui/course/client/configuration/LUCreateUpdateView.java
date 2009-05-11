package org.kuali.student.lum.lu.ui.course.client.configuration;



import org.kuali.student.common.ui.client.application.ApplicationComposite;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.history.KSHistory;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class LUCreateUpdateView extends Composite implements View {
    ApplicationComposite app = new ApplicationComposite();
    private final SimplePanel panel = new SimplePanel();
    private ConfigurableLayout<CluInfo> layout;
	private final Validator validator;
	private final String luType;
	private final String luState;
	
	public LUCreateUpdateView(String type, String state, Validator validator) {
		this.validator = validator;
		this.luType = type;
		this.luState = state;		        
		app.setContent(panel);
		super.initWidget(app);
		
        LuRpcService.Util.getInstance().getObjectStructure("cluInfo", new AsyncCallback<ObjectStructure>(){
            public void onFailure(Throwable caught) {
                GWT.log("Unable to load object structure", caught);                
            }

            @Override
            public void onSuccess(ObjectStructure result) {
                layout = getLayout(result, luType, luState);
                
            }
        }); 
	}

	private ConfigurableLayout<CluInfo> getLayout(ObjectStructure structure, String type, String state) {	   	
		LULayoutFactory factory = new LULayoutFactory(structure, validator);
        
        DefaultCreateUpdateLayout layout = (DefaultCreateUpdateLayout)factory.getLayout(type, state);
//        history = new KSHistory(getController(), layout);
        layout.addCancelSectionHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                LUCreateUpdateView.this.getController().showView(LUMViews.HOME_MENU);
            }            
        });     

		return layout;
	}
	
	@Override
	public boolean beforeHide() {
		// TODO check if there are unsaved changes
		return true;
	}

	@Override
	public void beforeShow() {
	    IncrementalCommand command = new IncrementalCommand() {

            @Override
            public boolean execute() {
                if(layout == null) {
                    return true; //wait for stupid thing to load
                }
                panel.setWidget(layout);

                layout.render();
                return false;
            }
	        
	    };
	    if(command.execute()) //only scheduling it if I must
	        DeferredCommand.addCommand(command);
	    
	}

	@Override
	public Controller getController() {
		return Controller.findController(this);
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
	
	public ConfigurableLayout<CluInfo> getLayout() {
	    return layout;
	}

    public void addLayoutToHistory(final KSHistory history, final LUMViews create_course) {
        IncrementalCommand command = new IncrementalCommand() {

            @Override
            public boolean execute() {
                if(layout == null) {
                    return true; //wait for stupid thing to load
                }
                history.addLayoutToView(create_course, layout);
//                if(layout instanceof DefaultCreateUpdateLayout)
//                    ((DefaultCreateUpdateLayout<CluInfo>)layout).setShowStartSectionEnabled(false); //TODO this needs to be figured out
                History.fireCurrentHistoryState();
                return false;
            }
            
        };
        if(command.execute()) //only scheduling it if I must
            DeferredCommand.addCommand(command);
    }

}
