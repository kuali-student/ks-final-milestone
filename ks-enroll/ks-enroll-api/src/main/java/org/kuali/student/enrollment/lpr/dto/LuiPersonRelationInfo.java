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

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelation;
import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiPersonRelationInfo", propOrder = {"id", "typeKey", "stateKey", "luiId", "personId", "effectiveDate", "expirationDate", "metaInfo", "attributes", "_futureElements"})
public class LuiPersonRelationInfo extends RelationshipInfo
        implements LuiPersonRelation, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private final String luiId;
    @XmlElement
    private final String personId;
    @XmlAnyElement
    private final List<Element> _futureElements;

    private LuiPersonRelationInfo() {
        super ();
        luiId = null;
        personId = null;
        _futureElements = null;
    }

    private LuiPersonRelationInfo(LuiPersonRelation builder) {
        super(builder);
        this.luiId = builder.getLuiId();
        this.personId = builder.getPersonId();
        _futureElements = null;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }


    public static class Builder extends RelationshipInfo.Builder implements ModelBuilder<LuiPersonRelationInfo>, LuiPersonRelation {

        private String luiId;
        private String personId;

        public Builder() {
        }

        public Builder(LuiPersonRelation lprInfo) {
            super(lprInfo);
            this.luiId = lprInfo.getLuiId();
            this.personId = lprInfo.getPersonId();
        }

        @Override
        public LuiPersonRelationInfo build() {
            return new LuiPersonRelationInfo(this);
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
}
