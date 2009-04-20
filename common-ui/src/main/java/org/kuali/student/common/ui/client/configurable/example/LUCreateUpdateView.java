package org.kuali.student.common.ui.client.configurable.example;



import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.example.dto.MockCluInfo;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;

import com.google.gwt.user.client.ui.Composite;

public class LUCreateUpdateView extends Composite implements View {
	private final ConfigurableLayout<MockCluInfo> layout;
	private final Validator validator;
	
	public LUCreateUpdateView(String type, String state, Validator validator) {
		this.validator = validator;
		this.layout = getLayout(type, state);
		super.initWidget(layout);
	}
	
	private ConfigurableLayout<MockCluInfo> getLayout(String type, String state) {
		// TODO wire in calls to LU service to get the ObjectStructure
		ObjectStructure structure = null; // this is going to explode if someone runs it :)
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
