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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.infc.Entity;

/**
 * Information about an Entity.
 *
 * @author tom
 */

@SuppressWarnings("serial")
@XmlTransient
public abstract class EntityInfo 
    extends TypeStateEntityInfo 
    implements Entity, Serializable {

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;


    /**
     * Constructs a new EntityInfo.
     */
    public EntityInfo() {
    }

    /**
     * Constructs a new EntityInfo from another Entity.
     *
     * @entity the Entity to copy
     */
    public EntityInfo(Entity entity) {
        super(entity);
        if (entity != null) {
            this.name = entity.getName();
            if (entity.getDescr() != null) {
                this.descr = new RichTextInfo(entity.getDescr());
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }
}
