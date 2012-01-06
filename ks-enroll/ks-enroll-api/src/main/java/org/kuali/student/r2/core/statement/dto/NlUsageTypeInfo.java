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

package org.kuali.student.r2.core.statement.dto;

import org.kuali.student.r2.common.dto.TypeEntityInfo;
import org.kuali.student.r2.core.statement.infc.NlUsageType;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NlUsageTypeInfo", propOrder = {"key", "name", "descr", "effectiveDate", "expirationDate",
        "refObjectURI", "attributes", "_futureElements"})
public class NlUsageTypeInfo extends TypeEntityInfo implements NlUsageType {

    private static final long serialVersionUID = 1L;

    @XmlAnyElement
    private List<Element> _futureElements;

    public NlUsageTypeInfo() {
    }

    public NlUsageTypeInfo(NlUsageType nlUsageType) {
        super(nlUsageType);
    }

    @Override
    public String toString() {
        return "NlUsageTypeInfo[id=" + getKey() + "]";
    }
}
