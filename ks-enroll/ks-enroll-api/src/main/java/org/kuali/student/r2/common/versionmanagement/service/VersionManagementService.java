package org.kuali.student.common.versionmanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;

import org.kuali.student.r2.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

@WebService(name = "VersionManagementService", targetNamespace = "http://student.kuali.org/wsdl/versionmanagement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface VersionManagementService {

    /**
     * Retrieves a list of versions associated with the objectId.
     * 
     * @param refObjectTypeURI reference object type URI 
     * @param refObjectId reference object Id
     * @return a list of versions
     * @throws DoesNotExistException specified refObjectId and
     *         refObjectTypeURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectTypeURI,
     *         refObjectId, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public List<VersionDisplayInfo> getVersions(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Retrieves first version associated with the objectId.
     * 
     * @param refObjectTypeURI reference object type URI
     * @param refObjectId reference object Id
     * @return first version
     * @throws DoesNotExistException specified refObjectId and
     *         refObjectTypeURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectTypeURI,
     *         refObjectId, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public VersionDisplayInfo getFirstVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

   /**
     * Retrieves latest version associated with the objectId. This is
     * not always the current version.  A current version is what is
     * being used by the system right now, but there could be draft
     * version created after the current version.
     * 
     * @param refObjectTypeURI reference object type URI
     * @param refObjectId reference object Id
     * @return current version
     * @throws DoesNotExistException specified refObjectId and
     *         refObjectTypeURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectId,
     *         refObjectTypeURI, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public VersionDisplayInfo getLatestVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Retrieves current version associated with the objectId.
     * 
     * @param refObjectTypeURI reference object type URI
     * @param refObjectId reference object Id
     * @return current version
     * @throws DoesNotExistException specified refObjectId and
     *         refObjectTypeURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectTypeURI,
     *         refObjectId, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the version associated with the objectId and the
     * sequence number.
     * 
     * @param refObjectTypeURI reference object type URI
     * @param refObjectId reference object Id
     * @param sequence sequence number
     * @return version matching the sequence
     * @throws DoesNotExistException specified refObjectId and
     *         refObjectTypeURI, sequence not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectTypeURI,
     *         refObjectId, sequence, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public VersionDisplayInfo getVersionBySequenceNumber(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "sequence") Long sequence, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the current version associated with the objectId on a
     * given date.
     * 
     * @param refObjectTypeURI reference object type URI
     * @param refObjectId reference object Id
     * @param date date
     * @return current version on date
     * @throws DoesNotExistException specified refObjectId and
     *         refObjectTypeURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectTypeURI,
     *         refObjectId, date, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public VersionDisplayInfo getCurrentVersionOnDate(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "date") Date date, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the current version associated with the objectId on a
     * given date.
     * 
     * @param refObjectTypeURI reference object type URI
     * @param refObjectId reference object Id
     * @param from from date
     * @param to to date
     * @return current version on date
     * @throws DoesNotExistException specified refObjectId and
     *         refObjectTypeURI not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException refObjectTypeURI,
     *         refObjectId, from, to, or contextInfo is missing or
     *         null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     */
    public List<VersionDisplayInfo> getVersionsInDateRange(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name="contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;  
}
