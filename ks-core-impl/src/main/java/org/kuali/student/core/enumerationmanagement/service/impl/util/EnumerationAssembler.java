/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.core.enumerationmanagement.service.impl.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.kuali.student.core.enumerationmanagement.dto.EnumContextValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueFieldInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationMetaInfo;
import org.kuali.student.core.enumerationmanagement.dto.FieldDescriptorInfo;
import org.kuali.student.core.enumerationmanagement.entity.ContextEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueFieldEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumerationMetaEntity;

public class EnumerationAssembler {

    final static Logger logger = Logger.getLogger(EnumerationAssembler.class);
    
    public static void toEnumeratedValueEntity(EnumeratedValueInfo enumeratedValue, EnumeratedValueEntity enumeratedValueEntity) {
        try {
            BeanUtils.copyProperties(enumeratedValueEntity, enumeratedValue);
            List<EnumContextValueInfo> contextList = enumeratedValue.getContexts();
            List<ContextEntity> contextEntityList = new ArrayList<ContextEntity>();
            
            for (EnumContextValueInfo c : contextList) {
            	ContextEntity contextEntity = new ContextEntity();
            	contextEntity.setContextValue(c.getValue());
                contextEntity.setContextKey(c.getType());
                contextEntityList.add(contextEntity);
            }
            enumeratedValueEntity.setContextEntityList(contextEntityList);
        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }
    }

    public static void toEnumeratedValueInfo(EnumeratedValueEntity enumeratedValueEntity, EnumeratedValueInfo enumeratedValue) {
        try {
            BeanUtils.copyProperties(enumeratedValue, enumeratedValueEntity);
            List<ContextEntity> contextEntityList = enumeratedValueEntity.getContextEntityList();
            List<EnumContextValueInfo> contextList = new ArrayList<EnumContextValueInfo>();

            for (ContextEntity c : contextEntityList) {
            	EnumContextValueInfo context = new EnumContextValueInfo();
                context.setValue(c.getContextValue());
                context.setType(c.getContextKey());
                contextList.add(context);
            }
            enumeratedValue.setContexts(contextList);
        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }

    }

    public static List<EnumeratedValueInfo> toEnumeratedValueList(List<EnumeratedValueEntity> enumeratedValueEntityList) {

        List<EnumeratedValueInfo> enumeratedValueList = new ArrayList<EnumeratedValueInfo>();

        for (EnumeratedValueEntity enumeratedValueEntity : enumeratedValueEntityList) {
        	EnumeratedValueInfo eValue = new EnumeratedValueInfo();
            toEnumeratedValueInfo(enumeratedValueEntity, eValue);
            enumeratedValueList.add(eValue);
        }
        return enumeratedValueList;

    }
    
    public static List<EnumerationMetaInfo> toEnumerationMetaList(List<EnumerationMetaEntity>enumerationMetaEntityList){
        
        List<EnumerationMetaInfo> enumerationMetaList = new ArrayList<EnumerationMetaInfo>();
         
        for(EnumerationMetaEntity enumerationMetaEntity :enumerationMetaEntityList ){
        	EnumerationMetaInfo eMeta = new EnumerationMetaInfo();
            toEnumeratedMetaInfo(enumerationMetaEntity,eMeta);
            enumerationMetaList.add(eMeta);
        }
        
        return enumerationMetaList;
    }
    

    public static void toEnumeratedValueFieldInfo(EnumeratedValueFieldEntity enumeratedValueFieldEntity, EnumeratedValueFieldInfo enumeratedValueField) {
        enumeratedValueField.setId(enumeratedValueFieldEntity.getFieldKey());
        enumeratedValueField.setMaxOccurs(enumeratedValueFieldEntity.getMaxOccurs()); // enumeratedValueField.setMaxOccurs(String)
                                                                                                            // should be Integer
        enumeratedValueField.setMinOccurs(enumeratedValueFieldEntity.getMinOccurs());
        FieldDescriptorInfo fieldDescriptor = new FieldDescriptorInfo();
        fieldDescriptor.setMinLength((Integer)enumeratedValueFieldEntity.getMinLength());
        fieldDescriptor.setMaxLength(enumeratedValueFieldEntity.getMaxLength());
        fieldDescriptor.setDataType(enumeratedValueFieldEntity.getDataType());
        fieldDescriptor.setValidChars(enumeratedValueFieldEntity.getValidChars());
        fieldDescriptor.setInvalidChars(enumeratedValueFieldEntity.getInvalidChars());
        fieldDescriptor.setMinValue(enumeratedValueFieldEntity.getMinValue());
        fieldDescriptor.setMaxValue(enumeratedValueFieldEntity.getMaxValue());
        enumeratedValueField.setFieldDescriptor(fieldDescriptor);
    }
    


    public static void toEnumeratedValueFieldEntity(EnumeratedValueFieldInfo enumeratedValueField, EnumeratedValueFieldEntity enumeratedValueFieldEntity) {

        enumeratedValueFieldEntity.setFieldKey(enumeratedValueField.getId());
        enumeratedValueFieldEntity.setMaxOccurs(enumeratedValueField.getMaxOccurs());
        enumeratedValueFieldEntity.setMinOccurs(enumeratedValueField.getMinOccurs());
        enumeratedValueFieldEntity.setMinLength(enumeratedValueField.getFieldDescriptor().getMinLength());
        enumeratedValueFieldEntity.setMaxLength(enumeratedValueField.getFieldDescriptor().getMaxLength());
        enumeratedValueFieldEntity.setDataType(enumeratedValueField.getFieldDescriptor().getDataType());
        enumeratedValueFieldEntity.setValidChars(enumeratedValueField.getFieldDescriptor().getValidChars());
        enumeratedValueFieldEntity.setInvalidChars(enumeratedValueField.getFieldDescriptor().getInvalidChars());
        enumeratedValueFieldEntity.setMinValue(enumeratedValueField.getFieldDescriptor().getMinValue());
        enumeratedValueFieldEntity.setMaxValue(enumeratedValueField.getFieldDescriptor().getMaxValue());
    }

    public static void toEnumeratedMetaInfo(EnumerationMetaEntity enumerationMetaEntity, EnumerationMetaInfo enumerationMeta) {
        try {
            BeanUtils.copyProperties(enumerationMeta, enumerationMetaEntity);
            enumerationMeta.setDesc(enumerationMetaEntity.getEnumerationMetaKeyDesc());
            enumerationMeta.setId(enumerationMetaEntity.getEnumerationKey());
            List<EnumeratedValueFieldInfo> enumeratedValueFieldList = new ArrayList<EnumeratedValueFieldInfo>();
            EnumeratedValueFieldInfo eValueField;
            List<EnumeratedValueFieldEntity> eValueFieldEntityList = enumerationMetaEntity.getEnumeratedValueFieldList();
            for (EnumeratedValueFieldEntity eValueFieldEntity : eValueFieldEntityList) {
                eValueField = new EnumeratedValueFieldInfo();
                toEnumeratedValueFieldInfo(eValueFieldEntity, eValueField);
                enumeratedValueFieldList.add(eValueField);
            }

            enumerationMeta.setEnumeratedValueFields(enumeratedValueFieldList);

        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }

    }

    public static void toEnumeratedMetaEntity(EnumerationMetaInfo enumerationMeta, EnumerationMetaEntity enumerationMetaEntity) {
        try {
            BeanUtils.copyProperties(enumerationMetaEntity, enumerationMeta);
            enumerationMetaEntity.setEnumerationMetaKeyDesc(enumerationMeta.getDesc());
            enumerationMetaEntity.setEnumerationKey(enumerationMeta.getId());

            List<EnumeratedValueFieldEntity> eValueFieldEntityList = new ArrayList<EnumeratedValueFieldEntity>();
            EnumeratedValueFieldEntity enumeratedValueFieldEntity;


            List<EnumeratedValueFieldInfo> enumeratedValueFieldList = enumerationMeta.getEnumeratedValueFields();
            for (EnumeratedValueFieldInfo eValueField : enumeratedValueFieldList) {
                enumeratedValueFieldEntity = new EnumeratedValueFieldEntity();
                toEnumeratedValueFieldEntity(eValueField, enumeratedValueFieldEntity);
                eValueFieldEntityList.add(enumeratedValueFieldEntity);
            }
            enumerationMetaEntity.setEnumeratedValueFieldList(eValueFieldEntityList);
        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }

    }
}
