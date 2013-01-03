package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.theme.Theme;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * An information message which can change its style to appear with a warning around the
 * content and add additional information to its layout when it needs to warn the user about the contained
 * information/widget functionality before they interact with it.
 * @author Kuali Student Team
 *
 */
public class WarnContainer extends Composite{
	private Image icon = Theme.INSTANCE.getCommonImages().getWarningDiamondIcon();
	private FlowPanel layout = new FlowPanel();
	private FlowPanel normalLayout = new FlowPanel();
	private FlowPanel warnLayout = new FlowPanel();
	
	public WarnContainer(){
		icon.addStyleName("ks-message-static-image");
		normalLayout.addStyleName("ks-message-static-margin");
		layout.add(icon);
		layout.add(normalLayout);
		layout.add(warnLayout);
		this.initWidget(layout);
	}
	
	public WarnContainer(boolean shown){
		icon.addStyleName("ks-message-static-image");
		normalLayout.addStyleName("ks-message-static-margin");
		layout.add(icon);
		layout.add(normalLayout);
		layout.add(warnLayout);
		this.initWidget(layout);
		this.showWarningLayout(shown);
	}
	
	public void add(Widget w){
		normalLayout.add(w);
	}
	
	public void addWarnWidget(Widget w){
		warnLayout.add(w);
		w.getElement().setAttribute("style", "display: inline");
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
