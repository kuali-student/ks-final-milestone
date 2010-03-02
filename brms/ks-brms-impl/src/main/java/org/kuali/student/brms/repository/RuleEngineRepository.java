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

import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.util.List;

import org.drools.repository.RulesRepositoryException;
import org.kuali.student.brms.repository.exceptions.CategoryExistsException;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.exceptions.RuleExistsException;
import org.kuali.student.brms.repository.exceptions.RuleSetExistsException;
import org.kuali.student.brms.repository.rule.CompilerResultList;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

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
 * <b>Example 9: Create and load a rule set snapshot.</b>
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
 *     rulesRepository.loadRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;).getCompiledRuleSetObject();
 * ...
 * </pre>
 * 
 * <p>
 * <b>Example 10: Load and execute (using Drools rule engine) a compiled rule set snapshot.</b>
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
 *     rulesRepository.loadRuleSetSnapshot(&quot;MyRuleSet&quot;, &quot;MyRuleSetSnapshot1&quot;).getCompiledRuleSetObject();
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
     * @throws RuleEngineRepositoryException Thrown if loading child categories fails
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
     * @throws RuleEngineRepositoryException Thrown if creating category fails
     */
    public Boolean createCategory(String path, String name, String description) throws CategoryExistsException;

    /**
     * Checkin a rule set into the repository.
     * Checkin rule set will create a new version of the rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @return New version number
     * @throws RuleEngineRepositoryException Thrown if checkin rule set fails
     */
    public long checkinRuleSet(String uuid, String comment);

    /**
     * Updates a rule set in the repository and returns an updated rule set.
     *  
     * @param ruleSet A rule set to update
     * @return An updated rule set
     * @throws RuleEngineRepositoryException Thrown if updating rule set fails
     */
    public RuleSet updateRuleSet(RuleSet ruleSet);
    
    /**
     * Checkin a rule by uuid into the repository.
     * Checkin rule will create a new version of the rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @return New version number
     * @throws RuleEngineRepositoryException Thrown if checkin rule fails
     */
    public long checkinRule(String uuid, String comment);

    /**
     * Updates a rule in the repository and returns an updated rule.
     * 
     * @param rule A rule to update
     * @return An updated rule
     * @throws RuleEngineRepositoryException Thrown if updating rule fails
     */
    public Rule updateRule(Rule rule);
    
    /**
     * Deletes a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @throws RuleEngineRepositoryException Thrown if removing rule fails
     */
    public void removeRule(String uuid);

    /**
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws RuleEngineRepositoryException Thrown if removing rule set fails
     */
    public void removeRuleSet(String uuid);

    /**
     * Loads a rule's history.
     * 
     * @param uuid
     *            Rule uuid
     * @return List of history rules
     * @throws RuleEngineRepositoryException Thrown if loading rule history fails
     */
    public List<Rule> loadRuleHistory(String uuid);

    /**
     * Loads a rule set history
     * 
     * @param uuid Rule set uuid
     * @return A list of <code>RuleSet</code>s
     * @throws RuleEngineRepositoryException Thrown because method is not yet implemented due to a Drools bug
     */
    public List<RuleSet> loadRuleSetHistory( String uuid );

    /**
     * Loads all archived rule sets.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException Thrown because method is not yet implemented due to a Drools bug
     */
    public List<RuleSet> loadArchivedRuleSets();

    /**
     * Loads all archived rules.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException Thrown if loading archived rules fails
     */
    public List<Rule> loadArchivedRules();

    /**
     * Restores a rule or rule set to a specific version.
     * 
     * @param oldVersionUUID
     *            Old version UUID
     * @param newVersionUUID
     *            New version UUID
     * @param comment
     *            Comments of why a version was restored
     * @throws RuleEngineRepositoryException Thrown if restoring version fails
     */
    public void restoreVersion(String versionUUID, String assetUUID, String comment);

    /**
     * Exports the rules repository as a zip file container an XML repository file.
     * 
     * @param filename
     *            E.g. repository_export.xml.zip
     * @return A zip file
     * @throws RuleEngineRepositoryException Thrown if exporting rules repository fails
     */
    public ByteArrayOutputStream exportRulesRepositoryAsZip(String filename);

    /**
     * Exports the rules repository as XML.
     * 
     * @return XML content as a byte array
     * @throws RuleEngineRepositoryException Thrown if exporting rules repository fails
     */
    public byte[] exportRulesRepositoryAsXml();

    /**
     * Imports an XML rules repository file.
     * 
     * @param byteArray
     *            Byte array (XML file) - E.g. an repository_export.xml
     * @throws RuleEngineRepositoryException Thrown if importing rules repository fails
     */
    public void importRulesRepositoryAsXml(byte[] byteArray);

    /**
     * Creates and compiles a rule set.
     * 
     * @param ruleSet
     *            Rule set to create
     * @return A new rule set (containing a uuid)
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException Throws if compiling a rule set fails
     */
    public RuleSet createRuleSet(RuleSet ruleSet) throws RuleSetExistsException, RuleExistsException;

    /**
     * Loads a rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException Thrown if loading rule set fails
     */
    public RuleSet loadRuleSet(String uuid);

    /**
     * 
     * Loads a rule set (and rules) for a specific rule set name.
     * 
     * @param ruleSetName rule set name
     * @return A rule set
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    public RuleSet loadRuleSetByName(String ruleSetName);
    
    /**
     * Returns true if the repository contains the specified 
     * <code>ruleSetUUID</code> otherwise false.
     * 
     * @param ruleSetUUID Rule set UUID
     * @return True if contains <code>ruleSetUUID</code> otherwise false
     */
    public boolean containsRuleSet(final String ruleSetUUID);
    
    /**
     * Returns true if the repository contains the specified 
     * <code>ruleSetName</code> otherwise false.
     * 
     * @param ruleSetName Rule set name
     * @return True if contains <code>ruleSetName</code> otherwise false
     */
    public boolean containsRuleSetByName(final String ruleSetName);
    
    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    public Rule loadRule(String uuid);

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws RuleEngineRepositoryException Thrown if creating status fails
     */
    public String createStatus(String name);

    /**
     * Removes a status from the repository.
     * 
     * @param uuid Status name
     * @throws RuleEngineRepositoryException Thrown if removing status fails
     */
    public void removeStatus(final String name);
    
    /**
     * Loads all states.
     * 
     * @return List of all states
     * @throws RuleEngineRepositoryException Thrown if loading states fails
     */
    public List<String> loadStates();

    /**
     * Returns true if the repository contains the specified 
     * <code>status</code>; otherwise false.
     * 
     * @param status Status to check
     * @return True if repository contains the specified status; otherwise false
     */
    public boolean containsStatus(String status);

    /**
     * Changes rule status by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @param newState
     *            New rule status
     * @throws RuleEngineRepositoryException Thrown if changing rule status fails
     */
    public void changeRuleStatus(String uuid, String newState);

    /**
     * Changes a rule set status by uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @param newState
     *            New rule set status
     * @throws RuleEngineRepositoryException Thrown if changing rule set status fails
     */
    public void changeRuleSetStatus(String uuid, String newState);

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
     * @return New rule set
     * @throws RuleEngineRepositoryException 
     *            Thrown if rule set fails to compile or any other errors occur
     */
    public RuleSet createRuleSetSnapshot(String ruleSetName, String snapshotName, String comment);

    /**
     * Rebuilds (recompiles) an existing rule set snapshot. 
     * If the rule set fails to compile, an exception will be thrown and
     * a snapshot will not be created. 
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param comment
     *            Comments for creating the snapshot
     * @return New rule set
     * @throws RuleEngineRepositoryException 
     *            Thrown if rule set fails to compile or any other errors occur
     */
    public void rebuildRuleSetSnapshot(String ruleSetName, String snapshotName);

    /**
     * Rebuilds all snapshots in the repository.
     * 
     * @throws RuleEngineRepositoryException Thrown if rebuilding snapshots fails
     */
    public void rebuildAllSnapshots();

    /**
     * Replaces a rule set snapshot.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Snapshot comments
     * @return
     */
    public RuleSet replaceRuleSetSnapshot(String ruleSetName, String snapshotName, String comment);
    
    /**
     * 
     * Removes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws RuleEngineRepositoryException Thrown if snapshot fails to be removed or any other errors occur
     */
    public void removeRuleSetSnapshot(final String ruleSetName, final String snapshotName);

    /**
     * Removes a category.
     * 
     * @param categoryPath
     *            Category path
     * @throws RuleEngineRepositoryException Thrown if removing a category fails
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
     * @throws RuleEngineRepositoryException Thrown if renaming a rule fails
     */
    public String renameRule(String uuid, String newName);

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException Thrown if archiving a rule fails
     */
    public void archiveRule(String uuid, String comment);

    /**
     * Unarchives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException Thrown if unarchiving a rule fails
     */
    public void unArchiveRule(String uuid, String comment);

    /**
     * Archives a rule set.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException Thrown if archiving a rule set fails
     */
    public void archiveRuleSet(String uuid, String comment);

    /**
     * Unarchives a rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @throws RuleEngineRepositoryException Thrown if unarchiving a rule set fails
     */
    public void unArchiveRuleSet(String uuid, String comment);

    /**
     * Renames a rule set.
     * 
     * @param uuid
     * @param newName
     *            New rule set name
     * @return uuid of new rule set
     * @throws RuleEngineRepositoryException Thrown if renaming a rule set fails
     */
    public String renameRuleSet(String uuid, String newName);

    /**
     * Compiles a rule set.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Any rule set compilation errors otherwise null
     * @throws RuleEngineRepositoryException Thrown if compiling a rule set fails
     */
    public CompilerResultList compileRuleSet(String ruleSetUUID);

    /**
     * Compiles a rule set and returns the source code. For Drools, it returns the DRL.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Rule set source (Drools DRL)
     * @throws RuleEngineRepositoryException Thrown if compiling a rule set fails
     */
    public String compileRuleSetSource(String ruleSetUUID);

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Rule set's snapshot name
     * @return A rule set snapshot
     * @throws RuleEngineRepositoryException Thrown if loading a snapshot fails
     */
    public RuleSet loadRuleSetSnapshot(String ruleSetName, String snapshotName);

    /**
     * Compiles source code (e.g. A Drools DRL file) and returns a compiled rule engine specific object (e.g. a Drools
     * <code>org.drools.rule.Package</code>).
     * 
     * @param source
     *            DRL file to compile
     * @return A drools package (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     *             Thrown if compilation fails
     */
    public Object compileSource(Reader source);

    /**
     * <p>Loads all rules in a specific rule category.</p>
     * <p>This is a dynamic rule set which is not stored in the repository
     * and therefore has no UUID or version. The rule set's name is the same 
     * as the <code>category</name> name.</p> 
     * 
     * @param category Category rules belong to
     * @return A dynamic rule set
     */
    public RuleSet loadRuleSetByRuleCategory(String category);

    /**
     * Loads a list of rule sets by category name.
     * 
     * @param category Category name
     * @return A list of rule sets
     */
    public List<RuleSet> loadRuleSetsByCategory(String category) 
    	throws RulesRepositoryException;

    /**
     * Loads a list of rule set snapshots by category name.
     * 
     * @param category Category name
     * @return A list of rule sets
     */
    public List<RuleSet> loadRuleSetSnapshotsByCategory(String category)
    	throws RulesRepositoryException;
}