package org.kuali.student.lum.atp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.dto.AttributeInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.entity.Attribute;
import org.kuali.student.core.entity.Meta;
import org.kuali.student.lum.atp.dao.AtpDao;
import org.kuali.student.lum.atp.dto.AtpInfo;
import org.kuali.student.lum.atp.dto.DateRangeInfo;
import org.kuali.student.lum.atp.dto.MilestoneInfo;
import org.kuali.student.lum.atp.entity.Atp;
import org.kuali.student.lum.atp.entity.AtpAttribute;
import org.kuali.student.lum.atp.entity.AtpAttributeDef;
import org.kuali.student.lum.atp.entity.AtpType;
import org.kuali.student.lum.atp.entity.DateRange;
import org.kuali.student.lum.atp.entity.DateRangeAttribute;
import org.kuali.student.lum.atp.entity.DateRangeAttributeDef;
import org.kuali.student.lum.atp.entity.DateRangeType;
import org.kuali.student.lum.atp.entity.Milestone;
import org.kuali.student.lum.atp.entity.MilestoneAttribute;
import org.kuali.student.lum.atp.entity.MilestoneAttributeDef;
import org.kuali.student.lum.atp.entity.MilestoneType;
import org.springframework.beans.BeanUtils;

public class AtpAssembler {

	public static DateRange toDateRange(DateRangeInfo dateRangeInfo, AtpDao dao) throws InvalidParameterException {
		
		DateRange dateRange = new DateRange();
		
		BeanUtils.copyProperties(dateRangeInfo, dateRange,new String[]{"atpKey", "type", "attributes", "metaInfo"});
		
		//Copy the attributes
		dateRange.setAttributes(toAttributes(dateRangeInfo.getAttributes(),dateRange,dao));
		
		//Search for and copy the associated Atp
		Atp atp = dao.fetch(Atp.class, dateRangeInfo.getAtpKey());
		if(atp==null){
			throw new InvalidParameterException("Atp does not exist for key: " + dateRangeInfo.getAtpKey());
		}
		dateRange.setAtp(atp);
		
		//Search for and copy the Type
		DateRangeType dateRangeType = dao.fetch(DateRangeType.class, dateRangeInfo.getType());
		if(dateRangeType==null){
			throw new InvalidParameterException("DateRangeType does not exist for key: " + dateRangeInfo.getType());
		}
		dateRange.setType(dateRangeType);
		
		return dateRange;
	}

//	private static <T,S> List<T> toAttributes(Class<T> attributeClass, List<AttributeInfo> attributes, S dateRange, AtpDao dao) throws InvalidParameterException {
//		
//		List<T> dateRangeAttributes = new ArrayList<T>();
//		for(AttributeInfo attributeInfo:attributes){
//			//Look up the attribute definition
//			DateRangeAttributeDef attributeDef = dao.fetchAttributeDefByName(DateRangeAttributeDef.class, attributeInfo.getKey());
//			if(attributeDef==null){
//				throw new InvalidParameterException("Invalid Attribute : " + attributeInfo.getKey());
//			}
//			T attribute = attributeClass.newInstance();
//			attribute.setValue(attributeInfo.getValue());
//			attribute.setAttrDef(attributeDef);
//			attribute.setOwner(dateRange);
//		}
//		return dateRangeAttributes;
//	}
	
	private static List<DateRangeAttribute> toAttributes(List<AttributeInfo> attributes, DateRange dateRange, AtpDao dao) throws InvalidParameterException {
		
		List<DateRangeAttribute> dateRangeAttributes = new ArrayList<DateRangeAttribute>();
		
		for(AttributeInfo attributeInfo:attributes){
			
			//Look up the attribute definition
			DateRangeAttributeDef attributeDef = dao.fetchAttributeDefByName(DateRangeAttributeDef.class, attributeInfo.getKey());
			
			if(attributeDef==null){
				throw new InvalidParameterException("Invalid Attribute : " + attributeInfo.getKey());
			}
			
			DateRangeAttribute dateRangeAttribute = new DateRangeAttribute();
			dateRangeAttribute.setValue(attributeInfo.getValue());
			dateRangeAttribute.setAttrDef(attributeDef);
			dateRangeAttribute.setOwner(dateRange);
			
			dateRangeAttributes.add(dateRangeAttribute);
		}
		
		return dateRangeAttributes;
	}

	public static DateRangeInfo toDateRangeInfo(DateRange dateRange) {
		
		DateRangeInfo dateRangeInfo = new DateRangeInfo();

		BeanUtils.copyProperties(dateRange, dateRangeInfo, new String[]{"atp", "type", "attributes", "metaInfo"});
		
		//copy attributes, metadata, Atp, and Type
		dateRangeInfo.setAttributes(toAttributeInfos(dateRange.getAttributes()));
		dateRangeInfo.setMetaInfo(toMetaInfo(dateRange.getMeta(),dateRange.getVersionInd()));
		dateRangeInfo.setType(dateRange.getType().getKey());
		dateRangeInfo.setAtpKey(dateRange.getAtp().getKey());
		
		return dateRangeInfo;
	}

	private static MetaInfo toMetaInfo(Meta meta, long versionInd) {
		
		MetaInfo metaInfo = new MetaInfo();
		
		BeanUtils.copyProperties(meta, metaInfo);
		
		return metaInfo;
	}

	private static List<AttributeInfo> toAttributeInfos(
			List<? extends Attribute> attributes) {

		List<AttributeInfo> attributeInfos = new ArrayList<AttributeInfo>();
		
		for(Attribute attribute:attributes){
			AttributeInfo attributeInfo = new AttributeInfo();
			attributeInfo.setKey(attribute.getAttrDef().getName());
			attributeInfo.setValue(attribute.getValue());
			attributeInfos.add(attributeInfo);
		}
		
		return attributeInfos;
	}

	public static Atp toAtp(AtpInfo atpInfo, AtpDao dao) throws InvalidParameterException {
		
		Atp atp = new Atp();
		
		//Copy all basic properties
		BeanUtils.copyProperties(atpInfo, atp, new String[]{"type","attributes","metaInfo"});
		
		//Copy Attributes
		atp.setAttributes(toAttributes(atpInfo.getAttributes(),atp,dao));
		
		//Search for and copy the type
		AtpType atpType = dao.fetch(AtpType.class, atpInfo.getType());
		if(atpType==null){
			throw new InvalidParameterException("AtpType does not exist for key: " + atpInfo.getType());
		}
		
		return atp;
	}

	private static List<AtpAttribute> toAttributes(
			List<AttributeInfo> attributes, Atp atp, AtpDao dao) throws InvalidParameterException {
		List<AtpAttribute> atpAttributes = new ArrayList<AtpAttribute>();
		
		for(AttributeInfo attributeInfo:attributes){
			//Look up the attribute definition
			AtpAttributeDef attributeDef = dao.fetchAttributeDefByName(AtpAttributeDef.class, attributeInfo.getKey());
		
			if(attributeDef==null){
				throw new InvalidParameterException("Invalid Attribute : " + attributeInfo.getKey());
			}
			
			AtpAttribute atpAttribute = new AtpAttribute();
			atpAttribute.setValue(attributeInfo.getValue());
			atpAttribute.setAttrDef(attributeDef);
			atpAttribute.setOwner(atp);
			
			atpAttributes.add(atpAttribute);
		}
		
		return atpAttributes;
	}

	public static AtpInfo toAtpInfo(Atp atp) {
		AtpInfo atpInfo = new AtpInfo();

		BeanUtils.copyProperties(atp, atpInfo, new String[]{"type", "attributes", "metaInfo"});
		
		//copy attributes, metadata, Atp, and Type
		atpInfo.setAttributes(toAttributeInfos(atp.getAttributes()));
		atpInfo.setMetaInfo(toMetaInfo(atp.getMeta(),atp.getVersionInd()));
		atpInfo.setType(atp.getType().getKey());
			
		return atpInfo;
	}

	public static Milestone toMilestone(MilestoneInfo milestoneInfo,
			AtpDao dao) throws InvalidParameterException {
		
		Milestone milestone = new Milestone();
		
		BeanUtils.copyProperties(milestoneInfo, milestone,new String[]{"atpKey", "type", "attributes","metaInfo"});
		
		//Copy the attributes
		milestone.setAttributes(toAttributes(milestoneInfo.getAttributes(),milestone,dao));
		
		//Search for and copy the associated Atp
		Atp atp = dao.fetch(Atp.class, milestoneInfo.getAtpKey());
		if(atp==null){
			throw new InvalidParameterException("Atp does not exist for key: " + milestoneInfo.getAtpKey());
		}
		milestone.setAtp(atp);
		
		//Search for and copy the Type
		MilestoneType milestoneType = dao.fetch(MilestoneType.class, milestoneInfo.getType());
		if(milestoneType==null){
			throw new InvalidParameterException("MilestoneType does not exist for key: " + milestoneInfo.getType());
		}
		milestone.setType(milestoneType);
		
		return milestone;
		
	}

	private static List<MilestoneAttribute> toAttributes(
			List<AttributeInfo> attributes, Milestone milestone, AtpDao dao) throws InvalidParameterException {
		List<MilestoneAttribute> milestoneAttributes = new ArrayList<MilestoneAttribute>();
		
		for(AttributeInfo attributeInfo:attributes){
			//Look up the attribute definition
			MilestoneAttributeDef attributeDef = dao.fetchAttributeDefByName(MilestoneAttributeDef.class, attributeInfo.getKey());
		
			if(attributeDef==null){
				throw new InvalidParameterException("Invalid Attribute : " + attributeInfo.getKey());
			}
			
			MilestoneAttribute milestoneAttribute = new MilestoneAttribute();
			milestoneAttribute.setValue(attributeInfo.getValue());
			milestoneAttribute.setAttrDef(attributeDef);
			milestoneAttribute.setOwner(milestone);
			
			milestoneAttributes.add(milestoneAttribute);
		}
		
		return milestoneAttributes;
	}

	public static MilestoneInfo toMilestoneInfo(Milestone milestone) {
		MilestoneInfo milestoneInfo = new MilestoneInfo();

		BeanUtils.copyProperties(milestone, milestoneInfo, new String[]{"atp", "type", "attributes", "metaInfo"});
		
		//copy attributes, metadata, Atp, and Type
		milestoneInfo.setAttributes(toAttributeInfos(milestone.getAttributes()));
		milestoneInfo.setMetaInfo(toMetaInfo(milestone.getMeta(),milestone.getVersionInd()));
		milestoneInfo.setType(milestone.getType().getKey());
		milestoneInfo.setAtpKey(milestone.getAtp().getKey());
		
		return milestoneInfo;
	}


}
