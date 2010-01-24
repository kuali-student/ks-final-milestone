package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.ListBox;

/**
 * @author Kuali Student Team
 *         </p>
 *         The standard gwt ListBox with a couple extra methods I commonly use.
 */
public class SearchListBox extends ListBox implements HasText {

    
    private ChangeListenerCollection changeListeners;

    
    protected void fireChanges(){
        if (changeListeners != null) {
            changeListeners.fireChange(this);
        }
    }
    
    public void addChangeListener(ChangeListener listener) {
        if (changeListeners == null) {
          changeListeners = new ChangeListenerCollection();
          sinkEvents(Event.ONCHANGE);
        }
        changeListeners.add(listener);
    }
    
    public int findIndex(String value){ 
        int iRet = -1;  
        if(value == null) return iRet;      
        for(int i=0; i< this.getItemCount(); i++){
            if(value.equals(this.getValue(i))){
                iRet = i;
            }
        }       
        return iRet;                
    }
    
    public int findIndexOfText(String value){    
        int iRet = -1;  
        if(value == null) return iRet;      
        for(int i=0; i< this.getItemCount(); i++){
            if(value.equals(this.getItemText(i))){
                iRet = i;
            }
        }       
        return iRet;                
    }
    
    /**
     * This overridden method ...
     * 
     * @see com.google.gwt.user.client.ui.ListBox#setItemSelected(int, boolean)
     */   
    public void setItemSelected(int index, boolean selected) {
        super.setItemSelected(index, selected);
        this.fireChanges();
    }

    public void setItemSelected(String value, boolean selected)
        throws java.lang.IndexOutOfBoundsException
    {
        int index = this.findIndex(value);      
        if(index >= 0)  {   
            this.setItemSelected(index, selected);
            this.fireChanges();
        }
    }
    
    public String getSelectedValue(){
        return this.getValue(this.getSelectedIndex());
    }
    
    public String getSelectedText(){        
        return this.getItemText(this.getSelectedIndex());
    }

    
    public String getText() {        
        return this.getSelectedText();
    }
    
    public void setText(String text) {
        int index = this.findIndexOfText(text);
        if(index >= 0)  {
            this.setSelectedIndex(this.findIndexOfText(text));
            this.fireChanges();
        }
    }

}
