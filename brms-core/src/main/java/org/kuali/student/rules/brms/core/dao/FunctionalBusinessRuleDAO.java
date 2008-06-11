/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.brms.core.dao;

import java.util.List;

import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;

/**
 * Defines DAO operations for Functional Business Rule
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public interface FunctionalBusinessRuleDAO {

    /**
     * Creates FunctionalBusinessRule in database.
     * 
     * @param rule
     *            A business rule to persist in database.
     * @return persisted rule.
     */
    public FunctionalBusinessRule createBusinessRule(FunctionalBusinessRule rule);

    /**
     * Updates FunctionalBusinessRule in database.
     * 
     * @param rule
     *            A business rule to update in database.
     * @return rule used for update.
     */
    public FunctionalBusinessRule updateBusinessRule(FunctionalBusinessRule rule);

    /**
     * Delete FunctionalBusinessRule from database.
     * 
     * @param rule
     *            A business rule to delete from database.
     * @return true on success
     */
    public boolean deleteBusinessRule(FunctionalBusinessRule rule);

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @param id
     *            ID of a business rule to locate in database
     * @return found functional business rule or null if element not found.
     */
    public FunctionalBusinessRule lookupBusinessRule(String id);

    /**
     * Finds FunctionalBusinessRule in database.
     * 
     * @param ruleIdentifier
     *            ID of a business rule to locate in database.
     * @return found functional business rule or null if element not found.
     */
    public FunctionalBusinessRule lookupBusinessRuleID(String ruleIdentifier);

    /**
     * Finds one or more FunctionalBusinessRule in database based on given parameters.
     * 
     * @param agendaType
     * @param businessRuleType
     * @param anchorType
     * @param anchor
     * @return found functional business rules or null if element not found.
     */
    public List<FunctionalBusinessRule> lookupCompiledRuleIDs(String agendaType, String businessRuleType,
            String anchorType, String anchor);
}
