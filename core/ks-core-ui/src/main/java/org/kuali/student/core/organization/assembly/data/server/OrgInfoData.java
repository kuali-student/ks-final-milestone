package org.kuali.student.core.organization.assembly.data.server;

import org.kuali.student.core.organization.dto.OrgInfo;

public class OrgInfoData {
	public enum ModificationState {
		CREATED,
		UPDATED,
		DELETED
	}
	private OrgInfo orgInfo;

	private ModificationState modificationState;
	
	public OrgInfo getOrgInfo() {
		return orgInfo;
	}
	public void setOrgInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}
	
	public ModificationState getModificationState() {
		return modificationState;
	}
	public void setModificationState(ModificationState modificationState) {
		this.modificationState = modificationState;
	}
	
	
}
