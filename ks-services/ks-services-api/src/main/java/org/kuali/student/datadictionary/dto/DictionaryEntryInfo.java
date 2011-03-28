/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.datadictionary.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.student.datadictionary.infc.AttributeDefinitionInfc;
import org.kuali.student.datadictionary.infc.DictionaryEntryInfc;

/**
 * This is an info ojbect that
 *
 * TODO: Convert to immutable with builder pattern
 *
 * @author nwright
 */
@XmlAccessorType(XmlAccessType.NONE)
public class DictionaryEntryInfo implements DictionaryEntryInfc, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String objectClass;
    @XmlElement
    private String name;
    @XmlElement
    private String objectLabel;
    @XmlElement
    private String objectDescription;
    @XmlElement
    private String titleAttribute;
    @XmlElement
    private List<String> primaryKeys;
    @XmlElement
    private List<AttributeDefinitionInfo> attributes;

    public DictionaryEntryInfo() {
        this.objectClass = null;
        this.name = null;
        this.objectLabel = null;
        this.objectDescription = null;
        this.titleAttribute = null;
        this.primaryKeys = null;
        this.attributes = null;
    }

    private DictionaryEntryInfo(DictionaryEntryInfc infc) {
        this.objectClass = infc.getObjectClass();
        this.name = infc.getName();
        this.objectLabel = infc.getObjectLabel();
        this.objectDescription = infc.getObjectDescription();
        this.titleAttribute = infc.getTitleAttribute();
        if (infc.getPrimaryKeys() != null) {
            this.primaryKeys = Collections.unmodifiableList(infc.getPrimaryKeys());
        }
        if (infc.getAttributes() != null) {
            List<AttributeDefinitionInfo> list = new ArrayList(infc.getAttributes().size());
            for (AttributeDefinitionInfc ad : infc.getAttributes()) {
                list.add(new AttributeDefinitionInfo.Builder(ad).build());
            }
            this.attributes = Collections.unmodifiableList(list);
        }
    }

    @Override
    public List<AttributeDefinitionInfo> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getObjectClass() {
        return objectClass;
    }

    @Override
    public String getObjectDescription() {
        return objectDescription;
    }

    @Override
    public String getObjectLabel() {
        return objectLabel;
    }

    @Override
    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    @Override
    public String getTitleAttribute() {
        return titleAttribute;
    }

    public static class Builder implements DictionaryEntryInfc {

        private String objectClass;
        private String name;
        private String objectLabel;
        private String objectDescription;
        private String titleAttribute;
        private List<String> primaryKeys;
        private List<AttributeDefinitionInfo> attributes;

        public Builder() {
        }

        public Builder(DictionaryEntryInfc infc) {
            this.objectClass = infc.getObjectClass();
            this.name = infc.getName();
            this.objectLabel = infc.getObjectLabel();
            this.objectDescription = infc.getObjectDescription();
            this.titleAttribute = infc.getTitleAttribute();
            this.primaryKeys = new ArrayList(infc.getPrimaryKeys());
            this.attributes = new ArrayList(infc.getAttributes());
        }

        public DictionaryEntryInfo build() {
            return new DictionaryEntryInfo(this);
        }

        @Override
        public List<AttributeDefinitionInfo> getAttributes() {
            return attributes;
        }

        public Builder setAttributes(List<AttributeDefinitionInfo> attributes) {
            this.attributes = attributes;
            return this;
        }

        @Override
        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String getObjectClass() {
            return objectClass;
        }

        public Builder setObjectClass(String objectClass) {
            this.objectClass = objectClass;
            return this;
        }

        @Override
        public String getObjectDescription() {
            return objectDescription;
        }

        public Builder setObjectDescription(String objectDescription) {
            this.objectDescription = objectDescription;
            return this;
        }

        @Override
        public String getObjectLabel() {
            return objectLabel;
        }

        public Builder setObjectLabel(String objectLabel) {
            this.objectLabel = objectLabel;
            return this;
        }

        @Override
        public List<String> getPrimaryKeys() {
            return primaryKeys;
        }

        public Builder setPrimaryKeys(List<String> primaryKeys) {
            this.primaryKeys = primaryKeys;
            return this;
        }

        @Override
        public String getTitleAttribute() {
            return titleAttribute;
        }

        public Builder setTitleAttribute(String titleAttribute) {
            this.titleAttribute = titleAttribute;
            return this;
        }
    }
}
