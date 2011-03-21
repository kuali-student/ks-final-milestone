/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.AttributeInfc;

@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeInfo implements AttributeInfc {

    @XmlElement
    private String id;

    @XmlElement
    private String key;

    @XmlElement
    private String value;

     /**
     * @return the internally assigned id to this key/value pair
     */
    @Override
    public String getId() {
        return key;
    }

    /**
     * @param id to set
     *
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the key
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * @param key
     *            the key to set
     */
    @Override
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }


}
