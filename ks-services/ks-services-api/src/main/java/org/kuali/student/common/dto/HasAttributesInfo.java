/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.HasAttributes;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class HasAttributesInfo implements HasAttributes, Serializable {
	
	@XmlElement
    private final List<AttributeInfo> attributes;
	
	protected HasAttributesInfo() {
		attributes = null;
	}
	
	protected HasAttributesInfo(HasAttributes builder) {
		attributes = new ArrayList<AttributeInfo>();
		
		AttributeInfo.Builder attBuilder = new AttributeInfo.Builder();
		for (AttributeInfc att : builder.getAttributes()) {
			attributes.add(attBuilder.key(att.getKey()).value(att.getValue()).id(att.getId()).build());
		}
	}

    /**
     * @return the attributes
     */
    @Override
    public List<AttributeInfo> getAttributes() {
        return attributes;
    }
    
    public static class Builder implements HasAttributes {
    	private List<? extends AttributeInfc> attributes = new ArrayList<AttributeInfo>();
    	
    	public Builder() {}
    	
    	public Builder(HasAttributes hasAtts) {
    		this.attributes = hasAtts.getAttributes();
		}
    	
		@Override
		public List<? extends AttributeInfc> getAttributes() {
			return attributes;
		}
		
        public Builder attributes(List<? extends AttributeInfc> attributes) {
            this.attributes = attributes;
            return this;
        }
	}
}
