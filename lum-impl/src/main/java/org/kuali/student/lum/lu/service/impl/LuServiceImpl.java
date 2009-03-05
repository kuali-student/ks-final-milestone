package org.kuali.student.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jws.WebService;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.entity.TimeAmount;
import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.CluCluRelationCriteria;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluCriteria;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LrTypeInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiCriteria;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationCriteria;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluAccounting;
import org.kuali.student.lum.lu.entity.CluAccountingAttribute;
import org.kuali.student.lum.lu.entity.CluAtpTypeKey;
import org.kuali.student.lum.lu.entity.CluAttribute;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluCluRelationAttribute;
import org.kuali.student.lum.lu.entity.CluCredit;
import org.kuali.student.lum.lu.entity.CluFee;
import org.kuali.student.lum.lu.entity.CluFeeAttribute;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluInstructor;
import org.kuali.student.lum.lu.entity.CluInstructorAttribute;
import org.kuali.student.lum.lu.entity.CluOrg;
import org.kuali.student.lum.lu.entity.CluPublishing;
import org.kuali.student.lum.lu.entity.CluPublishingAttribute;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.LuCode;
import org.kuali.student.lum.lu.entity.LuCodeAttribute;
import org.kuali.student.lum.lu.entity.LuLuRelationType;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.LuStatementType;
import org.kuali.student.lum.lu.entity.LuType;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.LuiLuiRelationAttribute;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.lum.lu.service.LuService", serviceName = "LuService", portName = "LuService", targetNamespace = "http://student.kuali.org/lum/lu")
@Transactional
public class LuServiceImpl implements LuService {

	private LuDao luDao;

	@Override
	public StatusInfo addCluResourceRequirement(String resourceTypeKey,
			String cluId) throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addCluSetToCluSet(String cluSetId, String addedCluSetId)
			throws CircularReferenceException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addCluToCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addLrScaleToClu(String lrScaleTypeKey, String lrTypeKey,
			String cluId) throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addLrScaleToLui(String lrScaleTypeKey, String lrTypeKey,
			String luiId) throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addLuStatementToClu(String cluId, String luStatementId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addOutcomeLoToClu(String loId, String cluId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addOutcomeLoToLui(String loId, String luiId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluInfo createClu(String luTypeKey, CluInfo cluInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(cluInfo, "cluInfo");

		Clu clu = new Clu();

		LuType luType = luDao.fetch(LuType.class,luTypeKey);
		clu.setLuType(luType);

		if(cluInfo.getOfficialIdentifier()!=null){
			CluIdentifier officialIdentifier = new CluIdentifier();
			BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(), officialIdentifier);
			clu.setOfficialIdentifier(officialIdentifier);
		}
		
		for(CluIdentifierInfo cluIdInfo : cluInfo.getAlternateIdentifiers()){
			CluIdentifier identifier = new CluIdentifier();
			BeanUtils.copyProperties(cluIdInfo, identifier);
			clu.getAlternateIdentifiers().add(identifier);
		}

		if(cluInfo.getDesc()!=null){
			clu.setDesc(LuServiceAssembler.toRichText(cluInfo.getDesc()));
		}
		
		if(cluInfo.getMarketingDesc()!=null){
			clu.setMarketingDesc(LuServiceAssembler.toRichText(cluInfo.getMarketingDesc()));
		}
			
		for(String orgId:cluInfo.getParticipatingOrgs()){
			CluOrg cluOrg = new CluOrg();
			cluOrg.setOrgId(orgId);
			clu.getParticipatingOrgs().add(cluOrg);
		}

		if(cluInfo.getPrimaryInstructor()!=null){
			CluInstructor primaryInstructor = new CluInstructor();
			BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(),primaryInstructor,new String[]{"attributes"});
			primaryInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, cluInfo.getPrimaryInstructor().getAttributes(), primaryInstructor, luDao));
			clu.setPrimaryInstructor(primaryInstructor);
		}
			
		for(CluInstructorInfo instructorInfo: cluInfo.getInstructors()){
			CluInstructor instructor = new CluInstructor();
			BeanUtils.copyProperties(instructorInfo,instructor,new String[]{"attributes"});
			instructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, instructorInfo.getAttributes(), instructor, luDao));
			clu.getInstructors().add(instructor);
		}

		if(cluInfo.getStdDuration()!=null){
			clu.setStdDuration(LuServiceAssembler.toTimeAmount(cluInfo.getStdDuration()));
		}
			
		for(LuCodeInfo luCodeInfo:cluInfo.getLuCodes()){
			LuCode luCode = new LuCode();
			luCode.setAttributes(LuServiceAssembler.toGenericAttributes(LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode, luDao));
			BeanUtils.copyProperties(luCodeInfo,luCode,new String[]{"attributes","metaInfo"});
			clu.getLuCodes().add(luCode);
		}

		clu.setCredit(LuServiceAssembler.toCluCredit(cluInfo.getCreditInfo()));//Required field

		if(cluInfo.getPublishingInfo()!=null){
			CluPublishing cluPublishing = new CluPublishing();
			BeanUtils.copyProperties(cluInfo.getPublishingInfo(),cluPublishing,new String[]{"attributes","instructors","primaryInstructor"});
			cluPublishing.setAttributes(LuServiceAssembler.toGenericAttributes(CluPublishingAttribute.class, cluInfo.getPublishingInfo().getAttributes(), cluPublishing, luDao));
		
			if(cluInfo.getPublishingInfo().getPrimaryInstructor()!=null){	
				CluInstructor primaryPubInstructor = new CluInstructor();
				BeanUtils.copyProperties(cluInfo.getPublishingInfo().getPrimaryInstructor(),primaryPubInstructor,new String[]{"attributes"});
				primaryPubInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, cluInfo.getPublishingInfo().getPrimaryInstructor().getAttributes(), primaryPubInstructor, luDao));
				cluPublishing.setPrimaryInstructor(primaryPubInstructor);
			}
				
			for(CluInstructorInfo instructorInfo: cluInfo.getPublishingInfo().getInstructors()){
				CluInstructor instructor = new CluInstructor();
				BeanUtils.copyProperties(instructorInfo,instructor,new String[]{"attributes"});
				instructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, instructorInfo.getAttributes(), instructor, luDao));
				cluPublishing.getInstructors().add(instructor);
			}
	
			clu.setPublishing(cluPublishing);
		}

		for(String atpTypeKey : cluInfo.getOfferedAtpTypes()){
			CluAtpTypeKey cluAtpTypeKey = new CluAtpTypeKey();
			cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
			clu.getOfferedAtpTypes().add(cluAtpTypeKey);
		}

		if(cluInfo.getFeeInfo()!=null){
			CluFee cluFee = new CluFee();
			cluFee.setAttributes(LuServiceAssembler.toGenericAttributes(CluFeeAttribute.class, cluInfo.getFeeInfo().getAttributes(), cluFee, luDao));
			clu.setFee(cluFee);
		}

		if(cluInfo.getAccountingInfo()!=null){
			CluAccounting cluAccounting = new CluAccounting();
			cluAccounting.setAttributes(LuServiceAssembler.toGenericAttributes(CluAccountingAttribute.class, cluInfo.getAccountingInfo().getAttributes(), cluAccounting, luDao));
			clu.setAccounting(cluAccounting);
		}

		clu.setAttributes(LuServiceAssembler.toGenericAttributes(CluAttribute.class, cluInfo.getAttributes(), clu, luDao));

		//Now copy all not standard properties
		BeanUtils.copyProperties(cluInfo,clu,new String[]{"luType","officialIdentifier","alternateIdentifiers","desc","marketingDesc","participatingOrgs","luCodes",
					"primaryInstructor","instructors","stdDuration","codeInfo","publishingInfo","offeredAtpTypes","feeInfo","accountingInfo","attributes","metaInfo"});

		luDao.create(clu);

		return LuServiceAssembler.toCluInfo(clu);
	}

	@Override
	public CluCluRelationInfo createCluCluRelation(String cluId,
			String relatedCluId, String luLuRelationTypeKey,
			CluCluRelationInfo cluCluRelationInfo)
			throws AlreadyExistsException, CircularReferenceException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");
        
        Clu clu = luDao.fetch(Clu.class, cluId);
        Clu relatedClu = luDao.fetch(Clu.class, relatedCluId);
        
        CluCluRelation cluCluRelation = new CluCluRelation();
        BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation, new String[] { "cluId", "relatedCluId", "isCluRelationRequired", "attributes", "metaInfo" });
        
        cluCluRelation.setClu(clu);
        cluCluRelation.setRelatedClu(relatedClu);
        cluCluRelation.setCluRelationRequired(cluCluRelationInfo.getIsCluRelationRequired() == null? true: cluCluRelationInfo.getIsCluRelationRequired()); //TODO maybe this is unnecessary, contract specifies not null
        cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(CluCluRelationAttribute.class, cluCluRelationInfo.getAttributes(), cluCluRelation, luDao));
        
        LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class, luLuRelationTypeKey);
               
        cluCluRelation.setLuLuRelationType(luLuRelationType);
        
        luDao.create(cluCluRelation);
        
		return LuServiceAssembler.toCluCluRelationInfo(cluCluRelation);
	}

	@Override
	public CluSetInfo createDynamicCluSet(String cluSetName,
			CluSetInfo cluSetInfo, CluCriteria cluCriteria)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluSetInfo createEnumeratedCluSet(String cluSetName,
			CluSetInfo cluSetInfo) throws AlreadyExistsException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuDocRelationInfo createLuDocRelationForClu(
			String luDocRelationType, String documentId, String cluId,
			LuDocRelationInfo luDocRelationInfo) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuStatementInfo createLuStatement(String luStatementType,
			LuStatementInfo luStatementInfo) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    checkForMissingParameter(luStatementType, "luStatementType");
	    checkForMissingParameter(luStatementInfo, "luStatementInfo");

	    LuStatement luStatement = null;

	    try {
            luStatement = LuServiceAssembler.toLuStatementRelation(false, luStatementInfo, luDao);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Version Mismatch.", e);
        }

        luDao.create(luStatement);

	    return LuServiceAssembler.toLuStatementInfo(luStatement);
	}

	@Override
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    checkForMissingParameter(cluId, "cludId");
	    checkForMissingParameter(atpKey, "atpKey");
	    checkForMissingParameter(luiInfo, "luiInfo");
        
		Lui lui = new Lui();
		luiInfo.setCluId(cluId);
		
		try {
			lui = LuServiceAssembler.toLui(false, luiInfo, luDao);
		} catch (VersionMismatchException vme) {
		}

		luDao.create(lui);
		
		return LuServiceAssembler.toLuiInfo(lui);
	}

	@Override
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
			String relatedLuiId, String luLuRelationTypeKey,
			LuiLuiRelationInfo luiLuiRelationInfo)
			throws AlreadyExistsException, CircularReferenceException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(luiId, "luiId");
	    checkForMissingParameter(relatedLuiId, "relatedLuiId");
	    checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
	    checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");
	    
        Lui lui = luDao.fetch(Lui.class, luiId);
        Lui relatedLui = luDao.fetch(Lui.class, relatedLuiId);
        
        LuiLuiRelation luiLuiRelation = new LuiLuiRelation();
        BeanUtils.copyProperties(luiLuiRelationInfo, luiLuiRelation, new String[] { "luiId", "relatedLuiId", "attributes", "metaInfo" });
        
        luiLuiRelation.setLui(lui);
        luiLuiRelation.setRelatedLui(relatedLui);
        luiLuiRelation.setAttributes(LuServiceAssembler.toGenericAttributes(LuiLuiRelationAttribute.class, luiLuiRelationInfo.getAttributes(), luiLuiRelation, luDao));
        
        LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class, luLuRelationTypeKey);
               
        luiLuiRelation.setLuLuRelationType(luLuRelationType);
        
        luDao.create(luiLuiRelation);
        
		return LuServiceAssembler.toLuiLuiRelationInfo(luiLuiRelation);
	}

	@Override
	public ReqComponentInfo createReqComponent(String reqComponentType,
			ReqComponentInfo reqComponentInfo) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    checkForMissingParameter(reqComponentType, "reqComponentType");
	    checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        ReqComponent reqComp = null;

        try {
            reqComp = LuServiceAssembler.toReqComponentRelation(false, reqComponentInfo, luDao);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("Version Mismatch.", e);
        }

        luDao.create(reqComp);

        return LuServiceAssembler.toReqComponentInfo(reqComp);
	}

	@Override
	public StatusInfo deleteClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			DependentObjectsExistException, OperationFailedException,
			PermissionDeniedException {
	    checkForMissingParameter(cluId, "cluId");

	    luDao.delete(Clu.class, cluId);
		
	    StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
		
		return statusInfo;
	}

	@Override
	public StatusInfo deleteCluCluRelation(String cluCluRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteLuDocRelation(String luDocRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteLuStatement(String luStatementId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

        checkForMissingParameter(luStatementId, "luStatementId");

        LuStatement stmt = luDao.fetch(LuStatement.class, luStatementId);

        if(stmt==null){
            throw new DoesNotExistException("LuStatement does not exist for id: "+luStatementId);
        }

        luDao.delete(stmt);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
	}

	@Override
	public StatusInfo deleteLui(String luiId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    luDao.delete(Lui.class, luiId);
		
	    StatusInfo statusInfo = new StatusInfo();
		statusInfo.setSuccess(true);
		
		return statusInfo;
	}

	@Override
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteReqComponent(String reqComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

        checkForMissingParameter(reqComponentId, "reqComponentId");

        ReqComponent reqComp = luDao.fetch(ReqComponent.class, reqComponentId);

        if(reqComp==null){
            throw new DoesNotExistException("ReqComponent does not exist for id: "+reqComponentId);
        }

        luDao.delete(reqComp);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
	}

	@Override
	public List<String> getAllCluIdsInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
		}

	@Override
	public List<CluInfo> getAllClusInCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
		}

	@Override
	public List<String> getAllowedLrScaleTypesForLuType(String luTypeKey,
			String lrTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllowedLrTypesForLuType(String luTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypeInfosByCluId(String cluId,
			String relatedCluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypeInfosByLuiId(String luiId,
			String relatedLuiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllowedLuLuRelationTypeInfosForLuType(
			String luTypeKey, String relatedLuTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluInfo getClu(String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForMissingParameter(cluId, "cluId");

		Clu clu = luDao.fetch(Clu.class, cluId);
		return LuServiceAssembler.toCluInfo(clu);
	}

	@Override
	public CluCluRelationInfo getCluCluRelation(String cluCluRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCluIdsByLoId(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCluIdsByLuType(String luTypeKey, String luState)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(luState, "luState");
		List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
		List<String> ids = new ArrayList<String>(clus.size());
		for (Clu clu : clus) {
			ids.add(clu.getId());
		}
		return ids;
	}

	@Override
	public List<String> getCluIdsByRelation(String relatedCluId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCluIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<String> ids = new ArrayList<String>(cluSet.getClus().size());
		for (Clu clu : cluSet.getClus()) {
			ids.add(clu.getId());
		}
		return ids;
	}

	@Override
	public List<String> getCluSetIdsFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<String> ids = new ArrayList<String>(cluSet.getClus().size());
		for (CluSet cluSet2 : cluSet.getCluSets()) {
			ids.add(cluSet2.getId());
		}
		return ids;
	}

	@Override
	public CluSetInfo getCluSetInfo(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		return LuServiceAssembler.toCluSetInfo(cluSet);
	}

	@Override
	public List<CluSetInfo> getCluSetInfoByIdList(List<String> cluSetIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetIdList, "cluSetIdList");
		List<CluSet> cluSets = luDao.getCluSetInfoByIdList(cluSetIdList);
		return LuServiceAssembler.toCluSetInfos(cluSets);
	}

	@Override
	public List<CluInfo> getClusByIdList(List<String> cluIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(cluIdList, "cluIdList");
		List<Clu> clus = luDao.getClusByIdList(cluIdList);
		return LuServiceAssembler.toCluInfos(clus);
	}

	@Override
	public List<CluInfo> getClusByLuType(String luTypeKey, String luState)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luTypeKey, "luTypeKey");
		checkForMissingParameter(luState, "lustate");
		List<Clu> clus = luDao.getClusByLuType(luTypeKey, luState);
		return LuServiceAssembler.toCluInfos(clus);
	}

	@Override
	public List<CluInfo> getClusByRelation(String relatedCluId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluInfo> getClusFromCluSet(String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		checkForMissingParameter(cluSetId, "cluSetId");
		CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
		List<CluInfo> clus = new ArrayList<CluInfo>(cluSet.getClus().size());
		for (Clu clu : cluSet.getClus()) {
			clus.add(LuServiceAssembler.toCluInfo(clu));
		}
		return clus;
	}

	@Override
	public List<String> getClusUsingComponent(String reqComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLoIdsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLoIdsByLui(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLrScaleTypesForClu(String cluId, String lrTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLrScaleTypesForLui(String luiId, String lrTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LrTypeInfo getLrType(String lrTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LrTypeInfo> getLrTypes() throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLrTypesForClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLrTypesForLui(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuDocRelationInfo getLuDocRelation(String luDocRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuDocRelationTypeInfo getLuDocRelationType(
			String luDocRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuDocRelationTypeInfo> getLuDocRelationTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByDocument(String documentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByIdList(
			List<String> luDocRelationIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuDocRelationInfo> getLuDocRelationsByType(
			String luDocRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuLuRelationTypeInfo getLuLuRelationTypeInfo(
			String luLuRelationTypeKey) throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuLuRelationTypeInfo> getLuLuRelationTypeInfos()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuStatementInfo getLuStatement(String luStatementId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

	    return LuServiceAssembler.toLuStatementInfo(luDao.fetch(LuStatement.class, luStatementId));
	}

	@Override
	public LuStatementTypeInfo getLuStatementType(String luStatementTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

        return LuServiceAssembler.toLuStatementTypeInfo(luDao.fetch(LuStatementType.class, luStatementTypeKey));
	}

	@Override
	public List<LuStatementTypeInfo> getLuStatementTypes()
			throws OperationFailedException {

	    return LuServiceAssembler.toLuStatementTypeInfos(luDao.find(LuStatementType.class));
	}

	@Override
	public List<LuStatementTypeInfo> getLuStatementTypesForLuStatementType(
			String luStatementTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        // TODO Auto-generated method stub
        return null;
	}

	@Override
	public List<LuStatementInfo> getLuStatementsByType(String luStatementTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        checkForMissingParameter(luStatementTypeKey, "luStatementTypeKey");

        List<LuStatement> luStatements = luDao.getLuStatementsForLuStatementType(luStatementTypeKey);
        return LuServiceAssembler.toLuStatementInfos(luStatements);
	}

	@Override
	public List<LuStatementInfo> getLuStatementsForClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuTypeInfo getLuType(String luTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuTypeInfo> getLuTypes() throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiInfo getLui(String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		checkForMissingParameter(luiId, "luiId");

		Lui lui = luDao.fetch(Lui.class, luiId);
		return LuServiceAssembler.toLuiInfo(lui);
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLuiIdsByLoId(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLuiIdsByRelation(String relatedLuiId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelations(String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiInfo> getLuisByIdList(List<String> luiIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		checkForMissingParameter(luiIdList, "luiIdList");
		List<Lui> luis = luDao.getLuisByIdList(luiIdList);
		return LuServiceAssembler.toLuiInfos(luis);
	}

	@Override
	public List<LuiInfo> getLuisByRelation(String relatedLuiId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRelatedCluIdsByCluId(String cluId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CluInfo> getRelatedClusByCluId(String cluId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
			LuLuRelationTypeInfo luLuRelationType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReqComponentInfo getReqComponent(String reqComponentId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

	    return LuServiceAssembler.toReqComponentInfo(luDao.fetch(ReqComponent.class, reqComponentId));
	}

	@Override
	public ReqComponentTypeInfo getReqComponentType(String reqComponentTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

        return LuServiceAssembler.toReqComponentTypeInfo(luDao.fetch(ReqComponentType.class, reqComponentTypeKey));
	}

	@Override
	public List<ReqComponentTypeInfo> getReqComponentTypes()
			throws OperationFailedException {

	    return LuServiceAssembler.toReqComponentTypeInfos(luDao.find(ReqComponentType.class));
	}

	@Override
	public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(
			String luStatementTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReqComponentInfo> getReqComponentsByType(
			String reqComponentTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        checkForMissingParameter(reqComponentTypeKey, "reqComponentTypeKey");

        List<ReqComponent> reqComponents = luDao.getReqComponentsByType(reqComponentTypeKey);
        return LuServiceAssembler.toReqComponentInfos(reqComponents);
	}

	@Override
	public List<ReqComponentInfo> getReqComponentsUsingClu(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getResourceRequirementsForCluId(String cluId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuStatementInfo> getStatementsUsingComponent(
			String reqComponentId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isCluInCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeCluFromCluSet(String cluId, String cluSetId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeCluResourceRequirement(String resourceTypeKey,
			String cluId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeCluSetFromCluSet(String cluSetId,
			String removedCluSetId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeLrScaleFromClu(String lrScaleTypeKey,
			String lrTypeKey, String cluId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeLrScaleFromLui(String lrScaleTypeKey,
			String lrTypeKey, String luiId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeLuStatementFromClu(String cluId,
			String luStatementId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeOutcomeLoFromClu(String loId, String cluId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeOutcomeLoFromLui(String loId, String luiId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForCluCluRelations(
			CluCluRelationCriteria cluCluRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForClus(CluCriteria cluCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForLuiLuiRelations(
			LuiLuiRelationCriteria luiLuiRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForLuis(LuiCriteria luiCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Result> searchForResults(String searchTypeKey,
			List<QueryParamValue> queryParamValues)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluInfo updateClu(String cluId, CluInfo cluInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		
		checkForMissingParameter(cluId, "cluId");
		checkForMissingParameter(cluInfo, "cluInfo");
		
		Clu clu = luDao.fetch(Clu.class, cluId);
		
		if (!String.valueOf(clu.getVersionInd()).equals(cluInfo.getMetaInfo().getVersionInd())){
			throw new VersionMismatchException("Clu to be updated is not the current version");
		}
		
		LuType luType = luDao.fetch(LuType.class,cluInfo.getType());
		clu.setLuType(luType);
		
		if(cluInfo.getOfficialIdentifier()!=null){
			if(clu.getOfficialIdentifier()==null){
				clu.setOfficialIdentifier(new CluIdentifier());
			}
			BeanUtils.copyProperties(cluInfo.getOfficialIdentifier(), clu.getOfficialIdentifier(), new String[]{"id"});
		}else if(clu.getOfficialIdentifier()!=null){
			luDao.delete(clu.getOfficialIdentifier());
		}
		
		//Update the list of Alternate Identifiers
		//Get a map of Id->object of all the currently persisted objects in the list
		Map<String, CluIdentifier> oldAltIdMap = new HashMap<String, CluIdentifier>();
		for(CluIdentifier altIdentifier : clu.getAlternateIdentifiers()){
			oldAltIdMap.put(altIdentifier.getId(),altIdentifier);
		}
		clu.getAlternateIdentifiers().clear();
		
		//Loop through the new list, if the item exists already update and remove from the list
		//otherwise create a new entry
		for(CluIdentifierInfo cluIdInfo : cluInfo.getAlternateIdentifiers()){
			CluIdentifier identifier = oldAltIdMap.remove(cluIdInfo.getId());
			if(identifier==null){
				identifier = new CluIdentifier();
			}
			//Do Copy
			BeanUtils.copyProperties(cluIdInfo, identifier);
			clu.getAlternateIdentifiers().add(identifier);
		}
		
		//Now delete anything left over
		for(Entry<String, CluIdentifier> entry:oldAltIdMap.entrySet()){
			luDao.delete(entry.getValue());
		}
		
		if(cluInfo.getDesc()!=null){
			if(clu.getDesc() == null){
				clu.setDesc(new RichText());
			}
			BeanUtils.copyProperties(cluInfo.getDesc(), clu.getDesc());
		}else if(clu.getDesc()!=null){
			luDao.delete(clu.getDesc());
		}
		
		if(cluInfo.getMarketingDesc()!=null){
			if(clu.getMarketingDesc() == null){
				clu.setMarketingDesc(new RichText());
			}
			BeanUtils.copyProperties(cluInfo.getMarketingDesc(), clu.getMarketingDesc());
		}else if(clu.getMarketingDesc()!=null){
			luDao.delete(clu.getMarketingDesc());
		}
		
		//Update the list of participating orgs
		//Get a map of Id->object of all the currently persisted objects in the list
		Map<String, CluOrg> oldPrtcOrgMap = new HashMap<String, CluOrg>();
		for(CluOrg cluOrg : clu.getParticipatingOrgs()){
			oldPrtcOrgMap.put(cluOrg.getOrgId(),cluOrg);
		}
		clu.getParticipatingOrgs().clear();
		
		//Loop through the new list, if the item exists already update and remove from the list
		//otherwise create a new entry
		for(String orgId : cluInfo.getParticipatingOrgs()){
			CluOrg cluOrg = oldPrtcOrgMap.remove(orgId);
			if(cluOrg == null){
				cluOrg = new CluOrg();
			}
			//Do Copy
			cluOrg.setOrgId(orgId);
			clu.getParticipatingOrgs().add(cluOrg);
		}
		
		//Now delete anything left over
		for(Entry<String, CluOrg> entry:oldPrtcOrgMap.entrySet()){
			luDao.delete(entry.getValue());
		}
		
		if(cluInfo.getPrimaryInstructor()!=null){
			if(clu.getPrimaryInstructor() == null){
				clu.setPrimaryInstructor(new CluInstructor());
			}
			BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(),clu.getPrimaryInstructor(),new String[]{"attributes"});
			clu.getPrimaryInstructor().setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, cluInfo.getPrimaryInstructor().getAttributes(), clu.getPrimaryInstructor(), luDao));
		}else if(clu.getPrimaryInstructor()!=null){
			luDao.delete(clu.getPrimaryInstructor());
		}
		
		//Update the List of instructors
		//Get a map of Id->object of all the currently persisted objects in the list
		Map<String, CluInstructor> oldInstructorMap = new HashMap<String, CluInstructor>();
		for(CluInstructor cluInstructor : clu.getInstructors()){
			oldInstructorMap.put(cluInstructor.getOrgId()+"_"+cluInstructor.getPersonId(),cluInstructor);
		}
		clu.getInstructors().clear();
		
		//Loop through the new list, if the item exists already update and remove from the list
		//otherwise create a new entry
		for(CluInstructorInfo instructorInfo : cluInfo.getInstructors()){
			CluInstructor cluInstructor = oldInstructorMap.remove(instructorInfo.getOrgId()+"_"+instructorInfo.getPersonId());
			if(cluInstructor == null){
				cluInstructor = new CluInstructor();
			}
			//Do Copy
			BeanUtils.copyProperties(instructorInfo,cluInstructor,new String[]{"attributes"});
			cluInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, instructorInfo.getAttributes(), cluInstructor, luDao));
			clu.getInstructors().add(cluInstructor);			
		}
		
		//Now delete anything left over
		for(Entry<String, CluInstructor> entry:oldInstructorMap.entrySet()){
			luDao.delete(entry.getValue());
		}
	
		if(cluInfo.getStdDuration()!=null){
			if(clu.getStdDuration()==null){
				clu.setStdDuration(new TimeAmount());
			}
			BeanUtils.copyProperties(cluInfo.getStdDuration(), clu.getStdDuration());
		}else if(clu.getStdDuration()!=null){
			luDao.delete(clu.getStdDuration());
		}

		//Update the LuCodes
		//Get a map of Id->object of all the currently persisted objects in the list
		Map<String, LuCode> oldLuCodeMap = new HashMap<String, LuCode>();
		for(LuCode luCode: clu.getLuCodes()){
			oldLuCodeMap.put(luCode.getId(),luCode);
		}
		clu.getLuCodes().clear();
		
		//Loop through the new list, if the item exists already update and remove from the list
		//otherwise create a new entry
		for(LuCodeInfo luCodeInfo : cluInfo.getLuCodes()){
			LuCode luCode = oldLuCodeMap.remove(luCodeInfo.getId());
			if(luCode == null){
				luCode = new LuCode();
			}else{
				if (!String.valueOf(luCode.getVersionInd()).equals(luCodeInfo.getMetaInfo().getVersionInd())){
					throw new VersionMismatchException("LuCode to be updated is not the current version");
				}
			}
			//Do Copy
			luCode.setAttributes(LuServiceAssembler.toGenericAttributes(LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode, luDao));
			BeanUtils.copyProperties(luCodeInfo,luCode,new String[]{"attributes","metaInfo"});
			clu.getLuCodes().add(luCode);			
		}
		
		//Now delete anything left over
		for(Entry<String, LuCode> entry:oldLuCodeMap.entrySet()){
			luDao.delete(entry.getValue());
		}
		
		//Credit is required
		if(clu.getCredit()==null){ 
			clu.setCredit(new CluCredit());
		}
		LuServiceAssembler.copyCluCredit(cluInfo.getCreditInfo(),clu.getCredit());
		
		if(cluInfo.getPublishingInfo()!=null){
			if(clu.getPublishing()==null){
				clu.setPublishing(new CluPublishing());
			}
			BeanUtils.copyProperties(cluInfo.getPublishingInfo(),clu.getPublishing(),new String[]{"attributes","instructors","primaryInstructor"});
			clu.getPublishing().setAttributes(LuServiceAssembler.toGenericAttributes(CluPublishingAttribute.class, cluInfo.getPublishingInfo().getAttributes(), clu.getPublishing(), luDao));
			
			if(cluInfo.getPublishingInfo().getPrimaryInstructor()!=null){
				if(clu.getPublishing().getPrimaryInstructor()==null){
					clu.getPublishing().setPrimaryInstructor(new CluInstructor());
				}
				BeanUtils.copyProperties(cluInfo.getPublishingInfo().getPrimaryInstructor(),clu.getPublishing().getPrimaryInstructor(),new String[]{"attributes"});
				clu.getPublishing().getPrimaryInstructor().setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, cluInfo.getPublishingInfo().getPrimaryInstructor().getAttributes(), clu.getPublishing().getPrimaryInstructor(), luDao));
			}else if(clu.getPublishing().getPrimaryInstructor()!=null){
				luDao.delete(clu.getPublishing().getPrimaryInstructor());
			}
			
			//Update the Publishing Instructors
			//Get a map of Id->object of all the currently persisted objects in the list
			Map<String, CluInstructor> oldPubInstructorMap = new HashMap<String, CluInstructor>();
			for(CluInstructor cluInstructor : clu.getPublishing().getInstructors()){
				oldPubInstructorMap.put(cluInstructor.getOrgId()+"_"+cluInstructor.getPersonId(),cluInstructor);
			}
			clu.getPublishing().getInstructors().clear();
			
			//Loop through the new list, if the item exists already update and remove from the list
			//otherwise create a new entry
			for(CluInstructorInfo instructorInfo : cluInfo.getPublishingInfo().getInstructors()){
				CluInstructor cluInstructor = oldPubInstructorMap.remove(instructorInfo.getOrgId()+"_"+instructorInfo.getPersonId());
				if(cluInstructor == null){
					cluInstructor = new CluInstructor();
				}
				//Do Copy
				BeanUtils.copyProperties(instructorInfo,cluInstructor,new String[]{"attributes"});
				cluInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, instructorInfo.getAttributes(), cluInstructor, luDao));
				clu.getPublishing().getInstructors().add(cluInstructor);			
			}
			
			//Now delete anything left over
			for(Entry<String, CluInstructor> entry:oldPubInstructorMap.entrySet()){
				luDao.delete(entry.getValue());
			}
		}else if(clu.getPublishing()!=null){
			luDao.delete(clu.getPublishing());
		}
		
		//Update the list of AtpTypeKeys
		//Get a map of Id->object of all the currently persisted objects in the list
		Map<String, CluAtpTypeKey> oldOfferedAtpTypesMap = new HashMap<String, CluAtpTypeKey>();
		for(CluAtpTypeKey cluAtpTypeKey : clu.getOfferedAtpTypes()){
			oldOfferedAtpTypesMap.put(cluAtpTypeKey.getAtpTypeKey(),cluAtpTypeKey);
		}
		clu.getOfferedAtpTypes().clear();
		
		//Loop through the new list, if the item exists already update and remove from the list
		//otherwise create a new entry
		for(String atpTypeKey : cluInfo.getOfferedAtpTypes()){
			CluAtpTypeKey cluAtpTypeKey = oldOfferedAtpTypesMap.remove(atpTypeKey);
			if(cluAtpTypeKey == null){
				cluAtpTypeKey = new CluAtpTypeKey();
			}
			//Do Copy
			cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
			clu.getOfferedAtpTypes().add(cluAtpTypeKey);
		}
		
		//Now delete anything left over
		for(Entry<String, CluAtpTypeKey> entry:oldOfferedAtpTypesMap.entrySet()){
			luDao.delete(entry.getValue());
		}
		
		if(cluInfo.getFeeInfo()!=null){
			if(clu.getFee() == null){
				clu.setFee(new CluFee());
			}
			clu.getFee().setAttributes(LuServiceAssembler.toGenericAttributes(CluFeeAttribute.class, cluInfo.getFeeInfo().getAttributes(), clu.getFee(), luDao));
		}else if(clu.getFee()!=null){
			luDao.delete(clu.getFee());
		}
		
		if(cluInfo.getAccountingInfo()!=null){
			if(clu.getAccounting() == null){
				clu.setAccounting(new CluAccounting());
			}
			clu.getAccounting().setAttributes(LuServiceAssembler.toGenericAttributes(CluAccountingAttribute.class, cluInfo.getAccountingInfo().getAttributes(), clu.getAccounting(), luDao));
		}else if(clu.getAccounting()!=null){
			luDao.delete(clu.getAccounting());
		}
			
		clu.setAttributes(LuServiceAssembler.toGenericAttributes(CluAttribute.class, cluInfo.getAttributes(), clu, luDao));
		
		//Now copy all not standard properties
		BeanUtils.copyProperties(cluInfo,clu,new String[]{"luType","officialIdentifier","alternateIdentifiers","desc","marketingDesc","participatingOrgs","luCodes",
					"primaryInstructor","instructors","stdDuration","codeInfo","publishingInfo","offeredAtpTypes","feeInfo","accountingInfo","attributes","metaInfo"});
		
		Clu updated = luDao.update(clu);
		
		return LuServiceAssembler.toCluInfo(updated);
	}

	@Override
	public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId,
			CluCluRelationInfo cluCluRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CluInfo updateCluState(String cluId, String luState)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        //Check Missing params
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luState, "luState");
        Clu clu = luDao.fetch(Clu.class, cluId);
        clu.setState(luState);
        Clu updated = luDao.update(clu);
		return LuServiceAssembler.toCluInfo(updated);
	}

	@Override
	public LuDocRelationInfo updateLuDocRelation(String luDocRelationId,
			LuDocRelationInfo luDocRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuStatementInfo updateLuStatement(String luStatementId,
			LuStatementInfo luStatementInfo) throws CircularReferenceException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        //Check Missing params
        checkForMissingParameter(luStatementId, "luStatementId");
        checkForMissingParameter(luStatementInfo, "luStatementInfo");

        //Set all the values on luStatementInfo
        luStatementInfo.setId(luStatementId);

        LuStatement stmt = null;

        //Update persistence entity from the luStatementInfo
        stmt = LuServiceAssembler.toLuStatementRelation(true, luStatementInfo, luDao);

        //Update the luStatement
        LuStatement updatedStmt = luDao.update(stmt);

        //Copy back to an luStatementInfo and return
        LuStatementInfo updLuStatementInfo = LuServiceAssembler.toLuStatementInfo(updatedStmt);
        return updLuStatementInfo;
	}

	@Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiInfo updateLuiState(String luiId, String luState)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReqComponentInfo updateReqComponent(String reqComponentId,
			ReqComponentInfo reqComponentInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        //Check Missing params
        checkForMissingParameter(reqComponentId, "reqComponentId");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        //Set all the values on reqComponentInfo
        reqComponentInfo.setId(reqComponentId);

        ReqComponent reqComp = null;

        //Update persistence entity from the reqComponentInfo
        reqComp = LuServiceAssembler.toReqComponentRelation(true, reqComponentInfo, luDao);

        //Update the reqComponen
        ReqComponent updatedReqComp = luDao.update(reqComp);

        //Copy back to an reqComponentInfo and return
        ReqComponentInfo updReqCompInfo = LuServiceAssembler.toReqComponentInfo(updatedReqComp);
        return updReqCompInfo;
	}

	@Override
	public List<ValidationResult> validateClu(String validationType,
			CluInfo cluInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateCluCluRelation(String validationType,
			CluCluRelationInfo cluCluRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateLuDocRelation(String validationType,
			LuDocRelationInfo luDocRelationInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateLuStatement(String validationType,
			LuStatementInfo luStatementInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResult> validateReqComponent(String validationType,
			ReqComponentInfo reqComponentInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getObjectTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateObject(String objectTypeKey, String stateKey,
			String info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateStructureData(String objectTypeKey, String stateKey,
			String info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EnumeratedValue> getEnumeration(String enumerationKey,
			String enumContextKey, String contextValue, Date contextDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchCriteriaTypeInfo getSearchCriteriaType(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchResultTypeInfo> getSearchResultTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchTypeInfo getSearchType(String searchTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByCriteria(
			String searchCriteriaTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SearchTypeInfo> getSearchTypesByResult(
			String searchResultTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	public LuDao getLuDao() {
		return luDao;
	}

	public void setLuDao(LuDao luDao) {
		this.luDao = luDao;
	}

	/**
	 * Check for missing parameter and throw localized exception if missing
	 *
	 * @param param
	 * @param parameter name
	 * @throws MissingParameterException
	 */
	private void checkForMissingParameter(Object param, String paramName)
			throws MissingParameterException {
		if (param == null) {
			throw new MissingParameterException(paramName + " can not be null");
		}
	}


}
