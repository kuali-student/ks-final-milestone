/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by delyea on 3/18/14
 */
package org.kuali.student.cm.course.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.RetireCourseWrapper;
import org.kuali.student.cm.proposal.util.ProposalUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class is a Util class designed to hold common course proposal related code that should be shared across multiple classes
 *
 * @author Kuali Student Team
 */
public class CourseProposalUtil {

    /**
     * Constructs a text URL for a particular course proposal.
     */
    public static String buildCourseProposalUrl(String methodToCall, String pageId, String workflowDocId, String proposalType) {
        Properties props = ProposalUtil.getProposalUrlProperties(methodToCall, pageId, workflowDocId);
        if (CurriculumManagementConstants.CoursePageIds.REVIEW_RETIRE_COURSE_PROPOSAL_PAGE.equals(pageId)) {
            props.put(UifParameters.DATA_OBJECT_CLASS_NAME, RetireCourseWrapper.class.getCanonicalName());
        } else {
            props.put(UifParameters.DATA_OBJECT_CLASS_NAME, CourseInfoWrapper.class.getCanonicalName());
        }
        String controllerRequestMapping = CurriculumManagementConstants.ControllerRequestMappings.MappingsByProposalType.getControllerMapping(proposalType);
        if (StringUtils.isBlank(controllerRequestMapping)) {
            throw new RuntimeException("Cannot find request mapping for proposal type: " + proposalType);
        }
        String courseBaseUrl = controllerRequestMapping.replaceFirst("/", "");
        return UrlFactory.parameterizeUrl(courseBaseUrl, props);
    }

    /**
     * Constructs the url for view course for redirect from retire course page.
     */
    public static String getViewCourseUrl(){

        String cmViewCourseControllerMapping = CurriculumManagementConstants.ControllerRequestMappings.VIEW_COURSE.replaceFirst("/", "");

        StringBuilder cmViewCourseUrl = new StringBuilder(cmViewCourseControllerMapping);
        cmViewCourseUrl.append("?" + KRADConstants.DISPATCH_REQUEST_PARAMETER + "=").append(KRADConstants.START_METHOD);
        cmViewCourseUrl.append("&" + UifConstants.UrlParams.VIEW_ID + "=").append(CurriculumManagementConstants.CourseViewIds.VIEW_COURSE_VIEW);

        return cmViewCourseUrl.toString();
    }

}
