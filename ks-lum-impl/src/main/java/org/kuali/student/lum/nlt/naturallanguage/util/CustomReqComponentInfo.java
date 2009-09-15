package org.kuali.student.lum.nlt.naturallanguage.util;

import java.util.List;

import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;

/**
 * {@link ReqComponentTypeInfo} wrapper class.
 */
public class CustomReqComponentInfo {
    private String id;
    private ReqComponentTypeInfo requiredComponentType;
    private List<ReqCompFieldInfo> reqCompFields;

    public CustomReqComponentInfo() {}
    
    public String getId() {
		return id;
	}

    public void setId(String id) {
		this.id = id;
	}

    public ReqComponentTypeInfo getRequiredComponentType() {
		return requiredComponentType;
	}

    public void setRequiredComponentType(ReqComponentTypeInfo requiredComponentType) {
		this.requiredComponentType = requiredComponentType;
	}

    public List<ReqCompFieldInfo> getReqCompFields() {
		return reqCompFields;
	}

    public void setReqCompFields(List<ReqCompFieldInfo> reqCompFields) {
		this.reqCompFields = reqCompFields;
	}

	@Override
	public String toString() {
		return "CustomReqComponentInfo [id=" + id + ", requiredComponentTypeId="
				+ (requiredComponentType==null ? "null" : requiredComponentType.getId()) + "]";
	}
}
