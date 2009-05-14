package org.kuali.student.core.enumerationmanagement.service.impl.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.student.core.dictionary.dto.Context;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueField;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueFields;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationMeta;
import org.kuali.student.core.enumerationmanagement.entity.ContextEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueFieldEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumerationMetaEntity;

public class EnumerationAssembler {

    public static void toEnumeratedValueEntity(EnumeratedValue enumeratedValue, EnumeratedValueEntity enumeratedValueEntity) {
        try {
            BeanUtils.copyProperties(enumeratedValueEntity, enumeratedValue);
            List<Context> contextList = enumeratedValue.getContexts();
            List<ContextEntity> contextEntityList = new ArrayList<ContextEntity>();
            ContextEntity contextEntity = new ContextEntity();
         
            for (Context c : contextList) {
                contextEntity.setContextValue(c.getValue());
                contextEntity.setContextKey(c.getType());
                contextEntityList.add(contextEntity);
            }
            enumeratedValueEntity.setContextEntityList(contextEntityList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void toEnumeratedValue(EnumeratedValueEntity enumeratedValueEntity, EnumeratedValue enumeratedValue) {
        try {
            BeanUtils.copyProperties(enumeratedValue, enumeratedValueEntity);
            List<ContextEntity> contextEntityList = enumeratedValueEntity.getContextEntityList();
            List<Context> contextList = new ArrayList<Context>();
            Context context;
            for (ContextEntity c : contextEntityList) {
                context = new Context();
                context.setValue(c.getContextValue());
                context.setType(c.getContextKey());
                contextList.add(context);
            }
            enumeratedValue.setContexts(contextList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static List<EnumeratedValue> toEnumeratedValueList(List<EnumeratedValueEntity> enumeratedValueEntityList, Class<EnumeratedValue> enumeratedValue) {

        List<EnumeratedValue> enumeratedValueList = new ArrayList<EnumeratedValue>();
        EnumeratedValue eValue;
        for (EnumeratedValueEntity enumeratedValueEntity : enumeratedValueEntityList) {
            eValue = new EnumeratedValue();
            toEnumeratedValue(enumeratedValueEntity, eValue);
            enumeratedValueList.add(eValue);
        }
        return enumeratedValueList;

    }
    
    public static List<EnumerationMeta> toEnumerationMetaList(List<EnumerationMetaEntity>enumerationMetaEntityList, Class<EnumerationMeta> enumerationMeta ){
        
        List<EnumerationMeta> enumerationMetaList = new ArrayList<EnumerationMeta>();
        EnumerationMeta eMeta;
        for(EnumerationMetaEntity enumerationMetaEntity :enumerationMetaEntityList ){
            eMeta = new EnumerationMeta();
            toEnumeratedMeta(enumerationMetaEntity,eMeta);
            enumerationMetaList.add(eMeta);
        }
        
        return enumerationMetaList;
    }
    

    public static void toEnumeratedValueField(EnumeratedValueFieldEntity enumeratedValueFieldEntity, EnumeratedValueField enumeratedValueField) {
        enumeratedValueField.setKey(enumeratedValueFieldEntity.getFieldKey());
        enumeratedValueField.setMaxOccurs(enumeratedValueFieldEntity.getMaxOccurs()); // enumeratedValueField.setMaxOccurs(String)
                                                                                                            // should be Integer
        enumeratedValueField.setMinOccurs(enumeratedValueFieldEntity.getMinOccurs());
        FieldDescriptor fieldDescriptor = new FieldDescriptor();
        fieldDescriptor.setMinLength((Integer)enumeratedValueFieldEntity.getMinLength());
        fieldDescriptor.setMaxLength((Integer)enumeratedValueFieldEntity.getMaxLength());
        fieldDescriptor.setDataType(enumeratedValueFieldEntity.getDataType());
        fieldDescriptor.setValidChars(enumeratedValueFieldEntity.getValidChars());
        fieldDescriptor.setInvalidChars(enumeratedValueFieldEntity.getInvalidChars());
        fieldDescriptor.setMinValue(enumeratedValueFieldEntity.getMinValue());
        fieldDescriptor.setMaxValue(enumeratedValueFieldEntity.getMaxValue());
        enumeratedValueField.setFieldDescriptor(fieldDescriptor);
    }
    


    public static void toEnumeratedValueFieldEntity(EnumeratedValueField enumeratedValueField, EnumeratedValueFieldEntity enumeratedValueFieldEntity) {

        enumeratedValueFieldEntity.setFieldKey(enumeratedValueField.getKey());
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

    public static void toEnumeratedMeta(EnumerationMetaEntity enumerationMetaEntity, EnumerationMeta enumerationMeta) {
        try {
            BeanUtils.copyProperties(enumerationMeta, enumerationMetaEntity);
            enumerationMeta.setDesc(enumerationMetaEntity.getEnumerationMetaKeyDesc());
            enumerationMeta.setKey(enumerationMetaEntity.getEnumerationKey());
            List<EnumeratedValueField> enumeratedValueFieldList = new ArrayList<EnumeratedValueField>();
            EnumeratedValueField eValueField;
            List<EnumeratedValueFieldEntity> eValueFieldEntityList = enumerationMetaEntity.getEnumeratedValueFieldList();
            for (EnumeratedValueFieldEntity eValueFieldEntity : eValueFieldEntityList) {
                eValueField = new EnumeratedValueField();
                toEnumeratedValueField(eValueFieldEntity, eValueField);
                enumeratedValueFieldList.add(eValueField);
            }
            EnumeratedValueFields enumeratedValueFields = new EnumeratedValueFields();
            enumeratedValueFields.setEnumeratedValueField(enumeratedValueFieldList);
            enumerationMeta.setEnumeratedValueFields(enumeratedValueFields);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static void toEnumeratedMetaEntity(EnumerationMeta enumerationMeta, EnumerationMetaEntity enumerationMetaEntity) {
        try {
            BeanUtils.copyProperties(enumerationMetaEntity, enumerationMeta);
            enumerationMetaEntity.setEnumerationMetaKeyDesc(enumerationMeta.getDesc());
            enumerationMetaEntity.setEnumerationKey(enumerationMeta.getKey());

            List<EnumeratedValueFieldEntity> eValueFieldEntityList = new ArrayList<EnumeratedValueFieldEntity>();
            EnumeratedValueFieldEntity enumeratedValueFieldEntity;

            EnumeratedValueFields enumeratedValueFields = enumerationMeta.getEnumeratedValueFields();
            List<EnumeratedValueField> enumeratedValueFieldList = enumeratedValueFields.getEnumeratedValueField();
            for (EnumeratedValueField eValueField : enumeratedValueFieldList) {
                enumeratedValueFieldEntity = new EnumeratedValueFieldEntity();
                toEnumeratedValueFieldEntity(eValueField, enumeratedValueFieldEntity);
                eValueFieldEntityList.add(enumeratedValueFieldEntity);
            }
            enumerationMetaEntity.setEnumeratedValueFieldList(eValueFieldEntityList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
