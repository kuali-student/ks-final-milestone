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
import javax.xml.bind.annotation.XmlElement;

/**
 * Defines a relationship between two attributes in a search.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:49 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/searchRelationshipInfo+Structure+v1.0-rc2">SearchRelationshipInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchRelationshipInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private TypeAttributeInfo lhs;

    @XmlElement
    private TypeAttributeInfo rhs;

    /**
     * Identifies an attribute of a type.
     */
    public TypeAttributeInfo getLhs() {
        return lhs;
    }

    public void setLhs(TypeAttributeInfo lhs) {
        this.lhs = lhs;
    }

    /**
     * Identifies an attribute of a type.
     */
    public TypeAttributeInfo getRhs() {
        return rhs;
    }

    public void setRhs(TypeAttributeInfo rhs) {
        this.rhs = rhs;
    }
}