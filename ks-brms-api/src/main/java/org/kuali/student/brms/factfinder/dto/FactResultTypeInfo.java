/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.brms.factfinder.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.brms.factfinder.service.jaxws.adapter.FactResultColumnInfoMapAdapter;
import org.kuali.student.core.dto.TypeInfo;

/**
 * This class defines the properties of the result of a fetchFact call 
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FactResultTypeInfo extends TypeInfo {
    
    private static final long serialVersionUID = 1L;
    @XmlElement
    @XmlJavaTypeAdapter(FactResultColumnInfoMapAdapter.class)
    private Map<String, FactResultColumnInfo> resultColumnsMap;

    /**
     * @return the resultColumnsMap
     */
    public Map<String, FactResultColumnInfo> getResultColumnsMap() {
        return resultColumnsMap;
    }

    /**
     * @param resultColumnsMap the resultColumnsMap to set
     */
    public void setResultColumnsMap(Map<String, FactResultColumnInfo> resultColumnsMap) {
        this.resultColumnsMap = resultColumnsMap;
    }

    public String toString() {
    	return 
    		"(resultColumnsMap=" + this.resultColumnsMap + ")";
   }
}
