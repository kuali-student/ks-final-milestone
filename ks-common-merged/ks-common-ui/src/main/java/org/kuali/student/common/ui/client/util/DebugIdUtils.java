package org.kuali.student.common.ui.client.util;

import com.google.gwt.user.client.ui.UIObject;

public class DebugIdUtils {
    
    
    /** 
     * Converts a String to a WebDriver safe debug ID by replacing all non-digit, non-letter (including Unicode characters) with a dash('-') character.
     * Please note that space characters (' ') will also be replaced by a dash character.
     * This also strips the 'gwt-debug' prefix if it exists.
     * 
     * @param debugId The existing debugId.
     * @return The converted string debug ID.
     */
    public static String createWebDriverSafeDebugId(String debugId) {
        StringBuilder newDebugId = null;
        if (debugId != null) {
            //Strip the gwt-debug prefix if it exists
            if (debugId.startsWith(UIObject.DEBUG_ID_PREFIX)) {
                debugId = debugId.substring(UIObject.DEBUG_ID_PREFIX.length());
                
            }
            newDebugId = new StringBuilder();
            for (int i = 0; i < debugId.length(); i++) {
                newDebugId.append(Character.isLetterOrDigit(debugId.charAt(i)) ? debugId.charAt(i) : "-");
            }
            return newDebugId.toString();
        }
        return null;
    }

}
