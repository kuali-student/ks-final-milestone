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
 * Created by vgadiyak on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.service.impl;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ScheduleOfClassesWrapper;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class performs lookups on Populations.
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesWrapperLookupableImpl extends LookupableImpl {

    private transient CourseOfferingService courseOfferingService;

    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<ScheduleOfClassesWrapper> scheduleOfClassesWrappers = new ArrayList<ScheduleOfClassesWrapper>();

        ContextInfo context = ContextUtils.createDefaultContextInfo();

        try {
            //perform the lookup using the service
            QueryByCriteria qbc = buildQueryByCriteria(fieldValues);
            // To Do
        } catch (Exception e) {
            throw new RuntimeException("ScheduleOfClassesWrapperLookupableImpl exception. ", e);
        }

        return scheduleOfClassesWrappers;
    }

    /**
     * Builds a QueryByCriteria based on the KRAD field values passed in.
     * Performs fuzzy searching on the keyword field against the name and description fields on PopulationEntity
     *
     * @param fieldValues
     * @return a criteria query
     */
    private QueryByCriteria buildQueryByCriteria(Map<String, String> fieldValues){
        String keyword = fieldValues.get("keyword");
        String stateKey = fieldValues.get("populationInfo.stateKey");

        keyword = keyword.isEmpty()?"*":keyword; //search for all if empty

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.or(PredicateFactory.like("name", "%" + keyword + "%"),
                PredicateFactory.like("descrPlain", "%" + keyword + "%")));

        // Any extra TO DO?

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));

        return qbcBuilder.build();
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }

}