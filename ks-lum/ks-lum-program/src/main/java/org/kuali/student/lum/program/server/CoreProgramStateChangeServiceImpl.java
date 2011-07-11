package org.kuali.student.lum.program.server;

import java.util.List;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.ProgramServiceConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is called whenever the state of a major discipline changes.
 * <p>
 * We have a separate class because the operations need to be marked with the @Transactional annotation.
 * <p>
 * THIS CLASS IS DUPLICATED FOR MAJOR DISCIPLINE, CORE, AND CREDENTIAL PROGRAMS SINCE THERE ARE DIFFERENT SERVICE METHODS FOR
 * EACH OF THESE TYPES (THOUGH THEY ARE SIMILAR)
 * <P>
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Transactional(noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class CoreProgramStateChangeServiceImpl  implements StateChangeService {

    
    private ProgramService programService;

    /**
     * This method is called by workflow when the state changes.
     * 
     * @param majorDisciplineId
     * @param state
     * @return
     * @throws Exception
     */
    public void changeState(String coreProgramId, String newState) throws Exception {
        // This method will be called from workflow.
        // Since we cannot activate a program from the workflow we do not need to add endEntryTerm and endEnrollTerm
        changeState(null, null, coreProgramId, newState);
    }

    /**
     * This method is called from the UI (servlet) when state changes.
     * 
     * @param endEntryTerm
     * @param endEnrollTerm
     * @param programType
     * @param majorDisciplineId
     * @param newState
     * @return
     * @throws Exception
     */
    public void changeState(String endEntryTerm, String endEnrollTerm, String coreProgramId, String newState) throws Exception {

        // New state must not be null
        if (newState == null)
            throw new InvalidParameterException("new state cannot be null");

        // The version selected in the UI
        CoreProgramInfo selectedVersion = programService.getCoreProgram(coreProgramId);

        // If we are activating this version we need to mark the previous version superseded,
        // update the previous version end terms, and make the selected version current.
        if (newState.equals(DtoConstants.STATE_ACTIVE)) {

            // Find the previous version
            CoreProgramInfo previousVersion = findPreviousVersion(selectedVersion);

            if (previousVersion != null) {

                // Set end terms on previous version
                setEndTerms(previousVersion, endEntryTerm, endEnrollTerm);

                // Mark previous version as superseded and update state on all associated objects
                updateCoreProgramInfoState(previousVersion, DtoConstants.STATE_SUPERSEDED);
            }

            // Update state of all associated objects for current version
            // NOTE: we must update state BEFORE making the version current
            updateCoreProgramInfoState(selectedVersion, newState);

            // Make this the current version
            makeCurrent(selectedVersion);
        } else {

            // Update state of all associated objects for current version
            updateCoreProgramInfoState(selectedVersion, newState);
        }

    }

    /**
     * This method updates the end terms for the major discipline passed into it.
     * <p>
     * You must still call updateState() to save the object using the web service.
     * 
     * @param coreProgramInfo
     * @param endEntryTerm
     * @param endEnrollTerm
     */
    private void setEndTerms(CoreProgramInfo coreProgramInfo, String endEntryTerm, String endEnrollTerm) {
        coreProgramInfo.setEndProgramEntryTerm(endEntryTerm);
        coreProgramInfo.setEndTerm(endEnrollTerm);
    }

    /**
     * This method will update the state of this object and all associated objects.
     * <p>
     * It is needed because we need to make separate web service calls to update the state of these objects.
     * 
     * @param coreProgramInfo
     * @param newState
     */
    private void updateCoreProgramInfoState(CoreProgramInfo coreProgramInfo, String newState) throws Exception {
        // Update the statement tree
        List<String> programRequirementIds = coreProgramInfo.getProgramRequirements();
        updateRequirementsState(programRequirementIds, newState);
        
        // Credential and core programs do not have variations
 

        // Update major discipline
        coreProgramInfo.setState(newState);
        programService.updateCoreProgram(coreProgramInfo);
    }

    /**
     * This method will make this version of the major discipline the current one.
     * 
     * @param coreProgramInfo
     */
    private void makeCurrent(CoreProgramInfo coreProgramInfo) throws Exception {

        // Check if this is the current version before trying to make it current
        // (the web service will error if you try to make a version current that is already current)
        VersionDisplayInfo currentVersion = programService.getCurrentVersion(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, coreProgramInfo.getVersionInfo().getVersionIndId());

        // If this is not the current version, then make it current
        if (!currentVersion.getSequenceNumber().equals(coreProgramInfo.getVersionInfo().getSequenceNumber())) {
            programService.setCurrentCoreProgramVersion(coreProgramInfo.getId(), null);
        }
    }

    /**
     * This method finds the previous version (the version right before this one).
     * <p>
     * e.g. v1 = ACTIVE v2 = APPROVED
     * <p>
     * If you passed v2 into this method, it would return v1.
     * <p>
     * If there is only one major discipline this method will return null.
     * 
     * @param coreProgramInfo
     * @return
     */
    private CoreProgramInfo findPreviousVersion(CoreProgramInfo coreProgramInfo) throws Exception {
        // Find all previous versions using the version independent indicator
        List<VersionDisplayInfo> versions = programService.getVersions(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, coreProgramInfo.getVersionInfo().getVersionIndId());

        // Take the sequence number for this version
        Long sequenceNumber = coreProgramInfo.getVersionInfo().getSequenceNumber();

        // And subtract 1 from the sequence number to get the previous version
        sequenceNumber -= 1;

        // Loop over all versions and find the previous version based on the sequence number
        /*
         * NOTE: Dan suggested we loop over all versions and change any version with state=active to state=superseded.
         * However, we decided not to go that route because we would need to pull back all data for each version to determine
         * if a version is active, since versioninfo does not have a getState() method
         */
        CoreProgramInfo previousVersion = null;
        for (VersionDisplayInfo versionInfo : versions) {
            if (versionInfo.getSequenceNumber().equals(sequenceNumber)) {
                previousVersion = programService.getCoreProgram(versionInfo.getId());
                break;
            }
        }
        return previousVersion;
    }

    /**
     * This method will update the requirement state.
     * <p>
     * Note that it uses StatementUtil to update the statement tree.
     * 
     * @param coreProgramInfo
     * @param newState
     * @throws Exception
     */
    public void updateRequirementsState(List<String> programRequirementIds, String newState) throws Exception {

        for (String programRequirementId : programRequirementIds) {

            // Get program requirement from the program service
            ProgramRequirementInfo programRequirementInfo = programService.getProgramRequirement(programRequirementId, null, null);

            // Look in the requirement for the statement tree
            StatementTreeViewInfo statementTree = programRequirementInfo.getStatement();

            // And recursively update the entire tree with the new state
            StatementUtil.updateStatementTreeViewInfoState(newState, statementTree);

            // Update the state of the requirement object
            programRequirementInfo.setState(newState);

            // The write the requirement back to the program service
            programService.updateProgramRequirement(programRequirementInfo);

        }
    }

    /**
     * This method is used by Spring to inject the program service into this bean.
     * 
     * @param programService
     */
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

}
