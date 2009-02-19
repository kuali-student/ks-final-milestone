package org.kuali.student.ui.kitchensink.client.kscommons.listbox;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSListBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListBoxExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final CaptionPanel panel1 = new CaptionPanel("Single select");
    final CaptionPanel panel2 = new CaptionPanel("Multi select");

    final KSListBox listBox1 = GWT.create(KSListBox.class);
    final KSListBox listBox2 = GWT.create(KSListBox.class);
    
    List<String> institutionList = new ArrayList<String>();
    StringBuffer sb = new StringBuffer("You Have selected < ");

    public ListBoxExample() { 

        loadLists();

        listBox1.setMultipleSelect(false);
        listBox1.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent arg0) {
                ListBox lb = (ListBox)arg0.getSource();
                int i = lb.getSelectedIndex();
                Window.alert("You selected <" + lb.getItemText(i).trim() + ">");

            }});

        listBox2.setMultipleSelect(true);

        // Doesn't quite work yet!
//      ksListBox2.addChangeHandler(new ChangeHandler() {
//      @Override
//      public void onChange(ChangeEvent arg0) {

//      ListBox lb = (ListBox)arg0.getSource();
//      if (lb.getSelectedIndex() >= 0) {
//      for (int i = 0; i < lb.getItemCount(); i++) {
//      if (lb.isItemSelected(i))
//      sb.append(lb.getValue(i).trim());
//      sb.append(" ");
//      }
//      }
//      sb.append(" >");
//      Window.alert( sb.toString() );

//      }});

        panel1.add(listBox1);
        panel2.add(listBox2);

        main.add(panel1);
        main.add(panel2);

        super.initWidget(main);
    }

    private void loadLists() {
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

        for (String s : institutionList) {
            listBox1.addItem(s);
            listBox2.addItem(s);    
        }
    }
}
