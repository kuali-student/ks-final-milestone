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
package org.kuali.student.rules.repository.drools.rule;

import org.kuali.student.rules.repository.rule.RuleSet;

/**
 * This factory class creates new instances of <code>RuleSet</code>s. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleSetFactory {

    /** Rule factory instance */
    private static RuleSetFactory factory;
    
    /**
     * Private constructor
     */
    private RuleSetFactory() { }

    /**
     * Gets an instance of this class.
     * 
     * @return A factory
     */
    public static RuleSetFactory getInstance() {
        if ( factory == null ) {
            factory = new RuleSetFactory();
        }
        return factory;
    }
    
    /**
     * Creates a new rule set.
     * 
     * @param name Rule set name
     * @param description Rule set description
     * @return A new rule set
     */
    public RuleSet createRuleSet( final String name, final String description ) {
        return new DroolsRuleSetImpl( name, description, DroolsConstants.FORMAT_DRL );
    }
    
    /**
     * Creates a new rule set.
     * 
     * @param name Rule set name
     * @param description Rule set description
     * @param format Rule set source code format
     * @return A new rule set
     */
    public RuleSet createRuleSet( final String name, final String description, final String format ) {
        return new DroolsRuleSetImpl( name, description, format );
    }
    
    /**
     * <p>For internal use only.</p>
     * 
     * <p>Creates a new rule set. 
     * <code>uuid</code> and <code>version</code> are set by the 
     * repository implementation.</p>
     * 
     * @param uuid Rule set UUID - Set by the repository implementation
     * @param name Rule set name
     * @param version Rule version number - Set by the repository implementation
     * @return A new rule set
     */
    public DroolsRuleSetImpl createRuleSet( final String uuid, final String name, final String description, final long version ) {
        return new DroolsRuleSetImpl( uuid, name, description, version );
    }
    
}
