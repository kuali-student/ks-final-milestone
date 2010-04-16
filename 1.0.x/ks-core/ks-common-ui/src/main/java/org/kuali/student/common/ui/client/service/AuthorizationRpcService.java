/**
 * 
 */
package org.kuali.student.common.ui.client.service;

import java.util.Map;


/**
 * Service interface for authorization methods
 *
 */
public interface AuthorizationRpcService {

	public enum PermissionType{
		INITIATE("Initiate Document"),OPEN("Open Document"),SEARCH("Lookup Object");

		private String label;

		private PermissionType(String label) {
	        this.label = label;
        }

		@Override
        public String toString() {
	        return label;
        }
	};

	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes);

}
