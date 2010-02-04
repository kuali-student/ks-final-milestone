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
package org.kuali.student.brms.statement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSSTMT_REF_STMT_REL")
public class RefStatementRelation {

    @Id
    @Column(name = "ID")
    private String id;
	
	@ManyToOne()
    @JoinColumn(name = "REF_STMT_REL_TYPE_ID")
    private RefStatementRelationType refStatementRelationType;

	@ManyToOne()
    @JoinColumn(name = "STMT_ID")
    private Statement statement;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RefStatementRelationType getRefStatementRelationType() {
		return refStatementRelationType;
	}

	public void setRefStatementRelationType(RefStatementRelationType refStatementRelationType) {
		this.refStatementRelationType = refStatementRelationType;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	@Override
	public String toString() {
		return "RefStatementRelation[id=" + id + "]";
	}

}
