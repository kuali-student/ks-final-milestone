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

package org.kuali.student.r2.lum.clu.dto;


import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.infc.SearchParam;
import org.kuali.student.r2.lum.clu.infc.MembershipQuery;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MembershipQueryInfo", propOrder = {"id", "searchTypeKey", "queryParamValues" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class MembershipQueryInfo implements Serializable, MembershipQuery {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private String searchTypeKey;

    @XmlElement
    private List<SearchParamInfo> queryParamValues;

    //@XmlAnyElement
    //private List<Element> _futureElements;

    public MembershipQueryInfo() {

    }

    public MembershipQueryInfo(MembershipQuery membershipQuery) {
        if (null != membershipQuery) {
            this.id = membershipQuery.getId();
            this.searchTypeKey = membershipQuery.getSearchTypeKey();
            this.queryParamValues = new ArrayList<SearchParamInfo>();
            for (SearchParam searchParam : membershipQuery.getQueryParamValues()) {
                this.queryParamValues.add(new SearchParamInfo(searchParam));
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getSearchTypeKey() {
        return searchTypeKey;
    }

    public void setSearchTypeKey(String searchTypeKey) {
        this.searchTypeKey = searchTypeKey;
    }

    @Override
    public List<SearchParamInfo> getQueryParamValues() {
        if (queryParamValues == null) {
            queryParamValues = new ArrayList<SearchParamInfo>(0);
        }
        return queryParamValues;
    }
    
    public void setQueryParamValues(List<SearchParamInfo> queryParamValues) {
        this.queryParamValues = queryParamValues;
    }
}