package org.kuali.student.brms.ruleexecution.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class AgendaExecutionResultInfo implements java.io.Serializable {
	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    @XmlElement
	private String id;

    @XmlElement
    private Boolean executionSuccessful = Boolean.FALSE;

    @XmlElement
    private Boolean agendaReportSuccessful = Boolean.FALSE;

    @XmlElement
    private List<ExecutionResultInfo> executionResultList = new ArrayList<ExecutionResultInfo>();

    @XmlElement
    private String successMessageSummary;

    @XmlElement
    private String failureMessageSummary;

    /**
     * Constructor
     */
	public AgendaExecutionResultInfo() {
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

	public void addExecutionResult(ExecutionResultInfo executionResult) {
		this.executionResultList.add(executionResult);
	}
	
	public List<ExecutionResultInfo> getExecutionResultList() {
		return this.executionResultList;
	}

	public void setExecutionResultList(List<ExecutionResultInfo> executionResultList) {
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
