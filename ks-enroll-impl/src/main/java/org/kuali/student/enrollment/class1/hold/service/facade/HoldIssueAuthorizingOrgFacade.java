package org.kuali.student.enrollment.class1.hold.service.facade;

import org.kuali.rice.kim.api.role.Role;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.util.List;

/**
 * Used to support service calls related to exam offerings. *
 *
 * @author Kuali Student Team
 */
public interface HoldIssueAuthorizingOrgFacade {

    /**
     * Retrieves a list of Functions that apply to holds:Apply Hold to a Student
     * Expire the Applied Hold on Students
     * @return
     */
    List<Role> getHoldFunctions(ContextInfo contextInfo);

    /**
     * Store (or persist) the binding between:The Hold Issue,he Org Id, The Function (Apply or Expire)
     *
     * @param holdIssueId
     * @param orgId
     * @param role
     * @return
     */
    void storeBinding(String holdIssueId, String orgId, Role role, ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Store (or persist) the binding between:The Hold Issue,he Org Id, The Function (Apply or Expire)
     *
     * @param holdIssueId
     * @param orgId
     * @param role
     * @return
     */
    void removeBinding(String holdIssueId, String orgId, Role role, ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves binding by the Function and Hold Issue so we can display the list of orgs are associated with this hold issue and function.
     *
     * @param roleId
     * @param holdIssueId
     * @return list of org ids
     */
    List<String> getBindingByRoleAndHoldIssue(String roleId, String holdIssueId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;
}
