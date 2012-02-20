package org.kuali.student.r1.core.statement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.BaseEntity;

@Deprecated
@Entity
@Table(name = "KSST_STMT_TYP_JN_RC_TYP")
public class OrderedReqComponentType extends BaseEntity {

	@Column(name = "STMT_TYPE_ID", insertable = false, updatable = false)
	private String statementTypeId;

	@Column(name = "REQ_COM_TYPE_ID", insertable = false, updatable = false)
	private String reqComponentTypeId;
	
	@Column(name="SORT_ORDER")
	private Integer sortOrder;
	
	@ManyToOne
    @JoinColumn(name = "STMT_TYPE_ID")
	private StatementType statementType;
	
	@ManyToOne
    @JoinColumn(name = "REQ_COM_TYPE_ID")
	private ReqComponentType reqComponentType;

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

	public ReqComponentType getReqComponentType() {
		return reqComponentType;
	}

	public void setReqComponentType(ReqComponentType reqComponentType) {
		this.reqComponentType = reqComponentType;
	}
}
