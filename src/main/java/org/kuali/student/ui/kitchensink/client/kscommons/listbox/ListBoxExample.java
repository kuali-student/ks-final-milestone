package org.kuali.student.ui.kitchensink.client.kscommons.listbox;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.ui.client.widgets.KSListBox;

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
    final KSListBox ksListBox1 ;
    final KSListBox ksListBox2 ;
    List<String> institutionList = new ArrayList<String>();
    StringBuffer sb = new StringBuffer("You Have selected < ");

    public ListBoxExample() { 

        loadList();

        ksListBox1 = new KSListBox(institutionList, false);
        ksListBox1.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent arg0) {
                ListBox lb = (ListBox)arg0.getSource();
                int i = lb.getSelectedIndex();
                Window.alert("You selected <" + lb.getItemText(i).trim() + ">");

            }});

        ksListBox2 = new KSListBox(institutionList, true);
        
        // Doesn't quite work yet!
//        ksListBox2.addChangeHandler(new ChangeHandler() {
//            @Override
//            public void onChange(ChangeEvent arg0) {
//
//                ListBox lb = (ListBox)arg0.getSource();
//                if (lb.getSelectedIndex() >= 0) {
//                    for (int i = 0; i < lb.getItemCount(); i++) {
//                        if (lb.isItemSelected(i))
//                            sb.append(lb.getValue(i).trim());
//                            sb.append(" ");
//                    }
//                }
//                sb.append(" >");
//                Window.alert( sb.toString() );
//
//            }});

        panel1.add(ksListBox1);
        panel2.add(ksListBox2);

        main.add(panel1);
        main.add(panel2);

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
