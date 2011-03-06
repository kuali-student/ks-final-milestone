/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lui.infc;

import java.util.List;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.enrollment.common.infc.StatusInfc;
import org.kuali.student.enrollment.common.infc.ValidationResultInfc;

public interface LuiServiceInfc {

 /**
  * Retrieves information about a LUI.
  *
  * @param luiId - String - identifier of the LUI
  * @return information about a LUI
  */
 public LuiInfc getLui(String luiId)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves information about LUIs from a list of Ids.
  *
  * @param luiIdList - StringList - List of LUI identifiers
  * @return information about a list of LUIs
  */
 public List<LuiInfc> getLuisByIdList(List<String> luiIdList)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of LUIs for the specified CLU and period.
  *
  * @param cluId - String - identifier of the CLU
  * @param atpKey - String - identifier for the academic time period
  * @return list of LUI information
  */
 public List<LuiInfc> getLuisInAtpByCluId(String cluId, String atpKey)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of LUI ids for the specified CLU.
  *
  * @param cluId - String - identifier of the CLU
  * @return list of LUI identifiers
  */
 public List<String> getLuiIdsByCluId(String cluId)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of LUI ids for the specified CLU and Time period.
  *
  * @param cluId - String - identifier of the CLU
  * @param atpKey - String - identifier for the academic time period
  * @return list of LUI identifiers
  */
 public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of allowed relation types between the
  * specified LUIs.
  *
  * @param luiId - String - identifier of the first LUI
  * @param relatedLuiId - String - identifier of the second LUI
  * @return list of LU to LU relation types
  */
 public List<String> getAllowedLuiLuiRelationTypesByLuiId(String luiId, String relatedLuiId)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of LUI information for the LUIs related
  * the specified LUI Id with a certain LU to LU relation
  * (getRelatedLuisByLuiId from the other direction)
  *
  * @param relatedLuiId - String - identifier of the LUI
  * @param luLuRelationType - String - the LU to LU relation type
  * @return list of LUI information
  */
 public List<LuiInfc> getLuisByRelation(String relatedLuiId, String luLuRelationType)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of LUI Ids for the specified related LUI
  * and LU to LU relation type (getRelatedLuiIdsByLuiId from
  * other direction).
  *
  * @param relatedLuiId - String - identifier of the LUI
  * @param luLuRelationType - String - the LU to LU relation type
  * @return list of LUI identifiers
  */
 public List<String> getLuiIdsByRelation(String relatedLuiId, String luLuRelationType)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of related LUI information for the
  * LUI Id and LU to LU relation type (getLuisByRelation from
  * other direction).
  *
  * @param luiId - String - identifier of the LUI
  * @param luLuRelationType - String - the LU to LU relation type
  * @return list of LUI information
  */
 public List<LuiInfc> getRelatedLuisByLuiId(String luiId, String luLuRelationType)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of related LUI Ids for the specified LUI
  * and LU to LU relation type. (getLuiIdsByRelation from the
  * direction).
  *
  * @param luiId - String - identifier of the LUI
  * @param luLuRelationType - String - the LU to LU relation type
  * @return list of LUI identifiers
  */
 public List<String> getRelatedLuiIdsByLuiId(String luiId, String luLuRelationType)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the relationship information between LUIs given
  * specific relation instance.
  *
  * @param luiLuiRelationId - String - identifier of LUI to LUI relation
  * @return information on the relation between two LUIs
  */
 public LuiLuiRelationInfc getLuiLuiRelation(String luiLuiRelationId)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Retrieves the list of relationship information for
  * specified LUI.
  *
  * @param luiId - String - identifier of the LUI
  * @return list of LUI to LUI relation information
  */
 public List<LuiLuiRelationInfc> getLuiLuiRelationsByLui(String luiId)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Validates a LUI. Depending on the value of validationType,
  * validation could be limited to tests on just the current
  * and its directly contained sub-objects or expanded to
  * all tests related to this object. If an identifier is
  * for the LUI (and/or one of its contained sub-objects) and
  * record is found for that identifier, the validation checks
  * the LUI can be shifted to the new values. If an identifier
  * not present or a record cannot be found for the identifier,
  * is assumed that the record does not exist and as such,
  * checks performed will be much shallower, typically
  * those performed by setting the validationType to the
  * object.
  *
  * @param validationType - String - identifier of the extent of validation
  * @param luiInfo - LuiInfo - LUI information to be tested.
  * @return results from performing the validation
  */
 public List<ValidationResultInfc> validateLui(String validationType, LuiInfc luiInfo)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Creates a new LUI.
  *
  * @param cluId - String - identifier of the CLU for the LUI being created
  * @param atpKey - String - identifier of the academic time period for the LUI
  * being created
  * @param luiInfo - LuiInfo - information about the LUI being created
  * @return the created LUI information
  */
 public LuiInfc createLui(String cluId, String atpKey, LuiInfc luiInfo)
         throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

 /**
  * Updates an existing LUI.
  *
  * @param luiId - String - identifier for the LUI to be updated
  * @param luiInfo - LuiInfo - updated information about the LUI
  * @return the updated LUI information
  */
 public LuiInfc updateLui(String luiId, LuiInfc luiInfo)
         throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

 /**
  * Deletes a LUI record.
  *
  * @param luiId - String - identifier for the LUI to be deleted
  * @return status of the operation
  */
 public StatusInfc deleteLui(String luiId)
         throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

 /**
  * Updates the state of the LUI.
  *
  * @param luiId - String - identifier for the LUI to be updated
  * @param luState - String - New state for LUI. Value is expected to be constrained
  * to those in
  *        the luState enumeration.
  * @return the updated LUI information
  */
 public LuiInfc updateLuiState(String luiId, String luState)
         throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

 /**
  * Validates a relationship between LUIs. Depending on the
  * of validationType, this validation could be limited to tests
  * just the current object and its directly contained sub-
  * or expanded to perform all tests related to this object. If
  * identifier is present for the relationship (and/or one of
  * contained sub-objects) and a record is found for
  * identifier, the validation checks if the relationship can
  * shifted to the new values. If an identifier is not present or
  * record cannot be found for the identifier, it is assumed
  * the record does not exist and as such, the checks
  * will be much shallower, typically mimicking those performed
  * setting the validationType to the current object.
  *
  * @param validationType - String - identifier of the extent of validation
  * @param luiLuiRelationInfo - LuiLuiRelationInfo - LUI to LUI relationship
  * information to be tested.
  * @return results from performing the validation
  */
 public List<ValidationResultInfc> validateLuiLuiRelation(String validationType, LuiLuiRelationInfc luiLuiRelationInfo)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

 /**
  * Create a relationship between two LUIs.
  *
  * @param luiId - String - identifier of the first LUI in the relationship
  * @param relatedLuiId - String - identifier of the second LUI in the relationship
  * to be related to
  * @param luLuRelationType - String - the LU to LU relationship type of the
  * relationship
  * @param luiLuiRelationInfo - LuiLuiRelationInfo - information about the
  * relationship between the two LUIs
  * @return the created LUI to LUI relation information
  */
 public LuiLuiRelationInfc createLuiLuiRelation(String luiId, String relatedLuiId, String luLuRelationType, LuiLuiRelationInfc luiLuiRelationInfo)
         throws AlreadyExistsException, CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

 /**
  * Updates a relationship between two LUIs.
  *
  * @param luiLuiRelationId - String - identifier of the LUI to LUI relation to
  * update
  * @param luiLuiRelationInfo - LuiLuiRelationInfo - changed information about the
  * relationship between the two LUIs
  * @return the update LUI to LUI relation information
  */
 public LuiLuiRelationInfc updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfc luiLuiRelationInfo)
         throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

 /**
  * Deletes a relationship between two LUIs.
  *
  * @param luiLuiRelationId - String - identifier of the LUI to LUI relation to
  * delete
  * @return status of the operation (success or failure)
  */
 public StatusInfc deleteLuiLuiRelation(String luiLuiRelationId)
         throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}

