package org.kuali.student.r2.core.versionmanagement.infc;

import java.util.Date;

/**
 * Detailed display information about a version.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface VersionDisplay {

    /**
     * The unique id of this version record
     * 
     * @name Id
     * @readOnly
     */
    public String getId();
    
    /**
     * Version from which this version was created
     * @name Versioned From Id
     * @readOnly
     */
    public String getVersionedFromId();

    /**
     * URI identifying the object
     * @name Object Type URI
     * @required
     */

    public String getRefObjectUri();

    /**
     * Version independent Id that remains the same across all versions
     * @name Version Ind Id
     * @readOnly
     * @required
     */
    public String getVersionIndId();

    /**
     * The sequence number of the version
     * @name Sequence Number
     * @readOnly
     * @required
     */
    public Long getSequenceNumber();

    /**
     * The date and time this version became current.
     * @name Current Version Start
     * @required
     */
    public Date getCurrentVersionStart();

    /**
     * The date and time when this version stopped being current.
     * @name Current Version End
     */
    public Date getCurrentVersionEnd();

    /**
     * Comments associated with the version
     * @name Version Comment
     */
    public String getVersionComment();

}
