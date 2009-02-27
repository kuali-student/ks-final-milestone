package org.kuali.student.common.ui.client.widgets;



import org.kuali.student.common.ui.client.images.TextIcons;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
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
public class KSRichEditor extends Composite {
	private final VerticalPanel windowPanel = new VerticalPanel();
	private final Grid content = new Grid(2, 1);
	
	private final RichTextArea textArea = new RichTextArea();
	private final KSRichTextToolbar toolbar;
	
	private final PopupPanel popoutImagePanel = new PopupPanel();
	private PopupPanel glass = new PopupPanel();
	
	private final KSInfoDialogPanel popoutWindow = new KSInfoDialogPanel();
	private KSRichEditor popoutEditor = null;
	
	private boolean focused = false;
	private boolean toolbarShowing = false;
	
	private boolean isUsedInPopup = false;
	private int toolbarHeight;
	
	private TextIcons images = (TextIcons) GWT.create(TextIcons.class);
	private final Image popoutImage = images.popout().createImage();
	
	private int height;
	private int width;
	private boolean dimensionsObtained = false;
	
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

		toolbar = new KSRichTextToolbar(textArea);
		content.setWidget(0, 0, toolbar);
		content.setWidget(1, 0, textArea);
		
		setupDefaultStyle();
		
		super.initWidget(content);
		
		if(isUsedInPopup){
	        toolbar.setVisible(true);
	        textArea.addStyleName(KSStyles.KS_RICH_TEXT_LARGE);
		}
		else
		{
			textArea.addFocusHandler(focusHandler);
			textArea.addBlurHandler(blurHandler);
			textArea.addStyleName(KSStyles.KS_RICH_TEXT_NORMAL);
			
			toolbar.setVisible(false);
			
			popoutEditor = new KSRichEditor(true);
			popoutWindow.setAutoHide(true);
			windowPanel.add(popoutEditor);
			
			
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
			windowPanel.add(buttonPanel);
			
			
			popoutImage.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					focused = true;
					textArea.setEnabled(false);
					popoutEditor.setHTML(textArea.getHTML());
					glass.show();	
					popoutWindow.show();
					//if(popoutWindow.)
					popoutImagePanel.hide();
					popoutEditor.textArea.setFocus(true);	
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
				}
			});
			//windowPanel.setSize("100%", "100%");
			popoutWindow.setWidget(windowPanel);
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
		if(!isUsedInPopup){
	        focusTimer.scheduleRepeating(250);
		    
		}
	}
	
	protected void onUnload() {
		super.onUnload();
        if(!isUsedInPopup){
        	focusTimer.cancel();
        }
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
}
