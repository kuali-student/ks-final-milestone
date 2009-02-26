package org.kuali.student.common.ui.client;

import org.kuali.student.common.ui.client.widgets.KSResizablePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;

public class CommonUI implements EntryPoint {

	public void onModuleLoad() {
        KSResizablePanel r = new KSResizablePanel();
        final RichTextArea ll = new RichTextArea();
        ll.setPixelSize(200,200);
        ll.setHTML("asdf");
        
        r.setWidget(ll);
        RootPanel.get().add(r);
        
        final FlowPanel p = new FlowPanel(); 
        p.add(new Label("1"));
        p.add(new Label("2"));
        p.add(new Label("3"));
        p.setPixelSize(200,200);
        
        final KSResizablePanel resizePop = new KSResizablePanel();
        final PopupPanel pop = new PopupPanel(true);
        final RichTextArea l = new RichTextArea();
        l.setPixelSize(200,200);
        l.setHTML("asdf");
        //pop.addStyleName(KSStyles.KS_POPUP);
        
        Button bn = new Button("Click to show a popup panel");
        
        RootPanel.get().add(bn);
        bn.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                DeferredCommand.addCommand(new Command() {
                    public void execute() {
                        resizePop.setWidget(p);

                        pop.add(resizePop);                
                        pop.setPixelSize(l.getOffsetWidth(), l.getOffsetHeight());
                    }
                });
               
                pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                    public void setPosition(int offsetWidth, int offsetHeight) {
                        
                      int left = (Window.getClientWidth() - offsetWidth) / 3;
                      int top = (Window.getClientHeight() - offsetHeight) / 3;
                      pop.setPopupPosition(left, top);
                    }
                  });

            }
            
        });
	
	}
	


}
