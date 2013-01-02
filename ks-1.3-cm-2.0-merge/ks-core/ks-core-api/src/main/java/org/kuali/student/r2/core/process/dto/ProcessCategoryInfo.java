/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.process.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.process.infc.ProcessCategory;

//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessCategoryInfo", propOrder = { "id", "typeKey", 
                "stateKey", "name", "descr", "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code

public class ProcessCategoryInfo extends IdEntityInfo 
    implements ProcessCategory, Serializable {

    private static final long serialVersionUID = 1L;
    
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;
    

    /**
     * Constructs a new ProcessCategoryInfo.
     */
    public ProcessCategoryInfo() {
    }

    /**
     * Constructs a new ProcessCategoryInfo from another
     * ProcessCategory.
     * 
     * @param processCategory the ProcessCategory to copy
     */
    public ProcessCategoryInfo(ProcessCategory processCategory) {
        super(processCategory);
    }
}
