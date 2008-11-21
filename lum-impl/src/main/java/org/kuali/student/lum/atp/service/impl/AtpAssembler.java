package org.kuali.student.lum.atp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.TypeInfo;
import org.kuali.student.core.entity.Attribute;
import org.kuali.student.core.entity.AttributeDef;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.Meta;
import org.kuali.student.core.entity.Type;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.atp.dao.AtpDao;
import org.kuali.student.lum.atp.dto.AtpInfo;
import org.kuali.student.lum.atp.dto.AtpTypeInfo;
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

	public static DateRange toDateRange(boolean isUpdate,
			DateRangeInfo dateRangeInfo, AtpDao dao)
			throws InvalidParameterException, DoesNotExistException, VersionMismatchException {

		DateRange dateRange;
		if (isUpdate) {
			dateRange = dao.fetch(DateRange.class, dateRangeInfo.getKey());
			if (dateRange == null) {
				throw new DoesNotExistException("DateRange does not exist for key: " + dateRangeInfo.getKey());
			}
			if (!String.valueOf(dateRange.getVersionInd()).equals(dateRangeInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("DateRange to be updated is not the current version");
			}
		} else {
			dateRange = new DateRange();
		}

		BeanUtils.copyProperties(dateRangeInfo, dateRange, new String[] {
				"atpKey", "type", "attributes", "metaInfo" });

		// Copy the attributes
		dateRange.setAttributes(toGenericAttributes(
				DateRangeAttributeDef.class, DateRangeAttribute.class,
				dateRangeInfo.getAttributes(), dateRange, dao));

		// Search for and copy the associated Atp
		Atp atp = dao.fetch(Atp.class, dateRangeInfo.getAtpKey());
		if (atp == null) {
			throw new InvalidParameterException("Atp does not exist for key: "
					+ dateRangeInfo.getAtpKey());
		}
		dateRange.setAtp(atp);

		// Search for and copy the Type
		DateRangeType dateRangeType = dao.fetch(DateRangeType.class,
				dateRangeInfo.getType());
		if (dateRangeType == null) {
			throw new InvalidParameterException(
					"DateRangeType does not exist for key: "
							+ dateRangeInfo.getType());
		}
		dateRange.setType(dateRangeType);

		return dateRange;
	}

	public static DateRangeInfo toDateRangeInfo(DateRange dateRange) {

		DateRangeInfo dateRangeInfo = new DateRangeInfo();

		BeanUtils.copyProperties(dateRange, dateRangeInfo, new String[] {
				"atp", "type", "attributes", "metaInfo" });

		// copy attributes, metadata, Atp, and Type
		dateRangeInfo
				.setAttributes(toAttributeMap(dateRange.getAttributes()));
		dateRangeInfo.setMetaInfo(toMetaInfo(dateRange.getMeta(), dateRange
				.getVersionInd()));
		dateRangeInfo.setType(dateRange.getType().getKey());
		dateRangeInfo.setAtpKey(dateRange.getAtp().getKey());

		return dateRangeInfo;
	}

	private static MetaInfo toMetaInfo(Meta meta, long versionInd) {

		MetaInfo metaInfo = new MetaInfo();
		// If there was a meta passed in then copy the values
		if (meta != null) {
			BeanUtils.copyProperties(meta, metaInfo);
		}
		metaInfo.setVersionInd(String.valueOf(versionInd));

		return metaInfo;
	}

	private static Map<String,String> toAttributeMap(
			List<? extends Attribute<?, ?>> attributes) {

		Map<String,String> attributeInfos = new HashMap<String,String>();

		for (Attribute<?, ?> attribute : attributes) {
			attributeInfos.put(attribute.getAttrDef().getName(), attribute.getValue());
		}

		return attributeInfos;
	}

	public static Atp toAtp(boolean isUpdate, AtpInfo atpInfo, AtpDao dao)
			throws InvalidParameterException, DoesNotExistException, VersionMismatchException {
		Atp atp;
		if (isUpdate) {
			atp = dao.fetch(Atp.class, atpInfo.getKey());
			if (atp == null) {
				throw new DoesNotExistException("Atp does not exist for key: " + atp.getKey());
			}
			if (!String.valueOf(atp.getVersionInd()).equals(atpInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("Atp to be updated is not the current version");
			}
		} else {
			atp = new Atp();
		}

		// Copy all basic properties
		BeanUtils.copyProperties(atpInfo, atp, new String[] { "type",
				"attributes", "metaInfo" });

		// Copy Attributes
		atp.setAttributes(toGenericAttributes(AtpAttributeDef.class,
				AtpAttribute.class, atpInfo.getAttributes(), atp, dao));

		// Search for and copy the type
		AtpType atpType = dao.fetch(AtpType.class, atpInfo.getType());
		if (atpType == null) {
			throw new InvalidParameterException(
					"AtpType does not exist for key: " + atpInfo.getType());
		}
		atp.setType(atpType);

		return atp;
	}

	private static <A extends Attribute<O, D>, O extends AttributeOwner<A>, D extends AttributeDef> List<A> toGenericAttributes(
			Class<D> attributeDefClass, Class<A> attributeClass,
			Map<String,String> attributeMap, O owner, AtpDao dao)
			throws InvalidParameterException {
		List<A> attributes = new ArrayList<A>();

		// Delete all the old attributes(if the owner is not null)
		for (A attribute : owner.getAttributes()) {
			dao.delete(attribute);
		}
		owner.getAttributes().clear();

		for (Map.Entry<String,String> attributeEntry : attributeMap.entrySet()) {
			// Look up the attribute definition
			D attributeDef = dao.fetchAttributeDefByName(attributeDefClass,
					attributeEntry.getKey());

			if (attributeDef == null) {
				throw new InvalidParameterException("Invalid Attribute : "
						+ attributeEntry.getKey());
			}

			A attribute;
			try {
				attribute = attributeClass.newInstance();
				attribute.setValue(attributeEntry.getValue());
				attribute.setAttrDef(attributeDef);
				attribute.setOwner(owner);
				attributes.add(attribute);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return attributes;
	}

	public static AtpInfo toAtpInfo(Atp atp) {
		AtpInfo atpInfo = new AtpInfo();

		BeanUtils.copyProperties(atp, atpInfo, new String[] { "type",
				"attributes", "metaInfo" });

		// copy attributes, metadata, Atp, and Type
		atpInfo.setAttributes(toAttributeMap(atp.getAttributes()));
		atpInfo.setMetaInfo(toMetaInfo(atp.getMeta(), atp.getVersionInd()));
		atpInfo.setType(atp.getType().getKey());

		return atpInfo;
	}

	public static Milestone toMilestone(boolean isUpdate,
			MilestoneInfo milestoneInfo, AtpDao dao)
			throws InvalidParameterException, DoesNotExistException, VersionMismatchException {

		Milestone milestone;
		if (isUpdate) {
			milestone = dao.fetch(Milestone.class, milestoneInfo.getKey());
			if (milestone == null) {
				throw new DoesNotExistException("Milestone does not exist for key: " + milestoneInfo.getKey());
			}
			if (!String.valueOf(milestone.getVersionInd()).equals(milestoneInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("Milestone to be updated is not the current version");
			}
		} else {
			milestone = new Milestone();
		}

		BeanUtils.copyProperties(milestoneInfo, milestone, new String[] {
				"atpKey", "type", "attributes", "metaInfo" });

		// Copy the attributes
		milestone.setAttributes(toGenericAttributes(
				MilestoneAttributeDef.class, MilestoneAttribute.class,
				milestoneInfo.getAttributes(), milestone, dao));

		// Search for and copy the associated Atp
		Atp atp = dao.fetch(Atp.class, milestoneInfo.getAtpKey());
		if (atp == null) {
			throw new InvalidParameterException("Atp does not exist for key: "
					+ milestoneInfo.getAtpKey());
		}
		milestone.setAtp(atp);

		// Search for and copy the Type
		MilestoneType milestoneType = dao.fetch(MilestoneType.class,
				milestoneInfo.getType());
		if (milestoneType == null) {
			throw new InvalidParameterException(
					"MilestoneType does not exist for key: "
							+ milestoneInfo.getType());
		}
		milestone.setType(milestoneType);

		return milestone;

	}

	public static MilestoneInfo toMilestoneInfo(Milestone milestone) {

		MilestoneInfo milestoneInfo = new MilestoneInfo();

		BeanUtils.copyProperties(milestone, milestoneInfo, new String[] {
				"atp", "type", "attributes", "metaInfo" });

		// copy attributes, metadata, Atp, and Type
		milestoneInfo
				.setAttributes(toAttributeMap(milestone.getAttributes()));
		milestoneInfo.setMetaInfo(toMetaInfo(milestone.getMeta(), milestone
				.getVersionInd()));
		milestoneInfo.setType(milestone.getType().getKey());
		milestoneInfo.setAtpKey(milestone.getAtp().getKey());

		return milestoneInfo;
	}

	public static AtpTypeInfo toAtpTypeInfo(AtpType atpType) {

		AtpTypeInfo atpTypeInfo = new AtpTypeInfo();

		BeanUtils.copyProperties(atpType, atpTypeInfo, new String[] {
				"seasonalType", "durationType", "attributes" });

		// Copy attributes and duration/seasonal types
		atpTypeInfo.setAttributes(toAttributeMap(atpType.getAttributes()));

		atpTypeInfo.setDurationType(atpType.getDurationType().getKey());
		atpTypeInfo.setSeasonalType(atpType.getSeasonalType().getKey());

		return atpTypeInfo;
	}

	/**
	 * @param <T>
	 *            TypeInfo class
	 * @param <S>
	 *            Type Class
	 * @param typeInfoClass
	 *            the class of the resulting typeInfo object
	 * @param typeEntity
	 *            the typeEntity to copy from
	 * @return a new TypeInfo
	 */
	public static <T extends TypeInfo, S extends Type> T toGenericTypeInfo(
			Class<T> typeInfoClass, S typeEntity) {
		T typeInfo;
		try {
			// Create a new TypeInfo based on the <T> class and copy the
			// properties
			typeInfo = typeInfoClass.newInstance();
			BeanUtils.copyProperties(typeEntity, typeInfo,
					new String[] { "attributes" });

			// Copy the attributes
			typeInfo
					.setAttributes(toAttributeMap(typeEntity.getAttributes()));

			return typeInfo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static <T extends TypeInfo, S extends Type> List<T> toGenericTypeInfoList(
			Class<T> typeInfoClass, List<S> typeEntities) {
		List<T> typeInfoList = new ArrayList<T>();
		for (S typeEntity : typeEntities) {
			typeInfoList.add(toGenericTypeInfo(typeInfoClass, typeEntity));
		}
		return typeInfoList;
	}

	public static List<AtpTypeInfo> toAtpTypeInfoList(List<AtpType> atpTypes) {
		List<AtpTypeInfo> typeInfoList = new ArrayList<AtpTypeInfo>();
		for (AtpType atpType : atpTypes) {
			typeInfoList.add(toAtpTypeInfo(atpType));
		}
		return typeInfoList;
	}

	public static List<AtpInfo> toAtpInfoList(List<Atp> atps) {
		List<AtpInfo> atpInfoList = new ArrayList<AtpInfo>();
		for (Atp atp : atps) {
			atpInfoList.add(toAtpInfo(atp));
		}
		return atpInfoList;
	}

	public static List<DateRangeInfo> toDateRangeInfoList(
			List<DateRange> dateRanges) {
		List<DateRangeInfo> dateRangeInfoList = new ArrayList<DateRangeInfo>();
		for (DateRange dateRange : dateRanges) {
			dateRangeInfoList.add(toDateRangeInfo(dateRange));
		}
		return dateRangeInfoList;
	}

	public static List<MilestoneInfo> toMilestoneInfoList(
			List<Milestone> milestones) {
		List<MilestoneInfo> milestoneInfoList = new ArrayList<MilestoneInfo>();
		for (Milestone milestone : milestones) {
			milestoneInfoList.add(toMilestoneInfo(milestone));
		}
		return milestoneInfoList;
	}

}
