package org.kuali.student.ui.kitchensink.client.kscommons.dropdown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class DropDownExample extends Composite {

    final HorizontalPanel main = new HorizontalPanel();
     
    final KSDropDown dropDown = new KSDropDown();
    
    ListItems schools;

    public DropDownExample() {
        
        loadList();

        dropDown.setListItems(schools);
        dropDown.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                List<String> ids = w.getSelectedItems();
                Window.alert("You picked <" + schools.getItemText(ids.get(0)) + ">");
                
            }});
        main.add(dropDown);

        super.initWidget(main);
    }

    private void loadList() {       
        schools = new ListItems(){
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
