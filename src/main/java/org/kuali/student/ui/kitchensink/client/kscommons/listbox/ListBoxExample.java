package org.kuali.student.ui.kitchensink.client.kscommons.listbox;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListBoxExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final KSListBox listBox = new KSListBox();

    ListItems institutionList;

    public ListBoxExample() { 

        main.addStyleName(STYLE_EXAMPLE);

        loadLists();

        listBox.setListItems(institutionList);
        listBox.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                List<String> ids = w.getSelectedItems();
                StringBuffer sb = new StringBuffer("You Have selected:\n");
                for (String item:ids){
                    sb.append(institutionList.getItemText(item)).append("\n");
                }
                Window.alert(sb.toString());
                
            }});

        main.add(listBox);

        super.initWidget(main);
    }

    private void loadLists() {      
        institutionList = new ListItems(){
            List<String> names = Arrays.asList(
                    "University of British Columbia",
                    "Florida State University",
                    "Naval Postgraduate School",
                    "San Joaquin Delta College",
                    "University of California, Berkeley",
                    "University of Maryland, College Park",
                    "Massachusetts Institute of Technology",
                    "University of Southern California",
                    "University of Washington",
                    "Carnegie Mellon University");

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                Integer index;
                try{
                    index = Integer.valueOf(id);
                    value = names.get(index);
                } catch (Exception e) {
                }

                return value;
            }

            @Override
            public int getItemCount() {    
                return names.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(int i=0; i < names.size(); i++){
                    ids.add(String.valueOf(i));
                }
                return ids;
            }

            @Override
            public String getItemText(String id) {
                return getItemAttribute(id, "Name");
            }
        };               
    }
}
