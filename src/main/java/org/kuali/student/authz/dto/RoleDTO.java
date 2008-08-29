/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.authz.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class RoleDTO {
    private String id;
    private String name;
    private List<PermissionDTO> permissions;
    private QualifierHierarchyDTO qualifierHierarchy;
    private QualifierTypeDTO qualifierType;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * @return the permissions
	 */
	public List<PermissionDTO> getPermissions() {
		if(permissions==null){
			permissions = new ArrayList<PermissionDTO>();
		}
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the qualifierHierarchy
	 */
	public QualifierHierarchyDTO getQualifierHierarchy() {
		return qualifierHierarchy;
	}

	/**
	 * @param qualifierHierarchy the qualifierHierarchy to set
	 */
	public void setQualifierHierarchy(QualifierHierarchyDTO qualifierHierarchy) {
		this.qualifierHierarchy = qualifierHierarchy;
	}

	/**
	 * @return the qualifierType
	 */
	public QualifierTypeDTO getQualifierType() {
		return qualifierType;
	}

	/**
	 * @param qualifierType the qualifierType to set
	 */
	public void setQualifierType(QualifierTypeDTO qualifierType) {
		this.qualifierType = qualifierType;
	}
}
