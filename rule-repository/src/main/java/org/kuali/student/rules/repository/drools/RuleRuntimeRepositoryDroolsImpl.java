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
package org.kuali.student.rules.repository.drools;

import org.drools.repository.RulesRepository;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.RuleRuntimeRepository;
import org.kuali.student.rules.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;

/**
 * This is a convenience interface for the rules repository interface.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 */
public class RuleRuntimeRepositoryDroolsImpl implements RuleRuntimeRepository {
    /** Drools rule repository */
    private RuleEngineRepository rulesRepository;
    
    /**
     * Constructor
     * 
     * @param repository Drools rules repository
     */
    public RuleRuntimeRepositoryDroolsImpl(RulesRepository repository) {
        this.rulesRepository = new RuleEngineRepositoryDroolsImpl(repository);
    }

    /**
     * <p>
     * Creates a new category in the repository.
     * </p>
     * Example: Create a new category for rules
     * 
     * <pre>
     * boolean b = repository.createCategory(&quot;/&quot;, &quot;EnrollmentRules&quot;, &quot;A test category 1.0 description&quot;);
     * b = repository.createCategory(&quot;/EnrollmentRules&quot;, &quot;Math&quot;, &quot;A Math category description&quot;);
     *         
     * List&lt;String&gt; category = repository.loadChildCategories(&quot;/&quot;);
     * ... 
     * category = repository.loadChildCategories(&quot;/EnrollmentRules&quot;);
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
     * @throws CategoryExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    public Boolean createCategory(String path, String name, String description) 
        throws CategoryExistsException {
        return this.rulesRepository.createCategory(path, name, description);
    }

    /**
     * <p>
     * Updates a rule in the repository and returns an updated rule.
     * </p>
     * Example: Update a rule with new source code content.
     * 
     * <pre>
     * // Load rule
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * Rule rule = ruleSet.getRules().get(0);
     * 
     * // Update Rule
     * String newContent = &quot;rule \&quot;new_rule\&quot; when then end&quot;;
     * rule.setContent(newContent);
     * repository.updateRule(rule);
     * ...
     * </pre>
     * 
     * @param rule
     *            A rule to update
     * @throws RuleEngineRepositoryException
     */
    public Rule updateRule(Rule rule) {
        return this.rulesRepository.updateRule(rule);
    }

    /**
     * <p>
     * Checks in a rule by UUID into the repository.
     * </p>
     * Example: Create a rule set and check in a rule into the repository.
     * 
     * <pre>
     * // Create rule set
     * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
     * ...
     * 
     * // Create rule
     * Rule rule = RuleFactory.getInstance().createRule(&quot;MyNewRule&quot;);
     * ...
     * ruleSet.addRule(rule);
     * 
     * // Create a category for the rules
     * repository.createCategory(&quot;/&quot;, &quot;MyCategory&quot;, &quot;My new rule category&quot;);
     * 
     * // Create and store the rule set in the repository
     * String ruleSetUUID = repository.createRuleSet(ruleSet);
     * // Load rule set to get rule UUID
     * RuleSet ruleSet2 = repository.loadRuleSet(ruleSetUUID);
     * Rule rule2 = ruleSet2.getRules().get(0);
     * 
     * repository.checkinRule(rule2.getUUID(), &quot;Checkin Rule Version 1&quot;);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRule(String uuid, String comment) {
        this.rulesRepository.checkinRule(uuid, comment);
    }

    /**
     * <p>
     * Creates, compiles and checks in a rule set into the repository.
     * </p>
     * Example: Create a rule set and a rule and store it in the repository.
     * 
     * <pre>
     * // Create rule set
     * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
     * ruleSet.setDescription(&quot;My new rule set&quot;);
     * 
     * // Create rule
     * Rule rule = RuleFactory.getInstance().createRule(&quot;MyNewRule&quot;);
     * rule.setDescription(&quot;My new rule&quot;);
     * rule.setCategory(null);
     * rule.setFormat(&quot;drl&quot;);
     * rule.setContent(&quot;rule \&quot;new_rule\&quot; when then end&quot;);
     * ruleSet.addRule(rule);
     * 
     * // Create a category for the rules
     * repository.createCategory(&quot;/&quot;, &quot;MyCategory&quot;, &quot;My new rule category&quot;);
     * 
     * // Create and store the rule set in the repository
     * String ruleSetUUID = repository.createRuleSet(ruleSet);
     * RuleSet ruleSet2 = repository.loadRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    public RuleSet createRuleSet(RuleSet ruleSet) 
        throws RuleSetExistsException, RuleExistsException {
        return this.rulesRepository.createRuleSet(ruleSet);
    }

    /**
     * <p>
     * Updates a rule set in the repository and returns a new rule set.
     * </p>
     * Example: Load a rule set and update its header.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * 
     * // Update rule set with new header
     * String header = &quot;import java.util.List;&quot;;
     * ruleSet.addHeader(header);
     * 
     * // Update rule set
     * repository.updateRuleSet(ruleSet);
     * ...
     * </pre>
     * 
     * @param ruleSet
     *            A rule set to update
     * @return An updated rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet updateRuleSet(RuleSet ruleSet) {
        return this.rulesRepository.updateRuleSet(ruleSet);
    }

    /**
     * <p>
     * Checks in a rule set into the repository. Rule set version is incremented by 1
     * </p>
     * Example: Create and check in a rule set.
     * 
     * <pre>
     * // Create rule set
     * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
     * ruleSet.setDescription(&quot;My new rule set&quot;);
     * 
     * // Rule Set Version 1
     * String ruleSetUUID = repository.createRuleSet(ruleSet);
     * 
     * // Check in rule set version 2
     * repository.checkinRuleSet(ruleSetUUID, &quot;Checkin Rule Set Version 2&quot;);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) {
        this.rulesRepository.checkinRuleSet(uuid, comment);
    }

    /**
     * <p>
     * Loads a rule set (including all rules) by UUID from the repository.
     * </p>
     * Example: Load a rule set by UUID.
     * 
     * <pre>
     * // Create rule set
     * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
     * ...
     * 
     * // Create rule
     * Rule rule = RuleFactory.getInstance().createRule(&quot;MyNewRule&quot;);
     * ...
     * 
     * // Create rule set
     * String ruleSetUUID = repository.createRuleSet(ruleSet);
     * 
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) {
        return this.rulesRepository.loadRuleSet(uuid);
    }

    /**
     * <p>
     * Loads a compiled rule set from the repository.
     * </p>
     * Example: Load a compiled rule set (e.g. Drools package).
     * 
     * <pre>
     * org.drools.rule.Package binPkg = (org.drools.rule.Package)
     *     repository.loadCompiledRuleSet(ruleSetUUID);
     * ...
     * </pre>
     * 
     * @param ruleSetUUID
     *            Rule set UUID
     * @return A compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUUID) {
        return this.rulesRepository.loadCompiledRuleSet(ruleSetUUID);
    }

    /**
     * <p>
     * Creates a new rule set snapshot for deployment and stores it in the repository.
     * </p>
     * Example: Create and load a rule set snapshot.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * 
     * repository.createRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;,
     *     &quot;Snapshot Version 1&quot;);
     * 
     * org.drools.rule.Package pkg = (org.drools.rule.Package)
     *     repository.loadCompiledRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;);
     * ...
     * </pre>
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, String comment) {
        this.rulesRepository.createRuleSetSnapshot(ruleSetName, snapshotName, comment);
    }

    /**
     * Replaces an existing rule set snapshot and stores it in the repository.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException
     */
    public void replaceRuleSetSnapshot(String ruleSetName, String snapshotName, String comment) {
        this.rulesRepository.replaceRuleSetSnapshot(ruleSetName, snapshotName, comment);
    }

    /**
     * <p>
     * Loads a compiled rule set snapshot from the repository.
     * </p>
     * Example: Load and execute a compiled rule set snapshot.
     * 
     * <pre>
     * // Load rule set
     * RuleSet ruleSet = repository.loadRuleSet(ruleSetUUID);
     * 
     * repository.createRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;,
     *     false, &quot;Snapshot Version 1&quot;);
     * 
     * org.drools.rule.Package pkg = (org.drools.rule.Package)
     *     repository.loadCompiledRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;);
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
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) {
        return this.rulesRepository.loadCompiledRuleSetSnapshot(ruleSetName, snapshotName);
    }
}
