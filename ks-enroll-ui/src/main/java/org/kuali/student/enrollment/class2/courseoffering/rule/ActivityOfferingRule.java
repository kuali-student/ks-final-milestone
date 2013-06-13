package org.kuali.student.enrollment.class2.courseoffering.rule;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.population.util.PopulationConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;


public class ActivityOfferingRule extends KsMaintenanceDocumentRuleBase {

    private transient PopulationService populationService;

    @Override
    protected boolean isDocumentValidForSave(MaintenanceDocument maintenanceDocument) {
        if ( ! super.isDocumentValidForSave(maintenanceDocument)) {
            return false;
        }

        boolean result = true;
        ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)maintenanceDocument.getNewMaintainableObject().getDataObject();

        // validate Seat Pool population
        result &= validateSeatpools(aoWrapper);

        // validate colocated Activity Offerings
        if (aoWrapper.isColocatedAO() && aoWrapper.getColocatedActivities().isEmpty()) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS,
                    ActivityOfferingConstants.MSG_ERROR_COLOCATED_NOTFOUND);
            result = false;
        }

        return result;
    }


    private boolean validateSeatpools(ActivityOfferingWrapper activityOfferingWrapper){
        List<SeatPoolWrapper> seatPoolWrappers = activityOfferingWrapper.getSeatpools();
        String errorMsgInvalidPop  = "";
        String errorMsgDupPop = "";
        Set<String> populationIds = new HashSet<String>();
        boolean validFlag = true;

        for (SeatPoolWrapper seatPool : seatPoolWrappers) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.equal("populationState", PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY),
                    PredicateFactory.equalIgnoreCase("name", seatPool.getSeatPoolPopulation().getName())));
            QueryByCriteria criteria = qbcBuilder.build();

            try {
                List<PopulationInfo> populationInfoList =
                        getPopulationService().searchForPopulations(criteria, createContextInfo());
                //check if the population is valid
                if (populationInfoList == null || populationInfoList.isEmpty()){
                    if (errorMsgInvalidPop.isEmpty()) {
                        errorMsgInvalidPop = seatPool.getSeatPoolPopulation().getName();
                    } else {
                        errorMsgInvalidPop += ", " + seatPool.getSeatPoolPopulation().getName();
                    }
                } else {
                    seatPool.getSeatPoolPopulation().setName(populationInfoList.get(0).getName());
                    seatPool.getSeatPoolPopulation().setId(populationInfoList.get(0).getId());

                    //check if the population is duplicated. If the id can't be added into the populationIds, it means it is duplicated
                    if (!populationIds.add(populationInfoList.get(0).getId())) {
                        if (errorMsgDupPop.isEmpty()) {
                            errorMsgDupPop = seatPool.getSeatPoolPopulation().getName();
                        } else {
                            errorMsgDupPop += ", " + seatPool.getSeatPoolPopulation().getName();
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (!errorMsgInvalidPop.isEmpty()) {
            GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup",
                    PopulationConstants.POPULATION_MSG_ERROR_POPULATION_NOT_FOUND, errorMsgInvalidPop);
            validFlag = false;
        }
        if (!errorMsgDupPop.isEmpty()) {
            GlobalVariables.getMessageMap().putErrorForSectionId("ao-seatpoolgroup",
                    PopulationConstants.POPULATION_MSG_ERROR_NAME_IS_NOT_UNIQUE, errorMsgDupPop);
            validFlag = false;
        }

        return validFlag;
    }


    private PopulationService getPopulationService() {
        if (populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, PopulationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return populationService;
    }

    private ContextInfo createContextInfo() {
        return ContextUtils.createDefaultContextInfo();
    }

}
