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

package org.kuali.student.r2.lum.lu.dto;

import org.kuali.student.r2.common.dto.SearchParamInfo;
import org.kuali.student.r2.common.infc.SearchParam;
import org.kuali.student.r2.lum.lu.infc.MembershipQuery;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MembershipQueryInfo", propOrder = {"id", "searchTypeKey", "queryParamValueList", "_futureElements"})
public class MembershipQueryInfo implements Serializable, MembershipQuery {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private String searchTypeKey;

    @XmlElement
    private List<SearchParamInfo> queryParamValueList;

    @XmlAnyElement
    private List<Element> _futureElements;

    public MembershipQueryInfo() {

    }

    public MembershipQueryInfo(MembershipQuery membershipQuery) {
        if (null != membershipQuery) {
            this.id = membershipQuery.getId();
            this.searchTypeKey = membershipQuery.getSearchTypeKey();
            this.queryParamValueList = new ArrayList<SearchParamInfo>();
            for (SearchParam searchParam : membershipQuery.getQueryParamValueList()) {
                // Fix after search service is checked-in
                //this.queryParamValueList.add(new SearchParamInfo(searchParam));
                throw new RuntimeException("Insert new SearchParam");
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
    public List<SearchParamInfo> getQueryParamValueList() {
        if (queryParamValueList == null) {
            queryParamValueList = new ArrayList<SearchParamInfo>(0);
        }
        return queryParamValueList;
    }

    public void setQueryParamValueList(List<SearchParamInfo> queryParamValueList) {
        this.queryParamValueList = queryParamValueList;
    }
}