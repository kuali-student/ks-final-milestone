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
package org.kuali.student.r2.common.infc;

import java.util.Date;

public interface Version {

    /**
     * Name: Version Idependent Id
     *
     * Version independent Id that remains the same across all versions
     */
    public String getVersionIndId();
    /**
     * Name: Sequence Number
     *
     * The sequence number of the version
     */
    public Long getSequenceNumber();

    /**
     * Name: Current Version Start Date
     *
     * The date and time this version became current.
     */
    public Date getCurrentVersionStart();

    /**
     * Name: Current Version End Date
     *
     * The date and time when this version stopped being current.
     */
    public Date getCurrentVersionEnd();

    /**
     * Name: Version Comment
     *
     * Comments associated with the verison
     */
    public String getVersionComment();

    /**
     * Name: Versioned From Id
     *
     * Versioned From Id
     *
     * TODO: Understand and document what is really is
     */
    public String getVersionedFromId();
}

