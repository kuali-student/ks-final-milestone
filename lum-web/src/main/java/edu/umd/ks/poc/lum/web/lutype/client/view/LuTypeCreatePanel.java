package edu.umd.ks.poc.lum.web.lutype.client.view;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;

public class LuTypeCreatePanel extends Composite{

    public static class LuTypeCreateEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypeCreateEvent.class, new LuTypeCreateEvent().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypeCreateEvent.class);
        }
    }
    static {
        new LuTypeCreateEvent(); 
    }
    
    VerticalPanel    root = new VerticalPanel();
    HorizontalPanel top       = null;
    FlexTable       fTblTop = null;
    
    TextBox luTypeName    = null;
    TextBox luTypeDesc    = null;
    TextArea luTypeComment    = null;
    
    Button  save          = null;
    
    String luTypeKey;
    
    boolean loaded = false;
    
    
    public LuTypeCreatePanel() {        
        super();
        
        super.initWidget(root);
        
    }

    protected void onLoad() {
        super.onLoad();
        
        if(!loaded){
            loaded = true;    
            VerticalPanel buttonPanel = new VerticalPanel();
            buttonPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
            

            top  = new HorizontalPanel();
            fTblTop = new FlexTable();
            
            //luTypeName = new TextBox();
            luTypeDesc = new TextBox();
            luTypeComment = new TextArea();
            
            save = new Button("Save");
            
            buttonPanel.add(save);
            
            //fTblTop.setWidget(0, 0,new Label( "Lu Type Name:"));
            //fTblTop.setWidget(0, 1,luTypeName);
            fTblTop.setWidget(0, 0,new Label( "Lu Type Description:"));
            fTblTop.setWidget(0, 1,luTypeDesc);
            fTblTop.setWidget(1, 0,new Label( "Comments:"));
            fTblTop.setWidget(1, 1,luTypeComment);
            
            
            save.addClickListener(new ClickListener(){                
                public void onClick(Widget sender) {
                    if(isValid()){
                        createLuType();
                    }else{
                        Window.alert("Invalid Lu Type");
                    }
                }});
            
            top.add(fTblTop);
            top.add(buttonPanel);
            root.add(top);
                                    
        }
        
    }
    
    protected boolean isValid(){
        boolean bRet = false;
        if(this.luTypeDesc.getText() != null &&
                !this.luTypeDesc.getText().equals("")){
            bRet =  true;
        } 
        
        return bRet;
            
    }
    
    protected void createLuType(){
        LuTypeInfo  luType      = new LuTypeInfo();
        
        luType.setDescription(this.luTypeDesc.getText());
        luType.setUpdateUserComment(this.luTypeComment.getText());
        
        GlobalEventDispatcher.getInstance().fireEvent(LuTypeCreateEvent.class, luType);
        
        
    }

	/**
	 * @return the luTypeKey
	 */
	public String getLuTypeKey() {
		return luTypeKey;
	}

	/**
	 * @param luTypeKey the luTypeKey to set
	 */
	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
	}
    
}
