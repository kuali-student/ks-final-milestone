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

package org.kuali.student.r1.lum.lrc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.r1.lum.lrc.entity.ResultComponent;
import org.kuali.student.r1.lum.lrc.entity.ResultValue;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.service.util.AssemblerHelper;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r1.common.service.impl.BaseAssembler;
import org.kuali.student.r1.lum.lrc.dao.LrcDao;
import org.kuali.student.r1.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.r1.lum.lrc.dto.ScaleInfo;
import org.kuali.student.r1.lum.lrc.entity.LrcRichText;
import org.kuali.student.r1.lum.lrc.entity.ResultComponent;
import org.kuali.student.r1.lum.lrc.entity.ResultComponentAttribute;
import org.kuali.student.r1.lum.lrc.entity.ResultComponentType;
import org.kuali.student.r1.lum.lrc.entity.ResultValue;
import org.kuali.student.r1.lum.lrc.entity.Scale;
import org.springframework.beans.BeanUtils;

public class LrcServiceAssembler extends BaseAssembler {
   

    public static ResultValuesGroupInfo toResultValuesGroupInfo(ResultComponent entity) throws DoesNotExistException {
        ResultValuesGroupInfo dto = new ResultValuesGroupInfo();

        List<String> resultValueKeys = new ArrayList<String>(entity.getResultValues().size());
        for (ResultValue rv : entity.getResultValues()) {
            String rvId = rv.getResultComponent().getId();
            if (rvId.startsWith("kuali.creditType.credit.degree.")) {
                resultValueKeys.add("kuali.result.value.credit.degree." + rv.getValue());
            } else if (rvId.equals("kuali.resultComponent.grade.letter")) {
                resultValueKeys.add("kuali.result.value.grade.letter." + rv.getValue().toLowerCase());
            } else if (rvId.equals("kuali.resultComponent.grade.passFail")) {
                resultValueKeys.add("kuali.result.value.grade.pf." + rv.getValue().substring(0, 1).toLowerCase());
            } else {
                throw new DoesNotExistException("No mapping exists for Result Values with Component " + rvId);
            }
        }
        dto.setKey(entity.getId());
        dto.setDescr(toRichTextInfo(entity.getDescr()));
        dto.setResultValueKeys(resultValueKeys);
        dto.setAttributes(AssemblerHelper.toAttributeList(entity.getAttributes()));
		dto.setMeta(entity.toDTO());
        dto.setTypeKey(entity.getType().getId());
        dto.setStateKey(entity.getState());
        return dto;
    }

    public static List<ResultValuesGroupInfo> toReListComonentInfos(List<ResultComponent> entities) throws DoesNotExistException {
        List<ResultValuesGroupInfo> dtos = new ArrayList<ResultValuesGroupInfo>(entities.size());
        for (ResultComponent entity : entities) {
            dtos.add(toResultValuesGroupInfo(entity));
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

    public static ResultComponent toResultComponent(String resultComponentTypeKey, ResultValuesGroupInfo dto, LrcDao lrcDao) throws InvalidParameterException, DataValidationErrorException {
        ResultComponent entity = new ResultComponent();
        toResultComponent(entity, dto, lrcDao);
        return entity;
    }

    public static void toResultComponent(ResultComponent entity, ResultValuesGroupInfo dto, LrcDao lrcDao) throws InvalidParameterException, DataValidationErrorException {
        BeanUtils.copyProperties(dto, entity,
                new String[] { "descr", "resultValues", "attributes", "meta", "type" , "id"});
        
        ResultComponentType type;
        try {
            type = lrcDao.fetch(ResultComponentType.class, dto.getTypeKey());
            entity.setType(type);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(dto.getTypeKey() + " does not exist.");
        }
        entity.setId(dto.getKey());
        entity.setDescr(toRichText(LrcRichText.class, dto.getDescr()));
        entity.setState(dto.getStateKey());

        //Create new Result Values, and delete unwanted ones, keep the overlap 
        List<ResultValue> resultValues = new ArrayList<ResultValue>(dto.getResultValueKeys().size());
        Map<String,ResultValue> currentResultValues = new HashMap<String,ResultValue>();
        if(entity.getResultValues()!=null){
        	for(ResultValue resultValue:entity.getResultValues()){
        		currentResultValues.put(resultValue.getValue(),resultValue);
        	}
        }
        
        for(String value:dto.getResultValueKeys()){
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
        entity.setAttributes(AssemblerHelper.toGenericAttributes(ResultComponentAttribute.class, dto.getAttributes(), entity, lrcDao));
    }
}
