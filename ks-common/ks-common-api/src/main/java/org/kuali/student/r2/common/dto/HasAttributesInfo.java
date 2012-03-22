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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.HasAttributes;

/**
 * Information about a entities with attributes.
 *
 * @author tom
 */

@SuppressWarnings("serial")
@XmlTransient
public abstract class HasAttributesInfo 
    implements HasAttributes, Serializable {

    @XmlElement
    private List<AttributeInfo> attributes;


    /**
     * Constructs a new HasAttributesInfo.
     */
    public HasAttributesInfo() {
    }
    
    /**
     * Constructs a new HasAttributesInfo from another HasAttributes.
     *
     * @param hasAttrs the HasAttributes to copy
     */
    public HasAttributesInfo(HasAttributes hasAttrs) {
        if (null != hasAttrs) {
            attributes = new ArrayList<AttributeInfo>();
            
            if (null != hasAttrs.getAttributes()) {
                for (Attribute attr : hasAttrs.getAttributes()) {
                    attributes.add(new AttributeInfo(attr));
                }
            }
        }
    }
    
    @Override
    public List<AttributeInfo> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<AttributeInfo> attributes) {
        this.attributes = attributes;
    }
    
    public static String getAttributeValue(List<AttributeInfo> attributes, String attributeKey) {
		for (AttributeInfo attrInfo : attributes) {
			 
			if (attrInfo.getKey().equals(attributeKey)) {
				return attrInfo.getValue();
			}
		}
		return null;
	}  
}
