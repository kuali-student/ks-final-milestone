package org.kuali.student.lum.kim.role.type;

import org.kuali.rice.krad.kim.PermissionDerivedRoleTypeServiceImpl;


/**
 * Just like PermissionDerivedRoleTypeServiceImpl but without caching
 *
 */
public class PermissionDerivedRoleTypeServiceNoCacheImpl extends
		PermissionDerivedRoleTypeServiceImpl {

    /**
     * Returns true, as some permissions change often enough that role membership is highly volatile
     *
     * @see org.kuali.rice.kim.framework.role.RoleTypeService#dynamicRoleMembership(java.lang.String, java.lang.String)
     */
    @Override
    public boolean dynamicRoleMembership(String namespaceCode, String roleName) {
        return true;
    }

}
