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


import org.kuali.student.common.ui.client.widgets.impl.KSImageImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;


/**
 * KSImage is a wrapper for gwt Image.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of events (for improved browser compatibility and customizability).  In addition, removes
 * a bug with IE which does not allow multiple copies of the same Image to display properly.
 * 
 * @author Kuali Student Team
 *
 */
public class KSImage extends KSImageAbstract{
    
    KSImageAbstract image = GWT.create(KSImageImpl.class);
    
    /**
     * Sets the URL of the image to be displayed.
     * 
     * @param url the URL of the image to display.
     */
    public KSImage(String url) {
        image.init(url);
        this.initWidget(image);
    }
	
	/**
	 * Creates a clipped image with a specified URL and visibility rectangle. The visibility rectangle is declared relative to 
	 * the the rectangle which encompasses the entire image, which has an upper-left vertex of (0,0). The load event will be fired 
	 * immediately after the object has been constructed (i.e. potentially before the image has been loaded in the browser). Since the 
	 * width and height are specified explicitly by the user, this behavior will not cause problems with retrieving the width and height 
	 * of a clipped image in a load event handler.
	 * 
	 * @param url the URL of the image to display.
	 * @param left the horizontal co-ordinate of the upper-left vertex of the visibility rectangle
	 * @param top the vertical co-ordinate of the upper-left vertex of the visibility rectangle
	 * @param width the width of the visibility rectangle
	 * @param height the height of the visibility rectangle
	 */
	public KSImage(String url, int left, int top, int width, int height){
	    image.init( url,  left,  top,  width,  height);
	    this.initWidget(image);
	}
	
	public KSImage(ImageResource resource){
		image.init(resource);
		this.initWidget(image);
	}

	public void addClickHandler(ClickHandler handler){
		image.addClickHandler(handler);
	}
    /**
     * Initialized the KSImage with the passed in values
     * 
     * @param url the URL of the image to display.
     */
    @Override
    protected void init(String url) {
        image.init(url);
        
    }

    /**
     * Initialized the KSImage with the passed in values.
     * 
     * @param url the URL of the image to display.
     * @param left the horizontal co-ordinate of the upper-left vertex of the visibility rectangle
     * @param top the vertical co-ordinate of the upper-left vertex of the visibility rectangle
     * @param width the width of the visibility rectangle
     * @param height the height of the visibility rectangle
     */
    @Override
    protected void init(String url, int left, int top, int width, int height) {
        image.init(url, left, top, width, height);
        
    }

	@Override
	protected void init(ImageResource resource) {
		image.init(resource);
		
	}
	
	public Image getImage(){
		return image.getImage();
	}
}
