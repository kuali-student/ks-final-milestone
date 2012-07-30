package org.kuali.student.enrollment.class2.population.rule;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.impl.util.KRMSPropertyConstants;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.enrollment.class2.population.util.PopulationConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class PopulationWrapperRule extends MaintenanceDocumentRuleBase {
    private transient PopulationService populationService;

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }

    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {

        boolean isValid = true;
        isValid &= super.processCustomRouteDocumentBusinessRules(document);
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
            isValid &= checkReferneceAndChildpopulations(newWrapper);
        }
        return isValid;
    }

    protected boolean checkReferneceAndChildpopulations (PopulationWrapper wrapper){
        boolean isValid  = true;
        List<PopulationInfo> populationInfoList = wrapper.getChildPopulations();
        String referenceId = wrapper.getPopulationRuleInfo().getReferencePopulationId();
        if(populationInfoList == null || populationInfoList.isEmpty()){
            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.operationType",
                    PopulationConstants.POPULATION_MSG_ERROR_NEED_ONE_POPULATIONS, "Exclusion");
            isValid = false;
        }
        if(populationInfoList!= null && populationInfoList.size() > 1 ){     //Two or more
            boolean hasDuplicates = false;
            //Compare and make sure there is no duplication
            List<PopulationInfo> populationInfoList1 = populationInfoList;
            for (PopulationInfo populationInfo: populationInfoList) {
                for (PopulationInfo populationInfo1: populationInfoList1) {
                    if (populationInfo.getId().equals(populationInfo1.getId())){
                        hasDuplicates = true;
                        isValid = false;
                        GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.operationType",
                                PopulationConstants.POPULATION_MSG_ERROR_NEED_TWO_DIFFERENT_POPULATIONS, "Exclusion");
                        break;
                    }
                }
                if ( hasDuplicates ){
                    break;
                }
            }
        }

        return isValid;
    }

    protected boolean needTwoChildPopulations (PopulationWrapper wrapper, String operation){
        boolean isValid  = true;
        List<PopulationInfo> populationInfoList = wrapper.getChildPopulations();
        List<PopulationInfo> populationInfoList1 = populationInfoList;
        if(populationInfoList.size() > 1 ){     //Two or more
            for (PopulationInfo populationInfo: populationInfoList) {
                int duplicateCntr = 0;
                for (PopulationInfo populationInfo1: populationInfoList1) {
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
//              GlobalVariables.getMessageMap().putError("newCollectionLines[document.newMaintainableObject.dataObject.childPopulations].name",
            GlobalVariables.getMessageMap().putError("document.newMaintainableObject.dataObject.operationType",
                    PopulationConstants.POPULATION_MSG_ERROR_NEED_TWO_DIFFERENT_POPULATIONS, operation);
//            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Must select at least 2 different populations.");
        }

        return isValid;
    }

    protected boolean populationNameUniqueCheck(PopulationWrapper wrapper){
        boolean isNameUnique = true;

        String popName = wrapper.getPopulationInfo().getName();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("name", popName));
        QueryByCriteria criteria = qbcBuilder.build();
        ContextInfo context = ContextInfo.createDefaultContextInfo();
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
