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

package org.kuali.student.r2.core.state.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.state.infc.Lifecycle;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LifecycleInfo", propOrder = {
                "key", "name", "descr", "refObjectUri",
                "meta", "attributes", "_futureElements"})

public class LifecycleInfo 
    extends HasAttributesAndMetaInfo
    implements Lifecycle, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String key;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private String refObjectUri;

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

        this.key = lifecycle.getKey();
        if (lifecycle != null) {
            this.name = lifecycle.getName();
            if (lifecycle.getDescr() != null) {
                this.descr = new RichTextInfo(lifecycle.getDescr());
            }
        }

        this.refObjectUri = lifecycle.getRefObjectUri();
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
    public String getRefObjectUri() {
        return refObjectUri;
    }
	
    public void setRefObjectUri(String refObjectUri) {
        this.refObjectUri = refObjectUri;
    }
}
