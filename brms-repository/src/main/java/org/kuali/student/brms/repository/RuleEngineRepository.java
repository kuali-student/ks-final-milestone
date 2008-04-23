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

import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.CompilerResultList;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;


/**
 * This is the interface to the rules engine repository which stores
 * rule sets, rules, categories, states, snapshots and compiled rule sets.
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
    public List<String> loadChildCategories(String categoryPath) throws RuleEngineRepositoryException;

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
     * @throws RuleEngineRepositoryException
     */
    public Boolean createCategory(String path, String name, String description) throws RuleEngineRepositoryException;

    /**
     * Checkin a rule set into the repository.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * Updates a rule and save it to the repository.
     *  
     * @param ruleSet A rule set to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException;
    
    /**
     * Checkin a rule into the repository.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRule(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * Updates a rule and saves it to the repository.
     * 
     * @param rule A rule to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRule(Rule rule) throws RuleEngineRepositoryException;
    
    /**
     * Deletes a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @throws RuleEngineRepositoryException
     */
    public void removeRule(String uuid) throws RuleEngineRepositoryException;

    /**
     * Loads a rule's history.
     * 
     * @param uuid
     *            Rule uuid
     * @return List of history rules
     * @throws RuleEngineRepositoryException
     */
    public List<Rule> loadRuleHistory(String uuid) throws RuleEngineRepositoryException;

    // public List<RuleSet> loadRuleSetHistory( String uuid )
    // throws RuleEngineRepositoryException;

    /**
     * Loads all archived rule sets.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException
     */
    public List<RuleSet> loadArchivedRuleSets() throws RuleEngineRepositoryException;

    /**
     * Loads all archived rules.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException
     */
    public List<Rule> loadArchivedRules() throws RuleEngineRepositoryException;

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
    public void restoreVersion(String versionUUID, String assetUUID, String comment) throws RuleEngineRepositoryException;

    /**
     * Exports the rules repository as a zip file container an XML repository file.
     * 
     * @param filename
     *            E.g. repository_export.xml.zip
     * @return A zip file
     * @throws RuleEngineRepositoryException
     */
    public ByteArrayOutputStream exportRulesRepositoryAsZip(String filename) throws RuleEngineRepositoryException;

    /**
     * Exports the rules repository as XML.
     * 
     * @return XML content as a byte array
     * @throws RuleEngineRepositoryException
     */
    public byte[] exportRulesRepositoryAsXml() throws RuleEngineRepositoryException;

    /**
     * Imports an XML rules repository file.
     * 
     * @param byteArray
     *            Byte array (XML file) - E.g. an repository_export.xml
     * @throws RuleEngineRepositoryException
     */
    public void importRulesRepository(byte byteArray[]) throws RuleEngineRepositoryException;

    /**
     * Creates a rule set and compiles all rules.
     * 
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleEngineRepositoryException
     */
    public String createRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException;

    /**
     * Adds a fact to a rule set. E.g. in Drools: import java.util.Calendar
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @param fact
     *            A java class
     * @throws RuleEngineRepositoryException
     */
    public void setFactsToRuleSet(String ruleSetUuid, String fact) throws RuleEngineRepositoryException;

    /**
     * Loads all rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) throws RuleEngineRepositoryException;

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException
     */
    public Rule loadRule(String uuid) throws RuleEngineRepositoryException;

    /**
     * Creates a new status.
     * 
     * @param name
     *            Status name
     * @return New status uuid
     * @throws RuleEngineRepositoryException
     */
    public String createStatus(String name) throws RuleEngineRepositoryException;

    /**
     * Loads all states (statuses).
     * 
     * @return Array of all states (statuses)
     * @throws RuleEngineRepositoryException
     */
    public String[] loadStates() throws RuleEngineRepositoryException;

    /**
     * Changes rule status by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @param newState
     *            New rule status
     * @throws RuleEngineRepositoryException
     */
    public void changeRuleStatus(String uuid, String newState) throws RuleEngineRepositoryException;

    /**
     * Changes rule set status by uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @param newState
     *            New rule set status
     * @throws RuleEngineRepositoryException
     */
    public void changeRuleSetStatus(String uuid, String newState) throws RuleEngineRepositoryException;

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
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment) throws RuleEngineRepositoryException;

    /**
     * Removes a category.
     * 
     * @param categoryPath
     *            Category path
     * @throws RuleEngineRepositoryException
     */
    public void removeCategory(String categoryPath) throws RuleEngineRepositoryException;

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
    public String renameRule(String uuid, String newName) throws RuleEngineRepositoryException;

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void archiveRule(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * Unarchives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void unArchiveRule(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * Archives an rule set.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void archiveRuleSet(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * Unarchives an rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @throws RuleEngineRepositoryException
     */
    public void unArchiveRuleSet(String uuid, String comment) throws RuleEngineRepositoryException;

    /**
     * Renames a rule set.
     * 
     * @param uuid
     * @param newName
     *            New rule set name
     * @return uuid of new rule set
     * @throws RuleEngineRepositoryException
     */
    public String renameRuleSet(String uuid, String newName) throws RuleEngineRepositoryException;

    /**
     * Compiles a rule set.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Any rule set building errors otherwise null
     * @throws RuleEngineRepositoryException
     */
    public CompilerResultList compileRuleSet(String ruleSetUUID) throws RuleEngineRepositoryException;

    /**
     * Compiles a rule set and returns the source code. For Drools, it returns the DRL.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Rule set source
     * @throws RuleEngineRepositoryException
     */
    public String compileRuleSetSource(String ruleSetUUID) throws RuleEngineRepositoryException;

    /**
     * Loads a compiled rule set.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @return A compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUuid) throws RuleEngineRepositoryException;

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
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) throws RuleEngineRepositoryException;

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
    public RuleSet loadRuleSetSnapshot(String ruleSetName, String snapshotName) throws RuleEngineRepositoryException;

    /**
     * Rebuilds all snapshots in the repository.
     * 
     * @throws RuleEngineRepositoryException
     */
    public void rebuildAllSnapshots() throws RuleEngineRepositoryException;

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
    public Object compileSource(Reader source) throws RuleEngineRepositoryException;
}