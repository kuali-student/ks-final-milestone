package edu.umd.ks.poc.lum.web.scat.client;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;


public class CreateScat extends Composite {
    
    public static class CreateScatEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(CreateScatEvent.class, new CreateScatEvent().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(CreateScatEvent.class);
        }
    }

    static {
        new CreateScatEvent();
    }
    

    VerticalPanel root = new VerticalPanel();
    
    TextBox scatTableDesc = null;
    
    Button btnCreate = null;
    
    boolean loaded = false;
    
    
    public CreateScat() {
        super.initWidget(root);
    }
    
    
    protected void onLoad() {    
        super.onLoad();
        if(!loaded){
            loaded=true;
            FlexTable fTable = new FlexTable();
            
            scatTableDesc = new TextBox();
            btnCreate = new Button("Create");
            
            btnCreate.addClickListener(new ClickListener(){                
                public void onClick(Widget sender) {
                    GlobalEventDispatcher.getInstance().fireEvent(CreateScatEvent.class, getScatTableInfo());
                }});
            
            fTable.setWidget(0, 0, new Label("Table Description"));
            fTable.setWidget(0, 1, scatTableDesc);
            fTable.setWidget(0, 2, btnCreate);
            
            root.add(fTable);
            
        }                
    }
    
    public ScatTableInfo getScatTableInfo(){
        ScatTableInfo sRet = null;
        
        if(scatTableDesc.getText() != null && !scatTableDesc.getText().equals("")){
            sRet = new ScatTableInfo();
            sRet.setTableDescription(scatTableDesc.getText());
        }
        
        return sRet;
    }

    public void reset(){
    	scatTableDesc.setText("");
    }
}
