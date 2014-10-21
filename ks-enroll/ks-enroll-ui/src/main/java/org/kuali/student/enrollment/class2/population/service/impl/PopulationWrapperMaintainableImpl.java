package org.kuali.student.enrollment.class2.population.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintainableImpl;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.enrollment.class2.population.util.PopulationConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class performs Population Maintenance
 *
 * @author Kuali Student Team
 */
public class PopulationWrapperMaintainableImpl extends MaintainableImpl {
    private static final long serialVersionUID = 1L;
    private transient PopulationService populationService;

    @Override
    public void saveDataObject() {
        try {
            PopulationWrapper wrapper = (PopulationWrapper)getDataObject();
            if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_NEW_ACTION )) {
                createPopulation(wrapper);
            }
            else if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_EDIT_ACTION)){
                updatePopulation(wrapper);
            }

        } catch (Exception e) {
            throw new RuntimeException("PopulationWrapperMaintainableImpl exception. ", e);
        }

    }

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        try {
            String populationId =  dataObjectKeys.get("id");
            PopulationWrapper wrapper = getPopulation(populationId);
            wrapper.setId(populationId);
            if (PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY.equals(wrapper.getPopulationRuleInfo().getTypeKey())){
                //core type by rule
                wrapper.setCreateByRule(true);
            }
            else {
                //constructed type by combining populations
                wrapper.setCreateByRule(false);
                List<String> childPopulationIds = wrapper.getPopulationRuleInfo().getChildPopulationIds();
                List<PopulationInfo> childPopulations = new ArrayList<PopulationInfo>();
                if(!childPopulationIds.isEmpty()) {
                    childPopulations = getChildPopulations(childPopulationIds);
                }
                wrapper.setChildPopulations(childPopulations);
                // To display operation type as text
                if (wrapper.getOperationType().equals(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY)) {
                    wrapper.setOperationTypeText("Union");
                } else if (wrapper.getOperationType().equals(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY)) {
                    wrapper.setOperationTypeText("Intersection");
                } else if (wrapper.getOperationType().equals(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY)) {
                    wrapper.setOperationTypeText("Exclusion");
                }
            }

            return wrapper;
        } catch (Exception e) {
            throw new RuntimeException("Fail to retrieve Population for edit.", e);
        }
    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterEdit
     */
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription("Edit Population");

    }

    /**
     * @see org.kuali.rice.krad.maintenance.Maintainable#processAfterNew
     */
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        //set documentDescription to document.documentHeader.documentDescription
        document.getDocumentHeader().setDocumentDescription("Create a New Population");

    }

    // getting the PopulationInfo and PopulationRuleInfo and pushing them into wrapper
    public PopulationWrapper getPopulation(String populationId) {

        PopulationWrapper wrapper = new PopulationWrapper();

        try {
            ContextInfo context = ContextUtils.getContextInfo();
            //copy data to wrapper
            wrapper.setPopulationInfo(getPopulationService().getPopulation(populationId, context));
            wrapper.setPopulationRuleInfo(getPopulationService().getPopulationRuleForPopulation(populationId, context));
            wrapper.setOperationType(wrapper.getPopulationRuleInfo().getTypeKey());
            if(wrapper.getPopulationRuleInfo().getReferencePopulationId() != null){
                PopulationInfo referencePopulation = getPopulationService().getPopulation(wrapper.getPopulationRuleInfo().getReferencePopulationId(), context);
                wrapper.setReferencePopulation(referencePopulation);
            }
            wrapper.getChildPopulations().clear();
            for(String childPopulationId : wrapper.getPopulationRuleInfo().getChildPopulationIds()){
                wrapper.getChildPopulations().add(getPopulationService().getPopulation(childPopulationId, context));
            }

        } catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return wrapper;
    }

    // create the PopulationInfo and PopulationRuleInfo
    public PopulationWrapper createPopulation(PopulationWrapper wrapper) throws Exception {
        ContextInfo context = ContextUtils.getContextInfo();

        PopulationInfo populationInfo = getPopulationService().createPopulation(wrapper.getPopulationInfo().getTypeKey(),
                wrapper.getPopulationInfo(), context);

        wrapper.getPopulationRuleInfo().getChildPopulationIds().clear();
        for(PopulationInfo childPopulation : wrapper.getChildPopulations()){
            wrapper.getPopulationRuleInfo().getChildPopulationIds().add(childPopulation.getId());
        }
        if(wrapper.getReferencePopulation()!=null){
            wrapper.getPopulationRuleInfo().setReferencePopulationId(wrapper.getReferencePopulation().getId());
        }
        PopulationRuleInfo  populationRuleInfo = getPopulationService().createPopulationRule(
                wrapper.getPopulationRuleInfo().getTypeKey(), 
                wrapper.getPopulationRuleInfo(), context);

        getPopulationService().applyPopulationRuleToPopulation(populationRuleInfo.getId(), populationInfo.getId(), context);

        wrapper.setPopulationInfo(populationInfo);
        wrapper.setPopulationRuleInfo(populationRuleInfo);
        wrapper.setId(populationInfo.getId());
        return wrapper;
    }

    public void updatePopulation(PopulationWrapper wrapper) throws Exception {
        ContextInfo context = ContextUtils.getContextInfo();

        PopulationInfo populationInfo = getPopulationService().updatePopulation(wrapper.getId(), wrapper.getPopulationInfo(), context);

        wrapper.getPopulationRuleInfo().getChildPopulationIds().clear();
        for(PopulationInfo childPopulation : wrapper.getChildPopulations()){
            wrapper.getPopulationRuleInfo().getChildPopulationIds().add(childPopulation.getId());
        }
        if(wrapper.getReferencePopulation()!=null){
            wrapper.getPopulationRuleInfo().setReferencePopulationId(wrapper.getReferencePopulation().getId());
        }
        PopulationRuleInfo populationRuleInfo = getPopulationService().updatePopulationRule(wrapper.getPopulationRuleInfo().getId(), wrapper.getPopulationRuleInfo(), context);

        wrapper.setPopulationInfo(populationInfo);
        wrapper.setPopulationRuleInfo(populationRuleInfo);
        wrapper.setId(populationInfo.getId());
    }

    public List<PopulationInfo> getChildPopulations(List<String> childPopulationIds) throws Exception{
        ContextInfo context = ContextUtils.getContextInfo();
        List<PopulationInfo> childPopulations = new ArrayList<PopulationInfo>();
        for (String id : childPopulationIds){
            PopulationInfo populationInfo = getPopulationService().getPopulation(id, context);
            childPopulations.add(populationInfo);
        }
        return childPopulations;
    }

    /**
     * @see org.kuali.rice.krad.maintenance.MaintainableImpl#prepareForSave()
     */
    @Override
    public void prepareForSave() {
        if (getMaintenanceAction().equalsIgnoreCase(KRADConstants.MAINTENANCE_NEW_ACTION)) {
            PopulationWrapper wrapper = (PopulationWrapper)getDataObject();
            PopulationInfo populationInfo = wrapper.getPopulationInfo();
            populationInfo.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            populationInfo.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
            PopulationRuleInfo populationRuleInfo = wrapper.getPopulationRuleInfo();
            populationRuleInfo.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
            populationRuleInfo.setVariesByTime(false);
            populationRuleInfo.setSupportsGetMembers(false);
            if (wrapper.isCreateByRule()) {
                populationRuleInfo.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY);
            }
            else {
                populationRuleInfo.setTypeKey(wrapper.getOperationType());
            }
        }
        super.prepareForSave();
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return populationService;
    }


    @Override
    protected boolean performAddLineValidation(ViewModel model, Object newLine, String collectionId, String collectionPath) {
        ContextInfo context = ContextUtils.getContextInfo();
        Map<String, String> fieldValues = new HashMap<String, String>();

        if (newLine instanceof PopulationInfo) {
            PopulationInfo populationInfo = (PopulationInfo) newLine;
            fieldValues.put("name", populationInfo.getName());

            try {
                QueryByCriteria qbc = buildQueryByCriteria(fieldValues);
                List<PopulationInfo> populationInfoList = getPopulationService().searchForPopulations(qbc, context);

                if(populationInfoList == null || populationInfoList.isEmpty()){
                    GlobalVariables.getMessageMap().putErrorForSectionId("populations_table", PopulationConstants.POPULATION_MSG_ERROR_POPULATION_NOT_FOUND, populationInfo.getName());
                    return false;
                } else {
                    populationInfo.setName(populationInfoList.get(0).getName());
                    populationInfo.setId(populationInfoList.get(0).getId());
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return super.performAddLineValidation(model, newLine, collectionId, collectionPath);
    }


    private QueryByCriteria buildQueryByCriteria(Map<String, String> fieldValues){
        String populationName = fieldValues.get("name");

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(populationName)) {
            predicates.add(PredicateFactory.equalIgnoreCase("name", populationName));
            predicates.add(PredicateFactory.and(PredicateFactory.equal("populationState", PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY)));
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));

        return qbcBuilder.build();
    }
}
