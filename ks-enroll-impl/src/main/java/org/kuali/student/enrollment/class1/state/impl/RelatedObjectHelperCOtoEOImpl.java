/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 10/22/13
 */
package org.kuali.student.enrollment.class1.state.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class retrieves associated exam offerings of a given course offering
 * It is for performing the state propagations from course offering to exam offering
 *
 * @author Kuali Student Team
 */
public class RelatedObjectHelperCOtoEOImpl implements RelatedObjectHelper {
    private ExamOfferingService examOfferingService;
    private CourseOfferingService courseOfferingService;

    public RelatedObjectHelperCOtoEOImpl() {
    }

    @Override
    public Map<String, String> getRelatedObjectsIdAndState(String courseOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Map<String, String> idsAndStatesMap = new HashMap<String, String>();

        //retrieve the final exam driver of the CO that the AO is associated with
        String finalExamDriver = null;
        CourseOfferingInfo coInfo = this.getCourseOfferingService().getCourseOffering(courseOfferingId, contextInfo);
        if (coInfo != null) {
            finalExamDriver = coInfo.getAttributeValue(CourseOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR);
        }

        if (StringUtils.equals(finalExamDriver, LuServiceConstants.LU_EXAM_DRIVER_CO_KEY)) {
            List<FormatOfferingInfo> formatOfferingInfos = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);
            for (FormatOfferingInfo formatOfferingInfo : formatOfferingInfos) {
                List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(formatOfferingInfo.getId(), contextInfo);
                for(ExamOfferingRelationInfo eoRelation : eoRelations){
                    ExamOfferingInfo examOffering = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);

                    if (StringUtils.equals(examOffering.getAttributeValue(ExamOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR), "PER_CO")) {
                        idsAndStatesMap.put(examOffering.getId(), examOffering.getStateKey());
                    }
                }

            }
        }

        return idsAndStatesMap;
    }

    protected ExamOfferingService getExamOfferingService(){
        if (examOfferingService == null){
            examOfferingService = (ExamOfferingService) GlobalResourceLoader.getService(new QName(ExamOfferingServiceConstants.NAMESPACE, ExamOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  examOfferingService;
    }

    protected CourseOfferingService getCourseOfferingService(){
        if (courseOfferingService == null){
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  courseOfferingService;
    }
}
