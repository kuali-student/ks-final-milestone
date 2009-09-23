package org.kuali.student.lum.lu.dto.workflow;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrincipalIdRoleAttribute implements Serializable {

	private static final long serialVersionUID = -688805585187943857L;
	private String recipientPrincipalId;

	public String getRecipientPrincipalId() {
		return recipientPrincipalId;
	}

	public void setRecipientPrincipalId(String recipientPrincipalId) {
		this.recipientPrincipalId = recipientPrincipalId;
	}
}
