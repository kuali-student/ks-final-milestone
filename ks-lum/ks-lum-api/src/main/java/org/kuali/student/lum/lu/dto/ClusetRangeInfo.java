package org.kuali.student.lum.lu.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClusetRangeInfo implements Serializable, HasAttributes{
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String Id;

    @XmlElement
    private String searchTypeKey;

    @XmlElement
    private String queryParams;
    
    @XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String,String> attributes;
    
    
    public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSearchTypeKey() {
		return searchTypeKey;
	}

	public void setSearchTypeKey(String searchTypeKey) {
		this.searchTypeKey = searchTypeKey;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public Map<String,String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String,String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String,String> attributes) {
        this.attributes = attributes;
    }


}
