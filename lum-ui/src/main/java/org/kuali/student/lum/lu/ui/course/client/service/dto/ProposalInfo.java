/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.service.dto;

import java.util.List;

import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.RichTextInfo;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class ProposalInfo implements Idable, HasTypeState {
    String id;
    String name;
    RichTextInfo desc;
    String proposerPersonId;
    String proposerOrgId;
    RichTextInfo rationale;
    String type = "clu";
    String state = "proposal";
    List<ProposalReference> proposalReferences;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.core.dto.HasTypeState#getState()
     */
    @Override
    public String getState() {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return state;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.core.dto.HasTypeState#getType()
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.core.dto.HasTypeState#setState(java.lang.String)
     */
    @Override
    public void setState(String type) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.core.dto.HasTypeState#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getProposerPersonId() {
        return proposerPersonId;
    }

    public void setProposerPersonId(String proposerPersonId) {
        this.proposerPersonId = proposerPersonId;
    }

    public String getProposerOrgId() {
        return proposerOrgId;
    }

    public void setProposerOrgId(String proposerOrgId) {
        this.proposerOrgId = proposerOrgId;
    }

    public RichTextInfo getDesc() {
        return desc;
    }

    public void setDesc(RichTextInfo desc) {
        this.desc = desc;
    }

    public RichTextInfo getRationale() {
        return rationale;
    }

    public void setRationale(RichTextInfo rationale) {
        this.rationale = rationale;
    }

    public List<ProposalReference> getProposalReferences() {
        return proposalReferences;
    }

    public void setProposalReferences(List<ProposalReference> proposalReferences) {
        this.proposalReferences = proposalReferences;
    }

    
}
