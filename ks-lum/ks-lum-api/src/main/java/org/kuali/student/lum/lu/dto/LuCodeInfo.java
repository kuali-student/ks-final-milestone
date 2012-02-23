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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.dto.IdEntityInfo;
import org.kuali.student.lum.lu.infc.LuCode;

/**
 * Detailed information about learning unit codes.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuCodeInfo", propOrder = { "id", "typeKey", "stateKey", 
                "name", "descr", "value", 
                "meta", "attributes" /*TODO KSCM-gwt-compile , "_futureElements" */ })
public class LuCodeInfo extends IdEntityInfo implements LuCode, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String value;
    
    //TODO KSCM-gwt-compile
    //@XmlAnyElement
    //private List<Element> _futureElements;


    public LuCodeInfo() {
        super();
        value = null;
        //TODO KSCM-gwt-compile _futureElements = null;
    }

    public LuCodeInfo(LuCode luCode) {
        super(luCode);
        this.value = luCode.getValue();
        //TODO KSCM-gwt-compile _futureElements = null;
    }
	
    public String getValue() {
	return value;
    }
    
    public void setValue(String value) {
	this.value = value;
    }
}
