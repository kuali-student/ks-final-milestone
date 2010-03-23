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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSST_REF_STMT_REL")
@NamedQueries( {
    @NamedQuery(name = "RefStatementRelation.getRefStatementRelations", query = "SELECT r FROM RefStatementRelation r WHERE r.refObjectTypeKey = :refObjectTypeKey and r.refObjectId = :refObjectId")
})
public class RefStatementRelation extends MetaEntity implements AttributeOwner<RefStatementRelationAttribute> {

    @Id
    @Column(name = "ID")
    private String id;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;    

    @Column(name="REF_OBJ_TYPE_KEY")
	private String refObjectTypeKey;

    @Column(name="REF_OBJ_ID")
	private String refObjectId;

    @ManyToOne()
    @JoinColumn(name = "REF_STMT_REL_TYPE_ID")
    private RefStatementRelationType refStatementRelationType;
    
    @Column(name="ST")
    private String state;

	@ManyToOne()
    @JoinColumn(name = "STMT_ID")
    private Statement statement;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER")
    private List<RefStatementRelationAttribute> attributes = new ArrayList<RefStatementRelationAttribute>();

    /**
     * AutoGenerate the Id
     */
    @Override
    public void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }
    
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
	public List<RefStatementRelationAttribute> getAttributes() {
		return this.attributes;
	}

	@Override
	public void setAttributes(List<RefStatementRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRefObjectTypeKey() {
		return refObjectTypeKey;
	}

	public void setRefObjectTypeKey(String refObjectTypeKey) {
		this.refObjectTypeKey = refObjectTypeKey;
	}

	public String getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(String refObjectId) {
		this.refObjectId = refObjectId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "RefStatementRelation[id=" + id + "]";
	}
}
