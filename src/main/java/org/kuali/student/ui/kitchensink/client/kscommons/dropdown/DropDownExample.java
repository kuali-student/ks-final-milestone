package org.kuali.student.ui.kitchensink.client.kscommons.dropdown;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSDropDown;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class DropDownExample extends Composite {

    final HorizontalPanel panel = new HorizontalPanel();
    final KSDropDown ksDropDown = new KSDropDown();
    List<String> institutionList = new ArrayList<String>();

    public DropDownExample() {
        
        loadList();
        ksDropDown.populateDropDown(institutionList);
        ksDropDown.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent arg0) {
                ListBox lb = (ListBox)arg0.getSource();
                int i = lb.getSelectedIndex();
                Window.alert("You picked <" + lb.getItemText(i) + ">");
                
            }});
        panel.add(ksDropDown);

        super.initWidget(panel);
    }

    private void loadList() {
       institutionList.add("University of British Columbia");
       institutionList.add("Florida State University");
       institutionList.add("Naval Postgraduate School");
       institutionList.add("San Joaquin Delta College");
       institutionList.add("University of California, Berkeley");
       institutionList.add("University of Maryland, College Park");
       institutionList.add("Massachusetts Institute of Technology");
       institutionList.add("University of Southern California");
       institutionList.add("University of Washington");
       institutionList.add("Carnegie Mellon University");
        
    }
}
