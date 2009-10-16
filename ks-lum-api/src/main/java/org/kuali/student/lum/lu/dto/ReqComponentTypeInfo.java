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
package org.kuali.student.lum.lu.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReqComponentTypeInfo extends TypeInfo {  
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "reqCompFieldType")
    @XmlElementWrapper(name = "reqCompFieldTypes")
    private List<ReqCompFieldTypeInfo> reqCompFieldTypeInfos;

    @XmlElement(name = "nlUsageTemplates")
    private List<ReqComponentTypeNLTemplateInfo> nlUsageTemplates;	

	/**
     * @return the reqCompFieldTypeInfos
     */
    public List<ReqCompFieldTypeInfo> getReqCompFieldTypeInfos() {
        return reqCompFieldTypeInfos;
    }

    /**
     * @param reqCompFieldTypeInfos the reqCompFieldTypeInfos to set
     */
    public void setReqCompFieldTypeInfos(List<ReqCompFieldTypeInfo> reqCompFieldTypeInfos) {
        this.reqCompFieldTypeInfos = reqCompFieldTypeInfos;
    }        

    public List<ReqComponentTypeNLTemplateInfo> getNlUsageTemplates() {
		return nlUsageTemplates;
	}

	public void setNlUsageTemplates(
			List<ReqComponentTypeNLTemplateInfo> nlUsageTemplates) {
		this.nlUsageTemplates = nlUsageTemplates;
	}
}
