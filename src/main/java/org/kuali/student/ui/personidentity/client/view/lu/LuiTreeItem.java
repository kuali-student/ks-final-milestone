/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;

import com.google.gwt.user.client.ui.TreeItem;

/**
 * @author Garey
 *
 */
public class LuiTreeItem extends TreeItem {
	GwtLuiDisplay disp = null;
	
	public LuiTreeItem(GwtLuiDisplay disp){
		this.disp = disp;
		this.setText(disp.getCluDisplay().getCluShortName());
		
	}
	
	public GwtLuiDisplay getLuiDisplay(){
		return this.disp;
	}

}
