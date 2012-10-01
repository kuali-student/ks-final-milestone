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
 * Created by Charles on 7/9/12
 */
package org.kuali.student.enrollment.class2.courseofferingset.model;

import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.infc.SocRolloverResult;
import org.kuali.student.r2.common.assembler.DynAttrConverter;
import org.kuali.student.r2.common.assembler.DynamicAttrReadOnlyWrapper;
import org.kuali.student.r2.common.assembler.DynamicAttrWrapper;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SocRolloverResultDynAttrConverter {
    private static Map<String, DynamicAttrReadOnlyWrapper<SocRolloverResult>> _createDynAttrReadOnlyMap(SocRolloverResult result) {
        Map<String, DynamicAttrReadOnlyWrapper<SocRolloverResult>> dynAttrReadOnlyMap =
                new HashMap<String, DynamicAttrReadOnlyWrapper<SocRolloverResult>>();
        // Date initiated dyn attr
        dynAttrReadOnlyMap.put(CourseOfferingSetServiceConstants.DATE_INITIATED_RESULT_DYNATTR_KEY,
                new DynamicAttrReadOnlyWrapper<SocRolloverResult>(result, Date.class,
                        CourseOfferingSetServiceConstants.DATE_INITIATED_RESULT_DYNATTR_KEY) {
                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Date> dateConverter = (DynAttrConverter<Date>) TransformUtility.getConverterByClass(Date.class);
                        String dateStr = dateConverter.convertNativeValueToString(getDto().getDateInitiated());
                        return dateStr;
                    }
                });
        // Date completed dyn attr
        dynAttrReadOnlyMap.put(CourseOfferingSetServiceConstants.DATE_COMPLETED_RESULT_DYNATTR_KEY,
                new DynamicAttrReadOnlyWrapper<SocRolloverResult>(result, Date.class,
                        CourseOfferingSetServiceConstants.DATE_COMPLETED_RESULT_DYNATTR_KEY) {
                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Date> dateConverter = (DynAttrConverter<Date>) TransformUtility.getConverterByClass(Date.class);
                        String dateStr = dateConverter.convertNativeValueToString(getDto().getDateCompleted());
                        return dateStr;
                    }
                });
        // CO Created dyn attribute
        dynAttrReadOnlyMap.put(CourseOfferingSetServiceConstants.CO_CREATED_RESULT_DYNATTR_KEY,
                new DynamicAttrReadOnlyWrapper<SocRolloverResult>(result, Date.class,
                        CourseOfferingSetServiceConstants.CO_CREATED_RESULT_DYNATTR_KEY) {
                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getCourseOfferingsCreated());
                        return intStr;
                    }
                });
        // CO Created dyn attribute
        dynAttrReadOnlyMap.put(CourseOfferingSetServiceConstants.CO_SKIPPED_RESULT_DYNATTR_KEY,
                new DynamicAttrReadOnlyWrapper<SocRolloverResult>(result, Date.class,
                        CourseOfferingSetServiceConstants.CO_SKIPPED_RESULT_DYNATTR_KEY) {

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getCourseOfferingsSkipped());
                        return intStr;
                    }
                });
        // AO Created dyn attribute
        dynAttrReadOnlyMap.put(CourseOfferingSetServiceConstants.AO_CREATED_RESULT_DYNATTR_KEY,
                new DynamicAttrReadOnlyWrapper<SocRolloverResult>(result, Date.class,
                        CourseOfferingSetServiceConstants.AO_CREATED_RESULT_DYNATTR_KEY) {

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getActivityOfferingsCreated());
                        return intStr;
                    }
                });
        // AO Created dyn attribute
        dynAttrReadOnlyMap.put(CourseOfferingSetServiceConstants.AO_SKIPPED_RESULT_DYNATTR_KEY,
                new DynamicAttrReadOnlyWrapper<SocRolloverResult>(result, Date.class,
                        CourseOfferingSetServiceConstants.AO_SKIPPED_RESULT_DYNATTR_KEY) {

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getActivityOfferingsSkipped());
                        return intStr;
                    }
                });
        return dynAttrReadOnlyMap;
    }

    private static Map<String, DynamicAttrWrapper<SocRolloverResultInfo>> _createDynAttrMap(SocRolloverResultInfo result) {
        Map<String, DynamicAttrWrapper<SocRolloverResultInfo>> dynAttrMap = new HashMap<String, DynamicAttrWrapper<SocRolloverResultInfo>>();
        // Date initiated dyn attr
        dynAttrMap.put(CourseOfferingSetServiceConstants.DATE_INITIATED_RESULT_DYNATTR_KEY,
                new DynamicAttrWrapper<SocRolloverResultInfo>(result, Date.class,
                        CourseOfferingSetServiceConstants.DATE_INITIATED_RESULT_DYNATTR_KEY) {

                    @Override
                    public void readInStringValue(String attrValue) {
                        DynAttrConverter<Date> dateConverter = (DynAttrConverter<Date>) TransformUtility.getConverterByClass(Date.class);
                        Date convertedDate = dateConverter.convertStringValueToNative(attrValue);
                        getDto().setDateInitiated(convertedDate);
                    }

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Date> dateConverter = (DynAttrConverter<Date>) TransformUtility.getConverterByClass(Date.class);
                        String dateStr = dateConverter.convertNativeValueToString(getDto().getDateInitiated());
                        return dateStr;
                    }
                });
        // Date completed dyn attr
        dynAttrMap.put(CourseOfferingSetServiceConstants.DATE_COMPLETED_RESULT_DYNATTR_KEY,
                new DynamicAttrWrapper<SocRolloverResultInfo>(result, Date.class,
                        CourseOfferingSetServiceConstants.DATE_COMPLETED_RESULT_DYNATTR_KEY) {

                    @Override
                    public void readInStringValue(String attrValue) {
                        DynAttrConverter<Date> dateConverter = (DynAttrConverter<Date>) TransformUtility.getConverterByClass(Date.class);
                        Date convertedDate = dateConverter.convertStringValueToNative(attrValue);
                        getDto().setDateCompleted(convertedDate);
                    }

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Date> dateConverter = (DynAttrConverter<Date>) TransformUtility.getConverterByClass(Date.class);
                        String dateStr = dateConverter.convertNativeValueToString(getDto().getDateCompleted());
                        return dateStr;
                    }
                });
        // CO Created dyn attribute
        dynAttrMap.put(CourseOfferingSetServiceConstants.CO_CREATED_RESULT_DYNATTR_KEY,
                new DynamicAttrWrapper<SocRolloverResultInfo>(result, Date.class,
                        CourseOfferingSetServiceConstants.CO_CREATED_RESULT_DYNATTR_KEY) {

                    @Override
                    public void readInStringValue(String attrValue) {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        Integer intVal = intConverter.convertStringValueToNative(attrValue);
                        getDto().setCourseOfferingsCreated(intVal);
                    }

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getCourseOfferingsCreated());
                        return intStr;
                    }
                });
        // CO Created dyn attribute
        dynAttrMap.put(CourseOfferingSetServiceConstants.CO_SKIPPED_RESULT_DYNATTR_KEY,
                new DynamicAttrWrapper<SocRolloverResultInfo>(result, Date.class,
                        CourseOfferingSetServiceConstants.CO_SKIPPED_RESULT_DYNATTR_KEY) {

                    @Override
                    public void readInStringValue(String attrValue) {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        Integer intVal = intConverter.convertStringValueToNative(attrValue);
                        getDto().setCourseOfferingsSkipped(intVal);
                    }

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getCourseOfferingsSkipped());
                        return intStr;
                    }
                });
        // AO Created dyn attribute
        dynAttrMap.put(CourseOfferingSetServiceConstants.AO_CREATED_RESULT_DYNATTR_KEY,
                new DynamicAttrWrapper<SocRolloverResultInfo>(result, Date.class,
                        CourseOfferingSetServiceConstants.AO_CREATED_RESULT_DYNATTR_KEY) {

                    @Override
                    public void readInStringValue(String attrValue) {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        Integer intVal = intConverter.convertStringValueToNative(attrValue);
                        getDto().setActivityOfferingsCreated(intVal);
                    }

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getActivityOfferingsCreated());
                        return intStr;
                    }
                });
        // AO Created dyn attribute
        dynAttrMap.put(CourseOfferingSetServiceConstants.AO_SKIPPED_RESULT_DYNATTR_KEY,
                new DynamicAttrWrapper<SocRolloverResultInfo>(result, Date.class,
                        CourseOfferingSetServiceConstants.AO_SKIPPED_RESULT_DYNATTR_KEY) {

                    @Override
                    public void readInStringValue(String attrValue) {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        Integer intVal = intConverter.convertStringValueToNative(attrValue);
                        getDto().setActivityOfferingsSkipped(intVal);
                    }

                    @Override
                    public String writeOutStringValue() {
                        DynAttrConverter<Integer> intConverter = (DynAttrConverter<Integer>) TransformUtility.getConverterByClass(Integer.class);
                        String intStr = intConverter.convertNativeValueToString(getDto().getActivityOfferingsSkipped());
                        return intStr;
                    }
                });
        return dynAttrMap;
    }

    private static Map<String, SocRolloverResultAttributeEntity> _createEntityAttrMap(SocRolloverResultEntity entity) {
        Map<String, SocRolloverResultAttributeEntity> entityAttrMap = new HashMap<String, SocRolloverResultAttributeEntity>();
        if (entity.getAttributes() != null) {
            for (SocRolloverResultAttributeEntity attrEnt: entity.getAttributes()) {
                entityAttrMap.put(attrEnt.getKey(), attrEnt);
            }
        }
        return entityAttrMap;
    }

    public static void copyDtoDynAttrsToEntity(SocRolloverResult result, SocRolloverResultEntity entity) {
        Map<String, DynamicAttrReadOnlyWrapper<SocRolloverResult>> dynAttrMap = _createDynAttrReadOnlyMap(result);
        Map<String, SocRolloverResultAttributeEntity> entityAttrMap = _createEntityAttrMap(entity);
        for (String attrName: dynAttrMap.keySet()) {
            SocRolloverResultAttributeEntity attrEntity = entityAttrMap.get(attrName);
            DynamicAttrReadOnlyWrapper<SocRolloverResult> wrapper = dynAttrMap.get(attrName);
            if (attrEntity == null) {
                // Can't find this in the list of attributes, so create a new one
                AttributeInfo attr = new AttributeInfo(attrName, wrapper.writeOutStringValue());
                SocRolloverResultAttributeEntity newAttrEntity = new SocRolloverResultAttributeEntity(attr, entity);
                entity.getAttributes().add(newAttrEntity);
            } else {
                // Reuse the attribute
                attrEntity.setValue(wrapper.writeOutStringValue());
            }
        }
    }

    public static void copyEntityAttrsToDtoDynAttrs(SocRolloverResultEntity entity, SocRolloverResultInfo info) {
        Map<String, DynamicAttrWrapper<SocRolloverResultInfo>> dynAttrMap = _createDynAttrMap(info);
        Map<String, SocRolloverResultAttributeEntity> entityAttrMap = _createEntityAttrMap(entity);
        for (String attrName: dynAttrMap.keySet()) {
            SocRolloverResultAttributeEntity attrEntity = entityAttrMap.get(attrName);
            DynamicAttrWrapper<SocRolloverResultInfo> wrapper = dynAttrMap.get(attrName);
            if (attrEntity != null) {
                // Reuse the attribute
                wrapper.readInStringValue(attrEntity.getValue());
            }
        }
    }
}
