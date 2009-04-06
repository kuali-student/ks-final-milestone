package org.kuali.student.common.ui.client.widgets.counting.impl;



import org.kuali.student.common.ui.client.images.KSImages;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSInfoDialogPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.counting.KSRichEditorAbstract;
import org.kuali.student.common.ui.client.widgets.KSRichTextToolbar;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/** 
 * KS default rich text editor
 * 
 * TODO implement with a clean toolbar and i18n
 */
public class KSRichEditorImpl extends KSRichEditorAbstract {
    private final FlowPanel content = new FlowPanel();
    
    private final RichTextArea textArea = new RichTextArea();
    private final KSRichTextToolbar toolbar;
    
    private final PopupPanel popoutImagePanel = new PopupPanel();
    private PopupPanel glass = new PopupPanel();
    
    private final KSInfoDialogPanel popoutWindow = new KSInfoDialogPanel();
    private KSRichEditorImpl popoutEditor = null;
    
    private boolean focused = false;
    private boolean toolbarShowing = false;
    private boolean popoutActive = false;
    
    private boolean isUsedInPopup = false;
    private int toolbarHeight;
    
    private KSImages images = (KSImages) GWT.create(KSImages.class);
    private final Image popoutImage = images.popout().createImage();
    
    private int height;
    private int width;
    private boolean dimensionsObtained = false;
    
    private final FocusHandler focusHandler = new FocusHandler() {
        @Override
        public void onFocus(FocusEvent event) {
            if(!popoutActive){
                focused = true;
                if(!toolbar.inUse()){
                    showToolbar(focused);
                }
            }
        }
    };
    
    private final BlurHandler blurHandler = new BlurHandler() {

        @Override
        public void onBlur(BlurEvent event) {           
            if(!popoutActive){
                focused = false;
                if(!toolbar.inUse()){
                    showToolbar(focused);
                }
            }
        }
    };
    private final VerticalPanel textPanel = new VerticalPanel();
    private KSLabel label = new KSLabel();
    private String label2ndPart = " Characters remaining | "; //TODO get from resource
    private String label4thPart = " Character Maximum"; //TODO get from resource
    private int maxTextLength = 256;    
    private final KeyUpHandler keyUpHandler = new KeyUpHandler(){
        @Override
        public void onKeyUp(KeyUpEvent event) {
            label.setText(getHTML().length() + label2ndPart + maxTextLength + label4thPart); 
        }
    };    
    public KSRichEditorImpl(){
        textArea.addKeyUpHandler(keyUpHandler);
        toolbar = new KSRichTextToolbar(textArea);
        content.add(toolbar);
        textPanel.add(textArea);
        textPanel.add(label);
        content.add(textPanel);
        super.initWidget(content);
    }
    
    @Override
    protected void init(boolean isUsedInPopup,int maxTextLength) {
        this.isUsedInPopup = isUsedInPopup;  
        this.maxTextLength = maxTextLength;
        label.setText("0" + label2ndPart + maxTextLength + label4thPart);
        setupDefaultStyle();
                
        if(isUsedInPopup){
            toolbar.setVisible(true);
            content.addStyleName(KSStyles.KS_RICH_TEXT_LARGE);
            textArea.setWidth("100%");
            textArea.setHeight("100%");
        }
        else
        {
            textArea.addFocusHandler(focusHandler);
            textArea.addBlurHandler(blurHandler);
            textArea.addStyleName(KSStyles.KS_RICH_TEXT_NORMAL);
            content.addStyleName(KSStyles.KS_RICH_TEXT_NORMAL);
            
            toolbar.setVisible(false);
            
            popoutEditor = new KSRichEditorImpl();
            popoutEditor.init(true,maxTextLength);
            popoutWindow.setAutoHide(true);
            popoutWindow.setHeader("TextEditor");
            //windowPanel.add(popoutEditor);
            
            
            HorizontalPanel buttonPanel = new HorizontalPanel();
            KSButton okButton = new KSButton("OK");
            okButton.addClickHandler(new ClickHandler(){

                @Override
                public void onClick(ClickEvent event) {
                    popoutWindow.hide();
                    glass.hide();
                }
            });
            buttonPanel.add(okButton);
            buttonPanel.setWidth("100%");
            //windowPanel.add(buttonPanel);
            
            
            popoutImage.addClickHandler(new ClickHandler(){

                @Override
                public void onClick(ClickEvent event) {
                    focused = true;
                    textArea.setEnabled(false);
                    popoutEditor.setHTML(textArea.getHTML());
                    glass.show();   
                    popoutWindow.show();
                    popoutImagePanel.hide();
                    popoutEditor.textArea.setFocus(true);
                }
            });
            
            popoutImage.addMouseDownHandler(new MouseDownHandler(){

                @Override
                public void onMouseDown(MouseDownEvent event) {
                    popoutActive = true;
                }
            });

            popoutImagePanel.add(popoutImage);
            
            
            popoutWindow.addCloseHandler(new CloseHandler(){

                @Override
                public void onClose(CloseEvent event) {
                    textArea.setHTML(popoutEditor.getHTML());
                    textArea.setEnabled(true);
                    textArea.setFocus(true);
                    popoutWindow.hide();
                    glass.hide();
                    popoutImagePanel.show();
                    popoutActive = false;
                }
            });

            popoutWindow.setWidget(popoutEditor);
        }
    }
    
    public RichTextArea getRichTextArea(){
        return textArea;
    }
    
    private void showToolbar(boolean show){
        
        if(!dimensionsObtained){
            height = textArea.getOffsetHeight();
            width = textArea.getOffsetWidth();
            dimensionsObtained = true;
        }
        
        
        if(show){
            if(!toolbarShowing){
                toolbar.setVisible(true);
                //firefox fix
                
                toolbarHeight = toolbar.getOffsetHeight();
                textArea.setHeight(height-toolbarHeight + "px");
                
                if(!isUsedInPopup){
                    popoutImagePanel.show();
                    int left = textArea.getAbsoluteLeft() + width - popoutImagePanel.getOffsetWidth() -18; 
                    int top = textArea.getAbsoluteTop() + textArea.getOffsetHeight() - popoutImagePanel.getOffsetHeight() -2;
                    popoutImagePanel.setPopupPosition(left, top);
                }
                
            }
            toolbarShowing = true;
        }
        else{
            if(toolbarShowing){
                
                toolbar.setVisible(false);
                textArea.setHeight(height + "px");
            }
            popoutImagePanel.hide();    
            toolbarShowing = false;
        }
    }
    
    private void setupDefaultStyle(){
        popoutImagePanel.addStyleName(KSStyles.KS_POPOUT_IMAGE_PANEL);
        glass.addStyleName(KSStyles.KS_RICH_TEXT_POPOUT_GLASS);
        popoutWindow.addStyleName(KSStyles.KS_POPOUT_WINDOW);
        popoutImage.addStyleName(KSStyles.KS_POPOUT_IMAGE);
        popoutImage.addMouseOverHandler(new MouseOverHandler(){

            @Override
            public void onMouseOver(MouseOverEvent event) {
                popoutImage.addStyleName(KSStyles.KS_POPOUT_IMAGE_HOVER);
            }
        });
        
        popoutImage.addMouseOutHandler(new MouseOutHandler(){

            @Override
            public void onMouseOut(MouseOutEvent event) {
                popoutImage.removeStyleName(KSStyles.KS_POPOUT_IMAGE_HOVER);
            }
        });
        
    }
    
    protected void onLoad() {
        super.onLoad();
    }
    
    protected void onUnload() {
        super.onUnload();
        popoutImagePanel.hide();
    }
    
    // delegate methods to RichTextArea
    public String getHTML() {
        return textArea.getHTML();
    }

    public String getText() {
        return textArea.getText();
    }

    public void setHTML(String html) {
        textArea.setHTML(html);
    }

    public void setText(String text) {
        textArea.setText(text);
    }
    
    public RichTextArea getTextArea(){
        return textArea;
    }

    /**
     * Maximum number of characters application allows for this field.
     * @return the maxTextLength
     */
    @Override
    public int getMaxTextLength() {
        return maxTextLength;
    }

    /**
     * Maximum character length application allows in this field.
     * Should be called in the wrapper class constructor
     * @param maxTextLength the maxTextLength to set
     */
    protected void setMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
    }
}
