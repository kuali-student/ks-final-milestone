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
 * Created by David Yin on 5/10/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.common.util.security.ContextUtils;

import java.util.List;
import java.util.Map;

/**
 * This class provides a Lookupable implementation for SocRolloverResultItems
 *
 * @author Kuali Student Team
 */
public class SocRolloverResultItemInfoLookupableImpl extends LookupableImpl {

    private static final long serialVersionUID = 1L;
    public final static String SOC_ROLLOVER_RESULT_ID = "socRolloverResultId";

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        List<SocRolloverResultItemInfo> socRolloverResultItemInfos;
        String resultId = searchCriteria.get(SOC_ROLLOVER_RESULT_ID);

        try {
            socRolloverResultItemInfos = CourseOfferingManagementUtil.getCourseOfferingSetService().getSocRolloverResultItemsByResultId(resultId, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            throw new RuntimeException("Error getting SocRolloverResultItemsByResultId. ", e);
        }

        return socRolloverResultItemInfos;
    }

}
