/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * Created by Mezba Mahtab on 6/19/12
 */
package org.kuali.student.r2.core.process.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;

import java.util.*;

/**
 * This class is a mock implementation of ProcessService
 *
 * @author Kuali Student Team
 */
public class ProcessServiceMockImpl
        implements ProcessService, MockService {

    ///////////////////////////
    // Data Variables
    ///////////////////////////
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order, key: process category id
    private Map<String, ProcessCategoryInfo> processCategoryMap = new LinkedHashMap<String, ProcessCategoryInfo>();
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ProcessInfo> processMap = new LinkedHashMap<String, ProcessInfo>();
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, CheckInfo> checkMap = new LinkedHashMap<String, CheckInfo>();
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, InstructionInfo> instructionMap = new LinkedHashMap<String, InstructionInfo>();
    // stores mapping betweeen process and process category. the key is the process key, the list is the process categories for that process key
    private Map<String, Set<String>> processCategoriesForProcessMap = new LinkedHashMap<String, Set<String>>();

    ////////////////////////////////
    // FUNCTIONALS
    ////////////////////////////////
    
    
    @Override
    public ProcessCategoryInfo getProcessCategory(String processCategoryId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.processCategoryMap.containsKey(processCategoryId)) {
            throw new DoesNotExistException(processCategoryId);
        }
        return this.processCategoryMap.get(processCategoryId);
    }

    @Override
	public void clear() {
		this.checkMap.clear();
		this.instructionMap.clear();
		this.processCategoriesForProcessMap.clear();
		this.processCategoryMap.clear();
		this.processMap.clear();

	}

	@Override
    public List<ProcessCategoryInfo> getProcessCategoriesByIds(List<String> processCategoryIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ProcessCategoryInfo> list = new ArrayList<ProcessCategoryInfo>();
        for (String id : processCategoryIds) {
            list.add(this.getProcessCategory(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getProcessCategoryIdsByType(String processTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ProcessCategoryInfo info : processCategoryMap.values()) {
            if (processTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<ProcessCategoryInfo> getProcessCategoriesForProcess(String processKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.processCategoriesForProcessMap.containsKey(processKey)) {
            return new ArrayList<ProcessCategoryInfo>();
        }
        List<ProcessCategoryInfo> list = new ArrayList<ProcessCategoryInfo>();
        for (String categoryId : processCategoriesForProcessMap.get(processKey)) {
            try {
                list.add(this.getProcessCategory(categoryId, contextInfo));
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException("unexpected", ex);
            }
        }
        return list;
    }

    @Override
    public List<String> searchForProcessCategoryIds(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForProcessCategoryIds has not been implemented");
    }

    @Override
    public List<ProcessCategoryInfo> searchForProcessCategories(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForProcessCategories has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateProcessCategory(String validationTypeKey,
            String processCategoryTypeKey,
            ProcessCategoryInfo processCategoryInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ProcessCategoryInfo createProcessCategory(String processCategoryTypeKey,
            ProcessCategoryInfo processCategoryInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create
        if (!processCategoryTypeKey.equals(processCategoryInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        ProcessCategoryInfo copy = new ProcessCategoryInfo(processCategoryInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        if (processCategoryMap.containsKey(copy.getId())) {
            throw new OperationFailedException("processCategoryId " + copy.getId() + " already exists");
        }
        processCategoryMap.put(copy.getId(), copy);
        return new ProcessCategoryInfo(copy);
    }

    @Override
    public ProcessCategoryInfo updateProcessCategory(String processCategoryId,
            ProcessCategoryInfo processCategoryInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        if (!processCategoryId.equals(processCategoryInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ProcessCategoryInfo copy = new ProcessCategoryInfo(processCategoryInfo);
        ProcessCategoryInfo old = this.getProcessCategory(processCategoryInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.processCategoryMap.put(processCategoryInfo.getId(), copy);
        return new ProcessCategoryInfo(copy);
    }

    @Override
    public StatusInfo deleteProcessCategory(String processCategoryId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            DependentObjectsExistException {
        List<ProcessInfo> list = this.getProcessesForProcessCategory(processCategoryId, contextInfo);
        if (!list.isEmpty()) {
            throw new DependentObjectsExistException(list.size() + " processes are connected to this category");
        }
        if (this.processCategoryMap.remove(processCategoryId) == null) {
            throw new DoesNotExistException(processCategoryId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo addProcessToProcessCategory(String processKey,
            String processCategoryId,
            ContextInfo contextInfo)
            throws AlreadyExistsException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // check ids/keys exist
        ProcessCategoryInfo categoryInfo = this.getProcessCategory(processCategoryId, contextInfo);
        ProcessInfo processInfo = this.getProcess(processKey, contextInfo);
        // get the set of ids
        Set<String> processCategoryIds = processCategoriesForProcessMap.get(processKey);
        if (processCategoryIds == null) {
            processCategoryIds = new LinkedHashSet<String>();
            processCategoriesForProcessMap.put(processKey, processCategoryIds);
        }
        if (!processCategoryIds.add(processCategoryId)) {
            throw new AlreadyExistsException(processCategoryId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo removeProcessFromProcessCategory(String processKey,
            String processCategoryId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // check ids/keys exist
        ProcessCategoryInfo categoryInfo = this.getProcessCategory(processCategoryId, contextInfo);
        ProcessInfo processInfo = this.getProcess(processKey, contextInfo);
        // get the set of ids
        Set<String> processCategoryIds = processCategoriesForProcessMap.get(processKey);
        if (processCategoryIds == null) {
            processCategoryIds = new LinkedHashSet<String>();
            processCategoriesForProcessMap.put(processKey, processCategoryIds);
        }
        if (!processCategoryIds.remove(processCategoryId)) {
            throw new DoesNotExistException(processCategoryId); // this process category not found for process
        }
        return newStatus();
    }

    @Override
    public ProcessInfo getProcess(String processKey,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.processMap.containsKey(processKey)) {
            throw new DoesNotExistException(processKey);
        }
        return processMap.get(processKey);
    }

    @Override
    public List<ProcessInfo> getProcessesByKeys(List<String> processKeys,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ProcessInfo> list = new ArrayList<ProcessInfo>();
        for (String processKey : processKeys) {
            list.add(getProcess(processKey, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getProcessKeysByType(String processTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (ProcessInfo processInfo : processMap.values()) {
            if (processTypeKey.equals(processInfo.getTypeKey())) {
                list.add(processInfo.getKey());
            }
        }
        return list;
    }

    @Override
    public List<ProcessInfo> getProcessesForProcessCategory(String processCategoryId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
       List<ProcessInfo> list = new ArrayList<ProcessInfo>();
        ProcessCategoryInfo info = this.processCategoryMap.get(processCategoryId);
        if (info == null) {
            return list;
        }
        for (String processKey : processCategoriesForProcessMap.keySet()) {
            Set<String> categoryIds = processCategoriesForProcessMap.get(processKey);
            if (categoryIds.contains(processCategoryId)) {
                try {
                    list.add(this.getProcess(processKey, contextInfo));
                } catch (DoesNotExistException ex) {
                    throw new OperationFailedException ("unexpected", ex);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForProcessKeys(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForProcessKeys has not been implemented");
    }

    @Override
    public List<ProcessInfo> searchForProcess(QueryByCriteria criteria,
                                              ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForProcess has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateProcess(String validationTypeKey,
            String processTypeKey,
            ProcessInfo processInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ProcessInfo createProcess(String processKey,
            String processTypeKey,
            ProcessInfo processInfo,
            ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create
        if (!processKey.equals(processInfo.getKey())) {
            throw new InvalidParameterException("The key parameter does not match the key on the info object");
        }
        if (!processTypeKey.equals(processInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        ProcessInfo copy = new ProcessInfo(processInfo);
        if (copy.getKey() == null) {
            copy.setKey(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        if (processMap.containsKey(copy.getKey())) {
            throw new AlreadyExistsException("process key" + copy.getKey() + " already exists");
        }
        processMap.put(copy.getKey(), copy);
        return new ProcessInfo(copy);
    }

    @Override
    public ProcessInfo updateProcess(String processKey,
            ProcessInfo processInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        if (!processKey.equals(processInfo.getKey())) {
            throw new InvalidParameterException("The key parameter does not match the key on the info object");
        }
        ProcessInfo copy = new ProcessInfo(processInfo);
        ProcessInfo old = this.getProcess(processInfo.getKey(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.processMap.put(processInfo.getKey(), copy);
        return new ProcessInfo(copy);
    }

    @Override
    public StatusInfo deleteProcess(String processKey,
            ContextInfo contextInfo)
            throws DependentObjectsExistException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<ProcessCategoryInfo> categs = this.getProcessCategoriesForProcess(processKey, contextInfo);
        if (!categs.isEmpty()) {
            throw new DependentObjectsExistException(categs.size() + " process categories are connected to this process");
        }
        List<InstructionInfo> instructions = this.getInstructionsByProcess(processKey, contextInfo);
        if (!instructions.isEmpty()) {
            throw new DependentObjectsExistException(instructions.size() + " instructions are connected to this process");
        }
        if (this.processMap.remove(processKey) == null) {
            throw new DoesNotExistException(processKey);
        }
        return newStatus();
    }

    @Override
    public CheckInfo getCheck(String checkId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.checkMap.containsKey(checkId)) {
            throw new DoesNotExistException(checkId);
        }
        return this.checkMap.get(checkId);
    }

    @Override
    public List<CheckInfo> getChecksByIds(List<String> checkIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<CheckInfo> list = new ArrayList<CheckInfo>();
        for (String id : checkIds) {
            list.add(this.getCheck(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getCheckIdsByType(String checkTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CheckInfo checkInfo : checkMap.values()) {
            if (checkTypeKey.equals(checkInfo.getTypeKey())) {
                list.add(checkInfo.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForCheckIds(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForCheckIds has not been implemented");
    }

    @Override
    public List<CheckInfo> searchForChecks(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForChecks has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateCheck(String validationTypeKey,
            String checkTypeKey,
            CheckInfo checkInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public CheckInfo createCheck(String checkTypeKey,
            CheckInfo checkInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create
        if (!checkTypeKey.equals(checkInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        CheckInfo copy = new CheckInfo(checkInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        if (checkMap.containsKey(copy.getId())) {
            throw new OperationFailedException("check id" + copy.getId() + " already exists");
        }
        checkMap.put(copy.getId(), copy);
        return new CheckInfo(copy);
    }

    @Override
    public CheckInfo updateCheck(String checkId,
            CheckInfo checkInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        if (!checkId.equals(checkInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CheckInfo copy = new CheckInfo(checkInfo);
        CheckInfo old = this.getCheck(checkInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.checkMap.put(checkInfo.getId(), copy);
        return new CheckInfo(copy);
    }

    @Override
    public StatusInfo deleteCheck(String checkId,
            ContextInfo contextInfo)
            throws DependentObjectsExistException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> instructions = this.getInstructionsByCheck(checkId, contextInfo);
        if (!instructions.isEmpty()) {
            throw new DependentObjectsExistException(instructions.size() + " instructions are connected to this check");
        }
        if (this.checkMap.remove(checkId) == null) {
            throw new DoesNotExistException(checkId);
        }
        return newStatus();
    }

    @Override
    public InstructionInfo getInstruction(String instructionId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (!this.instructionMap.containsKey(instructionId)) {
            throw new DoesNotExistException(instructionId);
        }
        return this.instructionMap.get(instructionId);
    }

    @Override
    public List<InstructionInfo> getInstructionsByIds(List<String> instructionIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (String id : instructionIds) {
            list.add(this.getInstruction(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getInstructionIdsByType(String instructionTypeKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (InstructionInfo info : instructionMap.values()) {
            if (instructionTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcess(String processKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (InstructionInfo info : instructionMap.values()) {
            if (processKey.equals(info.getProcessKey())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsByCheck(String checkId,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (InstructionInfo info : instructionMap.values()) {
            if (checkId.equals(info.getCheckId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<InstructionInfo> getInstructionsByProcessAndCheck(String processKey,
            String checkId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<InstructionInfo> list = new ArrayList<InstructionInfo>();
        for (InstructionInfo info : instructionMap.values()) {
            if (processKey.equals(info.getProcessKey())) {
                if (checkId.equals(info.getCheckId())) {
                    list.add(info);
                }
            }
        }
        if (list.size() == 0) {
            throw new DoesNotExistException("No instructions found for process / check relation.");
        }
        return list;
    }

    @Override
    public List<String> searchForInstructionIds(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForInstructionIds has not been implemented");
    }

    @Override
    public List<InstructionInfo> searchForInstructions(QueryByCriteria criteria,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("searchForInstructions has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateInstruction(String validationTypeKey,
            String processKey,
            String checkId,
            String instructionTypeKey,
            InstructionInfo instructionInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public InstructionInfo createInstruction(String processKey,
            String checkId,
            String instructionTypeKey,
            InstructionInfo instructionInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // create
        if (!instructionTypeKey.equals(instructionInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        InstructionInfo copy = new InstructionInfo(instructionInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        if (instructionMap.containsKey(copy.getId())) {
            throw new OperationFailedException("instruction id " + copy.getId() + " already exists");
        }
        instructionMap.put(copy.getId(), copy);
        return new InstructionInfo(copy);
    }

    @Override
    public InstructionInfo updateInstruction(String instructionId,
            InstructionInfo instructionInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // update
        if (!instructionId.equals(instructionInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        InstructionInfo copy = new InstructionInfo(instructionInfo);
        InstructionInfo old = this.getInstruction(instructionInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.instructionMap.put(instructionInfo.getId(), copy);
        return new InstructionInfo(copy);
    }

    @Override
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
        return newStatus();
    }

    @Override
    public StatusInfo deleteInstruction(String instructionId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        if (this.instructionMap.remove(instructionId) == null) {
            throw new DoesNotExistException(instructionId);
        }
        return newStatus();
    }

    @Override
    public List<InstructionInfo> getInstructionsForEvaluation(String processKey,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
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
            public int compare(InstructionInfo instruction1,
                    InstructionInfo instruction2) {
                return instruction1.getPosition().compareTo(instruction2.getPosition());
            }
        });

        return instructions;
    }

    // copied from ProcessServiceImpl
    private boolean isInstructionCurrent(InstructionInfo instruction,
            ContextInfo contextInfo)
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

    private MetaInfo updateMeta(MetaInfo old,
            ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
