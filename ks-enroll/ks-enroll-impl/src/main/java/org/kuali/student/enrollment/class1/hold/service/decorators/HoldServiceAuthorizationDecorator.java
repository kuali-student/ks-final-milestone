package org.kuali.student.enrollment.class1.hold.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
// import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.service.HoldServiceDecorator;

public class HoldServiceAuthorizationDecorator extends HoldServiceDecorator implements HoldsPermissionService {
    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "HoldService.";

    private PermissionService permissionService;

    @Override
    public PermissionService getPermissionService() {
        return permissionService;
    }

    @Override
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public HoldInfo getHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHold", null)) {
            return getNextDecorator().getHold(holdId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HoldInfo> getHoldsByIssue(String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHoldsByIssue", null)) {
            return getNextDecorator().getHoldsByIssue(issueId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> searchForHoldIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHoldIds", null)) {
            return getNextDecorator().searchForHoldIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HoldInfo> searchForHolds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHolds", null)) {
            return getNextDecorator().searchForHolds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<ValidationResultInfo> validateHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateHold", null)) {
            return getNextDecorator().validateHold(validationTypeKey, holdInfo, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public HoldInfo createHold(HoldInfo holdInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createHold", null)) {
            return getNextDecorator().createHold(holdInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public HoldInfo updateHold(String holdId, HoldInfo holdInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateHold", null)) {
            return getNextDecorator().updateHold(holdId, holdInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public HoldInfo releaseHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "releaseHold", null)) {
            return getNextDecorator().releaseHold(holdId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteHold", null)) {
            return getNextDecorator().deleteHold(holdId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public IssueInfo getIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getIssue", null)) {
            return getNextDecorator().getIssue(issueId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<IssueInfo> getIssuesByOrg(String organizationId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getIssuesByOrg", null)) {
            return getNextDecorator().getIssuesByOrg(organizationId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<IssueInfo> searchForIssues(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForIssues", null)) {
            return getNextDecorator().searchForIssues(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<ValidationResultInfo> validateIssue(String validationTypeKey, IssueInfo issueInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateIssue", null)) {
            return getNextDecorator().validateIssue(validationTypeKey, issueInfo, context);
        } else {
            throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public IssueInfo createIssue(IssueInfo issueInfo, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createIssue", null)) {
            return getNextDecorator().createIssue(issueInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public IssueInfo updateIssue(String issueId, IssueInfo issueInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateIssue", null)) {
            return getNextDecorator().updateIssue(issueId, issueInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public StatusInfo deleteIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteIssue", null)) {
            return getNextDecorator().deleteIssue(issueId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

}
