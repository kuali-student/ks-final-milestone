package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.user.client.ui.Composite;

/**
 * This class defines methods for implementations of a DisclosureSection.
 * Developers shouldn't reference this class, but should use KSImage
 * directly.
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public abstract class KSImageAbstract extends Composite{
	/**
	 * This method is a work around because GWT.Create can't call
     * constructors with arguments.
	 * 
	 * @param url
	 */
	protected abstract void init(String url); 
	
	/**
	 * This method is a work around because GWT.Create can't call
     * constructors with arguments.
	 * 
	 * @param url
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */
	protected abstract void init(String url, int left, int top, int width, int height);

}
