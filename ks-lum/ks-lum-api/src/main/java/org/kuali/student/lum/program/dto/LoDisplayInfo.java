/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.lum.program.dto;

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

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Detailed information about an LO  and all it's children for application use.
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:56:06 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/loDisplayInfo+Structure">LoDisplayInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LoDisplayInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private LoInfo loInfo;

    @XmlElement
    private List<LoDisplayInfo> loDisplayInfoList;

    @XmlElement
    private String parentRelType;

    @XmlElement
    private String parentLoRelationid;

    @XmlElement
    private List<LoCategoryInfo> loCategoryInfoList;

    /**
     * Detailed information about a learning objective
     */
    public LoInfo getLoInfo() {
        return loInfo;
    }

    public void setLoInfo(LoInfo loInfo) {
        this.loInfo = loInfo;
    }

    /**
     * List of Lo Display information. (info and child relations
     */
    public List<LoDisplayInfo> getLoDisplayInfoList() {
        if (loDisplayInfoList == null) {
            loDisplayInfoList = new ArrayList<LoDisplayInfo>(0);
        }
        return loDisplayInfoList;
    }

    public void setLoDisplayInfoList(List<LoDisplayInfo> loDisplayInfoList) {
        this.loDisplayInfoList = loDisplayInfoList;
    }

    /**
     * Unique identifier for the LO to LO relation type.
     */
    public String getParentRelType() {
        return parentRelType;
    }

    public void setParentRelType(String parentRelType) {
        this.parentRelType = parentRelType;
    }

    /**
     * Unique identifier for a LO to LO relationship.
     */
    public String getParentLoRelationid() {
        return parentLoRelationid;
    }

    public void setParentLoRelationid(String parentLoRelationid) {
        this.parentLoRelationid = parentLoRelationid;
    }

    /**
     * List of learning objective category information.
     */
    public List<LoCategoryInfo> getLoCategoryInfoList() {
        if (loCategoryInfoList == null) {
            loCategoryInfoList = new ArrayList<LoCategoryInfo>(0);
        }
        return loCategoryInfoList;
    }

    public void setLoCategoryInfoList(List<LoCategoryInfo> loCategoryInfoList) {
        this.loCategoryInfoList = loCategoryInfoList;
    }
}