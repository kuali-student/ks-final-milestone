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
import org.kuali.student.brms.repository.BRMSRepository;
import org.kuali.student.brms.repository.BRMSRepositoryException;
import org.kuali.student.brms.repository.BuilderResultList;
import org.kuali.student.brms.repository.Rule;
import org.kuali.student.brms.repository.RuleSet;

public class BRMSRepositoryDroolsImpl implements BRMSRepository {
    private RulesRepository repository;

    private BRMSRepository brms;

    public BRMSRepositoryDroolsImpl(RulesRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads child categories.
     * 
     * @param categoryPath
     *            Category path
     * @return List of child category names
     * @throws BRMSRepositoryException
     */
    public List<String> loadChildCategories(String categoryPath) throws BRMSRepositoryException {
        if (categoryPath == null || categoryPath.trim().length() == 0) {
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
            throw new BRMSRepositoryException("Loading child categories failed: " + "categoryPath=" + categoryPath, e);
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
     * @throws BRMSRepositoryException
     */
    public Boolean createCategory(String path, String name, String description) throws BRMSRepositoryException {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("name cannot be null or empty");
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
            throw new BRMSRepositoryException("Creating category failed: " + "path=" + path, e);
        }
    }

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
    public String createRule(String ruleSetUuid, String ruleName, String description, String ruleContent, String category) throws BRMSRepositoryException {
        if (ruleName == null || ruleName.trim().length() == 0) {
            throw new IllegalArgumentException("ruleName cannot be null or empty");
        } else if (ruleContent == null || ruleContent.trim().length() == 0) {
            throw new IllegalArgumentException("ruleContent cannot be null or empty");
        }
        /*
         * else if ( category == null || category.trim().length() == 0 ) { throw new IllegalArgumentException( "category
         * cannot be null or empty" ); }
         */
        else if (ruleSetUuid == null || ruleSetUuid.trim().length() == 0) {
            throw new IllegalArgumentException("ruleSetUuid cannot be null or empty");
        }

        try {
            // String format = AssetFormats.DRL;
            String format = "drl";
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUuid);
            AssetItem asset = null;
            asset = pkg.addAsset(ruleName, description, category, format);
            asset.updateContent(ruleContent);
            asset.updateDescription(description);
            this.repository.save();
            return asset.getUUID();
        } catch (RulesRepositoryException e) {
            if (e.getCause() instanceof ItemExistsException) {
                throw new BRMSRepositoryException("Creating new rule failed - " + "Duplicate rule: ruleName=" + ruleName, e);
            } else {
                throw new BRMSRepositoryException("Creating new rule failed: " + "ruleName=" + ruleName, e);
            }
        }
    }

    /**
     * Checkin a rule set into the repository.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Checkin comments
     * @throws BRMSRepositoryException
     */
    public void checkinRuleSet(String uuid, String comment) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            PackageItem item = this.repository.loadPackageByUUID(uuid);
            item.updateDescription(item.getDescription());
            item.checkin(comment);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Checkin rule set failed: uuid=" + uuid, e);
        }
    }

    /**
     * Checkin a rule by uuid into the repository.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Checkin comments
     * @throws BRMSRepositoryException
     */
    public void checkinRule(String uuid, String comment) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem item = this.repository.loadAssetByUUID(uuid);
            item.updateDateEffective(item.getDateEffective());
            item.updateDateExpired(item.getDateExpired());
            item.checkin(comment);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Checkin rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Checkin a rule into the repository.
     * 
     * @param rule
     *            Rule
     * @param comment
     *            Checkin comments
     * @throws BRMSRepositoryException
     */
    public void checkinRule(Rule rule, String comment) throws BRMSRepositoryException {
        if (rule.getUUID() == null || rule.getUUID().trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
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
            item.checkin(comment);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Checkin rule failed: uuid=" + rule.getUUID(), e);
        }
    }

    /**
     * Deletes a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @throws BRMSRepositoryException
     */
    public void removeRule(String uuid) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem asset = this.repository.loadAssetByUUID(uuid);
            asset.remove();
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Removing rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Loads a rule's history.
     * 
     * @param uuid
     *            Rule uuid
     * @return List of history rules
     * @throws BRMSRepositoryException
     */
    public List<Rule> loadRuleHistory(String uuid) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
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
            throw new BRMSRepositoryException("Loading rule history failed: uuid=" + uuid, e);
        }
    }

    /*
     * public List<RuleSet> loadRuleSetHistory( String uuid ) throws BRMSRepositoryException { if ( uuid == null ||
     * uuid.trim().length() == 0 ) { throw new IllegalArgumentException( "UUID cannot be null or empty" ); } try {
     * PackageItem item = this.repository.loadPackageByUUID( uuid ); Node node = item.getNode(); AssetHistoryIterator it =
     * new AssetHistoryIterator( this.repository, node ); //AssetHistoryIterator it = item.getHistory(); List<RuleSet> list =
     * new ArrayList<RuleSet>(); while( it != null && it.hasNext() ) { AssetItem historyItem = (AssetItem) it.next(); long
     * versionNumber = historyItem.getVersionNumber(); System.out.println( "***** uuid=" +historyItem.getUUID() + ",
     * version="+versionNumber ); System.out.println( "***** getCheckinComment=" +historyItem.getCheckinComment() );
     * System.out.println( "***** getContent=" +historyItem.getContent() ); if ( versionNumber != 0 && versionNumber !=
     * item.getVersionNumber() ) { RuleSet ruleset = DroolsUtil.buildHistoricalRuleSet( historyItem ); list.add( ruleset ); } }
     * return list; } catch( RulesRepositoryException e ) { throw new BRMSRepositoryException( "Loading ruleset history
     * failed: uuid=" + uuid, e ); } }
     */

    /**
     * Loads all archived rule sets.
     * 
     * @return List of archived rules
     * @throws BRMSRepositoryException
     */
    public List<RuleSet> loadArchivedRuleSets() throws BRMSRepositoryException {
        throw new BRMSRepositoryException("Not implemented - Drools repository bug - Cannot load archived rule sets");

        // XXX This doesn't work properly
        /*
         * try { System.out.println( "\n\n****************** loadArchivedRuleSets *****************\n\n" ); Iterator<PackageItem>
         * it = findArchivedPackages(); List<RuleSet> list = new ArrayList<RuleSet>(); while( it.hasNext() ) { PackageItem
         * item = it.next(); System.out.println( "***** item=" +item.getUUID() + ", name=" +item.getName() + ", isArchived=" +
         * item.isArchived() ); RuleSet ruleSet = DroolsUtil.buildRuleSet( item ); list.add( ruleSet ); } System.out.println(
         * "\n\n***********************************************************\n\n" ); return list; } catch(
         * RulesRepositoryException e ) { throw new BRMSRepositoryException( "Loading archieved rule sets failed", e ); }
         */
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
     * @throws BRMSRepositoryException
     */
    public List<Rule> loadArchivedRules() throws BRMSRepositoryException {
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
            throw new BRMSRepositoryException("Loading archieved rules failed", e);
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
     * @throws BRMSRepositoryException
     */
    public void restoreVersion(String oldVersionUUID, String newVersionUUID, String comment) throws BRMSRepositoryException {
        if (oldVersionUUID == null || oldVersionUUID.trim().length() == 0) {
            throw new IllegalArgumentException("oldVersionUUID cannot be null or empty");
        } else if (newVersionUUID == null || newVersionUUID.trim().length() == 0) {
            throw new IllegalArgumentException("newVersionUUID cannot be null or empty");
        } else if (comment == null || comment.trim().length() == 0) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        AssetItem old = this.repository.loadAssetByUUID(oldVersionUUID);
        AssetItem head = this.repository.loadAssetByUUID(newVersionUUID);

        try {
            this.repository.restoreHistoricalAsset(old, head, comment);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Restoring version failed: " + "oldVersionUUID=" + oldVersionUUID + ", newVersionUUID=" + newVersionUUID, e);
        }

    }

    /**
     * Exports the rules repository as a zip file container an XML repository file.
     * 
     * @param filename
     *            E.g. repository_export.xml.zip
     * @return A zip file
     * @throws BRMSRepositoryException
     */
    public ByteArrayOutputStream exportRulesRepositoryAsZip(String filename) throws BRMSRepositoryException {
        if (filename == null || filename.trim().length() == 0) {
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
            throw new BRMSRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        }
    }

    /**
     * Exports the rules repository as XML.
     * 
     * @param filename
     *            E.g. repository_export.xml
     * @return XML content as a byte array
     * @throws BRMSRepositoryException
     */
    public byte[] exportRulesRepositoryAsXml() throws BRMSRepositoryException {
        try {
            return this.repository.dumpRepositoryXml();
        } catch (IOException e) {
            throw new BRMSRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        } catch (PathNotFoundException e) {
            throw new BRMSRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        } catch (RepositoryException e) {
            throw new BRMSRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        }
    }

    /**
     * Imports an XML rules repository file.
     * 
     * @param byteArray
     *            Byte array (XML file) - E.g. an repository_export.xml
     * @throws BRMSRepositoryException
     */
    public void importRulesRepository(byte byteArray[]) throws BRMSRepositoryException {
        if (byteArray == null || byteArray.length == 0) {
            throw new IllegalArgumentException("byteArray cannot be null or empty");
        }

        try {
            this.repository.importRulesRepository(byteArray);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Importing rules repository failed", e);
        }
    }

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
    public String createRuleSet(String name, String description) throws BRMSRepositoryException {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }

        try {
            PackageItem item = this.repository.createPackage(name, description);
            this.repository.save();
            return item.getUUID();
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Creating rule set failed: name=" + name, e);
        }
    }

    /**
     * Adds a fact to a rule set. E.g. in Drools: import java.util.Calendar
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @param fact
     *            A java class
     * @throws BRMSRepositoryException
     */
    public void setFactsToRuleSet(String ruleSetUuid, String fact) throws BRMSRepositoryException {
        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUuid);
            pkg.updateHeader(fact);
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Adding fact to rule set failed: ruleSetUuid=" + ruleSetUuid, e);
        }
    }

    /**
     * Loads all rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws BRMSRepositoryException
     */
    public RuleSet loadRuleSet(String uuid) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(uuid);
            RuleSet ruleSet = DroolsUtil.buildRuleSet(pkg);
            return ruleSet;
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Loading rule set failed: uuid=" + uuid, e);
        }
    }

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws BRMSRepositoryException
     */
    public Rule loadRule(String uuid) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }

        try {
            AssetItem item = repository.loadAssetByUUID(uuid);
            Rule rule = DroolsUtil.buildRule(item);

            return rule;
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Loading rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Creates a new status.
     * 
     * @param name
     *            Status name
     * @return New status uuid
     * @throws BRMSRepositoryException
     */
    public String createStatus(String name) throws BRMSRepositoryException {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }

        try {
            String uuid = this.repository.createState(name).getNode().getUUID();
            this.repository.save();
            return uuid;
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Creating status failed: name=" + name, e);
        } catch (UnsupportedRepositoryOperationException e) {
            throw new BRMSRepositoryException("Creating status failed: name=" + name, e);
        } catch (RepositoryException e) {
            throw new BRMSRepositoryException("Creating status failed: name=" + name, e);
        }
    }

    /**
     * Loads all states (statuses).
     * 
     * @return Array of all states (statuses)
     * @throws BRMSRepositoryException
     */
    public String[] loadStates() throws BRMSRepositoryException {
        try {
            StateItem[] states = this.repository.listStates();
            String[] result = new String[states.length];
            for (int i = 0; i < states.length; i++) {
                result[i] = states[i].getName();
            }
            return result;
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Listing states failed", e);
        }
    }

    /**
     * Changes rule status by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @param newState
     *            New rule status
     * @throws BRMSRepositoryException
     */
    public void changeRuleStatus(String uuid, String newState) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newState == null || newState.trim().length() == 0) {
            throw new IllegalArgumentException("newState cannot be null or empty");
        }

        try {
            AssetItem asset = this.repository.loadAssetByUUID(uuid);
            asset.updateState(newState);
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Changing rule status failed: uuid=" + uuid, e);
        }
    }

    /**
     * Changes rule set status by uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @param newState
     *            New rule set status
     * @throws BRMSRepositoryException
     */
    public void changeRuleSetStatus(String uuid, String newState) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newState == null || newState.trim().length() == 0) {
            throw new IllegalArgumentException("newState cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(uuid);
            pkg.changeStatus(newState);
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Changing rule set status failed: uuid=" + uuid, e);
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
     * @throws BRMSRepositoryException
     */
    public void createRuleSetSnapshot(String ruleSetName, String snapshotName, boolean replaceExisting, String comment) throws BRMSRepositoryException {
        if (ruleSetName == null || ruleSetName.trim().length() == 0) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        } else if (snapshotName == null || snapshotName.trim().length() == 0) {
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
            throw new BRMSRepositoryException("Creating rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
    }

    /**
     * Removes a category.
     * 
     * @param categoryPath
     *            Category path
     * @throws BRMSRepositoryException
     */
    public void removeCategory(String categoryPath) throws BRMSRepositoryException {
        if (categoryPath == null || categoryPath.trim().length() == 0) {
            throw new IllegalArgumentException("categoryPath cannot be null or empty");
        }

        try {
            this.repository.loadCategory(categoryPath).remove();
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Removing category failed: " + "categoryPath" + categoryPath, e);
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
     * @throws BRMSRepositoryException
     */
    public String renameRule(String uuid, String newName) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newName == null || newName.trim().length() == 0) {
            throw new IllegalArgumentException("newName cannot be null or empty");
        }

        try {
            return this.repository.renameAsset(uuid, newName);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Renaming rule failed: " + "uuid" + uuid, e);
        }
    }

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws BRMSRepositoryException
     */
    public void archiveRule(String uuid, String comment) throws BRMSRepositoryException {
        archiveRule(uuid, comment, true);
    }

    /**
     * Unarchives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws BRMSRepositoryException
     */
    public void unArchiveRule(String uuid, String comment) throws BRMSRepositoryException {
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
     * @throws BRMSRepositoryException
     */
    private void archiveRule(String uuid, String comment, boolean archive) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
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
            throw new BRMSRepositoryException("Archiving rule failed: " + "uuid=" + uuid, e);
        }
    }

    /**
     * Archives an rule set.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws BRMSRepositoryException
     */
    public void archiveRuleSet(String uuid, String comment) throws BRMSRepositoryException {
        archiveRuleSet(uuid, comment, true);
    }

    /**
     * Unarchives an rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @throws BRMSRepositoryException
     */
    public void unArchiveRuleSet(String uuid, String comment) throws BRMSRepositoryException {
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
     * @throws BRMSRepositoryException
     */
    private void archiveRuleSet(String uuid, String comment, boolean archive) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
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
            throw new BRMSRepositoryException("Archiving rule failed: " + "uuid=" + uuid, e);
        }
    }

    /**
     * Renames a rule set.
     * 
     * @param uuid
     * @param newName
     *            New rule set name
     * @return uuid of new rule set
     * @throws BRMSRepositoryException
     */
    public String renameRuleSet(String uuid, String newName) throws BRMSRepositoryException {
        if (uuid == null || uuid.trim().length() == 0) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        } else if (newName == null || newName.trim().length() == 0) {
            throw new IllegalArgumentException("newName cannot be null or empty");
        }

        try {
            return this.repository.renamePackage(uuid, newName);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Renaming rule set failed: " + "uuid=" + uuid, e);
        }
    }

    /**
     * Compiles a rule set.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Any rule set building errors otherwise null
     * @throws BRMSRepositoryException
     */
    public BuilderResultList compileRuleSet(String ruleSetUUID) throws BRMSRepositoryException {
        if (ruleSetUUID == null || ruleSetUUID.trim().length() == 0) {
            throw new IllegalArgumentException("ruleSetUUID cannot be null or empty");
        }

        PackageItem item = null;

        try {
            item = this.repository.loadPackageByUUID(ruleSetUUID);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Compiling rule set failed: " + "ruleSetUUID=" + ruleSetUUID, e);
        }

        try {
            BuilderResultList result = DroolsUtil.compile(item);

            if (result != null && result.size() > 0) {
                return result;
            } else {
                item.updateCompiledPackage(DroolsUtil.getBinaryPackage(result.getPackage()));
                this.repository.save();
            }
        } catch (IOException e) {
            throw new BRMSRepositoryException("Compiling rule set failed", e);
        } catch (DroolsParserException e) {
            throw new BRMSRepositoryException("Compiling and parsing rule set failed", e);
        }
        return null;
    }

    /**
     * Compiles a rule set and returns the source code. For Drools, it returns the DRL.
     * 
     * @param ruleSetUUID
     *            Rule set uuid
     * @return Rule set source (Drools DRL)
     * @throws BRMSRepositoryException
     */
    public String compileRuleSetSource(String ruleSetUUID) throws BRMSRepositoryException {
        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUUID);

            BuilderResultList result = DroolsUtil.compile(pkg);

            if (result != null && result.size() > 0) {
                throw new BRMSRepositoryException("Compiling rule set failed: " + result);
            }
            return DroolsUtil.getDRL(pkg);
        } catch (IOException e) {
            throw new BRMSRepositoryException("Compiling rule set failed: ", e);
        } catch (DroolsParserException e) {
            throw new BRMSRepositoryException("Compiling rule set failed", e);
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Compiling rule set failed: " + "ruleSetUUID=" + ruleSetUUID, e);
        }
    }

    /**
     * Loads a compiled rule set.
     * 
     * @param ruleSetUuid
     *            Rule set uuid
     * @return A compiled rule set (<code>org.drools.rule.Package</code>)
     * @throws BRMSRepositoryException
     */
    public Object loadCompiledRuleSet(String ruleSetUuid) throws BRMSRepositoryException {
        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUuid);
            return DroolsUtil.getPackage(pkg.getCompiledPackageBytes());
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Loading rule set failed: " + "ruleSetUuid=" + ruleSetUuid, e);
        } catch (Exception e) {
            throw new BRMSRepositoryException("Loading rule set failed: " + "ruleSetUuid=" + ruleSetUuid, e);
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
     * @throws BRMSRepositoryException
     */
    public Object loadCompiledRuleSetSnapshot(String ruleSetName, String snapshotName) throws BRMSRepositoryException {
        try {
            PackageItem pkg = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);
            return DroolsUtil.getPackage(pkg.getCompiledPackageBytes());
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Loading compiled rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        } catch (Exception e) {
            throw new BRMSRepositoryException("Loading compiled rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
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
     * @throws BRMSRepositoryException
     */
    public RuleSet loadRuleSetSnapshot(String ruleSetName, String snapshotName) throws BRMSRepositoryException {
        try {
            PackageItem pkg = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);
            RuleSet ruleSet = DroolsUtil.buildRuleSet(pkg);
            return ruleSet;
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Loading rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
    }

    /**
     * Rebuilts all snapshots in the repository.
     * 
     * @throws BRMSRepositoryException
     */
    public void rebuildAllSnapshots() throws BRMSRepositoryException {
        try {
            Iterator<PackageItem> it = repository.listPackages();
            while (it.hasNext()) {
                PackageItem pkg = it.next();
                String[] snapshots = this.repository.listPackageSnapshots(pkg.getName());
                for (String snapshotName : snapshots) {
                    PackageItem snapshotItem = this.repository.loadPackageSnapshot(pkg.getName(), snapshotName);
                    BuilderResultList result = this.compileRuleSet(snapshotItem.getUUID());
                    if (result != null) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < result.size(); i++) {
                            sb.append('\n');
                            sb.append(result.get(i).toString());
                        }
                        sb.append('\n');
                        throw new BRMSRepositoryException("Rebuilding snapshot failed: snapshotName=" + snapshotName + sb.toString());
                    }
                }
            }
        } catch (RulesRepositoryException e) {
            throw new BRMSRepositoryException("Rebuilding all snapshots failed", e);
        }
    }

    /**
     * Compiles source code (e.g. A Drools DRL file) and returns a compiled rule engine specific object (e.g. a Drools
     * <code>org.drools.rule.Package</code>).
     * 
     * @param sourceDrl
     *            DRL file to compile
     * @return A drools package (<code>org.drools.rule.Package</code>)
     * @throws BRMSRepositoryException
     *             Thrown if compilation fails
     */
    public Object compileSource(Reader source) throws BRMSRepositoryException {
        try {
            PackageBuilder builder = new PackageBuilder();
            builder.addPackageFromDrl(source);
            org.drools.rule.Package pkg = builder.getPackage();
            pkg.checkValidity();
            return pkg;
        } catch (RuntimeException e) {
            throw new BRMSRepositoryException("Compiling DRL failed: " + e.getMessage(), e);
        } catch (DroolsParserException e) {
            throw new BRMSRepositoryException("Compiling and parsing DRL failed: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new BRMSRepositoryException(e);
        }
    }
}
