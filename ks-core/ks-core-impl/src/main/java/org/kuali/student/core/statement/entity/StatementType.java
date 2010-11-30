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

package org.kuali.student.core.statement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSST_STMT_TYPE")
public class StatementType extends Type<StatementTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<StatementTypeAttribute> attributes;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "statementType")
    @OrderBy("statementType,sortOrder ASC")
	private List<OrderedReqComponentType> allowedReqComponentTypes;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "statementType")
    @OrderBy("statementType,sortOrder ASC")
	private List<OrderedStatementType> allowedStatementTypes;
    
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "statementTypeList") //, fetch = FetchType.EAGER)
    private List<RefStatementRelationType> refStatementRelationTypes;

	public List<StatementTypeAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<StatementTypeAttribute> attributes) {
        this.attributes = attributes;
    }
        
    public List<OrderedReqComponentType> getAllowedReqComponentTypes() {
    	return allowedReqComponentTypes;
    }

    public void setAllowedReqComponentTypes(List<OrderedReqComponentType> allowedReqComponentTypes) {
        this.allowedReqComponentTypes = allowedReqComponentTypes;
    }

    public List<OrderedStatementType> getAllowedStatementTypes() {
        return allowedStatementTypes;
    }

    public void setAllowedStatementTypes(List<OrderedStatementType> allowedStatementTypes) {
        this.allowedStatementTypes = allowedStatementTypes;
    }

	public List<RefStatementRelationType> getRefStatementRelationTypes() {
		return refStatementRelationTypes;
	}

	public void setRefStatementRelationTypes(List<RefStatementRelationType> refStatementRelationTypes) {
		this.refStatementRelationTypes = refStatementRelationTypes;
	}

	@Override
	public String toString() {
		return "statementType[id=" + getId() + "]";
	}
}
