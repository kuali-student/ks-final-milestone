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
 * Contains a set of criterion structures and/or other criteria sets along with the operation to apply to the set.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:52 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/criteriaSetInfo+Structure+v1.0-rc2">CriteriaSetInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CriteriaSetInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private CriterionInfo criterion;

    @XmlElement
    private CriteriaSetInfo criteria;

    @XmlAttribute
    private String operator;

    /**
     * Contains information about a constraint on a single field in a search.
     */
    public CriterionInfo getCriterion() {
        return criterion;
    }

    public void setCriterion(CriterionInfo criterion) {
        this.criterion = criterion;
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
     * Identifier for operator used in criteria sets. Allowed values are "AND" and "OR".
     */
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}