package org.kuali.student.ui.kitchensink.client.kscommons.dropdown;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class DropDownExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
     
    final KSDropDown dropDown = new KSDropDown(false);

    public DropDownExample() {
        
        main.addStyleName(STYLE_EXAMPLE);
        
        loadList();

        dropDown.addChangeHandler(new ChangeHandler() {
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
       dropDown.addItem("University of British Columbia");
       dropDown.addItem("Florida State University");
       dropDown.addItem("Naval Postgraduate School");
       dropDown.addItem("San Joaquin Delta College");
       dropDown.addItem("University of California, Berkeley");
       dropDown.addItem("University of Maryland, College Park");
       dropDown.addItem("Massachusetts Institute of Technology");
       dropDown.addItem("University of Southern California");
       dropDown.addItem("University of Washington");
       dropDown.addItem("Carnegie Mellon University");
        
    }
}
