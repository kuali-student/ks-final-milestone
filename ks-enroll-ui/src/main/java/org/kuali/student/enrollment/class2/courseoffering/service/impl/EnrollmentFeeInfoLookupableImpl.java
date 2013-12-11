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

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.FeeServiceConstants;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;
import org.kuali.student.r2.core.fee.service.FeeService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides a Lookupable implementation for Enrollment Fees
 *
 * @author Kuali Student Team
 */
public class EnrollmentFeeInfoLookupableImpl extends LookupableImpl {

    private static final Logger LOG = Logger.getLogger(EnrollmentFeeInfoInquirableImpl.class);

    private FeeService feeService;

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        List<EnrollmentFeeInfo> enrollmentFeeInfos = new ArrayList<EnrollmentFeeInfo>();

        try {
            String id = searchCriteria.get("id");
            String refObjectURI = searchCriteria.get("refObjectURI");
            String refObjectId = searchCriteria.get("refObjectId");
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            // perform this search first so we don't have to search through the list for duplicates later
            if(refObjectId != null && !"".equals(refObjectId) && refObjectURI != null && !"".equals(refObjectURI) ){
                List<EnrollmentFeeInfo> efiList = getFeeService().getFeesByReference(refObjectURI,refObjectId, contextInfo);

                for(EnrollmentFeeInfo efi : efiList){
                    enrollmentFeeInfos.add(efi);
                }
            }


            if(id != null && !"".equals(id)){
               EnrollmentFeeInfo efi = getFeeService().getFee(id, contextInfo);

               if(efi != null && !enrollmentFeeInfos.contains(efi)){
                   enrollmentFeeInfos.add(efi);
               }
            }

        } catch (Exception e) {
           LOG.error("Error looking up fees", e);
        }

        return enrollmentFeeInfos;
    }

    public FeeService getFeeService() {
        if (feeService == null) {
            this.feeService = (FeeService) GlobalResourceLoader.getService(new QName(FeeServiceConstants.NAMESPACE, FeeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.feeService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }
}
