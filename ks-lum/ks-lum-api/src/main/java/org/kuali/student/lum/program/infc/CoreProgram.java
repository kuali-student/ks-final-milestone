package org.kuali.student.lum.program.infc;

import org.kuali.student.common.infc.RichText;
import org.kuali.student.common.infc.IdEntity;
import org.kuali.student.lum.course.infc.LoDisplay;

import java.util.List;

/**
 * Detailed information about a core program requirements associated with
 * Credential Programs
 * 
 * @author Kuali Student Team (Sambit)
 */
public interface CoreProgram extends ProgramAttributes {

    

    /**
     * An URL for additional information about the Core Requirement.
     * 
     * @name reference URL
     */

    public String getReferenceURL();

   

}
