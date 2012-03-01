package org.kuali.student.r2.lum.program.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * Detailed information about a single honors program
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface HonorsProgram extends IdNamelessEntity {
    /**
     * Identifier of the credential program under which the honors belongs
     * 
     * @name Credential Program Id
     * @required
     */
    public String getCredentialProgramId();

    /**
     * Identifiers of the requirements associated with this honors program.
     * 
     * @name Honors Program Requirement Ids
     */
    public List<String> getProgramRequirements();

}
