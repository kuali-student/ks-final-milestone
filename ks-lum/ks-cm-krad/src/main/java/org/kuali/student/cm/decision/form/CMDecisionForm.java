/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by venkat on 7/28/14
 */
package org.kuali.student.cm.decision.form;

import org.kuali.rice.krad.web.form.KsUifFormBase;
import org.kuali.student.cm.decision.form.wrapper.CMDecisionWrapper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public class CMDecisionForm extends KsUifFormBase {

    protected List<CMDecisionWrapper> decisions;
    protected ProposalInfo proposal;


    public CMDecisionForm(){
        decisions = new ArrayList<CMDecisionWrapper>();
    }

    public ProposalInfo getProposal() {
        return proposal;
    }

    public void setProposal(ProposalInfo proposal) {
        this.proposal = proposal;
    }

    public List<CMDecisionWrapper> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<CMDecisionWrapper> decisions) {
        this.decisions = decisions;
    }

}
