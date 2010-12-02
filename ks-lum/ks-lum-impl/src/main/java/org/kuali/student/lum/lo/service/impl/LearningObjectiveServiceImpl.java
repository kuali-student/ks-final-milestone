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

package org.kuali.student.lum.lo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.student.common.validator.Validator;
import org.kuali.student.common.validator.ValidatorFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.SearchManager;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lo.dao.LoDao;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationTypeInfo;
import org.kuali.student.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;
import org.kuali.student.lum.lo.entity.Lo;
import org.kuali.student.lum.lo.entity.LoCategory;
import org.kuali.student.lum.lo.entity.LoCategoryType;
import org.kuali.student.lum.lo.entity.LoLoRelation;
import org.kuali.student.lum.lo.entity.LoLoRelationType;
import org.kuali.student.lum.lo.entity.LoRepository;
import org.kuali.student.lum.lo.entity.LoType;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.springframework.transaction.annotation.Transactional;

/*n
 * @author Kuali Student team
 *
 */
@WebService(endpointInterface = "org.kuali.student.lum.lo.service.LearningObjectiveService", serviceName = "LearningObjectiveService", portName = "LearningObjectiveService", targetNamespace = "http://student.kuali.org/wsdl/lo")
@Transactional(noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class LearningObjectiveServiceImpl implements LearningObjectiveService {
    private LoDao loDao;
	private SearchManager searchManager;
    private DictionaryService dictionaryServiceDelegate;
	private ValidatorFactory validatorFactory;

	public LoDao getLoDao() {
        return loDao;
    }

    public void setLoDao(LoDao dao) {
        this.loDao = dao;
    }

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

    public void setDictionaryServiceDelegate(DictionaryService dictionaryServiceDelegate) {
        this.dictionaryServiceDelegate = dictionaryServiceDelegate;
    }

	public ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    /*
     * (non-Javadoc)
     * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoRepositories()
     */
	@Override
	public List<LoRepositoryInfo> getLoRepositories()
			throws OperationFailedException {
	    List<LoRepository> repositories = loDao.find(LoRepository.class);
		return LearningObjectiveServiceAssembler.toLoRepositoryInfos(repositories);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoRepository(java.lang.String)
	 */
	@Override
	public LoRepositoryInfo getLoRepository(String loRepositoryKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
		return LearningObjectiveServiceAssembler.toLoRepositoryInfo(loDao.fetch(LoRepository.class, loRepositoryKey));
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoTypes()
	 */
	@Override
	public List<LoTypeInfo> getLoTypes() throws OperationFailedException {
	    List<LoType> find = loDao.find(LoType.class);
		return LearningObjectiveServiceAssembler.toLoTypeInfos(find);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoType(java.lang.String)
	 */
	@Override
	public LoTypeInfo getLoType(String loTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(loTypeKey, "loTypeKey");
	    LoType fetch = loDao.fetch(LoType.class, loTypeKey);
		return LearningObjectiveServiceAssembler.toLoTypeInfo(fetch);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoLoRelationTypes()
	 */
	@Override
	public List<LoLoRelationTypeInfo> getLoLoRelationTypes()
			throws OperationFailedException {
	    List<LoLoRelationType> fetch = loDao.find(LoLoRelationType.class);
		return LearningObjectiveServiceAssembler.toLoLoRelationTypeInfos(fetch);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoLoRelationType(java.lang.String)
	 */
	@Override
	public LoLoRelationTypeInfo getLoLoRelationType(String loLoRelationTypeKey)
			throws OperationFailedException, MissingParameterException, DoesNotExistException {
	    checkForMissingParameter(loLoRelationTypeKey, "loLoRelationTypeKey");
		return LearningObjectiveServiceAssembler.toLoLoRelationTypeInfo(loDao.fetch(LoLoRelationType.class, loLoRelationTypeKey));
	}

	@Override
	public List<String> getAllowedLoLoRelationTypesForLoType(String loTypeKey, String relatedLoTypeKey)
			throws DoesNotExistException, InvalidParameterException,
					MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loTypeKey, "loTypeKey");
	    checkForMissingParameter(relatedLoTypeKey, "relatedLoTypeKey");
	    
	    return loDao.getAllowedLoLoRelationTypesForLoType(loTypeKey, relatedLoTypeKey);
	}

    /*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#addLoCategoryToLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo addLoCategoryToLo(String loCategoryId, String loId)
			throws AlreadyExistsException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			UnsupportedActionException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    checkForMissingParameter(loId, "loId");
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(loDao.addLoCategoryToLo(loCategoryId, loId));
        return statusInfo;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#createLo(java.lang.String, java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
	 */
	@Override
	public LoInfo createLo(String repositoryId, String loType, LoInfo loInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(repositoryId, "repositoryId");
	    checkForMissingParameter(loType, "loType");
	    checkForMissingParameter(loInfo, "loInfo");
	    
	    
		// Validate LO
		List<ValidationResultInfo> val = validateLo("SYSTEM", loInfo);
		if(null != val && val.size() > 0) {
			for (ValidationResultInfo result : val) {
				System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
			}
			throw new DataValidationErrorException("Validation error!");
		}
		
	    // make sure LoType and LoRepository exist before trying to create
	    // if checkForMissingParameter above did its job, we don't have to null-check these id's
	    LoType type = null;
	    LoRepository repository = null;
	    try {
		    type = loDao.fetch(LoType.class, loType);
		    repository = loDao.fetch(LoRepository.class, repositoryId); 
	    } catch (DoesNotExistException dnee) {
	    	throw new DoesNotExistException("Specified " + (null == type ? "LoType" : "LoRepository") + " does not exist", dnee);
	    }
	    
	    loInfo.setLoRepositoryKey(repositoryId);
	    loInfo.setType(loType);
	    
	    Lo lo = null;
	    try {
		    lo = LearningObjectiveServiceAssembler.toLo(false, loInfo, loDao);
	    } catch (VersionMismatchException vme) {
	    	// should never happen in a create call, but
	    	throw new OperationFailedException("VersionMismatchException caught during Learning Objective creation");
	    }
	    lo.setLoType(type);
	    lo.setLoRepository(repository);
	    loDao.create(lo);
	    
		return LearningObjectiveServiceAssembler.toLoInfo(lo);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#deleteLo(java.lang.String)
	 */
	@Override
	public StatusInfo deleteLo(String loId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loId, "loId");
	    
	    StatusInfo returnStatus = new StatusInfo();
	    returnStatus.setSuccess(loDao.deleteLo(loId));
		return returnStatus;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#deleteLoCategory(java.lang.String)
	 */
	@Override
	public StatusInfo deleteLoCategory(String loCategoryId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    
	    loDao.deleteLoCategory(loCategoryId);
	    
		return new StatusInfo();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLo(java.lang.String)
	 */
	@Override
	public LoInfo getLo(String loId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(loId, "loId");
	    
		return LearningObjectiveServiceAssembler.toLoInfo(loDao.fetch(Lo.class, loId));
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoByIdList(java.util.List)
	 */
	@Override
	public List<LoInfo> getLoByIdList(List<String> loIds)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(loIds, "loId");
	    checkForEmptyList(loIds, "loId");
	    List<Lo> los = loDao.getLoByIdList(loIds);
		return LearningObjectiveServiceAssembler.toLoInfos(los);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoCategories(java.lang.String)
	 */
	@Override
	public List<LoCategoryInfo> getLoCategories(String loRepositoryKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
	    List<LoCategory> categories = loDao.getLoCategories(loRepositoryKey);
        return LearningObjectiveServiceAssembler.toLoCategoryInfos(categories);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoCategoriesForLo(java.lang.String)
	 */
	@Override
	public List<LoCategoryInfo> getLoCategoriesForLo(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loId, "loId");
	    List<LoCategory> categories = loDao.getLoCategoriesForLo(loId);
		return LearningObjectiveServiceAssembler.toLoCategoryInfos(categories);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoCategory(java.lang.String)
	 */
	@Override
	public LoCategoryInfo getLoCategory(String loCategoryId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    
		return LearningObjectiveServiceAssembler.toLoCategoryInfo(loDao.fetch(LoCategory.class, loCategoryId));
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoParents(java.lang.String)
	public List<LoInfo> getLoParents(String loId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(loId, "loId");
	    List<Lo> loParents = loDao.getLoParents(loId);
		return LearningObjectiveServiceAssembler.toLoInfos(loParents);
	}
	 */

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLosByLoCategory(java.lang.String)
	 */
	@Override
	public List<LoInfo> getLosByLoCategory(String loCategoryId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    List<Lo> los = loDao.getLosByLoCategory(loCategoryId);
		return LearningObjectiveServiceAssembler.toLoInfos(los);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#isDescendant(java.lang.String, java.lang.String)
	@Override
	public Boolean isDescendant(String loId, String descendantLoId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(descendantLoId, "descendantLoId");
		return loDao.isDescendant(loId, descendantLoId);
	}
	 */

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#isEquivalent(java.lang.String, java.lang.String)
	@Override
	public Boolean isEquivalent(String loId, String equivalentLoId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(equivalentLoId, "equivalentLoId");
		return loDao.isEquivalent(loId, equivalentLoId);
	}
	 */

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#removeChildLoFromLo(java.lang.String, java.lang.String)
	@Override
	public StatusInfo removeChildLoFromLo(String loId, String parentLoId)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(parentLoId, "parentLoId");
	    
	    StatusInfo statusInfo = new StatusInfo();
	    statusInfo.setSuccess(loDao.removeChildLoFromLo(loId, parentLoId));
		return statusInfo;
	}
	 */

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#removeEquivalentLoFromLo(java.lang.String, java.lang.String)
	@Override
	public StatusInfo removeEquivalentLoFromLo(String loId,
			String equivalentLoId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(equivalentLoId, "equivalentLoId");
	    
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(loDao.removeEquivalentLoFromLo(loId, equivalentLoId));
        return statusInfo;
	}
	 */

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#removeLoCategoryFromLo(java.lang.String, java.lang.String)
	 */
	@Override
	public StatusInfo removeLoCategoryFromLo(String loCategoryId, String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, UnsupportedActionException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    checkForMissingParameter(loId, "loId");
	    
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(loDao.removeLoCategoryFromLo(loCategoryId, loId));
        return statusInfo;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#updateLo(java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
	 */
	@Override
	public LoInfo updateLo(String loId, LoInfo loInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(loInfo, "loInfo");

		// Validate LO
		List<ValidationResultInfo> val = validateLo("SYSTEM", loInfo);
		if(null != val && val.size() > 0) {
			for (ValidationResultInfo result : val) {
				System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
			}
			throw new DataValidationErrorException("Validation error!");
		}
		
	    Lo lo = loDao.fetch(Lo.class, loId);
        
        if (!String.valueOf(lo.getVersionNumber()).equals(loInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("LO to be updated is not the current version");
        }
        
        lo = LearningObjectiveServiceAssembler.toLo(true, lo, loInfo, loDao);
        loDao.update(lo);
        return LearningObjectiveServiceAssembler.toLoInfo(lo);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#updateLoCategory(java.lang.String, org.kuali.student.lum.lo.dto.LoCategoryInfo)
	 */
	@Override
	public LoCategoryInfo updateLoCategory(String loCategoryId,
			LoCategoryInfo loCategoryInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    checkForMissingParameter(loCategoryInfo, "loCategoryInfo");
	    
		// Validate LoCategory
		List<ValidationResultInfo> val = validateLoCategory("SYSTEM", loCategoryInfo);

		//kslum-136 - don't allow dups w/ same name (case insensitive), type, state & repository
        if (doesLoCategoryExist(loCategoryInfo.getLoRepository(), loCategoryInfo, loCategoryId)) {
            ValidationResultInfo vr = new ValidationResultInfo();
            vr.setElement("LO Category Name");
            vr.setError("LO Category already exists");
            val.add(vr);
        }
        if(null != val && val.size() > 0) {
            for (ValidationResultInfo result : val) {
                System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
            }
            throw new DataValidationErrorException("Validation error!", val);
        }
	    LoCategory loCategory = loDao.fetch(LoCategory.class, loCategoryId);
        
        if (!String.valueOf(loCategory.getVersionNumber()).equals(loCategoryInfo.getMetaInfo().getVersionInd())){
            throw new VersionMismatchException("LoCategory to be updated is not the current version");
        }
        
        // if state is changing from "active"
        if (loCategory.getState().equals("active") && ( ! loCategoryInfo.getState().equals("active") )) {
    		// N.B. - ability to 'retire' LoCategory's that are still associated w/ active
    		// LO's is configured and enforced on the client
        	List<LoInfo> loInfos = getLosByLoCategory(loCategoryId);
    		if (null != loInfos) {
				// remove associations of this LoCategory from active LO's
    			for (LoInfo info : loInfos) {
    				if (info.getState().equals("active"))  {
	    				try {
							removeLoCategoryFromLo(loCategoryId, info.getId());
						} catch (UnsupportedActionException uaee) {
				    		throw new OperationFailedException("Unable to update LoCategory: could not remove association with active LearningObjective", uaee);
						}
    				}
    			}
    		}
        }
        	
        // if type is changing
        if ( ! loCategory.getLoCategoryType().getId().equals(loCategoryInfo.getType()) ) {
        	loCategory = cloneLoCategory(loCategory, loCategoryInfo);
        } else {
	        loCategory = LearningObjectiveServiceAssembler.toLoCategory(loCategory, loCategoryInfo, loDao);
	        loDao.update(loCategory);
        }
        return LearningObjectiveServiceAssembler.toLoCategoryInfo(loCategory);
	}

    // inactivate current LoCategory & clone it w/ its relationships,
	// used when changing immutable type of LoCategory
	// https://test.kuali.org/confluence/display/KULSTG/DS+-+LO+Centrally+Maintain+Categories
	private LoCategory cloneLoCategory(LoCategory loCategory, LoCategoryInfo loCategoryInfo) throws DoesNotExistException, InvalidParameterException, OperationFailedException {
    	LoCategoryType catType = null;
    	
    	try {
        	catType = loDao.fetch(LoCategoryType.class, loCategoryInfo.getType());
    	} catch (DoesNotExistException dnee) {
    		throw new DoesNotExistException("Attempt to set LoCategory's type to nonexistent LoCategoryType", dnee);
    	}
        	
    	// clone the existing LO
    	LoCategoryInfo newLoCategoryInfo = LearningObjectiveServiceAssembler.toLoCategoryInfo(loCategory);
    	newLoCategoryInfo.setType(catType.getId());
    	newLoCategoryInfo.setName(loCategoryInfo.getName());
    	LoCategory newLoCategory = loDao.create(LearningObjectiveServiceAssembler.toLoCategory(newLoCategoryInfo, loDao));
        	
    	// clone Lo-LoCategory relations
    	List<Lo> catsLos = loDao.getLosByLoCategory(loCategory.getId());         	
    	for (Lo lo : catsLos) {
    		try {
    			// create the new one
				loDao.addLoCategoryToLo(newLoCategory.getId(), lo.getId());
				// remove the old one
				loDao.removeLoCategoryFromLo(loCategory.getId(), lo.getId());
			} catch (UnsupportedActionException uae) {
				throw new OperationFailedException(uae.getMessage(), uae);
			}
    	}
        	
    	// inactivate old LoCategory
    	loCategory.setState("inactive");
    	loDao.update(loCategory);
        	
    	return newLoCategory;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#validateLo(java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateLo(String validationType,
			LoInfo loInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(validationType, "validationType");
	    checkForMissingParameter(loInfo, "loInfo");
	    
	    try{
	    	String loDesc = loInfo.getDesc().getPlain(); 
	    	checkForEmptyString(loDesc, "loInfo.Desc");
	    } catch (NullPointerException e){
			//do not checkForEmptyString
		}
	    
	    ObjectStructureDefinition objStructure = this.getObjectStructure(LoInfo.class.getName());
	    Validator validator = validatorFactory.getValidator();
	    return validator.validateObject(loInfo, objStructure);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#validateLoCategory(java.lang.String, org.kuali.student.lum.lo.dto.LoCategoryInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateLoCategory(String validationType,
			LoCategoryInfo loCategoryInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(validationType, "validationType");
	    checkForMissingParameter(loCategoryInfo, "loCategoryInfo");
	    
	    try{
	    	String catDesc = loCategoryInfo.getDesc().getPlain(); 
	    	checkForEmptyString(catDesc, "loCategoryInfo.Desc");
	    } catch (NullPointerException e){
			//do not checkForEmptyString
		}

        ObjectStructureDefinition objStructure = this.getObjectStructure(LoCategoryInfo.class.getName());
        Validator validator = validatorFactory.getValidator();
        return validator.validateObject(loCategoryInfo, objStructure);

	}

	@Override
	public List<ValidationResultInfo> validateLoLoRelation(
			String validationType, LoLoRelationInfo loLoRelationInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(LoLoRelationInfo.class.getName());
        Validator validator = validatorFactory.getValidator();
        return validator.validateObject(loLoRelationInfo, objStructure);
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
        if (param != null && param instanceof List && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }

    /**
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForEmptyString(String param, String paramName)
            throws MissingParameterException {
        if (param != null && "".equals(param.trim())) {
            throw new MissingParameterException(paramName + " can not be empty");
        }
    }
    
    /**
     * @param loRepositoryKey
     * @param loCategoryInfo
     * @param loCategoryId
     * @throws MissingParameterException,OperationFailedException
     */
    private boolean doesLoCategoryExist(String loRepositoryKey, LoCategoryInfo loCategoryInfo, String loCategoryId)
            throws MissingParameterException, DataValidationErrorException {

        boolean exists = false;
	    SearchRequest request = new SearchRequest();
	    request.setSearchKey("lo.search.loCategoriesByNameRepoTypeState");
	    
 		List<SearchParam> searchParams = new ArrayList<SearchParam>();
		SearchParam qpv1 = new SearchParam();
		qpv1.setKey("lo.queryParam.loCategoryName");
		qpv1.setValue(loCategoryInfo.getName().toLowerCase());
		searchParams.add(qpv1);
		SearchParam qpv2 = new SearchParam();
		qpv2.setKey("lo.queryParam.loCategoryRepo");
		qpv2.setValue(loRepositoryKey);
		searchParams.add(qpv2);
		SearchParam qpv3 = new SearchParam();
		qpv3.setKey("lo.queryParam.loCategoryType");
		qpv3.setValue(loCategoryInfo.getType());
		searchParams.add(qpv3);
		SearchParam qpv4 = new SearchParam();
		qpv4.setKey("lo.queryParam.loCategoryState");
		qpv4.setValue(loCategoryInfo.getState());
		searchParams.add(qpv4);
		
		request.setParams(searchParams);
		
		SearchResult result = search(request);
		
		if(loCategoryId != null && !loCategoryId.trim().equals("")){
			if (result.getRows().size() > 0) {
				for(SearchResultRow srrow : result.getRows()){
					List<SearchResultCell> srCells = srrow.getCells();
					if(srCells != null && srCells.size() > 0){
						for(SearchResultCell srcell : srCells){
							if(!srcell.getValue().equals(loCategoryId)) {
                                exists = true;
                            }
						}
					}
				}
			}
		}
		else{
			if (result.getRows().size() > 0) {
                exists = true;
			}
		}
        return exists;
    }
    
    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes() {
        return dictionaryServiceDelegate.getObjectTypes();
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaType(java.lang.String)
	 */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaTypes()
	 */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
    throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchResultType(java.lang.String)
	 */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchResultTypes()
	 */
    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
    throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchType(java.lang.String)
	 */
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchTypes()
	 */
    @Override
    public List<SearchTypeInfo> getSearchTypes()
    throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
	 */
    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByResult(java.lang.String)
	 */
    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(
            String searchResultTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey);
    }

	@Override
	public LoLoRelationInfo createLoLoRelation(String loId, String relatedLoId,
			String loLoRelationType, LoLoRelationInfo loLoRelationInfo)
			throws AlreadyExistsException, 
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(relatedLoId, "relatedLoId");
	    checkForMissingParameter(loLoRelationType, "loLoRelationType");
	    checkForMissingParameter(loLoRelationInfo, "loLoRelationInfo");
	    
		// Validate LoLoRelation
		List<ValidationResultInfo> val = validateLoLoRelation("SYSTEM", loLoRelationInfo);
		if(null != val && val.size() > 0) {
			for (ValidationResultInfo result : val) {
				System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
			}
			throw new DataValidationErrorException("Validation error!");
		}
	    
	    if (null == loLoRelationInfo.getState()) {
	    	loLoRelationInfo.setState("draft"); // TODO - enum of allowed states? retrieve allowed states from dictionary?
	    }
	    Lo lo = loDao.fetch(Lo.class, loId);
	    Lo relatedLo = loDao.fetch(Lo.class, relatedLoId);
	    LoLoRelationType type = loDao.fetch(LoLoRelationType.class, loLoRelationType);
	    loLoRelationInfo.setLoId(loId);
	    loLoRelationInfo.setRelatedLoId(relatedLoId);
	    loLoRelationInfo.setType(loLoRelationType);
	    
	    LoLoRelation relation = null;
	    try {
		    relation = LearningObjectiveServiceAssembler.toLoLoRelation(false, loLoRelationInfo, loDao);
	    } catch (VersionMismatchException vme) {
	    	// should never happen in a create call, but
	    	throw new OperationFailedException("VersionMismatchException caught during LoLoRelation creation");
	    }
	    relation.setLo(lo);
	    relation.setRelatedLo(relatedLo);
	    relation.setLoLoRelationType(type);
	    
	    relation = loDao.create(relation);
	    
		return LearningObjectiveServiceAssembler.toLoLoRelationInfo(relation);
	}

	@Override
	public StatusInfo deleteLoLoRelation(String loLoRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    checkForMissingParameter(loLoRelationId, "loLoRelationId");
	    
	    loDao.deleteLoLoRelation(loLoRelationId);
	    
		return new StatusInfo();
	}

	@Override
	public LoLoRelationInfo getLoLoRelation(String loLoRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loLoRelationId, "loLoRelationId");
		return LearningObjectiveServiceAssembler.toLoLoRelationInfo(loDao.fetch(LoLoRelation.class, loLoRelationId));
	}

	@Override
	public List<LoLoRelationInfo> getLoLoRelationsByLoId(String loId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<LoLoRelation> llRelations = loDao.getLoLoRelationsByLoId(loId);
		return LearningObjectiveServiceAssembler.toLoLoRelationInfos(llRelations);
	}

	@Override
	public List<LoInfo> getLosByRelatedLoId(String relatedLoId,
			String loLoRelationType) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		List<Lo> relatedLos = loDao.getLosByRelatedLoId(relatedLoId, loLoRelationType);
		return LearningObjectiveServiceAssembler.toLoInfos(relatedLos);
	}

	@Override
	public List<LoInfo> getRelatedLosByLoId(String loId, String loLoRelationTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(loLoRelationTypeKey, "loLoRelationTypeKey");
	    List<Lo> relatedLos = loDao.getRelatedLosByLoId(loId, loLoRelationTypeKey);
		return LearningObjectiveServiceAssembler.toLoInfos(relatedLos);
	}

	@Override
	public LoLoRelationInfo updateLoLoRelation(String loLoRelationId,
			LoLoRelationInfo loLoRelationInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		
		// TODO - this is never called; why does it exist?
		/*
		// Validate LoLoRelation
		List<ValidationResultInfo> val = validateLoLoRelation("SYSTEM", loLoRelationInfo);
		if(null != val && val.size() > 0) {
			throw new DataValidationErrorException("Validation error!");
		}
		*/
	    
		return null;
	}

	@Override
	public LoCategoryInfo createLoCategory(String loRepositoryKey,
			String loCategoryTypeKey, LoCategoryInfo loCategoryInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
	    checkForMissingParameter(loCategoryTypeKey, "loCategoryTypeKey");
	    checkForMissingParameter(loCategoryInfo, "loCategoryInfo");
	    
		// Validate LoCategory
		List<ValidationResultInfo> val = validateLoCategory("SYSTEM", loCategoryInfo);

        //kslum-136 - don't allow dups w/ same name (case insensitive), type, state & repository       
        if (doesLoCategoryExist(loRepositoryKey, loCategoryInfo, null)) {
            ValidationResultInfo vr = new ValidationResultInfo();
            vr.setElement("LO Category Name");
            vr.setError("LO Category already exists");
            val.add(vr);
        }
        if(null != val && val.size() > 0) {
			for (ValidationResultInfo result : val) {
				System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
			}
			throw new DataValidationErrorException("Validation error!", val);
		}

	    LoCategory category = LearningObjectiveServiceAssembler.toLoCategory(loCategoryInfo, loDao);
	    LoCategoryType loCatType = loDao.fetch(LoCategoryType.class, loCategoryTypeKey);
	    category.setLoCategoryType(loCatType);
	    LoRepository loRepository = loDao.fetch(LoRepository.class, loRepositoryKey);
	    category.setLoRepository(loRepository);
	    loDao.create(category);
		return LearningObjectiveServiceAssembler.toLoCategoryInfo(category);
	}

	@Override
	public LoCategoryTypeInfo getLoCategoryType(String loCategoryTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loCategoryTypeKey, "loCategoryTypeKey");
	    LoCategoryType loCatType = loDao.fetch(LoCategoryType.class, loCategoryTypeKey);
	    return LearningObjectiveServiceAssembler.toLoCategoryTypeInfo(loCatType);
	}

	@Override
	public List<LoCategoryTypeInfo> getLoCategoryTypes()
			throws OperationFailedException {
		List<LoCategoryType> categoryTypes = loDao.find(LoCategoryType.class);
		return LearningObjectiveServiceAssembler.toLoCategoryTypeInfos(categoryTypes);
	}

	@Override
	public List<LoInfo> getLosByRepository(String loRepositoryKey,
			String loTypeKey, String loStateKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
	    List<Lo> los = loDao.getLosByRepository(loRepositoryKey);
		return LearningObjectiveServiceAssembler.toLoInfos(los);
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        return searchManager.search(searchRequest, loDao);
	}

}
