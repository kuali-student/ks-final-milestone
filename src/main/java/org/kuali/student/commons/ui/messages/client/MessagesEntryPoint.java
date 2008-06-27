package org.kuali.student.commons.ui.messages.client;

import com.google.gwt.core.client.EntryPoint;

public class MessagesEntryPoint implements EntryPoint {
	MessageCache provider = new MessageCache();
	
	public void onModuleLoad() {
//		final Map<String, String> data = new HashMap<String, String>();
//		final String firstName = "Wil";
//		final String lastName = "Johnson";
//		data.put("firstName", firstName);
//		data.put("lastName", lastName);
//		
//		VerticalPanel panel = new VerticalPanel();
//		RootPanel.get().add(panel);
//		
//		final TextArea text = new TextArea();
//		text.setHeight("320px");
//		text.setWidth("480px");
//		panel.add(text);
//		
//		Button cached = new Button("Show Cached", new ClickListener() {
//			public void onClick(Widget arg0) {
//				Messages m = provider.getMessages();
//				String s = m.get("hello", data);
//				s += "\n" + m.get("goodbye", firstName, lastName);
//				text.setText(s);
//			}
//		});
//		panel.add(cached);
//		
//		Button dynamic = new Button("Show Dynamic", new ClickListener() {
//			public void onClick(Widget arg0) {
//				provider.loadMessages("org.kuali.whatever", new AsyncCallback() {
//					public void onFailure(Throwable arg0) {
//						text.setText("Failed to load messages: " + arg0.getMessage());
//					}
//					public void onSuccess(Object arg0) {
//						Messages m = (Messages) arg0;
//						String s = m.get("hello", data);
//						s += "\n" + m.get("goodbye", firstName, lastName);
//						text.setText(s);
//					}
//				});
//			}
//		});
//		panel.add(dynamic);
//		
//		final Tree t = new Tree();
//		TreeItem cachedItems = t.addItem("Cached messages");
//		Messages m = provider.getMessages();
//		for (String s : m.keySet()) {
//			cachedItems.addItem(s + " = " + m.get(s));
//		}
//		provider.loadMessages("org.kuali.whatever", new AsyncCallback() {
//			public void onFailure(Throwable arg0) {
//				t.addItem("Failed to load messages from server");
//			}
//
//			public void onSuccess(Object arg0) {
//				TreeItem serverItems = t.addItem("Server messages");
//				Messages m = (Messages) arg0;
//				for (String s : m.keySet()) {
//					serverItems.addItem(s + " = " + m.get(s));
//				}
//			}
//		});
//		
//		panel.add(t);
		
	}
	

	


}
