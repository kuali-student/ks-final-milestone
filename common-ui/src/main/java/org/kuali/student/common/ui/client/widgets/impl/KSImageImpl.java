package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSStyles;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

public class KSImageImpl extends KSImage{

	private Image image;
	
	public KSImageImpl() { 
		super();

	}
	@Override
	public void init(String url) {
		image = new Image(url);
        this.initWidget(image);
		DOM.setElementAttribute(image.getElement(), "src", url); 
	}
	@Override	
	public void init(String url, int left, int top, int width, int height) {
		image = new Image(url, left, top, width, height);
        this.initWidget(image);
		DOM.setElementAttribute(image.getElement(), "src", url); 
	}

	public void setUrl(String url) {

		image.setUrl(url);
		DOM.setElementAttribute(image.getElement(), "src", url); 
	}

	public void setUrlAndVisibleRect(String url, int left, int top, int width,
			int height) {

		image.setUrlAndVisibleRect(url, left, top, width, height);
		DOM.setElementAttribute(image.getElement(), "src", url);
	}
	
	private void setupDefaultStyle(){
		addStyleName(KSStyles.KS_IMAGE_STYLE);
		
		image.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				addStyleName(KSStyles.KS_IMAGE_HOVER_STYLE);
				
			}		
		});
		
		image.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				removeStyleName(KSStyles.KS_IMAGE_HOVER_STYLE);
			}
			
		});
		
		image.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				removeStyleName(KSStyles.KS_IMAGE_HOVER_STYLE);
			}
			
		});
		
		image.addMouseDownHandler(new MouseDownHandler(){

			public void onMouseDown(MouseDownEvent event) {
				addStyleName(KSStyles.KS_IMAGE_CLICK_STYLE);
				
			}
		});
		
		image.addMouseUpHandler(new MouseUpHandler(){

			public void onMouseUp(MouseUpEvent event) {
				removeStyleName(KSStyles.KS_IMAGE_CLICK_STYLE);
				
			}
		});
		
	}
	@Override
	public Image getImage() {
		return image;
	}

}
