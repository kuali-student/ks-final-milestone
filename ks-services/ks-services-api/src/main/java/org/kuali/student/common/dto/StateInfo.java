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
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.StateInfc;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class StateInfo extends HasAttributesInfo implements StateInfc, Serializable {
	
	@XmlAttribute
	private String key;
	
	@XmlElement
	private String name;
	
	@XmlElement(name ="desc")
	private String descr;

	@XmlElement
	private Date effectiveDate;
	
	@XmlElement
	private Date expirationDate;

    /**
     * @return the key
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
    */
    @Override
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the descr
     */
    @Override
    public String getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    @Override
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * @return the effectiveDate
     */
    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    @Override
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the expirationDate
     */
    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}
