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
package org.kuali.student.brms.repository.drools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;

import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.repository.AssetHistoryIterator;
import org.drools.repository.AssetItem;
import org.drools.repository.AssetItemIterator;
import org.drools.repository.CategoryItem;
import org.drools.repository.PackageItem;
import org.drools.repository.PackageIterator;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryException;
import org.drools.repository.StateItem;
import org.kuali.student.brms.repository.RuleEngineRepository;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.CompilerResultList;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

public class RuleEngineRepositoryDroolsImpl implements RuleEngineRepository {
    RulesRepository repository;

    public RuleEngineRepositoryDroolsImpl(RulesRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads child categories.
     * 
     * @param categoryPath
     *            Category path
     * @return List of child category names
     * @throws RuleEngineRepositoryException
     */
    public List<String> loadChildCategories(String categoryPath) throws RuleEngineRepositoryException {
        if (categoryPath == null || categoryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("categoryPath cannot be null or empty");
        }

        try {
            List<String> list = new ArrayList<String>();
            CategoryItem item = this.repository.loadCategory(categoryPath);
            for (int i = 0; i < item.getChildTags().size(); i++) {
                CategoryItem item2 = (CategoryItem) item.getChildTags().get(i);
                list.add(item2.getName());
            }
            return list;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading child categories failed: " + "categoryPath=" + categoryPath, e);
        }
    }

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
    public Boolean createCategory(String path, String name, String description) throws RuleEngineRepositoryException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        else if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Category description cannot be null or empty");
        }

        if (path == null || "".equals(path)) {
            path = "/";
        }

        try {
            CategoryItem item = this.repository.loadCategory(path);
            item.addCategory(name, description);
            this.repository.save();
            return Boolean.TRUE;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Creating category failed: " + "path=" + path, e);
        }
    }

    /**
     * Checkin a rule set into the repository.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }
        else if (comment == null || comment.isEmpty()) {
            throw new IllegalArgumentException("Rule set checkin comment cannot be null or empty");
        }

        try {
            PackageItem item = this.repository.loadPackageByUUID(uuid);
            item.updateDescription(item.getDescription());
            item.checkin(comment);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Checkin rule set failed: uuid=" + uuid, e);
        }
    }

    /**
     * Updates a rule and save it to the repository.
     *  
     * @param ruleSet A rule set to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException {
        if (ruleSet == null) {
            throw new IllegalArgumentException("ruleSet cannot be null or empty");
        }
        else if (ruleSet.getDescription() == null || ruleSet.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Rule set description cannot be null or empty");
        }
        else if (ruleSet.getFormat() == null || ruleSet.getFormat().isEmpty()) {
            throw new IllegalArgumentException("Rule set format cannot be null or empty");
        }
        
        try {
            PackageItem item = this.repository.loadPackageByUUID(ruleSet.getUUID());
            item.updateDescription(ruleSet.getDescription());
            item.updateFormat(ruleSet.getFormat());
            item.updateHeader(ruleSet.getHeader());
            item.updateState(ruleSet.getStatus());
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Checkin rule set failed: uuid=" + ruleSet.getUUID(), e);
        }
    }

    /**
     * Checkin a rule by uuid into the repository.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws RuleEngineRepositoryException
     */
    public void checkinRule(String uuid, String comment) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem item = this.repository.loadAssetByUUID(uuid);
            item.updateDateEffective(item.getDateEffective());
            item.updateDateExpired(item.getDateExpired());
            item.checkin(comment);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Checkin rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Updates a rule and saves it to the repository.
     * 
     * @param rule A rule to update
     * @throws RuleEngineRepositoryException
     */
    public void updateRule(Rule rule) throws RuleEngineRepositoryException {
        if (rule.getUUID() == null || rule.getUUID().trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }
        else if (rule.getDescription() == null || rule.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Rule description cannot be null or empty");
        }
        else if (rule.getFormat() == null || rule.getFormat().isEmpty()) {
            throw new IllegalArgumentException("Rule format cannot be null or empty");
        }

        try {
            AssetItem item = this.repository.loadAssetByUUID(rule.getUUID());
            item.updateContent(rule.getContent());
            item.updateDescription(rule.getDescription());
            item.updateFormat(rule.getFormat());
            item.updateState(rule.getStatus());
            item.archiveItem(rule.isArchived());
            item.updateDateEffective(rule.getEffectiveDate());
            item.updateDateExpired(rule.getExpiryDate());
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Checkin rule failed: uuid=" + rule.getUUID(), e);
        }
    }

    /**
     * Deletes a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @throws RuleEngineRepositoryException
     */
    public void removeRule(String uuid) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem asset = this.repository.loadAssetByUUID(uuid);
            asset.remove();
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Removing rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Loads a rule's history.
     * 
     * @param uuid
     *            Rule uuid
     * @return List of history rules
     * @throws RuleEngineRepositoryException
     */
    public List<Rule> loadRuleHistory(String uuid) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem item = this.repository.loadAssetByUUID(uuid);
            AssetHistoryIterator it = item.getHistory();
            List<Rule> list = new ArrayList<Rule>();
            while (it != null && it.hasNext()) {
                AssetItem historyItem = (AssetItem) it.next();
                long versionNumber = historyItem.getVersionNumber();
                if (versionNumber != 0 && versionNumber != item.getVersionNumber()) {
                    Rule rule = DroolsUtil.buildHistoricalRule(historyItem);
                    list.add(rule);
                }
            }
            return list;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule history failed: uuid=" + uuid, e);
        }
    }

    public List<RuleSet> loadRuleSetHistory(String uuid) throws RuleEngineRepositoryException {
        throw new RuleEngineRepositoryException("Not implemented - Drools repository bug? - Cannot load rule sets history");

        // XXX This doesn't work properly - Drools bug???
        /*if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }
        try {
            PackageItem item = this.repository.loadPackageByUUID(uuid);
            Node node = item.getNode();
            AssetHistoryIterator it = new AssetHistoryIterator(this.repository, node);
            // AssetHistoryIterator it = item.getHistory();
            List<RuleSet> list = new ArrayList<RuleSet>();
            while (it != null && it.hasNext()) {
                AssetItem historyItem = (AssetItem) it.next();
                long versionNumber = historyItem.getVersionNumber();
                System.out.println("***** uuid=" + historyItem.getUUID() + ", version=" + versionNumber);
                System.out.println("***** getCheckinComment=" + historyItem.getCheckinComment());
                System.out.println("***** getContent=" + historyItem.getContent());
                if (versionNumber != 0 && versionNumber != item.getVersionNumber()) {
                    RuleSet ruleset = DroolsUtil.buildHistoricalRuleSet(historyItem);
                    list.add(ruleset);
                }
            }
            return list;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading ruleset history failed: uuid=" + uuid, e);
        }*/
    }

    /**
     * Loads all archived rule sets.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException
     */
    public List<RuleSet> loadArchivedRuleSets() throws RuleEngineRepositoryException {
        throw new RuleEngineRepositoryException("Not implemented - Drools repository bug - Cannot load archived rule sets");

        // XXX This doesn't work properly - Drools bug???
        /*try { 
            System.out.println( "\n\n****************** loadArchivedRuleSets *****************\n\n" ); 
            Iterator<PackageItem> it = findArchivedPackages(); 
            List<RuleSet> list = new ArrayList<RuleSet>(); 
            while( it.hasNext() ) { 
                PackageItem item = it.next(); 
                System.out.println( "***** item=" +item.getUUID() + ", name=" +item.getName() + ", isArchived=" + item.isArchived() ); 
                RuleSet ruleSet = DroolsUtil.buildRuleSet( item ); 
                list.add( ruleSet ); 
            } 
            System.out.println( "\n\n***********************************************************\n\n" ); 
            return list; 
        } catch( RulesRepositoryException e ) { 
            throw new RuleEngineRepositoryException( "Loading archieved rule sets failed", e ); 
        }*/
    }

    /**
     * Finds archived packages. XXX This method doesn't find archived packages for some reason.
     * 
     * @return An iterator of PackageItem
     */
    private Iterator<PackageItem> findArchivedPackages() {
        try {
            String sql = "SELECT " + PackageItem.TITLE_PROPERTY_NAME + ", " + PackageItem.DESCRIPTION_PROPERTY_NAME + ", " + PackageItem.CONTENT_PROPERTY_ARCHIVE_FLAG;
            sql += " FROM " + PackageItem.RULE_PACKAGE_TYPE_NAME;
            sql += " WHERE ";
            sql += " jcr:path LIKE '/" + RulesRepository.RULES_REPOSITORY_NAME + "/" + RulesRepository.RULE_PACKAGE_AREA + "/%'";
            sql += " AND " + PackageItem.CONTENT_PROPERTY_ARCHIVE_FLAG + " = 'true'";

            Query q = this.repository.getSession().getWorkspace().getQueryManager().createQuery(sql, Query.SQL);

            QueryResult res = q.execute();

            return new PackageIterator(this.repository, res.getNodes());
        } catch (RepositoryException e) {
            throw new RulesRepositoryException("Finding archived rule sets failed", e);
        }
    }

    /**
     * Loads all archived rules.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException
     */
    public List<Rule> loadArchivedRules() throws RuleEngineRepositoryException {
        try {
            AssetItemIterator it = this.repository.findArchivedAssets();
            // return it;
            List<Rule> list = new ArrayList<Rule>();
            while (it.hasNext()) {
                AssetItem item = (AssetItem) it.next();
                Rule rule = DroolsUtil.buildRule(item);
                list.add(rule);
            }
            return list;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading archieved rules failed", e);
        }
    }

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
    public void restoreVersion(String oldVersionUUID, String newVersionUUID, String comment) throws RuleEngineRepositoryException {
        if (oldVersionUUID == null || oldVersionUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("oldVersionUUID cannot be null or empty");
        } else if (newVersionUUID == null || newVersionUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("newVersionUUID cannot be null or empty");
        } else if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        AssetItem old = this.repository.loadAssetByUUID(oldVersionUUID);
        AssetItem head = this.repository.loadAssetByUUID(newVersionUUID);

        try {
            this.repository.restoreHistoricalAsset(old, head, comment);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Restoring version failed: " + "oldVersionUUID=" + oldVersionUUID + ", newVersionUUID=" + newVersionUUID, e);
        }

    }

    /**
     * Exports the rules repository as a zip file container an XML repository file.
     * 
     * @param filename
     *            E.g. repository_export.xml.zip
     * @return A zip file
     * @throws RuleEngineRepositoryException
     */
    public ByteArrayOutputStream exportRulesRepositoryAsZip(String filename) throws RuleEngineRepositoryException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("filename cannot be null or empty");
        }

        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ZipOutputStream zout = new ZipOutputStream(bout);
            zout.putNextEntry(new ZipEntry(filename));
            zout.write(exportRulesRepositoryAsXml());
            zout.closeEntry();
            zout.finish();
            return bout;
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        }
    }

    /**
     * Exports the rules repository as XML.
     * 
     * @param filename
     *            E.g. repository_export.xml
     * @return XML content as a byte array
     * @throws RuleEngineRepositoryException
     */
    public byte[] exportRulesRepositoryAsXml() throws RuleEngineRepositoryException {
        try {
            return this.repository.dumpRepositoryXml();
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        } catch (PathNotFoundException e) {
            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        }
    }

    /**
     * Imports an XML rules repository file.
     * 
     * @param byteArray
     *            Byte array (XML file) - E.g. an repository_export.xml
     * @throws RuleEngineRepositoryException
     */
    public void importRulesRepository(byte byteArray[]) throws RuleEngineRepositoryException {
        if (byteArray == null || byteArray.length == 0) {
            throw new IllegalArgumentException("byteArray cannot be null or empty");
        }

        try {
            this.repository.importRulesRepository(byteArray);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Importing rules repository failed", e);
        }
    }

    /**
     * Adds a fact to a rule set. E.g. in Drools: import java.util.Calendar
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @param fact
     *            A java class
     * @throws RuleEngineRepositoryException
     */
    public void setFactsToRuleSet(String ruleSetUuid, String fact) throws RuleEngineRepositoryException {
        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUuid);
            pkg.updateHeader(fact);
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Adding fact to rule set failed: ruleSetUuid=" + ruleSetUuid, e);
        }
    }

    /**
     * Loads all rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(uuid);
            RuleSet ruleSet = DroolsUtil.buildRuleSet(pkg);
            return ruleSet;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set failed: uuid=" + uuid, e);
        }
    }

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException
     */
    public Rule loadRule(String uuid) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem item = repository.loadAssetByUUID(uuid);
            Rule rule = DroolsUtil.buildRule(item);

            return rule;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Creates a new status.
     * 
     * @param name
     *            Status name
     * @return New status uuid
     * @throws RuleEngineRepositoryException
     */
    public String createStatus(String name) throws RuleEngineRepositoryException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }

        try {
            String uuid = this.repository.createState(name).getNode().getUUID();
            this.repository.save();
            return uuid;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Creating status failed: name=" + name, e);
        } catch (UnsupportedRepositoryOperationException e) {
            throw new RuleEngineRepositoryException("Creating status failed: name=" + name, e);
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Creating status failed: name=" + name, e);
        }
    }

    /**
     * Loads all states (statuses).
     * 
     * @return Array of all states (statuses)
     * @throws RuleEngineRepositoryException
     */
    public String[] loadStates() throws RuleEngineRepositoryException {
        try {
            StateItem[] states = this.repository.listStates();
            String[] result = new String[states.length];
            for (int i = 0; i < states.length; i++) {
                result[i] = states[i].getName();
            }
            return result;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Listing states failed", e);
        }
    }

    /**
     * Changes rule status by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @param newState
     *            New rule status
     * @throws RuleEngineRepositoryException
     */
    public void changeRuleStatus(String uuid, String newState) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newState == null || newState.trim().isEmpty()) {
            throw new IllegalArgumentException("newState cannot be null or empty");
        }

        try {
            AssetItem asset = this.repository.loadAssetByUUID(uuid);
            asset.updateState(newState);
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Changing rule status failed: uuid=" + uuid, e);
        }
    }

    /**
     * Changes rule set status by uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @param newState
     *            New rule set status
     * @throws RuleEngineRepositoryException
     */
    public void changeRuleSetStatus(String uuid, String newState) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newState == null || newState.trim().isEmpty()) {
            throw new IllegalArgumentException("newState cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(uuid);
            pkg.changeStatus(newState);
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Changing rule set status failed: uuid=" + uuid, e);
        }
    }

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
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment) throws RuleEngineRepositoryException {
        if (ruleSetName == null || ruleSetName.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        } else if (snapshotName == null || snapshotName.trim().isEmpty()) {
            throw new IllegalArgumentException("snapshotName cannot be null or empty");
        }

        try {
            if (replaceExisting) {
                this.repository.removePackageSnapshot(ruleSetName, snapshotName);
            }

            this.repository.createPackageSnapshot(ruleSetName, snapshotName);
            PackageItem item = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);
            item.updateCheckinComment(comment);
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Creating rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
    }

    /**
     * Removes a category.
     * 
     * @param categoryPath
     *            Category path
     * @throws RuleEngineRepositoryException
     */
    public void removeCategory(String categoryPath) throws RuleEngineRepositoryException {
        if (categoryPath == null || categoryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("categoryPath cannot be null or empty");
        }

        try {
            this.repository.loadCategory(categoryPath).remove();
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Removing category failed: " + "categoryPath" + categoryPath, e);
        }
    }

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
    public String renameRule(String uuid, String newName) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("newName cannot be null or empty");
        }

        try {
            return this.repository.renameAsset(uuid, newName);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Renaming rule failed: " + "uuid" + uuid, e);
        }
    }

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void archiveRule(String uuid, String comment) throws RuleEngineRepositoryException {
        archiveRule(uuid, comment, true);
    }

    /**
     * Unarchives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void unArchiveRule(String uuid, String comment) throws RuleEngineRepositoryException {
        archiveRule(uuid, comment, false);
    }

    /**
     * Archives an rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @param archive
     *            If true archive rule, otherwise unarchive rule
     * @throws RuleEngineRepositoryException
     */
    private void archiveRule(String uuid, String comment, boolean archive) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem item = this.repository.loadAssetByUUID(uuid);
            item.archiveItem(archive);
            if (comment == null) {
                if (archive) {
                    comment = "Archived";
                } else {
                    comment = "Unarchived";
                }
            }
            item.checkin(comment);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Archiving rule failed: " + "uuid=" + uuid, e);
        }
    }

    /**
     * Archives an rule set.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException
     */
    public void archiveRuleSet(String uuid, String comment) throws RuleEngineRepositoryException {
        archiveRuleSet(uuid, comment, true);
    }

    /**
     * Unarchives an rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @throws RuleEngineRepositoryException
     */
    public void unArchiveRuleSet(String uuid, String comment) throws RuleEngineRepositoryException {
        archiveRuleSet(uuid, comment, false);
    }

    /**
     * Archives an rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @param archive
     *            If true archive rule set, otherwise unarchive rule set
     * @throws RuleEngineRepositoryException
     */
    private void archiveRuleSet(String uuid, String comment, boolean archive) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            PackageItem item = this.repository.loadPackageByUUID(uuid);
            item.archiveItem(archive);
            if (comment == null) {
                if (archive) {
                    comment = "Archived";
                } else {
                    comment = "Unarchived";
                }
            }
            item.checkin(comment);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Archiving rule failed: " + "uuid=" + uuid, e);
        }
    }

    /**
     * Renames a rule set.
     * 
     * @param uuid
     * @param newName
     *            New rule set name
     * @return uuid of new rule set
     * @throws RuleEngineRepositoryException
     */
    public String renameRuleSet(String uuid, String newName) throws RuleEngineRepositoryException {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("newName cannot be null or empty");
        }

        try {
            return this.repository.renamePackage(uuid, newName);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Renaming rule set failed: " + "uuid=" + uuid, e);
        }
    }

    /**
     * Compiles a rule set.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Any rule set building errors otherwise null
     * @throws RuleEngineRepositoryException
     */
    public CompilerResultList compileRuleSet(String ruleSetUUID) throws RuleEngineRepositoryException {
        if (ruleSetUUID == null || ruleSetUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetUUID cannot be null or empty");
        }

        PackageItem item = null;

        try {
            item = this.repository.loadPackageByUUID(ruleSetUUID);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed: " + "ruleSetUUID=" + ruleSetUUID, e);
        }

        try {
            CompilerResultList result = DroolsUtil.compile(item);

            if (result != null && result.size() > 0) {
                return result;
            } else {
                item.updateCompiledPackage(DroolsUtil.getBinaryPackage(result.getPackage()));
                this.repository.save();
            }
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed", e);
        } catch (DroolsParserException e) {
            throw new RuleEngineRepositoryException("Compiling and parsing rule set failed", e);
        }
        return null;
    }

    /**
     * Compiles a rule set and returns the source code. For Drools, it returns the DRL.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Rule set source (Drools DRL)
     * @throws RuleEngineRepositoryException
     */
    public String compileRuleSetSource(String ruleSetUUID) throws RuleEngineRepositoryException {
        if (ruleSetUUID == null || ruleSetUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetUUID cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUUID);

            CompilerResultList result = DroolsUtil.compile(pkg);

            if (result != null && result.size() > 0) {
                throw new RuleEngineRepositoryException("Compiling rule set failed: " + result);
            }
            return DroolsUtil.getDRL(pkg);
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed: ", e);
        } catch (DroolsParserException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed", e);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed: " + "ruleSetUUID=" + ruleSetUUID, e);
        }
    }

    /**
     * Loads a compiled rule set.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @return A compiled rule set (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUUID) throws RuleEngineRepositoryException {
        if (ruleSetUUID == null || ruleSetUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetUUID cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUUID);
            return DroolsUtil.getPackage(pkg.getCompiledPackageBytes());
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set failed: " + "ruleSetUuid=" + ruleSetUUID, e);
        } catch (Exception e) {
            throw new RuleEngineRepositoryException("Loading rule set failed: " + "ruleSetUuid=" + ruleSetUUID, e);
        }
    }

    /**
     * Loads a compiled rule set snapshot.
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @return Compiled rule set (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     */
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) throws RuleEngineRepositoryException {
        if (ruleSetName == null || ruleSetName.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        }
        else if (snapshotName == null || snapshotName.trim().isEmpty()) {
            throw new IllegalArgumentException("snapshotName cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);
            return DroolsUtil.getPackage(pkg.getCompiledPackageBytes());
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading compiled rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        } catch (Exception e) {
            throw new RuleEngineRepositoryException("Loading compiled rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
    }

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
    public RuleSet loadRuleSetSnapshot(String ruleSetName, String snapshotName) throws RuleEngineRepositoryException {
        if (ruleSetName == null || ruleSetName.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        }
        else if (snapshotName == null || snapshotName.trim().isEmpty()) {
            throw new IllegalArgumentException("snapshotName cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);
            RuleSet ruleSet = DroolsUtil.buildRuleSet(pkg);
            return ruleSet;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
    }

    /**
     * Rebuilds all snapshots in the repository.
     * 
     * @throws RuleEngineRepositoryException
     */
    public void rebuildAllSnapshots() throws RuleEngineRepositoryException {
        try {
            Iterator<PackageItem> it = repository.listPackages();
            while (it.hasNext()) {
                PackageItem pkg = it.next();
                String[] snapshots = this.repository.listPackageSnapshots(pkg.getName());
                for (String snapshotName : snapshots) {
                    PackageItem snapshotItem = this.repository.loadPackageSnapshot(pkg.getName(), snapshotName);
                    CompilerResultList result = this.compileRuleSet(snapshotItem.getUUID());
                    if (result != null) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < result.size(); i++) {
                            sb.append('\n');
                            sb.append(result.get(i).toString());
                        }
                        sb.append('\n');
                        throw new RuleEngineRepositoryException("Rebuilding snapshot failed: snapshotName=" + snapshotName + sb.toString());
                    }
                }
            }
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Rebuilding all snapshots failed", e);
        }
    }

    /**
     * Compiles source code (e.g. A Drools DRL file) and returns a compiled rule engine specific object (e.g. a Drools
     * <code>org.drools.rule.Package</code>).
     * 
     * @param sourceDrl
     *            DRL file to compile
     * @return A drools package (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     *             Thrown if compilation fails
     */
    public Object compileSource(Reader source) throws RuleEngineRepositoryException {
        try {
            PackageBuilder builder = new PackageBuilder();
            builder.addPackageFromDrl(source);
            org.drools.rule.Package pkg = builder.getPackage();
            pkg.checkValidity();
            return pkg;
        } catch (RuntimeException e) {
            throw new RuleEngineRepositoryException("Compiling DRL failed: " + e.getMessage(), e);
        } catch (DroolsParserException e) {
            throw new RuleEngineRepositoryException("Compiling and parsing DRL failed: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuleEngineRepositoryException(e);
        }
    }

    /**
     * Creates a rule set and compiles all rules.
     * 
     * @param ruleSet
     *            Rule set to create
     * @return Rule set uuid
     * @throws RuleEngineRepositoryException
     */
    public String createRuleSet(RuleSet ruleSet) throws RuleEngineRepositoryException
    {
        if (ruleSet.getName() == null || ruleSet.getName().isEmpty()) {
            throw new IllegalArgumentException("Rule set name cannot be null or empty");
        }

        List<Rule> rules = ruleSet.getRules();
        PackageItem pkg = createpackage(ruleSet);

        for(int i=0; i<rules.size(); i++) {
            createRule( pkg, rules.get(i) );
        }

        try {
            CompilerResultList result = DroolsUtil.compile(pkg);

            if (result != null && !result.isEmpty()) {
                throw new RuleEngineRepositoryException("Compiling rule set failed: " + result);
            }
            pkg.updateCompiledPackage(DroolsUtil.getBinaryPackage(result.getPackage()));
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed", e);
        } catch (DroolsParserException e) {
            throw new RuleEngineRepositoryException("Compiling and parsing rule set failed", e);
        }

        this.repository.save();
        return pkg.getUUID();
    }
    
    private PackageItem createpackage(RuleSet ruleSet) throws RuleEngineRepositoryException
    {
        try {
            PackageItem pkg = this.repository.createPackage(ruleSet.getName(), ruleSet.getDescription());
            pkg.updateHeader(ruleSet.getHeader());
            this.repository.save();
            return pkg;
        } catch (RulesRepositoryException e) {
            if (e.getCause() instanceof ItemExistsException) {
                throw new RuleEngineRepositoryException("Creating new rule set failed - " + 
                        "Duplicate rule set: rule name=" + ruleSet.getName(), e);
            } else {
                throw new RuleEngineRepositoryException("Creating new rule set failed: " + 
                        "rule set name=" + ruleSet.getName(), e);
            }
        }
    }
    
    private void createRule(PackageItem pkg, Rule rule) throws RuleEngineRepositoryException
    {
        if (rule.getName() == null || rule.getName().isEmpty()) {
            throw new IllegalArgumentException("Rule name cannot be null or empty");
        }
        else if (rule.getDescription() == null || rule.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Rule description cannot be null or empty");
        }
        //else if (rule.getCategory() == null || rule.getCategory().isEmpty()) {
        //    throw new IllegalArgumentException("Rule category cannot be null or empty");
        //}
        else if (rule.getFormat() == null || rule.getFormat().isEmpty()) {
            throw new IllegalArgumentException("Rule format cannot be null or empty");
        }

        try {
            AssetItem asset = pkg.addAsset(rule.getName(), 
                    rule.getDescription(), rule.getCategory(), rule.getFormat());
            asset.updateContent(rule.getContent());
            asset.updateDescription(rule.getDescription());
        } catch (RulesRepositoryException e) {
            if (e.getCause() instanceof ItemExistsException) {
                throw new RuleEngineRepositoryException("Creating new rule failed - " + 
                        "Duplicate rule: ruleName=" + rule.getName(), e);
            } else {
                throw new RuleEngineRepositoryException("Creating new rule failed: " + 
                        "ruleName=" + rule.getName(), e);
            }
        }
    }
    
}
