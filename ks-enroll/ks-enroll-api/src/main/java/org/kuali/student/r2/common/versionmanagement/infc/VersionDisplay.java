package org.kuali.student.r2.common.versionmanagement.infc;

import java.util.Date;

/**
 * Detailed display information about a version.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface VersionDisplay {

    public String getVersionedFromId();

    public String getObjectTypeURI();

    /**
     * Version independent Id that remains the same across all versions
     */
    public String getVersionIndId();

    /**
     * The sequence number of the version
     */
    public Long getSequenceNumber();

    /**
     * The date and time this version became current.
     */
    public Date getCurrentVersionStart();

    /**
     * The date and time when this version stopped being current.
     */
    public Date getCurrentVersionEnd();

    /**
     * Comments associated with the verison
     */
    public String getVersionComment();

}
