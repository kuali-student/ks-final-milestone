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

package org.kuali.student.r2.core.class1.enumerationmanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.persistence.NoResultException;

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
import org.kuali.student.r2.common.util.constants.EnumerationManagementServiceConstants;
import org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumContextValueDao;
import org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumeratedValueDao;
import org.kuali.student.r2.core.class1.enumerationmanagement.dao.EnumerationDao;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumeratedValueEntity;
import org.kuali.student.r2.core.class1.enumerationmanagement.model.EnumerationEntity;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumerationInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Enumeration Management Service implementation class.
 *
 * @Version 2.0
 */
@WebService(name = "EnumerationManagementService", serviceName = "EnumerationManagementService", portName = "EnumerationManagementService", targetNamespace = EnumerationManagementServiceConstants.NAMESPACE)
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class EnumerationManagementServiceImpl implements EnumerationManagementService {

    private EnumerationDao enumDao;
    private EnumeratedValueDao enumValueDao;
    private EnumContextValueDao enumContextValueDao;
    
    public EnumerationDao getEnumDao() {
        return enumDao;
    }

    public void setEnumDao(EnumerationDao enumDao) {
        this.enumDao = enumDao;
    }

    public EnumeratedValueDao getEnumValueDao() {
        return enumValueDao;
    }

    public void setEnumValueDao(EnumeratedValueDao enumValueDao) {
        this.enumValueDao = enumValueDao;
    }

    public EnumContextValueDao getEnumContextValueDao() {
        return enumContextValueDao;
    }

    public void setEnumContextValueDao(EnumContextValueDao enumContextValueDao) {
        this.enumContextValueDao = enumContextValueDao;
    }

    @Override
    @Transactional(readOnly=true)
    public List<EnumerationInfo> getEnumerations(ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<EnumerationEntity> enumEntities =  this.enumDao.findAll();
        List<EnumerationInfo> enumInfos = new ArrayList<EnumerationInfo>(enumEntities.size());
        
        for (EnumerationEntity enumeration : enumEntities){
            enumInfos.add(enumeration.toDto());
        }
       
        return enumInfos;
        
    }

    @Override
    public EnumerationInfo getEnumeration(String enumerationKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        EnumerationEntity entity = enumDao.find(enumerationKey);
        
        if(entity == null)
            throw new DoesNotExistException(enumerationKey);
        
        return entity.toDto();
    }

    @Override
    public List<EnumeratedValueInfo> getEnumeratedValues(String enumerationKey, String contextTypeKey, String contextValue, Date contextDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        List<EnumeratedValueEntity> enumeratedValues = null;
        
        if(enumerationKey != null && contextTypeKey != null && contextValue != null && contextDate != null){
            enumeratedValues = enumValueDao.getByContextAndDate(enumerationKey, contextTypeKey, contextValue, contextDate);
        }
        else if(enumerationKey != null && contextTypeKey != null && contextValue != null){
            enumeratedValues = enumValueDao.getByContextTypeAndValue(enumerationKey, contextTypeKey, contextValue);
        }
        else if(enumerationKey != null && contextDate != null){
            enumeratedValues = enumValueDao.getByDate(enumerationKey, contextDate);
        }
        else if(enumerationKey != null){
            enumeratedValues = enumValueDao.getByEnumerationKey(enumerationKey);
        }
        
        if(enumeratedValues == null)
            throw new DoesNotExistException(enumerationKey);
        
        List<EnumeratedValueInfo> enumeratedValueInfos = new ArrayList<EnumeratedValueInfo>(enumeratedValues.size());
        for (EnumeratedValueEntity enumeratedValue : enumeratedValues){
            enumeratedValueInfos.add(enumeratedValue.toDto());
        }
        
        return enumeratedValueInfos;
    }

    @Override
    public List<ValidationResultInfo> validateEnumeratedValue(String validationTypeKey, String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null; //FIXME need real validation
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public EnumeratedValueInfo updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        EnumerationEntity enumerationEntity = enumDao.find(enumeratedValueInfo.getEnumerationKey());           
        if(enumerationEntity == null)
            throw new DoesNotExistException(enumeratedValueInfo.getEnumerationKey());

        EnumeratedValueEntity modifiedEntity = new EnumeratedValueEntity(enumeratedValueInfo);
        modifiedEntity.setEnumeration(enumerationEntity);
        
        try {
            modifiedEntity.setId(enumValueDao.getByEnumerationKeyAndCode(enumerationKey, code).getId());
        }catch (NoResultException e) {
            throw new DoesNotExistException(enumerationKey + code);
        }
        
        enumValueDao.merge(modifiedEntity);
        
        return enumValueDao.find(modifiedEntity.getId()).toDto();
        
    }

    @Override
    public StatusInfo deleteEnumeratedValue(String enumerationKey, String code, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        EnumeratedValueEntity enumeratedValue = enumValueDao.getByEnumerationKeyAndCode(enumerationKey, code);
        if (null != enumeratedValue) {
            enumValueDao.remove(enumeratedValue);
        } else
            status.setSuccess(Boolean.FALSE);

        return status;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public EnumeratedValueInfo addEnumeratedValue(String enumerationKey, String code, EnumeratedValueInfo enumeratedValueInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        EnumerationEntity enumerationEntity = enumDao.find(enumeratedValueInfo.getEnumerationKey());           
        if(enumerationEntity == null)
            throw new DoesNotExistException(enumeratedValueInfo.getEnumerationKey());
        
        EnumeratedValueEntity entity = null;
        try {
            
            enumValueDao.getByEnumerationKeyAndCode(enumerationKey, code);
            throw new AlreadyExistsException();
            
        } catch (NoResultException e) {
            
            entity = new EnumeratedValueEntity(enumeratedValueInfo);
            entity.setEnumeration(enumerationEntity);
            
            enumValueDao.persist(entity);
        }
        
        return enumValueDao.find(entity.getId()).toDto();
        
    }

}
