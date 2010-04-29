package org.kuali.student.common.ui.client.widgets.buttons;

import org.kuali.student.common.ui.client.widgets.ClickablePanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class KSLinkButton extends ClickablePanel{

	public static enum ButtonStyle{
		PRIMARY("ks-button-primary"),
		SECONDARY("ks-button-secondary"),
		HELP("ks-form-module-elements-help"),
		DELETE("ks-form-module-elements-delete"),
		FORM_SMALL("ks-form-button-small"),
		FORM_LARGE("ks-form-button-large");
		
		private String style;
		
		private ButtonStyle(String style){
			this.style = style;
		}
		
		public String getStyle(){
			return style;
		}
	};
	
	public KSLinkButton(Widget w){
		this(w, ButtonStyle.SECONDARY);
	}
	
	public KSLinkButton(Widget w, ButtonStyle style){
		super();
		
		String id = HTMLPanel.createUniqueId();
		HTMLPanel panel = new HTMLPanel("<a href='javascript:return false;' class='" + style.getStyle() + "' id='" + id + "'></a>");
		panel.add(w, id);
		
		this.setWidget(panel);
		this.setStyleName("ks-form-button-container");
	}
	
	public KSLinkButton(String text){
		super();
		
		//Label label = new Label(text);
		String id = HTMLPanel.createUniqueId();
		HTMLPanel panel = new HTMLPanel("<a href='javascript:return false;' id='" + id + "'>"+ text +"</a>");
		//panel.add(label, id);
		
		this.setWidget(panel);		
	}
	
	public KSLinkButton(String text, ButtonStyle style){
		super();
		
		//Label label = new Label(text);
		String id = HTMLPanel.createUniqueId();
		HTMLPanel panel = new HTMLPanel("<a href='javascript:return false;' class='" + style.getStyle() + "' id='" + id + "'>"+ text +"</a>");
		//panel.add(label, id);
		
		this.setWidget(panel);
		//this.setStyleName("ks-form-button-container");
	}
}
