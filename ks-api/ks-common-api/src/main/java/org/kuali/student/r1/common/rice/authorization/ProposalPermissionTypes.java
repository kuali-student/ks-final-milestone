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

import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;

/**
 * Enum to be used for Permission types (KRAD). Permission Type is analagous to a permission template.
 * 
 * When using permission types, permission checks will be performed using template names. 
 *
 */
public enum ProposalPermissionTypes {

    CURRICULUM_SPECIALIST("Curriculum Specialist User", StudentIdentityConstants.KS_NAMESPACE_CD, "Curriculum Specialist"),
    INITIATE("Initiate", KRADConstants.KUALI_RICE_SYSTEM_NAMESPACE, KewApiConstants.INITIATE_PERMISSION),
    OPEN("View", StudentIdentityConstants.KS_NAMESPACE_CD,"Open Document"),
    EDIT("Edit",KRADConstants.KNS_NAMESPACE,"Edit Document"),
    WITHDRAW("Withdraw","KS-SYS","Withdraw Document"),
    BLANKET_APPROVE("Blanket Approve",StudentIdentityConstants.KS_NAMESPACE_CD,"Blanket Approve"),
    ADD_COMMENT("Comment",StudentIdentityConstants.KS_NAMESPACE_CD,"Add a Comment"),
    EDIT_COMMENT("Edit Comment",StudentIdentityConstants.KS_NAMESPACE_CD,"Edit a Comment"),
    DELETE_COMMENT("Delete Comment",StudentIdentityConstants.KS_NAMESPACE_CD,"Delete a Comment");

    private String label = "";
    private String permissionNamespace = "";
    private String permissionTemplateName = "";

	private ProposalPermissionTypes(String label, String permissionNamespace, String permissionTemplateName) {
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

	public static ProposalPermissionTypes getByCode(String code) {
		for (ProposalPermissionTypes type : ProposalPermissionTypes.values()) {
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
