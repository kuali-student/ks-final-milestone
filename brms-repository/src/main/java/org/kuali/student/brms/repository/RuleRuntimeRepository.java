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
package org.kuali.student.brms.repository;

import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

/**
 * This is the rule engine runtime repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public interface RuleRuntimeRepository {

    /**
     * <p>
     * Creates a new category in the repository.
     * </p>
     * Example: Create a new category for rules
     * 
     * <pre>
     * boolean b = rulesRepository.createCategory(&quot;/&quot;, &quot;EnrollmentRules&quot;, &quot;A test category 1.0 description&quot;);
     * b = rulesRepository.createCategory(&quot;/EnrollmentRules&quot;, &quot;Math&quot;, &quot;A Math category description&quot;);
     *         
     * List&lt;String&gt; category = rulesRepository.loadChildCategories(&quot;/&quot;);
     * ... 
     * category = rulesRepository.loadChildCategories(&quot;/EnrollmentRules&quot;);
     * ...
     * </pre>
     * 
     * @param path
     *            Category path
     * @param name
     *            Category name
     * @param description
     *            Category description
     * @return True if category successfully created, otherwise false
     * @throws RuleEngineRepositoryException
     */
    public Boolean createCategory(String path, String name, String description) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Updates and saves a rule to the repository.
     * </p>
     * Example: Update a rule with new source code content.
     * 
     * <pre>
     * // Load rule
     * RuleSet ruleSet = rulesRepository.loadRuleSet(ruleSetUUID);
     * Rule rule = ruleSet.getRules().get(0);
     * 
     * // Update Rule
     * String newContent = &quot;rule \&quot;new_rule\&quot; when then end&quot;;
     * rule.setContent(newContent);
     * rulesRepository.updateRule(rule);
     * ...
     * </pre>
     * 
     * @param rule
     *            A rule to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRule(Rule rule) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Checks in a rule by UUID into the repository.
     * </p>
     * Example: Create a rule set and check in a rule into the repository.
     * 
     * <pre>
     * // Create rule set
     * RuleSetImpl ruleSet = new RuleSetImpl(&quot;MyNewRuleSet&quot;);
     * ...
     * 
     * // Create rule
     * Rule rule = new RuleImpl(&quot;MyNewRule&quot;);
     * ...
     * ruleSet.addRule(rule);
     * 
     * // Create a category for the rules
     * rulesRepository.createCategory(&quot;/&quot;, &quot;MyCategory&quot;, &quot;My new rule category&quot;);
     * 
     * // Create and store the rule set in the repository
     * String ruleSetUUID = rulesRepository.createRuleSet(ruleSet);
     * // Load rule set to get rule UUID
     * RuleSet ruleSet2 = rulesRepository.loadRuleSet(ruleSetUUID);
     * Rule rule2 = ruleSet2.getRules().get(0);
     * 
     * rulesRepository.checkinRule(rule2.getUUID(), &quot;Checkin Rule Version 1&quot;);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRule(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Creates, compiles and checks in a rule set into the repository.
     * </p>
     * Example: Create a rule set and a rule and store it in the repository.
     * 
     * <pre>
     * // Create rule set
     * RuleSetImpl ruleSet = new RuleSetImpl(&quot;MyNewRuleSet&quot;);
     * ruleSet.setDescription(&quot;My new rule set&quot;);
     * 
     * // Create rule
     * Rule rule = new RuleImpl(&quot;MyNewRule&quot;);
     * rule.setDescription(&quot;My new rule&quot;);
     * rule.setCategory(null);
     * rule.setFormat(&quot;drl&quot;);
     * rule.setContent(&quot;rule \&quot;new_rule\&quot; when then end&quot;);
     * ruleSet.addRule(rule);
     * 
     * // Create a category for the rules
     * rulesRepository.createCategory(&quot;/&quot;, &quot;MyCategory&quot;, &quot;My new rule category&quot;);
     * 
     * // Create and store the rule set in the repository
     * String ruleSetUUID = rulesRepository.createRuleSet(ruleSet);
     * RuleSet ruleSet2 = rulesRepository.loadRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleEngineRepositoryException
     */
    public String createRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Updates and saves a rule to the repository.
     * </p>
     * Example: Load a rule set and update its header.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = rulesRepository.loadRuleSet(ruleSetUUID);
     * 
     * // Update rule set with new header
     * String header = &quot;import java.util.List;&quot;;
     * ruleSet.addHeader(header);
     * 
     * // Update rule set
     * rulesRepository.updateRuleSet(ruleSet);
     * ...
     * </pre>
     * 
     * @param ruleSet
     *            A rule set to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Checks in a rule set into the repository. Rule set version is incremented by 1
     * </p>
     * Example: Create and check in a rule set.
     * 
     * <pre>
     * // Create rule set
     * RuleSetImpl ruleSet = new RuleSetImpl(&quot;MyNewRuleSet&quot;);
     * ruleSet.setDescription(&quot;My new rule set&quot;);
     * 
     * // Rule Set Version 1
     * String ruleSetUUID = rulesRepository.createRuleSet(ruleSet);
     * 
     * // Check in rule set version 2
     * rulesRepository.checkinRuleSet(ruleSetUUID, &quot;Checkin Rule Set Version 2&quot;);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Loads a rule set (including all rules) by UUID from the repository.
     * </p>
     * Example: Load a rule set by UUID.
     * 
     * <pre>
     * // Create rule set
     * RuleSetImpl ruleSet = new RuleSetImpl(&quot;MyNewRuleSet&quot;);
     * ...
     * 
     * // Create rule
     * Rule rule = new RuleImpl(&quot;MyNewRule&quot;);
     * ...
     * 
     * // Create rule set
     * String ruleSetUUID = rulesRepository.createRuleSet(ruleSet);
     * 
     * // Load rule set
     * RuleSet ruleSet = rulesRepository.loadRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Loads a compiled rule set from the repository.
     * </p>
     * Example: Load a compiled rule set (e.g. Drools package).
     * 
     * <pre>
     * org.drools.rule.Package binPkg = (org.drools.rule.Package)
     *     rulesRepository.loadCompiledRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return A compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUUID) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * </p>
     * Example: Create and load a rule set snapshot.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = rulesRepository.loadRuleSet(ruleSetUUID);
     * 
     * rulesRepository.createRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;,
     *     false, &quot;Snapshot Version 1&quot;);
     * 
     * org.drools.rule.Package pkg = (org.drools.rule.Package)
     *     rulesRepository.loadCompiledRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;);
     * ...
     * </pre>
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param replaceExisting
     *            Replace existing snapshot
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment) throws RuleEngineRepositoryException;

    /**
     * <p>
     * Loads a compiled rule set snapshot from the repository.
     * </p>
     * Example: Load and execute a compiled rule set snapshot.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = rulesRepository.loadRuleSet(ruleSetUUID);
     * 
     * rulesRepository.createRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;,
     *     false, &quot;Snapshot Version 1&quot;);
     * 
     * org.drools.rule.Package pkg = (org.drools.rule.Package)
     *     rulesRepository.loadCompiledRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;);
     * 
     * RuleBase rb = RuleBaseFactory.newRuleBase();
     * rb.addPackage( pkg );
     * StatelessSession sess = rb.newStatelessSession();
     * sess.execute( ... );
     * ...
     * </pre>
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @return Compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) throws RuleEngineRepositoryException;
}
