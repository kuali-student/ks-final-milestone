/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
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
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lu.dao.LuDao;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
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
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluAcademicSubjectOrg;
import org.kuali.student.lum.lu.entity.CluAccounting;
import org.kuali.student.lum.lu.entity.CluAccountingAttribute;
import org.kuali.student.lum.lu.entity.CluAccreditation;
import org.kuali.student.lum.lu.entity.CluAccreditationAttribute;
import org.kuali.student.lum.lu.entity.CluAdminOrg;
import org.kuali.student.lum.lu.entity.CluAdminOrgAttribute;
import org.kuali.student.lum.lu.entity.CluAtpTypeKey;
import org.kuali.student.lum.lu.entity.CluAttribute;
import org.kuali.student.lum.lu.entity.CluCampusLocation;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluCluRelationAttribute;
import org.kuali.student.lum.lu.entity.CluCredit;
import org.kuali.student.lum.lu.entity.CluFee;
import org.kuali.student.lum.lu.entity.CluFeeAttribute;
import org.kuali.student.lum.lu.entity.CluIdentifier;
import org.kuali.student.lum.lu.entity.CluInstructor;
import org.kuali.student.lum.lu.entity.CluInstructorAttribute;
import org.kuali.student.lum.lu.entity.CluPublishing;
import org.kuali.student.lum.lu.entity.CluPublishingAttribute;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.CluSetAttribute;
import org.kuali.student.lum.lu.entity.LearningObjective;
import org.kuali.student.lum.lu.entity.LrType;
import org.kuali.student.lum.lu.entity.LuCode;
import org.kuali.student.lum.lu.entity.LuCodeAttribute;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.LuDocumentRelationAttribute;
import org.kuali.student.lum.lu.entity.LuDocumentRelationType;
import org.kuali.student.lum.lu.entity.LuLuRelationType;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.LuStatementType;
import org.kuali.student.lum.lu.entity.LuType;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiAttribute;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.LuiLuiRelationAttribute;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.kuali.student.lum.lu.service.LuService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.lum.lu.service.LuService", serviceName = "LuService", portName = "LuService", targetNamespace = "http://student.kuali.org/wsdl/lu")
@Transactional(rollbackFor={Throwable.class})
public class LuServiceImpl implements LuService {
    
    final Logger logger = Logger.getLogger(LuServiceImpl.class);

    private LuDao luDao;
    private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

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
        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(addedCluSetId, "addedCluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        if(cluSet.isCriteriaSet()){
            throw new UnsupportedActionException("Can not add a CluSet to a dynamic CluSet");
        }

        CluSet addedCluSet = luDao.fetch(CluSet.class, addedCluSetId);

        checkCluSetCircularReference(addedCluSet,cluSetId);

        cluSet.getCluSets().add(addedCluSet);

        luDao.update(cluSet);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    private void checkCluSetCircularReference(CluSet addedCluSet,
            String cluSetId) throws CircularReferenceException {
        for(CluSet childSet:addedCluSet.getCluSets()){
            if(childSet.getId().equals(cluSetId)){
                throw new CircularReferenceException("Set already contains this Set");
            }
            checkCluSetCircularReference(childSet, cluSetId);
        }
    }

    @Override
    public StatusInfo addCluToCluSet(String cluId, String cluSetId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        if(cluSet.isCriteriaSet()){
            throw new UnsupportedActionException("Cannot add a Clu to a Dynamic CluSet");
        }

        Clu clu = luDao.fetch(Clu.class, cluId);

        for(Clu childClu:cluSet.getClus()){
            if(childClu.getId().equals(cluId)){
                StatusInfo statusInfo = new StatusInfo();
                statusInfo.setSuccess(false);
                statusInfo.setMessage("CluSet already contains Clu:"+cluId);
                return statusInfo;
            }
        }

        cluSet.getClus().add(clu);

        luDao.update(cluSet);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
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
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luStatementId, "luStatementId");

        LuStatement stmt = luDao.fetch(LuStatement.class, luStatementId);
        if (stmt == null) {
            throw new DoesNotExistException("LuStatement does not exist for id: " + luStatementId);
        }

        Clu clu = luDao.fetch(Clu.class, cluId);
        if (clu == null) {
            throw new DoesNotExistException("Clu does not exist for id: " + cluId);
        }

        List<Clu> cluList = stmt.getClus();
        if(cluList == null){
        	cluList = new ArrayList<Clu>();
        }
        for(Clu cluItem : cluList) {
            if(cluItem.getId().equals(cluId))  {
                throw new AlreadyExistsException("Statement with Id " + luStatementId + " already exists for Clu " + cluId);
            }
        }

        cluList.add(clu);
        stmt.setClus(cluList);

        luDao.update(stmt);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
    }

    @Override
    public StatusInfo addOutcomeLoToClu(String loId, String cluId)
    throws AlreadyExistsException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(loId, "loId");
        checkForMissingParameter(cluId, "cluId");

        Clu clu = luDao.fetch(Clu.class, cluId);
        if (clu == null) {
            throw new DoesNotExistException("Clu does not exist for id: " + cluId);
        }
        // TODO I'm assuming the loId passed in is good since that's external

        for(LearningObjective lo : clu.getLearningObjectives())
            if(lo.getLearningObjectiveId().equals(loId))
                throw new AlreadyExistsException(String.format("LearningObjective with id '%s' is already associated with Clu with id '%s'",loId,cluId));

        LearningObjective learningObjective = new LearningObjective();
        learningObjective.setClu(clu);
        learningObjective.setLearningObjectiveId(loId);

        clu.getLearningObjectives().add(learningObjective);
//      luDao.update(learningObjective);
        luDao.update(clu);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;
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

        if(clu.getAlternateIdentifiers()==null){
        	clu.setAlternateIdentifiers(new ArrayList<CluIdentifier>());
        }
        List<CluIdentifier> alternateIdentifiers = clu.getAlternateIdentifiers();

        for(CluIdentifierInfo cluIdInfo : cluInfo.getAlternateIdentifiers()){
            CluIdentifier identifier = new CluIdentifier();
            BeanUtils.copyProperties(cluIdInfo, identifier);
            alternateIdentifiers.add(identifier);
        }

        if(cluInfo.getDesc()!=null){
            clu.setDesc(LuServiceAssembler.toRichText(cluInfo.getDesc()));
        }

        if(cluInfo.getMarketingDesc()!=null){
            clu.setMarketingDesc(LuServiceAssembler.toRichText(cluInfo.getMarketingDesc()));
        }

        if(cluInfo.getPrimaryAdminOrg()!=null){
            CluAdminOrg primaryAdminOrg = new CluAdminOrg();
            BeanUtils.copyProperties(cluInfo.getPrimaryAdminOrg(),primaryAdminOrg,new String[]{"attributes"});
            primaryAdminOrg.setAttributes(LuServiceAssembler.toGenericAttributes(CluAdminOrgAttribute.class, 
                    cluInfo.getPrimaryAdminOrg().getAttributes(), primaryAdminOrg, luDao));
            clu.setPrimaryAdminOrg(primaryAdminOrg);
        }

        if(clu.getAlternateAdminOrgs()==null){
        	clu.setAlternateAdminOrgs(new ArrayList<CluAdminOrg>());
        }
        List<CluAdminOrg> alternateOrgs = clu.getAlternateAdminOrgs();
        for(AdminOrgInfo orgInfo: cluInfo.getAlternateAdminOrgs()){
            CluAdminOrg instructor = new CluAdminOrg();
            BeanUtils.copyProperties(orgInfo,instructor,new String[]{"attributes"});
            instructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluAdminOrgAttribute.class, 
                    orgInfo.getAttributes(), instructor, luDao));
            alternateOrgs.add(instructor);
        }
        
        if(cluInfo.getPrimaryInstructor()!=null){
            CluInstructor primaryInstructor = new CluInstructor();
            BeanUtils.copyProperties(cluInfo.getPrimaryInstructor(),primaryInstructor,new String[]{"attributes"});
            primaryInstructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, cluInfo.getPrimaryInstructor().getAttributes(), primaryInstructor, luDao));
            clu.setPrimaryInstructor(primaryInstructor);
        }
        
        if(clu.getInstructors()==null){
        	clu.setInstructors(new ArrayList<CluInstructor>());
        }
        List<CluInstructor> instructors = clu.getInstructors();
        for(CluInstructorInfo instructorInfo: cluInfo.getInstructors()){
            CluInstructor instructor = new CluInstructor();
            BeanUtils.copyProperties(instructorInfo,instructor,new String[]{"attributes"});
            instructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, instructorInfo.getAttributes(), instructor, luDao));
            instructors.add(instructor);
        }

        if(cluInfo.getStdDuration()!=null){
            clu.setStdDuration(LuServiceAssembler.toTimeAmount(cluInfo.getStdDuration()));
        }

        if(clu.getLuCodes()==null){
        	clu.setLuCodes(new ArrayList<LuCode>());
        }
        List<LuCode> luCodes = clu.getLuCodes();
        for(LuCodeInfo luCodeInfo:cluInfo.getLuCodes()){
            LuCode luCode = new LuCode();
            luCode.setAttributes(LuServiceAssembler.toGenericAttributes(LuCodeAttribute.class, luCodeInfo.getAttributes(), luCode, luDao));
            BeanUtils.copyProperties(luCodeInfo,luCode,new String[]{"attributes","metaInfo"});
            luCode.setClu(clu);
            luCodes.add(luCode);
        }

        if(cluInfo.getCreditInfo()!=null){
            clu.setCredit(LuServiceAssembler.toCluCredit(cluInfo.getCreditInfo()));//Required field
        }

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

            if(cluPublishing.getInstructors()==null){
            	cluPublishing.setInstructors(new ArrayList<CluInstructor>());
            }
            for(CluInstructorInfo instructorInfo: cluInfo.getPublishingInfo().getInstructors()){
                CluInstructor instructor = new CluInstructor();
                BeanUtils.copyProperties(instructorInfo,instructor,new String[]{"attributes"});
                instructor.setAttributes(LuServiceAssembler.toGenericAttributes(CluInstructorAttribute.class, instructorInfo.getAttributes(), instructor, luDao));
                cluPublishing.getInstructors().add(instructor);
            }

            clu.setPublishing(cluPublishing);
        }

        if(clu.getOfferedAtpTypes()==null){
        	clu.setOfferedAtpTypes(new ArrayList<CluAtpTypeKey>());
        }
        List<CluAtpTypeKey> offeredAtpTypes = clu.getOfferedAtpTypes();
        for(String atpTypeKey : cluInfo.getOfferedAtpTypes()){
            CluAtpTypeKey cluAtpTypeKey = new CluAtpTypeKey();
            cluAtpTypeKey.setAtpTypeKey(atpTypeKey);
            cluAtpTypeKey.setClu(clu);
            offeredAtpTypes.add(cluAtpTypeKey);
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

        if(clu.getAcademicSubjectOrgs()==null){
        	clu.setAcademicSubjectOrgs(new ArrayList<CluAcademicSubjectOrg>());
        }
        List<CluAcademicSubjectOrg> subjectOrgs = clu.getAcademicSubjectOrgs(); 
        for(String orgId:cluInfo.getAcademicSubjectOrgs()){
            CluAcademicSubjectOrg subjOrg = new CluAcademicSubjectOrg();
            subjOrg.setOrgId(orgId);
            subjOrg.setClu(clu);
            subjectOrgs.add(subjOrg);
        }

        if(cluInfo.getIntensity()!=null){
            clu.setIntensity(LuServiceAssembler.toTimeAmount(cluInfo.getIntensity()));
        }

        if(clu.getCampusLocationList()==null){
        	clu.setCampusLocationList(new ArrayList<CluCampusLocation>());
        }
        List<CluCampusLocation> locations = clu.getCampusLocationList(); 
        for(String locationName:cluInfo.getCampusLocationList()){
            CluCampusLocation location = new CluCampusLocation();
            location.setCampusLocation(locationName);
            location.setClu(clu);
            locations.add(location);
        }
        
        if(clu.getAccreditationList()==null){
        	clu.setAccreditationList(new ArrayList<CluAccreditation>());
        }
        List<CluAccreditation> accreditations = clu.getAccreditationList();
        for(AccreditationInfo accreditationInfo: cluInfo.getAccreditationList()){
            CluAccreditation accreditation = new CluAccreditation();
            BeanUtils.copyProperties(accreditationInfo,accreditation,new String[]{"attributes"});
            accreditation.setAttributes(LuServiceAssembler.toGenericAttributes(CluAccreditationAttribute.class, accreditationInfo.getAttributes(), accreditation, luDao));
            accreditations.add(accreditation);
        }
        
        //Now copy all not standard properties
        BeanUtils.copyProperties(cluInfo,clu,new String[]{"luType","officialIdentifier","alternateIdentifiers","desc","marketingDesc","participatingOrgs","luCodes",
                "primaryInstructor","instructors","stdDuration","codeInfo","publishingInfo","offeredAtpTypes","feeInfo","accountingInfo","attributes","metaInfo",
                "academicSubjectOrgs","intensity", "campusLocationList", "accreditationList", "primaryAdminOrg", "alternateAdminOrgs"});

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

        if(cluId.equals(relatedCluId)){
            throw new CircularReferenceException("Can not relate a Clu to itself");
        }

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
    public CluSetInfo createEnumeratedCluSet(String cluSetName,
            CluSetInfo cluSetInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException {

        checkForMissingParameter(cluSetName, "cluSetName");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

//        if(cluSetInfo.getCluCriteria()!=null){
//            throw new InvalidParameterException("Enumerated CluSets can not contain Criteria");
//        }

        CluSet cluSet = new CluSet();
        BeanUtils.copyProperties(cluSetInfo, cluSet, new String[] { "id", "desc", "name", "attributes", "metaInfo" });
        cluSet.setAttributes(LuServiceAssembler.toGenericAttributes(CluSetAttribute.class, cluSetInfo.getAttributes(), cluSet, luDao));
        cluSet.setName(cluSetName);
        cluSet.setDesc(LuServiceAssembler.toRichText(cluSetInfo.getDesc()));

        for(String cluId:cluSetInfo.getCluIds()){
            cluSet.getClus().add(luDao.fetch(Clu.class, cluId));
        }
        for(String cluSetId:cluSetInfo.getCluSetIds()){
            cluSet.getCluSets().add(luDao.fetch(CluSet.class, cluSetId));
        }
        cluSet.setCriteriaSet(false);
        luDao.create(cluSet);

        return LuServiceAssembler.toCluSetInfo(cluSet);
    }

    @Override
    public LuDocRelationInfo createLuDocRelationForClu(
            String luDocRelationType, String documentId, String cluId,
            LuDocRelationInfo luDocRelationInfo) throws AlreadyExistsException,
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(luDocRelationType, "luDocRelationType");
        checkForMissingParameter(documentId, "documentId");
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luDocRelationInfo, "luDocRelationInfo");

        LuDocumentRelation docRelation = new LuDocumentRelation();
        Clu clu = luDao.fetch(Clu.class, cluId);
        LuDocumentRelationType docRelationType = luDao.fetch(LuDocumentRelationType.class, luDocRelationType);

        BeanUtils.copyProperties(luDocRelationInfo, docRelation, new String[] { "id", "desc", "type", "cluId", "attributes", "documentId", "metaInfo" });
        docRelation.setClu(clu);
        docRelation.setLuDocumentRelationType(docRelationType);
        docRelation.setDesc(LuServiceAssembler.toRichText(luDocRelationInfo.getDesc()));
        docRelation.setDocumentId(documentId);
        docRelation.setAttributes(LuServiceAssembler.toGenericAttributes(LuDocumentRelationAttribute.class, luDocRelationInfo.getAttributes(), docRelation, luDao));

        luDao.create(docRelation);

        return LuServiceAssembler.toLuDocRelationInfo(docRelation);
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

        LuStatementInfo info = LuServiceAssembler.toLuStatementInfo(luStatement);

        return info;
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
        luiInfo.setAtpId(atpKey);

        try {
            lui = LuServiceAssembler.toLui(false, luiInfo, luDao);
        } catch (VersionMismatchException vme) {
        }

        luDao.create(lui);

        return LuServiceAssembler.toLuiInfo(lui);
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {

        checkForMissingParameter(luiId, "luiId");
        checkForMissingParameter(luiInfo, "luiInfo");

        Lui lui = luDao.fetch(Lui.class, luiId);

        if (!String.valueOf(lui.getVersionInd()).equals(luiInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("Lui to be updated is not the current version");
        }

        Clu clu = luDao.fetch(Clu.class, luiInfo.getCluId());
        lui.setClu(clu);

        lui.setAttributes(LuServiceAssembler.toGenericAttributes(LuiAttribute.class, luiInfo.getAttributes(), lui, luDao));

        //Now copy standard properties
        BeanUtils.copyProperties(luiInfo, lui, new String[] { "cluId", "attributes" });

        Lui updated = luDao.update(lui);

        return LuServiceAssembler.toLuiInfo(updated);
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

        if(luiId.equals(relatedLuiId)){
            throw new CircularReferenceException("Can not relate a Lui to itself");
        }

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

        reqComp = luDao.create(reqComp);

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
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");

        luDao.delete(CluCluRelation.class, cluCluRelationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public StatusInfo deleteCluSet(String cluSetId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        checkForMissingParameter(cluSetId, "cluSetId");

        luDao.delete(CluSet.class, cluSetId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
    }

    @Override
    public StatusInfo deleteLuDocRelation(String luDocRelationId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        checkForMissingParameter(luDocRelationId, "luDocRelationId");

        luDao.delete(LuDocumentRelation.class, luDocRelationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
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

        checkForMissingParameter(luiId, "luiId");

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

        checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");

        luDao.delete(LuiLuiRelation.class, luiLuiRelationId);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);

        return statusInfo;
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
        checkForMissingParameter(cluSetId, "cluSetId");
        List<Clu> clus = new ArrayList<Clu>();
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        findClusInCluSet(clus, cluSet);
        List<String> ids = new ArrayList<String>(clus.size());
        for (Clu clu : clus) {
            ids.add(clu.getId());
        }
        return ids;
    }

    @Override
    public List<CluInfo> getAllClusInCluSet(String cluSetId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        checkForMissingParameter(cluSetId, "cluSetId");
        List<Clu> clus = new ArrayList<Clu>();
        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);
        findClusInCluSet(clus, cluSet);
        List<CluInfo> infos = new ArrayList<CluInfo>(clus.size());
        for (Clu clu : clus) {
            infos.add(LuServiceAssembler.toCluInfo(clu));
        }
        return infos;
    }

    private void findClusInCluSet(List<Clu> clus, CluSet parentCluSet) throws DoesNotExistException {
        for (Clu clu : parentCluSet.getClus()) {
            if (!clus.contains(clu)) {
                clus.add(clu);
            }
        }
        // Recursion possible problem? Stack overflow
        for (CluSet cluSet : parentCluSet.getCluSets()) {
            findClusInCluSet(clus, cluSet);
        }
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
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
        return LuServiceAssembler.toCluCluRelationInfo(luDao.fetch(CluCluRelation.class, cluCluRelationId));
    }

    @Override
    public List<CluCluRelationInfo> getCluCluRelationsByClu(String cluId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluId, "cluId");
        List<CluCluRelation> cluCluRelations = luDao.getCluCluRelationsByClu(cluId);
        return LuServiceAssembler.toCluCluRelationInfos(cluCluRelations);
    }

    @Override
    public List<String> getCluIdsByLoId(String loId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(loId, "loId");
        List<String> clus = luDao.getCluIdsByLoId(loId);
        return clus;
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
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

        List<Clu> clus = luDao.getClusByRelation(relatedCluId, luLuRelationTypeKey);
        List<String> ids = new ArrayList<String>(clus.size());
        for (Clu clu : clus) {
            ids.add(clu.getId());
        }
        return ids;
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
        checkForEmptyList(cluSetIdList, "cluSetIdList");
        List<CluSet> cluSets = luDao.getCluSetInfoByIdList(cluSetIdList);
        return LuServiceAssembler.toCluSetInfos(cluSets);
    }

    @Override
    public List<CluInfo> getClusByIdList(List<String> cluIdList)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluIdList, "cluIdList");
        checkForEmptyList(cluIdList, "cluIdList");
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
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(relatedCluId, "relatedCluId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

        List<Clu> clus = luDao.getClusByRelation(relatedCluId, luLuRelationTypeKey);
        return LuServiceAssembler.toCluInfos(clus);

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
        checkForMissingParameter(cluId, "cluId");
        Clu clu = luDao.fetch(Clu.class, cluId);
        List<String> ids = new ArrayList<String>(clu.getLearningObjectives().size());
        for (LearningObjective lo : clu.getLearningObjectives()) {
            ids.add(lo.getLearningObjectiveId());
        }
        return ids;
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
        checkForMissingParameter(lrTypeKey, "lrTypeKey");
        return LuServiceAssembler.toLrTypeInfo(luDao.fetch(LrType.class, lrTypeKey));

    }

    @Override
    public List<LrTypeInfo> getLrTypes() throws OperationFailedException {
        return LuServiceAssembler.toLrTypeInfos(luDao.find(LrType.class));
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

        checkForMissingParameter(luDocRelationId, "luDocRelationId");

        LuDocumentRelation docRelation = luDao.fetch(LuDocumentRelation.class, luDocRelationId);
        return LuServiceAssembler.toLuDocRelationInfo(docRelation);
    }

    @Override
    public LuDocRelationTypeInfo getLuDocRelationType(
            String luDocRelationTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        checkForMissingParameter(luDocRelationTypeKey, "luDocRelationTypeKey");

        LuDocumentRelationType docRelationType = luDao.fetch(LuDocumentRelationType.class, luDocRelationTypeKey);
        return LuServiceAssembler.toLuDocRelationTypeInfo(docRelationType);
    }

    @Override
    public List<LuDocRelationTypeInfo> getLuDocRelationTypes()
    throws OperationFailedException {

        List<LuDocumentRelationType> docRelationTypes = luDao.find(LuDocumentRelationType.class);
        return LuServiceAssembler.toLuDocRelationTypeInfos(docRelationTypes);
    }

    @Override
    public List<LuDocRelationInfo> getLuDocRelationsByClu(String cluId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        checkForMissingParameter(cluId, "cluId");

        List<LuDocumentRelation> luDocRelationsByClu = luDao.getLuDocRelationsByClu(cluId);
        return  LuServiceAssembler.toLuDocRelationInfos(luDocRelationsByClu);
    }

    @Override
    public List<LuDocRelationInfo> getLuDocRelationsByDocument(String documentId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        checkForMissingParameter(documentId, "documentId");

        List<LuDocumentRelation> luDocRelationsByDocument = luDao.getLuDocRelationsByDocument(documentId);
        return  LuServiceAssembler.toLuDocRelationInfos(luDocRelationsByDocument);
    }

    @Override
    public List<LuDocRelationInfo> getLuDocRelationsByIdList(
            List<String> luDocRelationIdList) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        checkForMissingParameter(luDocRelationIdList, "luDocRelationIdList");
        checkForEmptyList(luDocRelationIdList, "luDocRelationIdList");

        List<LuDocumentRelation> luDocRelationsByIdList = luDao.getLuDocRelationsByIdList(luDocRelationIdList);
        return  LuServiceAssembler.toLuDocRelationInfos(luDocRelationsByIdList);
    }

    @Override
    public List<LuDocRelationInfo> getLuDocRelationsByType(
            String luDocRelationTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        checkForMissingParameter(luDocRelationTypeKey, "luDocRelationTypeKey");

        List<LuDocumentRelation> luDocRelationsByType = luDao.getLuDocRelationsByType(luDocRelationTypeKey);
        return  LuServiceAssembler.toLuDocRelationInfos(luDocRelationsByType);
    }

    @Override
    public LuLuRelationTypeInfo getLuLuRelationTypeInfo(
            String luLuRelationTypeKey) throws OperationFailedException, MissingParameterException, DoesNotExistException {
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

        LuLuRelationType luLuRelationType = luDao.fetch(LuLuRelationType.class, luLuRelationTypeKey);
        return LuServiceAssembler.toLuLuRelationTypeInfo(luLuRelationType);
    }

    @Override
    public List<LuLuRelationTypeInfo> getLuLuRelationTypeInfos()
    throws OperationFailedException {
        return LuServiceAssembler.toLuLuRelationTypeInfos(luDao.find(LuLuRelationType.class));
    }

    @Override
    public LuStatementInfo getLuStatement(String luStatementId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return LuServiceAssembler.toLuStatementInfo(luDao.fetch(LuStatement.class, luStatementId));
    }

    @Override
    public List<LuStatementInfo> getLuStatements(List<String> luStatementIdList)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return LuServiceAssembler.toLuStatementInfos(luDao.getLuStatements(luStatementIdList));
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
        checkForMissingParameter(luStatementTypeKey, "luStatementTypeKey");

        LuStatementType stmtType = luDao.fetch(LuStatementType.class, luStatementTypeKey);
        if(null == stmtType) {
            throw new DoesNotExistException("LuStatement Type: " + luStatementTypeKey + " does not exist.");
        }

        return LuServiceAssembler.toLuStatementTypeInfos( stmtType.getAllowedLuStatementTypes() );
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
        checkForMissingParameter(cluId, "cluId");

        List<LuStatement> luStatements = luDao.getLuStatementsForClu(cluId);
        return LuServiceAssembler.toLuStatementInfos(luStatements);
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

        checkForMissingParameter(cluId, "cluId");

        return luDao.getLuiIdsByCluId(cluId);
    }

    @Override
    public List<String> getLuiIdsByLoId(String loId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(atpKey, "atpKey");
        return luDao.getLuiIdsInAtpByCluId(cluId, atpKey);

    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");
        LuiLuiRelation luiLuiRelation = luDao.fetch(LuiLuiRelation.class, luiLuiRelationId);
        return LuServiceAssembler.toLuiLuiRelationInfo(luiLuiRelation);
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelations(String luiId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(luiId, "luiId");
        List<LuiLuiRelation> entities = luDao.getLuiLuiRelations(luiId);
        return LuServiceAssembler.toLuiLuiRelationInfos(entities);
    }

    @Override
    public List<LuiInfo> getLuisByIdList(List<String> luiIdList)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(luiIdList, "luiIdList");
        checkForEmptyList(luiIdList, "luiIdList");
        List<Lui> luis = luDao.getLuisByIdList(luiIdList);
        return LuServiceAssembler.toLuiInfos(luis);
    }

    @Override
    public List<LuiInfo> getLuisByRelation(String luiId,
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luiId, "luiId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

        return LuServiceAssembler.toLuiInfos(luDao.getLuisByRelationType(luiId, luLuRelationTypeKey));
    }

    @Override
    public List<String> getLuiIdsByRelation(String luiId,
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luiId, "luiId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");

        return luDao.getLuiIdsByRelationType(luiId, luLuRelationTypeKey);
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
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
        List<String> relatedCluIds = luDao.getRelatedCluIdsByCluId(cluId, luLuRelationTypeKey);
        return relatedCluIds;
    }

    @Override
    public List<CluInfo> getRelatedClusByCluId(String cluId,
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
        List<Clu> relatedClus = luDao.getRelatedClusByCluId(cluId, luLuRelationTypeKey);
        return LuServiceAssembler.toCluInfos(relatedClus);
    }

    @Override
    public List<String> getRelatedLuiIdsByLuiId(String luiId,
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luiId, "luiId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
        List<String> relatedLuiIds = luDao.getRelatedLuiIdsByLuiId(luiId, luLuRelationTypeKey);
        return relatedLuiIds;
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
            String luLuRelationTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(luiId, "luiId");
        checkForMissingParameter(luLuRelationTypeKey, "luLuRelationTypeKey");
        List<Lui> relatedLuis = luDao.getRelatedLuisByLuiId(luiId, luLuRelationTypeKey);
        return LuServiceAssembler.toLuiInfos(relatedLuis);
    }

    @Override
    public ReqComponentInfo getReqComponent(String reqComponentId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return LuServiceAssembler.toReqComponentInfo(luDao.fetch(ReqComponent.class, reqComponentId));
    }

    @Override
    public List<ReqComponentInfo> getReqComponents(List<String> reqComponentIdList)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

    	return LuServiceAssembler.toReqComponentInfos(luDao.getReqComponents(reqComponentIdList));
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
        checkForMissingParameter(luStatementTypeKey, "luStatementTypeKey");

        LuStatementType stmtType = luDao.fetch(LuStatementType.class, luStatementTypeKey);
        if(null == stmtType) {
            throw new DoesNotExistException("LuStatement Type: " + luStatementTypeKey + " does not exist.");
        }

        return LuServiceAssembler.toReqComponentTypeInfos( stmtType.getAllowedReqComponentTypes() );
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
        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");
        return luDao.isCluInCluSet(cluId, cluSetId);
    }

    @Override
    public StatusInfo removeCluFromCluSet(String cluId, String cluSetId)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException, UnsupportedActionException {

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(cluSetId, "cluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        if(cluSet.isCriteriaSet()){
            throw new UnsupportedActionException("Cannot remove a Clu from a Dynamic CluSet");
        }

        for(Iterator<Clu> i = cluSet.getClus().iterator();i.hasNext();){
            Clu clu = i.next();
            if(clu.getId().equals(cluId)){
                i.remove();
                luDao.update(cluSet);
                StatusInfo statusInfo = new StatusInfo();
                statusInfo.setSuccess(true);

                return statusInfo;
            }
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(false);
        statusInfo.setMessage("Clu set does not contain Clu:"+cluId);

        return statusInfo;
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

        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(removedCluSetId, "removedCluSetId");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        if(cluSet.isCriteriaSet()){
            throw new UnsupportedActionException("Cannot remove a CluSet from a Dynamic CluSet");
        }

        for(Iterator<CluSet> i = cluSet.getCluSets().iterator();i.hasNext();){
            CluSet childCluSet = i.next();
            if(childCluSet.getId().equals(removedCluSetId)){
                i.remove();
                luDao.update(cluSet);
                StatusInfo statusInfo = new StatusInfo();
                statusInfo.setSuccess(true);

                return statusInfo;
            }
        }

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(false);
        statusInfo.setMessage("CluSet does not contain CluSet:"+removedCluSetId);

        return statusInfo;
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

        checkForMissingParameter(cluId, "cluId");
        checkForMissingParameter(luStatementId, "luStatementId");

        LuStatement stmt = luDao.fetch(LuStatement.class, luStatementId);
        if (stmt == null) {
            throw new DoesNotExistException("LuStatement does not exist for id: " + luStatementId);
        }

        List<Clu> cluList = stmt.getClus();

        Clu clu = null;
        for(Clu cluItem : cluList) {
            if(cluItem.getId().equals(cluId))  {
                clu = cluItem;
            }
        }

        if(null == clu) {
            throw new DoesNotExistException("LuStatement with Id: " + luStatementId + " does not exist for Clu: " + cluId);
        }

        cluList.remove(clu);
        stmt.setClus(cluList);

        luDao.update(stmt);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(true);
        return statusInfo;

    }

    @Override
    public StatusInfo removeOutcomeLoFromClu(String loId, String cluId)
    throws DependentObjectsExistException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(loId, "loId");
        checkForMissingParameter(cluId, "cluId");

        Clu clu = luDao.fetch(Clu.class, cluId);
        if (clu == null) {
            throw new DoesNotExistException("Clu does not exist for id: " + cluId);
        }

        LearningObjective learningObjective = null;
        for(LearningObjective lo : clu.getLearningObjectives())
            if(lo.getLearningObjectiveId().equals(loId))
                learningObjective = lo;

        StatusInfo statusInfo = new StatusInfo();

        if(learningObjective == null) {
//          throw new DoesNotExistException(); //service definition doesn't require an exception here as I read it
            statusInfo.setSuccess(false); // should this be false?
            return statusInfo;
        }

        clu.getLearningObjectives().remove(learningObjective);
        luDao.update(clu);
        luDao.delete(learningObjective);

        statusInfo.setSuccess(true);
        return statusInfo;
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
    public List<Result> searchForResults(String searchTypeKey,
            List<QueryParamValue> queryParamValues)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        checkForMissingParameter(queryParamValues, "queryParamValues");

        return searchManager.searchForResults(searchTypeKey, queryParamValues, luDao);
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
            luCode.setClu(clu);
            clu.getLuCodes().add(luCode);
        }

        //Now delete anything left over
        for(Entry<String, LuCode> entry:oldLuCodeMap.entrySet()){
            luDao.delete(entry.getValue());
        }

        if(cluInfo.getCreditInfo()!=null){
            if(clu.getCredit()==null){
                clu.setCredit(new CluCredit());
            }
            LuServiceAssembler.copyCluCredit(cluInfo.getCreditInfo(),clu.getCredit());
        }else if(clu.getCredit()!=null){
            luDao.delete(clu.getCredit());
        }

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
            cluAtpTypeKey.setClu(clu);
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

        if(cluInfo.getIntensity()!=null){
            if(clu.getIntensity()==null){
                clu.setIntensity(new TimeAmount());
            }
            BeanUtils.copyProperties(cluInfo.getIntensity(), clu.getIntensity());
        }else if(clu.getIntensity()!=null){
            luDao.delete(clu.getIntensity());
        }
        
        
        //Update the list of academicSubjectOrgs
        //Get a map of Id->object of all the currently persisted objects in the list
        Map<String, CluAcademicSubjectOrg> oldOrgMap = new HashMap<String, CluAcademicSubjectOrg>();
        for(CluAcademicSubjectOrg subjOrg : clu.getAcademicSubjectOrgs()){
            oldOrgMap.put(subjOrg.getOrgId(),subjOrg);
        }
        clu.getAcademicSubjectOrgs().clear();

        //Loop through the new list, if the item exists already update and remove from the list
        //otherwise create a new entry
        for(String orgId : cluInfo.getAcademicSubjectOrgs()){
            CluAcademicSubjectOrg subjOrg = oldOrgMap.remove(orgId);
            if(subjOrg == null){
                subjOrg = new CluAcademicSubjectOrg();
            }
            //Do Copy
            subjOrg.setOrgId(orgId);
            subjOrg.setClu(clu);
            clu.getAcademicSubjectOrgs().add(subjOrg);
        }

        //Now delete anything left over
        for(Entry<String, CluAcademicSubjectOrg> entry:oldOrgMap.entrySet()){
            luDao.delete(entry.getValue());
        }
        
        
        //Update the list of campusLocations
        //Get a map of Id->object of all the currently persisted objects in the list
        Map<String, CluCampusLocation> oldLocationsMap = new HashMap<String, CluCampusLocation>();
        for(CluCampusLocation campus : clu.getCampusLocationList()){
            oldLocationsMap.put(campus.getCampusLocation(),campus);
        }
        clu.getCampusLocationList().clear();

        //Loop through the new list, if the item exists already update and remove from the list
        //otherwise create a new entry
        for(String locationName : cluInfo.getCampusLocationList()){
            CluCampusLocation location = oldLocationsMap.remove(locationName);
            if(location == null){
                location = new CluCampusLocation();
            }
            //Do Copy
            location.setCampusLocation(locationName);
            location.setClu(clu);
            clu.getCampusLocationList().add(location);
        }
        

        //Now delete anything left over
        for(Entry<String, CluCampusLocation> entry:oldLocationsMap.entrySet()){
            luDao.delete(entry.getValue());
        }

        //Update the List of accreditations
        //Get a map of Id->object of all the currently persisted objects in the list
        Map<String, CluAccreditation> oldAccreditationMap = new HashMap<String, CluAccreditation>();
        for(CluAccreditation cluAccreditation : clu.getAccreditationList()){
            oldAccreditationMap.put(cluAccreditation.getOrgId(),cluAccreditation);
        }
        clu.getAccreditationList().clear();

        //Loop through the new list, if the item exists already update and remove from the list
        //otherwise create a new entry
        for(AccreditationInfo accreditationInfo : cluInfo.getAccreditationList()){
            CluAccreditation cluAccreditation = oldAccreditationMap.remove(accreditationInfo.getOrgId());
            if(cluAccreditation == null){
                cluAccreditation = new CluAccreditation();
            }
            //Do Copy
            BeanUtils.copyProperties(accreditationInfo,cluAccreditation,new String[]{"attributes"});
            cluAccreditation.setAttributes(LuServiceAssembler.toGenericAttributes(CluAccreditationAttribute.class, accreditationInfo.getAttributes(), cluAccreditation, luDao));
            clu.getAccreditationList().add(cluAccreditation);
        }

        //Now delete anything left over
        for(Entry<String, CluAccreditation> entry:oldAccreditationMap.entrySet()){
            luDao.delete(entry.getValue());
        }
        
        // Update the primary admin org
        if(cluInfo.getPrimaryAdminOrg()!=null){
            if(clu.getPrimaryAdminOrg() == null){
                clu.setPrimaryAdminOrg(new CluAdminOrg());
            }
            BeanUtils.copyProperties(cluInfo.getPrimaryAdminOrg(),clu.getPrimaryAdminOrg(),new String[]{"attributes"});
            clu.getPrimaryAdminOrg().setAttributes(LuServiceAssembler.toGenericAttributes(CluAdminOrgAttribute.class, 
                    cluInfo.getPrimaryAdminOrg().getAttributes(), clu.getPrimaryAdminOrg(), luDao));
        }else if(clu.getPrimaryAdminOrg()!=null){
            luDao.delete(clu.getPrimaryAdminOrg());
        }

        //Update the List of alternate admin orgs
        //Get a map of Id->object of all the currently persisted objects in the list
        Map<String, CluAdminOrg> oldAdminOrgsMap = new HashMap<String, CluAdminOrg>();
        for(CluAdminOrg cluOrg : clu.getAlternateAdminOrgs()){
            oldAdminOrgsMap.put(cluOrg.getOrgId(),cluOrg);
        }
        clu.getAlternateAdminOrgs().clear();

        //Loop through the new list, if the item exists already update and remove from the list
        //otherwise create a new entry
        for(AdminOrgInfo orgInfo : cluInfo.getAlternateAdminOrgs()){
            CluAdminOrg cluOrg = oldAdminOrgsMap.remove(orgInfo.getOrgId());
            if(cluOrg == null){
                cluOrg = new CluAdminOrg();
            }
            //Do Copy
            BeanUtils.copyProperties(orgInfo,cluOrg,new String[]{"attributes"});
            cluOrg.setAttributes(LuServiceAssembler.toGenericAttributes(CluAdminOrgAttribute.class, 
                    orgInfo.getAttributes(), cluOrg, luDao));
            clu.getAlternateAdminOrgs().add(cluOrg);
        }

        //Now delete anything left over
        for(Entry<String, CluAdminOrg> entry:oldAdminOrgsMap.entrySet()){
            luDao.delete(entry.getValue());
        }
        
        //Now copy all not standard properties
        BeanUtils.copyProperties(cluInfo,clu,new String[]{"luType","officialIdentifier","alternateIdentifiers","desc","marketingDesc","participatingOrgs","luCodes",
                "primaryInstructor","instructors","stdDuration","codeInfo","publishingInfo","offeredAtpTypes","feeInfo","accountingInfo","attributes","metaInfo",                
                "academicSubjectOrgs","intensity", "campusLocationList", "accreditationList", "primaryAdminOrg", "alternateAdminOrgs"});
        Clu updated=null;
        try{
            updated = luDao.update(clu);
        }catch (Exception e){
            logger.error("Exception occured: ", e);
        }
        return LuServiceAssembler.toCluInfo(updated);
    }

    @Override
    public CluCluRelationInfo updateCluCluRelation(final String cluCluRelationId,
            final CluCluRelationInfo cluCluRelationInfo)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        checkForMissingParameter(cluCluRelationId, "cluCluRelationId");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        final CluCluRelation cluCluRelation = luDao.fetch(CluCluRelation.class, cluCluRelationId);
        BeanUtils.copyProperties(cluCluRelationInfo, cluCluRelation, new String[] { "cluId", "relatedCluId", "isCluRelationRequired", "attributes", "metaInfo" });

        cluCluRelation.setClu(luDao.fetch(Clu.class,cluCluRelationInfo.getCluId()));
        cluCluRelation.setRelatedClu(luDao.fetch(Clu.class,cluCluRelationInfo.getRelatedCluId()));
        cluCluRelation.setCluRelationRequired(cluCluRelationInfo.getIsCluRelationRequired() == null? true: cluCluRelationInfo.getIsCluRelationRequired()); //TODO maybe this is unnecessary, contract specifies not null
        cluCluRelation.setAttributes(LuServiceAssembler.toGenericAttributes(CluCluRelationAttribute.class, cluCluRelationInfo.getAttributes(), cluCluRelation, luDao));

        cluCluRelation.setLuLuRelationType(luDao.fetch(LuLuRelationType.class, cluCluRelationInfo.getType()));

        final CluCluRelation update = luDao.update(cluCluRelation);

        return LuServiceAssembler.toCluCluRelationInfo(update);
    }

    @Override
    public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException, CircularReferenceException, UnsupportedActionException {
        //Check Missing params
        checkForMissingParameter(cluSetId, "cluSetId");
        checkForMissingParameter(cluSetInfo, "cluSetInfo");

        CluSet cluSet = luDao.fetch(CluSet.class, cluSetId);

        if (!String.valueOf(cluSet.getVersionInd()).equals(cluSetInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("CluSet to be updated is not the current version");
        }

        if(cluSet.isCriteriaSet()){
            if(cluSetInfo.getCluIds().size()>0||cluSetInfo.getCluSetIds().size()>0){
                throw new UnsupportedActionException("Criteria CluSets can not contain Clus or CluSets");
            }
            //TODO update criteria here
        }

        if(!cluSet.isCriteriaSet()){
//            if(cluSetInfo.getCluCriteria()!=null){
//                throw new UnsupportedActionException("Enumerated CluSets can not contain Criteria");
//            }

        	//update the cluIds
            Set<String> newCluIds = new HashSet<String>(cluSetInfo.getCluIds());
            for(Iterator<Clu> i = cluSet.getClus().iterator();i.hasNext();){
                if(!newCluIds.remove(i.next().getId())){
                    i.remove();
                }
            }
            for(String newCluId:newCluIds){
                this.addCluToCluSet(newCluId, cluSet.getId());
            }

            //update the cluSetIds
            Set<String> newCluSetIds = new HashSet<String>(cluSetInfo.getCluSetIds());
            for(Iterator<CluSet> i = cluSet.getCluSets().iterator();i.hasNext();){
                if(!newCluSetIds.remove(i.next().getId())){
                    i.remove();
                }
            }
            for(String newCluSetId:newCluSetIds){
                this.addCluSetToCluSet(cluSet.getId(), newCluSetId);
            }
        }

        BeanUtils.copyProperties(cluSetInfo, cluSet, new String[] { "desc", "attributes", "metaInfo" });
        cluSet.setAttributes(LuServiceAssembler.toGenericAttributes(CluSetAttribute.class, cluSetInfo.getAttributes(), cluSet, luDao));
        cluSet.setDesc(LuServiceAssembler.toRichText(cluSetInfo.getDesc()));

        CluSet updated = luDao.update(cluSet);

        return LuServiceAssembler.toCluSetInfo(updated);
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

        checkForMissingParameter(luDocRelationId, "luDocRelationId");
        checkForMissingParameter(luDocRelationInfo, "luDocRelationInfo");

        LuDocumentRelation docRelation = luDao.fetch(LuDocumentRelation.class, luDocRelationId);

        if (!String.valueOf(docRelation.getVersionInd()).equals(luDocRelationInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("LuDocRelation to be updated is not the current version");
        }

        Clu clu = luDao.fetch(Clu.class, luDocRelationInfo.getCluId());
        LuDocumentRelationType docRelationType = luDao.fetch(LuDocumentRelationType.class, luDocRelationInfo.getType());

        BeanUtils.copyProperties(luDocRelationInfo, docRelation, new String[] { "id", "desc", "type", "cluId", "attributes", "metaInfo" });
        docRelation.setClu(clu);
        docRelation.setLuDocumentRelationType(docRelationType);
        docRelation.setDesc(LuServiceAssembler.toRichText(luDocRelationInfo.getDesc()));
        docRelation.setAttributes(LuServiceAssembler.toGenericAttributes(LuDocumentRelationAttribute.class, luDocRelationInfo.getAttributes(), docRelation, luDao));

        LuDocumentRelation updated = luDao.update(docRelation);

        return LuServiceAssembler.toLuDocRelationInfo(updated);
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
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
            LuiLuiRelationInfo luiLuiRelationInfo)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {

        checkForMissingParameter(luiLuiRelationId, "luiLuiRelationId");
        checkForMissingParameter(luiLuiRelationInfo, "luiLuiRelationInfo");

        LuiLuiRelation luiLuiRelation = luDao.fetch(LuiLuiRelation.class, luiLuiRelationId);

        if (!String.valueOf(luiLuiRelation.getVersionInd()).equals(luiLuiRelationInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("LuiLuiRelation to be updated is not the current version");
        }

        BeanUtils.copyProperties(luiLuiRelationInfo, luiLuiRelation, new String[] { "luiId", "relatedLuiId", "attributes", "metaInfo" });

        if(!luiLuiRelationInfo.getLuiId().equals(luiLuiRelation.getLui().getId())){
            luiLuiRelation.setLui(luDao.fetch(Lui.class, luiLuiRelationInfo.getLuiId()));
        }

        if(!luiLuiRelationInfo.getRelatedLuiId().equals(luiLuiRelation.getRelatedLui().getId())){
            luiLuiRelation.setRelatedLui(luDao.fetch(Lui.class, luiLuiRelationInfo.getRelatedLuiId()));
        }

        luiLuiRelation.setAttributes(LuServiceAssembler.toGenericAttributes(LuiLuiRelationAttribute.class, luiLuiRelationInfo.getAttributes(), luiLuiRelation, luDao));

        if(!luiLuiRelationInfo.getType().equals(luiLuiRelation.getLuLuRelationType().getId())){
            luiLuiRelation.setLuLuRelationType(luDao.fetch(LuLuRelationType.class, luiLuiRelationInfo.getType()));
        }

        LuiLuiRelation updated = luDao.update(luiLuiRelation);

        return LuServiceAssembler.toLuiLuiRelationInfo(updated);
    }

    @Override
    public LuiInfo updateLuiState(String luiId, String luiState)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {

        // check for missing params
        checkForMissingParameter(luiId, "luiId");
        checkForMissingParameter(luiState, "luiState");
        Lui lui = luDao.fetch(Lui.class, luiId);
        lui.setState(luiState);
        Lui updated = luDao.update(lui);
        return LuServiceAssembler.toLuiInfo(updated);
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

    private Validator createValidator() {
//      Validator validator = new Validator();
//      validator.setDateParser(new ServerDateParser());
////    validator.addMessages(null); //TODO this needs to be loaded somehow
//      return validator;
        return null;
    }

    @Override
    public List<ValidationResultContainer> validateClu(String validationType,
            CluInfo cluInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(cluInfo, "cluInfo");

        return createValidator().validateTypeStateObject(cluInfo, getObjectStructure("cluInfo"));
    }

    @Override
    public List<ValidationResultContainer> validateCluCluRelation(String validationType,
            CluCluRelationInfo cluCluRelationInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(cluCluRelationInfo, "cluCluRelationInfo");

        return createValidator().validateTypeStateObject(cluCluRelationInfo, getObjectStructure("cluCluRelationInfo"));
    }

    @Override
    public List<ValidationResultContainer> validateLuDocRelation(String validationType,
            LuDocRelationInfo luDocRelationInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(luDocRelationInfo, "luDocRelationInfo");

        return createValidator().validateTypeStateObject(luDocRelationInfo, getObjectStructure("luDocRelationInfo"));
    }

    @Override
    public List<ValidationResultContainer> validateLuStatement(String validationType,
            LuStatementInfo luStatementInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(luStatementInfo, "luStatementInfo");

        return createValidator().validateTypeStateObject(luStatementInfo, getObjectStructure("luStatementInfo"));
    }

    @Override
    public List<ValidationResultContainer> validateReqComponent(String validationType,
            ReqComponentInfo reqComponentInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(reqComponentInfo, "reqComponentInfo");

        return createValidator().validateTypeStateObject(reqComponentInfo, getObjectStructure("reqComponentInfo"));
    }

    @Override
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

    @Override
    public boolean validateObject(String objectTypeKey, String stateKey,
            String info) {
        return dictionaryServiceDelegate.validateObject(objectTypeKey, stateKey, info);
    }

    @Override
    public boolean validateStructureData(String objectTypeKey, String stateKey,
            String info) {
        return dictionaryServiceDelegate.validateStructureData(objectTypeKey, stateKey, info);
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

        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
    throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
    throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes()
    throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(
            String searchResultTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey);
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

    /**
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForEmptyList(Object param, String paramName)
    throws MissingParameterException {
        if (param != null && param instanceof List<?> && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }
}
