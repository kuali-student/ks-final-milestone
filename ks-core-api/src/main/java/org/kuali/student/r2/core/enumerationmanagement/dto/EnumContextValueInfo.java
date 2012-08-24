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

package org.kuali.student.r2.core.enumerationmanagement.dto;

import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.core.enumerationmanagement.infc.EnumContextValue;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnumContextValueInfo", propOrder = {"key", "value", "meta"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class EnumContextValueInfo implements EnumContextValue, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String key;
    @XmlElement
    private String value;
    @XmlElement
    private MetaInfo meta;
    //  TODO KSCM-372: Non-GWT translatable code
    //  @XmlAnyElement
    //    private List<Element> _futureElements;

    public EnumContextValueInfo() {
    }

    public EnumContextValueInfo(EnumContextValue enumContextValue) {
        if (null != enumContextValue) {
            this.meta = new MetaInfo(enumContextValue.getMeta());
            this.key = enumContextValue.getKey();
            this.value = enumContextValue.getValue();
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Meta getMeta() {
        return meta;
    }
    
    public void setMeta(MetaInfo meta) {
        this.meta = meta;
    }
    
}
