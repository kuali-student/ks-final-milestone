/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 4/25/12
 */
package org.kuali.student.r2.core.fee.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.fee.dao.EnrollmentFeeDao;
import org.kuali.student.r2.core.fee.dto.EnrollmentFeeInfo;
import org.kuali.student.r2.core.fee.model.EnrollmentFeeEntity;
import org.kuali.student.r2.core.fee.service.FeeService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Fee Service
 *
 * @author Kuali Student Team
 */
public class FeeServiceImpl implements FeeService {
    @Resource
    private EnrollmentFeeDao enrollmentFeeDao;

    public EnrollmentFeeDao getEnrollmentFeeDao() {
        return enrollmentFeeDao;
    }

    public void setEnrollmentFeeDao(EnrollmentFeeDao enrollmentFeeDao) {
        this.enrollmentFeeDao = enrollmentFeeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentFeeInfo getFee(String feeId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        EnrollmentFeeEntity feeEntity = enrollmentFeeDao.find(feeId);
        if (null == feeEntity) {
            throw new DoesNotExistException(feeId);
        }
        return feeEntity.toDto();
    }

    @Override
    public List<EnrollmentFeeInfo> getFeesByIds(@WebParam(name = "feeIds") List<String> feeIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getFeeIdsByType(@WebParam(name = "feeTypeKey") String feeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<EnrollmentFeeInfo> getFeesByReference(String refObjectURI, String refObjectId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<EnrollmentFeeEntity> entityList = enrollmentFeeDao.getFeesByRefObjectURIAndId(refObjectURI, refObjectId);
        List<EnrollmentFeeInfo> infoList = new ArrayList<EnrollmentFeeInfo>();
        if (entityList != null) {
            for (EnrollmentFeeEntity entity: entityList) {
                infoList.add(entity.toDto());
            }
        }
        return infoList;
    }

    @Override
    public List<String> searchForFeeIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<EnrollmentFeeInfo> searchForFees(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateFee(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "feeTypeKey") String feeTypeKey, @WebParam(name = "feeInfo") EnrollmentFeeInfo feeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public EnrollmentFeeInfo createFee(String feeTypeKey, EnrollmentFeeInfo feeInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        EnrollmentFeeEntity feeEntity = new EnrollmentFeeEntity(feeInfo);
       
        feeEntity.setEntityCreated(contextInfo);
        
        enrollmentFeeDao.persist(feeEntity);
        return feeEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public EnrollmentFeeInfo updateFee(String feeId, EnrollmentFeeInfo feeInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
                   MissingParameterException, OperationFailedException, PermissionDeniedException,
                   ReadOnlyException, VersionMismatchException {
        EnrollmentFeeEntity feeEntity = enrollmentFeeDao.find(feeId);
        if (null != feeEntity) {
            feeEntity.fromDto(feeInfo);
            
            feeEntity.setEntityUpdated(contextInfo);
            
            enrollmentFeeDao.merge(feeEntity);
            return feeEntity.toDto();
        } else {
            throw new DoesNotExistException(feeId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteFee(String feeId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        EnrollmentFeeEntity feeEntity = enrollmentFeeDao.find(feeId);
        if (null != feeEntity) {
            enrollmentFeeDao.remove(feeEntity);
        } else {
            throw new DoesNotExistException(feeId);
        }
        return status;
    }
}
