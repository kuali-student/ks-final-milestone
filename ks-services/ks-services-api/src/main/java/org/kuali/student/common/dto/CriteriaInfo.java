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
package org.kuali.student.common.dto;

import org.kuali.student.common.infc.ComparisonInfc;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.kuali.student.common.infc.CriteriaInfc;
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
@XmlType(name = "CriteriaType", propOrder = {"comparisons", "maxResults", "_futureElements"})
public class CriteriaInfo implements CriteriaInfc, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElementWrapper(name="comparisons")
    @XmlElement(name="comparison")
    private final List<ComparisonInfo> comparisons;
    @XmlElement
    private final Integer maxResults;
    @XmlAnyElement
    private final List<Element> _futureElements;
    
    private CriteriaInfo() {
        comparisons = null;
        maxResults = null;
        _futureElements = null;
    }

    @Override
    public List<ComparisonInfo> getComparisons() {
        return this.comparisons;
    }

    @Override
    public Integer getMaxResults() {
        return this.maxResults;
    }

    private CriteriaInfo(CriteriaInfc builder) {
        if (builder.getComparisons() == null) {
            this.comparisons = null;
        } else {
            List<ComparisonInfo> list = new ArrayList(builder.getComparisons().size());
            for (ComparisonInfc infc : builder.getComparisons()) {
                list.add(new ComparisonInfo.Builder(infc).build());
            }
            this.comparisons = Collections.unmodifiableList(list);
        }
        this.maxResults = builder.getMaxResults();
        this._futureElements = null;
    }

    public static class Builder implements CriteriaInfc {

        private List<? extends ComparisonInfc> comparisons;
        private Integer maxResults;

        public Builder() {
        }

        public Builder(CriteriaInfc info) {
            this.comparisons = info.getComparisons();
            this.maxResults = info.getMaxResults();
        }

        public CriteriaInfo build() {
            return new CriteriaInfo(this);
        }

        @Override
        public List<? extends ComparisonInfc> getComparisons() {
            return this.comparisons;
        }

        @Override
        public Integer getMaxResults() {
            return this.maxResults;
        }

        public Builder setComparisons(List<? extends ComparisonInfc> comparisons) {
            this.comparisons = comparisons;
            return this;
        }

        public Builder setMaxResults(Integer maxResults) {
            this.maxResults = maxResults;
            return this;
        }


		
		// Add setters as needed
    }
}
