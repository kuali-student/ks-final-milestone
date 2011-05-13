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
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetaInfo", propOrder = {"versionInd", "createTime", "createId", "updateTime", "updateId", "_futureElements"})
public class MetaInfo implements Meta, Serializable {

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
    @XmlAnyElement
    private List<Element> _futureElements;

    public static MetaInfo newInstance() {
        return new MetaInfo();
    }
    
    public static MetaInfo getInstance(Meta metaInfo) {
        return new MetaInfo(metaInfo);
    }
    
    private MetaInfo() {
        versionInd = null;
        createTime = null;
        createId = null;
        updateTime = null;
        updateId = null;
        _futureElements = null;
    }

    private MetaInfo(Meta meta) {
        if(meta != null){
            this.versionInd = meta.getVersionInd();
            this.createTime = null != meta.getCreateTime() ? new Date(meta.getCreateTime().getTime()) : null;
            this.createId = meta.getCreateId();
            this.updateTime = null != meta.getUpdateTime() ? new Date(meta.getUpdateTime().getTime()) : null;
            this.updateId = meta.getUpdateId();
            this._futureElements = null;
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
        if (null != createTime) {
	        this.createTime = new Date(createTime.getTime());
        }
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
        if (null != updateTime) {
	        this.updateTime = new Date(updateTime.getTime());
        }
    }

    @Override
    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
}
