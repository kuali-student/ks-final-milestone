package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommentServiceMockTest implements CommentService {
    /**
     * Retrieves information about a comment.
     *
     * @param commentId   comment identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return information about a comment
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified commentId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid commentId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          commentId, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CommentInfo getComment(@WebParam(name = "commentId") String commentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of Comments corresponding to the
     * given list of Comment Ids
     *
     * @param commentIds  list of Comments to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          an commentKey in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid commentKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          commentIds, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CommentInfo> getCommentsByIds(@WebParam(name = "commentIds") List<String> commentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of Comments of the specified type.
     *
     * @param commentTypeKey type to be retrieved
     * @param contextInfo    Context information containing the principalId and locale
     *                       information about the caller of service operation
     * @return a list of Comment keys
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid commentTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          commentTypeKey, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getCommentIdsByType(@WebParam(name = "commentTypeKey") String commentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized
     * to invoke the getCommentsByReferenceAndType operation, a PERMISSION_DENIED error is returned.
     * Assuming that the caller is authorized to invoke getCommentsByReferenceAndType, only comments that the caller
     * is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized
     * to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return Comment information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified referenceId, referenceTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid referenceId, referenceTypeKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          referenceId, referenceTypeKey, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CommentInfo> getCommentsByReferenceAndType(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Comments based on the criteria and returns a list
     * of Comment identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForCommentIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for Comments based on the criteria and returns a list of
     * Comments which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CommentInfo> searchForComments(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Adds a comment to a reference.
     *
     * @param referenceId      identifier of reference
     * @param referenceTypeKey reference type
     * @param commentTypeKey   comment type
     * @param commentInfo      detailed information about the comment
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return detailed information about the comment
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Id or Key does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          attempted update of readonly data
     */
    @Override
    public CommentInfo createComment(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "commentTypeKey") String commentTypeKey, @WebParam(name = "commentInfo") CommentInfo commentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a comment for a reference.
     *
     * @param commentId   comment identifier
     * @param commentInfo detailed information about the comment
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return detailed information about the comment
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          comment does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          commentId, commentInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          attempted update of readonly data
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of date version.
     */
    @Override
    public CommentInfo updateComment(@WebParam(name = "commentId") String commentId, @WebParam(name = "commentInfo") CommentInfo commentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes a comment from a reference.
     *
     * @param commentId   identifier of the comment
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          commentId, referenceId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          commentId, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteComment(@WebParam(name = "commentId") String commentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes all comments associated with a single reference
     *
     * @param referenceId      identifier of the reference
     * @param referenceTypeKey reference type
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          referenceId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          referenceId, referenceTypeKey not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCommentsByReference(@WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a comment. Depending on the value of validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects or expanded to perform all tests related
     * to this object. If an identifier is present for the comment (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation checks if the comment can be shifted to the new
     * values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the
     * record does not exist and as such, the checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param referenceId       identifier of reference
     * @param referenceTypeKey  reference type
     * @param commentTypeKey    the identifier for the Comment
     *                          Type to be validated
     * @param commentInfo       comment information to be tested
     * @param contextInfo       Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, commentInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          validationTypeKey, commentInfo, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateComment(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "referenceId") String referenceId, @WebParam(name = "referenceTypeKey") String referenceTypeKey, @WebParam(name = "commentTypeKey") String commentTypeKey, @WebParam(name = "commentInfo") CommentInfo commentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
