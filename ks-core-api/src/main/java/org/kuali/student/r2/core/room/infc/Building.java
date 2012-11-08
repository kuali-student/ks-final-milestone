/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.room.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Building information
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface Building extends IdEntity {

    /**
     * Unique code for the building (for example, "FRESON HALL")
     *
     * Must be unique.
     *
     * (?) or should it be unique within the campus?
     *
     * @name Building Code
     * @required
     */
    public String getBuildingCode();

    /**
     * Unique key for the campus, the building is part of
     *
     * @name Campus Key
     * @required
     */
    public String getCampusKey();
}