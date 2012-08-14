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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.lum.lo.dao.LoDao;
import org.kuali.student.r2.lum.lo.entity.Lo;
import org.kuali.student.r2.lum.lo.entity.LoCategory;
import org.kuali.student.r2.lum.lo.entity.LoCategoryType;
import org.kuali.student.r2.lum.lo.entity.LoLoRelation;
import org.kuali.student.r2.lum.lo.entity.LoLoRelationType;
import org.kuali.student.r2.lum.lo.entity.LoRepository;
import org.kuali.student.r2.lum.lo.entity.LoType;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.service.SearchManager;
import org.kuali.student.r1.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.r1.lum.lo.dto.LoLoRelationTypeInfo;
import org.kuali.student.r1.lum.lo.dto.LoTypeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.springframework.transaction.annotation.Transactional;

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
	public List<LoRepositoryInfo> getLoRepositories(@WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
	    List<LoRepository> repositories = loDao.find(LoRepository.class);
  	
	   return LearningObjectiveServiceAssembler.toLoRepositoryInfos(repositories);
	}


	@Override
    public List<String> getLoRepositoryKeysByType(@WebParam(name = "loRepositoryTypeKey") String loRepositoryTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForLoRepositoryKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoRepositoryInfo> searchForLoRepositories(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateLoRepository(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "loRepositoryTypeKey") String loRepositoryTypeKey, @WebParam(name = "loRepositoryInfo") LoRepositoryInfo loRepositoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LoRepositoryInfo createLoRepository(@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "loRepositoryTypeKey") String loRepositoryTypeKey, @WebParam(name = "loRepositoryInfo") LoRepositoryInfo loRepositoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LoRepositoryInfo updateLoRepository(@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "loRepositoryInfo") LoRepositoryInfo loRepositoryInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteLoRepository(@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<LoRepositoryInfo> getLoRepositoriesByKeys(@WebParam(name = "loRepositoryKeys") List<String> loRepositoryKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
      * (non-Javadoc)
      * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoTypes()
      */
	@Override
    @Transactional(readOnly=true)
	public List<LoTypeInfo> getLoTypes(ContextInfo contextInfo) throws OperationFailedException {
		List<LoType> find = loDao.find(LoType.class);
		return LearningObjectiveServiceAssembler.toLoTypeInfos(find);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoType(java.lang.String)
	 */
	@Override
    @Transactional(readOnly=true)
	public LoTypeInfo getLoType(String loTypeKey,ContextInfo contextInfo) throws DoesNotExistException,
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
    @Transactional(readOnly=true)
	public List<LoLoRelationTypeInfo> getLoLoRelationTypes(ContextInfo contextInfo)
			throws OperationFailedException {
	    List<LoLoRelationType> fetch = loDao.find(LoLoRelationType.class);
		return LearningObjectiveServiceAssembler.toLoLoRelationTypeInfos(fetch);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoLoRelationType(java.lang.String)
	 */
	@Override
    @Transactional(readOnly=true)
	public LoLoRelationTypeInfo getLoLoRelationType(String loLoRelationTypeKey,ContextInfo contextInfo)
			throws OperationFailedException, MissingParameterException, DoesNotExistException {
	    checkForMissingParameter(loLoRelationTypeKey, "loLoRelationTypeKey");
		return LearningObjectiveServiceAssembler.toLoLoRelationTypeInfo(loDao.fetch(LoLoRelationType.class, loLoRelationTypeKey));
	}

	@Override
    @Transactional(readOnly=true)
	public List<String> getAllowedLoLoRelationTypesForLoType(String loTypeKey, String relatedLoTypeKey,ContextInfo contextInfo)
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
    public List<LoLoRelationInfo> getLoLoRelationsByIds(@WebParam(name = "loLoRelationIds") List<String> loLoRelationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getLoLoRelationIdsByType(@WebParam(name = "loLoRelationTypeKey") String loLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForLoLoRelationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoLoRelationInfo> searchForLoLoRelations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public StatusInfo deleteLoCategoryByLo(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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

	// TODO replaced implementation to old method
    @Override
    public List<LoInfo> getLosByIds(@WebParam(name = "loIds") List<String> loIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getLoByIdList(loIds, contextInfo);
    }

    @Override
    public List<String> getLoIdsByType(@WebParam(name = "loTypeKey") String loTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    // TODO replaced implementation to old method
    @Override
    public List<LoInfo> getLosByLoRepository(@WebParam(name = "loRepositoryKey") String loRepositoryKey, @WebParam(name = "loTypeKey") String loTypeKey, @WebParam(name = "loStateKey") String loStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getLosByRepository(loRepositoryKey, loTypeKey, loStateKey);
    }

    /* (non-Javadoc)
      * @see org.kuali.student.lum.lo.service.LearningObjectiveService#getLoByIdList(java.util.List)
      */
	@Override
    @Transactional(readOnly=true)
	public List<LoInfo> getLoByIdList(List<String> loIds,ContextInfo contextInfo)
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
    @Transactional(readOnly=true)
	public List<LoCategoryInfo> getLoCategories(String loRepositoryKey,ContextInfo contextInfo)
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
    @Transactional(readOnly=true)
	public List<LoCategoryInfo> getLoCategoriesForLo(String loId,ContextInfo contextInfo)
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
    @Transactional(readOnly=true)
	public LoCategoryInfo getLoCategory(String loCategoryId,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loCategoryId, "loCategoryId");
	    
 
	    return LearningObjectiveServiceAssembler.toLoCategoryInfo(loDao.fetch(LoCategory.class, loCategoryId)); 
	}

    @Override
    public List<LoCategoryInfo> getLoCategoriesByIds(@WebParam(name = "loCategoryIds") List<String> loCategoryIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getLoCategoryIdsByType(@WebParam(name = "loCategoryTypeKey") String loCategoryTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    // TODO replaced implementation to old method
    @Override
    public List<LoCategoryInfo> getLoCategoriesByLo(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getLoCategoriesForLo(loId, contextInfo);
    }

    @Override
    public List<String> searchForLoCategoryIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoCategoryInfo> searchForLoCategories(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<String> searchForLoIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<LoInfo> searchForLos(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
            throws MissingParameterException, DataValidationErrorException {
    if (loCategoryInfo.getName() == null)
    {
     return false;
    }
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
		qpv3.setValue(loCategoryInfo.getTypeKey());
		searchParams.add(qpv3);
		SearchParam qpv4 = new SearchParam();
		qpv4.setKey("lo.queryParam.loCategoryState");
		qpv4.setValue(loCategoryInfo.getStateKey());
		searchParams.add(qpv4);
		
		request.setParams(searchParams);
			
		SearchResult result = search(request );
		
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
    
    //@Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey,ContextInfo contextInfo) {
        return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
    }

//    @Override
//    public List<String> getObjectTypes() {
//        return dictionaryServiceDelegate.getObjectTypes();
//    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaType(java.lang.String)
	 */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {

        return searchManager.getSearchCriteriaType(searchCriteriaTypeKey);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaTypes()
	 */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
    throws OperationFailedException {
        return searchManager.getSearchCriteriaTypes();
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchResultType(java.lang.String)
	 */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchResultTypeKey, "searchResultTypeKey");
        return searchManager.getSearchResultType(searchResultTypeKey);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchResultTypes()
	 */
    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
    throws OperationFailedException {
        return searchManager.getSearchResultTypes();
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchType(java.lang.String)
	 */
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        checkForMissingParameter(searchTypeKey, "searchTypeKey");
        return searchManager.getSearchType(searchTypeKey);
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchTypes()
	 */
    @Override
    public List<SearchTypeInfo> getSearchTypes()
    throws OperationFailedException {
        return searchManager.getSearchTypes();
    }

	/* (non-Javadoc)
	 * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
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
	 * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByResult(java.lang.String)
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
    public LoLoRelationInfo getLoLoRelation(@WebParam(name = "loLoRelationId") String loLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    checkForMissingParameter(loLoRelationId, "loLoRelationId");
	 return LearningObjectiveServiceAssembler.toLoLoRelationInfo(loDao.fetch(LoLoRelation.class, loLoRelationId));
    }
	
	@Override
	@Transactional(readOnly=true)
    public List<LoLoRelationInfo> getLoLoRelationsByLoId(@WebParam(name = "loId") String loId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    List<LoLoRelation> llRelations = loDao.getLoLoRelationsByLoId(loId);
	    return LearningObjectiveServiceAssembler.toLoLoRelationInfos(llRelations);   
    }
	
	@Override
	@Transactional(readOnly=true)
    public List<LoInfo> getLosByRelatedLoId(@WebParam(name = "relatedLoId") String relatedLoId, @WebParam(name = "loLoRelationTypeKey") String loLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	    List<Lo> relatedLos = loDao.getLosByRelatedLoId(relatedLoId, loLoRelationTypeKey);
	    return LearningObjectiveServiceAssembler.toLoInfos(relatedLos);
    }
	
	@Override
	@Transactional(readOnly=true)
    public List<LoInfo> getRelatedLosByLoId(@WebParam(name = "loId") String loId, @WebParam(name = "loLoRelationTypeKey") String loLoRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public StatusInfo deleteLoLoRelation(@WebParam(name = "loLoRelationId") String loLoRelationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
	public LoCategoryTypeInfo getLoCategoryType(String loCategoryTypeKey,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    checkForMissingParameter(loCategoryTypeKey, "loCategoryTypeKey");
	    LoCategoryType loCatType = loDao.fetch(LoCategoryType.class, loCategoryTypeKey);
	    return LearningObjectiveServiceAssembler.toLoCategoryTypeInfo(loCatType);
	}

	@Override
    @Transactional(readOnly=true)
	public List<LoCategoryTypeInfo> getLoCategoryTypes()
			throws OperationFailedException {
		List<LoCategoryType> categoryTypes = loDao.find(LoCategoryType.class);
		return LearningObjectiveServiceAssembler.toLoCategoryTypeInfos(categoryTypes);
	}

	@Override
    @Transactional(readOnly=true)
	public List<LoInfo> getLosByRepository(String loRepositoryKey,
			String loTypeKey, String loStateKey)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    checkForMissingParameter(loRepositoryKey, "loRepositoryKey");
	    List<Lo> los = loDao.getLosByRepository(loRepositoryKey);
	    return LearningObjectiveServiceAssembler.toLoInfos(los);
	}

	@Override
	public List<LoCategoryInfo> getLoCategoriesByLoRepository(
			String loRepositoryKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest) throws MissingParameterException {
        checkForMissingParameter(searchRequest, "searchRequest");
        SearchResult result =  searchManager.search(searchRequest, loDao);
        if("lo.search.loByCategory".equals(searchRequest.getSearchKey())){
        	for(SearchParam param:searchRequest.getParams()){
        		if("lo.queryParam.groupCategories".equals(param.getKey())&&"true".equals(param.getValue())){
        	groupCategories(result);
        		}
        	}
        }
        
        return result;
	}

	//Updates search results grouping category names as a comma delimited list
	private void groupCategories(SearchResult result) {
		Map<String,SearchResultCell> idToCellMap = new HashMap<String,SearchResultCell>();
		for(Iterator<SearchResultRow> iter = result.getRows().iterator();iter.hasNext();){
			SearchResultRow row = iter.next();
			SearchResultCell categoryCell = null;
			String loId = null;
			//Get search result cell values
			for(SearchResultCell cell:row.getCells()){
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
					SearchResultCell cell = idToCellMap.get(loId);
					if(cell == null){
						cell = new SearchResultCell("lo.resultColumn.categoryName","");
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
