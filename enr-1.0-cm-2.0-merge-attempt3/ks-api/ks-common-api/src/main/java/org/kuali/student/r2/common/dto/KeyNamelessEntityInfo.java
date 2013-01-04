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

import org.kuali.student.r2.common.infc.KeyNamelessEntity;

/**
 * Provides basic method for a nameless entity. It's the same as an
 * IdEntity without the name and description.
 *
 * @author tom
 */

@SuppressWarnings("serial")
@XmlTransient
public abstract class KeyNamelessEntityInfo 
    extends TypeStateEntityInfo 
    implements KeyNamelessEntity, Serializable {

    @XmlAttribute
    private String key;


    /**
     * Constructs a new KeyNamelessEntityInfo.
     */
    protected KeyNamelessEntityInfo() {
    }

    /**
     * Constructs a new KeyNamelessEntityInfo from another
     * KeyNamelessEntity.
     *
     * @param entity the KeyNamelessEntity to copy
     */
    public KeyNamelessEntityInfo(KeyNamelessEntity entity) {
        super(entity);
        if (entity != null) {
            this.key = entity.getKey();
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
