/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.List;

import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;

/**
 * @author Garey
 *
 */
public class CluDisplayItem extends TreeItem {
	GwtCluInfo cDisp = null;
	
	public static TreeListener GET_LUI = new TreeListener(){				
		public void onTreeItemSelected(TreeItem item) {
			
			if(item instanceof CluDisplayItem){
				final CluDisplayItem tItem = (CluDisplayItem)item;
				
				LearningUnitController.findLuisForClu(tItem.getCluDisplay().getCluId(),null, new AsyncCallback(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());					
					}
	
					public void onSuccess(Object result) {
						List<GwtLuiDisplay> lRet = (List<GwtLuiDisplay>)result;
						if(lRet != null){
							tItem.removeItems();
							DOM.setStyleAttribute(tItem.getElement(),"paddingLeft","0px");
							for(GwtLuiDisplay gcd : lRet){								
								tItem.addItem(new LuiTreeItem(gcd));								
							}
						}
					}});
				}
			}
		public void onTreeItemStateChanged(TreeItem item) {
			if(item instanceof CluDisplayItem && item.getState()){
				final CluDisplayItem tItem = (CluDisplayItem)item;
				
				LearningUnitController.findLuisForClu(tItem.getCluDisplay().getCluId(),null, new AsyncCallback(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());					
					}
	
					public void onSuccess(Object result) {
						List<GwtLuiDisplay> lRet = (List<GwtLuiDisplay>)result;
						if(lRet != null){
							tItem.removeItems();
							DOM.setStyleAttribute(tItem.getElement(),"paddingLeft","0px");
							for(GwtLuiDisplay gcd : lRet){								
								tItem.addItem(new LuiTreeItem(gcd));								
							}
						}
					}});
				}
		}
	};
	
	
	public CluDisplayItem(GwtCluInfo cDisp){
		this.cDisp = cDisp;
		this.setText(cDisp.getCluShortName());
		this.addItem("");
	}
	
	public GwtCluInfo getCluDisplay(){
		return this.cDisp;
	}

}
