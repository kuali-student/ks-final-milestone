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
