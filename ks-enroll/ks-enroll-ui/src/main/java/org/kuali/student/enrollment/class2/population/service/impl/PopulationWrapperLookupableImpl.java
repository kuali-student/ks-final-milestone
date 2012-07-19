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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class PopulationWrapperLookupableImpl extends LookupableImpl {
    private transient PopulationService populationService = getPopulationService();

    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<PopulationWrapper> populationWrappers = new ArrayList<PopulationWrapper>();
        List<PopulationInfo> populationInfoList = new ArrayList<PopulationInfo>();

        try {
            if (!fieldValues.get("keyword").isEmpty()) {
                QueryByCriteria qbc = buildQueryByCriteria(fieldValues, "name");  //search for keyword in name
                populationInfoList = getPopulationService().searchForPopulations(qbc, getContextInfo());
                qbc = buildQueryByCriteria(fieldValues, "descr");                 //search for keyword in description
                populationInfoList.addAll(getPopulationService().searchForPopulations(qbc, getContextInfo()));
            } else {
                return populationWrappers;
            }

            for (PopulationInfo populationInfo: populationInfoList) {
                PopulationRuleInfo populationRuleInfo = getPopulationService().getPopulationRuleForPopulation(populationInfo.getId(),getContextInfo());
                PopulationWrapper wrapper = new PopulationWrapper();
                if (//populationRuleInfo.getTypeKey().equals(fieldValues.get("populationRuleInfo.typeKey")) &&    out for now
                    populationInfo.getStateKey().equals(fieldValues.get("populationInfo.stateKey"))) {    //filter by state
                    wrapper.setPopulationRuleInfo(populationRuleInfo);
                    wrapper.setPopulationInfo(populationInfo);
                    populationWrappers.add(wrapper);
                }
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }

        return populationWrappers;
    }

    private QueryByCriteria buildQueryByCriteria(Map<String, String> fieldValues, String nameOrDescr){
        String keyword = fieldValues.get("keyword");

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (nameOrDescr.equals("name")) {
            predicates.add(PredicateFactory.like("name", "%"+keyword+"%"));
        } else {
            predicates.add(PredicateFactory.like("descrPlain", "%"+keyword+"%"));
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        return qbc;
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