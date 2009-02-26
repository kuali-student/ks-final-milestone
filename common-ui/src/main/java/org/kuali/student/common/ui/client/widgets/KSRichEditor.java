package org.kuali.student.common.ui.client.widgets;



import org.kuali.student.common.ui.client.images.HelpIcons;
import org.kuali.student.common.ui.client.images.TextIcons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.RichTextArea.BasicFormatter;
import com.google.gwt.user.client.ui.RichTextArea.ExtendedFormatter;

/**
 * KS default rich text editor
 * 
 * TODO implement with a clean toolbar and i18n
 */
public class KSRichEditor extends Composite {
	private final Grid content = new Grid(2, 1);
	
	private final RichTextArea textArea = new RichTextArea();
	private final KSRichTextToolbar toolbar;
	private final PopupPanel popup = new PopupPanel();
	
	private KSInfoPopupPanel popoutWindow = null;;
	private KSRichEditor popoutEditor = null;
	
	private boolean focused = false;
	private boolean toolbarShowing = false;
	
	private boolean isUsedInPopup = false;
	private int toolbarHeight;
	
	private int defaultHeight;
	
	private HelpIcons images = (HelpIcons) GWT.create(HelpIcons.class);
	
	private final FocusHandler focusHandler = new FocusHandler() {
		@Override
		public void onFocus(FocusEvent event) {
			focused = true;
		}
	};
	
	private final BlurHandler blurHandler = new BlurHandler() {

		@Override
		public void onBlur(BlurEvent event) {
			focused = false;
		}
	};
	
	private final Timer focusTimer = new Timer() {
		public void run() {
			if(!toolbar.inUse()){
				showToolbar(focused);
			}
		}
	};
	
	public KSRichEditor(){
		this(false);
	}
	
	public KSRichEditor(boolean isUsedInPopup) {
	    this.isUsedInPopup = isUsedInPopup;
		
		//content.setHeight("400px");
		//content.setWidth("500px");
		toolbar = new KSRichTextToolbar(textArea);
		textArea.setWidth("494px");
		textArea.setHeight("200px");
		toolbar.setWidth("100%");
		content.setWidget(0, 0, toolbar);
		content.setWidget(1, 0, textArea);

		if (isUsedInPopup) {
            toolbar.setVisible(true);
		} else {
		    popoutWindow = new KSInfoPopupPanel();
		    popoutEditor = new KSRichEditor(true);
		    popoutWindow.add(popoutEditor);
	        Image popoutImage = images.errorIcon().createImage();
	        popoutImage.addClickHandler(new ClickHandler(){

	            @Override
	            public void onClick(ClickEvent event) {
	                focused = true;
	                popoutEditor.setHTML(textArea.getHTML());
	                popoutWindow.show();
	                
	            }
	        });
	        
	        popoutWindow.addCloseHandler(new CloseHandler(){

	            @Override
	            public void onClose(CloseEvent event) {
	                textArea.setHTML(popoutEditor.getHTML());
	                
	            }
	        });
	        
	        popup.add(popoutImage);

	        textArea.addFocusHandler(focusHandler);
            textArea.addBlurHandler(blurHandler);
            toolbar.setVisible(false);
		}
		
		super.initWidget(content);
	}
	
	public RichTextArea getRichTextArea(){
	    return textArea;
	}
	
	private void showToolbar(boolean show){
		if(show){
			if(!toolbarShowing){
				toolbar.setVisible(true);
				toolbarHeight = toolbar.getOffsetHeight();
				textArea.setHeight(textArea.getOffsetHeight()-toolbarHeight + "px");
				
				popup.show();
				int left = textArea.getAbsoluteLeft() + textArea.getOffsetWidth() - popup.getOffsetWidth() -18; 
				int top = textArea.getAbsoluteTop() + textArea.getOffsetHeight() - popup.getOffsetHeight() -2;
				popup.setPopupPosition(left, top);
				//textArea.set
				
			}
			toolbarShowing = true;
		}
		else{
			if(toolbarShowing){
				popup.hide();
				toolbar.setVisible(false);
				textArea.setHeight(textArea.getOffsetHeight()+toolbarHeight + "px");
			}
			
			toolbarShowing = false;
		}
	}
	
	protected void onLoad() {
		super.onLoad();
		System.out.println(textArea.getOffsetWidth());
		if(!isUsedInPopup){
	        focusTimer.scheduleRepeating(250);
		    
		}
	}
	
	protected void onUnload() {
		super.onUnload();
        if(!isUsedInPopup){
        	focusTimer.cancel();
        }
	}

/*	private void setupToolbar() {
		// Get formatters - will be null if not available
		final ExtendedFormatter ef = textArea.getExtendedFormatter();
		final BasicFormatter bf = textArea.getBasicFormatter();
        if (bf != null && !isUsedInPopup) {
            addToolbarWidget(new PushButton(new Image("images/popup2.gif"), new ClickListener() {
                public void onClick(Widget sender) {
                    //popup.setHTML(textArea.getHTML());
                    //popup.showPopup();
                }
            }), "Enlarge to edit");
        }

	}
	}*/
	
	
	
	
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
}
