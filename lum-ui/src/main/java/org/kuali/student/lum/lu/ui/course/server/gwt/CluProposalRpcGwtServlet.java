/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.dto.ProposalInfo;
import org.kuali.student.lum.lu.ui.course.client.service.dto.ProposalReference;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * @author Kuali Student Team
 */
// Fixme: Replace Object with ProposalService interface class
public class CluProposalRpcGwtServlet extends BaseRpcGwtServletAbstract<Object> implements CluProposalRpcService {

    private static final long serialVersionUID = 1L;
    private LuService service;

    private Map<String, ProposalInfo> getProposalInfoMap() {
        Map<String, ProposalInfo> proposalInfoMap = (Map<String, ProposalInfo>) getThreadLocalRequest().getSession(true).getAttribute("proposal");

        return proposalInfoMap;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#createProposal(org.kuali.student.lum.lu.ui.course.client.service.CluProposal)
     */
    @Override
    public CluProposal createProposal(CluProposal cluProposal) {
        try {
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();
            proposalInfo.setId(UUIDHelper.genStringUUID());

            List<ProposalReference> proposalReferences = new ArrayList<ProposalReference>();

            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.createClu(cluProposal.getCluInfo().getType(), parentCluInfo);

            proposalReferences.add(new ProposalReference("proposal.primary", parentCluInfo.getId()));

            List<CluInfo> activities = cluProposal.getActivities();
            for (CluInfo cluInfo : activities) {

                cluInfo = service.createClu(cluInfo.getType(), cluInfo);
                CluCluRelationInfo relInfo = new CluCluRelationInfo();

                service.createCluCluRelation(parentCluInfo.getId(), cluInfo.getId(), "proposal.actvitiy", relInfo);
                proposalReferences.add(new ProposalReference("proposal.activity", cluInfo.getId()));
            }

            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cluProposal;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#deleteProposal(java.lang.String)
     */
    @Override
    public CluProposal deleteProposal(String id) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#getProposal(java.lang.String)
     */
    @Override
    public CluProposal getProposal(String id) {
        ProposalInfo proposalInfo = getProposalInfoMap().get(id);
        if (proposalInfo != null) {
            try {
                CluProposal cluProposal = new CluProposal();
                cluProposal.setProposalInfo(proposalInfo);

                CluInfo primaryCluInfo = null;
                List<CluInfo> activities = new ArrayList<CluInfo>();
                for (ProposalReference p : proposalInfo.getProposalReferences()) {
                    if (p.getReferenceTypeKey().equals("proposal.primary")) {
                        primaryCluInfo = service.getClu(p.getReferenceTypeId());
                    } else {
                        activities.add(service.getClu(p.getReferenceTypeId()));
                    }
                }

                cluProposal.setCluInfo(primaryCluInfo);
                cluProposal.setActivities(activities);
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
        return null;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#saveProposal(org.kuali.student.lum.lu.ui.course.client.service.CluProposal)
     */
    @Override
    public CluProposal saveProposal(CluProposal cluProposal) {
        try {
            ProposalInfo proposalInfo = cluProposal.getProposalInfo();
                       
            List<ProposalReference> proposalReferences = new ArrayList<ProposalReference>();

            CluInfo parentCluInfo = cluProposal.getCluInfo();
            parentCluInfo = service.updateClu(cluProposal.getCluInfo().getType(), parentCluInfo);

            proposalReferences.add(new ProposalReference("proposal.primary", parentCluInfo.getId()));

            List<CluInfo> activities = cluProposal.getActivities();
            for (CluInfo cluInfo : activities) {

                if (cluInfo.getId() == null){
                    cluInfo = service.createClu(cluInfo.getType(), cluInfo);
                    CluCluRelationInfo relInfo = new CluCluRelationInfo();
                    service.createCluCluRelation(parentCluInfo.getId(), cluInfo.getId(), "proposal.actvitiy", relInfo);
                    proposalReferences.add(new ProposalReference("proposal.activity", cluInfo.getId()));
                }                
            }

            getProposalInfoMap().put(proposalInfo.getId(), proposalInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        return cluProposal;
    }

    /**
     * @see org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService#submitProposal(org.kuali.student.lum.lu.ui.course.client.service.CluProposal)
     */
    @Override
    public CluProposal submitProposal(CluProposal cluProposal) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public LuService getService() {
        return service;
    }

    public void setService(LuService service) {
        this.service = service;
    }

}
