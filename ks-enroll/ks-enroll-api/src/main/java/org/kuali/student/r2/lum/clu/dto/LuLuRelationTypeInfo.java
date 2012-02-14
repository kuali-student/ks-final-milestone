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

package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.lum.lu.infc.LuLuRelationType;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuLuRelationTypeInfo", propOrder = {
        "revName", "revDescr", "_futureElements"})
public class LuLuRelationTypeInfo extends TypeInfo implements LuLuRelationType {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String revName;

    @XmlElement
    private String revDescr;

    @XmlAnyElement
    private List<Element> _futureElements;

    public LuLuRelationTypeInfo() {

    }

    public LuLuRelationTypeInfo(LuLuRelationType luLuRelationType) {
        super(luLuRelationType);
        if (null != luLuRelationType) {
            this.revName = luLuRelationType.getRevName();
            this.revDescr = luLuRelationType.getRevDescr();
        }
    }

    @Override
    public String getRevName() {
        return revName;
    }

    public void setRevName(String revName) {
        this.revName = revName;
    }

    @Override
    public String getRevDescr() {
        return revDescr;
    }

    public void setRevDescr(String revDesc) {
        this.revDescr = revDescr;
    }
}
