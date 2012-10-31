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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SocRolloverResultItemInfoLookupableImpl extends LookupableImpl {
    private transient CourseOfferingSetService courseOfferingSetService;
    private static final long serialVersionUID = 1L;
    public final static String SOC_ROLLOVER_RESULT_ID = "socRolloverResultId";

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<SocRolloverResultItemInfo> socRolloverResultItemInfos;
        String resultId = fieldValues.get(SOC_ROLLOVER_RESULT_ID);

        try {
            socRolloverResultItemInfos = getCourseOfferingSetService().getSocRolloverResultItemsByResultId(resultId, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            throw new RuntimeException("Error getting SocRolloverResultItemsByResultId. ", e);
        }

        return socRolloverResultItemInfos;
    }

    public CourseOfferingSetService getCourseOfferingSetService() {
        if (courseOfferingSetService == null) {
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }
}
