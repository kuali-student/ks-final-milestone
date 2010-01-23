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
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

@Entity
@Table(name = "KSLU_STMT")
@NamedQueries( {
    @NamedQuery(name = "LuStatement.getLuStatementsForLuStatementType", query = "SELECT ls FROM LuStatement ls WHERE ls.luStatementType.id = :luStatementTypeKey"),
    @NamedQuery(name = "LuStatement.getLuStatementsForClu", query = "SELECT ls FROM LuStatement ls JOIN ls.clus clu WHERE clu.id = :cluId"),
    @NamedQuery(name = "LuStatement.getLuStatements", query = "SELECT ls FROM LuStatement ls WHERE ls.id IN (:luStatementIdList)")
})
public class LuStatement extends MetaEntity implements AttributeOwner<LuStatementAttribute>{
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCR")
    private String desc;
    
    @Column(name="ST")
    private String state;

    @Column(name="OPERATOR")
    @Enumerated(EnumType.STRING)
    private StatementOperatorTypeKey operator;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "PARENT_LU_STMT_ID")
    private LuStatement parent;

    @OneToMany(mappedBy = "parent")
    private List<LuStatement> children = new ArrayList<LuStatement>();

    @ManyToMany
    @JoinTable(name = "KSLU_STMT_JN_REQ_COM", joinColumns = @JoinColumn(name = "LU_STMT_ID"), inverseJoinColumns = @JoinColumn(name = "REQ_COM_ID"))
    private List<ReqComponent> requiredComponents = new ArrayList<ReqComponent>();

    @ManyToOne
    @JoinColumn(name = "LU_STMT_TYPE_ID")
    private LuStatementType luStatementType;

    @ManyToMany
    @JoinTable(name = "KSLU_CLU_JN_LU_STMT", joinColumns = @JoinColumn(name = "LU_STMT_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_ID"))
    private List<Clu> clus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER")
    private List<LuStatementAttribute> attributes;
    
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

    public LuStatement getParent() {
        return parent;
    }

    public void setParent(LuStatement parent) {
        this.parent = parent;
    }

    public List<LuStatement> getChildren() {
        return children;
    }

    public void setChildren(List<LuStatement> children) {
        this.children = children;
    }

    public List<ReqComponent> getRequiredComponents() {
        return requiredComponents;
    }

    public void setRequiredComponents(List<ReqComponent> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    public LuStatementType getLuStatementType() {
        return luStatementType;
    }

    public void setLuStatementType(LuStatementType luStatementType) {
        this.luStatementType = luStatementType;
    }

    public List<Clu> getClus() {
        return clus;
    }

    public void setClus(List<Clu> clus) {
        this.clus = clus;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public List<LuStatementAttribute> getAttributes() {
        if(attributes==null){
            attributes = new ArrayList<LuStatementAttribute>();
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<LuStatementAttribute> attributes) {
        this.attributes=attributes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	@Override
	public String toString() {
		return "LuStatement[id=" + id + ", luStatementType=" 
		+ (luStatementType == null ? "null" : luStatementType.getId()) 
		+ ", operator=" + operator + "]";
	}
}
