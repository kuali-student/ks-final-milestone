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
 * Created by Mezba Mahtab on 6/21/12
 */
package org.kuali.student.r2.core.process.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;
import org.kuali.student.r2.core.process.dto.ProcessCategoryInfo;
import org.kuali.student.r2.core.process.dto.ProcessInfo;
import org.kuali.student.r2.core.process.service.ProcessService;

import java.util.Date;
import java.util.List;

/**
 * This class loads Process Service data
 *
 * @author Mezba Mahtab
 */
public class ProcessServiceDataLoader {

    ///////////////////
    // DATA FIELDS
    ///////////////////

    private  String principalId = ProcessServiceDataLoader.class.getSimpleName();
    private ProcessService processService = null;
    private boolean debugMode = true;
    private static Logger logger = null;

    ////////////////////
    // CONSTRUCTORS
    ////////////////////

    public ProcessServiceDataLoader (ProcessService processService, boolean debugMode, Logger logger){
        this.processService = processService;
        this.debugMode = debugMode;
        this.logger = logger;
    }

    ////////////////////
    // FUNCTIONALS
    ////////////////////

    public void loadData ()  {
        if (debugMode) { logger.warn("loadData called"); }

        // create the context
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, true);
    }

    private void loadProcessCategory (String categoryId, String type, String state, String name,
                                      String descriptionPlain, String descriptionFormatted, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ProcessCategoryInfo info = new ProcessCategoryInfo();
        info.setId(categoryId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setName(name);
        info.setDescr(new RichTextInfo(descriptionPlain, descriptionFormatted));
        processService.createProcessCategory(type, info, contextInfo);
    }

    private void loadProcess (String processId, String type, String state, String name,
                              String descriptionPlain, String descriptionFormatted,
                              String categoryId, ContextInfo contextInfo)
            throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ProcessInfo info = new ProcessInfo();
        info.setKey(processId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setName(name);
        info.setDescr(new RichTextInfo(descriptionPlain, descriptionFormatted));
        processService.createProcess(processId, type, info, contextInfo);
        processService.addProcessToProcessCategory(processId, categoryId, contextInfo);
    }

    private void loadCheck (String checkId, String type, String state, String name, String descriptionPlain, String descriptionFormatted,
                            String issueId,String milestoneType, String agendaId, String checkRightAgendaId,
                            String checkLeftAgendaId, String checkChildProcessId, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CheckInfo info = new CheckInfo();
        info.setId(checkId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setName(name);
        info.setDescr(new RichTextInfo(descriptionPlain, descriptionFormatted));
        info.setIssueId(issueId);
        info.setMilestoneTypeKey(milestoneType);
        info.setAgendaId(agendaId);
        info.setRightComparisonAgendaId(checkRightAgendaId);
        info.setLeftComparisonAgendaId(checkLeftAgendaId);
        info.setChildProcessKey(checkChildProcessId);
        processService.createCheck(type, info, contextInfo);
    }

    private void loadInstruction (String instructionId, String type, String state,
                                  Date effectiveDate, Date expirationDate,
                                  String processId, String checkId, String appliedPopulationId,
                                  String messagePlain, String messageFormatted, int position,
                                  boolean warning, boolean continueOnFail, boolean exemptible,
                                  List<String> appliedAtpTypes, ContextInfo contextInfo)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        InstructionInfo info = new InstructionInfo();
        info.setId(instructionId);
        info.setTypeKey(type);
        info.setStateKey(state);
        info.setEffectiveDate(effectiveDate);
        info.setExpirationDate(expirationDate);
        info.setProcessKey(processId);
        info.setCheckKey(checkId);
        info.setAppliedPopulationKey(appliedPopulationId);
        info.setMessage(new RichTextInfo(messagePlain, messageFormatted));
        info.setPosition(position);
        info.setIsWarning(warning);
        info.setContinueOnFail(continueOnFail);
        info.setIsExemptible(exemptible);
        info.setAppliedAtpTypeKeys(appliedAtpTypes);
        processService.createInstruction(processId, checkId, type, info, contextInfo);
    }
}