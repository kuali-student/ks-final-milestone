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
 * Created by venkat on 11/27/12
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements RelatedObjectHelper to provide relation logic for Format Offerings and Activity Offerings
 *
 * @author Kuali Student Team
 */
public class RelatedObjectHelperFOtoEOImpl implements RelatedObjectHelper {

    private ExamOfferingService examOfferingService;

    @Override
    public Map<String, String> getRelatedObjectsIdAndState(String formatOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Map<String, String> idsAndStatesMap = new HashMap<String, String>();

        List<ExamOfferingRelationInfo> eoRelations = this.getExamOfferingService().getExamOfferingRelationsByFormatOffering(formatOfferingId, contextInfo);
        for(ExamOfferingRelationInfo eoRelation : eoRelations){
            ExamOfferingInfo examOffering = this.getExamOfferingService().getExamOffering(eoRelation.getExamOfferingId(), contextInfo);
            idsAndStatesMap.put(examOffering.getId(), examOffering.getStateKey());
        }

        return idsAndStatesMap;
    }

    protected ExamOfferingService getExamOfferingService(){
        if (examOfferingService == null){
            examOfferingService = (ExamOfferingService) GlobalResourceLoader.getService(new QName(ExamOfferingServiceConstants.NAMESPACE, ExamOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  examOfferingService;
    }

}
