/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational
 * Community License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.osedu.org/licenses/ECL-2.0 Unless
 * required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.common.infc.Entity;

@SuppressWarnings("serial")
@XmlTransient
public abstract class EntityInfo extends HasAttributesAndMetaInfo implements Entity, Serializable {

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private String typeKey;

    @XmlElement
    private String stateKey;


    protected EntityInfo() {
        name = null;
        descr = null;
        typeKey = null;
        stateKey = null;
    }

    protected EntityInfo(Entity builder) {
        super(builder);
        this.name = builder.getName();
        this.descr = builder.getDescr();
        this.typeKey = builder.getType();
        this.stateKey = builder.getState();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    @Override
    public String getType() {
        return typeKey;
    }

    @Override
    public String getState() {
        return stateKey;
    }

    /**
     * The builder class for this abstract EntityInfo.
     */

    public static class Builder extends HasAttributesAndMetaInfo.Builder implements Entity {

        private String name;
        private RichTextInfo descr;
        private String typeKey;
        private String stateKey;

        public Builder() {}

        public Builder(Entity entity) {
            super(entity);
            this.name = entity.getName();
            this.descr = entity.getDescr();
            this.typeKey = entity.getType();
            this.stateKey = entity.getState();
        }

        @Override
        public String getName() {
            return name;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public RichTextInfo getDescr() {
            return descr;
        }

        public Builder descr(RichTextInfo descr) {
            this.descr = descr;
            return this;
        }

        @Override
        public String getType() {
            return typeKey;
        }

        public Builder type(String type) {
            this.typeKey = typeKey;
            return this;
        }

        @Override
        public String getState() {
            return stateKey;
        }

        public Builder state(String state) {
            this.stateKey = stateKey;
            return this;
        }
    }
}
