package org.kuali.student.ui.kitchensink.client.kscommons.selectabletable;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class SelectableTableExample extends Composite {
    final HorizontalPanel main = new HorizontalPanel();
    
    public class Person{
        private String id;
        private String lastName;
        private String firstName;
        
        public Person(String id, String firstName,  String lastName) {
            super();
            this.firstName = firstName;
            this.id = id;
            this.lastName = lastName;
        }
        
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firtName) {
            this.firstName = firtName;
        } 
        
    }
    
    private List<Person> people = new ArrayList<Person>();
    
    private ListItems tableItems = new ListItems(){
        @Override
        public List<String> getAttrKeys() {
            List<String> attributes = new ArrayList<String>();
            attributes.add("First");
            attributes.add("Last");
            return attributes;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String value = null;
            for(Person p: people){
                if(p.getId().equals(id)){
                    if(attrkey.equals("First")){
                        value = p.getFirstName();
                    }
                    else if(attrkey.equals("Last")){
                        value = p.getLastName();
                    }
                    break;
                }
            }
            return value;
        }

        @Override
        public int getItemCount() {    
            return people.size();
        }

        @Override
        public List<String> getItemIds() {
            List<String> ids = new ArrayList<String>();
            for(Person p: people){
                ids.add(p.getId());
            }
            return ids;
        }

        @Override
        public String getItemText(String id) {
            String value = null;
            for(Person p: people){
                if(p.getId().equals(id)){
                    value = p.getLastName() + ", " + p.getFirstName();
                    break;
                }
            }
            return value;
        }
    };
     
    public SelectableTableExample(){
        KSSelectableTableList selectTable = new KSSelectableTableList();
        people.add(new Person("1", "Bob", "Jones"));
        people.add(new Person("2", "Brian", "Smith"));
        people.add(new Person("3", "Sally", "Samson"));
        people.add(new Person("4", "Joe", "Somebody"));
        people.add(new Person("5", "Wil", "Johnson"));
        people.add(new Person("6", "Gary", "Struthers"));
        people.add(new Person("7", "Heather", "Johnson"));
        people.add(new Person("8", "Will", "Gomes"));
        people.add(new Person("9", "Joe", "Yin"));
        selectTable.setListItems(tableItems);
        final KSLabel label = new KSLabel();
        selectTable.addSelectionChangeHandler(new SelectionChangeHandler(){

            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
               List<String> selectedItems = w.getSelectedItems();
               String selectedString = "SELECTED COLOR: \n";
               
               if(selectedItems.size() > 0){
                   for(String s: selectedItems){
                       selectedString = selectedString + tableItems.getItemText(s) + "\n";
                   }
               }
               
               label.setText(selectedString);
                
            }
        });
        main.add(selectTable);
        main.add(label);
        this.initWidget(main);
    }

}
