/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.widgets.counting.impl;



import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSRichTextToolbar;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.counting.KSRichEditorAbstract;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

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
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;

/** 
 * KS default rich text editor
 * 
 * TODO implement with a clean toolbar and i18n
 */
@Deprecated
public class KSRichEditorImpl extends KSRichEditorAbstract {
private final VerticalFlowPanel content = new VerticalFlowPanel();
    
    private final RichTextArea textArea = new RichTextArea();
    private final KSRichTextToolbar toolbar;
    private final FocusGroup group = new FocusGroup(this);
    private final PopupPanel popoutImagePanel = new PopupPanel();
    
    private final KSLightBox popoutWindow = new KSLightBox();
    private KSRichEditorImpl popoutEditor = null;
    
    private boolean focused = false;
    private boolean toolbarShowing = false;
    private boolean popoutActive = false;
    


    
    private boolean isUsedInPopup = false;
    private int toolbarHeight;
    
    //private KSImages images = (KSImages) GWT.create(KSImages.class);
    private final Image popoutImage = Theme.INSTANCE.getRichTextEditorImages().popout().getImage();
    
    private int height;
    private int width;
    private boolean dimensionsObtained = false;
    
    private KSLabel label = new KSLabel();
    private static final String REMAINING = " Characters remaining | "; //TODO get from resource
    private static final String MAXIMUM = " Character Maximum"; //TODO get from resource
    private int maxTextLength = 256;    
    private final KeyUpHandler keyUpHandler = new KeyUpHandler(){
        @Override
        public void onKeyUp(KeyUpEvent event) {
            int remaining = maxTextLength - getHTML().length();
            label.setText(remaining + REMAINING + maxTextLength + MAXIMUM); 
        }
    };  
    
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
    
    private final FocusHandler focusHandler = new FocusHandler() {
        @Override
        public void onFocus(FocusEvent event) {
            int remaining = maxTextLength - getHTML().length();
            label.setText(remaining + REMAINING + maxTextLength + MAXIMUM);
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
            int remaining = maxTextLength - getHTML().length();
            label.setText(remaining + REMAINING + maxTextLength + MAXIMUM);
            if(!popoutActive){
                focused = false;
                if(!toolbar.inUse()){
                    showToolbar(focused);
                }
            }
        }
    };
    
    
    public KSRichEditorImpl(){
        toolbar = new KSRichTextToolbar(textArea);
        content.add(toolbar);
        content.add(textArea);
        content.add(label);
        super.initWidget(content);
    }
    
    @Override
    protected void init(boolean isUsedInPopup, int maxTextLength) {
        this.isUsedInPopup = isUsedInPopup;     
        setupDefaultStyle();
        this.maxTextLength = maxTextLength;
        textArea.addKeyUpHandler(keyUpHandler);
        label.setText(maxTextLength + REMAINING + maxTextLength + MAXIMUM);
        if(isUsedInPopup){
            toolbar.setVisible(true);
            
            content.addStyleName(KSStyles.KS_RICH_TEXT_LARGE_CONTENT);
            textArea.addStyleName(KSStyles.KS_RICH_TEXT_LARGE);
            toolbar.addStyleName(KSStyles.KS_RICH_TEXT_LARGE_TOOLBAR);
            textArea.addFocusHandler(focusHandler);
        }
        else
        {
        	group.addWidget(toolbar);
        	group.addWidget(textArea);
            textArea.addFocusHandler(focusHandler);
            textArea.addBlurHandler(blurHandler);
            content.addStyleName(KSStyles.KS_RICH_TEXT_NORMAL_CONTENT);
            textArea.addStyleName(KSStyles.KS_RICH_TEXT_NORMAL);
            toolbar.addStyleName(KSStyles.KS_RICH_TEXT_NORMAL_TOOLBAR);
            toolbar.setVisible(false);
            
            popoutEditor = new KSRichEditorImpl();
            popoutEditor.init(true, maxTextLength);    
            
            ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

                @Override
                public void exec(ConfirmCancelEnum result) {
                    switch(result){
                        case CONFIRM:
                            textArea.setHTML(popoutEditor.getHTML());
                            popoutEditor.setHTML("");
                            textArea.setEnabled(true);
                            textArea.setFocus(true);
                            popoutWindow.hide();
                            popoutImagePanel.show();
                            popoutActive = false;
                            break;
                        case CANCEL:
                            popoutEditor.setHTML("");
                            textArea.setEnabled(true);
                            textArea.setFocus(true);
                            popoutWindow.hide();
                            popoutImagePanel.show();
                            popoutActive = false;
                            break;
                    }
                    
                }
            });     
            
            popoutImage.addClickHandler(new ClickHandler(){

                @Override
                public void onClick(ClickEvent event) {
                    popoutWindow.show();
                    focused = true;
                    textArea.setEnabled(false);
                    popoutEditor.setHTML(textArea.getHTML());
                    popoutImagePanel.hide();
                    popoutEditor.getRichTextArea().setFocus(true);

                }
            });
            
            popoutImage.addMouseDownHandler(new MouseDownHandler(){

                @Override
                public void onMouseDown(MouseDownEvent event) {
                    popoutActive = true;
                }
            });
            popoutImagePanel.add(popoutImage);
            buttonPanel.setContent(popoutEditor);
            popoutWindow.setWidget(buttonPanel);
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

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return group.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		// TODO Auto-generated method stub
		return group.addFocusHandler(handler);
	}

}
