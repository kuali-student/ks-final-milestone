package org.kuali.student.core.versionmanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.versionmanagement.dto.VersionDisplayInfo;


@WebService(name = "VersionManagementService", targetNamespace = "http://student.kuali.org/wsdl/versionmanagement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface VersionManagementService {

    /**
     * Retrieves list of version associated with the objectId.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return list of versions
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<VersionDisplayInfo> getVersions(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    

    /**
     * Retrieves first version associated with the objectId.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return first version
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionDisplayInfo getFirstVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
	 * Retrieves latest version associated with the objectId. This is not always the current version. 
	 * A current version is what is being used by the system right now, but there could be draft version 
	 * created after the current version.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return current version
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionDisplayInfo getLatestVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * Retrieves current version associated with the objectId.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return current version
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves the version associated with the objectId and the sequence number.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @param sequence
     *            sequence number
     * @return version matching the sequence
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI, sequence not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI, sequence
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI, sequence not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionDisplayInfo getVersionBySequenceNumber(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "sequence") Long sequence) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves the current version associated with the objectId on a given date.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @param date
     *            date
     * @return current version on date
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI, date
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI, date not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionDisplayInfo getCurrentVersionOnDate(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "date") Date date) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves the current version associated with the objectId on a given date.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @param from
     *            from date
     * @param to
     *            to date
     * @return current version on date
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI, date
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI, date not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<VersionDisplayInfo> getVersionsInDateRange(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
   
}
