package org.kuali.student.r2.core.class1.type.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.class1.lpr.model.LuiPersonRelationEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.class1.type.dao.TypeDao;
import org.kuali.student.r2.core.class1.type.dao.TypeTypeRelationDao;
import org.kuali.student.r2.core.class1.type.model.TypeEntity;
import org.kuali.student.r2.core.class1.type.model.TypeTypeRelationEntity;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.type.service.TypeService;

public class TypeServiceImpl implements TypeService {
    private TypeDao typeDao;

    private TypeTypeRelationDao typeTypeRelationDao;

    public TypeDao getTypeDao() {
        return typeDao;
    }

    public void setTypeDao(TypeDao typeDao) {
        this.typeDao = typeDao;
    }

    public TypeTypeRelationDao getTypeTypeRelationDao() {
        return typeTypeRelationDao;
    }

    public void setTypeTypeRelationDao(TypeTypeRelationDao typeTypeRelationDao) {
        this.typeTypeRelationDao = typeTypeRelationDao;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return typeDao.find(typeKey).toDto();
    }

    @Override
    public List<TypeInfo> getTypesByKeys(List<String> typeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<TypeInfo> typeInfoList = new ArrayList<TypeInfo>();

        for (TypeEntity type : typeDao.findByIds(typeKeys)) {
            typeInfoList.add(type.toDto());
        }

        return typeInfoList;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<TypeTypeRelationEntity> typeTypeRelationEntities = typeTypeRelationDao.getTypeTypeRelationsByOwnerType(ownerTypeKey, relatedRefObjectURI);
        List<TypeInfo> typeInfoList = new ArrayList<TypeInfo>();
        for (TypeTypeRelationEntity typeTypeEntity : typeTypeRelationEntities) {
            typeInfoList.add(typeDao.find(typeTypeEntity.getRelatedTypeId()).toDto());
        }

        return typeInfoList;
    }

    @Override
    public List<ValidationResultInfo> validateType(String validationTypeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeInfo createType(String typeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        TypeEntity typeToPersist = new TypeEntity(typeInfo);
        typeToPersist.setId(typeKey);
        typeDao.persist(typeToPersist);
        return typeToPersist.toDto();
    }

    @Override
    public TypeInfo updateType(String typeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
     
        TypeEntity typeEntity = typeDao.find(typeKey);

        if (null != typeEntity) {
            TypeEntity modifiedType = new TypeEntity(typeInfo);

            typeDao.merge(modifiedType);
            return typeDao.find(modifiedType.getId()).toDto();
        } else
            throw new DoesNotExistException(typeKey);
    }

    @Override
    public StatusInfo deleteType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        StatusInfo deleteStatus = new StatusInfo();
        deleteStatus.setSuccess(false);
        
        
        TypeEntity typeEntityToRemove = typeDao.find(typeKey);
        typeDao.remove(typeEntityToRemove);

        
        deleteStatus.setSuccess(true);
        return deleteStatus;
    }

    @Override
    public TypeTypeRelationInfo getTypeTypeRelation(String typeTypeRelationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return typeTypeRelationDao.find(typeTypeRelationKey).toDto();
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeTypeRelationEntity> typeTypeEntities = typeTypeRelationDao.getTypeTypeRelationsByOwnerAndRelationTypes(ownerTypeKey, relationTypeKey);
        List<TypeTypeRelationInfo> typeTypeRealtionInfo = new ArrayList<TypeTypeRelationInfo>();
        for (TypeTypeRelationEntity typeTypeEntity : typeTypeEntities) {
            typeTypeRealtionInfo.add(typeTypeEntity.toDto());
        }
        return typeTypeRealtionInfo;
    }

    @Override
    public List<ValidationResultInfo> validateTypeTypeRelation(String validationTypeKey, String typeKey, String typePeerKey, String typeTypeRelationTypeKey, TypeTypeRelationInfo typeTypeRelationInfo,
            ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public TypeTypeRelationInfo createTypeTypeRelation(String typeTypeRelationKey, String typeKey, String typePeerKey, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        TypeTypeRelationEntity typeRelationToPersist = new TypeTypeRelationEntity(typeTypeRelationInfo);

        typeRelationToPersist.setId(typeTypeRelationKey);
        typeRelationToPersist.setOwnerTypeId(typeKey);
        typeRelationToPersist.setRelatedTypeId(typePeerKey);
        typeTypeRelationDao.persist(typeRelationToPersist);
        return typeRelationToPersist.toDto();
    }

    @Override
    public TypeTypeRelationInfo updateTypeTypeRelation(String typeTypeRelationKey, TypeTypeRelationInfo typeTypeRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
       
        TypeTypeRelationEntity typeRelationEntity = typeTypeRelationDao.find(typeTypeRelationKey);
        
        if (null != typeRelationEntity) {
            TypeTypeRelationEntity modifiedTypeTypeRelationEntity = new TypeTypeRelationEntity(typeTypeRelationInfo);

            typeTypeRelationDao.merge(modifiedTypeTypeRelationEntity);
            return typeTypeRelationDao.find(modifiedTypeTypeRelationEntity.getId()).toDto();
        } else
            throw new DoesNotExistException(typeTypeRelationKey);
    }

    @Override
    public StatusInfo deleteTypeTypeRelation(String typeTypeRelationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
       
        StatusInfo deleteStatus = new StatusInfo();
        deleteStatus.setSuccess(false);
        
        TypeTypeRelationEntity typeEntityToRemove = typeTypeRelationDao.find(typeTypeRelationKey);
        typeTypeRelationDao.remove(typeEntityToRemove);

        deleteStatus.setSuccess(true);
        return deleteStatus;
    }

    @Override
    public List<String> getRefObjectUris(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
//        TODO: waiting for Sambit to implemnet
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
    @Override
    public List<TypeInfo> getTypesByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<TypeInfo>  typDTOs =  new ArrayList<TypeInfo>();
       List<TypeEntity>  typeEntities =  typeDao.getTypesByRefObjectUri(refObjectUri);
       for( TypeEntity typeEntity : typeEntities ){
           typDTOs.add(typeEntity.toDto());
       }
       
       return typDTOs;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByIds(List<String> typeTypeRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
