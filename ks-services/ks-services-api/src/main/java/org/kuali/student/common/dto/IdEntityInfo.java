/*
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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.IdEntity;
import org.kuali.student.common.infc.HasAttributesAndMetaInfc;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)

public abstract class IdEntityInfo 
    extends EntityInfo
    implements IdEntity, 
	       Serializable {

    @XmlAttribute
    private String id;

    
    protected IdEntityInfo() {
        id = null;
    }
    

    protected IdEntityInfo(IdEntity builder) {
	super(builder);
	this.id = builder.getId();
    }


    @Override
    public String getId() {
	return id;
    }


    /**
     * The builder class for this abstract EntityInfo.
     */

    public static class Builder 
	extends EntityInfo.Builder 
	implements IdEntity {
    	
    	private String id;


	public Builder() {}
    	
    	public Builder(IdEntity entity) {
	    super(entity);
	    this.id = entity.getId();
    	}
		
	@Override
	public String getId() {
	    return id;
	}
    	
	public Builder id(String id) {
	    this.id = id;
	    return this;
	}
    }
}
