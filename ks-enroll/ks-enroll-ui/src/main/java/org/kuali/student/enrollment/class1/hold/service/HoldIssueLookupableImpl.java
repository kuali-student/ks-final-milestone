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
 * Created by Adi Rajesh on 6/7/12
 */
package org.kuali.student.enrollment.class1.hold.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class1.hold.util.HoldsConstants;
import org.kuali.student.enrollment.class1.hold.util.HoldsResourceLoader;
import org.kuali.student.enrollment.class1.hold.util.HoldsUtil;
import org.kuali.student.r2.core.constants.HoldServiceConstants;

import java.util.List;
import java.util.Map;


/**
 * This class provides a Lookupable implementation for HoldIssue objects
 *
 * @author Kuali Student Team
 */
public class HoldIssueLookupableImpl extends KSLookupableImpl {

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {

        try {
            searchCriteria.put(HoldsConstants.HOLD_ISSUE_STATE_KEY, HoldServiceConstants.ISSUE_ACTIVE_STATE_KEY);
            QueryByCriteria.Builder query = HoldsUtil.buildQueryByCriteria(searchCriteria);

            return HoldsUtil.createHoldIssueResultList(HoldsResourceLoader.getHoldService().searchForHoldIssues(query.build(), ContextUtils.createDefaultContextInfo()));

        } catch (Exception e) {
            throw new RuntimeException("Error Performing Search", e);
        }
    }

}