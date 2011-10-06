package org.kuali.student.common.ui.client.util;

public class DebugIdUtils {
    
    
    /** 
     * Converts a String to a WebDriver safe debug ID by replacing all non-digit, non-letter (including Unicode characters) with a dash('-') character.
     * Please note that space characters (' ') will also be replaced by a dash character.
     * 
     * @param oldDebugId The existing debugId.
     * @return The converted string debug ID.
     */
    public static String createWebDriverSafeDebugId(String oldDebugId) {
        StringBuilder newDebugId = null;
        if (oldDebugId != null) {
            newDebugId = new StringBuilder();
            for (int i = 0; i < oldDebugId.length(); i++) {
                newDebugId.append(Character.isLetterOrDigit(oldDebugId.charAt(i)) ? oldDebugId.charAt(i) : "-");
            }
            return newDebugId.toString();
        }
        return null;
    }

}
