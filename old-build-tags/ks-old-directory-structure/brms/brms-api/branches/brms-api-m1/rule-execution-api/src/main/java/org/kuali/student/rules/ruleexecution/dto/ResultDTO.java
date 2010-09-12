package org.kuali.student.rules.ruleexecution.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ResultDTO {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

	@XmlAttribute
    private String id;

	@XmlElement
	private final List<ValueDTO> factList = new ArrayList<ValueDTO>();

    //@XmlElement
    //private String executionLog;
    
	public ResultDTO() {}

	public ResultDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void addResult(String id, Object value) {
		ValueDTO v = new ValueDTO(id, value);
		this.factList.add(v);
	}
	
	public void addResult(ValueDTO value) {
		this.factList.add(value);
	}
	
	public List<ValueDTO> getResults() {
		return this.factList;
	}

	/*public String getExecutionLog() {
		return executionLog;
	}

	public void setExecutionLog(String executionLog) {
		this.executionLog = executionLog;
	}*/

}
