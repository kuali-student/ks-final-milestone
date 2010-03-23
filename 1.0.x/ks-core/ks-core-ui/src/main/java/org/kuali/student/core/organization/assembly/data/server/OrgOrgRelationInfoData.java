package org.kuali.student.core.organization.assembly.data.server;


import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;

public class OrgOrgRelationInfoData {

    public enum ModificationState {
        CREATED,
        UPDATED,
        DELETED
    }
    private OrgOrgRelationInfo orgOrgRelationInfo;

    private ModificationState modificationState;
    
    public OrgOrgRelationInfo getOrgOrgRelationInfo() {
        return orgOrgRelationInfo;
    }
    public void setOrgOrgRelationInfo(OrgOrgRelationInfo orgOrgRelationInfo) {
        this.orgOrgRelationInfo = orgOrgRelationInfo;
    }
    
    public ModificationState getModificationState() {
        return modificationState;
    }
    public void setModificationState(ModificationState modificationState) {
        this.modificationState = modificationState;
    }
}
