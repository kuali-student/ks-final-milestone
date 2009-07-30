package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.logging.Logger;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.buttongroups.SendCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.SendCancelEnum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KSErrorDialog {

    final static ApplicationContext context = Application.getApplicationContext();

    public enum MessagesRequired {
        DIALOG_TITLE("errorDialogTitle"),
        DESCRIBE_ACTION("describeAction"),
        ERROR_DESCRIPTION("errorDescription");

        private String messageId;
        private MessagesRequired(String messageId) {
            this.messageId = messageId;
        }
        public String toString() {
            return this.messageId;
        }
    }  
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


    public static void bindUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                new KSErrorDialog().show(e);
            }
        });
    }

    public void show(final Throwable error) {
        final KSLightBox lightbox = new KSLightBox();
        
        final VerticalPanel panel = new VerticalPanel();
        panel.addStyleName(ExposedStyles.ERROR_DIALOG.toString());

        final KSLabel title = new KSLabel(context.getMessage(MessagesRequired.DIALOG_TITLE.toString()));
        title.addStyleName(ExposedStyles.ERROR_DIALOG_TITLE.toString());

        final KSLabel descriptionLabel = new KSLabel(context.getMessage(MessagesRequired.ERROR_DESCRIPTION.toString()));
        descriptionLabel.addStyleName(ExposedStyles.ERROR_DIALOG_LABEL.toString());

        final KSTextArea description = new KSTextArea();
        description.setText(getErrorDescription(error));
        description.addStyleName(ExposedStyles.ERROR_DIALOG_TEXTAREA.toString());

        final KSLabel describeActionLabel = new KSLabel(context.getMessage(MessagesRequired.DESCRIBE_ACTION.toString()));
        describeActionLabel.addStyleName(ExposedStyles.ERROR_DIALOG_LABEL.toString());

        final KSTextArea actionDescription = new KSTextArea();
        actionDescription.addStyleName(ExposedStyles.ERROR_DIALOG_TEXTAREA.toString());

        final SendCancelGroup buttonPanel = new SendCancelGroup(new Callback<SendCancelEnum>(){

            @Override
            public void exec(SendCancelEnum result) {
                switch(result){
                    case SEND:
                        DeferredCommand.addCommand(new Command() {
                            public void execute() {
                                sendReport(error, actionDescription.getText());
                                lightbox.hide();
                            }
                        });
                        break;
                    case CANCEL:
                        lightbox.hide();
                        break;
                }
            }
        });

        panel.add(title);
        panel.add(descriptionLabel);
        panel.add(description);
        panel.add(describeActionLabel);
        panel.add(actionDescription);
        panel.add(buttonPanel);

        lightbox.setWidget(panel);
        lightbox.show();

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
