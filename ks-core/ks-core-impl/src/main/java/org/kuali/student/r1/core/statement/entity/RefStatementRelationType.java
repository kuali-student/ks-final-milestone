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

package org.kuali.student.r1.core.statement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;;

@Entity
@Table(name = "KSST_REF_STMT_REL_TYPE")
public class RefStatementRelationType extends Type<RefStatementRelationTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<RefStatementRelationTypeAttribute> attributes;
	
    @ManyToMany(cascade = {CascadeType.ALL}) //, fetch = FetchType.EAGER)
    @JoinTable(name = "KSST_RSTMT_RTYP_JN_OSUB_TYP", joinColumns = @JoinColumn(name = "REF_STMT_REL_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "OBJ_SUB_TYPE_ID"))
    List<ObjectSubType> objectSubTypeList;

    @ManyToMany(cascade = {CascadeType.ALL}) //, fetch = FetchType.EAGER)
    @JoinTable(name = "KSST_RSTMT_RTYP_JN_STMT_TYP", joinColumns = @JoinColumn(name = "REF_STMT_REL_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "STMT_TYPE_ID"))
    List<StatementType> statementTypeList;

	@Override
	public List<RefStatementRelationTypeAttribute> getAttributes() {
		return this.attributes;
	}

	@Override
	public void setAttributes(List<RefStatementRelationTypeAttribute> attributes) {
		this.attributes = attributes;
	}

	public List<ObjectSubType> getObjectSubTypeList() {
		return objectSubTypeList;
	}

	public void setObjectSubTypeList(List<ObjectSubType> objectSubTypeList) {
		this.objectSubTypeList = objectSubTypeList;
	}

	public List<StatementType> getStatementTypeList() {
		return statementTypeList;
	}

	public void setStatementTypeList(List<StatementType> statementTypeList) {
		this.statementTypeList = statementTypeList;
	}

	@Override
	public String toString() {
		return "RefStatementRelationType[id=" + super.getId() + "]";
	}
}
