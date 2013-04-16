/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may btain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

import java.util.Date;

/**
 * Create and last update info for the structure.  This is optional
 * and treated as read only since the data is set by the internals of
 * the service during maintenance operations.
 * 
 * @author nwright
 */

public interface Meta {

    /**
     * This is the field used for optimistic locking.
     *
     * An indicator of the version of the thing being described with this meta
     * information. This is set by the service implementation and will be used to
     * determine conflicts in updates.
     *
     * @name Version Indicator
     * @readOnly
     * @required on updates
     */
    public String getVersionInd();

    /**
     * The date and time the thing being described with this meta
     * information was last updated.
     *
     * @name Create Time
     * @readOnly
     * @required on updates
     */
    public Date getCreateTime();

    /**
     * The principal who created the thing being described with this
     * meta information.
     * 
     * @name Create Id
     * @readOnly
     * @required
     */
    public String getCreateId();

    /**
     * The date and time the thing being described with this meta
     * information was last updated.
     * 
     * Should be empty if it was never updated, just created.
     *
     * @name Update Time
     * @readOnly
     */
    public Date getUpdateTime();

    /**
     * The principal who last updated the thing being described with
     * this meta information.
     * 
     * Should be empty if it was never updated, just created.
     *
     * @name Update Id
     * @readOnly
     * @required
     */
    public String getUpdateId();
}

