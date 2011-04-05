/**
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.lum.lu.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.kuali.student.common.dto.SearchParamInfo;


/**
 * Specifies a search for CLU identifiers.
 *
 * @Author Kamal
 * @Since Mon Jan 11 15:21:50 PST 2010
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class MembershipQueryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private String searchTypeKey;

    @XmlElement
    private List<SearchParamInfo> queryParamValueList;


    /**
     * Identifier for a search type.
     */

    public String getSearchTypeKey() {
        return searchTypeKey;
    }

    public void setSearchTypeKey(String searchTypeKey) {
        this.searchTypeKey = searchTypeKey;
    }


    /**
     * List of query parameter values. Not required if the search
     * doesn't extend beyond the included object.
     */

    public List<SearchParamInfo> getQueryParamValueList() {
        if (queryParamValueList == null) {
            queryParamValueList = new ArrayList<SearchParamInfo>(0);
        }

        return queryParamValueList;
    }

    public void setQueryParamValueList(List<SearchParamInfo> queryParamValueList) {
        this.queryParamValueList = queryParamValueList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}