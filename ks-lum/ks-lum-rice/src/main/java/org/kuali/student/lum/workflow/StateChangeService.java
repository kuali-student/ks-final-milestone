package org.kuali.student.lum.workflow;

/**
 * This interface allows us to inject a different StateChangeService implementation for each of
 * the program types (credential, core, major discipline) using the spring configuration. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public interface StateChangeService {
    
    /**
     * 
     * This method will eventually be called from the Rice workflow after a major discipline, core, or 
     * credential program passes back into the local workflow.  It executes the same code as the other
     * change state method, but uses null for entry and enroll term, since they will not be available in 
     * the rice workflow (these parameters are entered when a program is superseded)
     * 
     * @param programId
     * @param newState
     * @throws Exception
     */
    public void changeState(String programId, String newState) throws Exception;

    /**
     * 
     * This method is called from the servlet when state is changed in the GWT application (e.g. the user
     * approves, activates, or creates a new major discipline, credential, or core program)
     * 
     * @param endEntryTerm
     * @param endEnrollTerm
     * @param programId
     * @param newState
     * @throws Exception
     */
    public void changeState(String endEntryTerm, String endEnrollTerm, String endInstAdmitTerm, String programId, String newState) throws Exception;
}
