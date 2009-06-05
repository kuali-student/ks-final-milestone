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
package org.kuali.student.core.comment.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;

/**
 * Key/value pair, typically used for information which may vary from the common set of information provided about an object.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:26 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/attributeInfo+Structure+v1.0-rc2">AttributeInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String value;

    @XmlAttribute(name="key")
    private String id;

    /**
     * The value for this dynamic attribute.
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * The identifier for this attribute.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}