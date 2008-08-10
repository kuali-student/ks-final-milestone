package org.kuali.student.poc.client.admin;

import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.utilities.Callback;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.BusyIndicator;
import org.kuali.student.poc.client.BaseEvents.ShowView;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.Info;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;

public class AdminPanel extends Controller {
	public static final String VIEW_NAME = "org.kuali.student.admin";
	
	public static abstract class ShowAdminPanel extends ShowView {}
	public static final ShowAdminPanel SHOW_ADMIN_PANEL = GWT.create(ShowAdminPanel.class);
	
	final ViewMetaData metadata = ApplicationContext.getViews().get(VIEW_NAME);
	
	final HorizontalSplitPanel panel = new HorizontalSplitPanel();
	final AdminTreeMenu menu = new AdminTreeMenu();
	
	boolean loaded = false;
	
	public AdminPanel() {
		super.initWidget(panel);
	}
	
	protected void onLoad() {
		if (!loaded) {
			loaded = true;
			int height = Window.getClientHeight() - 150;
			int width = (int) (Window.getClientWidth() * .95);
			int splitPos = (int) (width * .33);
			panel.setSize(width + "px", height + "px");
			panel.setLeftWidget(menu);
			
			panel.setSplitPosition(splitPos + "px");
			menu.setWidth("100%");
			menu.setHeight("100%");
			
			DeferredCommand.addCommand(new Command() {
				public void execute() {
					populateAdminTreeMenu();
				}
			});
			
			menu.addMessageGroupSelectionListener(new Callback<List<String>>() {
				public void onResult(final List<String> result) {
					if(result != null)
						showMessageEditor(result.get(0), result.get(1));
					else
						panel.remove(panel.getRightWidget());
				}
			});
			
			getEventDispatcher().addListener(MessageGroupEditor.SAVE, new MVCEventListener() {
				@SuppressWarnings("unchecked")
				public void onEvent(MVCEvent event, Object data) {
					saveMessages((List<MessageModelObject>) data);
				}
			});
			
			getEventDispatcher().addListener(MessageGroupEditor.CANCEL, new MVCEventListener() {
				public void onEvent(MVCEvent event, Object data) {
					//panel.remove(panel.getRightWidget());
				}
			});
		}
	}
	
	protected void showMessageEditor(String locale, String groupName) {
		ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);
		MessagesAdminService.Util.getInstance().getMessages(locale, groupName, new AsyncCallback<List<MessageModelObject>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
			}
			public void onSuccess(List<MessageModelObject> result) {
				panel.remove(panel.getRightWidget());
				panel.setRightWidget(new MessageGroupEditor(result));
				ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
			}
			
		});
	}
	
	protected void populateAdminTreeMenu() {
		ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);
		MessagesAdminService.Util.getInstance().getMessageGroupTree(new AsyncCallback<Map<String,List<String>>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
				ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
			}
			public void onSuccess(Map<String, List<String>> result) {
				for (String locale : result.keySet()) {
					for (String groupName : result.get(locale)) {
						menu.addMessageGroup(locale, groupName);
					}	
				}
				ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
			}
		});
	}
	
	protected void saveMessages(List<MessageModelObject> messages) {
//		Window.alert("saving " + messages.size() + " messages");
//		String s = "";
//		for (MessageModelObject m : messages) {
//			s += m.getMessage() + "\n";
//		}
//		Window.alert(s);
		ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);
		MessagesAdminService.Util.getInstance().update(messages, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				Window.alert(metadata.getMessages().get("saveFailed") + ": " + caught.getMessage());
				ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
			}
			public void onSuccess(Boolean result) {
				if (!result) {
					Window.alert(metadata.getMessages().get("saveFailed"));
				} else {
					//Remove not needed
					//panel.remove(panel.getRightWidget());
					Info.displayInfo(metadata.getMessages().get("adminInfo"),
							metadata.getMessages().get("saveSuccess"));
							//ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("personCreated"));
				}
				ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
			}
			
		});
	}
}
