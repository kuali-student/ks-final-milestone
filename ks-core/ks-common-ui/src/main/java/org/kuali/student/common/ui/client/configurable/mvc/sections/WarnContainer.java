package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WarnContainer extends Composite{
	private KSImage icon = Theme.INSTANCE.getCommonImages().getWarningDiamondIcon();
	private VerticalFlowPanel layout = new VerticalFlowPanel();
	private VerticalFlowPanel normalLayout = new VerticalFlowPanel();
	private VerticalFlowPanel warnLayout = new VerticalFlowPanel();
	
	public WarnContainer(){
		icon.addStyleName("ks-message-static-image");
		layout.add(icon);
		layout.add(normalLayout);
		layout.add(warnLayout);
		this.initWidget(layout);
	}
	
	public void add(Widget w){
		normalLayout.add(w);
	}
	
	public void addWarnWidget(Widget w){
		warnLayout.add(w);
	}
	
	public void showWarningLayout(boolean show){
		icon.setVisible(show);
		warnLayout.setVisible(show);
		if(show){
			layout.addStyleName("ks-message-static");
		}
		else{

			layout.removeStyleName("ks-message-static");
		}
	}

}
