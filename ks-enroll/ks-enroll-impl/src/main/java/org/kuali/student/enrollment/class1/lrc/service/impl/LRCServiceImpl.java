package org.kuali.student.enrollment.class1.lrc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.jws.WebService;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class1.lrc.dao.ResultScaleDao;
import org.kuali.student.enrollment.class1.lrc.dao.ResultValueDao;
import org.kuali.student.enrollment.class1.lrc.dao.ResultValuesGroupDao;
import org.kuali.student.enrollment.class1.lrc.model.ResultScaleEntity;
import org.kuali.student.enrollment.class1.lrc.model.ResultValueEntity;
import org.kuali.student.enrollment.class1.lrc.model.ResultValuesGroupEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.lrc.service.LrcServiceBusinessLogic;
import org.springframework.transaction.annotation.Transactional;

public class LRCServiceImpl implements LRCService {

    private ResultValuesGroupDao resultValuesGroupDao;
    private ResultValueDao resultValueDao;
    private ResultScaleDao resultScaleDao;
    private LrcServiceBusinessLogic lrcServiceBusinessLogic;

    public LrcServiceBusinessLogic getLrcServiceBusinessLogic() {
        return lrcServiceBusinessLogic;
    }

    public void setLrcServiceBusinessLogic(LrcServiceBusinessLogic lrcServiceBusinessLogic) {
        this.lrcServiceBusinessLogic = lrcServiceBusinessLogic;
    }

    public ResultScaleDao getResultScaleDao() {
        return resultScaleDao;
    }

    public void setResultScaleDao(ResultScaleDao resultScaleDao) {
        this.resultScaleDao = resultScaleDao;
    }

    public ResultValueDao getResultValueDao() {
        return resultValueDao;
    }

    public void setResultValueDao(ResultValueDao resultValueDao) {
        this.resultValueDao = resultValueDao;
    }

    public ResultValuesGroupDao getResultValuesGroupDao() {
        return resultValuesGroupDao;
    }

    public void setResultValuesGroupDao(ResultValuesGroupDao resultValuesGroupDao) {
        this.resultValuesGroupDao = resultValuesGroupDao;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultScaleInfo createResultScale(String typeKey,
            ResultScaleInfo info,
            ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultScaleEntity entity = this.resultScaleDao.find(info.getKey());
        if (entity != null) {
            throw new AlreadyExistsException(info.getKey());
        }
        info.setTypeKey(typeKey);
        entity = new ResultScaleEntity(info, this.resultScaleDao.getEm());
        entity.setEntityCreated(contextInfo);
        resultScaleDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValueInfo createResultValue(String resultScaleKey,
            String typeKey,
            ResultValueInfo info,
            ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValueEntity entity = this.resultValueDao.find(info.getKey());
        if (entity != null) {
            throw new AlreadyExistsException(info.getKey());
        }
        info.setResultScaleKey(resultScaleKey);
        info.setTypeKey(typeKey);
        entity = new ResultValueEntity(info, this.resultValueDao.getEm());
        entity.setEntityCreated(contextInfo);
        resultValueDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValuesGroupInfo createResultValuesGroup(String resultScaleKey,
            String typeKey,
            ResultValuesGroupInfo info,
            ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValuesGroupEntity entity = this.resultValuesGroupDao.find(info.getKey());
        if (entity != null) {
            throw new AlreadyExistsException(info.getKey());
        }
        info.setResultScaleKey(resultScaleKey);
        info.setTypeKey(typeKey);
        entity = new ResultValuesGroupEntity(info, resultValuesGroupDao.getEm());
        entity.setEntityCreated(contextInfo);
        resultValuesGroupDao.persist(entity);
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteResultScale(String key,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            DependentObjectsExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultScaleEntity entity = resultScaleDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        List<ResultValuesGroupInfo> list = this.getResultValuesGroupsByResultScale(key, contextInfo);
        if (!list.isEmpty()) {
            throw new DependentObjectsExistException(list.size() + " rvgs exist");
        }
        List<ResultValueInfo> list2 = this.getResultValuesForScale(key, contextInfo);
        if (!list2.isEmpty()) {
            throw new DependentObjectsExistException(list2.size() + " values exist");
        }
        resultScaleDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteResultValue(String key,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            DependentObjectsExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValueEntity entity = resultValueDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        List<ResultValuesGroupInfo> list = this.getResultValuesGroupsByResultValue(key, contextInfo);
        if (!list.isEmpty()) {
            throw new DependentObjectsExistException(list.size() + " rvgs exist");
        }
        resultValueDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteResultValuesGroup(String key,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValuesGroupEntity entity = resultValuesGroupDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        resultValuesGroupDao.remove(entity);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(String creditValue,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return this.getLrcServiceBusinessLogic().getCreateFixedCreditResultValuesGroup(creditValue, scaleKey, context);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(String creditValueMin,
            String creditValueMax,
            String creditValueIncrement,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValuesGroupInfo rvg = this.getLrcServiceBusinessLogic().getCreateRangeCreditResultValuesGroup(creditValueMin,
                creditValueMax,
                creditValueIncrement, scaleKey, context);
        return rvg;

    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValuesGroupInfo getCreateMultipleCreditResultValuesGroup(List<String> creditValues,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return this.getLrcServiceBusinessLogic().getCreateMultipleCreditResultValuesGroup(creditValues, scaleKey, context);
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValueInfo getCreateResultValueForScale(String resultValue,
            String scaleKey,
            ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return this.getLrcServiceBusinessLogic().getCreateResultValueForScale(resultValue, scaleKey, context);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultScaleInfo getResultScale(String key,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultScaleEntity entity = resultScaleDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getResultScaleKeysByType(String resultScaleTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return resultScaleDao.getIdsByType(resultScaleTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultScaleInfo> getResultScalesByKeys(List<String> keys,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultScaleInfo> infos = new ArrayList<ResultScaleInfo>();
        List<ResultScaleEntity> entities = resultScaleDao.findByIds(keys);
        for (ResultScaleEntity entity : entities) {
            ResultScaleInfo info = entity.toDto();
            infos.add(info);
        }
        return infos;
    }

    @Override
    @Transactional(readOnly = true)
    public ResultValueInfo getResultValue(String key,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValueEntity entity = resultValueDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public ResultValueInfo getResultValueForScaleAndValue(String resultScaleKey,
            String value,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValueEntity entity = resultValueDao.getByScaleAndValue(resultScaleKey, value);
        if (entity == null) {
            throw new DoesNotExistException (resultScaleKey + "." + value);
        }
        ResultValueInfo info = entity.toDto();
        return info;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getResultValueKeysByType(String resultValueTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return resultValueDao.getIdsByType(resultValueTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValueInfo> getResultValuesByKeys(List<String> keys,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValueInfo> infos = new ArrayList<ResultValueInfo>();
        List<ResultValueEntity> entities = resultValueDao.findByIds(keys);
        for (ResultValueEntity entity : entities) {
            ResultValueInfo info = entity.toDto();
            infos.add(info);
        }
        return infos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValueInfo> getResultValuesForResultValuesGroup(String resultValuesGroupKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // TODO: improve performance by turning this into a single JPQL query
        ResultValuesGroupInfo info = this.getResultValuesGroup(resultValuesGroupKey, contextInfo);
        return this.getResultValuesByKeys(info.getResultValueKeys(), contextInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValueInfo> getResultValuesForResultValuesGroups(List<String> resultValuesGroupKeys,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // TODO: improve performance by turning this into a single JPQL query
        // NOTE: this gets unduplicated RVs that might be in a bunch of groups
        List<ResultValueInfo> list = new ArrayList<ResultValueInfo>();
        Set<String> keys = new HashSet<String>();
        for (String rvgKey : resultValuesGroupKeys) {
            ResultValuesGroupInfo rvg = this.getResultValuesGroup(rvgKey, contextInfo);
            for (String rvKey : rvg.getResultValueKeys()) {
                if (keys.add(rvKey)) {
                    list.add(this.getResultValue(rvKey, contextInfo));
                }
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValueInfo> getResultValuesForScale(String resultScaleKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValueInfo> infos = new ArrayList<ResultValueInfo>();
        List<ResultValueEntity> entities = resultValueDao.getByScale(resultScaleKey);
        for (ResultValueEntity entity : entities) {
            ResultValueInfo info = entity.toDto();
            infos.add(info);
        }
        return infos;
    }

    @Override
    @Transactional(readOnly = true)
    public ResultValuesGroupInfo getResultValuesGroup(String key,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        ResultValuesGroupEntity entity = resultValuesGroupDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getResultValuesGroupKeysByType(String resultValuesGroupTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return resultValuesGroupDao.getIdsByType(resultValuesGroupTypeKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(List<String> keys,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> infos = new ArrayList<ResultValuesGroupInfo>();
        List<ResultValuesGroupEntity> entities = resultValuesGroupDao.findByIds(keys);
        for (ResultValuesGroupEntity entity : entities) {
            ResultValuesGroupInfo info = entity.toDto();
            infos.add(info);
        }
        return infos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScale(String resultScaleKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> infos = new ArrayList<ResultValuesGroupInfo>();
        List<ResultValuesGroupEntity> entities = resultValuesGroupDao.getByResultScale(resultScaleKey);
        for (ResultValuesGroupEntity entity : entities) {
            ResultValuesGroupInfo info = entity.toDto();
            infos.add(info);
        }
        return infos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScaleType(String resultScaleTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // TODO: improve performance by implementing this via a single jpql query that does the desired join
        List<ResultValuesGroupInfo> list = new ArrayList<ResultValuesGroupInfo>();
        List<String> scaleKeys = this.getResultScaleKeysByType(resultScaleTypeKey, contextInfo);
        for (String scaleKey : scaleKeys) {
            list.addAll(this.getResultValuesGroupsByResultScale(scaleKey, contextInfo));
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(String resultValueKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ResultValuesGroupInfo> list = new ArrayList<ResultValuesGroupInfo>();
        for (ResultValuesGroupEntity entity : this.resultValuesGroupDao.getByResultValue(resultValueKey)) {
            list.add(entity.toDto());
        }
        return list;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultScaleInfo updateResultScale(String key,
            ResultScaleInfo info,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        if (!key.equals(info.getKey())) {
            throw new InvalidParameterException(key + " does not match the key in the info object " + info.getKey());
        }
        ResultScaleEntity entity = resultScaleDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        entity.fromDTO(info, resultScaleDao.getEm());
        entity.setEntityUpdated(contextInfo);
        entity = resultScaleDao.merge(entity);
        resultScaleDao.getEm().flush(); // need to flush to get the version indicator updated
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValueInfo updateResultValue(String key,
            ResultValueInfo info,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        if (!key.equals(info.getKey())) {
            throw new InvalidParameterException(key + " does not match the key in the info object " + info.getKey());
        }
        ResultValueEntity entity = resultValueDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        entity.fromDTO(info, this.resultValueDao.getEm());
        entity.setEntityUpdated(contextInfo);
        entity = resultValueDao.merge(entity);
        resultScaleDao.getEm().flush(); // need to flush to get the version ind updated
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public ResultValuesGroupInfo updateResultValuesGroup(String key,
            ResultValuesGroupInfo info,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        if (!key.equals(info.getKey())) {
            throw new InvalidParameterException(key + " does not match the key in the info object " + info.getKey());
        }
        ResultValuesGroupEntity entity = resultValuesGroupDao.find(key);
        if (entity == null) {
            throw new DoesNotExistException(key);
        }
        entity.fromDTO(info, resultValuesGroupDao.getEm());
        entity.setEntityUpdated(contextInfo);
        entity = resultValuesGroupDao.merge(entity);
        this.resultValuesGroupDao.getEm().flush(); // need to flush to get the version ind updated
        return entity.toDto();
    }

    @Override
    public List<ValidationResultInfo> validateResultScale(String validationType,
            ResultScaleInfo gradeScaleInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new OperationFailedException("Should have been implemented in the Validation Decorator");
    }

    @Override
    public List<ValidationResultInfo> validateResultValue(String validationType,
            ResultValueInfo resultValueInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new OperationFailedException("Should have been implemented in the Validation Decorator");
    }

    @Override
    public List<ValidationResultInfo> validateResultValuesGroup(String validationType,
            ResultValuesGroupInfo gradeValuesGroupInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        throw new OperationFailedException("Should have been implemented in the Validation Decorator");
    }
}
