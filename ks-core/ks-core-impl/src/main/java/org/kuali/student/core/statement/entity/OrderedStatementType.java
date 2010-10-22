package org.kuali.student.core.statement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.BaseEntity;

@Entity
@Table(name = "KSST_STMT_TYP_JN_STMT_TYP")
public class OrderedStatementType extends BaseEntity {

	@Column(name = "STMT_TYPE_ID", insertable = false, updatable = false)
    private String statementTypeId;

	@Column(name = "CHLD_STMT_TYPE_ID", insertable = false, updatable = false)
    private String childStatementTypeId;
    
	@Column(name="SORT_ORDER")
	private Integer sortOrder;
	
	@ManyToOne
    @JoinColumn(name = "STMT_TYPE_ID")
	private StatementType statementType;
	
	@ManyToOne
    @JoinColumn(name = "CHLD_STMT_TYPE_ID")
	private StatementType childStatementType;

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public StatementType getStatementType() {
		return statementType;
	}

	public void setStatementType(StatementType statementType) {
		this.statementType = statementType;
	}

	public StatementType getChildStatementType() {
		return childStatementType;
	}

	public void setChildStatementType(StatementType childStatementType) {
		this.childStatementType = childStatementType;
	}
}
