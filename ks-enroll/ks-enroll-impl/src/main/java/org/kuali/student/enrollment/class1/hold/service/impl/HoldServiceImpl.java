/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.hold.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class1.hold.dao.HoldDao;
import org.kuali.student.enrollment.class1.hold.dao.HoldRichTextDao;
import org.kuali.student.enrollment.class1.hold.dao.HoldTypeDao;
import org.kuali.student.enrollment.class1.hold.dao.IssueDao;
import org.kuali.student.enrollment.class1.hold.dao.RestrictionDao;
import org.kuali.student.enrollment.class1.hold.model.HoldEntity;
import org.kuali.student.enrollment.class1.hold.model.HoldRichTextEntity;
import org.kuali.student.enrollment.class1.hold.model.HoldTypeEntity;
import org.kuali.student.enrollment.class1.hold.model.IssueEntity;
import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.dto.RestrictionInfo;
import org.kuali.student.enrollment.hold.service.HoldService;
import org.kuali.student.r2.common.dao.TypeTypeRelationDao;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;
import org.springframework.transaction.annotation.Transactional;

@WebService(name = "HoldService", serviceName = "HoldService", portName = "HoldService", targetNamespace = "http://student.kuali.org/wsdl/hold")
@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class HoldServiceImpl implements HoldService {

    private IssueDao issueDao;
    private RestrictionDao restrictionDao;
    private HoldTypeDao holdTypeDao;
    private HoldDao holdDao;
    private HoldRichTextDao holdRichTextDao;
    private TypeTypeRelationDao typeTypeRelationDao;
    private StateService stateService;
    
    public IssueDao getIssueDao() {
        return issueDao;
    }

    public void setIssueDao(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    public RestrictionDao getRestrictionDao() {
        return restrictionDao;
    }

    public void setRestrictionDao(RestrictionDao restrictionDao) {
        this.restrictionDao = restrictionDao;
    }

    public HoldTypeDao getHoldTypeDao() {
        return holdTypeDao;
    }

    public void setHoldTypeDao(HoldTypeDao holdTypeDao) {
        this.holdTypeDao = holdTypeDao;
    }
    
    public HoldDao getHoldDao() {
        return holdDao;
    }

    public void setHoldDao(HoldDao holdDao) {
        this.holdDao = holdDao;
    }

    public HoldRichTextDao getHoldRichTextDao() {
        return holdRichTextDao;
    }

    public void setHoldRichTextDao(HoldRichTextDao holdRichTextDao) {
        this.holdRichTextDao = holdRichTextDao;
    }

    public TypeTypeRelationDao getTypeTypeRelationDao() {
        return typeTypeRelationDao;
    }

    public void setTypeTypeRelationDao(TypeTypeRelationDao typetypeRelationDao) {
        this.typeTypeRelationDao = typetypeRelationDao;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	StateInfo stateInfo = stateService.getState(processKey, stateKey, context);
    	return stateInfo;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean isPersonRestricted(String restrictionKey, String personId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getRestrictedPersons(String restrictionKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByRestrictionForPerson(String restrictionKey, String personId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByRestrictionForPerson(String restrictionKey, String personId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public HoldInfo getHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        HoldEntity entity = holdDao.find(holdId);
        
        if(entity == null)
        	throw new DoesNotExistException(holdId);
        
        return entity.toDto();
    }

    @Override
    public List<HoldInfo> getHoldsByIdList(List<String> holdIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByIssue(String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getActiveHoldsForPerson(String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByIssueForPerson(String issueId, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByIssueForPerson(String issueId, String personId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    private StateEntity findState(String processKey, String stateKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException{
    	StateEntity state = null;
		try {
        	StateInfo stInfo = getState(processKey, stateKey, context);
        	if(stInfo != null){
        		state = new StateEntity(stInfo);
        		return state;
        	}
        	else
        		throw new OperationFailedException("The state does not exist. processKey " + processKey + " and stateKey: " + stateKey);
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("The state does not exist. processKey " + processKey + " and stateKey: " + stateKey);
		}			
    }
 
    private IssueEntity findIssue(String issueId) throws OperationFailedException{
    	IssueEntity issue = issueDao.find(issueId);
    	if(null != issue)
    		return issue;
    	else
    		throw new OperationFailedException("The issue does not exist. issue " + issueId);
    }
    
    private HoldTypeEntity findType(String typeId)throws OperationFailedException{
    	HoldTypeEntity type = holdTypeDao.find(typeId);
    	if(null != type)
    		return type;
    	else
    		throw new OperationFailedException("The type does not exist. type " + typeId);
    }

    @Override
    @Transactional
    public HoldInfo createHold(HoldInfo holdInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        HoldEntity entity = new HoldEntity(holdInfo);
        entity.setId(UUIDHelper.genStringUUID());
        
        if(null != holdInfo.getIssueId())
        	entity.setIssue(findIssue(holdInfo.getIssueId()));

        if (null != holdInfo.getStateKey())
        	entity.setHoldState(findState(HoldServiceConstants.STUDENT_HOLD_PROCESS_KEY, holdInfo.getStateKey(), context));
        
        if (null != holdInfo.getTypeKey())
        	entity.setHoldType(findType(holdInfo.getTypeKey()));

        if (null != holdInfo.getDescr())
        	entity.setDescr(new HoldRichTextEntity(holdInfo.getDescr()));
        
        HoldEntity existing = holdDao.find(entity.getId());
        if( existing != null) {
            throw new AlreadyExistsException();
	    }
        holdDao.persist(entity);
        
        return holdDao.find(entity.getId()).toDto();
    }

    @Override
    @Transactional
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
    	HoldEntity entity = holdDao.find(holdId);
        
        if( null != entity){
        	HoldEntity modifiedEntity = new HoldEntity(holdInfo);
            if(null != holdInfo.getIssueId())
            	entity.setIssue(findIssue(holdInfo.getIssueId()));
            if(holdInfo.getStateKey() != null)
            	modifiedEntity.setHoldState(findState(HoldServiceConstants.STUDENT_HOLD_PROCESS_KEY, holdInfo.getStateKey(), context));
            if(holdInfo.getTypeKey() != null)
            	modifiedEntity.setHoldType(findType(holdInfo.getTypeKey()));
            
            holdDao.merge(modifiedEntity);
	        return holdDao.find(modifiedEntity.getId()).toDto();
        }
        else
            throw new DoesNotExistException(holdId);
    }

    private HoldInfo updateHoldState(String holdId, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException{
    	HoldEntity entity = holdDao.find(holdId);
        
        if( null != entity){
        	entity.setHoldState(findState(HoldServiceConstants.STUDENT_HOLD_PROCESS_KEY, stateKey, context));
        	entity.setReleasedDate(new Date());
        	
        	holdDao.merge(entity);
 	        return holdDao.find(entity.getId()).toDto();
        }
        else
        	 throw new DoesNotExistException("The hold does not exist." + holdId);
       	
    }
    
    @Override
    @Transactional
    public HoldInfo releaseHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return updateHoldState(holdId, HoldServiceConstants.HOLD_RELEASED_STATE_KEY, context);
    }

    @Override
    @Transactional
    public StatusInfo deleteHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        
    	updateHoldState(holdId, HoldServiceConstants.HOLD_CANCELED_STATE_KEY, context);
    	return status;
    }

    @Override
    public IssueInfo getIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        IssueEntity entity = issueDao.find(issueId);
        
        if(entity == null) {
            throw new DoesNotExistException(issueId);
        }
        
        return entity.toDto();
    }

    @Override
    public List<IssueInfo> getIssuesByIdList(List<String> issueIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<IssueEntity> issues = issueDao.findByIds(issueIdList);
        
        if(issues == null) {
            throw new DoesNotExistException();
        }
        
        List<IssueInfo> result = new ArrayList<IssueInfo>(issues.size());
        for(IssueEntity entity : issues) {
            if(entity == null) {
                // if one of the entities from "findByIds" is returned as null, then one of the keys in the list was not found
                throw new DoesNotExistException();
            }
            result.add(entity.toDto());
        }
        
        return result;
    }

    @Override
    public List<String> getIssueIdsByType(String issueTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<IssueInfo> getIssuesByOrg(String organizationId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<IssueEntity> issues = issueDao.getByOrganizationId(organizationId);
        
        List<IssueInfo> results = new ArrayList<IssueInfo>(issues.size());
        
        for(IssueEntity issue : issues) {
            results.add(issue.toDto());
        }
        
        return results;
    }

    @Override
    public List<IssueInfo> getIssuesByRestriction(String restrictionKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo addIssueToRestriction(String restrictionKey, String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo removeIssueFromRestriction(String restrictionKey, String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey, IssueInfo issueInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public IssueInfo createIssue(IssueInfo issueInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public IssueInfo updateIssue(String issueId, IssueInfo issueInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RestrictionInfo getRestriction(String restrictionKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<RestrictionInfo> getRestrictionsByKeyList(List<String> restrictionKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getRestrictionKeysByType(String restrictionTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }


    @Override
    public List<RestrictionInfo> getRestrictionsByIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateRestriction(String validationTypeKey, RestrictionInfo restrictionInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RestrictionInfo createRestriction(String restrictionKey, RestrictionInfo restrictionInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public RestrictionInfo updateRestriction(String restrictionKey, RestrictionInfo restrictionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StatusInfo deleteRestriction(String restrictionKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO andy - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
