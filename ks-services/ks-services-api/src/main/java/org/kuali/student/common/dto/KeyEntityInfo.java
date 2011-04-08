/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.common.infc.KeyEntity;
import org.kuali.student.common.dto.EntityInfo;

@SuppressWarnings("serial")
@XmlTransient
public abstract class KeyEntityInfo extends EntityInfo implements KeyEntity, Serializable {

    @XmlAttribute
    private String key;

    protected KeyEntityInfo() {
        key = null;
    }

    protected KeyEntityInfo(KeyEntity builder) {
        super(builder);
        this.key = builder.getKey();
    }

    @Override
    public String getKey() {
        return key;
    }

    /**
     * The builder class for this abstract EntityInfo.
     */

    public static class Builder extends EntityInfo.Builder implements KeyEntity {

        private String key;

        public Builder() {}

        public Builder(KeyEntity entity) {
            super(entity);
            this.key = entity.getKey();
        }

        @Override
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
