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
package org.kuali.student.rules.brms.repository;

import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.util.List;

import org.kuali.student.rules.brms.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.brms.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.brms.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.brms.repository.rule.CompilerResultList;
import org.kuali.student.rules.brms.repository.rule.Rule;
import org.kuali.student.rules.brms.repository.rule.RuleSet;

/**
 * <p>
 * This is the rules engine repository interface which stores rule sets, 
 * rules, categories, states, snapshots and compiled rule sets.
 * </p>
 * <p>
 * Below are some examples of how to use this repository interface.
 * </p>
 * <p>
 * <b>Example 1: Setup a Drools repository.</b>
 * </p>
 * <pre>
 * // Location of the repository.xml file
 * URL url = RuleEngineRepository.class.getResource("/drools-repository");
 * 
 * // Setup repository
 * jackrabbitRepo = new DroolsJackrabbitRepository(url);
 * jackrabbitRepo.startupRepository();
 *
 * // Login to the Jackrabbit repository
 * String id = "superuser";
 * char[] password = "superuser".toCharArray();
 * Credentials credentials = new SimpleCredentials(id, password);
 * jackrabbitRepo.login(jackrabbitRepository.getCredentials());
 * 
 * // Delete all data in the repository
 * jackrabbitRepo.clearData();
 * rulesRepository = new RuleEngineRepositoryDroolsImpl(jackrabbitRepo.getRepository());
 * 
 * // Do rules stuff here ...
 * RuleSet ruleSet = rulesRepository.loadRuleSet(ruleSetUUID);
 * ...
 *  
 * // Logout from the Jackrabbit repository
 * jackrabbitRepo.logout();
 * // Shutdown the Jackrabbit repository
 * jackrabbitRepo.shutdownRepository();
 * </pre>
 * <p>
 * <b>Example 2: Create a new category for rules.</b>
 * </p>
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
 * <p>
 * <b>Example 3: Create a rule set and a rule and store it in the repository.</b>
 * </p>
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
 * rulesRepository.createCategory(&quot;/&quot;, &quot;MyCategory&quot;, &quot;My new rule category&quot;);
 * 
 * // Create and store the rule set in the repository
 * String ruleSetUUID = rulesRepository.createRuleSet(ruleSet);
 * ...
 * </pre>
 * 
 * <p>
 * <b>Example 4: Load a rule set by UUID.</b>
 * </p>
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
 * String ruleSetUUID = rulesRepository.createRuleSet(ruleSet);
 * 
 * // Load rule set
 * RuleSet ruleSet = rulesRepository.loadRuleSet(ruleSetUUID);
 * ...
 * </pre>
 * 
 * <p>
 * <b>Example 5: Create and check in a rule set.</b>
 * </p>
 * 
 * <pre>
 * // Create rule set
 * RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(&quot;MyNewRuleSet&quot;);
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
 * <p>
 * <b>Example 6: Update a rule with new source code content.</b>
 * </p>
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
 * <p>
 * <b>Example 7: Create a rule set and check in a rule into the repository.</b>
 * </p>
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
 * <p>
 * <b>Example 8: Create a rule set and a rule and store it in the repository.</b>
 * </p>
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
 * rulesRepository.createCategory(&quot;/&quot;, &quot;MyCategory&quot;, &quot;My new rule category&quot;);
 * 
 * // Create and store the rule set in the repository
 * String ruleSetUUID = rulesRepository.createRuleSet(ruleSet);
 * RuleSet ruleSet2 = rulesRepository.loadRuleSet(ruleSetUUID);
 * ...
 * </pre>
 * 
 * <p>
 * <b>Example 9: Load a compiled rule set (e.g. Drools package).</b>
 * </p>
 * 
 * <pre>
 * org.drools.rule.Package binPkg = (org.drools.rule.Package)
 *     rulesRepository.loadCompiledRuleSet(ruleSetUUID);
 * ...
 * </pre>
 * 
 * <p>
 * <b>Example 10: Create and load a rule set snapshot.</b>
 * </p>
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
 * <p>
 * <b>Example 11: Load and execute a compiled rule set snapshot.</b>
 * </p>
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
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 */
public interface RuleEngineRepository {
    /**
     * Loads child categories.
     * 
     * @param categoryPath
     *            Category path
     * @return List of child category names
     * @throws RuleEngineRepositoryException
     */
    public List<String> loadChildCategories(String categoryPath);

    /**
     * Creates a new category.
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
    public Boolean createCategory(String path, String name, String description) throws CategoryExistsException;

    /**
     * Checkin a rule set into the repository.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment);

    /**
     * Updates a rule and save it to the repository.
     *  
     * @param ruleSet A rule set to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRuleSet(RuleSet ruleSet);
    
    /**
     * Checkin a rule into the repository.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRule(String uuid, String comment);

    /**
     * Updates a rule and saves it to the repository.
     * 
     * @param rule A rule to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRule(Rule rule);
    
    /**
     * Deletes a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @throws RuleEngineRepositoryException
     */
    public void removeRule(String uuid);

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws RuleEngineRepositoryException
     */
    public void removeRuleSet(String uuid);

    /**
     * Loads a rule's history.
     * 
     * @param uuid
     *            Rule uuid
     * @return List of history rules
     * @throws RuleEngineRepositoryException
     */
    public List<Rule> loadRuleHistory(String uuid);

    // public List<RuleSet> loadRuleSetHistory( String uuid );

    /**
     * Loads all archived rule sets.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException
     */
    public List<RuleSet> loadArchivedRuleSets();

    /**
     * Loads all archived rules.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException
     */
    public List<Rule> loadArchivedRules();

    /**
     * Restores a rule or rule set to a specific version.
     * 
     * @param versionUUID
     *            Version uuid
     * @param assetUUID
     *            rule or rule set uuid
     * @param comment
     *            Comments
     * @throws RuleEngineRepositoryException
     */
    public void restoreVersion(String versionUUID, String assetUUID, String comment);

    /**
     * Exports the rules repository as a zip file container an XML repository file.
     * 
     * @param filename
     *            E.g. repository_export.xml.zip
     * @return A zip file
     * @throws RuleEngineRepositoryException
     */
    public ByteArrayOutputStream exportRulesRepositoryAsZip(String filename);

    /**
     * Exports the rules repository as XML.
     * 
     * @return XML content as a byte array
     * @throws RuleEngineRepositoryException
     */
    public byte[] exportRulesRepositoryAsXml();

    /**
     * Imports an XML rules repository file.
     * 
     * @param byteArray
     *            Byte array (XML file) - E.g. an repository_export.xml
     * @throws RuleEngineRepositoryException
     */
    public void importRulesRepository(byte[] byteArray);

    /**
     * Creates and compiles a rule set.
     * 
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException
     */
    public String createRuleSet(RuleSet ruleSet) throws RuleSetExistsException, RuleExistsException;

    /**
     * Loads all rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSet(String uuid);

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException
     */
    public Rule loadRule(String uuid);

    /**
     * Creates a new status.
     * 
     * @param name
     *            Status name
     * @return New status uuid
     * @throws RuleEngineRepositoryException
     */
    public String createStatus(String name);

    /**
     * Loads all states (statuses).
     * 
     * @return Array of all states (statuses)
     * @throws RuleEngineRepositoryException
     */
    public String[] loadStates();

    /**
     * Changes rule status by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @param newState
     *            New rule status
     * @throws RuleEngineRepositoryException
     */
    public void changeRuleStatus(String uuid, String newState);

    /**
     * Changes rule set status by uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @param newState
     *            New rule set status
     * @throws RuleEngineRepositoryException
     */
    public void changeRuleSetStatus(String uuid, String newState);

    /**
     * Creates a new rule set snapshot for deployment. Creates a copy of the rule set for deployment.
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
    //public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment);
    /**
     * Creates a new rule set snapshot for deployment. 
     * Creates a copy of the rule set for deployment.
     * If the rule set fails to compile, an exception will be thrown and
     * a snapshot will not be created. 
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException 
     *            If rule set fails to compile or any other errors occur
     */
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, String comment);

    /**
     * Replaces an existing rule set snapshot. 
     * If the rule set fails to compile, an exception will be thrown and
     * a snapshot will not be created. 
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param comment
     *            Comments for creating the snapshot
     * @throws RuleEngineRepositoryException 
     *            If rule set fails to compile or any other errors occur
     */
    public void replaceRuleSetSnapshot(String ruleSetName, String snapshotName, String comment);

    /**
     * Removes a category.
     * 
     * @param categoryPath
     *            Category path
     * @throws RuleEngineRepositoryException
     */
    public void removeCategory(String categoryPath);

    /**
     * Renames an rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param newName
     *            New rule name
     * @return New uuid of the rule
     * @throws RuleEngineRepositoryException
     */
    public String renameRule(String uuid, String newName);

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void archiveRule(String uuid, String comment);

    /**
     * Unarchives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void unArchiveRule(String uuid, String comment);

    /**
     * Archives an rule set.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void archiveRuleSet(String uuid, String comment);

    /**
     * Unarchives an rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @throws RuleEngineRepositoryException
     */
    public void unArchiveRuleSet(String uuid, String comment);

    /**
     * Renames a rule set.
     * 
     * @param uuid
     * @param newName
     *            New rule set name
     * @return uuid of new rule set
     * @throws RuleEngineRepositoryException
     */
    public String renameRuleSet(String uuid, String newName);

    /**
     * Compiles a rule set.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Any rule set building errors otherwise null
     * @throws RuleEngineRepositoryException
     */
    public CompilerResultList compileRuleSet(String ruleSetUUID);

    /**
     * Compiles a rule set and returns the source code. For Drools, it returns the DRL.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Rule set source
     * @throws RuleEngineRepositoryException
     */
    public String compileRuleSetSource(String ruleSetUUID);

    /**
     * Loads a compiled rule set.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @return A compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUuid);

    /**
     * Loads a compiled rule set as an array of bytes.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return A compiled rule set (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public byte[] loadCompiledRuleSetAsBytes(String ruleSetUUID);
    
    /**
     * Loads a compiled rule set snapshot.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @return Compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName);

    /**
     * Loads a compiled rule set snapshot as an array of bytes.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @return Compiled rule set (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public byte[] loadCompiledRuleSetSnapshotAsBytes(String ruleSetName, String snapshotName);
    
    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Rule set's snapshot name
     * @return A rule set snapshot
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSetSnapshot(String ruleSetName, String snapshotName);

    /**
     * Rebuilds all snapshots in the repository.
     * 
     * @throws RuleEngineRepositoryException
     */
    public void rebuildAllSnapshots();

    /**
     * Compiles source code (e.g. A Drools DRL file) and returns a compiled rule engine specific object (e.g. a Drools
     * <code>org.drools.rule.Package</code>).
     * 
     * @param source
     *            A rule engine specific source code to compile
     * @return A drools package (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     *             Thrown if compilation fails
     */
    public Object compileSource(Reader source);
}