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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.infc.Entity;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;

@SuppressWarnings("serial")
@XmlTransient
public abstract class EntityInfo extends HasAttributesAndMetaInfo implements Entity, Serializable {

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;

    @XmlAttribute
    private String typeKey;

    @XmlAttribute
    private String stateKey;


    protected EntityInfo() {
        super ();
        name = null;
        descr = null;
        typeKey = null;
        stateKey = null;
    }

    protected EntityInfo(Entity entity) {
        super(entity);
        if (null != entity) {
	        this.name = entity.getName();
	        this.descr = RichTextInfo.getInstance(entity.getDescr());
	        this.typeKey = entity.getTypeKey();
	        this.stateKey = entity.getStateKey();
        }
    }

    protected EntityInfo(String name, RichText descr, String typeKey, String stateKey, Meta meta) {
        super(meta);
        this.name = name; 
        this.descr = RichTextInfo.getInstance(descr);
        this.typeKey = typeKey;
        this.stateKey = stateKey;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public RichText getDescr() {
        return descr;
    }

   
    public void setDescr(RichText descr) {
        this.descr = RichTextInfo.getInstance(descr);
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
}
