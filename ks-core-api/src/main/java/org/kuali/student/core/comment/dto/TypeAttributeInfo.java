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
 * Identifies an attribute of a type.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:50 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/typeAttributeInfo+Structure+v1.0-rc2">TypeAttributeInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TypeAttributeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private SearchIndexedTypeInfo parentType;

    @XmlElement
    private String name;

    @XmlElement
    private String alias;

    /**
     * Identifier and alias for a type, used in a search.
     */
    public SearchIndexedTypeInfo getParentType() {
        return parentType;
    }

    public void setParentType(SearchIndexedTypeInfo parentType) {
        this.parentType = parentType;
    }

    /**
     * Identifier for an indexed attribute. Unlike most identifiers, this is not guaranteed to be unique except within a given type.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Alias of the indexed attribute.
     */
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}