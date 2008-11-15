package org.kuali.student.rules.ruleexecution.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class AgendaExecutionResultDTO {

    @XmlElement
	private String id;

    @XmlElement
    private Boolean executionResult = Boolean.FALSE;

    @XmlElement
    private List<ExecutionResultDTO> executionResultList;

    /**
     * Constructor
     */
	public AgendaExecutionResultDTO() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Boolean getExecutionResult() {
		return executionResult;
	}

	public void setExecutionResult(Boolean executionResult) {
		this.executionResult = executionResult;
	}

	public void addExecutionResult(ExecutionResultDTO executionResult) {
		this.executionResultList.add(executionResult);
	}
	
	public List<ExecutionResultDTO> getExecutionResultList() {
		return this.executionResultList;
	}

	public void setExecutionResultList(List<ExecutionResultDTO> executionResultList) {
		this.executionResultList = executionResultList;
	}

}
