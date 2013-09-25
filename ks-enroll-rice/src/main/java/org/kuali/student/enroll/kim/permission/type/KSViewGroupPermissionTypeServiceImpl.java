package org.kuali.student.enroll.kim.permission.type;

import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.krad.kim.ViewGroupPermissionTypeServiceImpl;

import java.util.List;
import java.util.Map;

public class KSViewGroupPermissionTypeServiceImpl extends ViewGroupPermissionTypeServiceImpl{

    @Override
    protected List<Permission> performPermissionMatches(Map<String, String> requestedDetails,
                                                        List<Permission> permissionsList) {
        List<Permission> matchedPermissions = super.performPermissionMatches(requestedDetails, permissionsList);
        //return matchedPermissions;
        return KSPermissionDetailsExpressionEvaluator.performPermissionMatches(requestedDetails, matchedPermissions);
    }
}
