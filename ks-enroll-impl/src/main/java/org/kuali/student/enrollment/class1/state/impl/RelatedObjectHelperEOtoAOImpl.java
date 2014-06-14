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
 * Created by David Yin on 10/20/13
 */
package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
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
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class retrieves the associated activity offerings given an exam offering
 * It is for processing exam offering state constraints
 *
 * @author Kuali Student Team
 */
public class RelatedObjectHelperEOtoAOImpl implements RelatedObjectHelper {
    private ExamOfferingService examOfferingService;
    private CourseOfferingService courseOfferingService;

    public RelatedObjectHelperEOtoAOImpl() {
    }

    @Override
    public Map<String, String> getRelatedObjectsIdAndState(String examOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Map<String, String> idsAndStatesMap = new HashMap<String, String>();
        List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByExamOffering(examOfferingId, contextInfo);
        if(eoRelations==null || eoRelations.isEmpty()){
            return idsAndStatesMap;
        }

        List<String> activityOfferingIds = new ArrayList<String>();

        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            activityOfferingIds.addAll(eoRelation.getActivityOfferingIds());
        }

        if (activityOfferingIds!=null && !activityOfferingIds.isEmpty()) {
            List<ActivityOfferingInfo> aoInfoList = getCourseOfferingService().getActivityOfferingsByIds(activityOfferingIds,contextInfo);
            for(ActivityOfferingInfo activityOfferingInfo : aoInfoList) {
                idsAndStatesMap.put(activityOfferingInfo.getId(),activityOfferingInfo.getStateKey());
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
