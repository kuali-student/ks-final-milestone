package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.lum.clu.infc.AdminOrg;

import java.util.List;

/**
 * Detailed information about a single credential program, e.g. Baccalaureate,
 * Master, Doctoral, Graduate Certificate, Undergraduate Certificate
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface CredentialProgram extends CommonWithCredentialProgram {

    /**
     * List of core programs related to this credential program
     * 
     * @name Core Program Ids
     */

    public List<String> getCoreProgramIds();

    /**
     * A code that indicates whether this is Graduate, Undergraduage etc
     * 
     * @name Program Level
     */
    public String getProgramLevel();

    /**
     * Institution owning the program.
     * 
     * @name Institution
     */
    public AdminOrg getInstitution();

    /**
     * Result outcomes from taking the Credential program.
     * 
     * @name Result Options
     */
    public List<String> getResultOptions();

}
