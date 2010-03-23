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
package org.kuali.student.core.role.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.ws.binding.AttributeSetAdapter;

/**
 * This class was generated by Apache CXF 2.1.3
 * Thu Nov 20 12:17:38 EST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getAcceptedAttributeNamesResponse", namespace = "http://student.kuali.org/wsdl/roleType")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAcceptedAttributeNamesResponse", namespace = "http://student.kuali.org/wsdl/roleType")

public class GetAcceptedAttributeNamesResponse {

    @XmlElement(name = "return")
    @XmlJavaTypeAdapter(AttributeSetAdapter.class)
    private java.util.List<AttributeSet> _return;

    public java.util.List<AttributeSet> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.List<AttributeSet> new_return)  {
        this._return = new_return;
    }

}

