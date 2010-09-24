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

package org.kuali.student.lum.lrc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.lrc.dao.LrcDao;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.dto.ScaleInfo;
import org.kuali.student.lum.lrc.entity.LrcRichText;
import org.kuali.student.lum.lrc.entity.ResultComponent;
import org.kuali.student.lum.lrc.entity.ResultComponentAttribute;
import org.kuali.student.lum.lrc.entity.ResultComponentType;
import org.kuali.student.lum.lrc.entity.ResultValue;
import org.kuali.student.lum.lrc.entity.Scale;
import org.springframework.beans.BeanUtils;

public class LrcServiceAssembler extends BaseAssembler {
   

    public static ResultComponentInfo toResultComponentInfo(ResultComponent entity) {
        ResultComponentInfo dto = new ResultComponentInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "resultValues", "desc", "attributes", "type" });
        List<String> resultValues = new ArrayList<String>(entity.getResultValues().size());
        for (ResultValue rv : entity.getResultValues()) {
        	resultValues.add(rv.getValue());
        }
        dto.setDesc(toRichTextInfo(entity.getDescr()));
        dto.setResultValues(resultValues);
        dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));
        dto.setType(entity.getType().getId());
        return dto;
    }

    public static List<ResultComponentInfo> toReListComonentInfos(List<ResultComponent> entities) {
        List<ResultComponentInfo> dtos = new ArrayList<ResultComponentInfo>(entities.size());
        for (ResultComponent entity : entities) {
            dtos.add(toResultComponentInfo(entity));
        }
        return dtos;
    }

    public static ResultComponentTypeInfo toResultComponentTypeInfo(ResultComponentType entity) {
        ResultComponentTypeInfo dto = new ResultComponentTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] {"attributes" });
        dto.setAttributes(toAttributeMap(entity.getAttributes()));

        return dto;
    }
    public static List<ResultComponentTypeInfo> toResultComponentTypeInfos(List<ResultComponentType> entites) {
        List<ResultComponentTypeInfo> dtos = new ArrayList<ResultComponentTypeInfo>(entites.size());
        for (ResultComponentType entity : entites) {
            dtos.add(toResultComponentTypeInfo(entity));
        }
        return dtos;
    }


    public static ScaleInfo toScaleInfo(Scale entity) {
       ScaleInfo dto = new ScaleInfo();
       BeanUtils.copyProperties(entity, dto,
               new String[] { "desc", "attributes" });
       dto.setDesc(toRichTextInfo(entity.getDesc()));
       dto.setAttributes(toAttributeMap(entity.getAttributes()));
       return dto;
    }

    public static ResultComponent toResultComponent(String resultComponentTypeKey, ResultComponentInfo dto, LrcDao lrcDao) throws DoesNotExistException, InvalidParameterException, DataValidationErrorException {
        ResultComponent entity = new ResultComponent();
        toResultComponent(entity, dto, lrcDao);
        return entity;
    }

    public static void toResultComponent(ResultComponent entity, ResultComponentInfo dto, LrcDao lrcDao) throws DoesNotExistException, InvalidParameterException, DataValidationErrorException {
        BeanUtils.copyProperties(dto, entity,
                new String[] { "desc", "resultValues", "attributes", "metaInfo", "type" });
        ResultComponentType type = lrcDao.fetch(ResultComponentType.class, dto.getType());
        entity.setType(type);
        
        entity.setDescr(toRichText(LrcRichText.class, dto.getDesc()));

        //Create new Result Values, and delete unwanted ones, keep the overlap 
        List<ResultValue> resultValues = new ArrayList<ResultValue>(dto.getResultValues().size());
        Map<String,ResultValue> currentResultValues = new HashMap<String,ResultValue>();
        if(entity.getResultValues()!=null){
        	for(ResultValue resultValue:entity.getResultValues()){
        		currentResultValues.put(resultValue.getValue(),resultValue);
        	}
        }
        
        for(String value:dto.getResultValues()){
        	if(!currentResultValues.containsKey(value)){
        		ResultValue newResultValue = new ResultValue();
        		newResultValue.setValue(value);
        		newResultValue.setResultComponent(entity);
        		resultValues.add(newResultValue);
        	}else{
        		resultValues.add(currentResultValues.remove(value));
        	}
        }
        //Delete leftovers
        for(Entry<String,ResultValue> entry:currentResultValues.entrySet()){
        	lrcDao.delete(entry.getValue());
        }
        
        entity.setResultValues(resultValues);
        entity.setAttributes(toGenericAttributes(ResultComponentAttribute.class, dto.getAttributes(), entity, lrcDao));
    }
}
