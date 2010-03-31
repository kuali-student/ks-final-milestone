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
		
	}
	
	public void addMessage(KSLabel message){
		listPanel.add(message);
	}
	
	public void clear(){
		listPanel.clear();
	}
}
