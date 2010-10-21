package org.kuali.student.lum.program.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import java.util.List;

/**
 * @author Igor
 */
public class ProgramUtils {

    public static DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    private ProgramUtils() {
    }

    public static void cutParentPartOfKey(List<ValidationResultInfo> result) {
        for (ValidationResultInfo validationResultInfo : result) {
            validationResultInfo.setElement(getLastPart(validationResultInfo.getElement()));
        }
    }

    private static String getLastPart(String element) {
        return element.substring(element.lastIndexOf("/") + 1);
    }
}
