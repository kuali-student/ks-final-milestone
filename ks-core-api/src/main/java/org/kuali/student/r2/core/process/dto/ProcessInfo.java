/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.process.dto;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.core.process.infc.Process;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessInfo", propOrder = { "key", "typeKey", "stateKey",
                "name", "descr", "ownerOrgId", "meta", "attributes", "_futureElements" }) 

public class ProcessInfo 
    extends KeyEntityInfo 
    implements Process, Serializable {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    private static final long serialVersionUID = 1L;
    
    @XmlElement 
    private String ownerOrgId;

    @XmlAnyElement
    private List<Object> _futureElements;  

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    /**
     * Constructs a new ProcessInfo.
     */
    public ProcessInfo() {
    }

    /**
     * Constructs a new ProcessInfo from another Process.
     * 
     * @param process the Process to copy
     */
    public ProcessInfo(Process process) {
        super(process);
        if (process != null) {
            this.ownerOrgId = process.getOwnerOrgId();
        }
    }

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    @Override
    public String getOwnerOrgId() {
        return this.ownerOrgId;
    }

    public void setOwnerOrgId(String ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    ///////////////////////
    // FUNCTIONALS
    ///////////////////////


    @Override
    public String toString() {
        return "ProcessInfo{" +
                "ownerOrgId='" + ownerOrgId + "' }";
    }
}
