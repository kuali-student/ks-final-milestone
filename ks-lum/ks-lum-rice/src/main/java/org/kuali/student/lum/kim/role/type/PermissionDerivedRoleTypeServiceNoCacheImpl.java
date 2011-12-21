package org.kuali.student.lum.kim.role.type;

import org.kuali.rice.kns.authorization.PermissionDerivedRoleTypeServiceImpl;

/**
 * Just like PermissionDerivedRoleTypeServiceImpl but without caching
 *
 */
public class PermissionDerivedRoleTypeServiceNoCacheImpl extends
		PermissionDerivedRoleTypeServiceImpl {

	/* (non-Javadoc)
	 * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#shouldCacheRoleMembershipResults(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean shouldCacheRoleMembershipResults(String namespaceCode,
			String roleName) {
		return false;
	}

}
