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
 * Created by venkat on 1/23/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class to transform DTOs between <code>LuiSet</code> class1 and <code>ColocatedOfferingSetInfo</code> class2 structure.
 *
 * @author Kuali Student Team
 */
public class ColocatedOfferingSetTransformer {

    private final static Logger LOG = Logger.getLogger(ColocatedOfferingSetTransformer.class);

    /**
     * This method converts <code>ColocatedOfferingSetInfo</code> class2 DTO to <code>LuiSetInfo</code> class1 DTO.
     *
     * @param luiSetInfo class1 dto to be transformed
     * @param colocatedOfferingSetInfo class2 dto
     * @throws InvalidParameterException if either luiSetInfo or colocatedOfferingSetInfo parameter is null.
     */
    /* TODOSSR     public void luiSet2ColocatedOfferingSet(LuiSetInfo luiSetInfo,ColocatedOfferingSetInfo colocatedOfferingSetInfo)
    throws InvalidParameterException {

        if (luiSetInfo == null || colocatedOfferingSetInfo == null){
            throw new InvalidParameterException("One of the parameters is empty");
        }

        LOG.debug("Building ColocatedOfferingSet from LuiSet");

        colocatedOfferingSetInfo.setId(luiSetInfo.getId());
        colocatedOfferingSetInfo.setName(luiSetInfo.getName());
        colocatedOfferingSetInfo.setStateKey(luiSetInfo.getStateKey());
        colocatedOfferingSetInfo.setTypeKey(luiSetInfo.getTypeKey());
        colocatedOfferingSetInfo.setDescr(luiSetInfo.getDescr());
        colocatedOfferingSetInfo.setMeta(luiSetInfo.getMeta());
        colocatedOfferingSetInfo.setEffectiveDate(luiSetInfo.getEffectiveDate());
        colocatedOfferingSetInfo.setExpirationDate(luiSetInfo.getExpirationDate());
        if (colocatedOfferingSetInfo.getActivityOfferingIds() == null){
            colocatedOfferingSetInfo.setActivityOfferingIds(new ArrayList<String>());
        }
        colocatedOfferingSetInfo.getActivityOfferingIds().addAll(luiSetInfo.getLuiIds());

        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        for (AttributeInfo attribute : luiSetInfo.getAttributes()){
            if (StringUtils.equals(attribute.getKey(),CourseOfferingServiceConstants.COLOCATED_SET_MAXIMUM_ENROLLMENT_ATTR)){
                colocatedOfferingSetInfo.setMaximumEnrollment(NumberUtils.toInt(attribute.getValue()));
            } else if (StringUtils.equals(attribute.getKey(),CourseOfferingServiceConstants.IS_MAX_ENROLLMENT_SHARED_ATTR)){
                colocatedOfferingSetInfo.setIsMaxEnrollmentShared(BooleanUtils.toBoolean(attribute.getValue()));
            } else {
                attributes.add(new AttributeInfo(attribute));
            }
        }

        colocatedOfferingSetInfo.setAttributes(attributes);
    }

    *//**
     * This method converts <code>LuiSetInfo</code> class1 DTO to <code>ColocatedOfferingSetInfo</code> class2 DTO.
     *
     * @param colocatedOfferingSetInfo class2 DTO to be transformed
     * @param luiSetInfo class1 DTO
     * @throws InvalidParameterException if either luiSetInfo or colocatedOfferingSetInfo parameter is null.
     *//*
    public void colocatedOfferingSet2LuiSet(ColocatedOfferingSetInfo colocatedOfferingSetInfo,LuiSetInfo luiSetInfo)
    throws InvalidParameterException {

        if (luiSetInfo == null || colocatedOfferingSetInfo == null){
            throw new InvalidParameterException("One of the parameters is empty");
        }

        LOG.debug("Building LuiSet from ColocatedOfferingSet");

        luiSetInfo.setId(colocatedOfferingSetInfo.getId());
        luiSetInfo.setName(colocatedOfferingSetInfo.getName());
        luiSetInfo.setStateKey(colocatedOfferingSetInfo.getStateKey());
        luiSetInfo.setTypeKey(colocatedOfferingSetInfo.getTypeKey());
        luiSetInfo.setDescr(colocatedOfferingSetInfo.getDescr());
        luiSetInfo.setMeta(colocatedOfferingSetInfo.getMeta());
        luiSetInfo.setEffectiveDate(colocatedOfferingSetInfo.getEffectiveDate());
        luiSetInfo.setExpirationDate(colocatedOfferingSetInfo.getExpirationDate());
        luiSetInfo.getLuiIds().addAll(colocatedOfferingSetInfo.getActivityOfferingIds());

        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        for (Attribute attr : colocatedOfferingSetInfo.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }

        AttributeInfo isMaxEnrollmentSharedAttr = new AttributeInfo();
        isMaxEnrollmentSharedAttr.setKey(CourseOfferingServiceConstants.IS_MAX_ENROLLMENT_SHARED_ATTR);
        if (colocatedOfferingSetInfo.getIsMaxEnrollmentShared() != null){
            isMaxEnrollmentSharedAttr.setValue(colocatedOfferingSetInfo.getIsMaxEnrollmentShared().toString());
        }
        attributes.add(isMaxEnrollmentSharedAttr);

        AttributeInfo maxEnrollmentAttr = new AttributeInfo();
        maxEnrollmentAttr.setKey(CourseOfferingServiceConstants.COLOCATED_SET_MAXIMUM_ENROLLMENT_ATTR);
        if (colocatedOfferingSetInfo.getMaximumEnrollment() != null){
            maxEnrollmentAttr.setValue(colocatedOfferingSetInfo.getMaximumEnrollment().toString());
        }
        attributes.add(maxEnrollmentAttr);

        luiSetInfo.setAttributes(attributes);
    }*/
}
