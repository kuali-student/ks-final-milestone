package org.kuali.student.poc.client.login;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.BusyIndicator;
import org.kuali.student.commons.ui.widgets.RoundedComposite;
import org.kuali.student.poc.client.POCMain;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoginComposite extends Composite {
    public static abstract class LoginRelatedEvent extends MVCEvent {}

    public static abstract class LoginSuccessfulEvent extends LoginRelatedEvent {}

    public static abstract class LoginFailedEvent extends LoginRelatedEvent {}

    public static abstract class LoginAbortedEvent extends LoginFailedEvent {}

    /**
     * Generic catch-all login related events
     */
    public static final LoginRelatedEvent LOGIN_RELATED_EVENT = GWT.create(LoginRelatedEvent.class);
    /**
     * Fired when successfully logged in. Event data is the login credentials object.
     */
    public static final LoginSuccessfulEvent LOGIN_SUCCESSFUL = GWT.create(LoginSuccessfulEvent.class);
    /**
     * Fired when login attempt is aborted.
     */
    public static final LoginAbortedEvent LOGIN_ABORTED = GWT.create(LoginAbortedEvent.class);
    /**
     * Fired when login fails due to max attempts, or any other reason, but the user did not cancel
     */
    public static final LoginFailedEvent LOGIN_FAILED = GWT.create(LoginFailedEvent.class);

    final ViewMetaData metadata = ApplicationContext.getViews().get(POCMain.VIEW_NAME);
    final Messages messages = metadata.getMessages();

    final RoundedComposite roundPanel = new RoundedComposite();
    
    final FocusPanel focus = new FocusPanel();
    
    final VerticalPanel panel = new VerticalPanel();
    final FlexTable table = new FlexTable();
    final Label instructions = new Label(messages.get("loginInstructions"));
    final Label userIdLabel = new Label(messages.get("userId"));
    final TextBox userId = new TextBox();
    final Label passwordLabel = new Label(messages.get("password"));
    final PasswordTextBox password = new PasswordTextBox();
    final Label failedMessage = new Label(messages.get("loginFailed"));

    final Label languageLabel = new Label(messages.get("language"));
    final ListBox language = new ListBox();
    final String[] languages = {"en", "zh"};

    Controller controller = null;

    boolean loaded = false;

    final Button login = new Button("Login", new ClickListener() {
        public void onClick(Widget sender) {
            doLogin();
        }
    });

    private void initStyles() {
        super.addStyleName("KS-LoginComposite");
        instructions.addStyleName("KS-Login-Instructions");
        userIdLabel.addStyleName("KS-Label");
        passwordLabel.addStyleName("KS-Label");
        languageLabel.addStyleName("KS-Label");
        
        userId.addStyleName("KS-TextBox");
        password.addStyleName("KS-TextBox");
        
        language.addStyleName("KS-ListBox");
        login.addStyleName("KS-Button");
        failedMessage.addStyleName("KS-Error");
        
    }
    public LoginComposite() {
        roundPanel.setWidget(focus);
        focus.setWidget(panel);
        panel.add(instructions);

        table.setWidget(0, 0, userIdLabel);
        table.setWidget(0, 1, userId);
        table.setWidget(1, 0, passwordLabel);
        table.setWidget(1, 1, password);

        int selected = 0;
        for (int i = 0; i < languages.length; i++) {
            language.addItem(messages.get(languages[i]));
            if (languages[i].equals(ApplicationContext.getLocale())) {
                selected = i;
            }
        }
        language.setSelectedIndex(selected);

        table.setWidget(2, 0, languageLabel);
        table.setWidget(2, 1, language);
        table.setWidget(3, 1, login);
        table.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_CENTER);

        panel.add(table);
        panel.add(failedMessage);
        failedMessage.setVisible(false);
        super.initWidget(roundPanel);
        
        //to remove the white space in Firefox
        language.setWidth("130px");
        language.setHeight("16px");
    }
    

    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            super.addStyleName("KS-LoginComposite");
            controller = MVC.findParentController(this);
            userId.setTabIndex(1);
            password.setTabIndex(2);
            language.setTabIndex(3);
            login.setTabIndex(4);
            userId.setFocus(true);
            focus.addKeyboardListener(new KeyboardListener() {
                public void onKeyDown(Widget sender, char keyCode, int modifiers) {}

                public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                    if (keyCode == KeyboardListener.KEY_ENTER) {
                        doLogin();
                    }
                }

                public void onKeyUp(Widget sender, char keyCode, int modifiers) {}

            });
            initStyles();
        }
    }

    public void reset() {
        userId.setText("");
        password.setText("");
        failedMessage.setVisible(false);
    }

    private void doLogin() {
        ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.BEGIN_TASK);

        final LoginCredentials credentials = new LoginCredentials();
        credentials.setUserId(userId.getText());
        credentials.setPassword(password.getText());
        credentials.setLocale(languages[language.getSelectedIndex()]);

        LoginService.Util.getInstance().login(credentials, new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                controller.getEventDispatcher().fireEvent(LOGIN_FAILED);
                ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
            }

         
            public void onSuccess(Boolean result) {
                if (result) {
                    controller.getEventDispatcher().fireEvent(LOGIN_SUCCESSFUL, credentials);
                } else {
                    failedMessage.setVisible(true);
                }
                ApplicationContext.getGlobalEventDispatcher().fireEvent(BusyIndicator.END_TASK);
            }

        });

    }

}
