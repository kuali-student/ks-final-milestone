/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.ModelBuilder;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeType", propOrder = {"id", "key", "value", "_futureElements"})
public final class AttributeInfo implements AttributeInfc, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private final String id;
    
    @XmlElement
    private final String key;
    
    @XmlElement
    private final String value;

    @XmlAnyElement
    private final List<Element> _futureElements;
    
    private AttributeInfo() {
        this.id = null;
        this.key = null;
        this.value = null;
        this._futureElements = null;
    }

    private AttributeInfo(AttributeInfc builder) {
        this.id = builder.getId();
        this.key = builder.getKey();
        this.value = builder.getValue();
        this._futureElements = null;
    }

    @Override
    public String getId() {
        return id;
    }


    @Override
    public String getKey() {
        return key;
    }


    @Override
    public String getValue() {
        return value;
    }

    
    public static class Builder implements ModelBuilder<AttributeInfo>, AttributeInfc {

        private String id;
        private String value;
        private String key;

        public Builder() {
        }

        public Builder(AttributeInfc attInfo) {
            this.id = attInfo.getId();
            this.key = attInfo.getKey();
            this.value = attInfo.getValue();
        }

        public AttributeInfo build() {
            return new AttributeInfo(this);
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String val) {
            this.value = val;
            return this;
        }
    }
}
