package org.kuali.student.lum.program.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;

/**
 * @author Igor
 */
public class ProgramUtils {

    public static DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    private ProgramUtils() {
    }

    public static void addCredentialProgramDataToVariation(Data variationData, DataModel model) {
        Data credentialProgram = new Data();
        Data institution = new Data();
        credentialProgram.set(ProgramConstants.INSTITUTION, institution);
        institution.set(ProgramConstants.ID, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM_INSTITUTION_ID));
        credentialProgram.set(ProgramConstants.PROGRAM_LEVEL, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.PROGRAM_LEVEL));

        Data runtimeData = new Data();
        Data programType = new Data();
        programType.set(ProgramConstants.ID_TRANSLATION, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM_TYPE_NAME));
        runtimeData.set(ProgramConstants.CREDENTIAL_PROGRAM_TYPE, programType);
        credentialProgram.set(ProgramConstants.RUNTIME_DATA, runtimeData);

        variationData.set(ProgramConstants.CREDENTIAL_PROGRAM, credentialProgram);
    }

    public static Data createNewSpecializationBasedOnMajor(DataModel programModel) {
        Data newSpecializationData = new Data();
        newSpecializationData.set(ProgramConstants.STATE, ProgramStatus.DRAFT.getValue());
        newSpecializationData.set(ProgramConstants.TYPE, ProgramConstants.VARIATION_TYPE_KEY);
        newSpecializationData.set(ProgramConstants.PROGRAM_REQUIREMENTS, new Data());
        addCredentialProgramDataToVariation(newSpecializationData, programModel);
        return newSpecializationData;
    }
}
