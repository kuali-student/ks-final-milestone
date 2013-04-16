/**
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
package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.lum.clu.infc.LuCode;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;

/**
 * Detailed information about learning unit codes.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuCodeInfo", propOrder = {"id",
    "typeKey",
    "descr",
    "value",
    "meta",
    "attributes",
    "_futureElements"})
public class LuCodeInfo extends HasAttributesAndMetaInfo implements LuCode, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String typeKey;
    @XmlElement
    private String value;
    @XmlElement
    private RichTextInfo descr;
    @XmlAnyElement
    private List<Element> _futureElements;

    public LuCodeInfo() {
    }

    public LuCodeInfo(LuCode luCode) {
        super(luCode);
        this.id = luCode.getId();
        this.typeKey = luCode.getTypeKey();
        if (luCode.getDescr() != null) {
            this.descr = new RichTextInfo(luCode.getDescr());
        }
        this.value = luCode.getValue();
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    @Override
    @Deprecated
    public String getType() {
        return typeKey;
    }

    @Deprecated
    public void setType(String typeKey) {
        this.typeKey = typeKey;
    }
}
