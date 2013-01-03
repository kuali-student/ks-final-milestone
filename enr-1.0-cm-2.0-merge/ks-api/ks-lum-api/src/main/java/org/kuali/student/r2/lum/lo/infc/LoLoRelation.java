package org.kuali.student.r2.lum.lo.infc;

import org.kuali.student.r2.common.infc.Relationship;

public interface LoLoRelation extends Relationship {

    /**
     * Unique identifier for a learning objective record. This is the "From" or
     * "Parent" in the relation.
     */
    public String getLoId();

    /**
     * Unique identifier for a learning objective record. This is the "To" or
     * "Child" of the relation.
     */
    public String getRelatedLoId();

}
