package org.kuali.student.lum.program.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.ModelDefinition;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.lum.common.client.configuration.AbstractSectionConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * @author Igor
 */
public class ProgramUtils {

    public static DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    private ProgramUtils() {
    }

    public static void addCredentialProgramDataToVariation(Data variationData, DataModel model) {
        Data credentialProgram = new Data();
        
        // this should mimic the formats in VariationInformationEditConfiguration::createReadOnlySection
        credentialProgram.set(ProgramConstants.INSTITUTION, model.<Data>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.INSTITUTION));
        credentialProgram.set(ProgramConstants.PROGRAM_LEVEL, model.<String>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.PROGRAM_LEVEL));        
        credentialProgram.set(ProgramConstants.RUNTIME_DATA, model.<Data>get(ProgramConstants.CREDENTIAL_PROGRAM + "/" + ProgramConstants.RUNTIME_DATA));

        variationData.set(ProgramConstants.CREDENTIAL_PROGRAM, credentialProgram);
    }

    public static Data createNewSpecializationBasedOnMajor(DataModel programModel) {
        Data newSpecializationData = new Data();
        newSpecializationData.set(ProgramConstants.STATE, programModel.<String>get(ProgramConstants.STATE));
        newSpecializationData.set(ProgramConstants.TYPE, ProgramConstants.VARIATION_TYPE_KEY);
        newSpecializationData.set(ProgramConstants.PROGRAM_REQUIREMENTS, new Data());
        
        //Manually copy default values here... we don't have access to the metadata and we only want to default the first time
        newSpecializationData.set(ProgramConstants.FINANCIAL_CONTROL_DIVISION, programModel.<Data>get(ProgramConstants.FINANCIAL_CONTROL_DIVISION)==null?null:programModel.<Data>get(ProgramConstants.FINANCIAL_CONTROL_DIVISION).copy());
        newSpecializationData.set(ProgramConstants.FINANCIAL_CONTROL_UNIT, programModel.<Data>get(ProgramConstants.FINANCIAL_CONTROL_UNIT)==null?null:programModel.<Data>get(ProgramConstants.FINANCIAL_CONTROL_UNIT).copy());
        newSpecializationData.set(ProgramConstants.FINANCIAL_RESOURCES_DIVISION, programModel.<Data>get(ProgramConstants.FINANCIAL_RESOURCES_DIVISION)==null?null:programModel.<Data>get(ProgramConstants.FINANCIAL_RESOURCES_DIVISION).copy());
        newSpecializationData.set(ProgramConstants.FINANCIAL_RESOURCES_UNIT, programModel.<Data>get(ProgramConstants.FINANCIAL_RESOURCES_UNIT)==null?null:programModel.<Data>get(ProgramConstants.FINANCIAL_RESOURCES_UNIT).copy());
        newSpecializationData.set(ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION, programModel.<Data>get(ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION)==null?null:programModel.<Data>get(ProgramConstants.CURRICULUM_OVERSIGHT_DIVISION).copy());
        newSpecializationData.set(ProgramConstants.CURRICULUM_OVERSIGHT_UNIT, programModel.<Data>get(ProgramConstants.CURRICULUM_OVERSIGHT_UNIT)==null?null:programModel.<Data>get(ProgramConstants.CURRICULUM_OVERSIGHT_UNIT).copy());
        newSpecializationData.set(ProgramConstants.DEPLOYMENT_DIVISION, programModel.<Data>get(ProgramConstants.DEPLOYMENT_DIVISION)==null?null:programModel.<Data>get(ProgramConstants.DEPLOYMENT_DIVISION).copy());
        newSpecializationData.set(ProgramConstants.DEPLOYMENT_UNIT, programModel.<Data>get(ProgramConstants.DEPLOYMENT_UNIT)==null?null:programModel.<Data>get(ProgramConstants.DEPLOYMENT_UNIT).copy());
        newSpecializationData.set(ProgramConstants.STUDENT_OVERSIGHT_DIVISION, programModel.<Data>get(ProgramConstants.STUDENT_OVERSIGHT_DIVISION)==null?null:programModel.<Data>get(ProgramConstants.STUDENT_OVERSIGHT_DIVISION).copy());
        newSpecializationData.set(ProgramConstants.STUDENT_OVERSIGHT_UNIT, programModel.<Data>get(ProgramConstants.STUDENT_OVERSIGHT_UNIT)==null?null:programModel.<Data>get(ProgramConstants.STUDENT_OVERSIGHT_UNIT).copy());

        addCredentialProgramDataToVariation(newSpecializationData, programModel);
        return newSpecializationData;
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
            	String fdPath = element;
            	if (element.matches(".*/[0-9]+")){
            		//If path ends in number then strip it off, it is for an individual item in a list element
            		fdPath = element.substring(0,element.lastIndexOf("/"));
            	}
            	FieldDescriptor fd = Application.getApplicationContext().getPathToFieldMapping(null, fdPath);
            	            	
            	//If field descriptor found, display error on the field, otherwise display a generic error message.
            	if(fd!=null){
            		fd.getFieldElement().processValidationResult(validationResult);
            	}else{
            		int specializationIndex = Integer.parseInt(element.split("/")[1]);
            		failedSpecializations.add(specializationIndex);
            	}
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
            	KSNotifier.add(new KSNotification(getLabel(ProgramMsgConstants.MAJOR_VARIATIONFAILED, resultMessage), false, true, 5000));
            } else {
            	KSNotifier.add(new KSNotification(getLabel(ProgramMsgConstants.MAJOR_VARIATIONSFAILED, resultMessage), false, true, 5000));
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
				try {
					eventBus.removeHandler(typeEventHandlerEntry.getKey(),typeEventHandlerEntry.getValue());
				} catch(Exception e) {
					//FIXME: Unregistering of handlers should be better handled
				}
				finally{return;}
			}
		}
    }
    
    /**
     * 
     * This method will grab the proposal ID from the data model.
     * 
     * @param programModel XML data model
     * @return
     */
    public static String getProposalId(DataModel programModel) {
        return programModel.get(ProgramConstants.PROPOSAL_ID);
    }

    public static String getProgramId(DataModel programModel) {
        return programModel.get(ProgramConstants.ID);
    }

    public static String getProgramState(DataModel programModel) {
        return programModel.get(                ProgramConstants.STATE);
    }
    
    public static String getLabel(String messageKey, String parameter) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("0", parameter);
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, messageKey, parameters);
    }
}
