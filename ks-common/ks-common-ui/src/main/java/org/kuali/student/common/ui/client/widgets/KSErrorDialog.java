/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.logging.Logger;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.buttongroups.OkGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.OkEnum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
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

    public static void bindUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                GWT.log(e.getMessage(), e);
                Window.alert("Uncaught exception was thrown:"+getStackTrace(e));
            	KSErrorDialog.show(e);
            }
        });
    }

    private static String getStackTrace(Throwable t){
    	StringBuilder sb =  new StringBuilder();
    	appendStackTrace(t,sb);
    	return sb.toString();
    }
    
    private static void appendStackTrace(Throwable t, StringBuilder s) {
        s.append(t.toString());
        s.append(": at\n");
        StackTraceElement[] stack = t.getStackTrace();
        for (StackTraceElement frame : stack) {
            s.append(frame.toString());
            s.append("\n");
        }
    }
    
    public static void show(final Throwable error) {
        final KSLightBox lightbox = new KSLightBox();

        final VerticalPanel panel = new VerticalPanel();
        panel.addStyleName("KS-Error-Dialog");

        final KSLabel title = new KSLabel(context.getMessage(MessagesRequired.DIALOG_TITLE.toString()));
        title.addStyleName("KS-Error-Dialog-Title");

        final KSLabel errorDescriptionLabel = new KSLabel(context.getMessage(MessagesRequired.ERROR_DESCRIPTION.toString()));
        errorDescriptionLabel.addStyleName("KS-Error-Dialog-Label");

        final SimplePanel errorDescriptionPanel = new SimplePanel();
        errorDescriptionPanel.addStyleName("KS-Error-Dialog-Panel");

        final KSTextArea errorDescription = new KSTextArea();
        errorDescription.setText(getErrorDescription(error));
        errorDescription.addStyleName("KS-Error-Dialog-TextArea");
        errorDescription.setReadOnly(true);
        //errorDescription.setEnabled(false);
        errorDescriptionPanel.add(errorDescription);

/*        final KSLabel describeActionLabel = new KSLabel(context.getMessage(MessagesRequired.DESCRIBE_ACTION.toString()));
        describeActionLabel.addStyleName(KSStyles.KS_ERROR_DIALOG_LABEL);

        final SimplePanel actionDescriptionPanel = new SimplePanel();
        actionDescriptionPanel.addStyleName(KSStyles.KS_ERROR_DIALOG_PANEL);

        final KSTextArea actionDescription = new KSTextArea();
        actionDescription.addStyleName(KSStyles.KS_ERROR_DIALOG_TEXTAREA);
        actionDescriptionPanel.add(actionDescription);
*/
        final OkGroup buttonPanel = new OkGroup(new Callback<OkEnum>(){

            @Override
            public void exec(OkEnum result) {
                switch(result){
                    case Ok:
                        DeferredCommand.addCommand(new Command() {
                            public void execute() {
                                sendReport(error/*, actionDescription.getText()*/);
                                lightbox.hide();
                            }
                        });
                        break;
                }
            }
        });

        panel.add(title);
        panel.add(errorDescriptionLabel);
        panel.add(errorDescriptionPanel);
//        panel.add(describeActionLabel);
//        panel.add(actionDescriptionPanel);
        panel.add(buttonPanel);

        panel.setSize("100", "100");

        lightbox.setWidget(panel);
        lightbox.setSize(440, 200);
        lightbox.show();

    }

    private static String getErrorDescription(Throwable error) {
        // TODO maybe retrieve more error info
        return error.getMessage();
    }

    private static void sendReport(final Throwable error/*, final String actionDescription*/) {
        // TODO actually gather client context info, such as browser version, user id, etc
        Logger.getClientContextInfo().put("logType", "clientError");
//        Logger.getClientContextInfo().put("actionDescription", actionDescription);
        Logger.error("Uncaught exception", error);
        Logger.sendLogs();

        // hack, clear out context info, should probably find a better way of doing this
        Logger.getClientContextInfo().clear();
    }

}
