/**
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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Attribute;
import org.w3c.dom.Element;

/**
 * The DTO for an Attribute.
 *
 * @author Kuali Student Services team
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeInfo", propOrder = {
                "id", "key", "value", "_futureElements"})

public final class AttributeInfo 
    implements Attribute, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String id;
    
    @XmlElement
    private String key;
    
    @XmlElement
    private String value;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new AttributeInfo.
     */
    public AttributeInfo() {
    }

    /**
     * Constructs a new AttributeInfo from another Attribute.
     *
     * @param attribute the attribute to copy
     */
    public AttributeInfo(Attribute attribute) {
        if (attribute != null) {
            this.id = attribute.getId();
            this.key = attribute.getKey();
            this.value = attribute.getValue();
        }
    }

    public AttributeInfo(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AttributeInfo [id=");
		builder.append(id);
		builder.append(", key=");
		builder.append(key);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
    
    
}
