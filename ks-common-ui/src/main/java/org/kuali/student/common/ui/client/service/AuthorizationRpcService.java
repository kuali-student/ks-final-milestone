/**
 * 
 */
package org.kuali.student.common.ui.client.service;

import java.util.Map;

import org.kuali.student.core.rice.authorization.PermissionType;


/**
 * Service interface for authorization methods
 *
 */
public interface AuthorizationRpcService {

	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes);

}
