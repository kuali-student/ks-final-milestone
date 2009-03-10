package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.widgets.impl.KSImageImpl;

import com.google.gwt.core.client.GWT;


public class KSImage extends KSImageAbstract{
    
    KSImageAbstract image = GWT.create(KSImageImpl.class);
    
    public KSImage(String url) {
        image.init(url);
        this.initWidget(image);
    }
	
	public KSImage(String url, int left, int top, int width, int height){
	    image.init( url,  left,  top,  width,  height);
	    this.initWidget(image);
	}

    @Override
    protected void init(String url) {
        image.init(url);
        
    }

    @Override
    protected void init(String url, int left, int top, int width, int height) {
        image.init(url, left, top, width, height);
        
    }
}
