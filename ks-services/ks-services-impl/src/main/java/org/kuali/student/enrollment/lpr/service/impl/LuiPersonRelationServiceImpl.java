/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationCriteriaInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTypeInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;
import org.springframework.stereotype.Service;


/**
 * @Author sambit
 */
@Service
public class LuiPersonRelationServiceImpl implements LuiPersonRelationServiceInfc {

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelationsForLui(
            String luiId, ContextInfc context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> createBulkRelationshipsForPerson(String personId,
                                                         List<String> luiIdList, String relationState,
                                                         String luiPersonRelationType,
                                                         LuiPersonRelationInfc luiPersonRelationInfc, ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO stub mock impl
        List<String> bulkRelationshipValues = new ArrayList<String>();

        bulkRelationshipValues.add(personId);

        System.out.println("Inside core impl for createBulkRelationshipsForPerson" );

        return bulkRelationshipValues;

    }

    @Override
    public String createLuiPersonRelation(String personId, String luiId,
                                          String luiPersonRelationType,
                                          LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
            throws AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuiPersonRelationInfc fetchLUIPersonRelation(
            String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findAllValidLuisForPerson(String personId,
                                                  String luiPersonRelationType, String relationState, String atpId,
                                                  ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findAllValidPeopleForLui(String luiId,
                                                 String luiPersonRelationType, String relationState,
                                                 ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationStateInfc> findAllowedRelationStates(
            String luiPersonRelationType, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiIdsRelatedToPerson(String personId,
                                                  String luiPersonRelationType, String relationState,
                                                  ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIds(String personId, String luiId,
                                                 ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIdsForLui(String luiId,
                                                       ContextInfc context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findLuiPersonRelationIdsForPerson(String personId,
                                                          ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationStateInfc> findLuiPersonRelationStates(
            ContextInfc context) throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypes(
            ContextInfc context) throws OperationFailedException {
    	// stub 
    	List<LuiPersonRelationTypeInfc> relationTypes = new ArrayList<LuiPersonRelationTypeInfc>();
    	
        return relationTypes;
    }

    @Override
    public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypesForLuiPersonRelation(
            String personId, String luiId, String relationState,
            ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelations(String personId,
                                                              String luiId, ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelationsByIdList(
            List<String> luiPersonRelationIdList, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationInfc> findLuiPersonRelationsForPerson(
            String personId, ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationInfc> findOrderedRelationStatesForLuiPersonRelation(
            String luiPersonRelationId, ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> findPersonIdsRelatedToLui(String luiId,
                                                  String luiPersonRelationType, String relationState,
                                                  ContextInfc context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuiPersonRelationStateInfc> findValidRelationStatesForLuiPersonRelation(
            String personId, String luiId, String luiPersonRelationType,
            ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isRelated(String personId, String luiId,
                             String luiPersonRelationType, String relationState,
                             ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isValidLuiPersonRelation(String personId, String luiId,
                                            String luiPersonRelationType, String relationState,
                                            ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> searchForLuiPersonRelationIds(
            LuiPersonRelationCriteriaInfc luiPersonRelationCriteria,
            ContextInfc context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LuiPersonRelationInfc updateLuiPersonRelation(
            String luiPersonRelationId,
            LuiPersonRelationInfc luiPersonRelationInfo,
            ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, ReadOnlyException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfc updateRelationState(String luiPersonRelationId,
                                          LuiPersonRelationStateInfc relationState,
                                          ContextInfc context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ValidationResultInfc validateLuiPersonRelation(String personId,
                                                          String luiId, String luiPersonRelationType, String relationState,
                                                          ContextInfc context) throws DoesNotExistException,
            DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }
}