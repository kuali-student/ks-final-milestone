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
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.FormatOfferingConstants;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FormatOfferingInfoLookupableImpl extends LookupableImpl {
    private static final long serialVersionUID = 1L;
    public final static String COURSE_OFFER_ID = "courseOfferingId";

    private transient CourseOfferingService courseOfferingService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<FormatOfferingInfo> formatOfferingInfos = null;

        String typeKey = fieldValues.get(FormatOfferingConstants.FORMAT_OFFERING_TYPE_KEY);
        String courseOfferingId = fieldValues.get(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID);
        try {
            if (StringUtils.isNotBlank(courseOfferingId)) {
                formatOfferingInfos = getCourseOfferingService().getFormatOfferingsByCourseOffering(fieldValues.get(COURSE_OFFER_ID), ContextUtils.createDefaultContextInfo());
            }  else if (StringUtils.isNotBlank(typeKey)) {
                formatOfferingInfos = getSearchResultsByType (typeKey);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting FormatOfferingInfo.", e);
        }

        return formatOfferingInfos;
    }

    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null)
            courseOfferingService= CourseOfferingResourceLoader.loadCourseOfferingService();
        return courseOfferingService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    private List<FormatOfferingInfo> getSearchResultsByType (String typeKey){
        List<FormatOfferingInfo> formatOfferingInfos = null;
        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.equalIgnoreCase(FormatOfferingConstants.FORMAT_OFFERING_LUI_TYPE, typeKey)));
            QueryByCriteria criteria = qbcBuilder.build();

            List<String> listFormatOfferingIDs = getCourseOfferingService().searchForFormatOfferingIds(criteria, getContextInfo());
            if (listFormatOfferingIDs != null) {
                String formatOfferingID = null;
                formatOfferingInfos = new ArrayList<FormatOfferingInfo>();
                for(Iterator i = listFormatOfferingIDs.iterator(); i.hasNext();){
                    formatOfferingID = (String) i.next();
                    formatOfferingInfos.add ((FormatOfferingInfo) getCourseOfferingService().getFormatOffering(formatOfferingID, getContextInfo()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting FormatOfferingInfo.getSearchResultsByType.errorLineNumber=" + getLineNumber(), e);
        }
        return formatOfferingInfos;
    }
}
