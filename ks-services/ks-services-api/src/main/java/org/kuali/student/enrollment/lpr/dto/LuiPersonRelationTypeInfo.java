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

package org.kuali.student.enrollment.lpr.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.common.dto.TypeInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTypeInfc;

/**
 * Information about a CLU LO relation type.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Mon Jan 11 15:19:49 PST 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/cluLoRelationTypeInfo+Structure+v1.0-rc1">CluLoRelationTypeInfo v1.0-rc1</>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LuiPersonRelationTypeInfo extends TypeInfo implements LuiPersonRelationTypeInfc {
	private static final long serialVersionUID = 1L;
    
	private LuiPersonRelationTypeInfo() {}
	
    private LuiPersonRelationTypeInfo(LuiPersonRelationTypeInfc builder) {
    	super(builder);
	}

    public static class Builder extends TypeInfo.Builder implements LuiPersonRelationTypeInfc {
    	
    public Builder() {}
    	public Builder(LuiPersonRelationTypeInfc lprTypeInfc) {
    	}
    	public LuiPersonRelationTypeInfo build() {
    		return new LuiPersonRelationTypeInfo(this);
    	}
    }
}