/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.BRMSCore.dao;

import org.kuali.student.rules.BRMSCore.entity.RuleElement;

/**
 * Defines DAO operations for RuleRule Element
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public interface RuleElementDAO {

    /**
     * Persists RuleElement in database.
     * 
     * @param ruleElement
     *            A rule element to persist in database.
     * @return persisted rule element.
     */
    public RuleElement createRuleElement(RuleElement ruleElement);

    /**
     * Updates RuleElement in database.
     * 
     * @param ruleElement
     *            A rule element to update in database.
     * @return updated rule element.
     */
    public RuleElement updateRuleElement(RuleElement ruleElement);

    /**
     * Finds RuleElement in database.
     * 
     * @param id
     *            ID of Rule Element to find in database.
     * @return found rule element or null if element not found.
     */
    public RuleElement lookupRuleElement(String id);

    /**
     * Deletes RuleElement from database.
     * 
     * @param ruleElement
     *            A rule element to delete from database.
     * @return true if element deleted.
     */
    public boolean deleteRuleElement(RuleElement ruleElement);
}
