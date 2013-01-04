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
 * Created by Daniel on 3/30/12
 */
package org.kuali.student.enrollment.class2.appointment.service.impl;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.appointment.dto.StudentGroupWrapper;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class StudentGroupWrapperLookupableImpl extends LookupableImpl {
    private transient PopulationService populationService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<StudentGroupWrapper> results = new ArrayList<StudentGroupWrapper>();
        ContextInfo context = new ContextInfo();

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        qBuilder.setPredicates();
        // create predicates for search parameters
        for(String key : fieldValues.keySet()){
            if(key.equalsIgnoreCase("name")){
                Predicate grpName = like(key,fieldValues.get(key));
                pList.add(grpName);
            } else{
                Predicate words = like(key,fieldValues.get(key));
                pList.add(words);
            }
        }
        if (!pList.isEmpty()){
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }

        try {
            // method returns list of populationinfos.
            java.util.List<PopulationInfo> populationInfos = getPopulationService().searchForPopulations(qBuilder.build(), context);
            for(PopulationInfo populationInfo:populationInfos){
                StudentGroupWrapper studentGroupWrapper = new StudentGroupWrapper();
                studentGroupWrapper.setId(populationInfo.getId());
                studentGroupWrapper.setName(populationInfo.getName());
                studentGroupWrapper.setDescription(populationInfo.getDescr().getPlain());
                results.add(studentGroupWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error Performing Search", e); //To change body of catch statement use File | Settings | File Templates.
        }
        return results;
    }

    protected PopulationService getPopulationService() {
        // populationService is retrieved using global resource loader which is wired in ks-enroll-context.xml
        if (populationService == null) {
            // TODO: Fix with real service later on
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationMockService"));

        }
        return populationService;
    }

}
