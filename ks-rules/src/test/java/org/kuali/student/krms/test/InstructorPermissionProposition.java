package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.ExecutionEnvironment;
import org.kuali.rice.krms.api.Proposition;

public class InstructorPermissionProposition implements Proposition {

    private static boolean hasPermission = true;
    
    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        return hasPermission;
    }

    public static void setHasPermission(boolean permission) {
        hasPermission = permission;
    }
    
}
