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

package org.kuali.student.common.dictionary.old.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class OccursConstraint implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<RequireConstraint> require;

    @XmlElement
    private List<OccursConstraint> occurs;
    
    @XmlAttribute
    private Integer min;
    
    @XmlAttribute
    private Integer max;
    
	/**
	 * @return the require
	 */
	public List<RequireConstraint> getRequire() {
		return require;
	}

	/**
	 * @param require the require to set
	 */
	public void setRequire(List<RequireConstraint> require) {
		this.require = require;
	}

	/**
	 * @return the min
	 */
	public Integer getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(Integer min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * @return the occurs
	 */
	public List<OccursConstraint> getOccurs() {
		return occurs;
	}

	/**
	 * @param occurs the occurs to set
	 */
	public void setOccurs(List<OccursConstraint> occurs) {
		this.occurs = occurs;
	}		
}
