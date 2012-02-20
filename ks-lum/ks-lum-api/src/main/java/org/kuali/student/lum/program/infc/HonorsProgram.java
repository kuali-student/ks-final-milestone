package org.kuali.student.lum.program.infc;

import java.util.List;

import org.kuali.student.common.infc.IdEntity;

/**
 * Detailed information about a single honors program
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface HonorsProgram extends IdEntity {
    /**
     * Identifier of the credential program under which the honors belongs
     * 
     * @name Credential Program Id
     * @required
     */
    public String getCredentialProgramId();

    /**
     * 
     * @name Honors Program Requirements.
     */
    public List<String> getProgramRequirements();

}
