package edu.umd.ks.poc.lum.web.core.client;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.kuali.student.commons.ui.widgets.ErrorDialog;
import org.kuali.student.commons.ui.widgets.ShadePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimpleErrorDialog {

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

    private static SimpleErrorDialog _instance = null;

    protected SimpleErrorDialog(){

    }

    public static SimpleErrorDialog getInstance(){
        return (_instance != null)?_instance:new SimpleErrorDialog();
    }

    public static void bindUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                getInstance().show(e);
            }
        });
    }

    public void show(final Throwable error) {
        final ShadePanel shade = new ShadePanel();
        final VerticalPanel panel = new VerticalPanel();
        panel.addStyleName(ExposedStyles.ERROR_DIALOG.toString());


        final TextArea description = new TextArea();
        description.setText(getErrorDescription(error));
        description.addStyleName(ExposedStyles.ERROR_DIALOG_TEXTAREA.toString());



        final Button sendButton = new Button("Ok", new ClickListener() {
            public void onClick(Widget sender) {
                DeferredCommand.addCommand(new Command() {
                    public void execute() {

                        shade.hide();
                    }
                });
            }

        });


        final HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.add(sendButton);

        panel.add(description);
        panel.add(buttonPanel);

        shade.setWidget(panel);
        shade.show();

    }

    private String getErrorDescription(Throwable error) {
        // TODO maybe retrieve more error info

        return stack2string(error);
    }

    public static String stack2string(Throwable e) {
        String s = new String(e.toString());
        try {

            StackTraceElement[] trace = e.getStackTrace();

            for (int i=0; i < trace.length; i++)
                s += ("\tat " + trace[i]);

            Throwable ourCause = e.getCause();
            trace = ourCause.getStackTrace();

            for (int i=0; i < trace.length; i++)
                s += ("\tat " + trace[i]);
        }
        catch(Exception e2) {
          return "bad stack2string";
        }
        return s;
       }

}
