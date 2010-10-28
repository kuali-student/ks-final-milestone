package org.kuali.student.lum.program.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class ProgramUtils {

    public static DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    private ProgramUtils() {
    }

    public static List<ValidationResultInfo> cutParentPartOfKey(List<ValidationResultInfo> validationResultInfos, String key) {
        List<ValidationResultInfo> result = new ArrayList<ValidationResultInfo>();
        for (ValidationResultInfo validationResultInfo : validationResultInfos) {
            String elementKey = validationResultInfo.getElement();
            if (elementKey.startsWith(key)) {
                validationResultInfo.setElement(getLastPart(elementKey));
                result.add(validationResultInfo);
            }
        }
        return result;
    }

    private static String getLastPart(String element) {
        return element.substring(element.lastIndexOf("/") + 1);
    }

    public static Data getNewSpecialization() {
        Data newSpecializationData = new Data();
        newSpecializationData.set(ProgramConstants.STATE, ProgramStatus.DRAFT.getValue());
        newSpecializationData.set(ProgramConstants.TYPE, "kuali.lu.type.Variation");
        newSpecializationData.set(ProgramConstants.PROGRAM_REQUIREMENTS, new Data());
        ProgramRegistry.setData(newSpecializationData);
        return newSpecializationData;
    }
}
