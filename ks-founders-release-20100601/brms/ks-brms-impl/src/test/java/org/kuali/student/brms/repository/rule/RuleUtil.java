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

package org.kuali.student.brms.repository.rule;

import org.kuali.student.brms.repository.drools.rule.DroolsConstants;
import org.kuali.student.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.brms.repository.drools.rule.RuleSetFactory;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

/**
 * this is a rule utility class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleUtil {

    public static String getSimpleRule( final String name ) {
        return "rule \"" + name + "\" when then end";
    }

    /**
     * Creates a new rule.
     * 
     * @param name Rule name
     * @return A new rule
     */
    public static Rule createRule( final String name ) {
        Rule rule = RuleFactory.getInstance().createDroolsRule( 
                name, "A new rule", "rule \"" + name + "\" when then end" );
        rule.setContent(getSimpleRule(name));
        return rule;
    }

    /**
     * Creates a new rule.
     * 
     * @param uuid Rule UUID
     * @param name Rule name
     * @param version Rule version
     * @return A new rule
     */
    public static Rule createRule( final String uuid, final String name, 
            final long version, final String ruleSetUUID, final String ruleSetName) {
        Rule rule = RuleFactory.getInstance().createDroolsRule(uuid, name, version, ruleSetUUID, ruleSetName);
        rule.setContent(getSimpleRule(name));
        return rule;
    }

    /**
     * Creates a new rule set.
     * 
     * @param name Rule set name
     * @return A new rule set
     */
    public static RuleSet createRuleSet(final String name) {
        return RuleSetFactory.getInstance().createRuleSet(name, "A new rule set", DroolsConstants.FORMAT_DRL);
    }   

    /**
     * Creates a new rule set.
     * 
     * @param uuid Rule set UUID
     * @param name Rule set name
     * @param version Rule set version
     * @return A new rule set
     */
    public static RuleSet createRuleSet(final String uuid, final String name, String description, final long version) {
        return RuleSetFactory.getInstance().createRuleSet( uuid, name, description, version );
    }    

}
