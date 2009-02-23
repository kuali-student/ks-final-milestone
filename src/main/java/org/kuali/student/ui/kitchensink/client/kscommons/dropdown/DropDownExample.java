package org.kuali.student.ui.kitchensink.client.kscommons.dropdown;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSDropDown;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class DropDownExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
     
    final KSDropDown dropDown = GWT.create(KSDropDown.class);

    public DropDownExample() {
        
        main.addStyleName(STYLE_EXAMPLE);
        
        dropDown.init(false);
        loadList();

        dropDown.getListBox().addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent arg0) {
                ListBox lb = (ListBox)arg0.getSource();
                int i = lb.getSelectedIndex();
                Window.alert("You picked <" + lb.getItemText(i) + ">");
                
            }});
        main.add(dropDown);

        super.initWidget(main);
    }

    private void loadList() {
       dropDown.getListBox().addItem("University of British Columbia");
       dropDown.getListBox().addItem("Florida State University");
       dropDown.getListBox().addItem("Naval Postgraduate School");
       dropDown.getListBox().addItem("San Joaquin Delta College");
       dropDown.getListBox().addItem("University of California, Berkeley");
       dropDown.getListBox().addItem("University of Maryland, College Park");
       dropDown.getListBox().addItem("Massachusetts Institute of Technology");
       dropDown.getListBox().addItem("University of Southern California");
       dropDown.getListBox().addItem("University of Washington");
       dropDown.getListBox().addItem("Carnegie Mellon University");
        
    }
}
