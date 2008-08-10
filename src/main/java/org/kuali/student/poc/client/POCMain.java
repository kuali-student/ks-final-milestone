package org.kuali.student.poc.client;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventDispatcher;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.BusyIndicator;
import org.kuali.student.commons.ui.widgets.RoundedComposite;
import org.kuali.student.poc.client.admin.AdminPanel;
import org.kuali.student.poc.client.login.LoginComposite;
import org.kuali.student.poc.client.login.LoginCredentials;
import org.kuali.student.poc.client.login.LoginService;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.controller.PersonIdentityController;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.view.AdminStudentTab;
import org.kuali.student.ui.personidentity.client.view.AdminStudentTabWrapper;
import org.kuali.student.ui.personidentity.client.view.BaseHeaderPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class POCMain extends Controller {
	public static final String VIEW_NAME = "org.kuali.student.base";
	
	public static abstract class RequestLogout extends MVCEvent {}
	public static final RequestLogout REQUEST_LOGOUT = GWT.create(RequestLogout.class);
	
	
	final POCMain me = this;
	final ViewMetaData viewMetaData = ApplicationContext.getViews().get(VIEW_NAME);
	final Messages messages = viewMetaData.getMessages();
	
	final BusyIndicator busyIndicator = new BusyIndicator(); 
	final AdminStudentTabWrapper studentTab = new AdminStudentTabWrapper();
	final AdminPanel adminPanel = new AdminPanel();
	
	final VerticalPanel rootPanel = new VerticalPanel();
	final BaseHeaderPanel header = new BaseHeaderPanel();
	final SimplePanel content = new SimplePanel();
	final RoundedComposite contentWrapper = new RoundedComposite();
	
	boolean loaded = false;
	
	
	
	public POCMain() {
		super.initWidget(rootPanel);
	}

	protected void onLoad() {
		super.onLoad();
		if (!loaded) {
			loaded = true;
			rootPanel.setWidth("100%");
			content.setWidth("100%");
			content.setHeight("80%");
			contentWrapper.setHeight("90%");
			contentWrapper.setWidth("90%");
			contentWrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			contentWrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
            
			content.addStyleName("KS-Center-Content-Panel");
			
			initializeNavigationListeners();
			rootPanel.add(header);
			rootPanel.add(content);
			
			LearningUnitController.setAdminStudentTab(studentTab.getTabs());
			PersonIdentityController.setAdminStudentTab(studentTab.getTabs());
			
			super.initializeModel(GwtPersonInfo.class, new Model<GwtPersonInfo>());
			super.initializeModel(GwtLuiInfo.class, new Model<GwtLuiInfo>());
			PersonIdentityController.setController(this);
			LearningUnitController.setController(this);
			// check if we're already logged in
			ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);
			LoginService.Util.getInstance().getCurrentLogin(new AsyncCallback<LoginCredentials>() {
                public void onFailure(Throwable caught) {
                    // should never happen unless network connection is lost
                    ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
                    Window.alert("Unable to check for current login state: " + caught.getMessage());
                }
                public void onSuccess(LoginCredentials result) {
                    ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
                    if (result != null) {
                        // TODO add credentials, etc, to the security context, etc
                        me.getEventDispatcher().fireEvent(LoginComposite.LOGIN_SUCCESSFUL);
                        me.getEventDispatcher().fireEvent(AdminStudentTab.SHOW_ADMIN_STUDENT_TAB);
                    } else {
                        showLogin();
                    }
                }
			});
		}
	}
	
	protected void showLogin() {
		LoginComposite login = new LoginComposite();
		
		final MVCEventListener successListener = new MVCEventListener() {
			public void onEvent(MVCEvent event, Object data) {
			    LoginCredentials credentials = (LoginCredentials) data;
			    if (!ApplicationContext.getLocale().equals(credentials.getLocale())) {
			        Window.Location.reload();
			    } else {
    				// TODO add credentials, etc, to the security context, etc
    				me.getEventDispatcher().fireEvent(AdminStudentTab.SHOW_ADMIN_STUDENT_TAB);
    				final MVCEventListener m = this;
    				DeferredCommand.addCommand(new Command() {
    					public void execute() {
    						me.getEventDispatcher().removeListener(LoginComposite.LOGIN_SUCCESSFUL, m);
    					}
    				});
			    }
			}
		};
		getEventDispatcher().addListener(LoginComposite.LOGIN_SUCCESSFUL, successListener);

		final MVCEventListener failListener = new MVCEventListener() {
			public void onEvent(MVCEvent event, Object data) {
				showWidget(new Label(messages.get("mustBeLoggedIn")));
				final MVCEventListener m = this;
				DeferredCommand.addCommand(new Command() {
					public void execute() {
						me.getEventDispatcher().removeListener(LoginComposite.LOGIN_FAILED, m);
					}
				});
				
			}
		};
		getEventDispatcher().addListener(LoginComposite.LOGIN_FAILED, failListener);

		content.setWidget(login);

	}
	
	private void showWidget(Widget w) {
	    contentWrapper.setWidget(w);
	    content.setWidget(contentWrapper);        
	    w.addStyleName("KS-Center-Content-Panel-Contents");
	}
	
	private void initializeNavigationListeners() {
		EventDispatcher e = getEventDispatcher();
		e.addListener(AdminPanel.SHOW_ADMIN_PANEL, new MVCEventListener() {
			public void onEvent(MVCEvent event, Object data) {
				DeferredCommand.addCommand(new Command() {
					public void execute() {
					    showWidget(adminPanel);
					}
				});
			}
		});
		
		e.addListener(AdminStudentTab.SHOW_ADMIN_STUDENT_TAB, new MVCEventListener() {
			public void onEvent(MVCEvent event, Object data) {
				DeferredCommand.addCommand(new Command() {
					public void execute() {
					    showWidget(studentTab);
					}
				});
			}
		});
		
		e.addListener(POCMain.REQUEST_LOGOUT, new MVCEventListener() {
            public void onEvent(MVCEvent event, Object data) {
                ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);
                LoginService.Util.getInstance().logout(new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
                        // should never happen unless network connection is lost
                        Window.alert("Failed to log out: " + caught.getMessage());
                    }

                    public void onSuccess(Boolean result) {
                        ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
                        Window.Location.reload();
                    }
                    
                });
            }
		});
		
	}
	
	
}
