package org.kuali.student.lum.lu.dto.workflow;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CluProposalDocInfo implements Serializable {

	private static final long serialVersionUID = -1582972991931504097L;
	private String cluId;
	private String orgId;

	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
