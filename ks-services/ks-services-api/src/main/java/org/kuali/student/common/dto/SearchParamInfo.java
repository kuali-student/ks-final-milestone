/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.infc.SearchParamInfc;
import org.w3c.dom.Element;

/**
 * Search Parameter
 *
 * A structure that holds a key value pair to supply a value to a parameter for searching.
 *
 * @author nwright
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchParamType", propOrder = {"key", "values", "_futureElements"})
public class SearchParamInfo implements SearchParamInfc, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlAttribute
    private final String key;
    @XmlElementWrapper(name="values")
    @XmlElement(name="value")
    private final List<String> values;
    @XmlAnyElement
    private final List<Element> _futureElements;

    
    public SearchParamInfo() {
        this.key = null;
        this.values = null;
        this._futureElements = null;
    }

    public SearchParamInfo(SearchParamInfc infc) {
        this.key = infc.getKey();
        if (this.values == null) {
            this.values = null;
        } else {
            this.values = new ArrayList(infc.getValues());
        }
        this._futureElements = null;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    @Override
    public String getKey() {
        return key;
    }

    public static class Builder implements ModelBuilder<SearchParamInfo>, SearchParamInfc {

        private String key;
        private List<String> values;

        @Override
        public List<String> getValues() {
            return values;
        }
        public SearchParamInfo build () {
            return new SearchParamInfo (this);
        }
        /**
         * Convenience method for setting a single value
         * Actually stores it as a list with one value.
         * @param value
         */
        public Builder value(String value) {
            this.values = Arrays.asList(value);
            return this;
        }

        public Builder values(List<String> values) {
            this.values = values;
            return this;
        }

        @Override
        public String getKey() {
            return key;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }
    }
}
