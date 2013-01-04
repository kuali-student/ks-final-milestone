/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.kim.api.permission.PermissionService;

/**
 *
 * @author nwright
 */
public class PermissionServiceIsAuthorizedCachingDecorator extends PermissionServiceDecorator {

    private final Map<String, Boolean> cache = new HashMap<String, Boolean>();
    private PermissionService permissionService;

    public PermissionServiceIsAuthorizedCachingDecorator(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public boolean isAuthorized(String principalId,
            String namespaceCode,
            String permissionName,
            Map<String, String> qualification)
            throws RiceIllegalArgumentException {
        String key = this.calcKey(principalId, namespaceCode, permissionName, qualification);
        Boolean isAuth = this.cache.get(key);
        if (isAuth != null) {
            return isAuth;
        }
        isAuth = permissionService.isAuthorized(principalId, namespaceCode, permissionName, qualification);
        this.cache.put(key, isAuth);
        return isAuth;
    }

    /**
     * might have to override to specify better separators
     */
    protected String calcKey(String principalId,
            String namespaceCode,
            String permissionName,
            Map<String, String> qualification) {
        StringBuilder bldr = new StringBuilder();
        bldr.append(principalId);
        bldr.append("|");
        bldr.append(namespaceCode);
        bldr.append("|");
        bldr.append(permissionName);
        bldr.append("|");
        if (qualification != null) {
            // sort so we don't get spurious misses
            List<String> keys = new ArrayList(qualification.keySet());
            Collections.sort(keys);
            for (String key : keys) {
                bldr.append(key);
                bldr.append("=");
                bldr.append(qualification.get(key));
            }
        }
        return bldr.toString();
    }
}
