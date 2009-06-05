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
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Specifies a search for comment identifiers.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:44 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/commentCriteriaInfo+Structure+v1.0-rc1">CommentCriteriaInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CommentCriteriaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<SearchIndexedTypeInfo> types;

    @XmlElement
    private List<SearchRelationshipInfo> relationships;

    @XmlElement
    private CriteriaSetInfo criteria;

    @XmlElement
    private CriterionInfo criterion;

    /**
     * The types this search should be executed against. A type of "commentInfo" is automatically included. Types are restricted to those within the service.
     */
    public List<SearchIndexedTypeInfo> getTypes() {
        if (types == null) {
            types = new ArrayList<SearchIndexedTypeInfo>(0);
        }
        return types;
    }

    public void setTypes(List<SearchIndexedTypeInfo> types) {
        this.types = types;
    }

    /**
     * List of attribute to attribute relationships. Not required if the search doesn't extend beyond the included object.
     */
    public List<SearchRelationshipInfo> getRelationships() {
        if (relationships == null) {
            relationships = new ArrayList<SearchRelationshipInfo>(0);
        }
        return relationships;
    }

    public void setRelationships(List<SearchRelationshipInfo> relationships) {
        this.relationships = relationships;
    }

    /**
     * Contains a set of criterion structures and/or other criteria sets along with the operation to apply to the set.
     */
    public CriteriaSetInfo getCriteria() {
        return criteria;
    }

    public void setCriteria(CriteriaSetInfo criteria) {
        this.criteria = criteria;
    }

    /**
     * Contains information about a constraint on a single field in a search.
     */
    public CriterionInfo getCriterion() {
        return criterion;
    }

    public void setCriterion(CriterionInfo criterion) {
        this.criterion = criterion;
    }
}