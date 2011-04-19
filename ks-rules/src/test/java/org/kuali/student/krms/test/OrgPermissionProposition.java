package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.Proposition;

public class OrgPermissionProposition implements Proposition {
    
    private final String orgId;
    
    private static boolean hasPermission = true;

    public OrgPermissionProposition(String orgId) {
        this.orgId = orgId;
    }
    
    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        return hasPermission;
    }
    
    public static void setHasPermission(boolean permission) {
        hasPermission = permission;
    }

}
