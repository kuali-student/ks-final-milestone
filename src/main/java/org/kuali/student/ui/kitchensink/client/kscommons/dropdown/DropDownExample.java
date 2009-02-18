package org.kuali.student.ui.kitchensink.client.kscommons.dropdown;

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
    List<String> institutionList = new ArrayList<String>();
      
    final KSDropDown dropDown = GWT.create(KSDropDown.class);

    public DropDownExample() {
        
        loadList();
        dropDown.populateDropDown(institutionList);
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
