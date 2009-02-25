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
import com.google.gwt.user.client.ui.TextBox;

public class KSTextBox extends TextBox{

    public KSTextBox() {
        super();
        setupDefaultStyle();
    }

    public KSTextBox(Element element) {
        super(element);
        setupDefaultStyle();
    }
    
    private void setupDefaultStyle() {
        addStyleName(KSStyles.KS_TEXTBOX_STYLE);
        
        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSTextBox.this.removeStyleName(KSStyles.KS_TEXTBOX_FOCUS_STYLE);
                
            }   
        }); 

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSTextBox.this.addStyleName(KSStyles.KS_TEXTBOX_FOCUS_STYLE);

            }       
        });
        
        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSTextBox.this.addStyleName(KSStyles.KS_TEXTBOX_HOVER_STYLE);
                
            }       
        });
        
        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSTextBox.this.removeStyleName(KSStyles.KS_TEXTBOX_HOVER_STYLE);
                
            }
            
        });
        
    }
}
