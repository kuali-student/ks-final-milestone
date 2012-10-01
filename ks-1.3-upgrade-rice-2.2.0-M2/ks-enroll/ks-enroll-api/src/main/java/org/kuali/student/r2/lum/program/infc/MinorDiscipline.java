package org.kuali.student.r2.lum.program.infc;


import java.util.List;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

/**
 * Created by IntelliJ IDEA. User: sambitpatnaik Date: 10/27/11 Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MinorDiscipline extends IdNamelessEntity {

    /**
     * Identifier of the credential program under which the minor belongs
     * 
     * @Name Credential Program Id
     * @required
     */
    public String getCredentialProgramId();

    /**
     * Identifiers for Minor Discipline Program Requirements.
     * 
     * @Name Program Requirement Ids
     */
    public List<String> getProgramRequirements();
    

}
