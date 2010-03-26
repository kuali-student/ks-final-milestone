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
		INITIATE("Initiate","KR-SYS","Initiate Document"),OPEN("View","KS-SYS","Open Document"),EDIT("Edit","KS-SYS","Edit Document"),
		ADD_COMMENT("Comment","KS-SYS","Comment on Document"),SEARCH("Lookup",null,null);

		private String label = "";
		private String permissionNamespace = "";
		private String permissionTemplateName = "";

		private PermissionType(String label, String permissionNamespace, String permissionTemplateName) {
	        this.label = label;
	        this.permissionNamespace = permissionNamespace;
	        this.permissionTemplateName = permissionTemplateName;
        }

		public String getLabel() {
        	return label;
        }

		public void setLabel(String label) {
        	this.label = label;
        }

		public String getPermissionNamespace() {
        	return permissionNamespace;
        }

		public void setPermissionNamespace(String permissionNamespace) {
        	this.permissionNamespace = permissionNamespace;
        }

		public String getPermissionTemplateName() {
        	return permissionTemplateName;
        }

		public void setPermissionTemplateName(String permissionTemplateName) {
        	this.permissionTemplateName = permissionTemplateName;
        }

		public String getCode() {
			return permissionNamespace + "~" + permissionTemplateName;
		}

		public static PermissionType getByCode(String code) {
			for (PermissionType type : PermissionType.values()) {
				if (type.getCode().equals(code)) {
					return type;
				}
			}
			return null;
		}

		@Override
        public String toString() {
	        return label;
        }
	};

	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes);

}
