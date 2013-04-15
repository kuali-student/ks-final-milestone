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
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.FeeServiceConstants;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;
import org.kuali.student.r2.core.fee.service.FeeService;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class EnrollmentFeeInfoInquirableImpl extends InquirableImpl {

    private static final Logger LOG = Logger.getLogger(EnrollmentFeeInfoInquirableImpl.class);

    private FeeService feeService;

    @Override
    public EnrollmentFeeInfo retrieveDataObject(Map<String, String> parameters) {

        EnrollmentFeeInfo efiRet = null;

        try {
            String id = parameters.get("id");

            if(id != null && !"".equals(id)){
                efiRet = getFeeService().getFee(id, ContextUtils.createDefaultContextInfo());
            }
        } catch (Exception e) {
            LOG.error("Error retrieving enrollment fee info", e);
        }
        return efiRet;
    }

    public FeeService getFeeService() {
        if (feeService == null) {
            this.feeService = (FeeService) GlobalResourceLoader.getService(new QName(FeeServiceConstants.NAMESPACE, FeeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.feeService;
    }

    public void setFeeService(FeeService feeService) {
        this.feeService = feeService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

}

