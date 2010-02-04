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
