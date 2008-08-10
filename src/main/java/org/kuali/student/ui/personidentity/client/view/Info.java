package org.kuali.student.ui.personidentity.client.view;


import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Info extends FlowPanel {
	Label titleLabel = new Label();
	Label contentLabel = new Label();

	public static void displayInfo(String title, String content) {
		Info info = new Info();
		info.setTitle(title);
		info.setContent(content);
		RootPanel.get().add(info);
		Timer hideTimer = new HideTimer(info);
		hideTimer.schedule(2000);
	}
	public Info() {
		setStyleName("info");
		titleLabel.setStyleName("info-titleLabel");
		contentLabel.setStyleName("info-content");
		add(titleLabel);
		add(contentLabel);
	}
	public void setTitle(String t) {
		titleLabel.setText(t);
	}

	public void setContent(String t) {
		contentLabel.setText(t);
	}
}
class HideTimer extends Timer {
	Widget element;
	public HideTimer(Widget element) {
		this.element = element;
	}
	public void run() {
		this.element.setVisible(false);
		RootPanel.get().remove(this.element);
	}
}