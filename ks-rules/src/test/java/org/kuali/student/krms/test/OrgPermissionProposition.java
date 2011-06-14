package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.PropositionResult;


public class OrgPermissionProposition extends AbstractProposition implements Proposition {
    
    private final String orgId;
    
    private static boolean hasPermission = true;

    public OrgPermissionProposition(String orgId) {
        this.orgId = orgId;
    }
    
    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        return new PropositionResult(hasPermission, "");
    }
    
    public static void setHasPermission(boolean permission) {
        hasPermission = permission;
    }

}
