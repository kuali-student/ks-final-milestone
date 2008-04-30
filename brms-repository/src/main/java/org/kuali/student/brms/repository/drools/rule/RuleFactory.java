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
    private static RuleFactory factory;

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
        if ( factory == null ) {
            factory = new RuleFactory();
        }
        return factory;
    }

    /**
     * Creates a rule.
     * 
     * @param name Rule name
     * @return A new rule
     */
    public Rule createDroolsRule( final String name ) {
        return new DroolsRuleImpl( name );
    }

    /**
     * <p>For internal use only.</p>
     * 
     * <p>Creates a rule.
     * <code>uuid</code> and <code>version</code> are set by the 
     * repository implementation.</p>
     * 
     * @param uuid Rule UUID - Set by the repository implementation
     * @param name Rule name
     * @param version Rule version number - Set by the repository implementation
     * @return A new rule
     */
    public DroolsRuleImpl createDroolsRule( final String uuid, final String name, long version ) {
        return new DroolsRuleImpl( uuid, name, version );
    }
}
