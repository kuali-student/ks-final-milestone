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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.SearchParam;
import org.w3c.dom.Element;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Search Parameter
 *
 * A structure that holds a key value pair to supply a value to a parameter for searching.
 *
 * @author nwright
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchParamInfo", propOrder = {"key", "values", "_futureElements"})
public class SearchParamInfo implements SearchParam, Serializable {

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

    public SearchParamInfo(SearchParam infc) {
        this.key = infc.getKey();
        if (this.values == null) {
            this.values = null;
        } else {
            this.values = new ArrayList<String>();
            Collections.copy(this.values ,infc.getValues());
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
}
