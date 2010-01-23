package org.kuali.student.commons.ui.widgets;


import org.kuali.student.commons.ui.client.UICommonsConstants;
import org.kuali.student.commons.ui.logging.client.Logger;
import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ErrorDialog {
    public enum ExposedStyles {
        ERROR_DIALOG("KS-Error-Dialog"),
        ERROR_DIALOG_TITLE("KS-Error-Dialog-Title"),
        ERROR_DIALOG_LABEL("KS-Error-Dialog-Label"),
        ERROR_DIALOG_DESCRIPTION("KS-Error-Dialog-Description"),
        ERROR_DIALOG_TEXTAREA("KS-Error-Dialog-TextArea"),
        ERROR_DIALOG_BUTTON("KS-Error-Dialog-Button");
        
        private String styleName;
        private ExposedStyles(String styleName) {
            this.styleName = styleName;
        }
        public String toString() {
            return this.styleName;
        }
    }
    
    public enum MessagesRequired {
        DIALOG_TITLE("errorDialogTitle"),
        DESCRIBE_ACTION("describeAction"),
        ERROR_DESCRIPTION("errorDescription"),
        SEND_REPORT("sendReport"),
        CANCEL("cancel");
        
        private String messageId;
        private MessagesRequired(String messageId) {
            this.messageId = messageId;
        }
        public String toString() {
            return this.messageId;
        }
    }
    
    ViewMetaData metadata = ApplicationContext.getViews().get(UICommonsConstants.BASE_VIEW_NAME);
    Messages messages = metadata.getMessages();

    public static void bindUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                new ErrorDialog().show(e);
            }
        });
    }
        
    public void show(final Throwable error) {
        final ShadePanel shade = new ShadePanel();
        final VerticalPanel panel = new VerticalPanel();
        panel.addStyleName(ExposedStyles.ERROR_DIALOG.toString());
        
        final Label title = new Label(messages.get(MessagesRequired.DIALOG_TITLE.toString()));
        title.addStyleName(ExposedStyles.ERROR_DIALOG_TITLE.toString());
        
        final Label descriptionLabel = new Label(messages.get(MessagesRequired.ERROR_DESCRIPTION.toString()));
        descriptionLabel.addStyleName(ExposedStyles.ERROR_DIALOG_LABEL.toString());
        
        final TextArea description = new TextArea();
        description.setText(getErrorDescription(error));
        description.addStyleName(ExposedStyles.ERROR_DIALOG_TEXTAREA.toString());
        
        final Label describeActionLabel = new Label(messages.get(MessagesRequired.DESCRIBE_ACTION.toString()));
        describeActionLabel.addStyleName(ExposedStyles.ERROR_DIALOG_LABEL.toString());
        
        final TextArea actionDescription = new TextArea();
        actionDescription.addStyleName(ExposedStyles.ERROR_DIALOG_TEXTAREA.toString());
        
        final Button sendButton = new Button(messages.get(MessagesRequired.SEND_REPORT.toString()), new ClickListener() {
            public void onClick(Widget sender) {
            	DeferredCommand.addCommand(new Command() {
        			public void execute() {
                        sendReport(error, actionDescription.getText());
                        shade.hide();
        			}
            	});
            }
            
        });
        
        final Button cancelButton = new Button(messages.get(MessagesRequired.CANCEL.toString()), new ClickListener() {
            public void onClick(Widget sender) {
                shade.hide();
            }
            
        });
        
        final HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);
    
        panel.add(title);
        panel.add(descriptionLabel);
        panel.add(description);
        panel.add(describeActionLabel);
        panel.add(actionDescription);
        panel.add(buttonPanel);
        
        shade.setWidget(panel);
        shade.show();
       
    }
    
    private String getErrorDescription(Throwable error) {
        // TODO maybe retrieve more error info
        return error.toString();
    }
    private void sendReport(final Throwable error, final String actionDescription) {
        // TODO actually gather client context info, such as browser version, user id, etc
        Logger.getClientContextInfo().put("logType", "clientError");
        Logger.getClientContextInfo().put("actionDescription", actionDescription);
        Logger.error("Uncaught exception", error);
        Logger.sendLogs();
        
        // hack, clear out context info, should probably find a better way of doing this
        Logger.getClientContextInfo().clear();
    }
    
    
}
