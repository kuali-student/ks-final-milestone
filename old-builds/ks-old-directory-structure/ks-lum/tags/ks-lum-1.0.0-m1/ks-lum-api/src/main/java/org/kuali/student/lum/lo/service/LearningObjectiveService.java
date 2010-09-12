/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.lum.lo.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerable.service.EnumerableService;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoHierarchyInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoTypeInfo;

/**
 *
 * @Author KSContractMojo
 * @Author jimt
 * @Since Thu Jun 18 21:14:17 PDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Learning+Objective+Service">LearningObjectiveService</>
 *
 */
@WebService(name = "LearningObjectiveService", targetNamespace = "http://student.kuali.org/wsdl/lo") // TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface LearningObjectiveService extends DictionaryService, EnumerableService, SearchService { 
    /** 
     * Retrieves the list of learning objective hierarchies known by this service, including the root node for each hierarchy.
     * @return list of learning objective hierarchy information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoHierarchyInfo> getLoHierarchies() throws OperationFailedException;

    /** 
     * Retrieves information about a particular learning objective hierarchy.
     * @param loHierarchyKey learning objective hierarchy identifier
     * @return information about a learning objective hierarchy
     * @throws DoesNotExistException specified learning objective hierarchy not found
     * @throws InvalidParameterException invalid loHierarchyKey
     * @throws MissingParameterException loHierarchyKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LoHierarchyInfo getLoHierarchy(@WebParam(name="loHierarchyKey")String loHierarchyKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of learning objective types known by this service.
     * @return list of learning objective type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoTypeInfo> getLoTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular learning objective type.
     * @param loTypeKey learning objective type identifier
     * @return information about a learning objective type
     * @throws DoesNotExistException specified learning objective type not found
     * @throws InvalidParameterException invalid loTypeKey
     * @throws MissingParameterException loTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public LoTypeInfo getLoType(@WebParam(name="loTypeKey")String loTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a learning objective category. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning objective category (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning objective category can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param loCategoryInfo learning objective category information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, loCategoryInfo
     * @throws MissingParameterException missing validationTypeKey, loCategoryInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validateLoCategory(@WebParam(name="validationType")String validationType, @WebParam(name="loCategoryInfo")LoCategoryInfo loCategoryInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a learning objective. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning objective (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning objective can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param loInfo learning objective information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, loInfo
     * @throws MissingParameterException missing validationTypeKey, loInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validateLo(@WebParam(name="validationType")String validationType, @WebParam(name="loInfo")LoInfo loInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about all the learning objective categories in a given learning objective hierarchy.
     * @param loHierarchyKey loHierarchy identifier
     * @return list of learning objective category information
     * @throws DoesNotExistException loHierarchyKey not found
     * @throws InvalidParameterException invalid loHierarchyKey
     * @throws MissingParameterException missing loHierarchyKey
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoCategoryInfo> getLoCategories(@WebParam(name="loHierarchyKey")String loHierarchyKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information about an learning objective category.
     * @param loCategoryId loCategory identifier
     * @return LoCategory
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId
     * @throws MissingParameterException missing loCategoryId
     * @throws OperationFailedException unable to complete request
	 */
    public LoCategoryInfo getLoCategory(@WebParam(name="loCategoryId")String loCategoryId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information on a single learning objective.
     * @param loId learning objective identifier
     * @return information about a single learning objective
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public LoInfo getLo(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves information on multiple learning objectives.
     * @param loId list of learning objective identifiers
     * @return list of learning objective information
     * @throws InvalidParameterException invalid loIdList
     * @throws MissingParameterException missing loIdList
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLoByIdList(@WebParam(name="loId")List<String> loId) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of learning objective categories for a specific learning objective.
     * @param loId learning objective identifier
     * @return list of learning objective category information
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoCategoryInfo> getLoCategoriesForLo(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of learning objectives that have a specific learning objective category.
     * @param loCategoryId learning objective category identifier
     * @return list of learning objective information
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId
     * @throws MissingParameterException missing loCategoryId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLosByLoCategory(@WebParam(name="loCategoryId")String loCategoryId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieve the children (direct descendants) of a learning objective. The learning objective hierarchy is not listed as a parameter as it would be redundant in the current model. Learning objectives exist only in a single hierarchy at a time and parent/child connections are limited to that hierarchy.
     * @param loId identifier of the learning objective
     * @return list of learning objective information
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLoChildren(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of identifiers for the "descendant" learning objectives of the specified learning objective. Information about the distance from the specified learning objective is not passed in this call, so this can be seen as a flattened and de-duplicated representation. The learning objective hierarchy is not listed as a parameter as it would be redundant in the current model. Learning objectives exist only in a single hierarchy at a time and parent/child connections are limited to that hierarchy.
     * @param loId identifier of the learning objective
     * @return list of identifiers for the "descendant" learning objectives for the specified learning objective
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAllDescendants(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieve the parent learning objectives for an existing learning objective. The learning objective hierarchy is not listed as a parameter as it would be redundant in the current model. Learning objectives exist only in a single hierarchy at a time and parent/child connections are limited to that hierarchy.
     * @param loId identifier of the learning objective
     * @return list of learning objective information
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLoParents(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Test if a learning objective is a descendant of another learning objective, returning true if the connection exists. This test is not only for direct children, but children through any number of intervening nodes. The learning objective hierarchy is not listed as a parameter as it would be redundant in the current model. Learning objectives exist only in a single hierarchy at a time and parent/child connections are limited to that hierarchy.
     * @param loId identifier of the parent learning objective
     * @param descendantLoId identifier of the potential descendant learning objective
     * @return true if the connection exists.
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId, descendantLoId
     * @throws MissingParameterException missing loId, descendantLoId
     * @throws OperationFailedException unable to complete request
	 */
    public Boolean isDescendant(@WebParam(name="loId")String loId, @WebParam(name="descendantLoId")String descendantLoId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of identifiers for "ancestor" learning objectives of the specified learning objective. Information about the distance from the specified learning objective is not passed in this call, so this can be seen as a flattened and de-duplicated representation. The learning objective hierarchy is not listed as a parameter as it would be redundant in the current model. Learning objectives exist only in a single hierarchy at a time and parent/child connections are limited to that hierarchy.
     * @param loId identifier of the learning objective
     * @return list of identifiers for the "ancestor" learning objectives of the specified learning objective
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getAncestors(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves all equivalent learning objectives of a learning objective. Note: Equivalency of learning objectives is uni-directional.
     * @param loId identifier of the learning objective
     * @return list of learning objectives
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getEquivalentLos(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves all learning objectives that a learning objective is equivalent to. Note: Equivalency of learning objectives is uni-directional.
     * @param loId identifier of the learning objective
     * @return list of learning objectives
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public List<LoInfo> getLoEquivalents(@WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Tests that a learning objectives is equivalent to another. Note: Equivalency of learning objectives is uni-directional.
     * @param loId identifier of the first learning objective
     * @param equivalentLoId identifier of the second learning objective
     * @return true if loId is equivalent to equivalentLoId
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
	 */
    public Boolean isEquivalent(@WebParam(name="loId")String loId, @WebParam(name="equivalentLoId")String equivalentLoId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Create a learning objective category in a particular learning objective hierarchy.
     * @param loHierarchyKey identifier of the learning objective hierarchy
     * @param loCategoryInfo information to create the learning objective category
     * @return information on the created learning objective category
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loHierarchyKey not found
     * @throws InvalidParameterException invalid loHierarchyKey, loCategoryInfo
     * @throws MissingParameterException missing loHierarchyKey, loCategoryInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LoCategoryInfo createLoCategory(@WebParam(name="loHierarchyKey")String loHierarchyKey, @WebParam(name="loCategoryInfo")LoCategoryInfo loCategoryInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a learning objective category in a particular learning objective hierarchy.
     * @param loCategoryId the learning objective category identifier
     * @param loCategoryInfo information to create the learning objective category
     * @return information on the uppdated learning objective category
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId, loCategoryInfo
     * @throws MissingParameterException missing loCategoryId, loCategoryInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version.
	 */
    public LoCategoryInfo updateLoCategory(@WebParam(name="loCategoryId")String loCategoryId, @WebParam(name="loCategoryInfo")LoCategoryInfo loCategoryInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes a learning objective category.
     * @param loCategoryId learning objective category identifier
     * @return status of the operation (success/failure)
     * @throws DependentObjectsExistException learning objective category is still attached to one or more learning objectives
     * @throws DoesNotExistException loCategoryId not found
     * @throws InvalidParameterException invalid loCategoryId
     * @throws MissingParameterException missing loCategoryId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLoCategory(@WebParam(name="loCategoryId")String loCategoryId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Create a learning objective.
     * @param parentLoId identifier of the parent learning objective
     * @param loType type for the learning objective
     * @param loInfo information to create the learning objective
     * @return information on the created learning objective
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException parentLoId, loType not found
     * @throws InvalidParameterException invalid parentLoId, loType, loInfo
     * @throws MissingParameterException missing parentLoId, loType, loInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public LoInfo createLo(@WebParam(name="parentLoId")String parentLoId, @WebParam(name="loType")String loType, @WebParam(name="loInfo")LoInfo loInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Update a learning objective.
     * @param loId identifier of the learning objective to update
     * @param loInfo updated information on the learning objective
     * @return information on the updated learning objective
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId, loInfo
     * @throws MissingParameterException missing loId, loInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException action was attempted on an out of date version.
	 */
    public LoInfo updateLo(@WebParam(name="loId")String loId, @WebParam(name="loInfo")LoInfo loInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Delete a learning objective.
     * @param loId identifier of the learning objective to delete
     * @return status of the operation (success or failure)
     * @throws DependentObjectsExistException removing the learning objective will orphan one or more child learning objectives
     * @throws DoesNotExistException loId not found
     * @throws InvalidParameterException invalid loId
     * @throws MissingParameterException missing loId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteLo(@WebParam(name="loId")String loId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Add a existing learning objective category to an existing learning objective in the same hierarchy.
     * @param loCategoryId identifier of the learning objective category to add
     * @param loId identifier of the learning objective
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException loCategoryId is already set as a category of the LoId
     * @throws DoesNotExistException loId, loCategoryId not found
     * @throws InvalidParameterException invalid loId, loCategoryId
     * @throws MissingParameterException missing loId, loCategoryId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException loId, loCategoryId are not in the same hierarchy
	 */
    public StatusInfo addLoCategoryToLo(@WebParam(name="loCategoryId")String loCategoryId, @WebParam(name="loId")String loId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove a existing learning objective category from an existing learning objective in the same hierarchy.
     * @param loCategoryId identifier of the learning objective category to remove
     * @param loId identifier of the learning objective
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException loId, loCategoryId not found
     * @throws InvalidParameterException invalid loId, loCategoryId
     * @throws MissingParameterException missing loId, loCategoryId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException loId, loCategoryId are not in the same hierarchy
	 */
    public StatusInfo removeLoCategoryFromLo(@WebParam(name="loCategoryId")String loCategoryId, @WebParam(name="loId")String loId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Add a existing learning objective as a child to an existing learning objective in the same hierarchy.
     * @param loId identifier of the learning objective to add
     * @param parentLoId identifier of the parent learning objective
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException loId is already set as a child to parentLoId
     * @throws CircularRelationshipException circular relationship between the descendants and ancestors
     * @throws DoesNotExistException loId, parentLoId not found
     * @throws InvalidParameterException invalid loId, parentLoId
     * @throws MissingParameterException missing loId, parentLoId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws UnsupportedActionException loId, parentId are not in the same hierarchy
	 */
    public StatusInfo addChildLoToLo(@WebParam(name="loId")String loId, @WebParam(name="parentLoId")String parentLoId) throws AlreadyExistsException, CircularReferenceException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, UnsupportedActionException;

    /** 
     * Remove a child learning objective from a parent learning objective.
     * @param loId identifier of the child learning objective
     * @param parentLoId identifier of the parent learning objective
     * @return status of the operation (success or failure)
     * @throws DependentObjectsExistException removing the learning objective will orphan this learning objective or orphan child learning objectives
     * @throws DoesNotExistException loId, parentLoId not found
     * @throws InvalidParameterException invalid loId, parentLoId
     * @throws MissingParameterException missing loId, parentLoId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeChildLoFromLo(@WebParam(name="loId")String loId, @WebParam(name="parentLoId") String parentLoId) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Set a learning objective as equivalent to another learning objective. The equivalency is unidirectional.
     * @param loId identifier of the learning objective to add an equivalency to
     * @param equivalentLoId identifier of the equivalent learning objective
     * @return status of the operation (success or failure)
     * @throws AlreadyExistsException equivalentLoId is already set as equivalent to loId
     * @throws DoesNotExistException loId, equivalentLoId not found
     * @throws InvalidParameterException invalid loId, equivalentLoId
     * @throws MissingParameterException missing loId, equivalentLoId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo addEquivalentLoToLo(@WebParam(name="loId")String loId, @WebParam(name="equivalentLoId")String equivalentLoId) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Remove an equivalency defined with another learning objective.
     * @param loId identifier of the learning objective to remove an equivalency from
     * @param equivalentLoId identifier of the equivalent learning objective
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException loId, equivalentLoId not found
     * @throws InvalidParameterException invalid loId, equivalentLoId
     * @throws MissingParameterException missing loId, equivalentLoId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeEquivalentLoFromLo(@WebParam(name="loId")String loId, @WebParam(name="equivalentLoId")String equivalentLoId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
