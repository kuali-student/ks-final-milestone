/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.repository.rule;

import org.kuali.student.brms.repository.drools.rule.RuleFactory;
import org.kuali.student.brms.repository.drools.rule.RuleSetFactory;

/**
 * this is a rule utility class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleUtil {

    /**
     * Creates a new rule.
     * 
     * @param name Rule name
     * @return A new rule
     */
    public static Rule createRule( String name ) {
        Rule rule = RuleFactory.getInstance().createDroolsRule( name );
        rule.setContent( "rule \"" + name + "\" when then end" );
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
    public static Rule createRule( String uuid, String name, long version ) {
        Rule rule = RuleFactory.getInstance().createDroolsRule( uuid, name, version );
        rule.setContent( "rule \"" + name + "\" when then end" );
        return rule;
    }

    /**
     * Creates a new rule set.
     * 
     * @param name Rule set name
     * @return A new rule set
     */
    public static RuleSet createRuleSet( String name ) {
        return RuleSetFactory.getInstance().createRuleSet( name );
    }    

    /**
     * Creates a new rule set.
     * 
     * @param uuid Rule set UUID
     * @param name Rule set name
     * @param version Rule set version
     * @return A new rule set
     */
    public static RuleSet createRuleSet( String uuid, String name, long version ) {
        return RuleSetFactory.getInstance().createRuleSet( uuid, name, version );
    }    

}
