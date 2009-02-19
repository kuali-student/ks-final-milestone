package org.kuali.student.common.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MessageUtils {
    /**
     * Interpolates a message based on values contained in the data map, assuming message is formatted using a ${key} syntax.
     * 
     * @param message
     *            the message to be interpolated
     * @param data
     *            Map<String, String> containing the data to be used for interpolation
     * @return the interpolated message
     */
    public static String interpolate(String message, String... data) {
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
    public static String interpolate(String message, Map<String, Object> data) {
        if (message != null) {
            Set<String> fields = findFields(message);
            for (String s : fields) {
                message = message.replaceAll("\\$\\{" + s + "\\}", "" + escape(data.get(s).toString()));
            }
        }
        return message;
    }

    private static String escape(String input) {
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
    private static Set<String> findFields(String input) {
        Set<String> result = new HashSet<String>();
        int begin = input.indexOf("${");
        while (begin != -1) {
            int end = input.indexOf("}", begin);
            result.add(input.substring(begin + 2, end));
            begin = input.indexOf("${", end);
        }
        return result;
    }

}
