package org.kuali.student.lum.atp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.core.dto.AttributeInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.lum.atp.dao.AtpDao;
import org.kuali.student.lum.atp.dto.DateRangeInfo;
import org.kuali.student.lum.atp.entity.DateRange;
import org.kuali.student.lum.atp.entity.DateRangeAttribute;
import org.kuali.student.lum.atp.entity.DateRangeAttributeDef;
import org.springframework.beans.BeanUtils;

public class AtpAssembler {

	public static DateRange toDateRange(DateRangeInfo dateRangeInfo, AtpDao dao) throws InvalidParameterException {
		
		DateRange dateRange = new DateRange();
		
		BeanUtils.copyProperties(dateRangeInfo, dateRange,new String[]{"attributes","metaInfo"});
		
		//Copy the attributes
		dateRange.setAttributes(toAttributes(dateRangeInfo.getAttributes(),dateRange,dao));
		
		return dateRange;
	}

	private static List<DateRangeAttribute> toAttributes(List<AttributeInfo> attributes, DateRange dateRange, AtpDao dao) throws InvalidParameterException {
		
		List<DateRangeAttribute> dateRangeAttributes = new ArrayList<DateRangeAttribute>();
		for(AttributeInfo attributeInfo:attributes){
			//Look up the attribute definition
			DateRangeAttributeDef attributeDef = dao.fetch(DateRangeAttributeDef.class, attributeInfo.getKey());
			if(attributeDef==null){
				throw new InvalidParameterException("Invalid Attribute : " + attributeInfo.getKey());
			}
			DateRangeAttribute dateRangeAttribute = new DateRangeAttribute();
			dateRangeAttribute.setValue(attributeInfo.getValue());
			dateRangeAttribute.setAttrDef(attributeDef);
			dateRangeAttribute.setOwner(dateRange);
		}
		return dateRangeAttributes;
	}

	public static DateRangeInfo toDateRangeInfo(DateRange dateRange) {
		DateRangeInfo dateRangeInfo = new DateRangeInfo();
		BeanUtils.copyProperties(dateRange, dateRangeInfo, new String[]{"attributes","metaInfo"});
		//copy attributes and metadata
		dateRangeInfo.setAttributes(toAttributes(dateRange.getAttributes()));
		
		MetaInfo metaInfo = new MetaInfo();
		BeanUtils.copyProperties(dateRange.getMeta(), metaInfo);
		// TODO Auto-generated method stub
		return dateRangeInfo;
	}

	private static List<AttributeInfo> toAttributes(
			List<DateRangeAttribute> attributes) {
		// TODO Auto-generated method stub
		return null;
	}

}
