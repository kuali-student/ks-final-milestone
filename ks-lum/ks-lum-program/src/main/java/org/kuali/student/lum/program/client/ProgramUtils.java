package org.kuali.student.lum.program.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.ModelDefinition;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import java.util.*;

/**
 * @author Igor
 */
public class ProgramUtils {

    public static DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    private ProgramUtils() {
    }

    public static void addCredentialProgramDataToVariation(Data variationData, DataModel model) {
        Data credentialProgram = new Data();
        credentialProgram.set(ProgramConstants.INSTITUTION, model.<Data>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.INSTITUTION));
        credentialProgram.set(ProgramConstants.PROGRAM_LEVEL, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.PROGRAM_LEVEL));
        credentialProgram.set(ProgramConstants.SHORT_TITLE, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.SHORT_TITLE));

        variationData.set(ProgramConstants.CREDENTIAL_PROGRAM, credentialProgram);
    }

    public static Data createNewSpecializationBasedOnMajor(DataModel programModel) {
        Data newSpecializationData = new Data();
        newSpecializationData.set(ProgramConstants.STATE, programModel.<String>get(ProgramConstants.STATE));
        newSpecializationData.set(ProgramConstants.TYPE, ProgramConstants.VARIATION_TYPE_KEY);
        newSpecializationData.set(ProgramConstants.PROGRAM_REQUIREMENTS, new Data());
        addCredentialProgramDataToVariation(newSpecializationData, programModel);
        return newSpecializationData;
    }

    public static void setStatus(DataModel dataModel, String status) {
        QueryPath statePath = new QueryPath();
        statePath.add(new Data.StringKey(ProgramConstants.STATE));
        dataModel.set(statePath, status);
        setStatus((Data) dataModel.get(ProgramConstants.VARIATIONS), status);
    }

    public static void setPreviousStatus(DataModel dataModel, String status) {
        QueryPath statePath = QueryPath.parse(ProgramConstants.PREV_STATE);
        dataModel.set(statePath, status);
    }

    private static void setStatus(Data inputData, String status) {
        if (inputData != null) {
            for (Data.Property property : inputData) {
                Data data = property.getValue();
                data.set(new Data.StringKey(ProgramConstants.STATE), status);
            }
        }
    }

    public static void retrofitValidationResults(List<ValidationResultInfo> validationResults) {
        for (ValidationResultInfo validationResult : validationResults) {
            String key = validationResult.getElement();
            if (ProgramConstants.RICH_TEXT_KEYS.contains(key)) {
                key = key + "/plain";
                validationResult.setElement(key);
            }
        }
    }

    public static void handleValidationErrorsForSpecializations(List<ValidationResultInfo> validationResults, DataModel programModel) {
        Set<Integer> failedSpecializations = new TreeSet<Integer>();
        for (ValidationResultInfo validationResult : validationResults) {
            String element = validationResult.getElement();
            if (element.contains(ProgramConstants.VARIATIONS)) {
                int specializationIndex = Integer.parseInt(element.split("/")[1]);
                failedSpecializations.add(specializationIndex);
            }
        }
        if (!failedSpecializations.isEmpty()) {
            final Data variationMap = programModel.get(ProgramConstants.VARIATIONS);
            StringBuilder validationMessage = new StringBuilder();
            for (Integer failedSpecialization : failedSpecializations) {
                Data data = variationMap.get(failedSpecialization);
                validationMessage.append(data.get(ProgramConstants.LONG_TITLE)).append(", ");
            }
            String resultMessage = validationMessage.toString();
            //Cutoff ', ' from the result
            resultMessage = resultMessage.substring(0, resultMessage.length() - 2);
            if (failedSpecializations.size() == 1) {
                Window.alert(ProgramProperties.get().major_variationFailed(resultMessage));
            } else {
                Window.alert(ProgramProperties.get().major_variationsFailed(resultMessage));
            }
        }
    }


    public static void syncMetadata(AbstractProgramConfigurer configurer, ModelDefinition modelDefinition) {
        ConfigurationManager configurationManager = configurer.getProgramSectionConfigManager();
        for (Configuration conf : configurationManager.getConfigurations()) {
            if (conf instanceof AbstractSectionConfiguration) {
                AbstractSectionConfiguration configuration = (AbstractSectionConfiguration) conf;
                View view = configuration.getView(false);
                if (view != null && view instanceof SectionView) {
                    SectionView sectionView = (SectionView) view;
                    sectionView.updateMetadata(modelDefinition);
                }
            }
        }
    }

    public static void unregisterUnusedHandlers(HandlerManager eventBus) {
        HashMap<GwtEvent.Type, EventHandler> eventsMap = ProgramRegistry.getSpecializationHandlers();
        if (eventsMap != null) {
            for (Map.Entry<GwtEvent.Type, EventHandler> typeEventHandlerEntry : eventsMap.entrySet()) {
                eventBus.removeHandler(typeEventHandlerEntry.getKey(), typeEventHandlerEntry.getValue());
            }
            eventsMap.clear();
        }
    }
}
