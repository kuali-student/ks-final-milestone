package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public abstract class KSImage extends Composite{
	public abstract void init(String url); 
	
	public abstract void init(String url, int left, int top, int width, int height);

	public abstract Image getImage();
}
