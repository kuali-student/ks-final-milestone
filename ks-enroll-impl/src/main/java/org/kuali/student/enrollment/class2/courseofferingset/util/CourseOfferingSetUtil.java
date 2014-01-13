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
 * Created by David Yin on 1/10/14
 */
package org.kuali.student.enrollment.class2.courseofferingset.util;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class has utility methods for Course Offering Set
 *
 * @author Kuali Student Team
 */
public class CourseOfferingSetUtil {
    final static Logger LOG = Logger.getLogger(CourseOfferingSetUtil.class);
    private static CourseOfferingSetService socService;


    /**
     * This method retrieve the soc given a term id.
     *
     * @param termId   Term Id
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return return the soc if there is one and only one soc with the type kuali.soc.type.main. Return NULL if
     *         there are no socs, or no main soc, or more than one main soc given a term id.
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public static SocInfo getMainSocForTermId(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        int mainSocCount = 0;
        SocInfo mainSoc = null;

        List<String> socIds = getSocService().getSocIdsByTerm(termId, contextInfo);

        if (socIds!=null && !socIds.isEmpty()) {
            List<SocInfo> socInfos = getSocService().getSocsByIds(socIds, contextInfo);

            for (SocInfo socInfo : socInfos) {
                if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    mainSoc = socInfo;
                    mainSocCount++;

                    //There shouldn't be more than one main SOC of a given term
                    if (mainSocCount > 1) {
                        LOG.warn(String.format("More than one main SOCs were found for term [%s].", termId));
                        return null;
                    }
                }
            }
            return mainSoc;
        }

        return null;
    }

    public static CourseOfferingSetService getSocService() {
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    public static void setSocService(CourseOfferingSetService socService) {
        CourseOfferingSetUtil.socService = socService;
    }
}
