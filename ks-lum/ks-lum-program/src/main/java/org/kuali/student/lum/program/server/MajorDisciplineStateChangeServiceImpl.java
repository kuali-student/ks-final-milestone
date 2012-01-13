package org.kuali.student.lum.program.server;

import java.util.Date;
import java.util.List;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.ProgramServiceConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is called whenever the state of a major discipline changes.
 * <p>
 * We have a separate class because the operations need to be marked with the @Transactional annotation.
 * <p>
 * THIS CLASS IS DUPLICATED FOR MAJOR DISCIPLINE, CORE, AND CREDENTIAL PROGRAMS SINCE THERE ARE
 * DIFFERENT SERVICE METHODS FOR EACH OF THESE TYPES (THOUGH THEY ARE SIMILAR)
 * <P>
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Transactional(noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class MajorDisciplineStateChangeServiceImpl implements StateChangeService{

    /**
     * The program service - injected by spring.
     */
    private ProgramService programService;
    private AtpService atpService;
    
    /**
     * This method is called by workflow when the state changes.
     * 
     * @param majorDisciplineId
     * @param state
     * @return
     * @throws Exception
     */
    public void changeState(String majorDisciplineId, String newState) throws Exception {
        // This method will be called from workflow.
        // Since we cannot activate a program from the workflow we do not need to add endEntryTerm and endEnrollTerm
        changeState(null, null, null, majorDisciplineId, newState);
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
    public void changeState(String endEntryTerm, String endEnrollTerm, String endInstAdmitTerm, String majorDisciplineId, String newState) throws Exception {

        // New state must not be null
        if (newState == null){
            throw new InvalidParameterException("new state cannot be null");
		}

        // The version selected in the UI
        MajorDisciplineInfo selectedVersion = programService.getMajorDiscipline(majorDisciplineId);

        // If we are activating this version we need to mark the previous version superseded,
        // update the previous version end terms, and make the selected version current.
        if (newState.equals(DtoConstants.STATE_ACTIVE)) {

            // Update previous versions to superseded and set end terms on previous current version.
        	updatePreviousVersions(selectedVersion, endEntryTerm, endEnrollTerm, endInstAdmitTerm);

            // Update state of all associated objects for current version
            // NOTE: we must update state BEFORE making the version current
            updateMajorDisciplineInfoState(selectedVersion, newState);

            // Make this the current version
            makeCurrent(selectedVersion);
        } else {

            // Update state of all associated objects for current version
            updateMajorDisciplineInfoState(selectedVersion, newState);
        }

      

    }


    /**
     * This method will update the state of this object and all associated objects.
     * <p>
     * It is needed because we need to make separate web service calls to update the state of these objects.
     * 
     * @param majorDisciplineInfo
     * @param newState
     */
    private void updateMajorDisciplineInfoState(MajorDisciplineInfo majorDisciplineInfo, String newState) throws Exception {
        // Update the statement tree
        List<String> programRequirementIds = majorDisciplineInfo.getProgramRequirements();
        updateRequirementsState(programRequirementIds, newState);

        
        // Update any variations 
        List<ProgramVariationInfo> variationList = majorDisciplineInfo.getVariations();
        updateVariationsRequirementsState(variationList, newState);
        
        
        // Update major discipline
        majorDisciplineInfo.setState(newState);
        programService.updateMajorDiscipline(majorDisciplineInfo);
    }

    /**
     * This method will make this version of the major discipline the current one.
     * 
     * @param majorDisciplineInfo
     */
    private void makeCurrent(MajorDisciplineInfo majorDisciplineInfo) throws Exception {

        // Check if this is the current version before trying to make it current
        // (the web service will error if you try to make a version current that is already current)
        VersionDisplayInfo currentVersion = programService.getCurrentVersion(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, majorDisciplineInfo.getVersionInfo().getVersionIndId());

        // If this is not the current version, then make it current
        if (!currentVersion.getSequenceNumber().equals(majorDisciplineInfo.getVersionInfo().getSequenceNumber())) {
            programService.setCurrentMajorDisciplineVersion(majorDisciplineInfo.getId(), null);
        }
    }

    /**
     * This method finds all previous versions of program and sets all previous ACTIVE,APPROVED,DRAFT versions to SUPERSEDED and
     * sets new end terms for previous current version.
 
     * @param majorDisciplineInfo The version of major discipline program being activated
     * @param endEntryTerm The new end entry term to set on previous active version
     * @param endEnrollTerm The new end enroll term to set on previous active version
     * @throws Exception
     */
    private void updatePreviousVersions (MajorDisciplineInfo selectedVersion, String endEntryTerm, String endEnrollTerm, String endInstAdmitTerm) throws Exception {
    	// Get the current version of major discipline given the selected version
    	MajorDisciplineInfo currentVersion = getCurrentVersion(selectedVersion);
    	
    	boolean isSelectedVersionCurrent = selectedVersion.getId().equals(currentVersion.getId());
    	

        // Fix for KSLAB-2382
        // When a version needs to be marked as superseded, the user is prompted with a screen to enter end terms.
        // The screen will place the end terms in the data object and we'll see them here when the changeState method is called.
        // if the end terms are null, we must not have been prompted with the screen, so we do not need to mark previous
        // versions superseded.   
    	// Another way to prevent this bug would be to ask if we are trying to activate a newly created program and,
    	// if so, we should not try to supersede anything (because there should be nothing to supersede). 
        if (endEntryTerm == null && endEnrollTerm == null && endInstAdmitTerm == null ){
            return;
        }
    	//Set the end terms on the current version of major discipline and update it's state to superseded
    	setEndTerms(currentVersion, endEntryTerm, endEnrollTerm, endInstAdmitTerm);
    	updateMajorDisciplineInfoState(currentVersion, DtoConstants.STATE_SUPERSEDED);

		// Loop through all previous active or approved programs and set the state to superseded.
		// We should only need to evaluated versions with sequence number
		// higher than previous active program

		List<VersionDisplayInfo> versions = programService.getVersions(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, 
				selectedVersion.getVersionInfo().getVersionIndId());
		Long startSeq = new Long(1);

		if (!isSelectedVersionCurrent) {
			startSeq = currentVersion.getVersionInfo().getSequenceNumber() + 1;
		}

		for (VersionDisplayInfo versionInfo : versions) {
			boolean isVersionNewerThanCurrentVersion = versionInfo.getSequenceNumber() >= startSeq;
			boolean isVersionSelectedVersion = versionInfo.getSequenceNumber().equals(selectedVersion.getVersionInfo().getSequenceNumber());  
			boolean updateState = isVersionNewerThanCurrentVersion && !isVersionSelectedVersion;
			if (updateState) {
				MajorDisciplineInfo otherProgram = programService.getMajorDiscipline(versionInfo.getId());
				if (otherProgram.getState().equals(DtoConstants.STATE_APPROVED) ||
					otherProgram.getState().equals(DtoConstants.STATE_ACTIVE)){
			        updateMajorDisciplineInfoState(otherProgram, DtoConstants.STATE_SUPERSEDED);
				}		
			}
		}    	

    }
    
	/**
	 * Get the current version of program given the selected version of program
	 * 
	 * @param verIndId
	 */
	protected MajorDisciplineInfo getCurrentVersion(MajorDisciplineInfo majorDisciplineInfo)
			throws Exception {
		// Get version independent id of program
		String verIndId = majorDisciplineInfo.getVersionInfo().getVersionIndId();

		// Get id of current version of program given the version independent id
		VersionDisplayInfo curVerDisplayInfo = programService.getCurrentVersion(
				ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, verIndId);
		String curVerId = curVerDisplayInfo.getId();

		// Return the current version of the course
		MajorDisciplineInfo currentVersion = programService.getMajorDiscipline(curVerId);

		return currentVersion;
	}
    
    /**
     * This method updates the end terms for the major discipline passed into it.
     * <p>
     * You must still call updateState() to save the object using the web service.
     * 
     * @param majorDisciplineInfo
     * @param endEntryTerm
     * @param endEnrollTerm
     * @param endInstAdmitTerm 
     * @throws OperationFailedException 
     * @throws MissingParameterException 
     * @throws InvalidParameterException 
     * @throws DoesNotExistException 
     */
    private void setEndTerms(MajorDisciplineInfo majorDisciplineInfo, String endEntryTerm, String endEnrollTerm, String endInstAdmitTerm) throws InvalidParameterException, MissingParameterException, OperationFailedException, DoesNotExistException {
        
    	//Set the end terms on the major discipline
    	majorDisciplineInfo.setEndProgramEntryTerm(endEntryTerm);
        majorDisciplineInfo.setEndTerm(endEnrollTerm);
        majorDisciplineInfo.getAttributes().put("endInstAdmitTerm", endInstAdmitTerm);
        
        //Check if there are variations to process
        if(!majorDisciplineInfo.getVariations().isEmpty()){
        	
        	//Find the major's end term atps and obtain their date information
   			AtpInfo majorEndEntryTermAtp = atpService.getAtp(endEntryTerm);
   			Date majorEndEntryTermEndDate = majorEndEntryTermAtp.getEndDate();
   			AtpInfo majorEndEnrollTermAtp = atpService.getAtp(endEnrollTerm);
   			Date majorEndEnrollTermEndDate = majorEndEnrollTermAtp.getEndDate();
       		AtpInfo majorEndInstAdmitTermAtp = atpService.getAtp(endInstAdmitTerm);
       		Date majorEndInstAdmitTermEndDate = majorEndInstAdmitTermAtp.getEndDate();
    
       		//Loop through the variations
	        for(ProgramVariationInfo variation:majorDisciplineInfo.getVariations()){
	        	//compare dates to get the older of the two end terms
	    		if(variation.getEndProgramEntryTerm() != null){
	    			AtpInfo variationEndEntryTermAtp = atpService.getAtp(variation.getEndProgramEntryTerm());
	    			Date variationEndEntryTermEndDate = variationEndEntryTermAtp.getEndDate();
	    			if(majorEndEnrollTermEndDate.compareTo(variationEndEntryTermEndDate)<=0){
		    			variation.setEndProgramEntryTerm(endEntryTerm);
	    			}
	    		}else{
	    			variation.setEndProgramEntryTerm(endEntryTerm);
	    		}
	    		//compare dates to get the older of the two end terms
	    		if(variation.getEndTerm() != null){
	    			AtpInfo variationEndTermAtp = atpService.getAtp(variation.getEndTerm());
	    			Date variationEndTermEndDate = variationEndTermAtp.getEndDate();
	    			if(majorEndEntryTermEndDate.compareTo(variationEndTermEndDate)<=0){
		    			variation.setEndTerm(endEnrollTerm);
	    			}
	    		}else{
	    			variation.setEndTerm(endEnrollTerm);
	    		}
	    		//compare dates to get the older of the two end terms
	    		if(variation.getAttributes().get("endInstAdmitTerm") != null){
	    			AtpInfo variationEndInstAdmitAtp = atpService.getAtp(variation.getAttributes().get("endInstAdmitTerm"));
	    			Date variationEndInstAdmitEndDate = variationEndInstAdmitAtp.getEndDate();
	    			if(majorEndInstAdmitTermEndDate.compareTo(variationEndInstAdmitEndDate)<=0){
	    				variation.getAttributes().put("endInstAdmitTerm", endInstAdmitTerm);
	    			}
	    		}else{
	    			variation.getAttributes().put("endInstAdmitTerm", endInstAdmitTerm);
	    		}
	    		
	        }
        }
    }

    /**
     * This method will update the requirement state.
     * <p>
     * Note that it uses StatementUtil to update the statement tree.
     * 
     * @param majorDisciplineInfo
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
     * This method will update the requirements of each variation.
     * <p>
     * We need to do this here as opposed to in the assemblers, since we need
     * to make calls out to a separate web service.  Also, this needs to be done here
     * because changing state no longer calls the save function.
     * <p>
     * Note that core and credential programs do not have variations so
     * this method isn't necessary.
     * <p>
     * 
     * @param majorDisciplineInfo
     * @param newState
     * @throws Exception
     */
    public void updateVariationsRequirementsState(List<ProgramVariationInfo> variationList, String newState) throws Exception {

        // Iterate over all variations
        for (ProgramVariationInfo variation : variationList) {
     
            // Get the requirements 
            List<String> programRequirementIds = variation.getProgramRequirements();
            
            // Call the method that will update the requirements state for the program
            // This will also update the statement tree
            updateRequirementsState(programRequirementIds, newState);
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
    
    public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}
}
