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

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class FormatOfferingInfoInquirableImpl extends InquirableImpl {
    public final static String FORMAT_OFFERING_ID = "id";
    final Logger logger = Logger.getLogger(FormatOfferingInfoInquirableImpl.class);

    @Override
    public FormatOfferingInfo retrieveDataObject(Map<String, String> parameters) {

        try {
            FormatOfferingInfo formatOfferingInfo = getCourseOfferingService().getFormatOffering(parameters.get(FORMAT_OFFERING_ID), getContextInfo());
            return formatOfferingInfo;
        } catch (Exception e) {
            logger.error("FormatOfferingInfo inquiry has failed. ", e);
            throw new RuntimeException("FormatOfferingInfo inquiry has failed. ", e);
        }
    }


    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
