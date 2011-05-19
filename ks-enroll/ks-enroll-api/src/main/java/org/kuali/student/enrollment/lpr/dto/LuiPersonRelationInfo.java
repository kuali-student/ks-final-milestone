/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LuiPersonRelation;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiPersonRelationInfo", propOrder = {"id", "typeKey", "stateKey", "luiId", "personId", "effectiveDate", "expirationDate", "metaInfo", "attributes", "_futureElements"})
public class LuiPersonRelationInfo extends RelationshipInfo
        implements LuiPersonRelation, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String luiId;
    @XmlElement
    private String personId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LuiPersonRelationInfo() {
        luiId = null;
        personId = null;
        _futureElements = null;
    }

    public LuiPersonRelationInfo(LuiPersonRelation lpr) {
        super(lpr);
        this.luiId = lpr.getLuiId();
        this.personId = lpr.getPersonId();
        _futureElements = null;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
