/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.dto;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class CluSetInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo desc;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private CluCriteria cluCriteria;

    @XmlElement
    private List<String> cluSetIds;

    @XmlElement
    private List<String> cluIds;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String,String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CluCriteria getCluCriteria() {
        return cluCriteria;
    }

    public void setCluCriteria(CluCriteria cluCriteria) {
        this.cluCriteria = cluCriteria;
    }

    public List<String> getCluSetIds() {
        if (cluSetIds == null) {
            cluSetIds = new ArrayList<String>();
        }
        return cluSetIds;
    }

    public void setCluSetIds(List<String> cluSetIds) {
        this.cluSetIds = cluSetIds;
    }

    public List<String> getCluIds() {
        if (cluIds == null) {
            cluIds = new ArrayList<String>();
        }
        return cluIds;
    }

    public void setCluIds(List<String> cluIds) {
        this.cluIds = cluIds;
    }

    public Map<String,String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String,String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String,String> attributes) {
        this.attributes = attributes;
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}