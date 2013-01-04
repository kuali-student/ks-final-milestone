package org.kuali.student.r2.core.versionmanagement.service;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.VersionManagementServiceConstants;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * Version Management Service supports the management of object versions
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

@WebService(name = "VersionManagementService", targetNamespace = VersionManagementServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface VersionManagementService {
    /**
     * Retrieves a list of versions associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return a list of versions
     * @throws DoesNotExistException     refObjectUri or refObjectId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException refObjectUri, refObjectId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<VersionDisplayInfo> getVersions(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves first version associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return first version
     * @throws DoesNotExistException     refObjectUri or refObjectId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectUri, refObjectId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public VersionDisplayInfo getFirstVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves latest version associated with the objectId. This is not always
     * the current version.  A current version is what is being used by the
     * system right now, but there could be draft version created after the
     * current version.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return latest version
     * @throws DoesNotExistException     specified refObjectId and refObjectUri
     *                                   not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectUri, refObjectId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public VersionDisplayInfo getLatestVersion(@WebParam(name = "refObjectUri") String refObjectUri,
                                               @WebParam(name = "refObjectId") String refObjectId,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves current version associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version
     * @throws DoesNotExistException     specified refObjectUri and refObjectId
     *                                   not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectUri, refObjectId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the version associated with the objectId and the sequence
     * number.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param sequence     sequence number
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return version matching the sequence
     * @throws DoesNotExistException     specified refObjectUri, refObjectId or
     *                                   sequence not found
     * @throws InvalidParameterException sequence or contextInfo is not valid
     * @throws MissingParameterException refObjectUri, refObjectId, sequence or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public VersionDisplayInfo getVersionBySequenceNumber(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "sequence") Long sequence, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the current version associated with the objectId on a given
     * date.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param date         date
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version on given date
     * @throws DoesNotExistException     specified refObjectId and refObjectUri
     *                                   not found
     * @throws InvalidParameterException date or contextInfo is not valid
     * @throws MissingParameterException refObjectUri, refObjectId, date or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public VersionDisplayInfo getCurrentVersionOnDate(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the current version associated with the objectId in a given
     * date range
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param from         from date
     * @param to           to date
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version in given date range
     * @throws DoesNotExistException     specified refObjectId and refObjectUri
     *                                   not found
     * @throws InvalidParameterException from, to or contextInfo is not valid
     * @throws MissingParameterException refObjectUri, refObjectId, from, to or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<VersionDisplayInfo> getVersionsInDateRange(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
