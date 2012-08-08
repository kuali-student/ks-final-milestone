package org.kuali.student.enrollment.class1.hold.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
// import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
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
    public HoldInfo getAppliedHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<String> getAppliedHoldIdsByIssue(String issueId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getHoldsByIssue", null)) {
            return getNextDecorator().getAppliedHoldIdsByIssue(issueId, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> searchForAppliedHoldIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForHoldIds", null)) {
            return getNextDecorator().searchForAppliedHoldIds(criteria, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<HoldInfo> searchForAppliedHolds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public List<ValidationResultInfo> validateAppliedHold(String validationTypeKey, HoldInfo holdInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
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
    public HoldInfo createAppliedHold(String personId,
            String issueId,
            String holdTypeKey,
            HoldInfo holdInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException();
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createHold", null)) {
            return getNextDecorator().createHold(personId, issueId, holdTypeKey, holdInfo, context);
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public HoldInfo updateAppliedHold(String holdId, HoldInfo holdInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
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
    public HoldInfo releaseAppliedHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public StatusInfo deleteAppliedHold(String holdId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public IssueInfo getHoldIssue(String issueId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public List<IssueInfo> getHoldIssuesByOrg(String organizationId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<IssueInfo> searchForHoldIssues(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
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
    public List<ValidationResultInfo> validateHoldIssue(String validationTypeKey, IssueInfo issueInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
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
    public IssueInfo createHoldIssue(String issueTypeKey, IssueInfo issueInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if (null == context) {
            throw new MissingParameterException("Missing context parameter");
        }

        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createIssue", null)) {
            return getNextDecorator().createIssue(issueTypeKey, issueInfo, context);
        } else {
            throw new PermissionDeniedException("Permission Denied.");
        }
    }

    @Override
    public IssueInfo updateHoldIssue(String issueId, IssueInfo issueInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
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
    public StatusInfo deleteHoldIssue(String issueId, ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
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
