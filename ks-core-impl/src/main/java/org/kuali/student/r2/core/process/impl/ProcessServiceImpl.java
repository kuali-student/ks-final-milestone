package org.kuali.student.r2.core.process.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
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
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dao.CheckDao;
import org.kuali.student.r2.core.process.dao.InstructionDao;
import org.kuali.student.r2.core.process.dao.ProcessDao;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.model.CheckEntity;
import org.kuali.student.r2.core.process.model.InstructionEntity;
import org.kuali.student.r2.core.process.model.ProcessCategoryEntity;
import org.kuali.student.r2.core.process.model.ProcessEntity;
import org.kuali.student.r2.core.process.service.ProcessService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebService(name = "ProcessService", serviceName = "ProcessService", portName = "ProcessService", targetNamespace = "http://student.kuali.org/wsdl/process")
public class ProcessServiceImpl implements ProcessService {

    private CheckDao checkDao;
    private InstructionDao instructionDao;
    private ProcessDao processDao;
    private StateService stateService;
    private CriteriaLookupService criteriaLookupService;


    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    public CriteriaLookupService getCriteriaLookupService() {
        return criteriaLookupService;
    }

    public CheckDao getCheckDao() {
        return checkDao;
    }

    public void setCheckDao(CheckDao checkDao) {
        this.checkDao = checkDao;
    }

    public InstructionDao getInstructionDao() {
        return instructionDao;
    }

    public void setInstructionDao(InstructionDao instructionDao) {
        this.instructionDao = instructionDao;
    }

    public ProcessDao getProcessDao() {
        return processDao;
    }

    public void setProcessDao(ProcessDao processDao) {
        this.processDao = processDao;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public ProcessCategoryInfo getProcessCategory(String processCategoryId,
                                                   ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public List<ProcessCategoryInfo> getProcessCategoriesByIds(
            List<String> processCategoryIds,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public List<String> getProcessCategoryIdsByType(String processTypeKey,
                                                     ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public List<ProcessCategoryInfo> getProcessCategoriesForProcess(String processKey,
                                                                     ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public List<String> searchForProcessCategoryIds(QueryByCriteria criteria,
                                                     ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<ProcessCategoryEntity> processCategories = criteriaLookupService.lookup(ProcessCategoryEntity.class, criteria);
        if (null != processCategories && processCategories.getResults().size() > 0) {
            for (ProcessCategoryEntity processCat : processCategories.getResults()) {
                results.add(processCat.getId());
            }
        }
        return results;
    }

    @Override
    public List<ProcessCategoryInfo> searchForProcessCategories(QueryByCriteria criteria,
                                                                 ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ProcessCategoryInfo> results = new ArrayList<ProcessCategoryInfo>();
        GenericQueryResults<ProcessCategoryEntity> processCategories = criteriaLookupService.lookup(ProcessCategoryEntity.class, criteria);
        if (null != processCategories && processCategories.getResults().size() > 0) {
            for (ProcessCategoryEntity processCat : processCategories.getResults()) {
                results.add(processCat.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateProcessCategory(
            String validationTypeKey,
            String processCategoryTypeKey,
            ProcessCategoryInfo processCategoryInfo,
             ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(String processCategoryTypeKey, ProcessCategoryInfo processInfo,
                                                      ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public ProcessCategoryInfo updateProcessCategory(String processCategoryId,
                                                     ProcessCategoryInfo processInfo,
                                                      ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public StatusInfo deleteProcessCategory(String processCategoryId,
                                             ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            DependentObjectsExistException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey,
                                                  String processCategoryId,
                                                   ContextInfo contextInfo) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public StatusInfo removeProcessFromProcessCategory(String processKey,
                                                       String processCategoryId,
                                                        ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public ProcessInfo getProcess(String processKey,
                                   ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProcessEntity processEntity = processDao.find(processKey);
        if (processEntity == null) {
            throw new DoesNotExistException(processKey);
        }

        return processEntity.toDto();
    }

    @Override
    public List<ProcessInfo> getProcessesByKeys(List<String> processKeys,
                                                 ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ProcessEntity> processEntities = processDao.findByIds(processKeys);
        if (processEntities == null || processEntities.isEmpty()) {
            throw new DoesNotExistException("No records found");
        }
        List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();
        for (ProcessEntity processEntity : processEntities) {
            if (processEntity == null) {
                throw new DoesNotExistException();
            }
            processInfoList.add(processEntity.toDto());
        }
        return processInfoList;
    }

    @Override
    public List<String> getProcessKeysByType(String processTypeKey,
                                              ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ProcessEntity> processEntities = processDao.getByProcessTypeId(processTypeKey);

        List<String> processIds = new ArrayList<String>();
        for (ProcessEntity processEntity : processEntities) {
            processIds.add(processEntity.getId());
        }

        return processIds;
    }

    @Override
    public List<ProcessInfo> getProcessesForProcessCategory(
            String processCategoryId,
             ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO
        // implement
    }

    @Override
    public List<String> searchForProcessKeys(QueryByCriteria criteria,
                                              ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<ProcessEntity> processes = criteriaLookupService.lookup(ProcessEntity.class, criteria);
        if (null != processes && processes.getResults().size() > 0) {
            for (ProcessEntity process : processes.getResults()) {
                results.add(process.getId());
            }
        }
        return results;
    }

    @Override
    public List<ProcessInfo> searchForProcess(QueryByCriteria criteria,
                                               ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ProcessInfo> results = new ArrayList<ProcessInfo>();
        GenericQueryResults<ProcessEntity> processes = criteriaLookupService.lookup(ProcessEntity.class, criteria);
        if (null != processes && processes.getResults().size() > 0) {
            for (ProcessEntity process : processes.getResults()) {
                results.add(process.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateProcess(String validationTypeKey,
                                                      String processTypeKey,
                                                      ProcessInfo processInfo,
                                                       ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false)
    public ProcessInfo createProcess(String processKey,
                                     String processTypeKey,
                                     ProcessInfo processInfo,
                                      ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        ProcessEntity existing = processDao.find(processKey);
        if (existing != null) {
            throw new AlreadyExistsException(processKey);
        }

        processInfo.setKey(processKey);
        processInfo.setTypeKey(processTypeKey);
        ProcessEntity process = new ProcessEntity(processInfo);
        process.setEntityCreated(contextInfo);
        processDao.persist(process);

        ProcessEntity retrieved = processDao.find(processKey);

        return retrieved.toDto();
    }

    @Override
    @Transactional(readOnly = false)
    public ProcessInfo updateProcess(String processKey,
                                     ProcessInfo processInfo,
                                      ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {

        ProcessEntity processEntity = processDao.find(processKey);

        if (processEntity == null) {
            throw new DoesNotExistException(processKey);
        }

        ProcessEntity toUpdate = new ProcessEntity(processInfo);
        toUpdate.setProcessType(processInfo.getTypeKey());
        toUpdate.setEntityUpdated(contextInfo);
        processDao.merge(toUpdate);
        processDao.getEm().flush();
        return processDao.find(toUpdate.getId()).toDto();

    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteProcess(String processKey,
                                     ContextInfo contextInfo) throws DependentObjectsExistException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        ProcessEntity processEntity = processDao.find(processKey);
        StatusInfo status = new StatusInfo();

        if (processEntity == null) {
            status.setSuccess(false);
            return status;
        }

        List<InstructionEntity> instructionEntities = instructionDao.getByProcess(processKey);
        for (InstructionEntity instructionEntity : instructionEntities) {
            instructionDao.remove(instructionEntity);
        }

        processDao.remove(processEntity);

        status.setSuccess(true);
        return status;

    }

    @Override
    public CheckInfo getCheck(String checkId,
                               ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        CheckEntity checkEntity = checkDao.find(checkId);
        if (checkEntity == null) {
            throw new DoesNotExistException(checkId);
        }
        return checkEntity.toDto();
    }

    @Override
    public List<CheckInfo> getChecksByIds(List<String> checkIds,
                                           ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CheckEntity> checkEntities = checkDao.findByIds(checkIds);
        if (checkEntities == null || checkEntities.isEmpty()) {
            throw new DoesNotExistException("No records found");
        }
        List<CheckInfo> checkInfoList = new ArrayList<CheckInfo>();
        for (CheckEntity checkEntity : checkEntities) {
            if (checkEntity == null) {
                throw new DoesNotExistException();
            }
            checkInfoList.add(checkEntity.toDto());
        }
        return checkInfoList;
    }

    @Override
    public List<String> getCheckIdsByType(String checkTypeKey,
                                           ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CheckEntity> checkEntities = checkDao.getByCheckType(checkTypeKey);

        List<String> checkIds = new ArrayList<String>();
        for (CheckEntity processEntity : checkEntities) {
            checkIds.add(processEntity.getId());
        }

        return checkIds;
    }

    @Override
    public List<String> searchForCheckIds(QueryByCriteria criteria,
                                           ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<CheckEntity> checks = criteriaLookupService.lookup(CheckEntity.class, criteria);
        if (null != checks && checks.getResults().size() > 0) {
            for (CheckEntity check : checks.getResults()) {
                results.add(check.getId());
            }
        }
        return results;
    }

    @Override
    public List<CheckInfo> searchForChecks(QueryByCriteria criteria,
                                            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CheckInfo> results = new ArrayList<CheckInfo>();
        GenericQueryResults<CheckEntity> checks = criteriaLookupService.lookup(CheckEntity.class, criteria);
        if (null != checks && checks.getResults().size() > 0) {
            for (CheckEntity check : checks.getResults()) {
                results.add(check.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey,
                                                    String checkTypeKey,
                                                    CheckInfo checkInfo,
                                                     ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false)
    public CheckInfo createCheck(String checkTypeKey,
                                 CheckInfo checkInfo,  ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        checkInfo.setTypeKey(checkTypeKey);
        CheckEntity checkEntity = new CheckEntity(checkInfo);

        if (StringUtils.isNotBlank(checkInfo.getChildProcessKey())) {
            ProcessEntity process = processDao.find(checkInfo.getChildProcessKey());
            checkEntity.setChildProcessId(process.getId());
        } else {
            checkEntity.setChildProcessId(null);
        }
        checkEntity.setEntityCreated(contextInfo);
        checkDao.persist(checkEntity);
        return checkEntity.toDto();

    }

    @Override
    @Transactional(readOnly = false)
    public CheckInfo updateCheck(String checkId,
                                 CheckInfo checkInfo,  ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException,
            VersionMismatchException {

        CheckEntity exists = checkDao.find(checkId);
        if (exists == null) {
            throw new DoesNotExistException(checkId);
        }

        CheckEntity toUpdate = new CheckEntity(checkInfo);

        if (StringUtils.isNotBlank(checkInfo.getChildProcessKey())) {
            ProcessEntity process = processDao.find(checkInfo.getChildProcessKey());
            toUpdate.setChildProcessId(process.getId());
        } else {
            toUpdate.setChildProcessId(null);
        }
        toUpdate.setEntityUpdated(contextInfo);
        checkDao.merge(toUpdate);
        CheckEntity retrieved = checkDao.find(checkInfo.getId());
        return retrieved.toDto();

    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteCheck(String checkId,
                                   ContextInfo contextInfo) throws DependentObjectsExistException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        CheckEntity exists = checkDao.find(checkId);
        StatusInfo status = new StatusInfo();

        if (exists == null) {
            status.setSuccess(false);
            return status;
        }

        List<InstructionEntity> instructionEntities = instructionDao.getByCheck(checkId);
        for (InstructionEntity instructionEntity : instructionEntities) {
            instructionDao.remove(instructionEntity);
        }

        checkDao.remove(exists);
        status.setSuccess(true);
        return status;
    }

    @Override
    public InstructionInfo getInstruction(String instructionId,
                                           ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        InstructionEntity instructionEntity = instructionDao.find(instructionId);
        if (instructionEntity == null) {
            throw new DoesNotExistException(instructionId);
        }
        return instructionEntity.toDto();
    }

    @Override
    public List<InstructionInfo> getInstructionsByIds(List<String> instructionIds,
                                                       ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<InstructionEntity> instructionEntities = instructionDao.findByIds(instructionIds);
        if (instructionEntities == null || instructionEntities.isEmpty()) {
            throw new DoesNotExistException();
        }
        List<InstructionInfo> instructionInfos = new ArrayList<InstructionInfo>();

        for (InstructionEntity instructionEntity : instructionEntities) {
            if (instructionEntity == null) {
                throw new DoesNotExistException();
            }
            instructionInfos.add(instructionEntity.toDto());
        }
        return instructionInfos;
    }

    @Override
    public List<String> getInstructionIdsByType(String instructionTypeKey,
                                                 ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<InstructionEntity> instructionEntities = instructionDao.getByInstructionTypeId(instructionTypeKey);

        List<String> instructionIds = new ArrayList<String>();
        for (InstructionEntity instructionEntity : instructionEntities) {
            instructionIds.add(instructionEntity.getId());
        }

        return instructionIds;
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey,
                                                           ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<InstructionInfo> instructionInfos = new ArrayList<InstructionInfo>();
        List<InstructionEntity> instructionEntities = instructionDao.getByProcess(processKey);
        if (null != instructionEntities) {
            for (InstructionEntity instructionEntity : instructionEntities) {
                instructionInfos.add(instructionEntity.toDto());
            }
        }
        return instructionInfos;
    }

    @Override
    public List<InstructionInfo> getInstructionsByCheck(String checkId,
                                                         ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<InstructionEntity> instructionEntities = instructionDao.getByCheck(checkId);
        List<InstructionInfo> instructionInfoList = new ArrayList<InstructionInfo>();

        for (InstructionEntity instructionEntity : instructionEntities) {
            instructionInfoList.add(instructionEntity.toDto());
        }

        return instructionInfoList;
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcessAndCheck(String checkId,
                                                                  String processKey,
                                                                   ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<InstructionEntity> instructionEntities = instructionDao.getByProcessAndCheck(processKey, checkId);
        List<InstructionInfo> instructionInfoList = new ArrayList<InstructionInfo>();

        for (InstructionEntity instructionEntity : instructionEntities) {
            instructionInfoList.add(instructionEntity.toDto());
        }

        return instructionInfoList;
    }

    @Override
    public List<String> searchForInstructionIds(QueryByCriteria criteria,
                                                 ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<InstructionEntity> instructions = criteriaLookupService.lookup(InstructionEntity.class, criteria);
        if (null != instructions && instructions.getResults().size() > 0) {
            for (InstructionEntity instruction : instructions.getResults()) {
                results.add(instruction.getId());
            }
        }
        return results;
    }

    @Override
    public List<InstructionInfo> searchForInstructions(QueryByCriteria criteria,
                                                        ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> results = new ArrayList<InstructionInfo>();
        GenericQueryResults<InstructionEntity> instructions = criteriaLookupService.lookup(InstructionEntity.class, criteria);
        if (null != instructions && instructions.getResults().size() > 0) {
            for (InstructionEntity instruction : instructions.getResults()) {
                results.add(instruction.toDto());
            }
        }
        return results;
    }

    @Override
    public List<ValidationResultInfo> validateInstruction(
            String validationTypeKey,
            String processKey,
            String checkId,
            String instructionTypeKey,
            InstructionInfo instructionInfo,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    @Transactional(readOnly = false)
    public InstructionInfo createInstruction(String processKey,
                                             String checkId, String instructionTypeKey,
                                             InstructionInfo instructionInfo,
                                              ContextInfo contextInfo) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException,
            ReadOnlyException {

        instructionInfo.setProcessKey(processKey);
        instructionInfo.setCheckId(checkId);
        instructionInfo.setTypeKey(instructionTypeKey);
        InstructionEntity instruction = new InstructionEntity(instructionInfo);

        List<String> appliedAtpTypes = instructionInfo.getAppliedAtpTypeKeys();
        if (appliedAtpTypes.contains(null)) {
            throw new InvalidParameterException("Could not find all appliedAtpTypes: "
                    + instructionInfo.getAppliedAtpTypeKeys());
        }
        instruction.setAppliedAtpTypes(appliedAtpTypes);

        ProcessEntity process = processDao.find(processKey);
        if (null == process) {
            throw new InvalidParameterException("processKey");
        }
        instruction.setProcessId(process.getId());

        CheckEntity check = checkDao.find(checkId);
        if (null == check) {
            throw new InvalidParameterException("checkId");
        }
        instruction.setCheckId(check.getId());
        instruction.setEntityCreated(contextInfo);
        instructionDao.persist(instruction);
        return instruction.toDto();
    }

    @Override
    @Transactional(readOnly = false)
    public InstructionInfo updateInstruction(String instructionId,
                                             InstructionInfo instructionInfo,
                                              ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        InstructionEntity exists = instructionDao.find(instructionId);
        if (exists == null) {
            throw new DoesNotExistException(instructionId);
        }

        InstructionEntity toUpdate = new InstructionEntity(instructionInfo);

        List<String> appliedAtpTypes = instructionInfo.getAppliedAtpTypeKeys();
        if (appliedAtpTypes.contains(null)) {
            throw new InvalidParameterException("Could not find all appliedAtpTypes: "
                    + instructionInfo.getAppliedAtpTypeKeys());
        }
        toUpdate.setAppliedAtpTypes(appliedAtpTypes);

        ProcessEntity process = processDao.find(instructionInfo.getProcessKey());
        if (null == process) {
            toUpdate.setProcessId(null);
        } else {
            toUpdate.setProcessId(process.getId());
        }

        CheckEntity check = checkDao.find(instructionInfo.getCheckId());
        if (null == check) {
            throw new InvalidParameterException("Instruction checkId");
        }
        toUpdate.setCheckId(check.getId());
        toUpdate.setEntityUpdated(contextInfo);
        instructionDao.merge(toUpdate);

        return instructionDao.find(instructionId).toDto();

    }


    @Override
    @Transactional(readOnly = false)
    public StatusInfo reorderInstructions(String processKey,
                                          List<String> instructionIds,
                                          ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> allInstructions = this.getInstructionsByProcess(processKey, contextInfo);
        Set<String> remainingInstructionIds = new LinkedHashSet<String>();
        for (InstructionInfo instr : allInstructions) {
            remainingInstructionIds.add(instr.getId());
        }
        // copy so we don't modify the list
        List<String> orderedInstructionIds = new ArrayList(instructionIds);
        for (String id : instructionIds) {
            if (!remainingInstructionIds.remove(id)) {
                throw new InvalidParameterException(id + " is not an instruction for the specified process");
            }
        }
        orderedInstructionIds.addAll(remainingInstructionIds);
        // update the position 
        for (int i = 0; i < orderedInstructionIds.size(); i++) {
            String instructionId = orderedInstructionIds.get(i);
            InstructionInfo instr = this.getInstruction(instructionId, contextInfo);
            instr.setPosition(i);
            try {
                this.updateInstruction(instructionId, instr, contextInfo);
            } catch (ReadOnlyException ex) {
                throw new OperationFailedException("unexpected", ex);
            } catch (VersionMismatchException ex) {
                throw new OperationFailedException("unexpected", ex);
            }
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(true);
        return status;
    }

    @Override
    @Transactional(readOnly = false)
    public StatusInfo deleteInstruction(String instructionId,
                                         ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        InstructionEntity exists = instructionDao.find(instructionId);
        StatusInfo status = new StatusInfo();

        if (exists == null) {
            status.setSuccess(false);
            return status;
        }

        instructionDao.remove(exists);

        status.setSuccess(true);
        return status;
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey,
                                                               ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> instructions = getInstructionsByProcess(processKey, contextInfo);
        for (InstructionInfo instruction : instructions) {
            if (!ProcessServiceConstants.PROCESS_ACTIVE_STATE_KEY.equals(instruction.getStateKey())) {
                // remove non-active
                instructions.remove(instruction);
            } else if (!isInstructionCurrent(instruction, contextInfo)) {
                // remove non-current
                instructions.remove(instruction);
            }
        }

        // order instructions
        Collections.sort(instructions, new Comparator<InstructionInfo>() {
            @Override
            public int compare(InstructionInfo instruction1, InstructionInfo instruction2) {
                return instruction1.getPosition().compareTo(instruction2.getPosition());
            }
        });

        return instructions;
    }

    /*private StateEntity findState(String processKey, String stateKey, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        try {
            StateInfo state = stateService.getState(stateKey, context);
            if (null == state) {
                throw new OperationFailedException("The state does not exist. processKey " + processKey
                        + " and stateKey: " + stateKey);
            }
            return new StateEntity(state);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("The state does not exist. processKey " + processKey + " and stateKey: "
                    + stateKey);
        }
    }*/

    private boolean isInstructionCurrent(InstructionInfo instruction, ContextInfo contextInfo)
            throws InvalidParameterException {
        boolean result = false;
        Date effectiveDate = instruction.getEffectiveDate();
        Date expirationDate = instruction.getExpirationDate();
        Date currentDate = Calendar.getInstance().getTime(); // TODO incorporate
        // context

        if (null == effectiveDate) { // TODO move to validation decorator?
            throw new InvalidParameterException("Instruction does not have an effective date.");
        }

        if (!effectiveDate.after(currentDate)) {
            if (null == expirationDate) {
                result = true;
            } else if (!expirationDate.before(currentDate)) {
                result = true;
            }
        }

        return result;
    }

}
