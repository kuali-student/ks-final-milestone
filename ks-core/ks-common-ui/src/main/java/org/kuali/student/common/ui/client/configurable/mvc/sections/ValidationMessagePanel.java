package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.List;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.client.ui.Composite;

public class ValidationMessagePanel extends Composite{
	
	private KSListPanel listPanel = new KSListPanel();
	
	public ValidationMessagePanel(){
		this.initWidget(listPanel);
		this.addStyleName("ks-form-module-validation");
	}
	
	@Deprecated
	public void addMessages(List<ValidationResultInfo> list){
		for(ValidationResultInfo vr: list){
    		//HorizontalBlockFlowPanel validationLine = new HorizontalBlockFlowPanel();
    		//VerticalFlowPanel imagePanel = new VerticalFlowPanel();
    		KSLabel message = new KSLabel(vr.getMessage());
    		listPanel.add(message);
    		message.setWordWrap(true);
    		if(vr.getLevel() == ErrorLevel.ERROR){
    			message.addStyleName("ks-form-validation-label");
    		}
    		else if(vr.getLevel() == ErrorLevel.WARN){
    			//message.addStyleName("KS-Validation-Warning-Message");
    		}
    		else{
    			//message.addStyleName("KS-Validation-Ok-Message");
    		}
    		//message.addStyleName("KS-Validation-Message");
        }
		
	}
	
	public void addMessage(KSLabel message){
		listPanel.add(message);
	}
	
	public void clear(){
		listPanel.clear();
	}
}
