/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

/**
 *Detailed information about a single LU statement.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class LuStatementInfo implements Serializable, Idable, HasTypeState, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private StatementOperatorTypeKey operator;

    @XmlElement
    private List<String> luStatementIds;

    @XmlElement
    private List<String> reqComponentIds;

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
    
    @XmlAttribute
    private String parentId;

    @XmlElement
    private LuStatementTypeInfo luStatementType;

    /**
     * Friendly name for the LU statement.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Narrative description for the LU statement.
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Logical operator used to assemble statements. Acceptable values are restricted to AND and OR. This operator applies to both the LU statements and requirement components contained within this statement.
     */
    public StatementOperatorTypeKey getOperator() {
        return operator;
    }

    public void setOperator(StatementOperatorTypeKey operator) {
        this.operator = operator;
    }

    /**
     * List of LU statement identifiers.
     */
    public List<String> getLuStatementIds() {
        if (luStatementIds == null) {
            luStatementIds = new ArrayList<String>();
        }
        return luStatementIds;
    }

    public void setLuStatementIds(List<String> luStatementIds) {
        this.luStatementIds = luStatementIds;
    }

    /**
     * List of requirement component identifiers.
     */
    public List<String> getReqComponentIds() {
        if (reqComponentIds == null) {
            reqComponentIds = new ArrayList<String>();
        }
        return reqComponentIds;
    }

    public void setReqComponentIds(List<String> reqComponentIds) {
        this.reqComponentIds = reqComponentIds;
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
     * Unique identifier for an LU statement type.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * The current status of the LU statement. The values for this field are constrained to those in the luStatementState enumeration. A separate setup operation does not exist for retrieval of the meta data around this value.
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a single LU statement record. This is optional, due to the identifier being set at the time of creation. Once the LU statement has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public LuStatementTypeInfo getLuStatementType() {
		return luStatementType;
	}

	public void setLuStatementType(LuStatementTypeInfo luStatementType) {
		this.luStatementType = luStatementType;
		if(luStatementType != null) {
			setType(luStatementType.getId());
		}
	}

	@Override
	public String toString() {
		return "LuStatementInfo[id=" + id + ", type=" + type + "]";
	}
}
