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

import org.apache.log4j.Logger;
import org.kuali.student.core.enumerationmanagement.dto.EnumContextValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.core.enumerationmanagement.entity.ContextEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.entity.Enumeration;

import java.util.ArrayList;
import java.util.List;

public class EnumerationAssembler {

    final static Logger logger = Logger.getLogger(EnumerationAssembler.class);

    public static void toEnumeratedValueEntity(EnumeratedValueInfo info, EnumeratedValue entity) {
        entity.setAbbrevValue(info.getAbbrevValue());
        entity.setCode(info.getCode());
        entity.setSortKey(Integer.parseInt(info.getSortKey()));
        entity.setValue(info.getValue());
        entity.setEffectiveDate(info.getEffectiveDate());
        entity.setExpirationDate(info.getExpirationDate());

        List<EnumContextValueInfo> contextList = info.getContexts();
        List<ContextEntity> contextEntityList = new ArrayList<ContextEntity>();

        for (EnumContextValueInfo c : contextList) {
            ContextEntity contextEntity = new ContextEntity();
            contextEntity.setContextValue(c.getValue());
            contextEntity.setContextKey(c.getKey());
            contextEntityList.add(contextEntity);
        }
        entity.setContextEntityList(contextEntityList);
    }

    public static void toEnumeratedValueInfo(EnumeratedValue entity, EnumeratedValueInfo info) {

        info.setAbbrevValue(entity.getAbbrevValue());
        info.setCode(entity.getCode());
        info.setSortKey(String.valueOf(entity.getSortKey()));
        info.setValue(entity.getValue());
        info.setEffectiveDate(entity.getEffectiveDate());
        info.setExpirationDate(entity.getExpirationDate());
        info.setEnumerationKey(entity.getEnumeration().getId());

        List<ContextEntity> contextEntityList = entity.getContextEntityList();
        List<EnumContextValueInfo> contextList = new ArrayList<EnumContextValueInfo>();

        for (ContextEntity c : contextEntityList) {
            EnumContextValueInfo context = new EnumContextValueInfo();
            context.setValue(c.getContextValue());
            context.setKey(c.getContextKey());
            contextList.add(context);
        }
        info.setContexts(contextList);

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

    public static List<EnumerationInfo> toEnumerationMetaList(List<Enumeration> enumerationMetaEntityList) {

        List<EnumerationInfo> enumerationMetaList = new ArrayList<EnumerationInfo>();

        for (Enumeration enumerationMetaEntity : enumerationMetaEntityList) {
            EnumerationInfo eMeta = new EnumerationInfo();
            toEnumerationInfo(enumerationMetaEntity, eMeta);
            enumerationMetaList.add(eMeta);
        }

        return enumerationMetaList;
    }


    public static void toEnumerationInfo(Enumeration entity, EnumerationInfo info) {
        info.setDescr(entity.getDescr());
        info.setName(entity.getName());
        info.setEffectiveDate(entity.getEffectiveDate());
        info.setExpirationDate(entity.getExpirationDate());
        info.setId(entity.getId());
    }

    public static void toEnumeration(EnumerationInfo info, Enumeration entity) {
        entity.setDescr(info.getDescr());
        entity.setName(info.getName());
        entity.setEffectiveDate(info.getEffectiveDate());
        entity.setExpirationDate(info.getExpirationDate());
        entity.setId(info.getId());
    }
}
