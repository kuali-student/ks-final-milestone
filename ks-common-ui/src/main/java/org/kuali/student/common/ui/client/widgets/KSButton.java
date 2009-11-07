/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;

/**
 * KSButton wraps gwt Button.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of button events (for improved browser compatibility and customizability).
 * An image can be also be added to a KSButton (an improvement over gwt Button).
 * 
 * @author Kuali Student Team
 *
 */
public class KSButton extends Button{
    public static enum ButtonImageAlign{LEFT, RIGHT, TOP, BOTTOM};
    private Image image = null;
    private InlineHTML alignmentHTML;
    private boolean primary;
    


    /**
     * This constructs a button with no caption.
     * 
     */
    public KSButton(){
        super();
        setupDefaultStyle();
        
    }
    
    /**
     * Creates a button with the given HTML caption.
     * 
     * @param html the HTML caption
     */
    public KSButton(String html){
        super(html);
        setupDefaultStyle();
    }
    
    /**
     * Creates a button with the given HTML caption and click handler.
     * 
     * @param html the HTML caption
     * @param handler the click handler
     */
    public KSButton(String html,ClickHandler handler){
        super(html, handler);
        setupDefaultStyle();
    }
    
    /**
     * This method makes the button a "primary" button.  It does not affect it's behavior,
     * but instead alters it's style to one that is different/more eye-catching than default buttons.
     * 
     * @param primary true if this button is primary, false otherwise.
     */
    public void setPrimary(boolean primary){
        this.primary = primary;
        if(primary){
            addStyleName(KSStyles.KS_BUTTON_SPECIAL_STYLE);
            //addStyleName("gradient");
        }
        else{
            removeStyleName(KSStyles.KS_BUTTON_SPECIAL_STYLE);
        }
    }
    
    public boolean isPrimary() {
        return primary;
    }
    
    /**
     * This method sets the default style for the button and button events.
     * 
     */
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

    /**
     * Sets the image for this button.  The image appears before other button content.
     * 
     * @param image the Image object to be used in this button
     */
    public void setImage(final Image image, ButtonImageAlign alignment) {
        if(this.alignmentHTML != null){
            this.getElement().removeChild(this.alignmentHTML.getElement());
        }
       
        switch(alignment){
            case LEFT:
                this.alignmentHTML = new InlineHTML("&nbsp;");
                this.getElement().insertBefore(alignmentHTML.getElement(), this.getElement().getFirstChild());
                this.getElement().insertBefore(image.getElement(), this.getElement().getFirstChild());
                break;
            case RIGHT:
                this.alignmentHTML = new InlineHTML("&nbsp;");
                this.getElement().insertBefore(alignmentHTML.getElement(), null);
                this.getElement().insertBefore(image.getElement(), null);
                break;
            case TOP:
                this.alignmentHTML = new InlineHTML("<br />");
                this.getElement().insertBefore(alignmentHTML.getElement(), this.getElement().getFirstChild());
                this.getElement().insertBefore(image.getElement(), this.getElement().getFirstChild());
                break;
            case BOTTOM:
                this.alignmentHTML = new InlineHTML("<br />");
                this.getElement().insertBefore(alignmentHTML.getElement(), null);
                this.getElement().insertBefore(image.getElement(), null);
                break;
            default:
                this.alignmentHTML = new InlineHTML("&nbsp;");
                this.getElement().insertBefore(alignmentHTML.getElement(), this.getElement().getFirstChild());
                this.getElement().insertBefore(image.getElement(), this.getElement().getFirstChild());
                break;
        }
        
        if (this.image != null) {
            this.getElement().removeChild(this.image.getElement());
        }
        
        this.image = image;
    }

    /**
     * Returns the image being used in this button.
     * 
     * @return Image used in this button, null otherwise.
     */
    public Image getImage() {
        return image;
    }
    
    
}
