package org.kuali.student.lum.program.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import org.kuali.student.common.ui.client.mvc.DataModel;
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

    public static void addCredentialProgramDataToVariation(Data variationData, DataModel model) {
        String institutionKey = ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.INSTITUTION + "/" + ProgramConstants.ID;
        Data credentialProgram = new Data();
        Data institution = new Data();
        credentialProgram.set(ProgramConstants.INSTITUTION, institution);
        institution.set(ProgramConstants.ID, model.<String>get(institutionKey));
        credentialProgram.set(ProgramConstants.PROGRAM_LEVEL, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.PROGRAM_LEVEL));

        Data runtimeData = new Data();
        Data programType = new Data();
        programType.set(ProgramConstants.ID_TRANSLATION, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM + "/_runtimeData/" + ProgramConstants.CREDENTIAL_PROGRAM_TYPE + "/id-translation"));
        runtimeData.set(ProgramConstants.CREDENTIAL_PROGRAM_TYPE, programType);
        credentialProgram.set(ProgramConstants.RUNTIME_DATA, runtimeData);

        variationData.set(ProgramConstants.CREDENTIAL_PROGRAM, credentialProgram);
    }

    public static Data createNewSpecializationBasedOnMajor(DataModel programModel) {
        Data newSpecializationData = new Data();
        newSpecializationData.set(ProgramConstants.STATE, ProgramStatus.DRAFT.getValue());
        newSpecializationData.set(ProgramConstants.TYPE, "kuali.lu.type.Variation");
        newSpecializationData.set(ProgramConstants.PROGRAM_REQUIREMENTS, new Data());
        addCredentialProgramDataToVariation(newSpecializationData, programModel);
        return newSpecializationData;
    }
}
