package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSModalDialogPanelAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSModalDialogPanelImpl extends KSModalDialogPanelAbstract{ 
	private PopupPanel glass = new PopupPanel();
	
	public KSModalDialogPanelImpl(){
		super();
		setupDefaultStyle();
		super.setModal(true);
		super.setResizable(false);
		
		Window.addResizeHandler(new ResizeHandler(){

			public void onResize(ResizeEvent event) {
				if(isShowing()){
					KSModalDialogPanelImpl.this.show();
				}
			}
		});
		
	}
	
	@Override
    public void show(){
		glass.addStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.show();
		super.center();
	}
	
	@Override
    public void hide(){
		super.hide();
		glass.removeStyleName(KSStyles.KS_MOUSE_WAIT);
		glass.hide();
	}
	
	private void setupDefaultStyle(){
		super.addStyleName(KSStyles.KS_MODAL_POPUP);
		glass.addStyleName(KSStyles.KS_MODAL_GLASS);
	}

    @Override
    public void addStyleName(String style) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        super.addStyleName(style);
    }

    @Override
    public void setHeader(String headerText) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        super.setHeader(headerText);
    }

    @Override
    public void setWidget(Widget w) {
        // TODO Gary Struthers - THIS METHOD NEEDS JAVADOCS
        super.setWidget(w);
    }
	
}
