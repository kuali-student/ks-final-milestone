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

package org.kuali.student.brms.ruleexecution.dto;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * This class contains a map of parameter.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ParameterMapInfo implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)    
	private Map<String,String> paramMap = new HashMap<String, String>();
	
    public ParameterMapInfo() {
    }

    public ParameterMapInfo(Map<String,String> paramMap) {
    	this.paramMap = paramMap;
    }
    
    public Map<String,String> getParameterMap() {
    	return this.paramMap;
    }
    
    public void addParameter(String key, String value) {
    	this.paramMap.put(key, value);
    }
    
    public String getParameter(String key) {
    	return this.paramMap.get(key);
    }
}
