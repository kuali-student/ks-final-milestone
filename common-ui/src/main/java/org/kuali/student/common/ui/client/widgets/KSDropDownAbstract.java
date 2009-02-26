package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
/**
 * This class defines methods for implementations of a DropDown.
 * Developers shouldn't reference this class, but should use KSDropDown
 * directly.
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public abstract class KSDropDownAbstract extends Composite{  
	/**
     * This method is a work around because GWT.Create can't call
     * DisclosurePanel constructors with arguments.
     * 
     * Init is not abstract to save adding an empty method to KSDropDown
	 * 
	 * @param isMultipleSelect
	 */
	public void init(boolean isMultipleSelect){}

    public abstract void addChangeHandler(ChangeHandler handler);

    public abstract void addItem(String item);

    public abstract void removeItem(int index);

    public abstract void clear();

    public abstract int getSelectedIndex();

}
