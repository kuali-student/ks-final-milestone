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

package org.kuali.student.r2.lum.lo.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.lo.dao.LoDao;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.r2.lum.lo.entity.*;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.*;

/*n
 * @author Kuali Student team
 *
 */
@WebService(endpointInterface = "org.kuali.student.r2.lum.lo.service.LearningObjectiveService", serviceName = "LearningObjectiveService", portName = "LearningObjectiveService", targetNamespace = "http://student.kuali.org/wsdl/lo")
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


	@Override
    @Transactional(readOnly=true)
	public List<LoRepositoryInfo> getLoRepositories( ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
	    List<LoRepository> repositories = loDao.find(LoRepository.class);
  	
	   return LearningObjectiveServiceAssembler.toLoRepositoryInfos(repositories);
	}


	@Override
    public List<String> getLoRepositoryKeysByType(String loRepositoryTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForLoRepositoryKeys(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoRepositoryInfo> searchForLoRepositories(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateLoRepository( String validationTypeKey,  String loRepositoryTypeKey,  LoRepositoryInfo loRepositoryInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LoRepositoryInfo createLoRepository( String loRepositoryKey,  String loRepositoryTypeKey,  LoRepositoryInfo loRepositoryInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LoRepositoryInfo updateLoRepository( String loRepositoryKey,  LoRepositoryInfo loRepositoryInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteLoRepository(String loRepositoryKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
      * (non-Javadoc)
      * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoRepository(java.lang.String)
      */
	@Override
    @Transactional(readOnly=true)
	public LoRepositoryInfo getLoRepository (String loRepositoryKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
	    return LearningObjectiveServiceAssembler.toLoRepositoryInfo(loDao.fetch(LoRepository.class, loRepositoryKey));
		
	}

    @Override
    public List<LoRepositoryInfo> getLoRepositoriesByKeys(List<String> loRepositoryKeys,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#addLoCategoryToLo(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo  addLoCategoryToLo( String loCategoryId, String loId,  ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException
    {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    checkForMissingParameter(loId, "loId");
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setSuccess(loDao.addLoCategoryToLo(loCategoryId, loId));
        return statusInfo;
	}

    @Override
    public List<LoLoRelationInfo> getLoLoRelationsByIds(List<String> loLoRelationIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getLoLoRelationIdsByType(String loLoRelationTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForLoLoRelationIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoLoRelationInfo> searchForLoLoRelations(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lo.service.LearningObjectiveService#createLo(java.lang.String, java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
      */
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public LoInfo createLo (String repositoryId, String loType, LoInfo loInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
 
        checkForMissingParameter(repositoryId, "repositoryId");
	    checkForMissingParameter(loType, "loType");
	    checkForMissingParameter(loInfo, "loInfo");
	    
	    
		// Validate LO
		List<ValidationResultInfo> val = validateLo("SYSTEM", loInfo,contextInfo);
		if(null != val && val.size() > 0) {
			for (ValidationResultInfo result : val) {
				System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
			}
            
			throw new DataValidationErrorException("Validation error!", val);
			
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
	   loInfo.setTypeKey(loType);

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
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteLo ( String loId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException ,DependentObjectsExistException{
	    checkForMissingParameter(loId, "loId");
	    
	    StatusInfo returnStatus = new StatusInfo();
	    returnStatus.setSuccess(loDao.deleteLo(loId));
		return returnStatus;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#deleteLoCategory(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteLoCategory(String loCategoryId,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    
	    loDao.deleteLoCategory(loCategoryId);
	    
		return new StatusInfo();
	}

    @Override
    public StatusInfo deleteLoCategoryByLo(String loId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLo(java.lang.String)
      */
	@Override
    @Transactional(readOnly=true)
	public LoInfo getLo(String loId,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(loId, "loId");
	    return LearningObjectiveServiceAssembler.toLoInfo(loDao.fetch(Lo.class, loId));
		
	}

	@Override
    @Transactional(readOnly=true)
    public List<LoInfo> getLosByIds(List<String> loIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(loIds, "loId");
        checkForEmptyList(loIds, "loId");
        List<Lo> los = loDao.getLoByIdList(loIds);
        return LearningObjectiveServiceAssembler.toLoInfos(los);
    }

    @Override
    public List<String> getLoIdsByType(String loTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly=true)
    public List<LoInfo> getLosByLoRepository( String loRepositoryKey,  String loTypeKey, String loStateKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
        List<Lo> los = loDao.getLosByRepository(loRepositoryKey);
        return LearningObjectiveServiceAssembler.toLoInfos(los);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoCategory(java.lang.String)
	 */
	@Override
    @Transactional(readOnly=true)
	public LoCategoryInfo getLoCategory(String loCategoryId,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    
 
	    return LearningObjectiveServiceAssembler.toLoCategoryInfo(loDao.fetch(LoCategory.class, loCategoryId)); 
	}

    @Override
    public List<LoCategoryInfo> getLoCategoriesByIds(List<String> loCategoryIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getLoCategoryIdsByType(String loCategoryTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly=true)
    public List<LoCategoryInfo> getLoCategoriesByLo(String loId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(loId, "loId");
        List<LoCategory> categories = loDao.getLoCategoriesForLo(loId);
        return LearningObjectiveServiceAssembler.toLoCategoryInfos(categories);
    }

    @Override
    public List<String> searchForLoCategoryIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoCategoryInfo> searchForLoCategories(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
    @Transactional(readOnly=true)
	public List<LoInfo> getLosByLoCategory(String loCategoryId,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    List<Lo> los = loDao.getLosByLoCategory(loCategoryId);
	    return LearningObjectiveServiceAssembler.toLoInfos(los);
	}

    @Override
    public List<String> searchForLoIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoInfo> searchForLos(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo removeLoCategoryFromLo(String loCategoryId, String loId,ContextInfo contextInfo)
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
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public LoInfo updateLo(String loId, LoInfo loInfo,ContextInfo contextInfo)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
	    checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(loInfo, "loInfo");

		// Validate LO
		List<ValidationResultInfo> val = validateLo("SYSTEM", loInfo,contextInfo);
		if(null != val && val.size() > 0) {
			for (ValidationResultInfo result : val) {
				System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
			}
			 
			throw new DataValidationErrorException("Validation error!", val);
			
		}
		
	    Lo lo = loDao.fetch(Lo.class, loId);
        
        if (!String.valueOf(lo.getVersionNumber()).equals(loInfo.getMeta().getVersionInd())){
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
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public LoCategoryInfo updateLoCategory(String loCategoryId,
			LoCategoryInfo loCategoryInfo,ContextInfo contextInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    checkForMissingParameter(loCategoryInfo, "loCategoryInfo");
	    
		// Validate LoCategory
		List<ValidationResultInfo> val = validateLoCategory("SYSTEM", loCategoryInfo,contextInfo);

		//kslum-136 - don't allow dups w/ same name (case insensitive), type, state & repository
//TODO KSCM-448
        if (doesLoCategoryExist(loCategoryInfo.getLoRepositoryKey(), loCategoryInfo, loCategoryId,contextInfo)) {
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
        
        if (!String.valueOf(loCategory.getVersionNumber()).equals(loCategoryInfo.getMeta().getVersionInd())){
            throw new VersionMismatchException("LoCategory to be updated is not the current version");
        }
        
        // if state is changing from "active"
        if (loCategory.getState().equals("active") && ( ! loCategoryInfo.getStateKey().equals("active") )) {
    		// N.B. - ability to 'retire' LoCategory's that are still associated w/ active
    		// LO's is configured and enforced on the atpService
        	List<LoInfo> loInfos = getLosByLoCategory(loCategoryId,contextInfo);
    		if (null != loInfos) {
				// remove associations of this LoCategory from active LO's
    			for (LoInfo info : loInfos) {
    				if (info.getStateKey().equals("active"))  {
	    				try {
							removeLoCategoryFromLo(loCategoryId, info.getId(),contextInfo);
						} catch (UnsupportedActionException uaee) {
				    		throw new OperationFailedException("Unable to update LoCategory: could not remove association with active LearningObjective", uaee);
						}
    				}
    			}
    		}
        }
        	
        // if type is changing
        if ( ! loCategory.getLoCategoryType().getId().equals(loCategoryInfo.getTypeKey()) ) {
        	loCategory = cloneLoCategory(loCategory, loCategoryInfo,contextInfo);
        } else {
        	loCategory = LearningObjectiveServiceAssembler.toLoCategory(loCategory, loCategoryInfo, loDao);
	        loDao.update(loCategory);
        }
       return LearningObjectiveServiceAssembler.toLoCategoryInfo(loCategory);

	}

    // inactivate current LoCategory & clone it w/ its relationships,
	// used when changing immutable type of LoCategory
	// https://test.kuali.org/confluence/display/KULSTG/DS+-+LO+Centrally+Maintain+Categories
	private LoCategory cloneLoCategory(LoCategory loCategory, LoCategoryInfo loCategoryInfo,ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, OperationFailedException {
    	LoCategoryType catType = null;
    	
    	try {
        	catType = loDao.fetch(LoCategoryType.class, loCategoryInfo.getTypeKey());
    	} catch (DoesNotExistException dnee) {
    		throw new DoesNotExistException("Attempt to set LoCategory's type to nonexistent LoCategoryType", dnee);
    	}
        	
    	// clone the existing LO
    	LoCategoryInfo newLoCategoryInfo = LearningObjectiveServiceAssembler.toLoCategoryInfo(loCategory);
    	newLoCategoryInfo.setTypeKey(catType.getId());
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
        	
    	// suspend old LoCategory
    	loCategory.setState("Suspended");
    	loDao.update(loCategory);
        	
    	return newLoCategory;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#validateLo(java.lang.String, org.kuali.student.lum.lo.dto.LoInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateLo(String validationType,
			LoInfo loInfo,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(validationType, "validationType");
	    checkForMissingParameter(loInfo, "loInfo");

     // this is the job of the validator not some hard coded value
//	    try{
//	    	String loDesc = loInfo.getDescr().getPlain();
//	    	checkForEmptyString(loDesc, "loInfo.Desc");
//	    } catch (NullPointerException e){
//			//do not checkForEmptyString
//		}
	    
	   ObjectStructureDefinition objStructure = this.getObjectStructure(LoInfo.class.getName(),contextInfo);
	    Validator validator = validatorFactory.getValidator();
	    return validator.validateObject(loInfo, objStructure,contextInfo);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#validateLoCategory(java.lang.String, org.kuali.student.lum.lo.dto.LoCategoryInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateLoCategory(String validationType,
			LoCategoryInfo loCategoryInfo,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(validationType, "validationType");
	    checkForMissingParameter(loCategoryInfo, "loCategoryInfo");

     // this is the job of the validator not some hard coded logic
//	    try{
//	    	String catDesc = loCategoryInfo.getDesc().getPlain();
//	    	checkForEmptyString(catDesc, "loCategoryInfo.Desc");
//	    } catch (NullPointerException e){
//			//do not checkForEmptyString
//		}

	   ObjectStructureDefinition objStructure = this.getObjectStructure(LoCategoryInfo.class.getName(),contextInfo);
        Validator validator = validatorFactory.getValidator();
        return validator.validateObject(loCategoryInfo, objStructure,contextInfo);
	}

	@Override
	public List<ValidationResultInfo> validateLoLoRelation(
			String validationType, LoLoRelationInfo loLoRelationInfo,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {

		ObjectStructureDefinition objStructure = this.getObjectStructure(LoLoRelationInfo.class.getName(),contextInfo);
        Validator validator = validatorFactory.getValidator();
        return validator.validateObject(loLoRelationInfo, objStructure,contextInfo);
	}

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
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

    // this is the job of the validator not some hard coded logic
    // besides it should create a validation result not a missing parameter exeception
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
    private boolean doesLoCategoryExist(String loRepositoryKey, LoCategoryInfo loCategoryInfo, String loCategoryId,ContextInfo contextInfo)
            throws MissingParameterException, DataValidationErrorException, PermissionDeniedException, OperationFailedException {
    if (loCategoryInfo.getName() == null)
    {
     return false;
    }
        boolean exists = false;
	    SearchRequestInfo request = new SearchRequestInfo();
	    request.setSearchKey("lo.search.loCategoriesByNameRepoTypeState");
	    
 		List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
		SearchParamInfo qpv1 = new SearchParamInfo();
		qpv1.setKey("lo.queryParam.loCategoryName");
		qpv1.getValues().add(loCategoryInfo.getName().toLowerCase());
		searchParams.add(qpv1);
		SearchParamInfo qpv2 = new SearchParamInfo();
		qpv2.setKey("lo.queryParam.loCategoryRepo");
		qpv2.getValues().add(loRepositoryKey);
		searchParams.add(qpv2);
		SearchParamInfo qpv3 = new SearchParamInfo();
		qpv3.setKey("lo.queryParam.loCategoryType");
		qpv3.getValues().add(loCategoryInfo.getTypeKey());
		searchParams.add(qpv3);
		SearchParamInfo qpv4 = new SearchParamInfo();
		qpv4.setKey("lo.queryParam.loCategoryState");
		qpv4.getValues().add(loCategoryInfo.getStateKey());
		searchParams.add(qpv4);
		
		request.setParams(searchParams);
			
		SearchResultInfo result = search(request, contextInfo);
		
		if(loCategoryId != null && !loCategoryId.trim().equals("")){
			if (result.getRows().size() > 0) {
				for(SearchResultRowInfo srrow : result.getRows()){
					List<SearchResultCellInfo> srCells = srrow.getCells();
					if(srCells != null && srCells.size() > 0){
						for(SearchResultCellInfo srcell : srCells){
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
    
    //@Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey,ContextInfo contextInfo) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

//    @Override
//    public List<String> getObjectTypes() {
//        return dictionaryServiceDelegate.getObjectTypes();
//    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaTypes()
	 */
    @Override
    public List<TypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchCriteriaTypes(contextInfo);
    }



    /* (non-Javadoc)
      * @see org.kuali.student.common.search.service.SearchService#getSearchResultTypes()
      */
    @Override
    public List<TypeInfo> getSearchResultTypes(ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchResultTypes(contextInfo);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchType(java.lang.String)
	 */
    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(String searchResultTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

    /* (non-Javadoc)
      * @see org.kuali.student.common.search.service.SearchService#getSearchTypes()
      */
    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return searchManager.getSearchTypes(contextInfo);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
	 */
    @Override
    public List<TypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        checkForMissingParameter(searchCriteriaTypeKey, "searchCriteriaTypeKey");
        return searchManager.getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public LoLoRelationInfo  createLoLoRelation ( String loLoRelationTypeKey,  LoLoRelationInfo loLoRelationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
		checkForMissingParameter(loLoRelationInfo, "loLoRelationInfo");
		checkForMissingParameter(loLoRelationTypeKey, "loLoRelationTypeKey");
		checkForMissingParameter(loLoRelationInfo.getLoId(), "loId");
	    checkForMissingParameter(loLoRelationInfo.getRelatedLoId(), "relatedLoId");
	    checkForMissingParameter(loLoRelationInfo.getTypeKey(), "loLoRelationType");
	   
		// Validate LoLoRelation
		List<ValidationResultInfo> val = validateLoLoRelation("SYSTEM", loLoRelationInfo,contextInfo);
		if(null != val && val.size() > 0) {
			for (ValidationResultInfo result : val) {
				System.err.println("Validation error. Element: " + result.getElement() + ",  Value: " + result.getMessage());
			}
		
			throw new DataValidationErrorException("Validation error!");
		}
	    
	    if (null == loLoRelationInfo.getStateKey()) {
	    	loLoRelationInfo.setStateKey(DtoConstants.STATE_DRAFT);
	    }
	    Lo lo = loDao.fetch(Lo.class, loLoRelationInfo.getLoId());
	    Lo relatedLo = loDao.fetch(Lo.class, loLoRelationInfo.getRelatedLoId());
	    LoLoRelationType type = loDao.fetch(LoLoRelationType.class, loLoRelationTypeKey);
	    loLoRelationInfo.setLoId(loLoRelationInfo.getLoId());
	    loLoRelationInfo.setRelatedLoId(loLoRelationInfo.getRelatedLoId());
        loLoRelationInfo.setTypeKey(loLoRelationInfo.getTypeKey());
	    
	    LoLoRelation relation = null;

	    try {
	    	relation = LearningObjectiveServiceAssembler.toLoLoRelation(false,  loLoRelationInfo, loDao);
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
	@Transactional(readOnly=true)
    public LoLoRelationInfo getLoLoRelation(String loLoRelationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loLoRelationId, "loLoRelationId");
	 return LearningObjectiveServiceAssembler.toLoLoRelationInfo(loDao.fetch(LoLoRelation.class, loLoRelationId));
    }
	
	@Override
	@Transactional(readOnly=true)
    public List<LoLoRelationInfo> getLoLoRelationsByLoId(String loId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    List<LoLoRelation> llRelations = loDao.getLoLoRelationsByLoId(loId);
	    return LearningObjectiveServiceAssembler.toLoLoRelationInfos(llRelations);   
    }
	
	@Override
	@Transactional(readOnly=true)
    public List<LoInfo> getLosByRelatedLoId(String relatedLoId,  String loLoRelationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    List<Lo> relatedLos = loDao.getLosByRelatedLoId(relatedLoId, loLoRelationTypeKey);
	    return LearningObjectiveServiceAssembler.toLoInfos(relatedLos);
    }
	
	@Override
	@Transactional(readOnly=true)
    public List<LoInfo> getRelatedLosByLoId(String loId,  String loLoRelationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		checkForMissingParameter(loId, "loId");
	    checkForMissingParameter(loLoRelationTypeKey, "loLoRelationTypeKey");
		List<Lo> relatedLos = loDao.getRelatedLosByLoId(loId, loLoRelationTypeKey);
	    return LearningObjectiveServiceAssembler.toLoInfos(relatedLos);
    }

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public LoLoRelationInfo updateLoLoRelation ( String loLoRelationId,  LoLoRelationInfo loLoRelationInfo,  ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
		

		// Validate LoLoRelation
		List<ValidationResultInfo> val = validateLoLoRelation("SYSTEM", loLoRelationInfo,contextInfo );
		if(null != val && val.size() > 0) {

			throw new DataValidationErrorException("Validation error!", val);

		}

	    
		return null;
	}

    @Override
    public StatusInfo deleteLoLoRelation(String loLoRelationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(loLoRelationId, "loLoRelationId");
        
        loDao.deleteLoLoRelation(loLoRelationId);
        
        return new StatusInfo();
    }

    @Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public LoCategoryInfo createLoCategory (String loCategoryTypeKey,  LoCategoryInfo loCategoryInfo,  ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
	    checkForMissingParameter(loCategoryInfo.getLoRepositoryKey(), "loRepositoryKey");
	    checkForMissingParameter(loCategoryTypeKey, "loCategoryTypeKey");
	    checkForMissingParameter(loCategoryInfo, "loCategoryInfo");
	    
		// Validate LoCategory
		List<ValidationResultInfo> val = validateLoCategory("SYSTEM", loCategoryInfo,contextInfo);

        //kslum-136 - don't allow dups w/ same name (case insensitive), type, state & repository
        if (doesLoCategoryExist(loCategoryInfo.getLoRepositoryKey(), loCategoryInfo, null,contextInfo)) {
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
        
	    LoCategory category = LearningObjectiveServiceAssembler.toLoCategory(loCategoryInfo,loDao);
	    LoCategoryType loCatType = loDao.fetch(LoCategoryType.class, loCategoryTypeKey);
	    category.setLoCategoryType(loCatType);
	    LoRepository loRepository = loDao.fetch(LoRepository.class, loCategoryInfo.getLoRepositoryKey() );
	    category.setLoRepository(loRepository);
	    loDao.create(category);
		return LearningObjectiveServiceAssembler.toLoCategoryInfo(category);
	}

    @Override
    @Transactional(readOnly=true)
    public TypeInfo getLoCategoryType(String loCategoryTypeKey,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        checkForMissingParameter(loCategoryTypeKey, "loCategoryTypeKey");
        LoCategoryType loCatType = loDao.fetch(LoCategoryType.class, loCategoryTypeKey);
        return LearningObjectiveServiceAssembler.toGenericTypeInfo(loCatType);
    }

    @Override
    @Transactional(readOnly=true)
    public List<TypeInfo> getLoCategoryTypes()
            throws OperationFailedException {
        List<LoCategoryType> categoryTypes = loDao.find(LoCategoryType.class);
        return LearningObjectiveServiceAssembler.toGenericTypeInfoList(categoryTypes);
    }

	@Override
    @Transactional(readOnly=true)
	public List<LoCategoryInfo> getLoCategoriesByLoRepository(
			String loRepositoryKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
        List<LoCategory> categories = loDao.getLoCategories(loRepositoryKey);
        return LearningObjectiveServiceAssembler.toLoCategoryInfos(categories);
	}

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo,  ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(searchRequestInfo, "searchRequest");
        SearchResultInfo result =  searchManager.search(searchRequestInfo, contextInfo);
        if("lo.search.loByCategory".equals(searchRequestInfo.getSearchKey())){
        	for(SearchParamInfo param:searchRequestInfo.getParams()){
        		if("lo.queryParam.groupCategories".equals(param.getKey())&&"true".equals(param.getValues().get(0))){
        	groupCategories(result);
        		}
        	}
        }
        
        return result;
	}

	//Updates search results grouping category names as a comma delimited list
	private void groupCategories(SearchResultInfo result) {
		Map<String,SearchResultCellInfo> idToCellMap = new HashMap<String,SearchResultCellInfo>();
		for(Iterator<SearchResultRowInfo> iter = result.getRows().iterator();iter.hasNext();){
			SearchResultRowInfo row = iter.next();
			SearchResultCellInfo categoryCell = null;
			String loId = null;
			//Get search result cell values
			for(SearchResultCellInfo cell:row.getCells()){
				if("lo.resultColumn.categoryName".equals(cell.getKey())){
					categoryCell = cell;
					break;
				}else if("lo.resultColumn.loId".equals(cell.getKey())){
					loId = cell.getValue();
				}
			}
			//If a row exists with the same loId, append the category to the existing row and remove the current row.
			if(loId!=null){
				if(idToCellMap.containsKey(loId)){
					SearchResultCellInfo cell = idToCellMap.get(loId);
					if(cell == null){
						cell = new SearchResultCellInfo("lo.resultColumn.categoryName","");
						idToCellMap.put(loId, cell);
					}
					if(categoryCell!=null){
						if(cell.getValue()==null||cell.getValue().isEmpty()){
							cell.setValue(categoryCell.getValue());
						}else if(categoryCell.getValue()!=null && !categoryCell.getValue().isEmpty()){
							cell.setValue(cell.getValue()+", "+categoryCell.getValue());
						}
					}
					//Remove this row since we alreay have a mapping to a row with this lo Id
					iter.remove();
				} else {
					//Otherwise add a mapping and continue
					idToCellMap.put(loId, categoryCell);
				}
			}
		}
	}
	
}
