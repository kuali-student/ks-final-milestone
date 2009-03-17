package org.kuali.student.ui.kitchensink.client.kscommons.radiobuttonlist;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class RadioButtonListExample extends Composite {
    final HorizontalPanel main = new HorizontalPanel();
    
    public class Color{
        private String id;
        private String color;
        
        public Color(String id, String color) {
            super();
            this.color = color;
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }
        
    }
    
    private List<Color> colors = new ArrayList<Color>();
    
    private ListItems tableItems = new ListItems(){
        @Override
        public List<String> getAttrKeys() {
            List<String> attributes = new ArrayList<String>();
            attributes.add("Color");
            return attributes;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String value = null;
            for(Color c: colors){
                if(c.getId().equals(id)){
                    if(attrkey.equals("Color")){
                        value = c.getColor();
                    }
                    break;
                }
            }
            return value;
        }

        @Override
        public int getItemCount() {    
            return colors.size();
        }

        @Override
        public List<String> getItemIds() {
            List<String> ids = new ArrayList<String>();
            for(Color c: colors){
                ids.add(c.getId());
            }
            return ids;
        }

        @Override
        public String getItemText(String id) {
            String value = null;
            for(Color c: colors){
                if(c.getId().equals(id)){
                    value = c.getColor();
                    break;
                }
            }
            return value;
        }
    };
     
    public RadioButtonListExample(){
        KSRadioButtonList radioButtonList = new KSRadioButtonList("Colors");
        colors.add(new Color("1", "Blue"));
        colors.add(new Color("2", "Red"));
        colors.add(new Color("3", "Orange"));
        colors.add(new Color("4", "Yellow"));
        colors.add(new Color("5", "Green"));
        colors.add(new Color("6", "Purple"));
        radioButtonList.setListItems(tableItems);
        final KSLabel label = new KSLabel();
        radioButtonList.addSelectionChangeHandler(new SelectionChangeHandler(){

            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
               List<String> selectedItems = w.getSelectedItems();
               String selectedString = "SELECTED: \n";
               
               if(selectedItems.size() > 0){
                   for(String s: selectedItems){
                       selectedString = selectedString + tableItems.getItemText(s) + "\n";
                   }
               }
               
               label.setText(selectedString);
                
            }
        });
        
        main.add(radioButtonList);
        this.initWidget(main);
    }

}
