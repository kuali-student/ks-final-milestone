/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.lum.lu.dto;
 
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

@XmlAccessorType(XmlAccessType.FIELD)
public class CluCluRelationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<SearchIndexedType> types;

    @XmlElement
    private List<SearchRelationship> relationships;

    @XmlElement
    private CriteriaSet criteria;

    @XmlElement
    private Criterion criterion;

    public List<SearchIndexedType> getTypes() {
        if (types == null) {
            types = new ArrayList<SearchIndexedType>();
        }
        return types;
    }

    public void setTypes(List<SearchIndexedType> types) {
        this.types = types;
    }

    public List<SearchRelationship> getRelationships() {
        if (relationships == null) {
            relationships = new ArrayList<SearchRelationship>();
        }
        return relationships;
    }

    public void setRelationships(List<SearchRelationship> relationships) {
        this.relationships = relationships;
    }

    public CriteriaSet getCriteria() {
        return criteria;
    }

    public void setCriteria(CriteriaSet criteria) {
        this.criteria = criteria;
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public void setCriterion(Criterion criterion) {
        this.criterion = criterion;
    }
}