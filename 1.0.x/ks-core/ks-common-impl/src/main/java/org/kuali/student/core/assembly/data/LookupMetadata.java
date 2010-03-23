/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.core.assembly.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class LookupMetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;				//unique ID of this lookup
    private String searchTypeId;    //id of search type defined in search xml
    private String name;			//name of this search
    private String desc;
    private String title;   		//advanced search window title

	@XmlElement(name="param")
    @XmlElementWrapper
    private List<LookupParamMetadata> params;
    
    @XmlElement(name="result")
    @XmlElementWrapper
    private List<LookupResultMetadata> results;
    
    private LookupQosMetadata qosMetadata;
    
    private String searchParamIdKey;
    
    private String resultReturnKey;
    
    private String resultDisplayKey;
    
    private String resultSortKey;
    
    private LookupImplMetadata impl;  //FIXME remove after LookupMetadataBank is gone

    // how a search criteria will be used. ADVANCED_CUSTOM is shown on both advanced
	// and custom screens of the advanced search lightbox
    public enum Usage {
        DEFAULT, ADVANCED, CUSTOM, ADVANCED_CUSTOM
    }

    private Usage usage;
    
    public enum Widget {
        NO_WIDGET, SUGGEST_BOX, ADVANCED_LIGHTBOX, DROP_DOWN, BUTTON
    }
    private Widget widget;    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    public List<LookupParamMetadata> getParams() {
        if (params == null) {
            params = new ArrayList<LookupParamMetadata>();
        }
        return params;
    }

    public void setParams(List<LookupParamMetadata> params) {
        this.params = params;
    }

    public List<LookupResultMetadata> getResults() {
        if (results == null) {
            results = new ArrayList<LookupResultMetadata>();
        }
        return results;
    }

    public void setResults(List<LookupResultMetadata> results) {
        this.results = results;
    }

    public String getSearchTypeId() {
        return searchTypeId;
    }

    public void setSearchTypeId(String searchTypeId) {
        this.searchTypeId = searchTypeId;
    }

    public String getResultReturnKey() {
        return resultReturnKey;
    }

    public void setResultReturnKey(String resultReturnKey) {
        this.resultReturnKey = resultReturnKey;
    }

    public String getResultDisplayKey() {
        return resultDisplayKey;
    }

    public void setResultDisplayKey(String resultDisplayKey) {
        this.resultDisplayKey = resultDisplayKey;
    }

    public String getResultSortKey() {
        return resultSortKey;
    }

    public void setResultSortKey(String resultSortKey) {
        this.resultSortKey = resultSortKey;
    }

    public LookupQosMetadata getQosMetadata() {
        return qosMetadata;
    }

    public void setQosMetadata(LookupQosMetadata qosMetadata) {
        this.qosMetadata = qosMetadata;
    }

	public String getSearchParamIdKey() {
		return searchParamIdKey;
	}

	public void setSearchParamIdKey(String searchParamIdKey) {
		this.searchParamIdKey = searchParamIdKey;
	}
    
    public LookupImplMetadata getImpl() {
        return impl;
    }

    public void setImpl(LookupImplMetadata impl) {
        this.impl = impl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
    
    public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(id);
        sb.append(",");        
        sb.append(searchTypeId);
        sb.append(",");
        sb.append(name);
        sb.append(",");
        sb.append(desc);
        return sb.toString();
    }
}
