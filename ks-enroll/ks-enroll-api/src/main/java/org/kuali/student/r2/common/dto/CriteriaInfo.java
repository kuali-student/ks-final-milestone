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
package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Comparison;
import org.kuali.student.r2.common.infc.Criteria;
import org.w3c.dom.Element;

/**
 * Query to return some information regarding LUI to person relationships.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue Mar 01 15:54:06 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/luiPersonRelationCriteria+Structure">LuiPersonRelationCriteria</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CriteriaInfo", propOrder = {"comparisons", "maxResults", "_futureElements"})
public class CriteriaInfo implements Criteria, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElementWrapper(name="comparisons")
    @XmlElement(name="comparison")
    private List<ComparisonInfo> comparisons;
    @XmlElement
    private Integer maxResults;
    @XmlAnyElement
    private List<Element> _futureElements;

    public static CriteriaInfo newIntance() {
        return new CriteriaInfo();
    }
    
    private CriteriaInfo() {
        comparisons = null;
        maxResults = null;
        _futureElements = null;
    }

    @Override
    public List<ComparisonInfo> getComparisons() {
        return this.comparisons;
    }

    public void setComparisons(List<? extends Comparison> comparisons) {
        this.comparisons = new ArrayList<ComparisonInfo>();
        for (Comparison comparison : comparisons) {
            this.comparisons.add(ComparisonInfo.getInstance(comparison));
        }
    }

    @Override
    public Integer getMaxResults() {
        return this.maxResults;
    }

    @Override
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    private CriteriaInfo(Criteria builder) {
        if (builder.getComparisons() == null) {
            this.comparisons = null;
        } else {
            List<ComparisonInfo> list = new ArrayList(builder.getComparisons().size());
            for (Comparison infc : builder.getComparisons()) {
                list.add(ComparisonInfo.getInstance(infc));
            }
            this.comparisons = Collections.unmodifiableList(list);
        }
        this.maxResults = builder.getMaxResults();
        this._futureElements = null;
    }
}
