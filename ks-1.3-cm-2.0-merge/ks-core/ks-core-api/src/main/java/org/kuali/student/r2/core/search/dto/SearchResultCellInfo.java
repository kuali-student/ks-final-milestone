/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.search.dto;

import java.util.List;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.search.infc.SearchResultCell;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchResultCellInfo", propOrder = {
                "key", "value"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code })

public class SearchResultCellInfo 
    implements SearchResultCell, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String key;    

    @XmlElement    
    private String value;
    
//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;


    /**
     * Constructs a new SearchResultCellInfo.
     */
    public SearchResultCellInfo() {
    }

    /**
     * Constructs a new SearchResultCellInfo from another
     * SearchResultCell.
     *
     * @param cell the SearchResultCell to copy
     */
    public SearchResultCellInfo(SearchResultCell cell) {
        if (cell != null) {
            this.key = cell.getKey();
            this.value = cell.getValue();
        }
    }
    
    /**
     * Constructs a new SearchResultCellInfo.
     *
     * @param key the key to the search result cell
     * @param value the value of the search result cell
     */
    public SearchResultCellInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    @Override
    public String toString() {
        return "SearchResultCell[key=" + key + ", value=" + value + "]";
    }
}
