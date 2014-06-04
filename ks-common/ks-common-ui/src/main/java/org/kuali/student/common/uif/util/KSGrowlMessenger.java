package org.kuali.student.common.uif.util;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.GrowlMessage;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is a Mapped implementation of the Messenger interface that sends messages to the user that will
 * be displayed as growl messages in the browser.
 *
 * @author Kuali Student Team
 */
public class KSGrowlMessenger implements Messenger {

    private Queue<GrowlMessage> messages;

    public static final String MESSENGER_KEY = "growl.messenger";

    public KSGrowlMessenger(){
        messages = new LinkedList<GrowlMessage>();
    }

    public void addWarningMessage(String key, String[] parameters) {
        addMessage(key, GrowlIcon.WARNING.name(), parameters);
    }

    public void addInfoMessage(String key, String[] parameters) {
        addMessage(key, GrowlIcon.INFORMATION.name(), parameters);
    }

    public void addErrorMessage(String key, String[] parameters) {
        addMessage(key, GrowlIcon.ERROR.name(), parameters);
    }

    public void addSuccessMessage(String key, String[] parameters) {
        addMessage(key, GrowlIcon.SUCCESS.name(), parameters);
    }

    private synchronized void addMessage(String key, String theme, String[] parameters) {
        GrowlMessage growlMessage = new GrowlMessage();
        growlMessage.setTitle("");
        growlMessage.setMessageKey(key);
        growlMessage.setMessageParameters(parameters);
        growlMessage.setTheme(theme);
        messages.add(growlMessage);
    }

    public synchronized void publishMessages() {
        while(!messages.isEmpty()){
            GlobalVariables.getMessageMap().addGrowlMessage(messages.poll());
        }

    }

}
