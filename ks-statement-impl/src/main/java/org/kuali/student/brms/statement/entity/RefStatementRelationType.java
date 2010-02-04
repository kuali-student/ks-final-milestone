package org.kuali.student.brms.statement.entity;

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
@Table(name = "KSSTMT_REF_STMT_REL_TYPE")
public class RefStatementRelationType extends Type<RefStatementRelationTypeAttribute> {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<RefStatementRelationTypeAttribute> attributes = new ArrayList<RefStatementRelationTypeAttribute>();
	
    @ManyToMany(cascade = {CascadeType.ALL}) //, fetch = FetchType.EAGER)
    @JoinTable(name = "KSSTMT_RSTMT_RTYP_JN_OSUB_TYP", joinColumns = @JoinColumn(name = "REF_STMT_REL_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "OBJ_SUB_TYPE_ID"))
    List<ObjectSubType> objectSubTypeList = new ArrayList<ObjectSubType>();

    @ManyToMany(cascade = {CascadeType.ALL}) //, fetch = FetchType.EAGER)
    @JoinTable(name = "KSSTMT_RSTMT_RTYP_JN_STMT_TYP", joinColumns = @JoinColumn(name = "REF_STMT_REL_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "STMT_TYPE_ID"))
    List<StatementType> statementTypeList = new ArrayList<StatementType>();

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
