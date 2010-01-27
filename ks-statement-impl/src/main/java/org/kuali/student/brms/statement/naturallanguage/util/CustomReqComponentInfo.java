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
package org.kuali.student.brms.statement.naturallanguage.util;

import java.util.List;

import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.brms.statement.entity.ReqComponentField;
import org.kuali.student.brms.statement.entity.ReqComponentType;


/**
 * {@link ReqComponentTypeInfo} wrapper class.
 */
public class CustomReqComponentInfo {
    private String id;
//    private ReqComponentTypeInfo requiredComponentType;
//  private List<ReqCompFieldInfo> reqCompFields;
    private ReqComponentType requiredComponentType;
    private List<ReqComponentField> reqCompFields;

    public CustomReqComponentInfo() {}
    
    public String getId() {
		return id;
	}

    public void setId(String id) {
		this.id = id;
	}

    public ReqComponentType getRequiredComponentType() {
		return requiredComponentType;
	}

    public void setRequiredComponentType(ReqComponentType requiredComponentType) {
		this.requiredComponentType = requiredComponentType;
	}

    public List<ReqComponentField> getReqCompFields() {
		return reqCompFields;
	}

    public void setReqCompFields(List<ReqComponentField> reqCompFields) {
		this.reqCompFields = reqCompFields;
	}

	@Override
	public String toString() {
		return "CustomReqComponentInfo [id=" + id + ", requiredComponentTypeId="
				+ (requiredComponentType==null ? "null" : requiredComponentType.getId()) + "]";
	}
}
