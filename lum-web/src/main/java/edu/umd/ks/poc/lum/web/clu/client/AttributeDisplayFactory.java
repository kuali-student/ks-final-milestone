package edu.umd.ks.poc.lum.web.clu.client;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.widgets.DateBox;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.web.core.client.Authorization;

public class AttributeDisplayFactory {
    
    private String attributeName;
    private List<String> values = new ArrayList<String>();
    private List<String> dropdownValues = new ArrayList<String>();
    private String type;
    private boolean list;    
    private String user;
    private boolean editable;
    FlexTable   tblValues = new FlexTable();

        public AttributeDisplayFactory(boolean editable, String attributeName, String type, String user, boolean list, List<String> values, List<String> dropdownValues) {
            super();            
            this.attributeName=attributeName;
            this.type=type;
            this.list=list;
            this.user=user;
            this.editable = editable;
            if(values!=null){
                this.values=values;
            }
            if(dropdownValues!=null){
                this.dropdownValues=dropdownValues;
            }
            init();
        }
        
        
        protected void init(){
                    
            
            final Widget input;            
            if("Drop Down".equals(type)&&Authorization.isAuthorized(user)&&editable){
                input = new ListBox();
            	((ListBox) input).addItem("Select:","");
                //((ListBox) input).setMultipleSelect(list);
            	int i=1;
                for(String item:dropdownValues){
                    ((ListBox) input).addItem(item);
                    if(!list&&values!=null&&values.size()>0&&values.get(0).equals(item)){
                    	((ListBox) input).setSelectedIndex(i);
                    	values.clear();
                    }
                    i++;
                }
                if(list){
                	this.tblValues.setWidget(0, 1, getAddValueWidget((ListBox) input));
            	}
            }else{
               if(!Authorization.isAuthorized(user)||!editable){
            	   input = new Label();
               }else if("Text Area".equals(type)){
                   input = new TextArea();
               }else if("Date Box".equals(type)){
                   input = new DateBox();
               }else if("Check Box".equals(type)){
            	   input = new CheckBox();
            	   if(values!=null&&values.size()>0){
            		   ((CheckBox)input).setChecked(Boolean.parseBoolean(values.get(0)));
            		   values.clear();
            	   }
               }else{ // Default to text box
                   input = new TextBox();
               }
               
               if(!"Check Box".equals(type)){
	               if(list&&Authorization.isAuthorized(user)&&editable){            
	                   this.tblValues.setWidget(0, 1, getAddValueWidget((HasText) input));
	               }else if(values!=null&&values.size()>0){
	            	   ((HasText)input).setText(values.get(0));
	            	   values.remove(0);
	               }
               }
               
            }
                                    
            this.tblValues.setWidget(0, 0, input);
            

            
            updateAttributeInitValues();
        }
        
    	private Widget getAddValueWidget(final ListBox dropDown) {
            Image add = new Image("images/treeClosed.gif");
        	add.addClickListener(new ClickListener(){
    			public void onClick(Widget sender) {
    				if(dropDown.getSelectedIndex()!=0){
    					values.add(dropDown.getValue(dropDown.getSelectedIndex()));
    					dropDown.setSelectedIndex(0);
    					updateAttributeInitValues();
    				}
    			}
        	});
    		return add;
    	}


		public List<String> getAttributeValues(){
			List<String> returnValues = new ArrayList<String>();
			if("Drop Down".equals(type)){
				returnValues.addAll(values);
				ListBox dropdown = ((ListBox)tblValues.getWidget(0, 0));
				if(dropdown.getSelectedIndex()!=0){
					returnValues.add(dropdown.getValue(dropdown.getSelectedIndex()));
				}
			}else if ("Check Box".equals(type) && tblValues.getWidget(0, 0) instanceof CheckBox){
				returnValues.addAll(values);
				CheckBox checkBox = ((CheckBox)tblValues.getWidget(0, 0));
				returnValues.add(String.valueOf(checkBox.isChecked()));
			}else{
				returnValues.addAll(values);
				HasText textField = ((HasText)tblValues.getWidget(0, 0));
				if(!"".equals(textField.getText())){
					returnValues.add(textField.getText());
				}
			}
			return returnValues;
		}
		
        private void updateAttributeInitValues(){
            
            for(int i = 1;i<tblValues.getRowCount();i++){
                tblValues.removeRow(i);
            }

            int i = 0;
           
            i = 0;
            for(String value:values){
                this.tblValues.setWidget(i+1, 0, new Label(value));
                if(Authorization.isAuthorized(user)){
                	this.tblValues.setWidget(i+1, 1, getRemoveValueWidget(i));
                }
                i++;
            }
        }
        
        private Widget getRemoveValueWidget(final int i) {
            Image remove = new Image("images/treeOpen.gif");            
            remove.addClickListener(new ClickListener(){
                public void onClick(Widget sender) {
                    values.remove(i);
                    updateAttributeInitValues();
                }
            });
            return remove;
        }

        private Widget getAddValueWidget(final HasText textBox) {
            Image add = new Image("images/treeClosed.gif");
        	add.addClickListener(new ClickListener(){
    			public void onClick(Widget sender) {
    				if(!"".equals(textBox.getText())){
    					values.add(textBox.getText());
    					textBox.setText("");
    					updateAttributeInitValues();
    				}
    			}
        	});
    		return add;
    	}

        /**
         * @return the tblValues
         */
        public FlexTable getTblValues() {
            return tblValues;
        }

        public String getAttributeName() {
            return this.attributeName;
        }
}
