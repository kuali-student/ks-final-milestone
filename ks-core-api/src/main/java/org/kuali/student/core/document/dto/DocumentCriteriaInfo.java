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
package org.kuali.student.core.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.search.dto.CriteriaSet;
import org.kuali.student.core.search.dto.Criterion;
import org.kuali.student.core.search.dto.SearchIndexedType;
import org.kuali.student.core.search.dto.SearchRelationship;

/**
 * Specifies a search for document identifiers.
 *
 * @Author KSContractMojo
 * @Since Tue Jun 16 13:58:32 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/documentCriteriaInfo+Structure+v1.0-rc1">DocumentCriteriaInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentCriteriaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<SearchIndexedType> types;

    @XmlElement
    private List<SearchRelationship> relationships;

    @XmlElement
    private CriteriaSet criteria;

    @XmlElement
    private Criterion criterion;

    /**
     * The types this search should be executed against. A type of "documentInfo" is automatically included. Types are restricted to those within the service.
     */
    public List<SearchIndexedType> getTypes() {
        if (types == null) {
            types = new ArrayList<SearchIndexedType>(0);
        }
        return types;
    }

    public void setTypes(List<SearchIndexedType> types) {
        this.types = types;
    }

    /**
     * List of attribute to attribute relationships. Not required if the search doesn't extend beyond the included object.
     */
    public List<SearchRelationship> getRelationships() {
        if (relationships == null) {
            relationships = new ArrayList<SearchRelationship>(0);
        }
        return relationships;
    }

    public void setRelationships(List<SearchRelationship> relationships) {
        this.relationships = relationships;
    }

    /**
     * Contains a set of criterion structures and/or other criteria sets along with the operation to apply to the set.
     */
    public CriteriaSet getCriteria() {
        return criteria;
    }

    public void setCriteria(CriteriaSet criteria) {
        this.criteria = criteria;
    }

    /**
     * Contains information about a constraint on a single field in a search.
     */
    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }
}