package org.kuali.student.common.ui.client.application;

import org.kuali.student.common.ui.client.service.AuthorizationRpcService.PermissionType;
import org.kuali.student.core.rice.StudentIdentityConstants;

/**
 * ViewContext can be used to pass along context information when switching or initializing a view.
 * 
 * For example a display view requires the id of the object to display, the view context can be used
 * to pass along that information from a different controller or view.
 *
 */
public class ViewContext {
	public enum IdType {
		// FIXME: remove hard coded strings below for KIM constants
		KS_KEW_OBJECT_ID(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID), DOCUMENT_ID("documentNumber"), OBJECT_ID("objectId"), COPY_OF_OBJECT_ID("copyOfObjectId");
        
		final String stringValue;

		private IdType(String value) {
            this.stringValue = value;
        }

        public String toString() {
            return stringValue;
        }
	}
	
	private String id = "";
	private IdType idType = null;
//	private IdType idType = IdType.OBJECT_ID;
	// FIXME: change state to proper default or null
	private String state = "draft";
	private PermissionType permissionType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public PermissionType getPermissionType() {
    	return permissionType;
    }

	public void setPermissionType(PermissionType permissionType) {
    	this.permissionType = permissionType;
    }

}
