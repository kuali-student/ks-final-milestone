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
package org.kuali.student.enrollment.lrr.dto;

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
 * Information about the Learning Result Record Info.
 *
 * @Author KSContractMojo
 * @Author sambit
 * @Since Wed May 04 15:34:13 PDT 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/learningResultRecordInfo+Structure">LearningResultRecordInfo</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LearningResultRecordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;

    @XmlElement
    private String luiId;

    @XmlElement
    private String lprType;

    @XmlAttribute
    private String resultUsageTypeKey;

    @XmlElement
    private String resultComponentId;

    @XmlElement
    private String resultId;

    @XmlElement
    private String atpKey;

    @XmlElement
    private String lprId;

    @XmlElement
    private List<SourceInfo> sourceInfoList;

    @XmlAttribute
    private String state;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;



    @XmlAttribute
    private String id;

    /**
     * person Identifier
     */
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * Any finite sequence of characters with letters, numerals, symbols and punctuation marks. The length can be any natural number between zero or any positive integer.
     */
    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    /**
     * Any finite sequence of characters with letters, numerals, symbols and punctuation marks. The length can be any natural number between zero or any positive integer.
     */
    public String getLprType() {
        return lprType;
    }

    public void setLprType(String lprType) {
        this.lprType = lprType;
    }

    /**
     * Unique identifier for a result usage type. This is immutable once set.
     */
    public String getResultUsageTypeKey() {
        return resultUsageTypeKey;
    }

    public void setResultUsageTypeKey(String resultUsageTypeKey) {
        this.resultUsageTypeKey = resultUsageTypeKey;
    }

    /**
     * Unique identifier for a result component.
     */
    public String getResultComponentId() {
        return resultComponentId;
    }

    public void setResultComponentId(String resultComponentId) {
        this.resultComponentId = resultComponentId;
    }

    /**
     * The page resultId Structure does not exist.
     */
    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    /**
     * Unique identifier for an Academic Time Period (ATP).
     */
    public String getAtpKey() {
        return atpKey;
    }

    public void setAtpKey(String atpKey) {
        this.atpKey = atpKey;
    }

    /**
     * Any finite sequence of characters with letters, numerals, symbols and punctuation marks. The length can be any natural number between zero or any positive integer.
     */
    public String getLprId() {
        return lprId;
    }

    public void setLprId(String lprId) {
        this.lprId = lprId;
    }

    /**
     * List of Sources.
     */
    public List<SourceInfo> getSourceInfoList() {
        if (sourceInfoList == null) {
            sourceInfoList = new ArrayList<SourceInfo>(0);
        }
        return sourceInfoList;
    }

    public void setSourceInfoList(List<SourceInfo> sourceInfoList) {
        this.sourceInfoList = sourceInfoList;
    }

    /**
     * The current status of the Learning Result Record. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }


    /**
     * Unique identifier for an learning result record. This is optional, due to the identifier being set at the time of creation. Once the result definition has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}