package org.kuali.student.lum.workflow;

import java.util.List;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
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
        changeState(null, null, majorDisciplineId, newState);
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
    public void changeState(String endEntryTerm, String endEnrollTerm, String majorDisciplineId, String newState) throws Exception {

        // New state must not be null
        if (newState == null)
            throw new InvalidParameterException("new state cannot be null");

        // The version selected in the UI
        MajorDisciplineInfo selectedVersion = programService.getMajorDiscipline(majorDisciplineId);

        // If we are activating this version we need to mark the previous version superseded,
        // update the previous version end terms, and make the selected version current.
        if (newState.equals(DtoConstants.STATE_ACTIVE)) {

            // Find the previous version
            MajorDisciplineInfo previousVersion = findPreviousVersion(selectedVersion);

            if (previousVersion != null) {

                // Set end terms on previous version
                setEndTerms(previousVersion, endEntryTerm, endEnrollTerm);

                // Mark previous version as superseded and update state on all associated objects
                updateMajorDisciplineInfoState(previousVersion, DtoConstants.STATE_SUPERSEDED);
            }

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
     * This method updates the end terms for the major discipline passed into it.
     * <p>
     * You must still call updateState() to save the object using the web service.
     * 
     * @param majorDisciplineInfo
     * @param endEntryTerm
     * @param endEnrollTerm
     */
    private void setEndTerms(MajorDisciplineInfo majorDisciplineInfo, String endEntryTerm, String endEnrollTerm) {
        majorDisciplineInfo.setEndProgramEntryTerm(endEntryTerm);
        majorDisciplineInfo.setEndTerm(endEnrollTerm);
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
     * This method finds the previous version (the version right before this one).
     * <p>
     * e.g. v1 = ACTIVE v2 = APPROVED
     * <p>
     * If you passed v2 into this method, it would return v1.
     * <p>
     * If there is only one major discipline this method will return null.
     * 
     * @param majorDisciplineInfo
     * @return
     */
    private MajorDisciplineInfo findPreviousVersion(MajorDisciplineInfo majorDisciplineInfo) throws Exception {
        // Find all previous versions using the version independent indicator
        List<VersionDisplayInfo> versions = programService.getVersions(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI, majorDisciplineInfo.getVersionInfo().getVersionIndId());

        // Take the sequence number for this version
        Long sequenceNumber = majorDisciplineInfo.getVersionInfo().getSequenceNumber();

        // And subtract 1 from the sequence number to get the previous version
        sequenceNumber -= 1;

        // Loop over all versions and find the previous version based on the sequence number
        /*
         * NOTE: Dan suggested we loop over all versions and change any version with state=active to state=superseded.
         * However, we decided not to go that route because we would need to pull back all data for each version to determine
         * if a version is active, since versioninfo does not have a getState() method
         */
        MajorDisciplineInfo previousVersion = null;
        for (VersionDisplayInfo versionInfo : versions) {
            if (versionInfo.getSequenceNumber().equals(sequenceNumber)) {
                previousVersion = programService.getMajorDiscipline(versionInfo.getId());
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

}
