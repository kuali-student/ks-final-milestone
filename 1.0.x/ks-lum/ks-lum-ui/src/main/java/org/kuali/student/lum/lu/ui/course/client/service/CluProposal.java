/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.ui.course.client.service;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

/**
 * This class should be removed in time, it is replaced by the model DTO pattern
 * 
 * @author Kuali Student Team
 *
 */
@Deprecated
public class CluProposal implements Idable, HasTypeState, Serializable{

	private static final long serialVersionUID = 1L;

	CluInfo cluInfo = null;
    List<CluInfo> activities;
    ProposalInfo proposalInfo = null;
    String workflowId;
    /**
     * @see org.kuali.student.core.dto.Idable#getId()
     */
    @Override
    public String getId() {
        return proposalInfo.getId();
    }
    
    /**
=     * @see org.kuali.student.core.dto.Idable#setId(java.lang.String)
     */
    @Override
    public void setId(String id) {
        proposalInfo.setId(id);
        
    }

    public CluInfo getCluInfo() {
        return cluInfo;
    }

    public void setCluInfo(CluInfo cluInfo) {
        this.cluInfo = cluInfo;
    }

    public List<CluInfo> getActivities() {
        return activities;
    }

    public void setActivities(List<CluInfo> activities) {
        this.activities = activities;
    }

    public ProposalInfo getProposalInfo() {
        return proposalInfo;
    }

    public void setProposalInfo(ProposalInfo proposalInfo) {
        this.proposalInfo = proposalInfo;
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#getState()
     */
    @Override
    public String getState() {
        return proposalInfo.getState();
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#getType()
     */
    @Override
    public String getType() {
        return proposalInfo.getType();
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#setState(java.lang.String)
     */
    @Override
    public void setState(String type) {
        proposalInfo.setState(type);
    }

    /**
     * @see org.kuali.student.core.dto.HasTypeState#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        proposalInfo.setType(type);
    }

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
    
    
}
