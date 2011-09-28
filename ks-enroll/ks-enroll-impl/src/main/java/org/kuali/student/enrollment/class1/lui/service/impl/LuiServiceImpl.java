
package org.kuali.student.enrollment.class1.lui.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class1.lui.dao.LuiDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiRichTextDao;
import org.kuali.student.enrollment.class1.lui.dao.LuiTypeDao;
import org.kuali.student.enrollment.class1.lui.model.LuCodeEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiLuiRelationEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiRichTextEntity;
import org.kuali.student.enrollment.class1.lui.model.LuiTypeEntity;
import org.kuali.student.enrollment.class1.lui.model.MeetingScheduleEntity;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dao.TypeTypeRelationDao;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.entity.TypeEntity;
import org.kuali.student.r2.common.entity.TypeTypeRelationEntity;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.model.StateEntity;
import org.kuali.student.r2.common.service.StateService;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.lu.service.LuService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class LuiServiceImpl implements LuiService {
	private LuiDao luiDao;
	private LuiRichTextDao luiRichTextDao;
	private LuiTypeDao luiTypeDao;
	private LuiLuiRelationDao luiLuiRelationDao;
	private StateService stateService;
	private TypeTypeRelationDao typeTypeRelationDao;
	private AtpService atpService;
	private LuService luService;
	
	public LuiDao getLuiDao() {
		return luiDao;
	}	
	public void setLuiDao(LuiDao luiDao) {
		this.luiDao = luiDao;
	}
	
	public LuiRichTextDao getLuiRichTextDao() {
		return luiRichTextDao;
	}
	public void setLuiRichTextDao(LuiRichTextDao luiRichTextDao) {
		this.luiRichTextDao = luiRichTextDao;
	}
	
	public LuiTypeDao getLuiTypeDao() {
		return luiTypeDao;
	}
	public void setLuiTypeDao(LuiTypeDao luiTypeDao) {
		this.luiTypeDao = luiTypeDao;
	}
	
	public LuiLuiRelationDao getLuiLuiRelationDao() {
		return luiLuiRelationDao;
	}
	public void setLuiLuiRelationDao(LuiLuiRelationDao luiLuiRelationDao) {
		this.luiLuiRelationDao = luiLuiRelationDao;
	}
	
	public StateService getStateService() {
		return stateService;
	}
	public void setStateService(StateService stateService) {
		this.stateService = stateService;
	}
	
	public TypeTypeRelationDao getTypeTypeRelationDao() {
		return typeTypeRelationDao;
	}
	public void setTypeTypeRelationDao(TypeTypeRelationDao typeTypeRelationDao) {
		this.typeTypeRelationDao = typeTypeRelationDao;
	}
	
	public AtpService getAtpService() {
		return atpService;
	}
	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}
	
	public LuService getLuService() {
		return luService;
	}
	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
	    return new ArrayList<String>();
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
	    return null;
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return null;
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<TypeInfo>();
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		
        if ( ! relatedRefObjectURI.startsWith(LuiServiceConstants.NAMESPACE) ) {
            throw new DoesNotExistException("This method does not know how to handle object type:"
                    + relatedRefObjectURI);
        }

        // get the TypeTypeRelations
        List<TypeTypeRelationEntity> typeTypeRelations = typeTypeRelationDao
                .getTypeTypeRelationsByOwnerAndRelationTypes(ownerTypeKey,
                        TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY);
        
//        System.out.println(">>> in LuiServiceImpl.getAllowedTypesForType,find typeTypeRelations.size() = "+typeTypeRelations.size()+
//        		" for ownerTypeKey="+ownerTypeKey+" and RelationType="+TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY);
        
        // create a List of the related Types' IDs
        List<String> ids = new ArrayList<String>();
        for (TypeTypeRelationEntity entity : typeTypeRelations) {
            ids.add(entity.getRelatedTypeId());
        }

        // now get the List of the related Types based on those IDs
        List<TypeEntity<? extends BaseAttributeEntity<?>>> typeEntities = new ArrayList<TypeEntity<? extends BaseAttributeEntity<?>>>();
       	typeEntities.addAll(luiTypeDao.findByIds(ids));

        // convert them to DTOs and return them
        List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
        for (TypeEntity<? extends BaseAttributeEntity<?>> entity : typeEntities) {
            typeInfos.add(entity.toDto());
        }
        
        return typeInfos;
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return new ArrayList<TypeTypeRelationInfo>();
	}

	@Override
	public LuiInfo getLui(String luiId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		LuiEntity lui = luiDao.find(luiId);
        if (null == lui) {
            throw new DoesNotExistException(luiId);
        }

        LuiInfo dto = lui.toDto();
        if(lui.getOfficialIdentifier() != null) {
            dto.setOfficialIdentifier(lui.getOfficialIdentifier().toDto());
        }

        return dto;
	}

	@Override
	public List<LuiInfo> getLuisByIdList(List<String> luiIdList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

        List<LuiEntity> entityList = luiDao.findByIds(luiIdList);
        List<LuiInfo> infoList = new ArrayList<LuiInfo>();

        for (LuiEntity luiEntity : entityList){
            infoList.add(luiEntity.toDto());
        }

        return infoList;
	}

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context) 
	    throws DoesNotExistException, InvalidParameterException, 
		   MissingParameterException, OperationFailedException {

        // make sure the given type key is a valid lui type
        findType(luiTypeKey);

        List<LuiEntity> luis = luiDao.getLuisByType(luiTypeKey);

        List<String> luiIds = new ArrayList<String>();

        for(LuiEntity lui : luis) {
            luiIds.add(lui.getId());
        }

        return luiIds;
	}

	@Override
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<LuiInfo>();
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return new ArrayList<String>();
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<String>();
	}

	@Override
	public List<LuiInfo> getLuisByRelation(String relatedLuiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        List<LuiEntity> entityList = luiLuiRelationDao.getLuisByRelation(relatedLuiId, luLuRelationTypeKey);
        
        List<LuiInfo> infoList = new ArrayList<LuiInfo>();
        if(entityList != null && !entityList.isEmpty()){
	        for (LuiEntity entity : entityList){
	             infoList.add(entity.toDto());
	        }
       
        }
        return infoList;
	}

	@Override
	public List<String> getLuiIdsByRelation(String relatedLuiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        List<String> returnVals = new ArrayList<String>();

        returnVals.addAll(luiLuiRelationDao.getLuiIdsByRelation(relatedLuiId, luLuRelationTypeKey));
        return returnVals;
	}

	@Override
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<LuiInfo>();
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        List<String> returnVals = new ArrayList<String>();
        returnVals.addAll(luiLuiRelationDao.getRelatedLuisByLuiId(luiId, luLuRelationTypeKey));
	    return returnVals;
	}

	@Override
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        LuiLuiRelationEntity obj = luiLuiRelationDao.find(luiLuiRelationId);
        if (null == obj) {
            throw new DoesNotExistException(luiLuiRelationId);
        }
        return obj.toDto();
	}

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIdList(List<String> luiLuiRelationIdList, ContextInfo context) 
		    throws DoesNotExistException, InvalidParameterException, 
		           MissingParameterException, OperationFailedException {
	    return new ArrayList<LuiLuiRelationInfo>();
	}

    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo context) 
	    throws DoesNotExistException, InvalidParameterException, 
		       MissingParameterException, OperationFailedException {
	    return new ArrayList<String>();
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

        // Ensure the lui id is valid
        getLui(luiId, context);
		
        List<LuiLuiRelationEntity> relEntities = luiLuiRelationDao.getLuiLuiRelationsByLui(luiId);
        List<LuiLuiRelationInfo> relInfos = new ArrayList<LuiLuiRelationInfo>();
        if(relEntities != null && !relEntities.isEmpty()){
	        for (LuiLuiRelationEntity relEntity : relEntities) {
	        	LuiLuiRelationInfo relInfo = relEntity.toDto();
	            relInfos.add(relInfo);
	        }
        }

       	return relInfos;
	}


    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria, ContextInfo context) 
	throws InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	return new ArrayList<String>();
    }

    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, ContextInfo context) 
	throws InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	return new ArrayList<LuiInfo>();
    }

	@Override
	public List<ValidationResultInfo> validateLui(String validationType,
			LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<ValidationResultInfo>();
	}

    private StateEntity findState(String processKey, String stateKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException{
    	StateEntity state = null;
		try {
			StateInfo stInfo = stateService.getState(processKey, stateKey, context);
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

    private LuiTypeEntity findType(String typeId)throws OperationFailedException{
    	LuiTypeEntity type = luiTypeDao.find(typeId);
    	if(null != type)
    		return type;
    	else
    		throw new OperationFailedException("The type does not exist. type " + typeId);
    }
    
    //TODO:call LuService 
    private boolean checkExistenceForClu(String cluId, ContextInfo context){
    	//clu = luService.getClu(cluId, context);
    	return true;
    }
    
    private boolean checkExistenceForAtp(String atpKey, ContextInfo context) throws DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, PermissionDeniedException {
 /*   	boolean existing = false;
    	try {
			AtpInfo atp = atpService.getAtp(atpKey, context);
			
			if(atp != null)
				existing = true;
			else
				throw new DoesNotExistException("The ATP does not exist. atp " + atpKey);
		} catch (DoesNotExistException e) {
			throw new DoesNotExistException("The ATP does not exist. atp " + atpKey);
		} catch (InvalidParameterException e) {
		} catch (MissingParameterException e) {
		} catch (OperationFailedException e) {
		} catch (PermissionDeniedException e) {
		}
		
		return existing; */
    	
    	return true;
    }
    
	@Override
	@Transactional
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        LuiEntity entity = new LuiEntity(luiInfo);
        entity.setId(UUIDHelper.genStringUUID());
        
        if( null != cluId && checkExistenceForClu(cluId, context))
        	entity.setCluId(cluId);
        
        if(null != atpKey && checkExistenceForAtp(atpKey, context))
        	entity.setAtpKey(atpKey);

        if (null != luiInfo.getStateKey())
        	entity.setLuiState(findState(LuiServiceConstants.COURSE_OFFERING_PROCESS_KEY, luiInfo.getStateKey(), context));
        
        if (null != luiInfo.getTypeKey())
        	entity.setLuiType(findType(luiInfo.getTypeKey()));

        if (null != luiInfo.getDescr())
        	entity.setDescr(new LuiRichTextEntity(luiInfo.getDescr()));
        
        if (null != luiInfo.getLuiCodes() && !luiInfo.getLuiCodes().isEmpty()){
        	for (LuCodeEntity luiCode : entity.getLuCodes()){
        		luiCode.setLui(entity);
        	}
        }

        if (null != luiInfo.getMeetingSchedules() && !luiInfo.getMeetingSchedules().isEmpty()){
        	for (MeetingScheduleEntity schedule : entity.getMeetingSchedules()){
        		schedule.setLui(entity);
        	}
        }

        LuiEntity existing = luiDao.find(entity.getId());
        if( existing != null) {
            throw new AlreadyExistsException();
	    }
        luiDao.persist(entity);
        
        return luiDao.find(entity.getId()).toDto();
	}

	@Override
	@Transactional
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
    	LuiEntity entity = luiDao.find(luiId);
        
        if( null != entity){
        	LuiEntity modifiedEntity = new LuiEntity(luiInfo);
        	String cluId = luiInfo.getCluId();
            if( null != cluId && checkExistenceForClu(cluId, context))
            	modifiedEntity.setCluId(cluId);
            
            String atpKey = luiInfo.getAtpKey();
            if(null != atpKey && checkExistenceForAtp(atpKey, context))
            	modifiedEntity.setAtpKey(atpKey);

            if (null != luiInfo.getStateKey())
            	modifiedEntity.setLuiState(findState(LuiServiceConstants.COURSE_OFFERING_PROCESS_KEY, luiInfo.getStateKey(), context));
            
            if (null != luiInfo.getTypeKey())
            	modifiedEntity.setLuiType(findType(luiInfo.getTypeKey()));

            if (null != luiInfo.getLuiCodes() && !luiInfo.getLuiCodes().isEmpty()){
            	for (LuCodeEntity luiCode : modifiedEntity.getLuCodes()){
            		luiCode.setLui(modifiedEntity);
            	}
            }

            if (null != luiInfo.getMeetingSchedules() && !luiInfo.getMeetingSchedules().isEmpty()){
            	for (MeetingScheduleEntity schedule : modifiedEntity.getMeetingSchedules()){
            		schedule.setLui(modifiedEntity);
            	}
            }
            
            luiDao.merge(modifiedEntity);
	        return luiDao.find(modifiedEntity.getId()).toDto();
        }
        else
            throw new DoesNotExistException(luiId);
	}

	@Override
	@Transactional
	public StatusInfo deleteLui(String luiId, ContextInfo context)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        
        LuiEntity entity = luiDao.find(luiId);
        if( null != entity){
        	List<LuiLuiRelationEntity> rels = luiLuiRelationDao.getLuiLuiRelationsByLui(luiId);
            if (null != rels && !rels.isEmpty()) {
                for (LuiLuiRelationEntity rel : rels)
                	luiLuiRelationDao.remove(rel);
            }
            
            luiDao.remove(entity);
        }
        else
            throw new DoesNotExistException(luiId);
        
        return status;
	}

	@Override
	public LuiInfo updateLuiState(String luiId, String luState,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    return null;
	}

    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, ContextInfo context) 
	throws InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	return new ArrayList<String>();
    }

    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, ContextInfo context) 
	throws InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	return new ArrayList<LuiLuiRelationInfo>();
    }

	@Override
	public List<ValidationResultInfo> validateLuiLuiRelation(
			String validationType, LuiLuiRelationInfo luiLuiRelationInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<ValidationResultInfo>();
	}

    private boolean checkExistenceForRelation(LuiLuiRelationInfo relationInfo) {
        boolean existing = false;

        List<LuiLuiRelationEntity> rels = luiLuiRelationDao.getLuiLuiRelationsByLui(relationInfo.getLuiId());

        if (rels != null && !rels.isEmpty()) {
            for (LuiLuiRelationEntity rel : rels) {
            	if(rel.getLui().getId().equals(relationInfo.getLuiId()) && rel.getRelatedLui().getId().equals(relationInfo.getRelatedLuiId())){
                    existing = true;
                    break;

            	}
            }
        }

        return existing;
    }
	@Override
	@Transactional
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
			String relatedLuiId, String luLuRelationTypeKey,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws AlreadyExistsException, CircularRelationshipException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
        if (!checkExistenceForRelation(luiLuiRelationInfo)) {
            LuiLuiRelationEntity entity = new LuiLuiRelationEntity(luiLuiRelationInfo);
            entity.setId(UUIDHelper.genStringUUID());

            if (null != luiLuiRelationInfo.getStateKey()) {
                entity.setLuiLuiRelationState(findState(LuiServiceConstants.LUI_LUI_RELATION_PROCESS_KEY, luiLuiRelationInfo.getStateKey(), context));
            }
            if (null != luiLuiRelationInfo.getTypeKey()) {
            	entity.setLuiLuiRelationType(findType(luiLuiRelationInfo.getTypeKey()));
            }
            if (null != luiLuiRelationInfo.getLuiId()) {
            	entity.setLui(luiDao.find(luiLuiRelationInfo.getLuiId()));
            }
            if (null != luiLuiRelationInfo.getRelatedLuiId()) {
            	entity.setRelatedLui(luiDao.find(luiLuiRelationInfo.getRelatedLuiId()));
            }

            luiLuiRelationDao.persist(entity);

            return luiLuiRelationDao.find(entity.getId()).toDto();
        } else
            throw new AlreadyExistsException("The Lui-Lui relation already exists. lui="
                    + luiLuiRelationInfo.getLuiId() + ", relatedLui=" + luiLuiRelationInfo.getRelatedLuiId());

	}

	@Override
	@Transactional
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
	    return null;
	}

	@Override
	@Transactional
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        LuiLuiRelationEntity obj = luiLuiRelationDao.find(luiLuiRelationId);
        if (obj != null)
        	luiLuiRelationDao.remove(obj);
        else
            throw new DoesNotExistException(luiLuiRelationId);

        return status;
	}

    @Override
    public LuiCapacityInfo getLuiCapacity(String luiCapacityId, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {
        return null;
    }

    @Override
    public List<LuiCapacityInfo> getLuiCapacitiesByIdList(List<String> luiCapacityIdList, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {
        return new ArrayList<LuiCapacityInfo>();
    }

    @Override
    public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {
        return new ArrayList<String>();
    }

    @Override
    public List<String> searchForLuiCapacityIds(QueryByCriteria criteria, ContextInfo context) 
	throws InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	return new ArrayList<String>();
    }

    @Override
    public List<LuiCapacityInfo> searchForLuiCapacities(QueryByCriteria criteria, ContextInfo context) 
	throws InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {

	return new ArrayList<LuiCapacityInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateLuiCapacity(String validationType, LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
	throws DoesNotExistException, InvalidParameterException, 
	       MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public LuiCapacityInfo createLuiCapacity(LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
	throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, 
	       InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public LuiCapacityInfo updateLuiCapacity(String luiCapacityId, LuiCapacityInfo luiCapacityInfo, ContextInfo context) 
	throws DataValidationErrorException, DoesNotExistException, 
	       InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException, 
	       VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteLuiCapacity(String luiCapacityId, ContextInfo context) 
	throws DependentObjectsExistException, DoesNotExistException, 
	       InvalidParameterException, MissingParameterException, 
	       OperationFailedException, PermissionDeniedException {
        return null;
    }
}

