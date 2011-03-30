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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.HasAttributesAndMetaInfc;
import org.kuali.student.common.infc.MetaInfc;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class HasAttributesAndMetaInfo extends HasAttributesInfo
        implements HasAttributesAndMetaInfc, Serializable {

    @XmlElement
    private final MetaInfo metaInfo;
    
    protected HasAttributesAndMetaInfo() {
    	metaInfo = null;
    }
    
	protected HasAttributesAndMetaInfo(HasAttributesAndMetaInfc builder) {
		super(builder);
		this.metaInfo = null != builder.getMetaInfo() ? new MetaInfo.Builder(builder.getMetaInfo()).build() : null;
	}


    /**
     * Name: Create/Update meta info
     * <p/>
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    @Override
    public MetaInfc getMetaInfo() {
        return metaInfo;
    }
    
    public static class Builder extends HasAttributesInfo.Builder implements HasAttributesAndMetaInfc {
    	
    	private MetaInfc metaInfo;
    	
		public Builder() {}
    	
    	public Builder(HasAttributesAndMetaInfc hasAMInfo) {
    		super(hasAMInfo);
    		this.metaInfo = hasAMInfo.getMetaInfo();
    	}
		
		@Override
		public MetaInfc getMetaInfo() {
			return metaInfo;
		}
    	
		public Builder metaInfo(MetaInfc metaInfo) {
			this.metaInfo = metaInfo;
			return this;
		}
    }
}
