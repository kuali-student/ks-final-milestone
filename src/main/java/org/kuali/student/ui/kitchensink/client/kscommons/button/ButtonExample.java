package org.kuali.student.ui.kitchensink.client.kscommons.button;

import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class ButtonExample extends Composite {

    final SimplePanel panel = new SimplePanel();
    FlexTable table = new FlexTable();
    final Label title = new Label("Click a button");
    final KSButton ksButton1 = new KSButton("Number 1");
    final KSButton ksButton2 = new KSButton("Number 2");
    final KSButton ksButton3 = new KSButton("Number 3");

    public ButtonExample() {
        table.addStyleName("ksButton");
        
        ksButton1.addClickHandler(new MyClickHandler());
        ksButton2.addClickHandler(new MyClickHandler());
        ksButton3.addClickHandler(new MyClickHandler());
        
        int row = 0;
        table.setWidget(row, 1, title);
        
        row++;
        table.setWidget(row,0, ksButton1);
        table.setWidget(row,1, ksButton2);
        table.setWidget(row,2, ksButton3);
        
        panel.add(table);

        super.initWidget(panel);
    }
    
    public class MyClickHandler implements ClickHandler  {

        @Override
        public void onClick(ClickEvent event) {
            KSButton b = (KSButton)(event.getSource());
            
            Window.alert("You clicked button " + b.getText());
            
        }     
    }
}
