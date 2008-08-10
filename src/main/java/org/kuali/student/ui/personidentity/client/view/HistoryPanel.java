package org.kuali.student.ui.personidentity.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class HistoryPanel extends DockPanel{
	PopupPanel popupPanel = new PopupPanel();
	boolean isVisible = false;
	public HistoryPanel(){
		setStylePrimaryName("historyPanel");
		FlexTable flexTable = new FlexTable();
		flexTable.setWidget(0, 0, new Label("1. Biographic Details"));
		flexTable.setWidget(1, 0, new Label("2. Create Person"));
		flexTable.setWidget(2, 0, new Label("3. People Search"));
		flexTable.setWidget(3, 0, new Label("4. Registration"));
		popupPanel.add(flexTable);
		popupPanel.setStylePrimaryName("historyPopupPanel");
		
		Button bn = new Button("Bn");
		
		bn.addClickListener(new ClickListener(){
			public void onClick(Widget arg0) {
				popupPanel.setPopupPosition( getAbsoluteLeft(), getAbsoluteTop()+getOffsetHeight());
				if(isVisible){
					popupPanel.hide();
					isVisible = false;
				}else{
				    popupPanel.show();
				    isVisible = true;
				}
			}
		});
		add(new Label("History"),DockPanel.WEST );
		add(bn,DockPanel.EAST);
	}

}
