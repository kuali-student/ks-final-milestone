/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 */
public class KsTree extends Tree  {
	
	

	/**
	 * 
	 */
	public KsTree() {
		super();
	}				
	
	
	public void add(Widget w){
		if(w instanceof Tree){
			Tree t = (Tree)w;
			if(t != null){
				TreeItem tItem = new TreeItem();
				
			}
			
			
		}
		else{
			super.add(w);
		}
			
	}
	
	

	
	

}
