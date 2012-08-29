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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Meta;
//import org.w3c.dom.Element;

/**
 * Information about meta data for entities.
 *
 * @author tom
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetaInfo", propOrder = {
                "versionInd", "createTime", "createId", 
                "updateTime", "updateId" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})

public class MetaInfo 
    implements Meta, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String versionInd;

    @XmlElement
    private Date createTime;

    @XmlElement
    private String createId;

    @XmlElement
    private Date updateTime;

    @XmlElement
    private String updateId;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;


    /**
     * Constructs a new MetaInfo.
     */
    public MetaInfo() {
    }

    /**
     * Constructs a new MetaInfo from another Meta.
     *
     * @param meta the Meta to copy
     */
    public MetaInfo(Meta meta) {
        if (meta != null) {

            this.versionInd = meta.getVersionInd();

            if (meta.getCreateTime() != null) {
                this.createTime = new Date(meta.getCreateTime().getTime());
            }

            this.createId = meta.getCreateId();

            if (meta.getUpdateTime() != null) {
                this.updateTime = new Date(meta.getUpdateTime().getTime());
            }

            this.updateId = meta.getUpdateId();
        }
    }

    @Override
    public String getVersionInd() {
        return versionInd;
    }

    public void setVersionInd(String versionInd) {
        this.versionInd = versionInd;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }


    // Compatibility methods

    @Deprecated
    public static MetaInfo newInstance() {
        return new MetaInfo();
    }
    
    @Deprecated
    public static MetaInfo getInstance(Meta metaInfo) {
        return new MetaInfo(metaInfo);
    }
}
