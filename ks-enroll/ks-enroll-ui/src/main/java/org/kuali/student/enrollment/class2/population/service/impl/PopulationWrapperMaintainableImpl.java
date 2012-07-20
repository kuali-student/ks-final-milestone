package org.kuali.student.enrollment.class2.population.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;

import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;

import javax.xml.namespace.QName;
import java.util.Map;

public class PopulationWrapperMaintainableImpl extends MaintainableImpl {
    private transient PopulationService populationService;
    final Logger logger = Logger.getLogger(PopulationWrapperMaintainableImpl.class);
    
    @Override
    public void saveDataObject() {
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION )) {

        }
        else if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)){

        }

    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            String populationId =  dataObjectKeys.get("id");
            PopulationInfo populationInfo = getPopulationService().getPopulation(populationId, getContextInfo());
            PopulationRuleInfo populationRuleInfo = getPopulationService().getPopulationRuleForPopulation(populationId, getContextInfo());
            PopulationWrapper wrapper = new PopulationWrapper();
            wrapper.setPopulationInfo(populationInfo);
            wrapper.setPopulationRuleInfo(populationRuleInfo);
            wrapper.setShowLinkSection(false);
            if (PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY.equals(populationRuleInfo.getTypeKey())){
                //core type by rule
                wrapper.setShowByRuleLink(true);

            }
            else {
                //constructed type by combining populations
                wrapper.setShowByRuleLink(false);
            }

            return wrapper;
        } catch (Exception e) {
            logger.error("Fail to retrieve Population for edit.", e);
            throw new RuntimeException("Fail to retrieve Population for edit.", e);
        }
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }

}
