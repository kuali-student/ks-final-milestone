package org.kuali.student.ap.planner;


import java.beans.PropertyEditorSupport;
import java.util.Collections;
import java.util.List;

/**
 * PropertyEditor that will display a sorted list of registration group codes (1001, 1002, 1003).
 */
public class RegistrationGroupCodePropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        List<String> regGroupCodes = (List<String>) getValue();
        Collections.sort(regGroupCodes);
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (String regGroupCode : regGroupCodes) {
            if (counter == 0) {
                sb.append(String.format("%s", regGroupCode));
            } else {
                sb.append(String.format(", %s", regGroupCode));
            }
            counter++;
        }
        return sb.toString();
    }
}
