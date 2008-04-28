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


import org.kuali.student.poc.learningunit.lu.entity.Atp;
import org.kuali.student.poc.learningunit.lu.entity.Clu;
import org.kuali.student.poc.learningunit.lu.entity.CluRelation;
import org.kuali.student.poc.learningunit.lu.entity.LuAttribute;
import org.kuali.student.poc.learningunit.lu.entity.LuType;
import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.xsd.learningunit.lu.dto.AtpDisplay;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;
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

/**
 * @author garystruthers
 *
 */
public class Assembler {
	
	public AtpDisplay createAtpDisplay(Atp atp) {
		AtpDisplay atpDisplay = new AtpDisplay();
		atpDisplay.setAtpId(atp.getAtpId());
		atpDisplay.setAtpName(atp.getAtpName());
		return atpDisplay;
	}
	
	public Atp createAtp(AtpDisplay atpDisplay) {
		Atp atp = new Atp();
		updateAtp( atpDisplay,  atp);
		return atp;
	}
	
	public void updateAtp(AtpDisplay atpDisplay, Atp atp) {
		atp.setAtpId(atpDisplay.getAtpId());
		atp.setAtpName(atpDisplay.getAtpName());
	}
	
	public CluInfo createCluInfo(Clu clu) {
		CluInfo cluInfo = new CluInfo();
		cluInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle().getAtpId());
		cluInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle().getAtpId());
		cluInfo.setApprovalStatus(clu.getApprovalStatus());
		cluInfo.setApprovalStatusTime(clu.getApprovalStatusTime());
		cluInfo.setCluLongName(clu.getCluLongName());
		cluInfo.setCluShortName(clu.getCluShortName());
		cluInfo.setDescription(clu.getDescription());
		cluInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		cluInfo.setCreateTime(clu.getCreateTime());
		cluInfo.setCreateUserComment(clu.getCreateUserComment());
		cluInfo.setCreateUserId(clu.getCreateUserId());
		cluInfo.setUpdateTime(clu.getUpdateTime());
		cluInfo.setUpdateUserComment(clu.getUpdateUserComment());
		cluInfo.setUpdateUserId(clu.getUpdateUserId());
		cluInfo.setLuTypeId(clu.getLuType().getLuTypeId());
		return cluInfo;
	}
	
	public Clu createClu(CluInfo cluInfo) {
		Clu clu = new Clu();
		updateClu(cluInfo, clu); 
		return clu;
	}
	
	public void updateClu(CluInfo cluInfo, Clu clu) {
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
	
	public CluDisplay createCluDisplay(Clu clu) {
		CluDisplay cluDisplay = new CluDisplay();
		AtpDisplay atpDisplayEnd = createAtpDisplay(clu.getEffectiveEndCycle());
		cluDisplay.setAtpDisplayEnd(atpDisplayEnd);
		AtpDisplay atpDisplayStart = createAtpDisplay(clu.getEffectiveStartCycle());
		cluDisplay.setAtpDisplayStart(atpDisplayStart);
		//cluDisplay.setCluCode(clu.) @FIX_ME
		cluDisplay.setCluId(clu.getCluId());
		cluDisplay.setCluShortName(clu.getCluShortName());
		cluDisplay.setLuTypeId(clu.getLuType().getLuTypeId());
		return cluDisplay;
	}
	
	public Clu createClu(CluDisplay cluDisplay) {
		Clu clu = new Clu();
		updateClu( cluDisplay,  clu); 
		return clu;
	}

	public void updateClu(CluDisplay cluDisplay, Clu clu) {
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

	public LuTypeInfo createLuTypeInfo(LuType luType) {
		LuTypeInfo luTypeInfo = new LuTypeInfo();
		luTypeInfo.setCreateTime(luType.getCreateTime());
		luTypeInfo.setCreateUserComment(luType.getCreateUserComment());
		luTypeInfo.setCreateUserId(luType.getCreateUserId());
		luTypeInfo.setUpdateTime(luType.getUpdateTime());
		luTypeInfo.setUpdateUserComment(luType.getUpdateUserComment());
		luTypeInfo.setUpdateUserId(luType.getUpdateUserId());
		return luTypeInfo;
	}
	
	public LuType createLuType(LuTypeInfo luTypeInfo) {
		LuType luType = new LuType();
		updateLuType(luTypeInfo, luType);
		return luType;
	}
	
	public void updateLuType(LuTypeInfo luTypeInfo, LuType luType)  {
		luType.setCreateTime(luTypeInfo.getCreateTime());
		luType.setCreateUserComment(luTypeInfo.getCreateUserComment());
		luType.setCreateUserId(luTypeInfo.getCreateUserId());
		luType.setUpdateTime(luTypeInfo.getUpdateTime());
		luType.setUpdateUserComment(luTypeInfo.getUpdateUserComment());
		luType.setUpdateUserId(luTypeInfo.getUpdateUserId());
	}
	
	public LuiInfo createLuiInfo(Lui lui) { 
		LuiInfo luiInfo = new LuiInfo();
		luiInfo.setAtpDisplay(createAtpDisplay(lui.getAtp()));
		Clu clu = lui.getClu();
		luiInfo.setCluDisplay(createCluDisplay(clu));
		luiInfo.setLuiCode(lui.getLuiCode());
		luiInfo.setMaxSeats(lui.getMaxSeats());
		return luiInfo;
	}

	public Lui createLui(LuiInfo luiInfo) {
		Lui lui = new Lui();
		updateLui( luiInfo,  lui);
		return lui;
	}
	
	public void updateLui(LuiInfo luiInfo, Lui lui) { 
		Atp atp = createAtp(luiInfo.getAtpDisplay());
		lui.setAtp(atp);
		Clu clu = createClu(luiInfo.getCluDisplay());
		lui.setClu(clu);
		lui.setLuiCode(luiInfo.getLuiCode());
		lui.setMaxSeats(luiInfo.getMaxSeats());
		lui.setLuiId(luiInfo.getLuiId());
	}
	
	public CluRelationInfo createCluRelationInfo(CluRelation cluRelation) {
		CluRelationInfo cluRelationInfo = new CluRelationInfo();
		CluDisplay cluDisplay = createCluDisplay(cluRelation.getClu());
		cluRelationInfo.setCluDisplay(cluDisplay);
		cluRelationInfo.setCreateTime(cluRelation.getCreateTime());
		cluRelationInfo.setCreateUserComment(cluRelation.getCreateUserComment());
		cluRelationInfo.setCreateUserId(cluRelation.getCreateUserComment());
		cluRelationInfo.setEffectiveEndDate(cluRelation.getEffectiveEndDate());
		cluRelationInfo.setEffectiveStartDate(cluRelation.getEffectiveStartDate());
		cluRelationInfo.setLuRelationTypeId(cluRelation.getLuRelationType().getId());
		cluDisplay = createCluDisplay(cluRelation.getRelatedClu());
		cluRelationInfo.setRelatedCluDisplay(cluDisplay);
		cluRelationInfo.setUpdateTime(cluRelation.getUpdateTime());
		cluRelationInfo.setUpdateUserComment(cluRelation.getUpdateUserComment());
		cluRelationInfo.setUpdateUserId(cluRelation.getUpdateUserId());
		return cluRelationInfo;		
	}
	
	public CluRelation createCluRelation(CluRelationInfo cluRelationInfo) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation( cluRelationInfo,  cluRelation); 
		return cluRelation;		
	}
	
	public void updateCluRelation(CluRelationInfo cluRelationInfo, CluRelation cluRelation) {
		Clu clu = createClu(cluRelationInfo.getCluDisplay());
		cluRelation.setClu(clu);
		cluRelation.setCreateTime(cluRelationInfo.getCreateTime());
		cluRelation.setCreateUserComment(cluRelationInfo.getCreateUserComment());
		cluRelation.setCreateUserId(cluRelationInfo.getCreateUserComment());
		cluRelation.setEffectiveEndDate(cluRelationInfo.getEffectiveEndDate());
		cluRelation.setEffectiveStartDate(cluRelationInfo.getEffectiveStartDate());
		//cluRelation.setLuRelationType(cluRelationInfo.getLuRelationTypeId()); @FIX_ME
		clu = createClu(cluRelationInfo.getRelatedCluDisplay());
		cluRelation.setRelatedClu(clu);
		cluRelation.setUpdateTime(cluRelationInfo.getUpdateTime());
		cluRelation.setUpdateUserComment(cluRelationInfo.getUpdateUserComment());
		cluRelation.setUpdateUserId(cluRelationInfo.getUpdateUserId());
	}
	
	public CluRelationDisplay createCluRelationDisplay(CluRelation cluRelation) {
		CluRelationDisplay cluRelationDisplay = new CluRelationDisplay();
		CluDisplay cluDisplay = createCluDisplay(cluRelation.getClu());
		cluRelationDisplay.setCluDisplay(cluDisplay);
		cluRelationDisplay.setLuRelationTypeId(cluRelation.getLuRelationType().getId());
		cluDisplay = createCluDisplay(cluRelation.getRelatedClu());
		cluRelationDisplay.setRelatedCluDisplay(cluDisplay);
		return cluRelationDisplay;		
	}

	public CluRelation createCluRelation(CluRelationDisplay cluRelationDisplay) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation( cluRelationDisplay,  cluRelation);
		return cluRelation;
	}
	
	public void updateCluRelation(CluRelationDisplay cluRelationDisplay, CluRelation cluRelation) {
		Clu clu = createClu(cluRelationDisplay.getCluDisplay());
		cluRelation.setClu(clu);
		//cluRelation.setLuRelationType(cluRelationInfo.getLuRelationTypeId()); @FIX_ME
		clu = createClu(cluRelationDisplay.getRelatedCluDisplay());
		cluRelation.setRelatedClu(clu);
	}
	
	public CluRelationAssignInfo createCluRelationAssignInfo(CluRelation cluRelation) {
		CluRelationAssignInfo cluRelationAssignInfo = new CluRelationAssignInfo();
		cluRelationAssignInfo.setEffectiveEndDate(cluRelation.getEffectiveEndDate());
		cluRelationAssignInfo.setEffectiveStartDate(cluRelation.getEffectiveStartDate());
		return cluRelationAssignInfo;		
	}

	public CluRelation createCluRelation(CluRelationAssignInfo cluRelationAssignInfo) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation( cluRelationAssignInfo,  cluRelation);
		return cluRelation;
	}
	
	public void updateCluRelation(CluRelationAssignInfo cluRelationAssignInfo, CluRelation cluRelation) {
		cluRelation.setEffectiveEndDate(cluRelationAssignInfo.getEffectiveEndDate());
		cluRelation.setEffectiveStartDate(cluRelationAssignInfo.getEffectiveStartDate());
	}

	public CluRelationUpdateInfo createCluRelationUpdateInfo(CluRelation cluRelation) {
		CluRelationUpdateInfo cluRelationUpdateInfo = new CluRelationUpdateInfo();
		cluRelationUpdateInfo.setEffectiveEndDate(cluRelation.getEffectiveEndDate());
		cluRelationUpdateInfo.setEffectiveStartDate(cluRelation.getEffectiveStartDate());
		return cluRelationUpdateInfo;		
	}

	public CluRelation createCluRelation(CluRelationUpdateInfo cluRelationUpdateInfo) {
		CluRelation cluRelation = new CluRelation();
		updateCluRelation( cluRelationUpdateInfo,  cluRelation);
		return cluRelation;
	}
	
	public void updateCluRelation(CluRelationUpdateInfo cluRelationUpdateInfo, CluRelation cluRelation) {
		cluRelation.setEffectiveEndDate(cluRelationUpdateInfo.getEffectiveEndDate());
		cluRelation.setEffectiveStartDate(cluRelationUpdateInfo.getEffectiveStartDate());
	}

	public CluCreateInfo createCluCreateInfo(Clu clu) {
		CluCreateInfo cluCreateInfo = new CluCreateInfo();
		cluCreateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle().getAtpId());
		cluCreateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle().getAtpId());
		cluCreateInfo.setCluLongName(clu.getCluLongName());
		cluCreateInfo.setCluShortName(clu.getCluShortName());
		cluCreateInfo.setDescription(clu.getDescription());
		cluCreateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluCreateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return cluCreateInfo;
	}

	public Clu createClu(CluCreateInfo cluCreateInfo) {
		Clu clu = new Clu();
		updateClu(cluCreateInfo, clu); 
		return clu;
	}
	
	public void updateClu(CluCreateInfo cluCreateInfo, Clu clu) {
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


	public CluSetCreateInfo createCluSetCreateInfo(Clu clu) {
		CluSetCreateInfo cluSetCreateInfo = new CluSetCreateInfo();
		cluSetCreateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle().getAtpId());
		cluSetCreateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle().getAtpId());
		cluSetCreateInfo.setDescription(clu.getDescription());
		cluSetCreateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluSetCreateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return cluSetCreateInfo;
	}


	public Clu createClu(CluSetCreateInfo cluSetCreateInfo) {
		Clu clu = new Clu();
		updateClu(cluSetCreateInfo, clu); 
		return clu;
	}
	
	public void updateClu(CluSetCreateInfo cluSetCreateInfo, Clu clu) {
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
	
	public CluSetDisplay createCluSetDisplay(Clu clu) {
		CluSetDisplay cluSetDisplay = new CluSetDisplay();
		cluSetDisplay.setCluSetId(clu.getCluId());
		cluSetDisplay.setCluSetName(clu.getCluShortName());
		return cluSetDisplay;
	}
	
	public Clu createClu(CluSetDisplay cluSetDisplay) {
		Clu clu = new Clu();
		updateClu( cluSetDisplay,  clu); 
		return clu;
	}
	
	public void updateClu(CluSetDisplay cluSetDisplay, Clu clu) {
		clu.setCluShortName(cluSetDisplay.getCluSetName());
		clu.setCluId(cluSetDisplay.getCluSetId());
		clu.setCluShortName(cluSetDisplay.getCluSetName());
	}	
	
	public CluSetInfo createCluSetInfo(Clu clu) {
		CluSetInfo cluSetInfo = new CluSetInfo();
		cluSetInfo.setDescription(clu.getDescription());
		cluSetInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluSetInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		cluSetInfo.setCluSetId(clu.getCluId());
		cluSetInfo.setCluSetName(clu.getCluShortName());
		return cluSetInfo;
	}
	
	public Clu createClu(CluSetInfo cluSetInfo) {
		Clu clu = new Clu();
		updateClu(cluSetInfo, clu); 
		return clu;
	}
	
	public void updateClu(CluSetInfo cluSetInfo, Clu clu) {
		clu.setDescription(cluSetInfo.getDescription());
		clu.setEffectiveEndDate(cluSetInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(cluSetInfo.getEffectiveStartDate());
		clu.setCluId(cluSetInfo.getCluSetId());
		clu.setCluShortName(cluSetInfo.getCluSetName());
	}

	
	public CluSetUpdateInfo createCluSetUpdateInfo(Clu clu) {
		CluSetUpdateInfo cluSetUpdateInfo = new CluSetUpdateInfo();
		cluSetUpdateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle().getAtpId());
		cluSetUpdateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle().getAtpId());
		cluSetUpdateInfo.setDescription(clu.getDescription());
		cluSetUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluSetUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		cluSetUpdateInfo.setCluSetName(clu.getCluShortName());
		return cluSetUpdateInfo;
	}

	public Clu createClu(CluSetUpdateInfo cluSetUpdateInfo) {
		Clu clu = new Clu();
		updateClu(cluSetUpdateInfo, clu); 
		return clu;
	}
	
	public void updateClu(CluSetUpdateInfo cluSetUpdateInfo, Clu clu) {
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

	
	public CluUpdateInfo createCluUpdateInfo(Clu clu) {
		CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
		cluUpdateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle().getAtpId());
		cluUpdateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle().getAtpId());
		cluUpdateInfo.setCluLongName(clu.getCluLongName());
		cluUpdateInfo.setCluShortName(clu.getCluShortName());
		cluUpdateInfo.setDescription(clu.getDescription());
		cluUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return cluUpdateInfo;
	}
	
	public Clu createClu(CluUpdateInfo cluUpdateInfo) {
		Clu clu = new Clu();
		updateClu(cluUpdateInfo, clu); 
		return clu;
	}
	
	public void updateClu(CluUpdateInfo cluUpdateInfo, Clu clu) {
		Atp atp = new Atp();
		atp.setAtpId(cluUpdateInfo.getEffectiveStartCycle());
		clu.setEffectiveStartCycle(atp);
		atp = new Atp();
		atp.setAtpId(cluUpdateInfo.getEffectiveEndCycle());
		clu.setEffectiveEndCycle(atp);
		clu.setCluLongName(cluUpdateInfo.getCluLongName());
		clu.setCluShortName(cluUpdateInfo.getCluShortName());
		clu.setDescription(cluUpdateInfo.getDescription());
		clu.setEffectiveEndDate(cluUpdateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(cluUpdateInfo.getEffectiveStartDate());
	}	
	
	public LuiCreateInfo createLuiCreateInfo(Lui lui) { 
		LuiCreateInfo luiCreateInfo = new LuiCreateInfo();
		//luiCreateInfo.setAttributes(attributes) @FIX_ME
		Clu clu = lui.getClu();
		luiCreateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiCreateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return luiCreateInfo;
	}

	public Lui createLui(LuiCreateInfo luiCreateInfo) {
		Lui lui = new Lui();
		updateLui( luiCreateInfo,  lui);
		return lui;
	}
	
	public void updateLui(LuiCreateInfo luiCreateInfo, Lui lui) { 
		Clu clu = new Clu();
		clu.setEffectiveEndDate(luiCreateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(luiCreateInfo.getEffectiveStartDate());
		lui.setClu(clu);
	}
	
	public LuiDisplay createLuiDisplay(Lui lui) { 
		LuiDisplay luiDisplay = new LuiDisplay();
		luiDisplay.setAtpDisplay(createAtpDisplay(lui.getAtp()));
		Clu clu = lui.getClu();
		luiDisplay.setCluDisplay(createCluDisplay(clu));
		luiDisplay.setLuiCode(lui.getLuiCode());
		luiDisplay.setLuiId(lui.getLuiId());
		return luiDisplay;
	}

	public Lui createLui(LuiDisplay luiDisplay) {
		Lui lui = new Lui();
		updateLui( luiDisplay, lui);
		return lui;
	}
	
	public void updateLui(LuiDisplay luiDisplay, Lui lui) { 
		Atp atp = createAtp(luiDisplay.getAtpDisplay());
		lui.setAtp(atp);
		Clu clu = createClu(luiDisplay.getCluDisplay());
		lui.setClu(clu);
		lui.setLuiCode(luiDisplay.getLuiCode());
		lui.setLuiId(luiDisplay.getLuiId());
	}
	
	public LuiRelationAssignInfo createLuiRelationAssignInfo(Lui lui) { 
		LuiRelationAssignInfo luiRelationAssignInfo = new LuiRelationAssignInfo();
		Clu clu = lui.getClu();
		luiRelationAssignInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiRelationAssignInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return luiRelationAssignInfo;
	}

	public Lui createLui(LuiRelationAssignInfo luiRelationAssignInfo) {
		Lui lui = new Lui();
		updateLui( luiRelationAssignInfo,  lui);
		return lui;
	}
	
	public void updateLui(LuiRelationAssignInfo luiRelationAssignInfo, Lui lui) { 
		Clu clu = new Clu();
		clu.setEffectiveEndDate(luiRelationAssignInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(luiRelationAssignInfo.getEffectiveStartDate());
		lui.setClu(clu);
	}
	
	public LuiRelationDisplay createLuiRelationDisplay(Lui lui) { 
		LuiRelationDisplay luiRelationDisplay = new LuiRelationDisplay();
		LuiDisplay luiDisplay = createLuiDisplay(lui);
		luiRelationDisplay.setLuiDisplay(luiDisplay);
		//luiRelationDisplay.setLuRelationTypeId(luRelationTypeId) @FIX_ME
		//luiRelationDisplay.setRelatedLuiDisplay(relatedLuiDisplay) @FIX_ME
		return luiRelationDisplay;
	}

	public Lui createLui(LuiRelationDisplay luiRelationDisplay) {
		Lui lui = new Lui();
		updateLui( luiRelationDisplay, lui);
		return lui;
	}
	
	public void updateLui(LuiRelationDisplay luiRelationDisplay, Lui lui) { 
		//@FIX_ME
	}

	public LuiRelationInfo createLuiRelationInfo(Lui lui) {
		LuiRelationInfo luiRelationInfo = new LuiRelationInfo();
		Clu clu = lui.getClu();
		luiRelationInfo.setCreateTime(clu.getCreateTime());
		luiRelationInfo.setCreateUserComment(clu.getCreateUserComment());
		luiRelationInfo.setCreateUserId(clu.getCreateUserId());
		luiRelationInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiRelationInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		LuiDisplay luiDisplay = createLuiDisplay(lui);
		luiRelationInfo.setLuiDisplay(luiDisplay);
		//luiRelationInfo.setLuRelationTypeId(clu.) @FIX_ME
		//luiRelationInfo.setRelatedLuiDisplay(relatedLuiDisplay) @FIX_ME
		luiRelationInfo.setUpdateTime(clu.getUpdateTime());
		luiRelationInfo.setUpdateUserComment(clu.getUpdateUserComment());
		luiRelationInfo.setUpdateUserId(clu.getUpdateUserId());
		return luiRelationInfo;
	}
	

	public Lui createLui(LuiRelationInfo luiRelationInfo) {
		Lui lui = new Lui();
		updateLui( luiRelationInfo, lui);
		return lui;
	}
	
	public void updateLui(LuiRelationInfo luiRelationInfo, Lui lui) {
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

	
	public LuiUpdateInfo createLuiUpdateInfo(Lui lui) { 
		LuiUpdateInfo luiUpdateInfo = new LuiUpdateInfo();
		Clu clu = lui.getClu();
		luiUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		luiUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		return luiUpdateInfo;
	}

	public Lui createLui(LuiUpdateInfo luiUpdateInfo) {
		Lui lui = new Lui();
		updateLui( luiUpdateInfo,  lui);
		return lui;
	}
	
	public void updateLui(LuiUpdateInfo luiUpdateInfo, Lui lui) { 
		Clu clu = new Clu();
		clu.setEffectiveEndDate(luiUpdateInfo.getEffectiveEndDate());
		clu.setEffectiveStartDate(luiUpdateInfo.getEffectiveStartDate());
		lui.setClu(clu);
	}

	public LuTypeInfo createLuTypeInfo(Lui lui) {
		LuTypeInfo luiTypeInfo = new LuTypeInfo();
		Clu clu = lui.getClu();
		luiTypeInfo.setCreateTime(clu.getCreateTime());
		luiTypeInfo.setCreateUserComment(clu.getCreateUserComment());
		luiTypeInfo.setCreateUserId(clu.getCreateUserId());
		luiTypeInfo.setDescription(clu.getDescription());
		//luiTypeInfo.setLuTypeKey()@FIX_ME
		luiTypeInfo.setUpdateTime(clu.getUpdateTime());
		luiTypeInfo.setUpdateUserComment(clu.getUpdateUserComment());
		luiTypeInfo.setUpdateUserId(clu.getUpdateUserId());
		return luiTypeInfo;
	}
	

	public Lui createLui(LuTypeInfo luiTypeInfo) {
		Lui lui = new Lui();
		updateLui( luiTypeInfo, lui);
		return lui;
	}
	
	public void updateLui(LuTypeInfo luiTypeInfo, Lui lui) {
		Clu clu = lui.getClu();
		clu.setCreateTime(luiTypeInfo.getCreateTime());
		clu.setCreateUserComment(luiTypeInfo.getCreateUserComment());
		clu.setCreateUserId(luiTypeInfo.getCreateUserId());
		clu.setDescription(luiTypeInfo.getDescription());
		clu.setUpdateTime(luiTypeInfo.getUpdateTime());
		clu.setUpdateUserComment(luiTypeInfo.getUpdateUserComment());
		clu.setUpdateUserId(luiTypeInfo.getUpdateUserId());
	}

}
