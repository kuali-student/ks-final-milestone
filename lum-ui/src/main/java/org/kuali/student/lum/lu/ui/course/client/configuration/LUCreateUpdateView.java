package org.kuali.student.lum.lu.ui.course.client.configuration;



import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class LUCreateUpdateView extends Composite implements View {
	private final SimplePanel panel = new SimplePanel();
    private ConfigurableLayout<CluInfo> layout;
	private final Validator validator;
	private final String luType;
	private final String luState;
	
	public LUCreateUpdateView(String type, String state, Validator validator) {
		this.validator = validator;
		this.luType = type;
		this.luState = state;
		
        LuRpcService.Util.getInstance().getObjectStructure("cluInfo", new AsyncCallback<ObjectStructure>(){
            public void onFailure(Throwable caught) {
                GWT.log("Unable to load object structure", caught);                
            }

            @Override
            public void onSuccess(ObjectStructure result) {
                layout = getLayout(result, luType, luState);
                panel.setWidget(layout);
            }
        });
        
		super.initWidget(panel);
	}
	
	private ConfigurableLayout<CluInfo> getLayout(ObjectStructure structure, String type, String state) {	   	
		LULayoutFactory factory = new LULayoutFactory(structure, validator);
		return factory.getLayout(type, state);
	}
	
	@Override
	public boolean beforeHide() {
		// TODO check if there are unsaved changes
		return true;
	}

	@Override
	public void beforeShow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Controller getController() {
		return Controller.findController(this);
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}
	


}
