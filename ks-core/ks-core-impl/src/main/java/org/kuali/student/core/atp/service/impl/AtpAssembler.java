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

package org.kuali.student.core.atp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.service.impl.BaseAssembler;
import org.kuali.student.core.atp.dao.AtpDao;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.atp.dto.DateRangeInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import org.kuali.student.core.atp.entity.Atp;
import org.kuali.student.core.atp.entity.AtpAttribute;
import org.kuali.student.core.atp.entity.AtpRichText;
import org.kuali.student.core.atp.entity.AtpType;
import org.kuali.student.core.atp.entity.DateRange;
import org.kuali.student.core.atp.entity.DateRangeAttribute;
import org.kuali.student.core.atp.entity.DateRangeType;
import org.kuali.student.core.atp.entity.Milestone;
import org.kuali.student.core.atp.entity.MilestoneAttribute;
import org.kuali.student.core.atp.entity.MilestoneType;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.springframework.beans.BeanUtils;

public class AtpAssembler extends BaseAssembler{

	public static Atp toAtp(boolean isUpdate, AtpInfo atpInfo, AtpDao dao)
			throws InvalidParameterException, DoesNotExistException, VersionMismatchException {
		Atp atp;
		if (isUpdate) {
			atp = dao.fetch(Atp.class, atpInfo.getId());
			if (atp == null) {
				throw new DoesNotExistException("Atp does not exist for key: " + atpInfo.getId());
			}
			if (!String.valueOf(atp.getVersionNumber()).equals(atpInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("Atp to be updated is not the current version");
			}
		} else {
			atp = new Atp();
		}

		// Copy all basic properties
		BeanUtils.copyProperties(atpInfo, atp, new String[] { "type",
				"attributes", "metaInfo", "desc" });

		// Copy Attributes
		atp.setAttributes(toGenericAttributes(AtpAttribute.class, atpInfo.getAttributes(), atp, dao));

		// Search for and copy the type
		AtpType atpType = dao.fetch(AtpType.class, atpInfo.getType());
		if (atpType == null) {
			throw new InvalidParameterException(
					"AtpType does not exist for key: " + atpInfo.getType());
		}
		atp.setType(atpType);

		//Copy RichText
		atp.setDescr(toRichText(AtpRichText.class, atpInfo.getDesc()));
		
		return atp;
	}

	public static AtpInfo toAtpInfo(Atp atp) {
		AtpInfo atpInfo = new AtpInfo();

		BeanUtils.copyProperties(atp, atpInfo, new String[] { "type",
				"attributes", "metaInfo", "desc" });

		// copy attributes, metadata, Atp, and Type
		atpInfo.setAttributes(toAttributeMap(atp.getAttributes()));
		atpInfo.setMetaInfo(toMetaInfo(atp.getMeta(), atp.getVersionNumber()));
		atpInfo.setType(atp.getType().getId());
		atpInfo.setDesc(toRichTextInfo(atp.getDescr()));
		
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

		atpTypeInfo.setDurationType(atpType.getDurationType().getId());
		atpTypeInfo.setSeasonalType(atpType.getSeasonalType().getId());

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
			dateRange = dao.fetch(DateRange.class, dateRangeInfo.getId());
			if (dateRange == null) {
				throw new DoesNotExistException("DateRange does not exist for key: " + dateRangeInfo.getId());
			}
			if (!String.valueOf(dateRange.getVersionNumber()).equals(dateRangeInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("DateRange to be updated is not the current version");
			}
		} else {
			dateRange = new DateRange();
		}

		BeanUtils.copyProperties(dateRangeInfo, dateRange, new String[] {
				"atpKey", "type", "attributes", "metaInfo","desc" });

		// Copy the attributes
		dateRange.setAttributes(toGenericAttributes(DateRangeAttribute.class,
				dateRangeInfo.getAttributes(), dateRange, dao));

		// Search for and copy the associated Atp
		Atp atp = dao.fetch(Atp.class, dateRangeInfo.getAtpId());
		if (atp == null) {
			throw new InvalidParameterException("Atp does not exist for key: "
					+ dateRangeInfo.getAtpId());
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

		dateRange.setDescr(toRichText(AtpRichText.class, dateRangeInfo.getDesc()));
		
		return dateRange;
	}

	public static DateRangeInfo toDateRangeInfo(DateRange dateRange) {

		DateRangeInfo dateRangeInfo = new DateRangeInfo();

		BeanUtils.copyProperties(dateRange, dateRangeInfo, new String[] {
				"atp", "type", "attributes", "metaInfo", "desc" });

		// copy attributes, metadata, Atp, and Type
		dateRangeInfo
				.setAttributes(toAttributeMap(dateRange.getAttributes()));
		dateRangeInfo.setMetaInfo(toMetaInfo(dateRange.getMeta(), dateRange
				.getVersionNumber()));
		dateRangeInfo.setType(dateRange.getType().getId());
		dateRangeInfo.setAtpId(dateRange.getAtp().getId());
		dateRangeInfo.setDesc(toRichTextInfo(dateRange.getDescr()));
		
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
			milestone = dao.fetch(Milestone.class, milestoneInfo.getId());
			if (milestone == null) {
				throw new DoesNotExistException("Milestone does not exist for key: " + milestoneInfo.getId());
			}
			if (!String.valueOf(milestone.getVersionNumber()).equals(milestoneInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("Milestone to be updated is not the current version");
			}
		} else {
			milestone = new Milestone();
		}

		BeanUtils.copyProperties(milestoneInfo, milestone, new String[] {
				"atpKey", "type", "attributes", "metaInfo", "desc" });

		// Copy the attributes
		milestone.setAttributes(toGenericAttributes(MilestoneAttribute.class,
				milestoneInfo.getAttributes(), milestone, dao));

		// Search for and copy the associated Atp
		Atp atp = dao.fetch(Atp.class, milestoneInfo.getAtpId());
		if (atp == null) {
			throw new InvalidParameterException("Atp does not exist for key: "
					+ milestoneInfo.getAtpId());
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

		milestone.setDescr(toRichText(AtpRichText.class, milestoneInfo.getDesc()));
		
		return milestone;

	}

	public static MilestoneInfo toMilestoneInfo(Milestone milestone) {

		MilestoneInfo milestoneInfo = new MilestoneInfo();

		BeanUtils.copyProperties(milestone, milestoneInfo, new String[] {
				"atp", "type", "attributes", "metaInfo", "desc" });

		// copy attributes, metadata, Atp, and Type
		milestoneInfo
				.setAttributes(toAttributeMap(milestone.getAttributes()));
		milestoneInfo.setMetaInfo(toMetaInfo(milestone.getMeta(), milestone
				.getVersionNumber()));
		milestoneInfo.setType(milestone.getType().getId());
		milestoneInfo.setAtpId(milestone.getAtp().getId());
		milestoneInfo.setDesc(toRichTextInfo(milestone.getDescr()));
		
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
