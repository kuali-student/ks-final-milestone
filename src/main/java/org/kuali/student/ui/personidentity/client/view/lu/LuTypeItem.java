/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.List;

import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;

/**
 * @author Garey
 *
 */
public class LuTypeItem extends TreeItem {
	GwtLuTypeInfo	tInfo;
	
	public static TreeListener GET_CLU = new TreeListener(){				
		public void onTreeItemSelected(TreeItem item) {
			
			if(item instanceof LuTypeItem){
				final LuTypeItem tItem = (LuTypeItem)item;
				
				LearningUnitController.findClusForLuType(tItem.getTypeInfo().getLuTypeKey(), new AsyncCallback(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());					
					}
	
					public void onSuccess(Object result) {
						List<GwtCluInfo> lRet = (List<GwtCluInfo>)result;
						if(lRet != null){
							tItem.removeItems();
							DOM.setStyleAttribute(tItem.getElement(),"paddingLeft","0px");
							for(GwtCluInfo gcd : lRet){
								
								tItem.addItem(new CluDisplayItem(gcd));
								
							}
						}
					}});
				}
			}
		public void onTreeItemStateChanged(TreeItem item) {
			if(item instanceof LuTypeItem && item.getState()){
				final LuTypeItem tItem = (LuTypeItem)item;
				
				LearningUnitController.findClusForLuType(tItem.getTypeInfo().getLuTypeKey(), new AsyncCallback(){
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());					
					}
	
					public void onSuccess(Object result) {
						List<GwtCluInfo> lRet = (List<GwtCluInfo>)result;
						if(lRet != null){
							tItem.removeItems();
							DOM.setStyleAttribute(tItem.getElement(),"paddingLeft","0px");
							for(GwtCluInfo gcd : lRet){
								
								tItem.addItem(new CluDisplayItem(gcd));
								
							}
						}
					}});
				}
		}
	};
	
	
	public LuTypeItem(GwtLuTypeInfo tInfo) {
		
		this.tInfo = tInfo;
		this.setText(tInfo.getDescription());
		this.addItem("");
								
	}			
	public GwtLuTypeInfo getTypeInfo(){
		return this.tInfo;
	}

}
