package org.kuali.student.lum.program.server;

import java.util.List;

import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.ProgramServiceConstants;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;
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
public class CredentialProgramStateChangeServiceImpl implements StateChangeService {

    /**
     * The program service - injected by spring.
     */
    private ProgramService programService;

    /**
     * This method is called by workflow when the state changes.
     * 
     * @param majorDisciplineId
     * @param state
     * @return
     * @throws Exception
     */
    public void changeState(String credentialProgramId, String newState) throws Exception {
        // This method will be called from workflow.
        // Since we cannot activate a program from the workflow we do not need to add endEntryTerm and endEnrollTerm
        changeState(null, null, null, credentialProgramId, newState);
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
    public void changeState(String endEntryTerm, String endEnrollTerm, String endInstAdmitTerm, String credentialProgramId, String newState) throws Exception {

        // New state must not be null
        if (newState == null)
            throw new InvalidParameterException("new state cannot be null");

        // The version selected in the UI
        CredentialProgramInfo selectedVersion = programService.getCredentialProgram(credentialProgramId,ContextUtils.getContextInfo());

        // If we are activating this version we need to mark the previous version superseded,
        // update the previous version end terms, and make the selected version current.
        if (newState.equals(DtoConstants.STATE_ACTIVE)) {

            // Update previous versions to superseded and set end terms on previous current version.
        	updatePreviousVersions(selectedVersion, endEntryTerm, endEnrollTerm, endInstAdmitTerm);
        	
            // Update state of all associated objects for current version
            // NOTE: we must update state BEFORE making the version current
            updateCredentialProgramInfoState(selectedVersion, newState);

            // Make this the current version
            makeCurrent(selectedVersion);
        } else {

            // Update state of all associated objects for current version
            updateCredentialProgramInfoState(selectedVersion, newState);
        }

    }

    /**
     * This method finds all previous versions of program and sets all previous ACTIVE,APPROVED,DRAFT versions to SUPERSEDED and
     * sets new end terms for previous current version.
 
     * @param selectedVersion The version of credential  program being activated
     * @param endEntryTerm The new end entry term to set on previous active version
     * @param endEnrollTerm The new end enroll term to set on previous active version
     * @throws Exception
     */
    private void updatePreviousVersions (CredentialProgramInfo selectedVersion, String endEntryTerm, String endEnrollTerm, String endInstAdmitTerm) throws Exception {
    	// Get the current version of major discipline given the selected version
    	CredentialProgramInfo currentVersion = getCurrentVersion(selectedVersion);
    	
    	boolean isSelectedVersionCurrent = selectedVersion.getId().equals(currentVersion.getId());
    	
    	//Set the end terms on the current version of major discipline and update it's state to superseded
    	setEndTerms(currentVersion, endEntryTerm, endEnrollTerm);
    	updateCredentialProgramInfoState(currentVersion, DtoConstants.STATE_SUPERSEDED);

		// Loop through all previous active or approved programs and set the state to superseded.
		// We should only need to evaluated versions with sequence number
		// higher than previous active program

		List<VersionDisplayInfo> versions = programService.getVersions(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI,
		selectedVersion.getVersionInfo(ContextUtils.getContextInfo()).getVersionIndId(),ContextUtils.getContextInfo());
		Long startSeq = new Long(1);

		if (!isSelectedVersionCurrent) {
			startSeq = currentVersion.getVersionInfo(ContextUtils.getContextInfo()).getSequenceNumber() + 1;
		}

		for (VersionDisplayInfo versionInfo : versions) {
			boolean isVersionNewerThanCurrentVersion = versionInfo.getSequenceNumber() >= startSeq;
			boolean isVersionSelectedVersion = versionInfo.getSequenceNumber().equals(selectedVersion.getVersionInfo(ContextUtils.getContextInfo()).getSequenceNumber());  
			boolean updateState = isVersionNewerThanCurrentVersion && !isVersionSelectedVersion;
			if (updateState) {
				CredentialProgramInfo otherProgram = programService.getCredentialProgram(versionInfo.getId(),ContextUtils.getContextInfo());
				if (otherProgram.getStateKey().equals(DtoConstants.STATE_APPROVED) ||
					otherProgram.getStateKey().equals(DtoConstants.STATE_ACTIVE)){
			        updateCredentialProgramInfoState(otherProgram, DtoConstants.STATE_SUPERSEDED);
				}		
			}
		}    	

    }
    
	/**
	 * Get the current version of program given the selected version of program
	 * 
	 * @param verIndId
	 */
	protected CredentialProgramInfo getCurrentVersion(CredentialProgramInfo credentialProgramInfo)
			throws Exception {
		// Get version independent id of program
		String verIndId = credentialProgramInfo.getVersionInfo(ContextUtils.getContextInfo()).getVersionIndId();

		// Get id of current version of program given the version independent id
		VersionDisplayInfo curVerDisplayInfo = programService.getCurrentVersion( ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, verIndId,ContextUtils.getContextInfo());
		String curVerId = curVerDisplayInfo.getId();

		// Return the current version of the course
		CredentialProgramInfo currentVersion = programService.getCredentialProgram(curVerId,ContextUtils.getContextInfo());

		return currentVersion;
	}
    
    /**
     * This method updates the end terms for the major discipline passed into it.
     * <p>
     * You must still call updateState() to save the object using the web service.
     * 
     * @param credentialProgramInfo
     * @param endEntryTerm
     * @param endEnrollTerm
     */
    private void setEndTerms(CredentialProgramInfo credentialProgramInfo, String endEntryTerm, String endEnrollTerm) {
        credentialProgramInfo.setEndProgramEntryTerm(endEntryTerm);
        credentialProgramInfo.setEndTerm(endEnrollTerm);
    }

    /**
     * This method will update the state of this object and all associated objects.
     * <p>
     * It is needed because we need to make separate web service calls to update the state of these objects.
     * 
     * @param credentialProgramInfo
     * @param newState
     */
    private void updateCredentialProgramInfoState(CredentialProgramInfo credentialProgramInfo, String newState) throws Exception {
        // Update the statement tree
        List<String> programRequirementIds = credentialProgramInfo.getProgramRequirements();
        updateRequirementsState(programRequirementIds, newState);
 
        // Credential and core programs do not have variations
        
        // Update program
        credentialProgramInfo.setStateKey(newState);
        programService.updateCredentialProgram(credentialProgramInfo.getId(), credentialProgramInfo, ContextUtils.getContextInfo());
    }

    /**
     * This method will make this version of the major discipline the current one.
     * 
     * @param credentialProgramInfo
     */
    private void makeCurrent(CredentialProgramInfo credentialProgramInfo) throws Exception {

        // Check if this is the current version before trying to make it current
        // (the web service will error if you try to make a version current that is already current)
        VersionDisplayInfo currentVersion = programService.getCurrentVersion(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, credentialProgramInfo.getVersionInfo(ContextUtils.getContextInfo()).getVersionIndId(),ContextUtils.getContextInfo());

        // If this is not the current version, then make it current
        if (!currentVersion.getSequenceNumber().equals(credentialProgramInfo.getVersionInfo(ContextUtils.getContextInfo()).getSequenceNumber())) {
            programService.setCurrentCredentialProgramVersion(credentialProgramInfo.getId(), null,ContextUtils.getContextInfo());
        }
    }

    /**
     * This method will update the requirement state.
     * <p>
     * Note that it uses StatementUtil to update the statement tree.
     * 
     * @param credentialProgramInfo
     * @param newState
     * @throws Exception
     */
    public void updateRequirementsState(List<String> programRequirementIds, String newState) throws Exception {
        
        for (String programRequirementId : programRequirementIds) {

            // Get program requirement from the program service
            ProgramRequirementInfo programRequirementInfo = null;
            programRequirementInfo = programService.getProgramRequirement(programRequirementId, ContextUtils.getContextInfo());

            // Look in the requirement for the statement tree
            StatementTreeViewInfo statementTree = programRequirementInfo.getStatement();

            // And recursively update the entire tree with the new state
            StatementUtil.updateStatementTreeViewInfoState(newState, statementTree);

            // Update the state of the requirement object
            programRequirementInfo.setStateKey(newState);

            // The write the requirement back to the program service
            programService.updateProgramRequirement(programRequirementInfo.getId(), programRequirementInfo.getTypeKey(), programRequirementInfo, ContextUtils.getContextInfo());

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
