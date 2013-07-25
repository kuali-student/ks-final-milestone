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
    public StateChangeInfo getStateChange( String stateChangeId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByIds( List<String> stateChangeIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStateChangeIdsByType( String stateChangeTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromState( String fromStateKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByToState( String toStateKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> getStateChangesByFromStateAndToState(String fromStateKey,  String toStateKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStateChangeIds( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateChangeInfo> searchForStateChanges( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStateChange( String validationTypeKey, String toStateKey,  String fromStateKey,String stateChangeTypeKey,  StateChangeInfo stateChangeInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateChangeInfo createStateChange(String toStateKey,String fromStateKey, String stateChangeTypeKey,  StateChangeInfo stateChangeInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateChangeInfo updateStateChange(String stateChangeId, StateChangeInfo stateChangeInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStateChange(String stateChangeId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo getStateConstraint( String stateConstraintId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateConstraintInfo> getStateConstraintsByIds( List<String> stateConstraintIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStateConstraintIdsByType( String stateConstraintTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStateConstraintIds( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StateConstraintInfo> searchForStateConstraints( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStateConstraint( String validationTypeKey, String stateConstraintTypeKey,  StateConstraintInfo stateConstraintInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo createStateConstraint( String stateConstraintTypeKey,  StateConstraintInfo stateConstraintInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StateConstraintInfo updateStateConstraint( String stateConstraintId,  StateConstraintInfo stateConstraintInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStateConstraint( String stateConstraintId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo getStatePropagation(String statePropagationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByIds( List<String> statePropagationIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> getStatePropagationIdsByType(String statePropagationTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> getStatePropagationsByTargetState( String targetStateId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<String> searchForStatePropagationIds( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<StatePropagationInfo> searchForStatePropagations( QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public List<ValidationResultInfo> validateStatePropagation( String validationTypeKey,  String targetStateChangeId, String statePropagationTypeKey,  StatePropagationInfo statePropagationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo createStatePropagation( String targetStateChangeId, String statePropagationTypeKey,  StatePropagationInfo statePropagationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatePropagationInfo updateStatePropagation(String statePropagationId,  StatePropagationInfo statePropagationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }

    @Override
    public StatusInfo deleteStatePropagation(String statePropagationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method has not yet been implemented.");
    }
}
