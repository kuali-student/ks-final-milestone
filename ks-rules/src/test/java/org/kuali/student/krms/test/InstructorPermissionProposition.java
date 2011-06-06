package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.Proposition;

public class InstructorPermissionProposition extends AbstractProposition implements Proposition {

    private static boolean hasPermission = true;
    
    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        return hasPermission;
    }

    public static void setHasPermission(boolean permission) {
        hasPermission = permission;
    }
    
}
