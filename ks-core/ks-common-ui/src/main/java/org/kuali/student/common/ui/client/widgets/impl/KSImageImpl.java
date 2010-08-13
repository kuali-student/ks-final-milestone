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

package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSImageAbstract;

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
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Image;

public class KSImageImpl extends KSImageAbstract{

	private Image image;

	@Override
    protected void init(String url, int left, int top, int width, int height) {
		image = new Image(url, left, top, width, height);
        this.initWidget(image);
		DOM.setElementAttribute(image.getElement(), "src", url);
		setupDefaultStyle();
	}

	@Override
	protected void init(String url) {
		image = new Image(url);
		this.initWidget(image);
		DOM.setElementAttribute(image.getElement(), "src", url);
		setupDefaultStyle();
	}

	@Override
	protected void init(ImageResource resource){
		image = new Image(resource.getURL());
		image.setUrlAndVisibleRect(resource.getURL(), resource.getLeft(), resource.getTop(), resource.getWidth(), resource.getHeight());
		this.initWidget(image);
		DOM.setElementAttribute(image.getElement(), "src", resource.getURL());
		setupDefaultStyle();
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
	    image.addStyleName("KS-Image");

		image.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
			    image.addStyleName("KS-Image-Hover");

			}
		});

		image.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
			    image.removeStyleName("KS-Image-Hover");
			}

		});

		image.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
			    image.removeStyleName("KS-Image-Hover");
			}

		});

		image.addMouseDownHandler(new MouseDownHandler(){

			public void onMouseDown(MouseDownEvent event) {
			    image.addStyleName("KS-Image-Click");

			}
		});

		image.addMouseUpHandler(new MouseUpHandler(){

			public void onMouseUp(MouseUpEvent event) {
			    image.removeStyleName("KS-Image-Click");

			}
		});

	}
	@Override
	public void addClickHandler(ClickHandler handler) {
		image.addClickHandler(handler);

	}

	public Image getImage(){
		return image;
	}




}
