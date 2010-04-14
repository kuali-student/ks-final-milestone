/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.repository.drools.rule;

import org.kuali.student.brms.repository.rule.Rule;

/**
 * This factory class creates new instances of <code>Rule</code>s. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleFactory {

    /** Rule factory instance */
    private final static RuleFactory factory = new RuleFactory();

    /**
     * Private constructor
     */
    private RuleFactory() { }

    /**
     * Gets an instance of this class.
     * 
     * @return A factory
     */
    public static RuleFactory getInstance() {
        return factory;
    }

    /**
     * Creates a new rule.
     * 
     * @param name Rule name
     * @param description Rule description
     * @param content Rule source code content
     * @return A new rule
     */
    public Rule createDroolsRule(final String name, final String description, final String content) {
        return new DroolsRuleImpl(name, description, content);
    }

    /**
     * Creates a new rule.
     * 
     * @param name Rule name
     * @param description Rule description
     * @param category Rule category
     * @param content Rule source code content
     * @param format Rule source code format
     * @return A new rule
     */
    public Rule createDroolsRule(final String name, final String description, final String category, final String content, final String format) {
        return new DroolsRuleImpl(name, description, category, content, format);
    }

    /**
     * <p>For internal use only.</p>
     * 
     * <p>Creates a new rule.
     * <code>uuid</code> and <code>version</code> are set by the 
     * repository implementation.</p>
     * 
     * @param uuid Rule UUID - Set by the repository implementation
     * @param name Rule name
     * @param version Rule version number - Set by the repository implementation
     * @return A new rule
     */
    public DroolsRuleImpl createDroolsRule(final String uuid, final String name, 
            final long version, final String ruleSetUUID, final String ruleSetName) {
        return new DroolsRuleImpl(uuid, name, version, ruleSetUUID, ruleSetName );
    }
}
