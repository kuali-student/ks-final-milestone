/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
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
//import org.w3c.dom.Element;

/**
 * Query to return some information regarding LUI to person
 * relationships.
 * 
 * @author Kamal
 * @since Tue Mar 01 15:54:06 PST 2011
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CriteriaInfo", propOrder = { 
                "comparisons", "maxResults" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code

public class CriteriaInfo 
    implements Criteria, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElementWrapper(name = "comparisons")
    @XmlElement(name = "comparison")
    private List<ComparisonInfo> comparisons;
	
    @XmlElement
    private Integer maxResults;
    
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;


    /**
     * Constructs a new CriteriaInfo.
     */
    public CriteriaInfo() {
    }

    /**
     * Constructs a new CriteriaInfo from another Criteria.
     *
     * @param criteria the criteria to copy
     */
    public CriteriaInfo(Criteria criteria) {
        if (criteria != null) {
            if (criteria.getComparisons() != null) {
                this.comparisons = new ArrayList<ComparisonInfo>();
                for (Comparison comparison : criteria.getComparisons()) {
                    this.comparisons.add(new ComparisonInfo(comparison));
                }
            }
            this.maxResults = criteria.getMaxResults();
        }
    }

    @Override
    public List<? extends Comparison> getComparisons() {
        if (comparisons == null) {
            comparisons = new ArrayList<ComparisonInfo>(0);
        }

        return this.comparisons;
    }
    
    public void setComparisons(List<ComparisonInfo> comparisons) {
        this.comparisons = comparisons;
    }

    @Override
    public Integer getMaxResults() {
        return this.maxResults;
    }
    
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
}
