package org.kuali.student.lum.lu.ui.course.client.widgets;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Frame;

/**
 * Use this to communicate between frames and the parent window.
 * This class uses JSNI to create a global function sendParams which fires a selection event that the 
 * principal has been selected.  It is used in conjunction with an html page that calls the sendParams 
 * function
 *
 */
public class KRUserSearchIFrame extends Frame implements HasSelectionHandlers<String>{
	
	public KRUserSearchIFrame() {
		super();
		this.setResolveFunction(this);
	}
	
	public KRUserSearchIFrame(String string) {
		super();
		this.setResolveFunction(this);
		setUrl(string);
	}
	
	public native void setResolveFunction(KRUserSearchIFrame x)/*-{
		$wnd.sendParams = function (location) {
			x.@org.kuali.student.lum.lu.ui.course.client.widgets.KRUserSearchIFrame::sendParams(Ljava/lang/String;)(location);
		};
	}-*/;

	public void sendParams(String location){
		String userId =
				location.substring(location.indexOf("&principalId=")+"&principalId=".length(),location.indexOf("&", location.indexOf("&principalId=")+"&principalId=".length()));
        SelectionEvent.fire(this, userId);
	}

	@Override
	public HandlerRegistration addSelectionHandler(
			SelectionHandler<String> handler) {
        return addHandler(handler, SelectionEvent.getType());
	}
	
	//This code should be used when Rice allows for params in the doc handler URL so multiple frames like this can be used/registered
//	private static HashMap<Long,KRUserSearchIFrame> instanceMap = new HashMap<Long,KRUserSearchIFrame>();
//	private static long instanceCount = 0;
//	private Long instanceId;
//	
//	public KRUserSearchIFrame(String urlString) {
//		super();
//		setResolveFunction();
//		instanceCount++;
//		instanceId = instanceCount;
//		instanceMap.put(instanceId, this);
//		urlString+="?KRUserSearchIFrame="+instanceId;
//		setUrl(urlString);
//	}
//	
//	public static native void setResolveFunction()/*-{
//		$wnd.sendParams = function (location) {
//			@org.kuali.student.lum.lu.ui.course.client.widgets.KRUserSearchIFrame::sendParams(Ljava/lang/String;)(location);
//		};
//	}-*/;
//
//	public static void sendParams(String location){
//		String instanceId = location.substring(location.indexOf("KRUserSearchIFrame=")+"KRUserSearchIFrame=".length());
//		KRUserSearchIFrame instance;
//		if(instanceMap.size()==1){
//			instance = instanceMap.entrySet().iterator().next().getValue();
//		}else{
//			instance = instanceMap.get(Long.decode(instanceId));
//		}
//		
//		String userId =
//				location.substring(location.indexOf("&principalId=")+"&principalId=".length(),location.indexOf("&", location.indexOf("&principalId=")+"&principalId=".length()));
//        SelectionEvent.fire(instance, userId);
//	}
//
//	@Override
//	public HandlerRegistration addSelectionHandler(
//			SelectionHandler<String> handler) {
//        return addHandler(handler, SelectionEvent.getType());
//	}
}