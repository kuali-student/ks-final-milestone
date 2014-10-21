/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by mharmath on 7/17/12
 */
package org.kuali.student.enrollment.class2.population.service.impl;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.util.StringUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class performs lookups on Populations.
 *
 * @author Kuali Student Team
 */
public class PopulationWrapperLookupableImpl extends KSLookupableImpl {
    private static final long serialVersionUID = 1L;
    private transient PopulationService populationService;

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        List<PopulationWrapper> populationWrappers = new ArrayList<PopulationWrapper>();

        ContextInfo context = ContextUtils.createDefaultContextInfo();

        try {
            //perform the lookup using the service
            QueryByCriteria qbc = buildQueryByCriteria(searchCriteria);
            List<PopulationInfo> populationInfoList = getPopulationService().searchForPopulations(qbc, context);

            //Transform each PopulationInfo to the wrapper class
            for (PopulationInfo populationInfo: populationInfoList) {
                PopulationRuleInfo populationRuleInfo = getPopulationService().getPopulationRuleForPopulation(populationInfo.getId(), context);
                PopulationWrapper wrapper = new PopulationWrapper();
                wrapper.setPopulationRuleInfo(populationRuleInfo);
                wrapper.setPopulationInfo(populationInfo);
                wrapper.setId(populationInfo.getId());
                //set display names
                wrapper.setPopulationRuleTypeKeyName(populationRuleInfo.getTypeKey().substring(populationRuleInfo.getTypeKey().lastIndexOf('.')+1));
                wrapper.setPopulationStateKeyName(populationInfo.getStateKey().substring(populationInfo.getStateKey().lastIndexOf('.')+1));

                populationWrappers.add(wrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException("PopulationWrapperLookupableImpl exception. ", e);
        }

        return populationWrappers;
    }

    /**
     * Builds a QueryByCriteria based on the KRAD field values passed in.
     * Performs fuzzy searching on the keyword field against the name and description fields on PopulationEntity
     *
     * @param searchCriteria map of field names and values
     * @return a criteria query
     */
    private QueryByCriteria buildQueryByCriteria(Map<String, String> searchCriteria){
        String keyword = searchCriteria.get("keyword");
        String stateKey = searchCriteria.get("populationInfo.stateKey");

        if (StringUtils.isEmpty(keyword)) {
            keyword = "*";      // search for all if empty
        }

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.or(PredicateFactory.like("name", "%"+keyword+"%"),
                                           PredicateFactory.like("descrPlain", "%"+keyword+"%")));
        if (stateKey.equals(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY) ||
                stateKey.equals(PopulationServiceConstants.POPULATION_INACTIVE_STATE_KEY)) {
            predicates.add(PredicateFactory.and(PredicateFactory.equal("populationState", stateKey)));
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));

        return qbcBuilder.build();
    }

    private PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return this.populationService;
    }

    /**
     * Determines if given data object has associated maintenance document that allows delete maintenance
     * actions.
     *
     * @return boolean true if the maintenance delete action is allowed for the data object instance, false otherwise
     */

    @Override
    public boolean allowsMaintenanceDeleteAction(Object dataObject) {
        // maintenance delete action not allowed
        return false;
    }

}