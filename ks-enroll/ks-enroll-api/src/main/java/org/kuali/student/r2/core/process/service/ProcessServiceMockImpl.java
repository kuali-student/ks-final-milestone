/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;

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
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;

/**
 *
 * @author nwright
 */
public class ProcessServiceMockImpl implements ProcessService {

    private Map<String, CheckInfo> checks = new HashMap<String, CheckInfo>();
    private Map<String, ProcessInfo> processes = new HashMap<String, ProcessInfo>();
    private Map<String, InstructionInfo> instructions = new HashMap<String, InstructionInfo>();
//    private Map<String, ProcessCategoryInfo> categories = new HashMap<String,ProcessCategoryInfo> ();
//    private Map<String, String> processCategoryMap = new HashMap<String,String> ();    

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CheckInfo createCheck(String checkKey, CheckInfo checkInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (checks.containsKey(checkInfo.getKey())) {
            throw new AlreadyExistsException(checkInfo.getKey());
        }
        CheckInfo copy = new CheckInfo(checkInfo);
        copy.setMeta(newMeta(contextInfo));
        checks.put(copy.getKey(), copy);
        return new CheckInfo(copy);
    }

    @Override
    public InstructionInfo createInstruction(String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        InstructionInfo copy = new InstructionInfo(instructionInfo);
        copy.setId(instructions.size() + "");
        copy.setMeta(newMeta(contextInfo));
        instructions.put(copy.getId(), copy);
        return new InstructionInfo(copy);
    }

    @Override
    public ProcessInfo createProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (processes.containsKey(processInfo.getKey())) {
            throw new AlreadyExistsException(processInfo.getKey());
        }
        ProcessInfo copy = new ProcessInfo(processInfo);
        copy.setMeta(newMeta(contextInfo));
        processes.put(copy.getKey(), copy);
        return new ProcessInfo(copy);
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(ProcessCategoryInfo processInfo, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo deleteCheck(String checkKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.checks.remove(checkKey) == null) {
            throw new DoesNotExistException(checkKey);
        }
        return newStatus();
    }

    @Override
    public StatusInfo deleteInstruction(String instructionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.instructions.remove(instructionId) == null) {
            throw new DoesNotExistException(instructionId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo deleteProcess(String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (this.processes.remove(processKey) == null) {
            throw new DoesNotExistException(processKey);
        }
        return newStatus();
    }

    @Override
    public StatusInfo deleteProcessCategory(String processCategoryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CheckInfo getCheck(String checkKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CheckInfo info = this.checks.get(checkKey);
        if (info == null) {
            throw new DoesNotExistException(checkKey);
        }
        return new CheckInfo(info);
    }

    @Override
    public List<String> getCheckKeysByType(String checkTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CheckInfo> getChecksByIds(List<String> checkKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public InstructionInfo getInstruction(String instructionId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        InstructionInfo info = this.instructions.get(instructionId);
        if (info == null) {
            throw new DoesNotExistException(instructionId);
        }
        return new InstructionInfo(info);
    }

    @Override
    public List<String> getInstructionIdsByType(String instructionTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (InstructionInfo info : this.instructions.values()) {
            if (info.getTypeKey().equals(instructionTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsByCheck(String checkKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (InstructionInfo info : this.instructions.values()) {
            if (info.getCheckKey().equals(checkKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsByIds(List<String> instructionIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (String id : instructionIds) {
            InstructionInfo info = this.getInstruction(id, contextInfo);
            list.add(info);
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (InstructionInfo info : this.instructions.values()) {
            if (info.getProcessKey().equals(processKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcessAndCheck(String checkKey, String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (InstructionInfo info : this.getInstructionsByCheck(checkKey, contextInfo)) {
            if (info.getProcessKey().equals(processKey)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (InstructionInfo info : this.getInstructionsByProcess(processKey, contextInfo)) {
            if (info.getStateKey().equals(ProcessServiceConstants.INSTRUCTION_ENABLED_STATE_KEY)) {
                list.add(info);
            }
        }
        Collections.sort(list, new PositionComparator ());
        return list;
    }

    private static class PositionComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            InstructionInfo info1 = (InstructionInfo) o1;
            
            InstructionInfo info2 = (InstructionInfo) o2;
            Integer i1 = info1.getPosition();
            if (i1 == null) {
                i1 = 0;
            }
            Integer i2 = info2.getPosition();
            if (i2 == null) {
                i2 = 0;
            }
            return i1.compareTo(i2);
        }   
    }
    
    @Override
    public ProcessInfo getProcess(String processKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProcessInfo info = this.processes.get(processKey);
        if (info == null) {
            throw new DoesNotExistException(processKey);
        }
        return new ProcessInfo(info);
    }

    @Override
    public List<ProcessCategoryInfo> getProcessCategoriesByIds(List<String> processCategoryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ProcessCategoryInfo> getProcessCategoriesForProcess(String processKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProcessCategoryInfo getProcessCategory(String processCategoryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getProcessCategoryIdsByType(String processTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getProcessKeysByType(String processTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ProcessInfo info : this.processes.values()) {
            if (info.getTypeKey().equals(processTypeKey)) {
                list.add(info.getKey());
            }
        }
        return list;
    }

    @Override
    public List<ProcessInfo> getProcessesByKeys(List<String> processKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ProcessInfo> list = new ArrayList<ProcessInfo>();
        for (String key : processKeys) {
            ProcessInfo info = this.getProcess(key, contextInfo);
            list.add(info);
        }
        return list;
    }

    @Override
    public List<ProcessInfo> getProcessesForProcessCategory(String processCategoryId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo removeProcessFromProcessCategory(String processKey, String processCategoryId, ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForCheckKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CheckInfo> searchForChecks(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForInstructionIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<InstructionInfo> searchForInstructions(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ProcessCategoryInfo> searchForProcessCategories(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForProcessCategoryIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForProcessKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ProcessInfo> searchForProcesss(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CheckInfo updateCheck(String checkKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        CheckInfo copy = new CheckInfo(checkInfo);
        CheckInfo old = this.getCheck(checkKey, contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.checks.put(checkInfo.getKey(), copy);
        return new CheckInfo(copy);
    }

    @Override
    public InstructionInfo updateInstruction(String instructionId, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        InstructionInfo copy = new InstructionInfo(instructionInfo);
        InstructionInfo old = this.getInstruction(instructionId, contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.instructions.put(instructionInfo.getId(), copy);
        return new InstructionInfo(copy);
    }

    @Override
    public ProcessInfo updateProcess(String processKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        ProcessInfo copy = new ProcessInfo(processInfo);
        ProcessInfo old = this.getProcess(processKey, contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.processes.put(processInfo.getKey(), copy);
        return new ProcessInfo(copy);
    }

    @Override
    public ProcessCategoryInfo updateProcessCategory(String processCategoryId, ProcessCategoryInfo processInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey, CheckInfo checkInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateInstruction(String validationTypeKey, String processKey, String checkKey, InstructionInfo instructionInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateProcess(String validationTypeKey, ProcessInfo processInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateProcessCategory(String validationTypeKey, ProcessCategoryInfo processCategoryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

 
}
