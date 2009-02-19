package org.kuali.student.common.ui.client.widgets;

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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;

public class KSImage extends Image{

	public KSImage() {
		super();

	}

	public KSImage(String url) {
		super(url);
		DOM.setElementAttribute(this.getElement(), "src", url); 
	}
	
	public KSImage(Element element) {
		super(element);

	}

	public KSImage(String url, int left, int top, int width, int height) {
		super(url, left, top, width, height);
		DOM.setElementAttribute(this.getElement(), "src", url); 
	}

	@Override
	public void setUrl(String url) {

		super.setUrl(url);
		DOM.setElementAttribute(this.getElement(), "src", url); 
	}

	@Override
	public void setUrlAndVisibleRect(String url, int left, int top, int width,
			int height) {

		super.setUrlAndVisibleRect(url, left, top, width, height);
		DOM.setElementAttribute(this.getElement(), "src", url);
	}
	
	private void setupDefaultStyle(){
		addStyleName(KSStyles.KS_IMAGE_STYLE);
		
		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				KSImage.this.addStyleName(KSStyles.KS_IMAGE_HOVER_STYLE);
				
			}		
		});
		
		this.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				KSImage.this.removeStyleName(KSStyles.KS_IMAGE_HOVER_STYLE);
			}
			
		});
		
		this.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				KSImage.this.removeStyleName(KSStyles.KS_IMAGE_HOVER_STYLE);
			}
			
		});
		
		this.addMouseDownHandler(new MouseDownHandler(){

			public void onMouseDown(MouseDownEvent event) {
				KSImage.this.addStyleName(KSStyles.KS_IMAGE_CLICK_STYLE);
				
			}
		});
		
		this.addMouseUpHandler(new MouseUpHandler(){

			public void onMouseUp(MouseUpEvent event) {
				KSImage.this.removeStyleName(KSStyles.KS_IMAGE_CLICK_STYLE);
				
			}
		});
		
	}

}
