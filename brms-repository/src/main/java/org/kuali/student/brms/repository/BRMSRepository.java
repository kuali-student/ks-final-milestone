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

/**
 * This is the interface to the rules engine repository which stores
 * rule sets, rules, categories, states, snapshots and compiled rule sets.
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 */
public interface BRMSRepository {
    /**
     * Loads child categories.
     * 
     * @param categoryPath
     *            Category path
     * @return List of child category names
     * @throws BRMSRepositoryException
     */
    public List<String> loadChildCategories(String categoryPath) throws BRMSRepositoryException;

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
     * @throws BRMSRepositoryException
     */
    public Boolean createCategory(String path, String name, String description) throws BRMSRepositoryException;

    /**
     * Creates a new rule n a rule set. It will be saved, but not checked in. The initial state will be set to draft state.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @param ruleName
     *            Rule name
     * @param description
     *            Rule description
     * @param ruleContent
     *            Rule code content
     * @param category
     *            Cateogry to add rule under
     * @return Rule set uuid
     * @throws BRMSRepositoryException
     */
    public String createRule(String ruleSetUuid, String ruleName, String description, String ruleContent, String category) throws BRMSRepositoryException;

    /**
     * Checkin a rule set into the repository.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws BRMSRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) throws BRMSRepositoryException;

    /**
     * Checkin a rule into the repository.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws BRMSRepositoryException
     */
    public void checkinRule(String uuid, String comment) throws BRMSRepositoryException;

    /**
     * Checkin a rule into the repository.
     * 
     * @param rule
     *            Rule
     * @param comment
     *            Checkin comments
     * @throws BRMSRepositoryException
     */
    public void checkinRule(Rule rule, String comment) throws BRMSRepositoryException;

    /**
     * Deletes a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @throws BRMSRepositoryException
     */
    public void removeRule(String uuid) throws BRMSRepositoryException;

    /**
     * Loads a rule's history.
     * 
     * @param uuid
     *            Rule uuid
     * @return List of history rules
     * @throws BRMSRepositoryException
     */
    public List<Rule> loadRuleHistory(String uuid) throws BRMSRepositoryException;

    // public List<RuleSet> loadRuleSetHistory( String uuid )
    // throws BRMSRepositoryException;

    /**
     * Loads all archived rule sets.
     * 
     * @return List of archived rules
     * @throws BRMSRepositoryException
     */
    public List<RuleSet> loadArchivedRuleSets() throws BRMSRepositoryException;

    /**
     * Loads all archived rules.
     * 
     * @return List of archived rules
     * @throws BRMSRepositoryException
     */
    public List<Rule> loadArchivedRules() throws BRMSRepositoryException;

    /**
     * Restores a rule or rule set to a specific version.
     * 
     * @param versionUUID
     *            Version uuid
     * @param assetUUID
     *            rule or rule set uuid
     * @param comment
     *            Comments
     * @throws BRMSRepositoryException
     */
    public void restoreVersion(String versionUUID, String assetUUID, String comment) throws BRMSRepositoryException;

    /**
     * Exports the rules repository as a zip file container an XML repository file.
     * 
     * @param filename
     *            E.g. repository_export.xml.zip
     * @return A zip file
     * @throws BRMSRepositoryException
     */
    public ByteArrayOutputStream exportRulesRepositoryAsZip(String filename) throws BRMSRepositoryException;

    /**
     * Exports the rules repository as XML.
     * 
     * @return XML content as a byte array
     * @throws BRMSRepositoryException
     */
    public byte[] exportRulesRepositoryAsXml() throws BRMSRepositoryException;

    /**
     * Imports an XML rules repository file.
     * 
     * @param byteArray
     *            Byte array (XML file) - E.g. an repository_export.xml
     * @throws BRMSRepositoryException
     */
    public void importRulesRepository(byte byteArray[]) throws BRMSRepositoryException;

    /**
     * Creates a new rule set.
     * 
     * @param name
     *            Rule set name
     * @param description
     *            Rule set description
     * @return Rule set uuid
     * @throws BRMSRepositoryException
     */
    public String createRuleSet(String name, String description) throws BRMSRepositoryException;

    /**
     * Adds a fact to a rule set. E.g. in Drools: import java.util.Calendar
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @param fact
     *            A java class
     * @throws BRMSRepositoryException
     */
    public void setFactsToRuleSet(String ruleSetUuid, String fact) throws BRMSRepositoryException;

    /**
     * Loads all rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws BRMSRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) throws BRMSRepositoryException;

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws BRMSRepositoryException
     */
    public Rule loadRule(String uuid) throws BRMSRepositoryException;

    /**
     * Creates a new status.
     * 
     * @param name
     *            Status name
     * @return New status uuid
     * @throws BRMSRepositoryException
     */
    public String createStatus(String name) throws BRMSRepositoryException;

    /**
     * Loads all states (statuses).
     * 
     * @return Array of all states (statuses)
     * @throws BRMSRepositoryException
     */
    public String[] loadStates() throws BRMSRepositoryException;

    /**
     * Changes rule status by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @param newState
     *            New rule status
     * @throws BRMSRepositoryException
     */
    public void changeRuleStatus(String uuid, String newState) throws BRMSRepositoryException;

    /**
     * Changes rule set status by uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @param newState
     *            New rule set status
     * @throws BRMSRepositoryException
     */
    public void changeRuleSetStatus(String uuid, String newState) throws BRMSRepositoryException;

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
     * @throws BRMSRepositoryException
     */
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment) throws BRMSRepositoryException;

    /**
     * Removes a category.
     * 
     * @param categoryPath
     *            Category path
     * @throws BRMSRepositoryException
     */
    public void removeCategory(String categoryPath) throws BRMSRepositoryException;

    /**
     * Renames an rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param newName
     *            New rule name
     * @return New uuid of the rule
     * @throws BRMSRepositoryException
     */
    public String renameRule(String uuid, String newName) throws BRMSRepositoryException;

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws BRMSRepositoryException
     */
    public void archiveRule(String uuid, String comment) throws BRMSRepositoryException;

    /**
     * Unarchives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws BRMSRepositoryException
     */
    public void unArchiveRule(String uuid, String comment) throws BRMSRepositoryException;

    /**
     * Archives an rule set.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws BRMSRepositoryException
     */
    public void archiveRuleSet(String uuid, String comment) throws BRMSRepositoryException;

    /**
     * Unarchives an rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @throws BRMSRepositoryException
     */
    public void unArchiveRuleSet(String uuid, String comment) throws BRMSRepositoryException;

    /**
     * Renames a rule set.
     * 
     * @param uuid
     * @param newName
     *            New rule set name
     * @return uuid of new rule set
     * @throws BRMSRepositoryException
     */
    public String renameRuleSet(String uuid, String newName) throws BRMSRepositoryException;

    /**
     * Compiles a rule set.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Any rule set building errors otherwise null
     * @throws BRMSRepositoryException
     */
    public BuilderResultList compileRuleSet(String ruleSetUUID) throws BRMSRepositoryException;

    /**
     * Compiles a rule set and returns the source code. For Drools, it returns the DRL.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Rule set source
     * @throws BRMSRepositoryException
     */
    public String compileRuleSetSource(String ruleSetUUID) throws BRMSRepositoryException;

    /**
     * Loads a compiled rule set.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @return A compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws BRMSRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUuid) throws BRMSRepositoryException;

    /**
     * Loads a compiled rule set snapshot.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @return Compiled rule set (e.g. <code>org.drools.rule.Package</code>)
     * @throws BRMSRepositoryException
     */
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) throws BRMSRepositoryException;

    /**
     * Loads a rule set snapshot.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Rule set's snapshot name
     * @return A rule set snapshot
     * @throws BRMSRepositoryException
     */
    public RuleSet loadRuleSetSnapshot(String ruleSetName, String snapshotName) throws BRMSRepositoryException;

    /**
     * Rebuilds all snapshots in the repository.
     * 
     * @throws BRMSRepositoryException
     */
    public void rebuildAllSnapshots() throws BRMSRepositoryException;

    /**
     * Compiles source code (e.g. A Drools DRL file) and returns a compiled rule engine specific object (e.g. a Drools
     * <code>org.drools.rule.Package</code>).
     * 
     * @param source
     *            A rule engine specific source code to compile
     * @return A drools package (<code>org.drools.rule.Package</code>)
     * @throws BRMSRepositoryException
     *             Thrown if compilation fails
     */
    public Object compileSource(Reader source) throws BRMSRepositoryException;
}