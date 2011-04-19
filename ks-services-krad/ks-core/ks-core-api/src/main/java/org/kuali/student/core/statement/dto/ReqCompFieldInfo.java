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

package org.kuali.student.core.statement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;

/**
 *Detailed information about a requirement component field value.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqCompFieldInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String type;

    @XmlElement
    private String value;

    /**
     * Unique identifier for a requirement component field type.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    /**
     * Value for this requirement component field.
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		return "ReqCompFieldInfo[id=" + id + ", value=" + value + "]";
	}
}
