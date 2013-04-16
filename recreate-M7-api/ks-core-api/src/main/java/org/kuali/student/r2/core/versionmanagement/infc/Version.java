/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.versionmanagement.infc;

import java.util.Date;

/**
 * Version object
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public interface Version {

    /**
     * Version independent Id that remains the same across all versions
     * @name Version Independent Id
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
     * @name Current Version Start Date
     * @required
     */
    public Date getCurrentVersionStart();

    /**
     * The date and time when this version stopped being current.
     * @name Current Version End Date
     */
    public Date getCurrentVersionEnd();

    /**
     * Comments associated with the version
     * @name Version Comment
     */
    public String getVersionComment();

    /**
     * Version from which this version was created
     *
     * @name Versioned From Id
     * @readOnly
     */
    public String getVersionedFromId();
}

