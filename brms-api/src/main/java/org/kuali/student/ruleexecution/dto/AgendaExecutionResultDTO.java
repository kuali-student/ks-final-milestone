package org.kuali.student.rules.ruleexecution.dto;

import java.util.ArrayList;
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
    private Boolean executionSuccessful = Boolean.FALSE;

    @XmlElement
    private Boolean agendaReportSuccessful = Boolean.FALSE;

    @XmlElement
    private List<ExecutionResultDTO> executionResultList = new ArrayList<ExecutionResultDTO>();

    @XmlElement
    private String successMessageSummary;

    @XmlElement
    private String failureMessageSummary;

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

	public Boolean isExecutionSuccessful() {
		return this.executionSuccessful;
	}

	public void setExecutionSuccessful(Boolean executionSuccessful) {
		this.executionSuccessful = executionSuccessful;
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

	public String getSuccessMessageSummary() {
		return successMessageSummary;
	}

	public void setSuccessMessageSummary(String successMessageSummary) {
		this.successMessageSummary = successMessageSummary;
	}

	public String getFailureMessageSummary() {
		return failureMessageSummary;
	}

	public void setFailureMessageSummary(String failureMessageSummary) {
		this.failureMessageSummary = failureMessageSummary;
	}

	public Boolean isAgendaReportSuccessful() {
		return agendaReportSuccessful;
	}

	public void setAgendaReportSuccessful(Boolean agendaReportSuccessful) {
		this.agendaReportSuccessful = agendaReportSuccessful;
	}

}
