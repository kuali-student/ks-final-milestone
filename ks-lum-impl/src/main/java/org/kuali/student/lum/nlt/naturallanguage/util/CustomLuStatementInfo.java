package org.kuali.student.lum.nlt.naturallanguage.util;

import java.util.List;

import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

/**
 * {@link LuStatementInfo} wrapper class.
 */
public class CustomLuStatementInfo {

    private String id;

    private String name;

    private StatementOperatorTypeKey operator;

    private LuStatementTypeInfo luStatementType;

    private CustomLuStatementInfo parent;

    private List<CustomLuStatementInfo> children; 
	
    private List<CustomReqComponentInfo> requiredComponents;
	
	public CustomLuStatementInfo() {
	}
	
    public String getId() {
    	return this.id;
    }
    
	public void setId(String id) {
		this.id = id;
	}

	public CustomLuStatementInfo getParent() {
		return this.parent;
	}

	public void setParent(CustomLuStatementInfo parent) {
		this.parent = parent;
	}

    public StatementOperatorTypeKey getOperator() {
        return this.operator;
    }

	public void setOperator(StatementOperatorTypeKey operator) {
		this.operator = operator;
	}

	public List<CustomLuStatementInfo> getChildren() {
		return this.children;
	}
	
	public void setChildren(List<CustomLuStatementInfo> children) {
		this.children = children;
	}

	public List<CustomReqComponentInfo> getRequiredComponents() {
		return this.requiredComponents;
	}

	public void setRequiredComponents(List<CustomReqComponentInfo> requiredComponents) {
		this.requiredComponents = requiredComponents;
	}
	
	public LuStatementTypeInfo getLuStatementType() {
		return this.luStatementType;
	}

	public void setLuStatementType(LuStatementTypeInfo luStatementType) {
		this.luStatementType = luStatementType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CustomLuStatementInfo[id=" + id + ", luStatementTypeId="
				+ (luStatementType == null ? "null" : luStatementType.getId()) 
				+ ", operator=" + operator + "]";
	}
}
