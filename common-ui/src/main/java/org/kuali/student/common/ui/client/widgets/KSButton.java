package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

public class KSButton extends Button{
    private Image image = null;
    

    public KSButton(){
        super();
        setupDefaultStyle();
        
    }
    
    public KSButton(String html){
        super(html);
        setupDefaultStyle();
    }
    
    public KSButton(String html,ClickHandler handler){
        super(html, handler);
        setupDefaultStyle();
    }
    
    private void setupDefaultStyle(){
        addStyleName(KSStyles.KS_BUTTON_STYLE);
        
        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSButton.this.removeStyleName(KSStyles.KS_BUTTON_FOCUS_STYLE);
                
            }   
        }); 

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSButton.this.addStyleName(KSStyles.KS_BUTTON_FOCUS_STYLE);

            }       
        });
        
        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSButton.this.addStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
                
            }       
        });
        
        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSButton.this.removeStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
                KSButton.this.removeStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
            }
            
        });
        
        this.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                KSButton.this.removeStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
            }
            
        });
        
        this.addMouseDownHandler(new MouseDownHandler(){

            public void onMouseDown(MouseDownEvent event) {
                KSButton.this.addStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
                
            }
        });
        
        this.addMouseUpHandler(new MouseUpHandler(){

            public void onMouseUp(MouseUpEvent event) {
                KSButton.this.removeStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
                
            }
        });
        
        this.addKeyDownHandler(new KeyDownHandler(){

            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    KSButton.this.addStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
                }
            }
            
        });
        
        this.addKeyUpHandler(new KeyUpHandler(){

            public void onKeyUp(KeyUpEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    KSButton.this.removeStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
                }
                
            }
            
        });
        
    }

    public void setImage(final Image image) {
        if (this.image == null) {
            this.getElement().insertBefore(image.getElement(), this.getElement().getFirstChild());
        } else {
            this.getElement().replaceChild(image.getElement(), this.getElement().getFirstChild());
        }
        
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
    
    
}
