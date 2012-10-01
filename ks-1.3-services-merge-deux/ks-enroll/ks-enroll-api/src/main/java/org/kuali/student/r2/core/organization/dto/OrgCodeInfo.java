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
package org.kuali.student.r2.core.organization.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.organization.infc.OrgCode;
import org.w3c.dom.Element;

/**
 * Detailed information about organization codes.
 *
 * @author tom
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgCodeInfo", propOrder = {
    "key", "value", "descr",
    "meta", "attributes", "_futureElements"})
public class OrgCodeInfo
        extends KeyNamelessEntityInfo
        implements OrgCode, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAnyElement
    private String value;
    @XmlAnyElement
    private RichTextInfo descr;
    @XmlAnyElement
    private List<Element> _futureElements;

    /**
     * Constructs a new OrgCodeInfo.
     */
    public OrgCodeInfo() {
    }

    /**
     * Constructs a new OrgCodeInfo from an OrgCode.
     *
     * @param orgCode the OrgCode to copy
     */
    public OrgCodeInfo(OrgCode orgCode) {
        super(orgCode);
        this.value = orgCode.getValue();
        if (orgCode.getDescr() != null) {
            this.descr = new RichTextInfo (orgCode.getDescr());
        }
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
