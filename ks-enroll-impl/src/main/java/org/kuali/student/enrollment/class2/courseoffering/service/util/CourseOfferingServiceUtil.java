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
 * Created by David Yin on 7/30/14
 */
package org.kuali.student.enrollment.class2.courseoffering.service.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import java.util.List;

/**
 * This class provides util methods for enrollment services
 *
 * @author Kuali Student Team
 */
public class CourseOfferingServiceUtil {

    //return true when attribute value has been changed or new attribute has been added
    public static boolean mergeAttribute(List<AttributeInfo> attributes, String attrKey, String attrValue) {
        AttributeInfo attributeInfo = getAttributeForKey(attributes, attrKey);

        if (attributeInfo != null) {
            if (StringUtils.equals(attrValue, attributeInfo.getValue())) {
                return false;
            } else {
                attributeInfo.setValue(attrValue);
            }
        } else {
            AttributeInfo newAttr = new AttributeInfo();
            newAttr.setKey(attrKey);
            newAttr.setValue(attrValue);
            attributes.add(newAttr);
        }
        return true;
    }

    private static AttributeInfo getAttributeForKey(List<AttributeInfo> attributeInfos, String key) {
        for (AttributeInfo info : attributeInfos) {
            if (info.getKey().equals(key)) {
                return info;
            }
        }
        return null;
    }

    public static boolean evaluateRolloverGesParameter (String parameterKey, String atpId, String courseId, GesService gesService, ContextInfo context)
            throws InvalidParameterException,
            DoesNotExistException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        GesCriteriaInfo criteria = new GesCriteriaInfo();
        criteria.setAtpId(atpId);
        criteria.setCourseId(courseId);

        ValueInfo value = gesService.evaluateValue(parameterKey, criteria, context);
        return value.getBooleanValue();
    }


}
