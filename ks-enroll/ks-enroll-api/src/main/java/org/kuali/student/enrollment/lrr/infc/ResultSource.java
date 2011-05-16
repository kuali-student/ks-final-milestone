package org.kuali.student.enrollment.lrr.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Information about the Result Source. Result source is used to indicate how the result was created (Manual, Articulation, Result transformation etc) 
 */
public interface ResultSource extends IdEntity {

    
    /**
     * Unique identifier for an articulation record.
     * @name Articulation Id     
     */
    public String getArticulationId();
        
    /**
     * Unique identifier for an learning result transformation.
     * @name Result Transformation Id
     */
    public String getResultTransformationId();    
}
