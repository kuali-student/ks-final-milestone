/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.dao;

import org.kuali.student.rules.common.entity.ComputationAssistant;

/**
 * Defines DAO operations for Computation Assistant
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public interface ComputationAssistantDAO {

    /**
     * Persists ComputationAssistant in database.
     * 
     * @param computationAssistant
     *            A computation assistant to persist in database.
     * @return persisted computation assistant.
     */
    public ComputationAssistant createComputationAssistant(ComputationAssistant computationAssistant);

    /**
     * Updates ComputationAssistant in database.
     * 
     * @param computationAssistant
     *            Computation assistant to update in database.
     * @return updated computation assistant.
     */
    public ComputationAssistant updateComputationAssistant(ComputationAssistant computationAssistant);

    /**
     * Finds ComputationAssistant in database.
     * 
     * @param id
     *            ID of Computation Assistant to find in database.
     * @return Found computation assistant or null if assistant not found.
     */
    public ComputationAssistant lookupComputationAssistant(String id);

    /**
     * Deletes ComputationAssistant from database.
     * 
     * @param computationAssistant
     *            Computation assistant to delete from database.
     * @return true if operation completed successfully
     */
    public boolean deleteComputationAssistant(ComputationAssistant computationAssistant);
}
