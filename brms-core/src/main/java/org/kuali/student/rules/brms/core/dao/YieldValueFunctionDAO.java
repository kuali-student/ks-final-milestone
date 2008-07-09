/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.dao;

import org.kuali.student.rules.internal.common.entity.YieldValueFunction;

/**
 * Defines DAO operations for YieldValueFunction
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public interface YieldValueFunctionDAO {

    /**
     * Persists YieldValueFunction in database.
     * 
     * @param YieldValueFunction
     *            A YieldValueFunction to persist in database.
     * @return persisted YieldValueFunction.
     */
    public YieldValueFunction createYieldValueFunction(YieldValueFunction yieldValueFunction);

    /**
     * Updates YieldValueFunction in database.
     * 
     * @param YieldValueFunction
     *            YieldValueFunction to update in database.
     * @return updated YieldValueFunction.
     */
    public YieldValueFunction updateYieldValueFunction(YieldValueFunction yieldValueFunction);

    /**
     * Finds YieldValueFunction in database.
     * 
     * @param id
     *            ID of YieldValueFunction to find in database.
     * @return Found YieldValueFunction or null if assistant not found.
     */
    public YieldValueFunction lookupYieldValueFunction(String id);

    /**
     * Deletes YieldValueFunction from database.
     * 
     * @param YieldValueFunction
     *            YieldValueFunction to delete from database.
     * @return true if operation completed successfully
     */
    public boolean deleteYieldValueFunction(YieldValueFunction yieldValueFunction);
}
