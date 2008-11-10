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
package edu.umd.ks.poc.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import edu.umd.ks.poc.lum.lu.dao.LuDao;
import edu.umd.ks.poc.lum.lu.dto.AtpDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluCriteria;
import edu.umd.ks.poc.lum.lu.dto.CluDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationAssignInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluRelationInfo;
import edu.umd.ks.poc.lum.lu.dto.CluRelationUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetDisplay;
import edu.umd.ks.poc.lum.lu.dto.CluSetInfo;
import edu.umd.ks.poc.lum.lu.dto.CluSetUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuTypeInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiCreateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuiInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationAssignInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationDisplay;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiRelationUpdateInfo;
import edu.umd.ks.poc.lum.lu.dto.LuiUpdateInfo;
import edu.umd.ks.poc.lum.lu.entity.Atp;
import edu.umd.ks.poc.lum.lu.entity.Clu;
import edu.umd.ks.poc.lum.lu.entity.CluAttribute;
import edu.umd.ks.poc.lum.lu.entity.CluCrit;
import edu.umd.ks.poc.lum.lu.entity.CluRelation;
import edu.umd.ks.poc.lum.lu.entity.CluSet;
import edu.umd.ks.poc.lum.lu.entity.LuAttributeType;
import edu.umd.ks.poc.lum.lu.entity.LuType;
import edu.umd.ks.poc.lum.lu.entity.Lui;
import edu.umd.ks.poc.lum.lu.entity.LuiAttribute;
import edu.umd.ks.poc.lum.lu.entity.LuiRelation;
import edu.umd.ks.poc.lum.lu.entity.SearchKeyValue;

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
		CluAttribute luAttribute = new CluAttribute();
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

	public static LuType createLuType(LuTypeInfo luTypeInfo,LuDao dao) {
		LuType luType = new LuType();
		updateLuType(luTypeInfo, luType, dao);
		return luType;
	}

	public static void updateLuType(LuTypeInfo luTypeInfo, LuType luType,LuDao dao) {
		luType.setCreateTime(luTypeInfo.getCreateTime());
		luType.setCreateUserComment(luTypeInfo.getCreateUserComment());
		luType.setCreateUserId(luTypeInfo.getCreateUserId());
		luType.setUpdateTime(luTypeInfo.getUpdateTime());
		luType.setUpdateUserComment(luTypeInfo.getUpdateUserComment());
		luType.setUpdateUserId(luTypeInfo.getUpdateUserId());
		luType.setDescription(luTypeInfo.getDescription());
		luType.setStatus(luTypeInfo.getStatus());
		//Clear the attribute types and add the new ones
		luType.getLuAttributeTypes().clear();
		for(String luAttrTypeId:luTypeInfo.getLuAttributeTypeIds()){
			LuAttributeType luAttrType = dao.fetchLuAttributeType(luAttrTypeId);
			if(luAttrType!=null){
				luType.getLuAttributeTypes().add(luAttrType);
				luAttrType.getLuTypes().add(luType);
			}
		}
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
		if (cluRelationUpdateInfo.getEffectiveEndDate() != null) {
			cluRelation.setEffectiveEndDate(cluRelationUpdateInfo
					.getEffectiveEndDate());
		}
		if (cluRelationUpdateInfo.getEffectiveStartDate() != null) {
			cluRelation.setEffectiveStartDate(cluRelationUpdateInfo
					.getEffectiveStartDate());
		}
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
		clu.setStatus(cluUpdateInfo.getStatus());
		clu.setAdminDepartment(cluUpdateInfo.getAdminDepartment());
		// Update the attributes that exist already and remove each one from the
		// UpdateInfo
		for (CluAttribute updateAttr : clu.getAttributes()) {
			if (cluUpdateInfo.getAttributes().containsKey(
					updateAttr.getLuAttributeType().getName())) {
				updateAttr.setValues(cluUpdateInfo.getAttributes().remove(
						updateAttr.getLuAttributeType().getName()));
			}
		}

		// Create a list of all the new attributes that are to be updated
		LuType luType = clu.getLuType();
		for (LuAttributeType luAttributeType : luType.getLuAttributeTypes()) {
			if (cluUpdateInfo.getAttributes().containsKey(
					luAttributeType.getName())) {
				CluAttribute luAttr = new CluAttribute();
				luAttr.setValues(cluUpdateInfo.getAttributes().get(
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
		//FIXME The LuiUpdateInfo seems incompatable with Lui
		//luiUpdateInfo.getEffectiveEndDate();
		//luiUpdateInfo.getEffectiveStartDate();
		
		// Update the attributes (do we make the attributes match whats in the update info? or just add/update and no deletes)
		Map<String,List<String>> attributes = luiUpdateInfo.getAttributes();
		
		//Delete all attributes
		lui.getAttributes().clear();
		lui.setStatus(luiUpdateInfo.getStatus());
		//Add in all the attributes
		for(LuAttributeType attrType:lui.getClu().getLuType().getLuAttributeTypes()){
			if(attributes.containsKey(attrType.getName())){
				LuiAttribute luiAttr = new LuiAttribute();
				luiAttr.setLuAttributeType(attrType);
				luiAttr.setValues(attributes.get(attrType.getName()));
				luiAttr.setLui(lui);
				lui.getAttributes().add(luiAttr);
			}
		}
		
	}

	public static LuTypeInfo createLuTypeInfo(LuType luType) {
		LuTypeInfo luTypeInfo = new LuTypeInfo();
		BeanUtils.copyProperties(luType, luTypeInfo);
		luTypeInfo.setLuTypeKey(luType.getLuTypeId());
		for(LuAttributeType luAttrType:luType.getLuAttributeTypes()){
			luTypeInfo.getLuAttributeTypeIds().add(luAttrType.getId());
		}
		return luTypeInfo;
	}

	public static Clu createClu(String luTypeId, CluCreateInfo cluCreateInfo,
			LuDao dao) {
		Clu clu = new Clu();
		BeanUtils.copyProperties(cluCreateInfo, clu, new String[] {
				"effectiveEndCycle", "effectiveStartCycle", "attributes" });
		if(cluCreateInfo.getEffectiveEndCycle()!=null){
			clu.setEffectiveEndCycle(dao.fetchAtp(cluCreateInfo
					.getEffectiveEndCycle()));
		}
		if(cluCreateInfo.getEffectiveStartCycle()!=null){
			clu.setEffectiveStartCycle(dao.fetchAtp(cluCreateInfo
					.getEffectiveStartCycle()));
		}
		LuType luType = dao.fetchLuType(luTypeId);
		clu.setLuType(luType);
		// Add all the attributes that match from the LuType
		for (LuAttributeType luAttributeType : luType.getLuAttributeTypes()) {
			if (cluCreateInfo.getAttributes().containsKey(
					luAttributeType.getName())) {
				CluAttribute luAttr = new CluAttribute();
				luAttr.setValues(cluCreateInfo.getAttributes().get(
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
		cluInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle()==null?null:clu.getEffectiveEndCycle().getAtpId());
		cluInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle()==null?null:clu.getEffectiveStartCycle().getAtpId());
		cluInfo.setLuTypeId(clu.getLuType().getLuTypeId());
		for (CluAttribute attr : clu.getAttributes()) {
			List<String> values = new ArrayList<String>();
			values.addAll(attr.getValues());
			cluInfo.getAttributes().put(attr.getLuAttributeType().getName(),
					values);
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
		if(atp!=null){
			atpDisplay.setAtpId(atp.getAtpId());
			atpDisplay.setAtpName(atp.getAtpName());
		}
		return atpDisplay;
	}

	public static Lui createLui(String cluId, String atpId,
			LuiCreateInfo luiCreateInfo, LuDao dao) {
		Lui lui = new Lui();
		
		// Do Lookups
		Atp atp = dao.fetchAtp(atpId);
		Clu clu = dao.fetchClu(cluId);
		
		// Set base information
		lui.setAtp(atp);
		lui.setClu(clu);

		// Set the attributes
		Map<String,List<String>> attributes = luiCreateInfo.getAttributes();
		for(LuAttributeType attrType:clu.getLuType().getLuAttributeTypes()){
			if(attributes.containsKey(attrType.getName())){
				LuiAttribute luiAttr = new LuiAttribute();
				luiAttr.setLuAttributeType(attrType);
				luiAttr.setValues(attributes.get(attrType.getName()));
				luiAttr.setLui(lui);
				lui.getAttributes().add(luiAttr);
			}
		}
		
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
		BeanUtils.copyProperties(lui, luiInfo,new String[] {"attributes" });
		luiInfo.setAtpDisplay(createAtpDisplay(lui.getAtp()));
		luiInfo.setCluDisplay(createCluDisplay(lui.getClu()));
		luiInfo.setLuTypeKey(lui.getClu().getLuType().getLuTypeId());
		luiInfo.setStatus(lui.getStatus());
		for(LuiAttribute luiAttr:lui.getAttributes()){
			List<String> values = new ArrayList<String>();
			values.addAll(luiAttr.getValues());
			luiInfo.getAttributes().put(luiAttr.getLuAttributeType().getName(), values);
		}
		
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

	public static void updateCluSet(CluSet cluSet,
			CluSetUpdateInfo cluSetUpdateInfo, LuDao dao) {
		if (cluSetUpdateInfo.getCluSetName() != null) {
			cluSet.setCluSetName(cluSetUpdateInfo.getCluSetName());
		}
		if (cluSetUpdateInfo.getDescription() != null) {
			cluSet.setDescription(cluSetUpdateInfo.getDescription());
		}
		if (cluSetUpdateInfo.getEffectiveEndDate() != null) {
			cluSet.setEffectiveEndDate(cluSetUpdateInfo.getEffectiveEndDate());
		}
		if (cluSetUpdateInfo.getEffectiveStartDate() != null) {
			cluSet.setEffectiveStartDate(cluSetUpdateInfo
					.getEffectiveStartDate());
		}
		if (cluSetUpdateInfo.getEffectiveEndCycle() != null) {
			cluSet.setEffectiveEndCycle(dao.fetchAtp(cluSetUpdateInfo
					.getEffectiveEndCycle()));
		}
		if (cluSetUpdateInfo.getEffectiveStartCycle() != null) {
			cluSet.setEffectiveStartCycle(dao.fetchAtp(cluSetUpdateInfo
					.getEffectiveStartCycle()));
		}

	}

	public static void updateLuiRelation(
			LuiRelationUpdateInfo luiRelationUpdateInfo, LuiRelation luiRelation) {
		luiRelation.setEffectiveEndDate(luiRelationUpdateInfo
				.getEffectiveEndDate());
		luiRelation.setEffectiveStartDate(luiRelationUpdateInfo
				.getEffectiveStartDate());
	}

	public static LuAttributeType createLuAttributeType(
			LuAttributeTypeInfo luAttributeTypeInfo) {
		LuAttributeType luAttrType = new LuAttributeType();
		updateLuAttributeType(luAttributeTypeInfo, luAttrType);
		return luAttrType;
	}

	public static LuAttributeTypeInfo createLuAttributeTypeInfo(
			LuAttributeType luAttrType) {
		LuAttributeTypeInfo luAttributeTypeInfo = new LuAttributeTypeInfo();
		luAttributeTypeInfo.setId(luAttrType.getId());		
		luAttributeTypeInfo.setDataType(luAttrType.getDataType());
		luAttributeTypeInfo.setDisplayType(luAttrType.getDisplayType());
		luAttributeTypeInfo.setList(luAttrType.isList());
		luAttributeTypeInfo.setName(luAttrType.getName());
		luAttributeTypeInfo.setScatId(luAttrType.getScatId());
		luAttributeTypeInfo.setGroupingCd(luAttrType.getGroupingCd());
		luAttributeTypeInfo.setStatus(luAttrType.getStatus());
		return luAttributeTypeInfo;
	}

	public static void updateLuAttributeType(
			LuAttributeTypeInfo luAttributeTypeInfo, LuAttributeType luAttrType) {
		luAttrType.setDataType(luAttributeTypeInfo.getDataType());
		luAttrType.setDisplayType(luAttributeTypeInfo.getDisplayType());
		luAttrType.setList(luAttributeTypeInfo.isList());
		luAttrType.setName(luAttributeTypeInfo.getName());
		luAttrType.setScatId(luAttributeTypeInfo.getScatId());
		luAttrType.setGroupingCd(luAttributeTypeInfo.getGroupingCd());
		luAttrType.setStatus(luAttributeTypeInfo.getStatus());
	}
}
