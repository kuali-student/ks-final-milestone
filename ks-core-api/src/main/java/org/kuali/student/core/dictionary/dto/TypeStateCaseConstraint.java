/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.dictionary.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class TypeStateCaseConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "typeStateWhen")
    protected List<TypeStateWhenConstraint> typeStateWhen;

	/**
	 * @return the typeStateWhen
	 */
	public List<TypeStateWhenConstraint> getTypeStateWhen() {
		return typeStateWhen;
	}

	/**
	 * @param typeStateWhen the typeStateWhen to set
	 */
	public void setTypeStateWhen(List<TypeStateWhenConstraint> typeStateWhen) {
		this.typeStateWhen = typeStateWhen;
	}


}
