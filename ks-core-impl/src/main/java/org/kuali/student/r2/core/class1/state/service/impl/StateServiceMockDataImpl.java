/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.state.service.impl;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateChangeInfo;
import org.kuali.student.r2.core.class1.state.dto.StateConstraintInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.dto.StatePropagationInfo;

import javax.jws.WebParam;
import java.util.List;


/**
 *  This uses the State Service mock implementation to nail up some
 *  example data.
 */

public class StateServiceMockDataImpl 
    extends StateServiceMockImpl {

    public StateServiceMockDataImpl()
        throws Exception {

        addLifecycle("kuali.atp.process", "ATP Lifecycle", "Lifecycle process for Academic Time Periods.", "");
        addState("kuali.atp.process", "kuali.atp.state.Draft", "Draft", "Indicates that this ATP is tentative.");
        addState("kuali.atp.process", "kuali.atp.state.Official", "Official", "Indicates that this ATP has been established.");

        addLifecycle("kuali.milestone.process", "Milestone Lifecycle", "Lifecycle process for Milestones.", "");
        addState("kuali.milestone.process", "kuali.milestone.state.Draft", "Draft", "Indicates that this Milestone is tentative.");
        addState("kuali.milestone.process", "kuali.milestone.state.Official", "Official", "Indicates that this Milestone has been established.");        
    }

    protected void addLifecycle(String key, String name, String desc, String ref)
        throws Exception {

        LifecycleInfo lifecycle = new LifecycleInfo();
        lifecycle.setName(name);

        RichTextInfo rtDesc = new RichTextInfo();
        rtDesc.setPlain(desc);
        rtDesc.setFormatted(desc);
        lifecycle.setDescr(rtDesc);

        lifecycle.setRefObjectUri(ref);

        createLifecycle(key, lifecycle, new ContextInfo());
    }

    protected void addState(String lifecycleKey, String key, String name, String desc)
        throws Exception {

        StateInfo state = new StateInfo();
        state.setName(name);

        RichTextInfo rtDesc = new RichTextInfo();
        rtDesc.setPlain(desc);
        rtDesc.setFormatted(desc);
        state.setDescr(rtDesc);

        createState(lifecycleKey, key, state, new ContextInfo());
    }

    @Override
    public StateChangeInfo getStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByIds(@WebParam(name = "stateChangeIds") List<String> stateChangeIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStateChangeIdsByType(@WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromState(@WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByToState(@WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromStateAndToState(@WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStateChangeIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> searchForStateChanges(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStateChange(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateChangeInfo createStateChange(@WebParam(name = "toStateKey") String toStateKey, @WebParam(name = "fromStateKey") String fromStateKey, @WebParam(name = "stateChangeTypeKey") String stateChangeTypeKey, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateChangeInfo updateStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "stateChangeInfo") StateChangeInfo stateChangeInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStateChange(@WebParam(name = "stateChangeId") String stateChangeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo getStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateConstraintInfo> getStateConstraintsByIds(@WebParam(name = "stateConstraintIds") List<String> stateConstraintIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStateConstraintIdsByType(@WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStateConstraintIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateConstraintInfo> searchForStateConstraints(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStateConstraint(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo createStateConstraint(@WebParam(name = "stateConstraintTypeKey") String stateConstraintTypeKey, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo updateStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "stateConstraintInfo") StateConstraintInfo stateConstraintInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStateConstraint(@WebParam(name = "stateConstraintId") String stateConstraintId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo getStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByIds(@WebParam(name = "statePropagationIds") List<String> statePropagationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStatePropagationIdsByType(@WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByTargetState(@WebParam(name = "targetStateId") String targetStateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStatePropagationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> searchForStatePropagations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStatePropagation(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "targetStateChangeId") String targetStateChangeId, @WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo createStatePropagation(@WebParam(name = "targetStateChangeId") String targetStateChangeId, @WebParam(name = "statePropagationTypeKey") String statePropagationTypeKey, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo updateStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "statePropagationInfo") StatePropagationInfo statePropagationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStatePropagation(@WebParam(name = "statePropagationId") String statePropagationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }
}
