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

package org.kuali.student.lum.lu.dto;

import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.TypeInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Information about a LU to LU relationship type.
 */ 
//KSCM-130:  Add @XmlType
@XmlType(name = "LuLuRelationTypeInfo", propOrder = {"id", "revName", "revDesc", "name", "descr", "effectiveDate", "expirationDate", "attributes"})
@XmlAccessorType(XmlAccessType.FIELD)
public class LuLuRelationTypeInfo extends TypeInfo{

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String revName;

    @XmlElement
    private String revDesc;

    /**
     * Name for the reverse LU to LU relationship type. This is primarily to be used by developers and may end up translated in the end system.
     */
    public String getRevName() {
        return revName;
    }

    public void setRevName(String revName) {
        this.revName = revName;
    }

    /**
     * Description of the reverse of the LU to LU relationship type
     */
    public String getRevDesc() {
        return revDesc;
    }

    public void setRevDesc(String revDesc) {
        this.revDesc = revDesc;
    }
}
