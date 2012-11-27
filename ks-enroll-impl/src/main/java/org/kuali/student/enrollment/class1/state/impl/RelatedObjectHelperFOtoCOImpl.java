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
 * Created by chongzhu on 11/27/12
 */
package org.kuali.student.enrollment.class1.state.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.state.service.RelatedObjectHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  * @Version 2.0
 *
 * @author Kuali Student Team
 */
public class RelatedObjectHelperFOtoCOImpl implements RelatedObjectHelper {

    private CourseOfferingService courseOfferingService;

    public RelatedObjectHelperFOtoCOImpl() {
    }

    protected CourseOfferingService getCourseOfferingService(){
        if (courseOfferingService == null){
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return  courseOfferingService;
    }

    @Override
    public Set<String> getRelatedObjectStateKeys(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Set<String> coStateKeySet = new HashSet<String>();

        coStateKeySet.add(getCourseOfferingInfoByFormatOfferingId(formatOfferingId, contextInfo).getStateKey());

        return coStateKeySet;
    }

    @Override
    public List<String> getRelatedObjectIds(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> coIdList = new ArrayList<String>();

        coIdList.add(getCourseOfferingInfoByFormatOfferingId(formatOfferingId, contextInfo).getId());

        return coIdList;
    }

    private CourseOfferingInfo getCourseOfferingInfoByFormatOfferingId(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        CourseOfferingInfo courseOfferingInfo = null;

        FormatOfferingInfo formatOfferingInfo = getCourseOfferingService().getFormatOffering(formatOfferingId, contextInfo);
        courseOfferingInfo = getCourseOfferingService().getCourseOffering(formatOfferingInfo.getCourseOfferingId(), contextInfo);

        return courseOfferingInfo;
    }
}
