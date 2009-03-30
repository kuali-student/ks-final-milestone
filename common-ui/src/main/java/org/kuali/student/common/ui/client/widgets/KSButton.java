package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.css.KSButtonCss;
import org.kuali.student.common.ui.client.css.KSCommonResources;

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

/**
 * KSButton wraps gwt Button.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of button events (for improved browser compatibility and customizability).
 * An image can be also be added to a KSButton (an improvement over gwt Button).
 * 
 * @author Kuali Student Team
 *
 */
public class KSButton extends Button{
    private Image image = null;
    
    private KSButtonCss css = KSCommonResources.INSTANCE.buttonCss();
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
     * This method sets the default style for the button and button events.
     * 
     */
    private void setupDefaultStyle(){
        addStyleName(css.KSButton());
        
        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSButton.this.removeStyleName(css.KSButtonFocus());
                
            }   
        }); 

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSButton.this.addStyleName(css.KSButtonFocus());

            }       
        });
        
        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSButton.this.addStyleName(css.KSButtonHover());
                
            }       
        });
        
        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSButton.this.removeStyleName(css.KSButtonHover());
                KSButton.this.removeStyleName(css.KSButtonClick());
            }
            
        });
        
        this.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                KSButton.this.removeStyleName(css.KSButtonHover());
            }
            
        });
        
        this.addMouseDownHandler(new MouseDownHandler(){

            public void onMouseDown(MouseDownEvent event) {
                KSButton.this.addStyleName(css.KSButtonClick());
                
            }
        });
        
        this.addMouseUpHandler(new MouseUpHandler(){

            public void onMouseUp(MouseUpEvent event) {
                KSButton.this.removeStyleName(css.KSButtonClick());
                
            }
        });
        
        this.addKeyDownHandler(new KeyDownHandler(){

            public void onKeyDown(KeyDownEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    KSButton.this.addStyleName(css.KSButtonClick());
                }
            }
            
        });
        
        this.addKeyUpHandler(new KeyUpHandler(){

            public void onKeyUp(KeyUpEvent event) {
                if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    KSButton.this.removeStyleName(css.KSButtonClick());
                }
                
            }
            
        });
        
    }

    /**
     * Sets the image for this button.  The image appears before other button content.
     * 
     * @param image the Image object to be used in this button
     */
    public void setImage(final Image image) {
        if (this.image == null) {
            this.getElement().insertBefore(image.getElement(), this.getElement().getFirstChild());
        } else {
            this.getElement().replaceChild(image.getElement(), this.getElement().getFirstChild());
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
