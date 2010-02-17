/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSLU_STMT_TYPE")
public class LuStatementType extends Type<LuStatementTypeAttribute> {

	@ManyToMany
	@JoinTable(name = "KSLU_LU_STMT_TYPE_JN_LU_TYPE", joinColumns = @JoinColumn(name = "LU_STMT_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "LU_TYPE_ID"))
	private List<LuType> luTypes;
   
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuStatementTypeAttribute> attributes;
    
    @OneToMany
    @JoinTable(name = "KSLU_STY_JN_RQTY", joinColumns = @JoinColumn(name = "LU_STMT_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COM_TYPE_ID"))
    private List<ReqComponentType> allowedReqComponentTypes;
    
    @OneToMany
    @JoinTable(name = "KSLU_STY_JN_LUSTY", joinColumns = @JoinColumn(name = "LU_STMT_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "CHLD_LU_STMT_TYPE_ID"))
    private List<LuStatementType> allowedLuStatementTypes;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuStatementTypeHeaderTemplate> statementHeader;

	public List<LuType> getLuTypes() {
		return luTypes;
	}

	public void setLuTypes(List<LuType> luTypes) {
		this.luTypes = luTypes;
	}

	public List<LuStatementTypeAttribute> getAttributes() {
        if(null == attributes){
            attributes = new ArrayList<LuStatementTypeAttribute>();
        }        
        return attributes;
    }

    public void setAttributes(List<LuStatementTypeAttribute> attributes) {
        this.attributes = attributes;
    }
        
    public List<ReqComponentType> getAllowedReqComponentTypes() {
        if(null == allowedReqComponentTypes) {
            allowedReqComponentTypes = new ArrayList<ReqComponentType>();
        }
        
        return allowedReqComponentTypes;
    }

    public void setAllowedReqComponentTypes(List<ReqComponentType> allowedReqComponentTypes) {
        this.allowedReqComponentTypes = allowedReqComponentTypes;
    }

    public List<LuStatementType> getAllowedLuStatementTypes() {
        if(null == allowedLuStatementTypes) {
            allowedLuStatementTypes = new ArrayList<LuStatementType>();
        }
        
        return allowedLuStatementTypes;
    }

    public void setAllowedLuStatementTypes(List<LuStatementType> allowedLuStatementTypes) {
        this.allowedLuStatementTypes = allowedLuStatementTypes;
    }

	public List<LuStatementTypeHeaderTemplate> getHeaders() {
		return statementHeader;
	}

	public void setHeaders(List<LuStatementTypeHeaderTemplate> header) {
		this.statementHeader = header;
	}

	@Override
	public String toString() {
		return "LuStatementType[id=" + getId() + "]";
	}
}
