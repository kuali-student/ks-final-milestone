package org.kuali.student.enrollment.class1.hold.service.facade;

import org.kuali.rice.kim.api.role.Role;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.List;

/**
 * Used to support service calls related to exam offerings. *
 *
 * @author Kuali Student Team
 */
public interface HoldIssueAuthorizingOrgFacade {

    /**
     * Retrieves a list of Roles/Functions that apply to hold issues
     *
     * Apply Hold to a Student
     * Expire the Applied Hold on Students
     *
     * @param contextInfo
     * @return list of Roles
     */
    List<Role> getHoldFunctions(ContextInfo contextInfo);

    /**
     * Store (or persist) the binding between the Hold Issue, the Org Id, and the Role/Function (Apply or Expire)
     *
     * @param holdIssueId
     * @param orgId
     * @param role
     * @param contextInfo
     * @throws DoesNotExistException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    void storeBinding(String holdIssueId, String orgId, Role role, ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Remove the binding between:The Hold Issue, the Org Id, and the Role/Function (Apply or Expire)
     *
     * @param holdIssueId
     * @param orgId
     * @param role
     * @param contextInfo
     * @throws DoesNotExistException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    void removeBinding(String holdIssueId, String orgId, Role role, ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves bindings by the Function and Hold Issue so we can display the list of orgs associated with this hold issue and function.
     *
     * @param roleId
     * @param holdIssueId
     * @param contextInfo
     * @return list of org ids
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    List<String> getBindingByRoleAndHoldIssue(String roleId, String holdIssueId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;
}
