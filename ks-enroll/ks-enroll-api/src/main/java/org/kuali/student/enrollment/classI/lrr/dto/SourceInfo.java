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
package org.kuali.student.enrollment.classI.lrr.dto;

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
 * List of Sources. 
 *
 * @Author KSContractMojo
 * @Author sambit
 * @Since Wed May 04 15:34:16 PDT 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/sourceInfo+Structure">SourceInfo</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String academicRecordId;

    @XmlElement
    private String achievedLoId;

    @XmlElement
    private String assignedCluResultId;

    @XmlElement
    private String desc;

    @XmlElement
    private String articulationId;

    @XmlElement
    private String resultTransformationId;

   

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String id;

    /**
     * The page academicRecordId Structure does not exist.
     */
    public String getAcademicRecordId() {
        return academicRecordId;
    }

    public void setAcademicRecordId(String academicRecordId) {
        this.academicRecordId = academicRecordId;
    }

    /**
     * Unique identifier for an achieved learning objective.
     */
    public String getAchievedLoId() {
        return achievedLoId;
    }

    public void setAchievedLoId(String achievedLoId) {
        this.achievedLoId = achievedLoId;
    }

    /**
     * Unique identifier for an assigned canonical learning unit result record.
     */
    public String getAssignedCluResultId() {
        return assignedCluResultId;
    }

    public void setAssignedCluResultId(String assignedCluResultId) {
        this.assignedCluResultId = assignedCluResultId;
    }

    /**
     * Description
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Unique identifier for an articulation record.
     */
    public String getArticulationId() {
        return articulationId;
    }

    public void setArticulationId(String articulationId) {
        this.articulationId = articulationId;
    }

    /**
     * Unique identifier for an learning result transformation.
     */
    public String getResultTransformationId() {
        return resultTransformationId;
    }

    public void setResultTransformationId(String resultTransformationId) {
        this.resultTransformationId = resultTransformationId;
    }

   

    /**
     * Unique identifier for the type of source for an academic record style record.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Unique identifier for an academic record style result source.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}