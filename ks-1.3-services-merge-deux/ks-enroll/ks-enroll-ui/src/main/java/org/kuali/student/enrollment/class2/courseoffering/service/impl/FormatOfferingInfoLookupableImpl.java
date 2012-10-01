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

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FormatOfferingInfoLookupableImpl extends LookupableImpl {
    public final static String COURSE_OFFER_ID = "courseOfferingId";
    private transient CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo = null;
    final Logger logger = Logger.getLogger(FormatOfferingInfoLookupableImpl.class);

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<FormatOfferingInfo> formatOfferingInfos;

        try {
            formatOfferingInfos = getCourseOfferingService().getFormatOfferingsByCourseOffering(fieldValues.get(COURSE_OFFER_ID), getContextInfo());
        } catch (DoesNotExistException e) {
            logger.error("FormatOfferingInfo does not exist. ", e);
            throw new RuntimeException("FormatOfferingInfo does not exist. ", e);
        } catch (InvalidParameterException e) {
            logger.error("FormatOfferingInfo invalid parameter. ", e);
            throw new RuntimeException("FormatOfferingInfo invalid parameter. ", e);
        } catch (MissingParameterException e) {
            logger.error("FormatOfferingInfo missing parameter. ", e);
            throw new RuntimeException("FormatOfferingInfo missing parameter. ", e);
        } catch (OperationFailedException e) {
            logger.error("FormatOfferingInfo operation failed. ", e);
            throw new RuntimeException("FormatOfferingInfo operation failed. ", e);
        } catch (PermissionDeniedException e) {
            logger.error("FormatOfferingInfo permission denied. ", e);
            throw new RuntimeException("FormatOfferingInfo permission denied. ", e);
        }

        return formatOfferingInfos;
    }

    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null)
            courseOfferingService= CourseOfferingResourceLoader.loadCourseOfferingService();
        return courseOfferingService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }
}
