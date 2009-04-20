package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite; 
import com.google.gwt.user.client.ui.Widget;

/**
 * This class defines methods for implementations of a DisclosureSection.
 * Developers shouldn't reference this class, but should use KSDisclosureSection
 * directly.
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public abstract class KSDisclosureSectionAbstract extends Composite{
	
	/**
	 * This method is a work around because GWT.Create can't call
	 * DisclosurePanel constructors with arguments.
	 * 
	 * Init is not abstract to save adding an empty method to KSDisclosureSection
	 * 
	 * @param headerText
	 * @param headerWidget
	 * @param isOpen
	 */
	protected void init(String headerText, Widget headerWidget, boolean isOpen){}

    public abstract void clear();

    public abstract void add(Widget w);

    public abstract boolean remove(Widget w);

    public abstract boolean isOpen();

    public abstract boolean isVisible();

    public abstract boolean isAnimationEnabled();

    public abstract void setOpen(boolean isOpen);

    public abstract void setVisible(boolean visible);

	

}
