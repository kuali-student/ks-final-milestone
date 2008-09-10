/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dao;

import java.util.List;

import org.kuali.student.rules.rulemanagement.entity.BusinessRule;

/**
 * Defines DAO operations for Functional Business Rule
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
public interface RuleManagementDAO {

    /**
     * Creates BusinessRule in database.
     * 
     * @param rule
     *            A business rule to persist in database.
     * @return persisted rule.
     */
    public BusinessRule createBusinessRule(BusinessRule rule);

    /**
     * Updates BusinessRule in database.
     * 
     * @param rule
     *            A business rule to update in database.
     * @return rule used for update.
     */
    public BusinessRule updateBusinessRule(BusinessRule rule);

    /**
     * Delete BusinessRule from database.
     * 
     * @param rule
     *            A business rule to delete from database.
     * @return true on success
     */
    public boolean deleteBusinessRule(BusinessRule rule);

    /**
     * Delete BusinessRule from database.
     * 
     * @param id
     *            ID of a business rule to delete from database.
     * @return true on success
     */
    public boolean deleteBusinessRule(String id);

    /**
     * Finds BusinessRule in database.
     * 
     * @param id
     *            ID of a business rule to locate in database
     * @return found functional business rule or null if element not found.
     */
    public BusinessRule lookupBusinessRuleUsingId(String id);

    /**
     * Finds BusinessRule in database.
     * 
     * @param ruleIdentifier
     *            ID of a business rule to locate in database.
     * @return found functional business rule or null if element not found.
     */
    public BusinessRule lookupBusinessRuleUsingRuleId(String ruleIdentifier);

    /**
     * Finds one or more BusinessRule in database based on given parameters.
     * 
     * @param businessRuleTypeKey
     * @param anchor
     * @return found functional business rules or null if element not found.
     */
    public List<BusinessRule> lookupCompiledIDs(String businessRuleTypeKey, String anchor);
}
