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
package org.kuali.student.common.infc;

import java.util.Date;

/**
 * Create and last update info for the structure.
 * This is optional and treated as read only since the data is set by the
 * internals of the service during maintenance operations.
 * 
 * @author nwright
 */
public interface MetaInfc {

    /**
     * Name: Version Indicator
     *
     * This is the field used for optimistic locking.
     *
     * An indicator of the version of the thing being described with this meta
     * information. This is set by the service implementation and will be used to
     * determine conflicts in updates.
     *
     *
     */
    public String getVersionInd();

    /**
     * Name: Create Time
     *
     * The date and time the thing being described with this meta information was last
     * updated
     */
    public Date getCreateTime();

    /**
     * Name: Create Id
     *
     * The principal who created the thing being described with this meta information
     */
    public String getCreateId();

    /**
     * Name: Update Time
     *
     * The date and time the thing being described with this meta information was last
     * updated
     */
    public Date getUpdateTime();

    /**
     * Name: Update Id
     * 
     * The principal who last updated the thing being described with this meta
     * information
     */
    public String getUpdateId();
}

