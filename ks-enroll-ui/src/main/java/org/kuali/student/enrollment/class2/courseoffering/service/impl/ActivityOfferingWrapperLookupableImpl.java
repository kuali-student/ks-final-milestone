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
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ActivityOfferingWrapperLookupableImpl extends LookupableImpl {

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<ActivityOfferingWrapper> activityOfferingWrappers = new ArrayList<ActivityOfferingWrapper>();

        try {
            if(hasCriteria(fieldValues)){
                QueryByCriteria qbc = buildQueryByCriteria(fieldValues);
                List<ActivityOfferingInfo> activityOfferings = getCourseOfferingService().searchForActivityOfferings(qbc, ContextUtils.createDefaultContextInfo());
                for (ActivityOfferingInfo activityOffering : activityOfferings) {
                    ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(activityOffering);
                    activityOfferingWrappers.add(wrapper);
                }
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }

        return activityOfferingWrappers;
    }

    private QueryByCriteria buildQueryByCriteria(Map<String, String> fieldValues){
        String aoId = fieldValues.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (StringUtils.isNotBlank(aoId)) {
            predicates.add(PredicateFactory.equalIgnoreCase("id", aoId));
        }

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        return qbc;
    }

    private boolean hasCriteria(Map<String, String> fieldValues){
        return StringUtils.isNotBlank(fieldValues.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID));
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }
}
