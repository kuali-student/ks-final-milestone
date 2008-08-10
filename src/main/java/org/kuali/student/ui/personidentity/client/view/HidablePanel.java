package org.kuali.student.ui.personidentity.client.view;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class HidablePanel extends DockPanel {
	Label title = new Label("Title");
	Widget center = new Label();
	int height = 0;
	int width = 0;
	PushButton headerImageButton = new PushButton();

	public HidablePanel() {
		setStylePrimaryName("hidablePanel");
		setHeight("100%");

		FlexTable titlePanel = new FlexTable();
		titlePanel.setStylePrimaryName("hidablePanel-Title");
		title.setStylePrimaryName("hidablePanel-TitleText");
		titlePanel.setWidget(0, 0, title);
		titlePanel.getCellFormatter().setWidth(0,0,"99%");
		titlePanel.setWidget(0, 1, headerImageButton);
		
		titlePanel.setWidth("100%");
		add(center, DockPanel.CENTER);
		add(titlePanel, DockPanel.NORTH);
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				height = getOffsetHeight();
				width = getOffsetWidth();
		        setWidth(width+"px");
		        setHeight(height + "px");				
			}
		});
		headerImageButton.addClickListener(new ClickListener() {
			public void onClick(Widget arg0) {
				doClick();
			}
		});

		headerImageButton.setStyleName("hidablePanelTitleButtonHide");
	}

	private void doClick() {
		if (center.isVisible()) {
			headerImageButton.setStyleName("hidablePanelTitleButtonUnhide");
			setWidth("30px");
			setHeight("23px");
		} else {
			headerImageButton.setStyleName("hidablePanelTitleButtonHide");
			setWidth(width+"px");
			setHeight(height + "px");
		}
		center.setVisible(!center.isVisible());
		title.setVisible(!title.isVisible());
		
		
	}
	public void setTitle(String text) {
		title.setText(text);
	}
	public void setCenterWidget(Widget c) {
		remove(center);
		center = c;
		add(center, DockPanel.CENTER);
	}
}