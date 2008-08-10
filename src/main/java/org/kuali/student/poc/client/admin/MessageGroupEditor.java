package org.kuali.student.poc.client.admin;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessageGroupEditor extends Composite {
	public static abstract class Save extends MVCEvent {}
	public static abstract class Cancel extends MVCEvent {}
	
	public static final Save SAVE = GWT.create(Save.class);
	public static final Cancel CANCEL = GWT.create(Cancel.class);
	
	final ViewMetaData metadata = ApplicationContext.getViews().get(AdminPanel.VIEW_NAME);
	final Messages messages = metadata.getMessages(); 
	
	final MessageGroupEditor me = this;
	final VerticalPanel panel = new VerticalPanel();
	final HorizontalPanel buttonBar = new HorizontalPanel();
	
	final Button save = new Button(messages.get("save"));
	final Button cancel = new Button(messages.get("cancel"));
	
	final Label messageIdLabel = new Label();
	final Label messagesLabel = new Label();
	
	final FlexTable table = new FlexTable();
	List<MessageModelObject> data = null;
	List<TextBox> textBoxes = new ArrayList<TextBox>();
	
	
	boolean loaded = false;
	
	
	public MessageGroupEditor(List<MessageModelObject> messages) {
		super.initWidget(panel);
		this.data = messages;
	}
	
	public void onLoad() {
		if (!loaded) {
			loaded = true;
			panel.add(buttonBar);
			buttonBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			buttonBar.add(save);
			//Does nothing?
			//buttonBar.add(cancel);
			panel.add(table);
			
			save.setStyleName("KS-Button");
			save.addStyleName("KS-Button-Padding");
			cancel.setStyleName("KS-Button");
			cancel.addStyleName("KS-Button-Padding");
			
			save.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					Controller c = MVC.findParentController(me);
					if (c != null) {
						for (int i=0; i<data.size(); i++) {
							data.get(i).setMessage(textBoxes.get(i).getText());
						}
						c.getEventDispatcher().fireEvent(SAVE, data);
					}
				}
			});
			/* Not needed
			cancel.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					Controller c = MVC.findParentController(me);
					if (c != null) {
						c.getEventDispatcher().fireEvent(CANCEL);
					}
				}
			});
			*/
			
			int row = 0;
			messageIdLabel.setText(messages.get("messageId"));
			messagesLabel.setText(messages.get("messages"));
			messageIdLabel.setStyleName("KS-Label");
			messagesLabel.setStyleName("KS-Label");
			
			
			table.setWidget(0, 0, messageIdLabel);
			table.setWidget(0, 1, messagesLabel);
			
			for (MessageModelObject message : data) {
				row++;
				Label label = new Label(message.getMessageId());
				label.setStyleName("KS-Label");
				table.setWidget(row, 0, label);
				
				TextBox text = new TextBox();
				text.setStyleName("KS-TextBox");
				text.setWidth("100%");
				text.setText(message.getMessage());
				table.setWidget(row, 1, text);
				
				textBoxes.add(text);
			}
		}
	}
}
