/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r1.common.rice.authorization;

/**
 * Enum to be used for Permission types. Permission Type is analagous to a permission template.
 * 
 * When using permission types, permission checks will be performed using template names. 
 *
 * @deprecated Only used by GWT. KRAD uses new {@link ProposalPermissionTypes} enum
 */
@Deprecated
public enum PermissionTypeGwt {
	INITIATE("Initiate","KR-SYS","Initiate Document"),
	OPEN("View","KS-SYS","Open Document (GWT)"),
	EDIT("Edit","KS-SYS","Edit Document"),
	ADD_COMMENT("Comment","KS-SYS","Comment on Document"),
	ADD_ADHOC_REVIEWER("Add Reviewer","KS-SYS","Add Adhoc Reviewer"), 
	WITHDRAW("Withdraw","KS-SYS","Withdraw Document"),
	SECTION_MAINTENANCE("Section Maintenance","KS-SYS","Section Maintenance"), 
	FIELD_ACCESS("Access Permission","KS-SYS","Access Permission"), 
	SEARCH("Lookup",null,null),
	REMOVE_ADHOC_REVIEWERS("Remove Reviewers","KS-SYS","Remove Reviewers"), 
	BLANKET_APPROVE("Blanket Approve","KS-SYS","Blanket Approve"),
	UPLOAD_DOCUMENTS("Upload","KS-SYS","Upload to Document"),
	USE_SCREEN("Use Screen","KS-SYS","Use Screen"),
    ADD_COLLABORATOR_ACTION("Add Collaborator Action","KS-SYS","Add Collaborator Action");

	private String label = "";
	private String permissionNamespace = "";
	private String permissionTemplateName = "";

	private PermissionTypeGwt(String label, String permissionNamespace, String permissionTemplateName) {
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

	public static PermissionTypeGwt getByCode(String code) {
		for (PermissionTypeGwt type : PermissionTypeGwt.values()) {
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
}
