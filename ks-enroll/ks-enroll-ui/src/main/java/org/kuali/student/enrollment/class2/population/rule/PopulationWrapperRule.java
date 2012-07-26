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
