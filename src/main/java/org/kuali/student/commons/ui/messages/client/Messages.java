package org.kuali.student.commons.ui.messages.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a logical group of internationalization messages.
 */
public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;
    private String groupName = null;
    private Map<String, String> messages = new HashMap<String, String>();
    private Set<Messages> inheritedMessages = new HashSet<Messages>();


    public Messages() {
        super();
    }
    /**
     * Creates a Messages object containing a Map of messages
     * 
     * @param groupName
     *            the logical name of the group of messages
     * @param messages
     *            Map<String, String> of messages keyed by message id
     */
    public Messages(String groupName, Map<String, String> messages) {
        super();
        this.groupName = groupName;
        this.messages = messages;
    }

    /**
     * Returns the group name
     * 
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the group name
     * 
     * @param groupName
     *            the group name to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 
     * Returns a Set<Messages> of Messages objects inherited at the ViewMetaData level,
     * defined using the "depends" property in the view configuration
     * 
     * @return Set<Messages> of Messages objects
     */
    public Set<Messages> getInheritedMessages() {
        return inheritedMessages;
    }


    /**
     * Returns the keySet of the underlying Map of messages
     * 
     * @return the keySet of the underlying Map of messages
     */
    public Set<String> keySet() {
        return messages.keySet();
    }

    /**
     * Directly retrieves a message from the messages Map. Performs no interpolation of values.
     * Searches inherited messages if they are loaded.
     * 
     * @param key
     *            the id of the message to retrieve
     * @return the raw message value, not interpolated
     */
    public String get(String key) {
        String result = messages.get(key);
        if (result == null) {
            for (Messages m : inheritedMessages) {
                if ((result = m.get(key)) != null) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Retrieves a message from the messages Map. Performs interpolation based on parameter index, assuming message is
     * formatted using a ${0}..${n} syntax
     * Searches inherited messages if they are loaded.
     * 
     * @param key
     *            the id of the message to retrieve
     * @param data
     *            varargs to be used for interpolation
     * @return the interpolated message
     */
    public String get(String key, String... data) {
        String result = get(key);
        return interpolate(result, data);
    }

    /**
     * Retrieves a message from the messages Map. Performs interpolation based on values contained in the data map, assuming
     * message is formatted using a ${key} syntax.
     * Searches inherited messages if they are loaded.
     * 
     * @param key
     *            the id of the message to retrieve
     * @param data
     *            Map<String, String> containing the data to be used for interpolation
     * @return the interpolated message
     */
    public String get(String key, Map<String, String> data) {
        String result = get(key);
        result = interpolate(result, data);
        return result;
    }

    /**
     * Retrieves a message from the messages Map. Performs interpolation based on both values contained in the data map, as
     * well as varargs based on index. Allows message format to use both ${key} and ${0}..${n} syntax.
     * Searches inherited messages if they are loaded.
     * 
     * @param key
     *            the id of the message to retrieve
     * @param data1
     *            Map<String, String> containing the data to be used for interpolation
     * @param data2
     *            varargs to be used for interpolation
     * @return the interpolated message
     */
    public String get(String key, Map<String, String> data1, String... data2) {
        String result = get(key);
        return interpolate(interpolate(result, data2), data1);
    }

    /**
     * Returns the underlying messages Map.
     * 
     * @return the underlying messages Map.
     */
    public Map<String, String> getMessageMap() {
        return messages;
    }

    /**
     * Interpolates a message based on values contained in the data map, assuming message is formatted using a ${key} syntax.
     * 
     * @param message
     *            the message to be interpolated
     * @param data
     *            Map<String, String> containing the data to be used for interpolation
     * @return the interpolated message
     */
    public String interpolate(String message, String... data) {
        if (message != null) {
            for (int i = 0; i < data.length; i++) {
                message = message.replaceAll("\\$\\{" + i + "\\}", "" + escape(data[i]));
            }
        }
        return message;
    }

    /**
     * Interpolates a message based on parameter index, assuming message is formatted using a ${0}..${n} syntax
     * 
     * @param message
     *            the message to be interpolated
     * @param data
     *            varargs to be used for interpolation
     * @return the interpolated message
     */
    public String interpolate(String message, Map<String, String> data) {
        if (message != null) {
            Set<String> fields = findFields(message);
            for (String s : fields) {
                message = message.replaceAll("\\$\\{" + s + "\\}", "" + escape(data.get(s)));
            }
        }
        return message;
    }

    private String escape(String input) {
        char[] toEscape = {'\\', '$', '.', '*', '+', '?', '|', '(', ')', '[', ']', '{', '}'};
        for (char c : toEscape) {
            input = input.replaceAll("\\" + c, "\\\\\\" + c);
        }
        return input;
    }
    /**
     * Returns a Set<String> of all interpolation targets (fields) within a String.
     * 
     * @param input
     *            the String from which to extract the interpolation targets
     * @return Set<String> containing the field names of the interpolation targets
     */
    protected Set<String> findFields(String input) {
        Set<String> result = new HashSet<String>();
        int begin = input.indexOf("${");
        while (begin != -1) {
            int end = input.indexOf("}", begin);
            result.add(input.substring(begin + 2, end));
            begin = input.indexOf("${", end);
        }
        return result;
    }

    public String toString() {
        String result = "Immediate messages: \n";
        for (String key : messages.keySet()) {
            result += key + " = " + messages.get(key) + "\n";
        }
        for (Messages m : inheritedMessages) {
            result += "Inherited from " + m.getGroupName() + ":\n" + m.toString();
        }
        return result;
    }
}
