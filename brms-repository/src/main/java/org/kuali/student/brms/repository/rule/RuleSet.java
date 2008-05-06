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

import java.util.List;

/**
 * This is the <code>RuleSet</code> interface. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public interface RuleSet extends Item {

    /**
     * Adds a rule to this rule set.
     * Null and duplicate rules are not allowed.
     * 
     * @param rule A rule
     */
    public void addRule(Rule rule);

    /**
     * Determines whether a rule exists in the rule set.
     * 
     * @param ruleName Rule name
     * @return True if the rule exists in the rule set otherwise false
     */
    public boolean containsRule(String ruleName);
    
    /**
     * Removes a rule from this rule set.
     * 
     * @param rule A rule
     * @return The rule that was removed
     */
    public Rule removeRule(String name);

    /**
     * Removes all rules from this rule set.
     * 
     * @param rule A rule
     */
    public void clearRules();

    /**
     * Gets a list of <code>org.kuali.student.brms.repository.rule.Rule</code> from this rule set.
     * 
     * @return List of rules
     */
    public List<Rule> getRules();

    /**
     * Gets a compiled rule set byte array.
     * 
     * @return Compiled rule set byte array
     */
    public byte[] getCompiledRuleSet();

    /**
     * Gets a compiled rule set object. E.g. A Drools a <code>org.drools.rule.Package</code>.
     * 
     * @return A compiled rule set object
     */
    public Object getCompiledRuleSetObject();

    /**
     * Adds a header to this rule set. 
     * E.g. <code>ruleSet.addHeader("java.util.Calendar");</code>
     * Null and duplicate headers are not allowed.
     * 
     * @param header A rule set header
     */
    public void addHeader(String header);

    /**
     * Determines whether a header exists in the rule set.
     * 
     * @param ruleName Rule name
     * @return True if the header exists in the rule set otherwise false
     */
    public boolean containsHeader(String header);
    
    /**
     * Removes a header from this rule set. 
     * E.g. <code>ruleSet.removeHeader("java.util.Calendar");</code>
     * 
     * @param header A rule set header
     * @return True if header was removed otherwise false
     */
    public boolean removeHeader(String header);

    /**
     * Removes all headers from this rule set. 
     * E.g. <code>ruleSet.removeHeader("java.util.Calendar");</code>
     * 
     * @param header A rule set header
     */
    public void clearHeaders();

    /**
     * Gets this rule set header as a string. 
     * List of all header information.
     * 
     * @return Rule set header
     */
    public String getHeader();

    /**
     * Gets a list of this rule sets headers.
     * 
     * @return A list of header items
     */
    public List<String> getHeaderList();
    
    /**
     * Gets the rule sets source code, including rule set header and 
     * the source code of all rules.
     * 
     * @return Rule set source code
     */
    public String getContent();

    /**
     * Determines whether this rule set is a snapshot.
     * 
     * @return If true then this rule set is snapshot, otherwise false
     */
    public boolean isSnapshot();

    /**
     * Gets the snapshot name of this rule set. 
     * A rule set can have multiple snapshots.
     * 
     * @return Snapshot name
     */
    public String getSnapshotName();

    /**
     * <p>Sets the snapshot name of this rule set. 
     * A rule set can have multiple snapshots.</p>
     * <p>This method has no effect unless a rule set snapshot is created in 
     * the repository.</p>
     * 
     * @param snapshotName Snapshot name
     */
    public void setSnapshotName(String snapshotName);
}
