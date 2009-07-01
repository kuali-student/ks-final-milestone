package edu.umd.dan.testricepersonlookup.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Label;

public class ExtFrame extends Frame {
	
	private Label selectedUserLabel;
	
	public ExtFrame(String string) {
		super();
		this.setResolveFunction(this);
		setUrl(string);
	}
	
	public native void setResolveFunction(ExtFrame x)/*-{
		$wnd.sendParams = function (location) {
			x.@edu.umd.dan.testricepersonlookup.client.ExtFrame::sendParams(Ljava/lang/String;)(location);
		};
	}-*/;

	public void sendParams(String location){
		Window.alert("Do some doc handling here!!!"+location);
		selectedUserLabel.setText(
				location.substring(location.indexOf("&principalId=")+"&principalId=".length(),location.indexOf("&", location.indexOf("&principalId=")+"&principalId=".length())));
		
	}

	public void setSelectedUserLabel(Label selectedUserLabel) {
		this.selectedUserLabel = selectedUserLabel;
	}
}
