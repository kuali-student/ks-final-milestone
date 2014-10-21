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
 * Created by venkat on 7/24/14
 */
package org.kuali.student.cm.common.util;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;

/**
 * This class has some common util methods available across CM.
 *
 * @author Kuali Student Team
 */
public class CMUtils {


    private static CourseService courseService;
    private static CluService cluService;
    private static ProposalService proposalService;
    private static AtpService atpService;
    private static Object lockObject = new Object();
    /**
     * This method returns the url for CM Home.
     *
     * @return url for CM home view
     */
    public static String getCMHomeUrl(){

        String cmHomeControllerMapping = CurriculumManagementConstants.ControllerRequestMappings.CM_HOME.replaceFirst("/", "");

        StringBuilder cmHomeUrl = new StringBuilder(cmHomeControllerMapping);
        cmHomeUrl.append("?" + KRADConstants.DISPATCH_REQUEST_PARAMETER + "=").append(KRADConstants.START_METHOD);
        cmHomeUrl.append("&" + UifConstants.UrlParams.VIEW_ID + "=").append(CurriculumManagementConstants.CourseViewIds.CM_HOME_VIEW);

        return cmHomeUrl.toString();
    }

    public static CourseService getCourseService() {
        return  (CourseService) CMUtils.getService(CMUtils.courseService, CourseServiceConstants.COURSE_NAMESPACE, CourseServiceConstants.SERVICE_NAME_LOCAL_PART);
    }

    public static CluService getCluService() {
        return  (CluService) CMUtils.getService(CMUtils.cluService, CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART);
    }

    public static AtpService getAtpService() {
        return  (AtpService) CMUtils.getService(CMUtils.atpService, AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART);
    }

    public static <T extends Object> T getService(T service, String nameSpace, String localPart) {
        if (service == null) {
            // 1. Synchronization overhead is incurred only when service == null, just first initialization
            // 2. Perhaps it is better to have lock object per service in case lookup blocks other threads,
            // but current impl of GlobalResourceLoader initializes by the time App context is up, so won't block
            synchronized(CMUtils.lockObject) {
                if (service == null) {
                    service = GlobalResourceLoader.getService(new QName(nameSpace, localPart));
                }
            }
        }
        return service;
    }

    public static ProposalService getProposalService() {
        if (proposalService == null) {
            proposalService = (ProposalService) GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return proposalService;
    }

}
