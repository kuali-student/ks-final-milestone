/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.infc.TypeStateEntity;


/**
 * Information about entities with types and states.
 *
 * @author nwright
 */

@SuppressWarnings("serial")
@XmlTransient
public abstract class TypeStateEntityInfo 
    extends HasAttributesAndMetaInfo 
    implements TypeStateEntity, Serializable {

    @XmlAttribute
    private String typeKey;

    @XmlAttribute
    private String stateKey;


    /**
     * Constructs a new TypeStateEntityInfo.
     */
    public TypeStateEntityInfo() {
    }

    /**
     * Constructs a new TypeStateEntityInfo from another
     * TypeStateEntity.
     *
     * @param entity the TypeStateEntity to copy
     */
    public TypeStateEntityInfo(TypeStateEntity entity) {
        super(entity);
        if (null != entity) {
            this.typeKey = entity.getTypeKey();
            this.stateKey = entity.getStateKey();
        }
    }

    @Override
    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    @Override
    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }


    // Compatibility methods

    @Override
    @Deprecated
    public String getType() {
        return getTypeKey();
    }

    @Deprecated
    public void setType(String typeKey) {
        setTypeKey(typeKey);
    }

    @Override
    @Deprecated
    public String getState() {
        return this.getStateKey();
    }

    @Deprecated
    public void setState(String stateKey) {
        setStateKey(stateKey);
    }
}
