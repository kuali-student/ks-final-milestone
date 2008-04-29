/**
 * An Assembler class is recommended by Martin Fowler to convert a domain class to a data transfer object
 * and back
 * @see http://www.martinfowler.com/eaaCatalog/dataTransferObject.html
 * 
 * This is a helper class for LuService; the DTO's are for that service.
 * Each DTO has a createDTO method which creates it from an entity, a create entity method which creates the
 * entity from the DTO, and an update entity method which updates an existing entity from a DTO
 * the create entity methods call update entity. 
 */
package org.kuali.student.poc.learningunit.lu.service.impl;

import java.util.Map;

import org.kuali.student.poc.learningunit.lu.dao.LuDao;
import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluCrit;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.CluSet;
import org.kuali.student.poc.learningunit.lu.entity.LuAttribute;
import org.kuali.student.poc.learningunit.lu.entity.LuAttributeType;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.learningunit.lu.entity.LuiRelation;
import org.kuali.student.poc.learningunit.lu.entity.SearchKeyValue;
import org.kuali.student.poc.xsd.learningunit.lu.dto.AtpDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCriteria;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationAssignInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluRelationUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluSetUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluUpdateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuTypeInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiCreateInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationAssignInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiRelationInfo;
import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiUpdateInfo;
import org.springframework.beans.BeanUtils;

/**
 * @author garystruthers
 * 
 */
public class Assembler {

	public static Atp createAtp(AtpDisplay atpDisplay) {
		Atp atp = new Atp();
		updateAtp(atpDisplay, atp);
		return atp;
	}

	public static void updateAtp(AtpDisplay atpDisplay, Atp atp) {
		atp.setAtpId(atpDisplay.getAtpId());
		atp.setAtpName(atpDisplay.getAtpName());
	}

	public static Clu createClu(CluInfo cluInfo) {
		Clu clu = new Clu();
		updateClu(cluInfo, clu);
		return clu;
	}

	public static void updateClu(CluInfo cluInfo, Clu clu) {
		Atp atp = new Atp();
		atp.setAtpId(cluInfo.getEffectiveStartCycle());
		clu.setEffectiveStartCycle(atp);
		atp = new Atp();
		atp.setAtpId(cluInfo.getEffectiveEndCycle());
		clu.setEffectiveEndCycle(atp);
		clu.setApprovalStatus(cluInfo.getApprovalStatus());
		clu.setApprovalStatusTime(cluInfo.getApprovalStatusTime());
		clu.setCluLongName(cluInfo.getCluLongName());
		clu.setCluShortName(cluInfo.getCluShortName());
		clu.setDescription(cluInfo.getDescription());
		clu.setEffectiveEndDate(cluInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(cluInfo.getEffectiveStartDate());
		clu.setCreateTime(cluInfo.getCreateTime());
		clu.setCreateUserComment(cluInfo.getCreateUserComment());
		clu.setCreateUserId(cluInfo.getCreateUserId());
		clu.setUpdateTime(cluInfo.getUpdateTime());
		clu.setUpdateUserComment(cluInfo.getUpdateUserComment());
		clu.setUpdateUserId(cluInfo.getUpdateUserId());
		LuType luType = new LuType();
		luType.setLuTypeId(cluInfo.getLuTypeId());
		clu.setLuType(luType);
	}

	public static Clu createClu(CluDisplay cluDisplay) {
		Clu clu = new Clu();
		updateClu(cluDisplay, clu);
		return clu;
	}

	public static void updateClu(CluDisplay cluDisplay, Clu clu) {
		LuAttribute luAttribute = new LuAttribute();
		luAttribute.setClu(clu);
		Atp atp = new Atp();
		atp.setAtpId(cluDisplay.getAtpDisplayStart().getAtpId());
		clu.setEffectiveStartCycle(atp);
		atp = new Atp();
		atp.setAtpId(cluDisplay.getAtpDisplayEnd().getAtpId());
		clu.setEffectiveEndCycle(atp);
		clu.setCluShortName(cluDisplay.getCluShortName());
		LuType luType = new LuType();
		luType.setLuTypeId(cluDisplay.getLuTypeId());
		clu.setLuType(luType);
	}

	public static LuType createLuType(LuTypeInfo luTypeInfo) {
		LuType luType = new LuType();
		updateLuType(luTypeInfo, luType);
		return luType;
	}

	public static void updateLuType(LuTypeInfo luTypeInfo, LuType luType) {
		luType.setCreateTime(luTypeInfo.getCreateTime());
		luType.setCreateUserComment(luTypeInfo.getCreateUserComment());
		luType.setCreateUserId(luTypeInfo.getCreateUserId());
		luType.setUpdateTime(luTypeInfo.getUpdateTime());
		luType.setUpdateUserComment(luTypeInfo.getUpdateUserComment());
		luType.setUpdateUserId(luTypeInfo.getUpdateUserId());
	}

	public static Lui createLui(LuiInfo luiInfo) {
		Lui lui = new Lui();
		updateLui(luiInfo, lui);
		return lui;
	}

	public static void updateLui(LuiInfo luiInfo, Lui lui) {
		Atp atp = createAtp(luiInfo.getAtpDisplay());
		lui.setAtp(atp);
		Clu clu = createClu(luiInfo.getCluDisplay());
		lui.setClu(clu);
		lui.setLuiCode(luiInfo.getLuiCode());
		lui.setMaxSeats(luiInfo.getMaxSeats());
		lui.setLuiId(luiInfo.getLuiId());
	}

	public static CluRelation createCluRelation(CluRelationInfo cluRelationInfo) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation(cluRelationInfo, cluRelation);
		return cluRelation;
	}

	public static void updateCluRelation(CluRelationInfo cluRelationInfo,
			CluRelation cluRelation) {
		Clu clu = createClu(cluRelationInfo.getCluDisplay());
		cluRelation.setClu(clu);
		cluRelation.setCreateTime(cluRelationInfo.getCreateTime());
		cluRelation
				.setCreateUserComment(cluRelationInfo.getCreateUserComment());
		cluRelation.setCreateUserId(cluRelationInfo.getCreateUserComment());
		cluRelation.setEffectiveEndDate(cluRelationInfo.getEffectiveEndDate());
		cluRelation.setEffectiveStartDate(cluRelationInfo
				.getEffectiveStartDate());
		// cluRelation.setLuRelationType(cluRelationInfo.getLuRelationTypeId());
		// @FIX_ME
		clu = createClu(cluRelationInfo.getRelatedCluDisplay());
		cluRelation.setRelatedClu(clu);
		cluRelation.setUpdateTime(cluRelationInfo.getUpdateTime());
		cluRelation
				.setUpdateUserComment(cluRelationInfo.getUpdateUserComment());
		cluRelation.setUpdateUserId(cluRelationInfo.getUpdateUserId());
	}

	public static CluRelationDisplay createCluRelationDisplay(
			CluRelation cluRelation) {
		CluRelationDisplay cluRelationDisplay = new CluRelationDisplay();
		CluDisplay cluDisplay = createCluDisplay(cluRelation.getClu());
		cluRelationDisplay.setCluDisplay(cluDisplay);
		cluRelationDisplay.setLuRelationTypeId(cluRelation.getLuRelationType()
				.getId());
		cluDisplay = createCluDisplay(cluRelation.getRelatedClu());
		cluRelationDisplay.setRelatedCluDisplay(cluDisplay);
		return cluRelationDisplay;
	}

	public static CluRelation createCluRelation(
			CluRelationDisplay cluRelationDisplay) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation(cluRelationDisplay, cluRelation);
		return cluRelation;
	}

	public static void updateCluRelation(CluRelationDisplay cluRelationDisplay,
			CluRelation cluRelation) {
		Clu clu = createClu(cluRelationDisplay.getCluDisplay());
		cluRelation.setClu(clu);
		// cluRelation.setLuRelationType(cluRelationInfo.getLuRelationTypeId());
		// @FIX_ME
		clu = createClu(cluRelationDisplay.getRelatedCluDisplay());
		cluRelation.setRelatedClu(clu);
	}

	public static CluRelationAssignInfo createCluRelationAssignInfo(
			CluRelation cluRelation) {
		CluRelationAssignInfo cluRelationAssignInfo = new CluRelationAssignInfo();
		cluRelationAssignInfo.setEffectiveEndDate(cluRelation
				.getEffectiveEndDate());
		cluRelationAssignInfo.setEffectiveStartDate(cluRelation
				.getEffectiveStartDate());
		return cluRelationAssignInfo;
	}

	public static CluRelation createCluRelation(
			CluRelationAssignInfo cluRelationAssignInfo) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation(cluRelationAssignInfo, cluRelation);
		return cluRelation;
	}

	public static void updateCluRelation(
			CluRelationAssignInfo cluRelationAssignInfo, CluRelation cluRelation) {
		cluRelation.setEffectiveEndDate(cluRelationAssignInfo
				.getEffectiveEndDate());
		cluRelation.setEffectiveStartDate(cluRelationAssignInfo
				.getEffectiveStartDate());
	}

	public static CluRelationUpdateInfo createCluRelationUpdateInfo(
			CluRelation cluRelation) {
		CluRelationUpdateInfo cluRelationUpdateInfo = new CluRelationUpdateInfo();
		cluRelationUpdateInfo.setEffectiveEndDate(cluRelation
				.getEffectiveEndDate());
		cluRelationUpdateInfo.setEffectiveStartDate(cluRelation
				.getEffectiveStartDate());
		return cluRelationUpdateInfo;
	}

	public static CluRelation createCluRelation(
			CluRelationUpdateInfo cluRelationUpdateInfo) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation(cluRelationUpdateInfo, cluRelation);
		return cluRelation;
	}

	public static void updateCluRelation(
			CluRelationUpdateInfo cluRelationUpdateInfo, CluRelation cluRelation) {
		cluRelation.setEffectiveEndDate(cluRelationUpdateInfo
				.getEffectiveEndDate());
		cluRelation.setEffectiveStartDate(cluRelationUpdateInfo
				.getEffectiveStartDate());
	}

	public static CluCreateInfo createCluCreateInfo(Clu clu) {
		CluCreateInfo cluCreateInfo = new CluCreateInfo();
		cluCreateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle()
				.getAtpId());
		cluCreateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle()
				.getAtpId());
		cluCreateInfo.setCluLongName(clu.getCluLongName());
		cluCreateInfo.setCluShortName(clu.getCluShortName());
		cluCreateInfo.setDescription(clu.getDescription());
		cluCreateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluCreateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return cluCreateInfo;
	}

	public static Clu createClu(CluCreateInfo cluCreateInfo) {
		Clu clu = new Clu();
		updateClu(cluCreateInfo, clu);
		return clu;
	}

	public static void updateClu(CluCreateInfo cluCreateInfo, Clu clu) {
		Atp atp = new Atp();
		atp.setAtpId(cluCreateInfo.getEffectiveStartCycle());
		clu.setEffectiveStartCycle(atp);
		atp = new Atp();
		atp.setAtpId(cluCreateInfo.getEffectiveEndCycle());
		clu.setEffectiveEndCycle(atp);
		clu.setCluLongName(cluCreateInfo.getCluLongName());
		clu.setCluShortName(cluCreateInfo.getCluShortName());
		clu.setDescription(cluCreateInfo.getDescription());
		clu.setEffectiveEndDate(cluCreateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(cluCreateInfo.getEffectiveStartDate());
	}

	public static CluSetCreateInfo createCluSetCreateInfo(Clu clu) {
		CluSetCreateInfo cluSetCreateInfo = new CluSetCreateInfo();
		cluSetCreateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle()
				.getAtpId());
		cluSetCreateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle()
				.getAtpId());
		cluSetCreateInfo.setDescription(clu.getDescription());
		cluSetCreateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluSetCreateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return cluSetCreateInfo;
	}

	public static Clu createClu(CluSetCreateInfo cluSetCreateInfo) {
		Clu clu = new Clu();
		updateClu(cluSetCreateInfo, clu);
		return clu;
	}

	public static void updateClu(CluSetCreateInfo cluSetCreateInfo, Clu clu) {
		Atp atp = new Atp();
		atp.setAtpId(cluSetCreateInfo.getEffectiveStartCycle());
		clu.setEffectiveStartCycle(atp);
		atp = new Atp();
		atp.setAtpId(cluSetCreateInfo.getEffectiveEndCycle());
		clu.setEffectiveEndCycle(atp);
		clu.setDescription(cluSetCreateInfo.getDescription());
		clu.setEffectiveEndDate(cluSetCreateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(cluSetCreateInfo.getEffectiveStartDate());
	}

	public static CluSetDisplay createCluSetDisplay(Clu clu) {
		CluSetDisplay cluSetDisplay = new CluSetDisplay();
		cluSetDisplay.setCluSetId(clu.getCluId());
		cluSetDisplay.setCluSetName(clu.getCluShortName());
		return cluSetDisplay;
	}

	public static Clu createClu(CluSetDisplay cluSetDisplay) {
		Clu clu = new Clu();
		updateClu(cluSetDisplay, clu);
		return clu;
	}

	public static void updateClu(CluSetDisplay cluSetDisplay, Clu clu) {
		clu.setCluShortName(cluSetDisplay.getCluSetName());
		clu.setCluId(cluSetDisplay.getCluSetId());
		clu.setCluShortName(cluSetDisplay.getCluSetName());
	}

	public static CluSetInfo createCluSetInfo(Clu clu) {
		CluSetInfo cluSetInfo = new CluSetInfo();
		cluSetInfo.setDescription(clu.getDescription());
		cluSetInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluSetInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		cluSetInfo.setCluSetId(clu.getCluId());
		cluSetInfo.setCluSetName(clu.getCluShortName());
		return cluSetInfo;
	}

	public static Clu createClu(CluSetInfo cluSetInfo) {
		Clu clu = new Clu();
		updateClu(cluSetInfo, clu);
		return clu;
	}

	public static void updateClu(CluSetInfo cluSetInfo, Clu clu) {
		clu.setDescription(cluSetInfo.getDescription());
		clu.setEffectiveEndDate(cluSetInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(cluSetInfo.getEffectiveStartDate());
		clu.setCluId(cluSetInfo.getCluSetId());
		clu.setCluShortName(cluSetInfo.getCluSetName());
	}

	public static CluSetUpdateInfo createCluSetUpdateInfo(Clu clu) {
		CluSetUpdateInfo cluSetUpdateInfo = new CluSetUpdateInfo();
		cluSetUpdateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle()
				.getAtpId());
		cluSetUpdateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle()
				.getAtpId());
		cluSetUpdateInfo.setDescription(clu.getDescription());
		cluSetUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluSetUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		cluSetUpdateInfo.setCluSetName(clu.getCluShortName());
		return cluSetUpdateInfo;
	}

	public static Clu createClu(CluSetUpdateInfo cluSetUpdateInfo) {
		Clu clu = new Clu();
		updateClu(cluSetUpdateInfo, clu);
		return clu;
	}

	public static void updateClu(CluSetUpdateInfo cluSetUpdateInfo, Clu clu) {
		Atp atp = new Atp();
		atp.setAtpId(cluSetUpdateInfo.getEffectiveStartCycle());
		clu.setEffectiveStartCycle(atp);
		atp = new Atp();
		atp.setAtpId(cluSetUpdateInfo.getEffectiveEndCycle());
		clu.setEffectiveEndCycle(atp);
		clu.setDescription(cluSetUpdateInfo.getDescription());
		clu.setEffectiveEndDate(cluSetUpdateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(cluSetUpdateInfo.getEffectiveStartDate());
		clu.setCluShortName(cluSetUpdateInfo.getCluSetName());
	}

	public static CluUpdateInfo createCluUpdateInfo(Clu clu) {
		CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
		cluUpdateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle()
				.getAtpId());
		cluUpdateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle()
				.getAtpId());
		cluUpdateInfo.setCluLongName(clu.getCluLongName());
		cluUpdateInfo.setCluShortName(clu.getCluShortName());
		cluUpdateInfo.setDescription(clu.getDescription());
		cluUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return cluUpdateInfo;
	}

	// public static Clu createClu(CluUpdateInfo cluUpdateInfo) {
	// Clu clu = new Clu();
	// updateClu(cluUpdateInfo, clu);
	// return clu;
	// }

	/**
	 * Updates the clu parameter with the cluUpdateInfo for non null values
	 * 
	 * @param cluUpdateInfo
	 * @param clu
	 *            Mutable
	 * @param dao
	 */
	public static void updateClu(CluUpdateInfo cluUpdateInfo, Clu clu, LuDao dao) {
		if (cluUpdateInfo.getCluLongName() != null) {
			clu.setCluLongName(cluUpdateInfo.getCluLongName());
		}
		if (cluUpdateInfo.getCluShortName() != null) {
			clu.setCluShortName(cluUpdateInfo.getCluShortName());
		}
		if (cluUpdateInfo.getDescription() != null) {
			clu.setDescription(cluUpdateInfo.getDescription());
		}
		if (cluUpdateInfo.getEffectiveEndDate() != null) {
			clu.setEffectiveEndDate(cluUpdateInfo.getEffectiveEndDate());
		}
		if (cluUpdateInfo.getEffectiveStartDate() != null) {
			clu.setEffectiveStartDate(cluUpdateInfo.getEffectiveStartDate());
		}
		if (cluUpdateInfo.getEffectiveEndCycle() != null) {
			clu.setEffectiveEndCycle(dao.fetchAtp(cluUpdateInfo
					.getEffectiveEndCycle()));
		}
		if (cluUpdateInfo.getEffectiveStartCycle() != null) {
			clu.setEffectiveStartCycle(dao.fetchAtp(cluUpdateInfo
					.getEffectiveStartCycle()));
		}

		// Update the attributes that exist already and remove each one from the
		// UpdateInfo
		for (LuAttribute updateAttr : clu.getAttributes()) {
			if (cluUpdateInfo.getAttributes().containsKey(
					updateAttr.getLuAttributeType().getName())) {
				updateAttr.setValue(cluUpdateInfo.getAttributes().remove(
						updateAttr.getLuAttributeType().getName()));
			}
		}

		// Create a list of all the new attributes that are to be updated
		LuType luType = clu.getLuType();
		for (LuAttributeType luAttributeType : luType.getLuAttributeTypes()) {
			if (cluUpdateInfo.getAttributes().containsKey(
					luAttributeType.getName())) {
				LuAttribute luAttr = new LuAttribute();
				luAttr.setValue(cluUpdateInfo.getAttributes().get(
						luAttributeType.getName()));
				luAttr.setLuAttributeType(luAttributeType);
				luAttr.setClu(clu);
				clu.getAttributes().add(luAttr);
			}
		}
	}

	public static LuiCreateInfo createLuiCreateInfo(Lui lui) {
		LuiCreateInfo luiCreateInfo = new LuiCreateInfo();
		// luiCreateInfo.setAttributes(attributes) @FIX_ME
		Clu clu = lui.getClu();
		luiCreateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiCreateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return luiCreateInfo;
	}

	public static Lui createLui(LuiCreateInfo luiCreateInfo) {
		Lui lui = new Lui();
		updateLui(luiCreateInfo, lui);
		return lui;
	}

	public static void updateLui(LuiCreateInfo luiCreateInfo, Lui lui) {
		Clu clu = new Clu();
		clu.setEffectiveEndDate(luiCreateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(luiCreateInfo.getEffectiveStartDate());
		lui.setClu(clu);
	}

	public static Lui createLui(LuiDisplay luiDisplay) {
		Lui lui = new Lui();
		updateLui(luiDisplay, lui);
		return lui;
	}

	public static void updateLui(LuiDisplay luiDisplay, Lui lui) {
		Atp atp = createAtp(luiDisplay.getAtpDisplay());
		lui.setAtp(atp);
		Clu clu = createClu(luiDisplay.getCluDisplay());
		lui.setClu(clu);
		lui.setLuiCode(luiDisplay.getLuiCode());
		lui.setLuiId(luiDisplay.getLuiId());
	}

	public static LuiRelationAssignInfo createLuiRelationAssignInfo(Lui lui) {
		LuiRelationAssignInfo luiRelationAssignInfo = new LuiRelationAssignInfo();
		Clu clu = lui.getClu();
		luiRelationAssignInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiRelationAssignInfo
				.setEffectiveStartDate(clu.getEffectiveStartDate());
		return luiRelationAssignInfo;
	}

	public static Lui createLui(LuiRelationAssignInfo luiRelationAssignInfo) {
		Lui lui = new Lui();
		updateLui(luiRelationAssignInfo, lui);
		return lui;
	}

	public static void updateLui(LuiRelationAssignInfo luiRelationAssignInfo,
			Lui lui) {
		Clu clu = new Clu();
		clu.setEffectiveEndDate(luiRelationAssignInfo.getEffectiveEndDate());
		clu
				.setEffectiveStartDate(luiRelationAssignInfo
						.getEffectiveStartDate());
		lui.setClu(clu);
	}

	public static LuiRelationDisplay createLuiRelationDisplay(Lui lui) {
		LuiRelationDisplay luiRelationDisplay = new LuiRelationDisplay();
		LuiDisplay luiDisplay = createLuiDisplay(lui);
		luiRelationDisplay.setLuiDisplay(luiDisplay);
		// luiRelationDisplay.setLuRelationTypeId(luRelationTypeId) @FIX_ME
		// luiRelationDisplay.setRelatedLuiDisplay(relatedLuiDisplay) @FIX_ME
		return luiRelationDisplay;
	}

	public static Lui createLui(LuiRelationDisplay luiRelationDisplay) {
		Lui lui = new Lui();
		updateLui(luiRelationDisplay, lui);
		return lui;
	}

	public static void updateLui(LuiRelationDisplay luiRelationDisplay, Lui lui) {
		// @FIX_ME
	}

	public static LuiRelationInfo createLuiRelationInfo(Lui lui) {
		LuiRelationInfo luiRelationInfo = new LuiRelationInfo();
		Clu clu = lui.getClu();
		luiRelationInfo.setCreateTime(clu.getCreateTime());
		luiRelationInfo.setCreateUserComment(clu.getCreateUserComment());
		luiRelationInfo.setCreateUserId(clu.getCreateUserId());
		luiRelationInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiRelationInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		LuiDisplay luiDisplay = createLuiDisplay(lui);
		luiRelationInfo.setLuiDisplay(luiDisplay);
		// luiRelationInfo.setLuRelationTypeId(clu.) @FIX_ME
		// luiRelationInfo.setRelatedLuiDisplay(relatedLuiDisplay) @FIX_ME
		luiRelationInfo.setUpdateTime(clu.getUpdateTime());
		luiRelationInfo.setUpdateUserComment(clu.getUpdateUserComment());
		luiRelationInfo.setUpdateUserId(clu.getUpdateUserId());
		return luiRelationInfo;
	}

	public static Lui createLui(LuiRelationInfo luiRelationInfo) {
		Lui lui = new Lui();
		updateLui(luiRelationInfo, lui);
		return lui;
	}

	public static void updateLui(LuiRelationInfo luiRelationInfo, Lui lui) {
		Clu clu = lui.getClu();
		clu.setCreateTime(luiRelationInfo.getCreateTime());
		clu.setCreateUserComment(luiRelationInfo.getCreateUserComment());
		clu.setCreateUserId(luiRelationInfo.getCreateUserId());
		clu.setEffectiveEndDate(luiRelationInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(luiRelationInfo.getEffectiveStartDate());
		clu.setUpdateTime(luiRelationInfo.getUpdateTime());
		clu.setUpdateUserComment(luiRelationInfo.getUpdateUserComment());
		clu.setUpdateUserId(luiRelationInfo.getUpdateUserId());
		updateLui(luiRelationInfo.getLuiDisplay(), lui);
	}

	public static LuiUpdateInfo createLuiUpdateInfo(Lui lui) {
		LuiUpdateInfo luiUpdateInfo = new LuiUpdateInfo();
		Clu clu = lui.getClu();
		luiUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return luiUpdateInfo;
	}

	public static Lui createLui(LuiUpdateInfo luiUpdateInfo) {
		Lui lui = new Lui();
		updateLui(luiUpdateInfo, lui);
		return lui;
	}

	public static void updateLui(LuiUpdateInfo luiUpdateInfo, Lui lui) {
		Clu clu = new Clu();
		clu.setEffectiveEndDate(luiUpdateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(luiUpdateInfo.getEffectiveStartDate());
		lui.setClu(clu);
	}

	public static LuTypeInfo createLuTypeInfo(Lui lui) {
		LuTypeInfo luiTypeInfo = new LuTypeInfo();
		Clu clu = lui.getClu();
		luiTypeInfo.setCreateTime(clu.getCreateTime());
		luiTypeInfo.setCreateUserComment(clu.getCreateUserComment());
		luiTypeInfo.setCreateUserId(clu.getCreateUserId());
		luiTypeInfo.setDescription(clu.getDescription());
		// luiTypeInfo.setLuTypeKey()@FIX_ME
		luiTypeInfo.setUpdateTime(clu.getUpdateTime());
		luiTypeInfo.setUpdateUserComment(clu.getUpdateUserComment());
		luiTypeInfo.setUpdateUserId(clu.getUpdateUserId());
		return luiTypeInfo;
	}

	public static Lui createLui(LuTypeInfo luiTypeInfo) {
		Lui lui = new Lui();
		updateLui(luiTypeInfo, lui);
		return lui;
	}

	public static void updateLui(LuTypeInfo luiTypeInfo, Lui lui) {
		Clu clu = lui.getClu();
		clu.setCreateTime(luiTypeInfo.getCreateTime());
		clu.setCreateUserComment(luiTypeInfo.getCreateUserComment());
		clu.setCreateUserId(luiTypeInfo.getCreateUserId());
		clu.setDescription(luiTypeInfo.getDescription());
		clu.setUpdateTime(luiTypeInfo.getUpdateTime());
		clu.setUpdateUserComment(luiTypeInfo.getUpdateUserComment());
		clu.setUpdateUserId(luiTypeInfo.getUpdateUserId());
	}

	public static LuTypeInfo createLuTypeInfo(LuType luType) {
		LuTypeInfo luTypeInfo = new LuTypeInfo();
		BeanUtils.copyProperties(luType, luTypeInfo);
		luTypeInfo.setLuTypeKey(luType.getLuTypeId());
		return luTypeInfo;
	}

	public static Clu createClu(String luTypeId, CluCreateInfo cluCreateInfo,
			LuDao dao) {
		Clu clu = new Clu();
		BeanUtils.copyProperties(cluCreateInfo, clu, new String[] {
				"effectiveEndCycle", "effectiveStartCycle", "attributes" });
		clu.setEffectiveEndCycle(dao.fetchAtp(cluCreateInfo
				.getEffectiveEndCycle()));
		clu.setEffectiveStartCycle(dao.fetchAtp(cluCreateInfo
				.getEffectiveStartCycle()));
		LuType luType = dao.fetchLuType(luTypeId);
		clu.setLuType(luType);
		// Add all the attributes that match from the LuType
		for (LuAttributeType luAttributeType : luType.getLuAttributeTypes()) {
			if (cluCreateInfo.getAttributes().containsKey(
					luAttributeType.getName())) {
				LuAttribute luAttr = new LuAttribute();
				luAttr.setValue(cluCreateInfo.getAttributes().get(
						luAttributeType.getName()));
				luAttr.setLuAttributeType(luAttributeType);
				luAttr.setClu(clu);
				clu.getAttributes().add(luAttr);
			}
		}
		return clu;
	}

	public static CluInfo createCluInfo(Clu clu) {
		CluInfo cluInfo = new CluInfo();
		BeanUtils.copyProperties(clu, cluInfo, new String[] {
				"effectiveEndCycle", "effectiveStartCycle", "attributes" });
		cluInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle().getAtpId());
		cluInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle().getAtpId());
		cluInfo.setLuTypeId(clu.getLuType().getLuTypeId());
		for (LuAttribute attr : clu.getAttributes()) {
			cluInfo.getAttributes().put(attr.getLuAttributeType().getName(),
					attr.getValue());
		}
		return cluInfo;
	}

	public static CluDisplay createCluDisplay(Clu clu) {
		CluDisplay cluDisplay = new CluDisplay();
		cluDisplay
				.setAtpDisplayEnd(createAtpDisplay(clu.getEffectiveEndCycle()));
		cluDisplay.setAtpDisplayStart(createAtpDisplay(clu
				.getEffectiveStartCycle()));
		cluDisplay.setCluShortName(clu.getCluShortName());
		cluDisplay.setCluId(clu.getCluId());
		cluDisplay.setLuTypeId(clu.getLuType().getLuTypeId());
		return cluDisplay;
	}

	public static AtpDisplay createAtpDisplay(Atp atp) {
		AtpDisplay atpDisplay = new AtpDisplay();
		atpDisplay.setAtpId(atp.getAtpId());
		atpDisplay.setAtpName(atp.getAtpName());
		return atpDisplay;
	}

	public static Lui createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo, LuDao dao) {
		// FIXME - the Lui class does not match the luiCreate info
		// need to add attributes to lui?
		Lui lui = new Lui();
		lui.setAtp(dao.fetchAtp(atpId));
		lui.setClu(dao.fetchClu(cluId));
		// set the attributes
		return lui;
	}

	public static LuiRelationInfo createLuiRelationInfo(LuiRelation luiRelation) {
		LuiRelationInfo luiRelationInfo = new LuiRelationInfo();
		BeanUtils.copyProperties(luiRelation, luiRelationInfo);
		luiRelationInfo.setLuiDisplay(createLuiDisplay(luiRelation.getLui()));
		luiRelationInfo.setRelatedLuiDisplay(createLuiDisplay(luiRelation
				.getRelatedLui()));
		luiRelationInfo.setLuRelationTypeId(luiRelation.getLuRelationType()
				.getId());
		return luiRelationInfo;
	}

	public static LuiDisplay createLuiDisplay(Lui lui) {
		LuiDisplay luiDisplay = new LuiDisplay();
		luiDisplay.setAtpDisplay(createAtpDisplay(lui.getAtp()));
		luiDisplay.setCluDisplay(createCluDisplay(lui.getClu()));
		luiDisplay.setLuiCode(lui.getLuiCode());
		luiDisplay.setLuiId(lui.getLuiId());
		luiDisplay.setLuTypeKey(lui.getClu().getLuType().getLuTypeId());
		return luiDisplay;
	}

	public static CluRelationInfo createCluRelationInfo(CluRelation cluRelation) {
		CluRelationInfo cluRelationInfo = new CluRelationInfo();
		BeanUtils.copyProperties(cluRelation, cluRelationInfo);
		cluRelationInfo.setCluDisplay(createCluDisplay(cluRelation.getClu()));
		cluRelationInfo.setRelatedCluDisplay(createCluDisplay(cluRelation
				.getRelatedClu()));
		cluRelationInfo.setLuRelationTypeId(cluRelation.getLuRelationType()
				.getId());
		return cluRelationInfo;
	}

	public static CluSetInfo createCluSetInfo(CluSet cluSet) {
		CluSetInfo cluSetInfo = new CluSetInfo();
		BeanUtils.copyProperties(cluSet, cluSetInfo,
				new String[] { "cluCriteria" });
		cluSetInfo.setCluCriteria(createCluCriteria(cluSet.getCluCriteria()));
		return cluSetInfo;
	}

	public static CluCriteria createCluCriteria(CluCrit cluCrit) {
		CluCriteria cluCriteria = new CluCriteria();
		cluCriteria.setLuTypeKey(cluCrit.getLuTypeKey());
		for (SearchKeyValue entry : cluCrit.getSearchKeyValues()) {
			cluCriteria.getSearchKeyValue().put(entry.getKeyName(),
					entry.getValue());
		}
		return cluCriteria;
	}

	public static LuiInfo createLuiInfo(Lui lui) {
		LuiInfo luiInfo = new LuiInfo();
		BeanUtils.copyProperties(lui, luiInfo);
		luiInfo.setAtpDisplay(createAtpDisplay(lui.getAtp()));
		luiInfo.setCluDisplay(createCluDisplay(lui.getClu()));
		luiInfo.setLuTypeKey(lui.getClu().getLuType().getLuTypeId());
		// FIXME copy attributes when they are added
		return luiInfo;
	}

	public static CluSet createCluSet(String cluSetName,
			CluSetCreateInfo cluSetCreateInfo, CluCriteria cluCriteria,
			LuDao dao) {
		CluSet cluSet = new CluSet();
		if (cluCriteria != null) {
			CluCrit cluCrit = new CluCrit();
			cluCrit.setLuTypeKey(cluCriteria.getLuTypeKey());
			for (Map.Entry<String, String> a : cluCriteria.getSearchKeyValue()
					.entrySet()) {
				SearchKeyValue s = new SearchKeyValue();
				s.setKeyName(a.getKey());
				s.setValue(a.getValue());
				cluCrit.getSearchKeyValues().add(s);
			}
			cluSet.setCluCriteria(cluCrit);
		}
		cluSet.setCluSetName(cluSetName);
		cluSet.setDescription(cluSetCreateInfo.getDescription());
		cluSet.setEffectiveEndDate(cluSetCreateInfo.getEffectiveEndDate());
		cluSet.setEffectiveStartDate(cluSetCreateInfo.getEffectiveStartDate());
		cluSet.setEffectiveEndCycle(dao.fetchAtp(cluSetCreateInfo
				.getEffectiveEndCycle()));
		cluSet.setEffectiveStartCycle(dao.fetchAtp(cluSetCreateInfo
				.getEffectiveStartCycle()));
		return cluSet;
	}
}
