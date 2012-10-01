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

import org.kuali.student.common.dto.HasAttributes;
import org.kuali.student.common.dto.HasTypeState;
import org.kuali.student.common.dto.Idable;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Detailed information about a single minor program
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:56:15 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/minorDisciplineInfo+Structure">MinorDisciplineInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MinorDisciplineInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String credentialProgramId;

    @XmlElement
    private List<String> programRequirements;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * Identifier of the credential program under which the minor belongs
     */
    public String getCredentialProgramId() {
        return credentialProgramId;
    }

    public void setCredentialProgramId(String credentialProgramId) {
        this.credentialProgramId = credentialProgramId;
    }

    /**
     * Minor Discipline Program Requirements.
     */
    public List<String> getProgramRequirements() {
        if (programRequirements == null) {
            programRequirements = new ArrayList<String>(0);
        }
        return programRequirements;
    }

    public void setProgramRequirements(List<String> programRequirements) {
        this.programRequirements = programRequirements;
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
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    /**
     * Unique identifier for a learning unit type. Once set at create time, this field may not be updated.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the credential program. The values for this field are constrained to those in the luState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for an Minor Discipline. This is optional, due to the identifier being set at the time of creation. Once the Program has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}