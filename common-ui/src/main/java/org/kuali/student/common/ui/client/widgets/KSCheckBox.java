package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.CheckBox;


public class KSCheckBox extends CheckBox{
    
    public KSCheckBox(){
        super();
        setupDefaultStyle();
    }
    
    public KSCheckBox(String label){
        super(label);
        setupDefaultStyle();
    }
    
    private void setupDefaultStyle(){
        addStyleName(KSStyles.KS_CHECKBOX_STYLE);
        
        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSCheckBox.this.removeStyleName(KSStyles.KS_CHECKBOX_FOCUS_STYLE);
                
            }   
        }); 

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSCheckBox.this.addStyleName(KSStyles.KS_CHECKBOX_FOCUS_STYLE);

            }       
        });
        
        //hover does not function fully for check boxes as is
/*        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSCheckBox.this.addStyleName(KSStyles.KS_CHECKBOX_HOVER_STYLE);
                
            }       
        });
        
        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSCheckBox.this.removeStyleName(KSStyles.KS_CHECKBOX_HOVER_STYLE);
                
            }
            
        });*/
        
        this.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                if(KSCheckBox.this.getValue()){
                    KSCheckBox.this.addStyleName(KSStyles.KS_CHECKBOX_CHECKED_STYLE);
                }
                else{
                    KSCheckBox.this.removeStyleName(KSStyles.KS_CHECKBOX_CHECKED_STYLE);
                }
                
            }
            
        });
    }

}
