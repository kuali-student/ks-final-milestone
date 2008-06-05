/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.dao;

import org.kuali.student.rules.brms.core.entity.RuleProposition;

/**
 * Defines DAO operations for Rule Proposition
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public interface RulePropositionDAO {

    /**
     * Persists RuleProposition in database.
     * 
     * @param ruleProposition
     *            A rule proposition to persist in database.
     * @return persisted rule proposition.
     */
    public RuleProposition createRuleProposition(RuleProposition ruleProposition);

    /**
     * Updates RuleProposition in database.
     * 
     * @param ruleProposition
     *            Rule Proposition to update in database.
     * @return updated rule proposition.
     */
    public RuleProposition updateRuleProposition(RuleProposition ruleProposition);

    /**
     * Finds RuleProposition in database.
     * 
     * @param id
     *            ID of Rule Proposition to find in database.
     * @return Found rule proposition or null if proposition not found.
     */
    public RuleProposition lookupRuleProposition(String id);

    /**
     * Deletes RuleProposition from database.
     * 
     * @param ruleProposition
     *            Rule Proposition to delete from database.
     * @return true if operation completed successfully
     */
    public boolean deleteRuleProposition(RuleProposition ruleProposition);
}
