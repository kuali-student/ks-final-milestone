package org.kuali.student.ap.test.mock;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
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
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class MessageServiceMockTest implements MessageService {
    /**
     * Retrieves the list of locales supported by this service.
     *
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of locales supported by this service
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<LocaleInfo> getLocales(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of message group keys known by the service
     *
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return a list of message group keys
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<String> getMessageGroupKeys(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves message information.
     *
     * @param localeInfo      the locale information
     * @param messageGroupKey an identifier for the message group to
     *                        which the message belongs
     * @param messageKey      the identifier for the requested message
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the requested message
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          messageGroupKey or messageKey is
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          localeInfo, messageGroupKey,
     *          messageKey, or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public MessageInfo getMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve messages associated with a locale and group.
     *
     * @param localeInfo      the locale information
     * @param messageGroupKey an identifier for the message group to
     *                        which the messages belong
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return a list of Messages
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          messageGroupKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          loacleInfo or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          localeInfo, messageGroupKey,
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<MessageInfo> getMessagesByGroup(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve messages associated with a locale from a specified
     * list of groups.
     *
     * @param localeInfo       the locale information
     * @param messageGroupKeys a list of identifiers for the message
     *                         groups
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return the list of Messages belonging to the list of message groups
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          localeInfo or a messageGroupKey
     *          in messageGroupKeys is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          localeInfo or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          localeInfo, messageGroupKeys,
     *          a messageGroupKey in messagegroupKey, or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<MessageInfo> getMessagesByGroups(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKeys") List<String> messageGroupKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a message.
     * <p/>
     * Depending on the value of validationType, this validation could
     * be limited to tests on just the current object and its directly
     * contained subobjects or expanded to perform all tests related
     * to this object. If an identifier is present for the message and
     * a record is found for that identifier, the validation checks if
     * the message can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the
     * record does not exist and as such, the checks performed will be
     * much shallower, typically mimicking those performed by setting
     * the validationType to the current object. This is a slightly
     * different pattern from the standard validation as the caller
     * provides the identifier in the create statement instead of the
     * server assigning an identifier.
     *
     * @param validationTypeKey Identifier of the extent of validation
     * @param messageInfo       The message information to be tested.
     * @return Results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, messageInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, messageInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateMessage(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update message associated with a locale and group.
     *
     * @param localeInfo      the locale information
     * @param messageGroupKey an identifier for the message group to
     *                        which the messages belong
     * @param messageKey      the indentifier for the message
     * @param messageInfo     the message information to be updated
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return message information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          messageKey is not found
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          if invalid data
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          localeInfo, messageInfo, or
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          localeInfo, messageKey, messageInfo,
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          an attempt at supplying information
     *          designated as read only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          an optimistic locking failure
     *          or the action was attempted on an out of date version
     */
    @Override
    public MessageInfo updateMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes the message associated with a locale and group for a
     * message key
     *
     * @param localeInfo      the locale information
     * @param messageGroupKey an identifier for the message group to
     *                        which the messages belong
     * @param messageKey      an identifier for the Message to be deleted
     * @param contextInfo     information containing the principalId and
     *                        information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          messageKey not found for locale
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          localeInfo or contextInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          localeInfo, messageKey, or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public StatusInfo deleteMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create a new message for a locale and group.
     *
     * @param localeInfo      the locale information
     * @param messageGroupKey an identifier for the message group
     * @param messageKey      an identifier for the message within the group
     * @param messageInfo     the message information to be added
     * @param contextInfo     information containing the principalId and
     *                        locale information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          messageGroupKey is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          localeInfo or contetInfo is
     *          not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          localeInfo, messageGroupKey,
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public StatusInfo createMessage(@WebParam(name = "localeInfo") LocaleInfo localeInfo, @WebParam(name = "messageGroupKey") String messageGroupKey, @WebParam(name = "messageKey") String messageKey, @WebParam(name = "messageInfo") MessageInfo messageInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
