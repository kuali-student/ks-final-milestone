package org.kuali.student.core.assembly.data;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r2.common.dto.AttributeInfo;

public class MockProposal {
    private String id;
    private List<String> orgId;
    private String proposalTitle;
    private String proposalRationale;
    private List<AttributeInfo> attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getOrgId() {
        return orgId;
    }

    public void setOrgId(List<String> orgId) {
        this.orgId = orgId;
    }

    public String getProposalTitle() {
        return proposalTitle;
    }

    public void setProposalTitle(String proposalTitle) {
        this.proposalTitle = proposalTitle;
    }

    public String getProposalRationale() {
        return proposalRationale;
    }

    public void setProposalRationale(String proposalRationale) {
        this.proposalRationale = proposalRationale;
    }

    public List<AttributeInfo> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<AttributeInfo>();
        }
        return attributes;
    }

    public void setAttributes(List<AttributeInfo> attributes) {
        this.attributes = attributes;
    }

}
