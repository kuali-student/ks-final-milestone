/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.AttributeInfc;

@XmlAccessorType(XmlAccessType.FIELD)
public final class AttributeInfo implements AttributeInfc, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private final String id;
    @XmlElement
    private final String key;
    @XmlElement
    private final String value;

    private AttributeInfo() {
        id = null;
        key = null;
        value = null;
    }

    private AttributeInfo(AttributeInfc builder) {
        this.id = builder.getId();
        this.key = builder.getKey();
        this.value = builder.getValue();
    }

    /**
     * @return the internally assigned id to this key/value pair
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @return the key
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * @return the value
     */
    @Override
    public String getValue() {
        return value;
    }

    public static class Builder implements AttributeInfc {

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

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setValue(String val) {
            this.value = val;
            return this;
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
    }
}
