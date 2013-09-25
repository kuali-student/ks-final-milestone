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

package org.kuali.student.r2.core.class1.state.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.class1.state.infc.State;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateInfo", propOrder = {
                "key", "name", "descr", "lifecycleKey", "isInitialState",
                "effectiveDate", "expirationDate", 
                "meta", "attributes" , "_futureElements" }) 

public class StateInfo 
    extends HasAttributesAndMetaInfo 
    implements State, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String key;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;

    @XmlAttribute
    private String lifecycleKey;

    @XmlElement
    private Boolean isInitialState;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Object> _futureElements;  


    /**
     * Constructs a new StateInfo.
     */
    public StateInfo() {
    }

    /**
     * Constructs a new StateInfo from
     * another State.
     */    
    public StateInfo(State state) {
        super(state);

        if(state != null) {
            this.key = state.getKey();
            this.name = state.getName();
            if (state.getDescr() != null) {
                this.descr = new RichTextInfo(state.getDescr());
            }

            this.lifecycleKey = state.getLifecycleKey();
            this.isInitialState = state.getIsInitialState();
            this.effectiveDate = null != state.getEffectiveDate() ? new Date(state.getEffectiveDate().getTime()) : null;
            this.expirationDate = null != state.getExpirationDate() ? new Date(state.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getLifecycleKey() {
        return lifecycleKey;
    }

    @Override
    public Boolean getIsInitialState() {
        return this.isInitialState;
    }

    public void setIsInitialState(Boolean initialState) {
        this.isInitialState = initialState;
    }

    public void setLifecycleKey(String lifecycleKey) {
        this.lifecycleKey = lifecycleKey;
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
