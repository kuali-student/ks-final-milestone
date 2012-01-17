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

package org.kuali.student.core.state.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.state.infc.Lifecycle;
import org.kuali.student.common.dto.KeyEntityInfo;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LifecycleInfo", propOrder = {
                "key", "name", "descr", "refObjectUri",
                "effectiveDate", "expirationDate", 
                "meta", "attributes", "_futureElements"})

public class LifecycleInfo 
    extends KeyEntityInfo
    implements Lifecycle, Serializable {
	    
    @XmlElement
    private String refObjectUri;

    @XmlElement
    private Date effectiveDate;
    
    @XmlElement
    private Date expirationDate;
    
    @XmlAnyElement
    private List<Element> _futureElements;    
    

    /**
     * Constructs a new LifecycleInfo.
     */
    public LifecycleInfo() {
    }

    /**
     * Constructs a new LifecycleInfo from
     * another Lifecycle.
     *
     * @param process
     */
		
    public LifecycleInfo(Lifecycle lifecycle) {
        super(lifecycle);
        this.refObjectUri = lifecycle.getRefObjectUri();
        this.effectiveDate = null != lifecycle.getEffectiveDate() ? new Date(lifecycle.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != lifecycle.getExpirationDate() ? new Date(lifecycle.getExpirationDate().getTime()) : null;
    }
	
    @Override
    public String getRefObjectUri() {
        return refObjectUri;
    }
	
    public void setRefObjectUri(String refObjectUri) {
        this.refObjectUri = refObjectUri;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }
	
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }
	
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
