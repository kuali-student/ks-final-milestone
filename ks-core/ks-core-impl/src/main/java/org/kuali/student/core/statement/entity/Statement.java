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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;

@Entity
@Table(name = "KSST_STMT")
@NamedQueries( {
    @NamedQuery(name = "Statement.getStatementsForStatementType", query = "SELECT ls FROM Statement ls WHERE ls.statementType.id = :statementTypeKey"),
    @NamedQuery(name = "Statement.getStatements", query = "SELECT ls FROM Statement ls WHERE ls.id IN (:statementIdList)"),
    @NamedQuery(name = "Statement.getStatementsForReqComponent", query = "SELECT ls FROM Statement ls JOIN ls.requiredComponents req WHERE req.id = :reqComponentId"),
    @NamedQuery(name = "Statement.getParentStatement", query = "SELECT DISTINCT stmt FROM Statement stmt JOIN stmt.children children WHERE children.id = :childId")
})
public class Statement extends MetaEntity implements AttributeOwner<StatementAttribute>{
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name="NAME")
    private String name;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private StatementRichText descr;

    @Column(name="ST")
    private String state;

    @Column(name="OPERATOR")
    @Enumerated(EnumType.STRING)
    private StatementOperatorTypeKey operator;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "KSST_STMT_JN_STMT", joinColumns = @JoinColumn(name = "STMT_ID"), inverseJoinColumns = @JoinColumn(name = "CHLD_STMT_ID"))
    private List<Statement> children;

    @ManyToMany
    @JoinTable(name = "KSST_STMT_JN_REQ_COM", joinColumns = @JoinColumn(name = "STMT_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COM_ID"))
    private List<ReqComponent> requiredComponents;

    @ManyToOne
    @JoinColumn(name = "STMT_TYPE_ID")
    private StatementType statementType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER")
    private List<StatementAttribute> attributes;

    @OneToMany(mappedBy = "statement")
    private List<RefStatementRelation> refStatementRelations;

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

    public List<Statement> getChildren() {
        return children;
    }

    public void setChildren(List<Statement> children) {
        this.children = children;
    }

    public List<ReqComponent> getRequiredComponents() {
        return requiredComponents;
    }

    public void setRequiredComponents(List<ReqComponent> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    public StatementType getStatementType() {
        return statementType;
    }

    public void setStatementType(StatementType statementType) {
        this.statementType = statementType;
    }

    public StatementOperatorTypeKey getOperator() {
        return operator;
    }

    public void setOperator(StatementOperatorTypeKey operator) {
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatementRichText getDescr() {
        return descr;
    }

    public void setDescr(StatementRichText descr) {
        this.descr = descr;
    }

    @Override
    public List<StatementAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<StatementAttribute> attributes) {
        this.attributes=attributes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public List<RefStatementRelation> getRefStatementRelations() {
		return refStatementRelations;
	}

	public void setRefStatementRelations(
			List<RefStatementRelation> refStatementRelations) {
		this.refStatementRelations = refStatementRelations;
	}

	@Override
	public String toString() {
		return "Statement[id=" + id + ", statementType="
		+ (statementType == null ? "null" : statementType.getId())
		+ ", operator=" + operator + "]";
	}
}
