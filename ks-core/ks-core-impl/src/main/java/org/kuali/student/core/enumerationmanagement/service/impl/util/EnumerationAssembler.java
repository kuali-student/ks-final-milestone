/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.core.enumerationmanagement.entity.ContextEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.entity.Enumeration;

public class EnumerationAssembler {

    final static Logger logger = Logger.getLogger(EnumerationAssembler.class);
    
    public static void toEnumeratedValueEntity(EnumeratedValueInfo enumeratedValue, EnumeratedValue enumeratedValueEntity) {
        try {
            BeanUtils.copyProperties(enumeratedValueEntity, enumeratedValue);
            List<EnumContextValueInfo> contextList = enumeratedValue.getContexts();
            List<ContextEntity> contextEntityList = new ArrayList<ContextEntity>();
            
            for (EnumContextValueInfo c : contextList) {
            	ContextEntity contextEntity = new ContextEntity();
            	contextEntity.setContextValue(c.getValue());
                contextEntity.setContextKey(c.getKey());
                contextEntityList.add(contextEntity);
            }
            enumeratedValueEntity.setContextEntityList(contextEntityList);
        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }
    }

    public static void toEnumeratedValueInfo(EnumeratedValue enumeratedValueEntity, EnumeratedValueInfo enumeratedValue) {
        try {
            BeanUtils.copyProperties(enumeratedValue, enumeratedValueEntity);
            List<ContextEntity> contextEntityList = enumeratedValueEntity.getContextEntityList();
            List<EnumContextValueInfo> contextList = new ArrayList<EnumContextValueInfo>();

            for (ContextEntity c : contextEntityList) {
            	EnumContextValueInfo context = new EnumContextValueInfo();
                context.setValue(c.getContextValue());
                context.setKey(c.getContextKey());
                contextList.add(context);
            }
            enumeratedValue.setContexts(contextList);
        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }

    }

    public static List<EnumeratedValueInfo> toEnumeratedValueList(List<EnumeratedValue> enumeratedValueEntityList) {

        List<EnumeratedValueInfo> enumeratedValueList = new ArrayList<EnumeratedValueInfo>();

        for (EnumeratedValue enumeratedValueEntity : enumeratedValueEntityList) {
        	EnumeratedValueInfo eValue = new EnumeratedValueInfo();
            toEnumeratedValueInfo(enumeratedValueEntity, eValue);
            enumeratedValueList.add(eValue);
        }
        return enumeratedValueList;

    }
    
    public static List<EnumerationInfo> toEnumerationMetaList(List<Enumeration>enumerationMetaEntityList){
        
        List<EnumerationInfo> enumerationMetaList = new ArrayList<EnumerationInfo>();
         
        for(Enumeration enumerationMetaEntity :enumerationMetaEntityList ){
        	EnumerationInfo eMeta = new EnumerationInfo();
            toEnumerationInfo(enumerationMetaEntity,eMeta);
            enumerationMetaList.add(eMeta);
        }
        
        return enumerationMetaList;
    }
        

    public static void toEnumerationInfo(Enumeration enumerationEntity, EnumerationInfo enumerationMeta) {
        try {
            BeanUtils.copyProperties(enumerationMeta, enumerationEntity);
            enumerationMeta.setId(enumerationEntity.getKey());
        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }

    }

    public static void toEnumeration(EnumerationInfo enumerationMeta, Enumeration enumerationEntity) {
        try {
            BeanUtils.copyProperties(enumerationEntity, enumerationMeta);
            enumerationEntity.setKey(enumerationMeta.getId());
        } catch (IllegalAccessException e) {
            logger.error("Exception occured: ", e);
        } catch (InvocationTargetException e) {
            logger.error("Exception occured: ", e);
        }

    }
}
