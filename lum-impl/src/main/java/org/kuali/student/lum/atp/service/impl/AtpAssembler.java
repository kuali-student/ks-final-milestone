package org.kuali.student.lum.atp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.service.impl.BaseAssembler;
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

public class AtpAssembler extends BaseAssembler{

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



	public static List<AtpInfo> toAtpInfoList(List<Atp> atps) {
		List<AtpInfo> atpInfoList = new ArrayList<AtpInfo>();
		for (Atp atp : atps) {
			atpInfoList.add(toAtpInfo(atp));
		}
		return atpInfoList;
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

	public static List<AtpTypeInfo> toAtpTypeInfoList(List<AtpType> atpTypes) {
		List<AtpTypeInfo> typeInfoList = new ArrayList<AtpTypeInfo>();
		for (AtpType atpType : atpTypes) {
			typeInfoList.add(toAtpTypeInfo(atpType));
		}
		return typeInfoList;
	}

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



	public static List<DateRangeInfo> toDateRangeInfoList(
			List<DateRange> dateRanges) {
		List<DateRangeInfo> dateRangeInfoList = new ArrayList<DateRangeInfo>();
		for (DateRange dateRange : dateRanges) {
			dateRangeInfoList.add(toDateRangeInfo(dateRange));
		}
		return dateRangeInfoList;
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

	public static List<MilestoneInfo> toMilestoneInfoList(
			List<Milestone> milestones) {
		List<MilestoneInfo> milestoneInfoList = new ArrayList<MilestoneInfo>();
		for (Milestone milestone : milestones) {
			milestoneInfoList.add(toMilestoneInfo(milestone));
		}
		return milestoneInfoList;
	}

}
