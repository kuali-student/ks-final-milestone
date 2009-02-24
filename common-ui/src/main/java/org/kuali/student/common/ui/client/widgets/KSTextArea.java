package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.TextArea;

public class KSTextArea extends TextArea{

    public KSTextArea() {
        super();
        setupDefaultStyle();
    }

    public KSTextArea(Element element) {
        super(element);
        setupDefaultStyle();
    }

    private void setupDefaultStyle() {
        addStyleName(KSStyles.KS_TEXTAREA_STYLE);
        
        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSTextArea.this.removeStyleName(KSStyles.KS_TEXTAREA_FOCUS_STYLE);
                
            }   
        }); 

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSTextArea.this.addStyleName(KSStyles.KS_TEXTAREA_FOCUS_STYLE);

            }       
        });
        
        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSTextArea.this.addStyleName(KSStyles.KS_TEXTAREA_HOVER_STYLE);
                
            }       
        });
        
        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSTextArea.this.removeStyleName(KSStyles.KS_TEXTAREA_HOVER_STYLE);
                
            }
            
        });
        
    }
    
    

}
