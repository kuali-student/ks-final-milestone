/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.core.tag.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.constants.TagServiceConstants;
import org.kuali.student.r2.core.tag.dto.TagInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * The Tag Service allows for the creation and management of user
 * tags associated with other objects across the system.
 *
 * @version 2.0
 * @Author Sri komandur@uw.edu
 * @Author Mezba (Reviewed for CM2.0 on 20/11/2012)
 */
@WebService(name = "TagService", targetNamespace = TagServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TagService {

    /**
     * Retrieves information about a tag.
     *
     * @param tagId       tag identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return information about a tag
     * @throws DoesNotExistException     specified tagId not found
     * @throws InvalidParameterException invalid tagId
     * @throws MissingParameterException tagId, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TagInfo getTag(@WebParam(name = "tagId") String tagId,
                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a list of Tags corresponding to the
     * given list of Tag keys.
     *
     * @param tagIds      list of Tags to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Tag information for the given list of Tag Ids
     * @throws DoesNotExistException     an tagKey in list not found
     * @throws InvalidParameterException invalid tagKey
     * @throws MissingParameterException tagIds, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> getTagsByIds(@WebParam(name = "tagIds") List<String> tagIds,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Tags of the specified type.
     *
     * @param tagTypeKey  type to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Tag Ids
     * @throws InvalidParameterException invalid tagTypeKey
     * @throws MissingParameterException tagTypeKey, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getTagIdsByType(@WebParam(name = "tagTypeKey") String tagTypeKey,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves tag information for a reference. The expected behavior is that if the caller is not authorized
     * to invoke the getTags operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized
     * to invoke getTags, only tags that the caller is authorized to view are included in the returned tagInfoList;
     * tags that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return list of tag information for the given object ref id and type
     * @throws DoesNotExistException     specified referenceId, referenceTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> getTagsByReferenceAndType(@WebParam(name = "referenceId") String referenceId,
                                                   @WebParam(name = "referenceTypeKey") String referenceTypeKey,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Tags based on the criteria and returns a list
     * of Tag identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Tag Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException criteria, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForTagIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Tags based on the criteria and returns a list of
     * Tags which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Tag information
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException criteria, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> searchForTags(@WebParam(name = "criteria") QueryByCriteria criteria,
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Adds a tag to a reference.
     *
     * @param referenceId      identifier of reference
     * @param referenceTypeKey reference type
     * @param tagInfo          detailed information about the tag
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return detailed information about the tag
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        Id or Key does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    referenceId, referenceTypeKey, tagInfo, contextInfo not specified
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            attempted update of readonly data
     */
    public TagInfo createTag(@WebParam(name = "referenceId") String referenceId,
                             @WebParam(name = "referenceTypeKey") String referenceTypeKey,
                             @WebParam(name = "tagInfo") TagInfo tagInfo,
                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Removes a tag from all references to which it is linked.
     *
     * @param tagId       identifier of the tag
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     tagId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException tagId, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteTag(@WebParam(name = "tagId") String tagId,
                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Removes all tags associated with a single reference
     *
     * @param referenceId      identifier of the reference
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     tagId, referenceId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException referenceId, referenceTypeKey, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteTagsByReference(@WebParam(name = "referenceId") String referenceId,
                                            @WebParam(name = "referenceTypeKey") String referenceTypeKey,
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}
