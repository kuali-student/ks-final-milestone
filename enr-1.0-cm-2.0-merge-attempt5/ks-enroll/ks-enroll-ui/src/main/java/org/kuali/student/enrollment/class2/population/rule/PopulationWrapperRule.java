package org.kuali.student.enrollment.class2.population.rule;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.enrollment.class2.population.util.PopulationConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class does business validation for Populations
 *
 * @author Kuali Student Team
 */
public class PopulationWrapperRule extends MaintenanceDocumentRuleBase {
    private transient PopulationService populationService;

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }

    /**
     * Main entry for validating populations.
     *
     * @param document
     * @return if this document contains valid population information
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
        PopulationWrapper newWrapper = (PopulationWrapper) document.getNewMaintainableObject().getDataObject();
        //Rule:  population Name has to be unique for creating a new population
        if (document.isNew()) {
            isValid &= populationNameUniqueCheck(newWrapper);
        }
        else if(document.isEdit()){
            PopulationWrapper oldWrapper = (PopulationWrapper) document.getOldMaintainableObject().getDataObject(); 
            if (!oldWrapper.getPopulationInfo().getName().equalsIgnoreCase(newWrapper.getPopulationInfo().getName())) {
                isValid &= populationNameUniqueCheck(newWrapper);
            }             
        }

        String operationType = newWrapper.getPopulationRuleInfo().getTypeKey();
        //Rule:
        //by Union - must select at least 2 DIFFERENT populations in order to create
        //by Intersection - must select at least 2 DIFFERENT populations in order to create
        if (operationType.equals(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY)){
            isValid &= needTwoChildPopulations(newWrapper, "Union" );
        }
        else if (operationType.equals(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY)){

            isValid &= needTwoChildPopulations(newWrapper, "Intersection" );
        }
        else if (operationType.equals(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY)){
            //Rule:
            //by Exclusion - must select one reference population and at least one other DIFFERENT population in order to create
            isValid &= checkReferenceAndChildPopulations(newWrapper);
        }
        return isValid;
    }

    protected boolean checkReferenceAndChildPopulations(PopulationWrapper wrapper){
        boolean isValid  = true;
        List<PopulationInfo> populationInfoList = wrapper.getChildPopulations();
        String referenceId = wrapper.getReferencePopulation().getId();
        if(populationInfoList == null || populationInfoList.isEmpty()){
            isValid = false;
            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.operationType",
                    PopulationConstants.POPULATION_MSG_ERROR_NEED_ONE_POPULATIONS, "Exclusion");
        }else{
            // the ref population cannot be in the  source populations.
            if(this.containsPopulation(populationInfoList,referenceId)){
                isValid = false;
                GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.operationType",
                        PopulationConstants.POPULATION_MSG_ERROR_REF_NOT_ALLOWED_IN_SOURCE_POPULATIONS, "Exclusion");
            }

            if(populationInfoList.size() > 1 ){     //Two or more
                if(this.hasDuplicates(populationInfoList)){
                    isValid = false;
                    GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.operationType",
                            PopulationConstants.POPULATION_MSG_ERROR_NEED_TWO_DIFFERENT_POPULATIONS, "Exclusion");
                }
            }
        }

        return isValid;
    }

    protected boolean containsPopulation(List<PopulationInfo> populationInfoList, String populationId){
        if(populationInfoList!=null){
            for(PopulationInfo population : populationInfoList){
                if(population.getId().equalsIgnoreCase(populationId)){
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean hasDuplicates(List<PopulationInfo> populationInfoList){
        //Compare and make sure there is no duplication
        for (int i= 0; i < populationInfoList.size(); i++) {
            for (int j= 0; j < populationInfoList.size(); j++) {
                if (i != j && populationInfoList.get(i).getId().equals(populationInfoList.get(j).getId())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Validates that a wrapper has at least two unique child populations
     * @param wrapper
     * @param operation PoplulationType type key (union,intersection/exclusion)
     * @return
     */
    protected boolean needTwoChildPopulations (PopulationWrapper wrapper, String operation){
        boolean isValid  = true;
        List<PopulationInfo> populationInfoList = wrapper.getChildPopulations();

        if(populationInfoList.size() > 1 ){     //Two or more
            for (PopulationInfo populationInfo: populationInfoList) {
                int duplicateCntr = 0;
                for (PopulationInfo populationInfo1: populationInfoList) {
                    if (populationInfo.getId().equals(populationInfo1.getId())){
                        duplicateCntr++;
                    }
                    if ( duplicateCntr > 1 ) {   //No duplicates
                        isValid = false;
                        break;
                    }
                }
                if ( !isValid ){
                    break;
                }
            }
        } else {
            isValid = false;
        }
        if ( !isValid ){
            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.operationType",
                    PopulationConstants.POPULATION_MSG_ERROR_NEED_TWO_DIFFERENT_POPULATIONS, operation);
        }

        return isValid;
    }

    /**
     * Validates that the population name has not already been used
     * @param wrapper
     * @return if the population name is unique for the application
     */
    protected boolean populationNameUniqueCheck(PopulationWrapper wrapper){
        boolean isNameUnique = true;

        String popName = wrapper.getPopulationInfo().getName();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("name", popName));
        QueryByCriteria criteria = qbcBuilder.build();
        ContextInfo context = ContextUtils.getContextInfo();
        try {
            List<PopulationInfo> populationInfoList = getPopulationService().searchForPopulations(criteria, context);
            if (populationInfoList.size()>0){
                putFieldError(PopulationConstants.PopulationWrapper.POPULATION_NAME, PopulationConstants.POPULATION_MSG_ERROR_NAME_IS_NOT_UNIQUE, popName);
                isNameUnique = false;
            }                 
        } catch (Exception e) {
            throw new RuntimeException("PopulationWrapperRule exception. ", e);
        }
        return isNameUnique;
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return populationService;
    }
    
}
