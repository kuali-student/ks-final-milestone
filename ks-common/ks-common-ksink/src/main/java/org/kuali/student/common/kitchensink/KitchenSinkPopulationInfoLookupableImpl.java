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
 * Created by bobhurt on 10/31/12
 */
package org.kuali.student.common.kitchensink;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class performs a search on the name & description properties in Populations
 *
 * @author Kuali Student Team
 */
public class KitchenSinkPopulationInfoLookupableImpl extends LookupableImpl {

    private transient PopulationService populationService;

    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        // build query criteria
        String keyword = fieldValues.get("keyword");
        keyword = keyword.isEmpty() ? "*" : keyword; //search for all if empty
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.or(PredicateFactory.like("name", "%" + keyword + "%"),
                PredicateFactory.like("descrPlain", "%" + keyword + "%")));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        // perform population search using criteria
        List<PopulationInfo> populationInfoList;
        try {
            populationInfoList =
                    getPopulationService().searchForPopulations(qbc, ContextUtils.createDefaultContextInfo());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return populationInfoList;
    }


    private PopulationService getPopulationService() {
        if (populationService == null) {
            populationService = (PopulationService)GlobalResourceLoader
                    .getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationService"));
        }
        return populationService;
    }

    /**
     * Determines if given data object has associated maintenance document that allows new or copy
     * maintenance
     * actions
     *
     * @return boolean true if the maintenance new or copy action is allowed for the data object instance, false
     *         otherwise
     */
    @Override
    public boolean allowsMaintenanceNewOrCopyAction() {
        // maintenance new or copy action not allowed
        return false;
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
