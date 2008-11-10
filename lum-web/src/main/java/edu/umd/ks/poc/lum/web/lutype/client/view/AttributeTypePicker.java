package edu.umd.ks.poc.lum.web.lutype.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;

public class AttributeTypePicker extends HorizontalPanel {
    
    boolean loaded = false;
    private String value;
    final AttributeTypePicker me = this;
    
    List<RadioButton>    radioList = new ArrayList<RadioButton>();
        
    public enum AttributeTypes{
        TEXT_BOX("Text Box"), TEXT_AREA("Text Area"), DATE_BOX("Date Box"), DROP_DOWN("Drop Down"), CHECKBOX("Check Box");
        
        private final String type;
        AttributeTypes(String str){
            type = str;
        }
        public String attributeType(){
            return type;
        }
    }
        
    public static class LuTypeAttributeSelector extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypeAttributeSelector.class, new LuTypeAttributeSelector().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypeAttributeSelector.class);
        }
    }
    static {
        new LuTypeAttributeSelector(); 
    }

    protected void onLoad() {
        super.onLoad();
        
        if(!loaded){
            loaded = true;
            
            for(AttributeTypes type: AttributeTypes.values()){
                final RadioButton rb =new RadioButton("attributeType", type.attributeType());
                
                rb.addClickListener(new ClickListener(){                    
                    public void onClick(Widget sender) {
                    	value=rb.getText();
                        GlobalEventDispatcher.getInstance().fireEvent(LuTypeAttributeSelector.class, rb);                        
                    }});
                
                this.add(rb);
                radioList.add(rb);
            }
            if(radioList.size()>0)
                radioList.get(0).setChecked(true);
        }
        
        
    }

	/**
	 * @return the value
	 */
	public String getValue() {
	    
	    for(RadioButton rb: radioList){
	        if(rb.isChecked()){
	            value = rb.getText();
	        }
	    }
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
