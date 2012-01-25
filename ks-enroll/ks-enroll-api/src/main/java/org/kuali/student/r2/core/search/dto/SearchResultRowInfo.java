/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.search.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.FIELD)

public class SearchResultRowInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SearchResultCellInfo> cells;


    public void addCell(String key, String value) {
        getCells().add(new SearchResultCellInfo(key, value));
    }
    
    public List<SearchResultCellInfo> getCells() {
        if (cells == null) {
            cells = new ArrayList<SearchResultCellInfo>(0);
        }
        return cells;
    }
    
    public void setCells(List<SearchResultCellInfo> cells) {
        this.cells = cells;
    }
    
    @Override
    public String toString() {
        return "SearchResultRow[cells=" + cells + "]";
    }
}
