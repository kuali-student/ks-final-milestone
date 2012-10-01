package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r1.core.proposal.service.ProposalService;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.core.statement.ui.client.widgets.rules.ReqComponentInfoUi;
import org.kuali.student.core.statement.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel.requirementState;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsSummaryView;
import org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcService;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

public class MajorDisciplineRpcServlet extends DataGwtServlet implements MajorDisciplineRpcService {

    public static final String PREVIOUS_VERSION_INFO = "proposal";

    final Logger LOG = Logger.getLogger(MajorDisciplineRpcServlet.class);

    private static final long serialVersionUID = 1L;

    private ProposalService proposalService;
    private ProgramService programService;
    private StatementService statementService;
    protected StateChangeService stateChangeService;
    private CluService cluService;

    /**
     * 
     * This method will update the state of a major discipline.
     * 
     * @see org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcService#updateStatus(org.kuali.student.common.assembly.data.Data,
     * java.lang.String)
     */
    public DataSaveResult updateState(Data data, String state) throws Exception {
        try {
            // Pull program ID from model
            String programId = data.get(ProgramConstants.ID);

            // Pull endEntryTerm and endEnrollTerm from model
            // These are set using drop downs when a program is activated
            Data previousVersionInfo = data.query(PREVIOUS_VERSION_INFO);
            String endEntryTerm = null;
            String endEnrollTerm = null;
            String endInstAdmitTerm = null;
            if (previousVersionInfo != null) {
                endEntryTerm = previousVersionInfo.get(ProgramConstants.PREV_END_PROGRAM_ENTRY_TERM);
                endEnrollTerm = previousVersionInfo.get(ProgramConstants.PREV_END_PROGRAM_ENROLL_TERM);
                endInstAdmitTerm = previousVersionInfo.get(ProgramConstants.PREV_END_INST_ADMIN_TERM);
                stateChangeService.changeState(endEntryTerm, endEnrollTerm, endInstAdmitTerm, programId, state);
            }
            else {
                // previousVersionInfo is null if this is the first version 
                stateChangeService.changeState(programId, state);
            }

            // Return updates to view
            DataSaveResult result = new DataSaveResult();
            result.setValue(data);
            return result;
        } catch (DataValidationErrorException e) {
            LOG.error("Error Updating Major Dicipline State", e);
            DataSaveResult result = new DataSaveResult();
            result.setValidationResults(e.getValidationResults());
            return result;
        } catch (Exception e) {
            LOG.error("Error Updating Major Dicipline State", e);
            throw e;
        }

    }

    public List<ProgramRequirementInfo> getProgramRequirements(List<String> programRequirementIds) throws Exception {
        try
        {
            List<ProgramRequirementInfo> programReqInfos = new ArrayList<ProgramRequirementInfo>();

            for (String programReqId : programRequirementIds) {
                ProgramRequirementInfo rule = null;
                rule = programService.getProgramRequirement(programReqId, ContextUtils.getContextInfo());
                setProgReqNL(rule);
                programReqInfos.add(rule);
            }

            return programReqInfos;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public Map<Integer, ProgramRequirementInfo> storeProgramRequirements(
            Map<Integer, ProgramRequirementsDataModel.requirementState> states,
            Map<Integer, ProgramRequirementInfo> progReqs) throws Exception {
        Map<Integer, ProgramRequirementInfo> storedRules = new HashMap<Integer, ProgramRequirementInfo>();
        try
        {
            for (Integer key : progReqs.keySet()) {
                ProgramRequirementInfo rule = progReqs.get(key);
                switch (states.get(key)) {
                    case STORED:
                        //rule was not changed so continue
                        storedRules.put(key, null);
                        break;
                    case ADDED:
                        storedRules.put(key, createProgramRequirement(rule));
                        break;
                    case EDITED:
                        storedRules.put(key, updateProgramRequirement(rule));
                        break;
                    case DELETED:
                        storedRules.put(key, null);
                        deleteProgramRequirement(rule.getId());
                        break;
                    default:
                        break;
                }
            }
            return storedRules;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public ProgramRequirementInfo createProgramRequirement(ProgramRequirementInfo programRequirementInfo)
            throws Exception {
        try
        {
            // If this requirement is using a temporary statement ID set the state to null
            if (programRequirementInfo.getId().indexOf(ProgramRequirementsSummaryView.NEW_PROG_REQ_ID) >= 0) {
                programRequirementInfo.setId(null);
            }

            // Strip the temporary statement IDs and allow permanent IDs to be created when written to the web service
            StatementUtil.stripStatementIds(programRequirementInfo.getStatement());

            // Update the state of the statement tree to match the state of the requirement
            // Note: the requirement state already matches the program state (e.g. Draft, Approved, etc)
            StatementUtil.updateStatementTreeViewInfoState(programRequirementInfo.getStateKey(),
                    programRequirementInfo.getStatement());

            // Call the web service to create the requirement and statement tree in the database
            ProgramRequirementInfo rule = programService.createProgramRequirement(programRequirementInfo.getId(),
                    programRequirementInfo, ContextUtils.getContextInfo());

            // Translate the requirement into its natural language equivalent
            setProgReqNL(rule);

            return rule;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public StatusInfo deleteProgramRequirement(String programRequirementId) throws Exception {
        try
        {
            return programService.deleteProgramRequirement(programRequirementId, ContextUtils.getContextInfo());
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public ProgramRequirementInfo updateProgramRequirement(ProgramRequirementInfo programRequirementInfo)
            throws Exception {
        try
        {
            // Strip the temporary statement IDs and allow permanent IDs to be created when written to the web service
            StatementUtil.stripStatementIds(programRequirementInfo.getStatement());

            // Update the state of the statement tree to match the state of the requirement
            // Note: the requirement state already matches the program state (e.g. Draft, Approved, etc)
            StatementUtil.updateStatementTreeViewInfoState(programRequirementInfo.getStateKey(),
                    programRequirementInfo.getStatement());

            //TODO temporary fix - see KSLUM 1421
            if (programRequirementInfo.getDescr() == null) {
                programRequirementInfo.setDescr(new RichTextInfo());
            }

            ProgramRequirementInfo rule = programService.updateProgramRequirement(null, null, programRequirementInfo,
                    ContextUtils.getContextInfo());
            setProgReqNL(rule);
            return rule;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void setProgReqNL(ProgramRequirementInfo programRequirementInfo) throws Exception {
        setReqCompNL(programRequirementInfo.getStatement());
    }

    private void setReqCompNL(StatementTreeViewInfo tree) throws Exception {
        List<StatementTreeViewInfo> statements = tree.getStatements();
        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();

        if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                setReqCompNL(statement); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
            for (int i = 0; i < reqComponentInfos.size(); i++) {
                ReqComponentInfoUi reqUi = null;
                reqUi = RulesUtil.clone(reqComponentInfos.get(i));
                reqUi.setNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi, "KUALI.RULE",
                        "en"));
                reqUi.setPreviewNaturalLanguageTranslation(statementService.translateReqComponentToNL(reqUi,
                        "KUALI.RULE.PREVIEW", "en"));
                reqComponentInfos.set(i, reqUi);
            }
        }
    }

    @Override
    public Boolean isLatestVersion(String versionIndId, Long versionSequenceNumber) throws Exception {
        try {
            //Perform a search to see if there are any new versions of the course that are approved, draft, etc.
            //We don't want to version if there are
            SearchRequest request = new SearchRequest("lu.search.isVersionable");
            request.addParam("lu.queryParam.versionIndId", versionIndId);
            request.addParam("lu.queryParam.sequenceNumber", versionSequenceNumber.toString());
            List<String> states = new ArrayList<String>();
            states.add("Approved");
            states.add("Active");
            states.add("Draft");
            states.add("Superseded");
            request.addParam("lu.queryParam.luOptionalState", states);
            SearchResult result = cluService.search(request);

            String resultString = result.getRows().get(0).getCells().get(0).getValue();
            return "0".equals(resultString);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public void setStateChangeService(StateChangeService stateChangeService) {
        this.stateChangeService = stateChangeService;
    }

    /**
     * 
     * This method will check to see if an object with the given reference ID is a
     * proposal.
     * <p>
     * At the moment, it is used by the UI to decide if we should hide the action box when
     * opening a draft proposal.
     * 
     * @see org.kuali.student.lum.program.client.rpc.MajorDisciplineRpcService#isProposal(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Boolean isProposal(String referenceTypeKey, String referenceId) {
        try {
            // Wire in proposal service from spring
            // Call method getProposalByReference().  
            // ProposalWorkflowFilter.applyOutboundDataFilter().  Set on line 130-131.  Use these for reference ID.

            // Ask the proposal service to return a list of proposals with this reference id    
            List<ProposalInfo> proposals = null;
            proposals = proposalService.getProposalsByReference(referenceTypeKey, referenceId);

            // If at least one proposal is returned, this is a proposal, so return true
            if (proposals != null && proposals.size() >= 1) {
                return new Boolean(true);
            }

            // This was not a proposal, so return false
            return new Boolean(false);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }

    /**
     * 
     * Proposal service is injected by spring in the lum-gwt-context.xml file
     * 
     * @return
     */
    public ProposalService getProposalService() {
        return proposalService;
    }

    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;

    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

}
