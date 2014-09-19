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
 * Created by David Yin on 9/11/14
 */
package org.kuali.student.enrollment.ges.service.decorators;

import org.apache.commons.lang3.StringUtils;
import org.kuali.student.core.constants.GesServiceConstants;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesService;
import org.kuali.student.core.ges.service.GesServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class performs GES evaluation for rollover
 *
 * @author Kuali Student Team
 */
public class GesServiceRolloverEvaluationDecorator extends GesServiceDecorator {
    private CourseService courseService;

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public ValueInfo evaluateValue(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , DoesNotExistException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        if (!parameterKey.startsWith(GesServiceConstants.PARAMETER_KEY_ROLLOVER_PREFIX)) {
            return getNextDecorator().evaluateValue(parameterKey, criteria, contextInfo);
        } else {
            return evaluateValueOnDate(parameterKey, criteria, pullEvaluationDate(contextInfo), contextInfo);
        }
    }

    @Override
    public ValueInfo evaluateValueOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo)
            throws InvalidParameterException
            , DoesNotExistException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {
        if (!parameterKey.startsWith(GesServiceConstants.PARAMETER_KEY_ROLLOVER_PREFIX)) {
            return getNextDecorator().evaluateValue(parameterKey, criteria, contextInfo);
        }

        if (StringUtils.isBlank(criteria.getAtpId())) {
            throw new MissingParameterException("atpId  is missing for gesCriteria to evaluate parameter: " + parameterKey);
        }

        List<ValueInfo> valueInfoList = this.evaluateValuesForParameterGroup(GesServiceConstants.GES_PARAMETER_GROUP_KEY_ROLLOVER, criteria, contextInfo);
        if (valueInfoList.isEmpty()) {
            throw new DoesNotExistException("No values are configured for the parameters of the parameter group: " + GesServiceConstants.GES_PARAMETER_GROUP_KEY_ROLLOVER);
        }

        Map<String, List<ValueInfo>> valueMap = generateGesEvaluationValueMap(valueInfoList);
        if (!valueMap.containsKey(parameterKey) || valueMap.get(parameterKey)==null || valueMap.get(parameterKey).isEmpty()) {
            throw new DoesNotExistException("Parameter: " + parameterKey + " is not active or no values are configured for it.");
        }

        CourseInfo courseInfo = getCourseService().getCourse(criteria.getCourseId(), contextInfo);
        boolean hasDefaultValue = false;
        int defaultValueIndex = 0;
        for (ValueInfo value : valueMap.get(parameterKey)) {
            if (StringUtils.isNotBlank(value.getCluId())
                    || StringUtils.isNotBlank(value.getSubjectCode())
                    || StringUtils.isNotBlank(value.getOrgId())) {
                if (StringUtils.equals(courseInfo.getId(), value.getCluId())
                        || StringUtils.equals(courseInfo.getSubjectArea(), value.getSubjectCode())
                        || StringUtils.equals(courseInfo.getUnitsContentOwner().get(0), value.getOrgId())) {
                    if (isValueActive(value, onDate)) {
                        return value;
                    }
                }
            } else {
                hasDefaultValue = true;
                defaultValueIndex = valueMap.get(parameterKey).indexOf(value);
                break;
            }
        }

        if (hasDefaultValue) {
            return valueMap.get(parameterKey).get(defaultValueIndex);
        } else {
            throw new DoesNotExistException("No values are applied to the course: " + criteria.getCourseId());
        }

    }

    private Date pullEvaluationDate(ContextInfo contextInfo) {
        Date currentDate = contextInfo.getCurrentDate();
        if (currentDate == null) {
            currentDate = new Date();
        }
        return currentDate;
    }

    private boolean isValueActive(ValueInfo value, Date date) {
        return (value.getEffectiveDate() == null || !date.before(value.getEffectiveDate())) &&
                        (value.getExpirationDate() == null || !date.after(value.getExpirationDate()));
    }

    private Map<String, List<ValueInfo>> generateGesEvaluationValueMap(List<ValueInfo> valueInfoList) {
        Map<String, List<ValueInfo>> gesEvaluationValueMap = new HashMap<String, List<ValueInfo>>();


        for (ValueInfo valueInfo : valueInfoList) {
            if (gesEvaluationValueMap.containsKey(valueInfo.getParameterKey())) {
                gesEvaluationValueMap.get(valueInfo.getParameterKey()).add(valueInfo);
            } else {
                List<ValueInfo> valuesForParamKey = new ArrayList<ValueInfo>();
                valuesForParamKey.add(valueInfo);
                gesEvaluationValueMap.put(valueInfo.getParameterKey(), valuesForParamKey);
            }
        }

        return gesEvaluationValueMap;
    }
}
