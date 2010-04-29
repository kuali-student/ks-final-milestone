/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.repository.drools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.ResourceType;
import org.drools.compiler.DroolsParserException;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.repository.AssetHistoryIterator;
import org.drools.repository.AssetItem;
import org.drools.repository.AssetItemIterator;
import org.drools.repository.CategorisableItem;
import org.drools.repository.CategoryItem;
import org.drools.repository.PackageItem;
import org.drools.repository.PackageIterator;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryException;
import org.drools.repository.StateItem;
import org.drools.repository.VersionableItem;
import org.kuali.student.brms.repository.RuleEngineRepository;
import org.kuali.student.brms.repository.drools.rule.DroolsConstants;
import org.kuali.student.brms.repository.drools.rule.RuleSetFactory;
import org.kuali.student.brms.repository.drools.util.DroolsUtil;
import org.kuali.student.brms.repository.exceptions.CategoryExistsException;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.exceptions.RuleExistsException;
import org.kuali.student.brms.repository.exceptions.RuleSetExistsException;
import org.kuali.student.brms.repository.rule.CompilerResultList;
import org.kuali.student.brms.repository.rule.Category;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

/**
 * This is the <a href="http://www.jboss.org/drools/">Drools</a> repository 
 * implementation of the rule engine repository interface 
 * {@link RuleEngineRepository}. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleEngineRepositoryDroolsImpl implements RuleEngineRepository {
    /** Drools rule repository */
    private RulesRepository repository;
    /** Drools utility class */
    private DroolsUtil droolsUtil = DroolsUtil.getInstance();

    public static final String KUALI_PACKAGE_TAG_PROPERTY_NAME = "drools:categoryReference";
    public static final String KUALI_PACKAGE_EFFECTIVE_DATE_PROPERTY_NAME = "drools:dateEffective";
    public static final String KUALI_PACKAGE_EXPIRY_DATE_PROPERTY_NAME = "drools:dateExpiry";
    public static final String KUALI_DROOLS_PACKAGE_SNAPSHOT_AREA_NAME = "drools:packagesnapshot_area";
    public static final String KUALI_DROOLS_ARCHIVE_NAME = "drools:archive";
    public static final String KUALI_DROOLS_PACKAGE_NODE_TYPE_NAME = "drools:packageNodeType";

    private final static class CategorisablePackageItem extends CategorisableItem {
    	public CategorisablePackageItem(RulesRepository rulesRepository, Node node) {
    		super(rulesRepository, node);
    	}
    	
        public VersionableItem getPrecedingVersion()
        	throws RulesRepositoryException {
            try {
                Node precedingVersionNode = getPrecedingVersionNode();
                if(precedingVersionNode != null)
                    return new CategorisablePackageItem(rulesRepository, precedingVersionNode);
            }
            catch(Exception e) {
                throw new RulesRepositoryException(e);
            }
            return null;
        }

        public VersionableItem getSucceedingVersion()
        	throws RulesRepositoryException {
            try {
                Node succeedingVersionNode = getSucceedingVersionNode();
                if(succeedingVersionNode != null)
                    return new CategorisablePackageItem(rulesRepository, succeedingVersionNode);
            }
            catch(Exception e) {
                throw new RulesRepositoryException(e);
            }
            return null;
        }
    }

    /**
     * Constructor.
     * 
     * @param repository Drools rules repository
     */
    public RuleEngineRepositoryDroolsImpl(final RulesRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads child categories.
     * 
     * @param categoryPath
     *            Category path
     * @return List of child category names
     * @throws RuleEngineRepositoryException Thrown if loading child categories fails
     */
    public List<String> loadChildCategories(final String categoryPath) {
        if (categoryPath == null || categoryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("categoryPath cannot be null or empty");
        }

        try {
            final List<String> list = new ArrayList<String>();
            CategoryItem item = this.repository.loadCategory(categoryPath);
            for (int i = 0; i < item.getChildTags().size(); i++) {
                CategoryItem item2 = (CategoryItem) item.getChildTags().get(i);
                list.add(item2.getName());
            }
            return list;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading child categories failed: " 
                    + "categoryPath=" + categoryPath + " - " + e.getMessage(), e);
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
     * @throws CategoryExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException Thrown if creating category fails
     */
    public Boolean createCategory(final String path, final String name, final String description) 
        throws CategoryExistsException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        } else if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Category description cannot be null or empty");
        }

        String newPath = path;
        if (newPath == null || "".equals(newPath)) {
            newPath = "/";
        }

        try {
            CategoryItem item = this.repository.loadCategory(newPath);
            item.addCategory(name, description);
            this.repository.save();
            return Boolean.TRUE;
        } catch (RulesRepositoryException e) {
            if (e.getCause() instanceof ItemExistsException) {
                throw new CategoryExistsException("Category already exists: " + "path=" + path, e);
            } else {
                throw new RuleEngineRepositoryException("Creating category failed: " + "path=" + path, e);
            }
        }
    }

    private boolean isSnapshot(Node parentNode)
	    throws RepositoryException
	{
	    return parentNode.getPath().indexOf(KUALI_DROOLS_PACKAGE_SNAPSHOT_AREA_NAME) != -1;
	}

    private boolean isArchived(Node parentNode)
	    throws RepositoryException
	{
	    return parentNode.getProperty(KUALI_DROOLS_ARCHIVE_NAME).getBoolean();
	}

    public List<RuleSet> loadRuleSetsByCategory(String categoryTag)
    	throws RulesRepositoryException {
        CategoryItem item = this.repository.loadCategory(categoryTag);
	    List<RuleSet> results = new ArrayList<RuleSet>();
	    try
	    {
	        PropertyIterator it = item.getNode().getReferences();
	        do
	        {
	            if(!it.hasNext()) {
	                break;
	            }
	            Property ruleLink = (Property)it.next();
	            Node parentNode = ruleLink.getParent();
	            if( parentNode.getPrimaryNodeType().getName().equals(KUALI_DROOLS_PACKAGE_NODE_TYPE_NAME)
	            		&& !isSnapshot(parentNode) && !isArchived(parentNode)) { 
		            PackageItem pkg = getCategorisablePackageItemItem(parentNode);
	            	RuleSet ruleSet = droolsUtil.buildRuleSet(pkg);
	            	results.add(ruleSet);
	            }
	        } while(true);
	        return results;
	    }
	    catch(RepositoryException e)
	    {
	        throw new RulesRepositoryException(e);
	    }
    }

    public List<RuleSet> loadRuleSetSnapshotsByCategory(String categoryTag)
    	throws RulesRepositoryException {
        CategoryItem item = this.repository.loadCategory(categoryTag);
	    List<RuleSet> results = new ArrayList<RuleSet>();
	    try
	    {
	        PropertyIterator it = item.getNode().getReferences();
	        do
	        {
	            if(!it.hasNext()) {
	                break;
	            }
	            Property ruleLink = (Property)it.next();
	            Node parentNode = ruleLink.getParent();
	            if( parentNode.getPrimaryNodeType().getName().equals(KUALI_DROOLS_PACKAGE_NODE_TYPE_NAME)
	            		&& isSnapshot(parentNode) && !isArchived(parentNode)) { 
		            PackageItem pkg = getCategorisablePackageItemItem(parentNode);
	            	RuleSet ruleSet = droolsUtil.buildRuleSet(pkg);
	            	results.add(ruleSet);
	            }
	        } while(true);
	        return results;
	    }
	    catch(RepositoryException e)
	    {
	        throw new RulesRepositoryException(e);
	    }
    }

    /*private List<RuleSet> loadRuleSetsByCategory(String categoryTag, 
    		boolean loadSnapshot, boolean loadArchivedAsset)
	    throws RulesRepositoryException {
        CategoryItem item = this.repository.loadCategory(categoryTag);
	    List<RuleSet> results = new ArrayList<RuleSet>();
	    try
	    {
	        PropertyIterator it = item.getNode().getReferences();
	        do
	        {
	            if(!it.hasNext())
	                break;
	            Property ruleLink = (Property)it.next();
	            Node parentNode = ruleLink.getParent();
//	            if( parentNode.getPrimaryNodeType().getName().equals(KUALI_DROOLS_PACKAGE_NODE_TYPE_NAME)
//	            		&& (loadSnapshot || !isSnapshot(parentNode)) 
//	            		&& (loadArchivedAsset || !isArchived(parentNode))) {
//	            	CategorisablePackageItem cat = new CategorisablePackageItem(this.repository, parentNode);
//	            	Node node = cat.getNode();
//	            	PackageItem pkg = new PackageItem(this.repository, node);
	            PackageItem pkg = getCategorisablePackageItemItem(parentNode);
	            	RuleSet ruleSet = droolsUtil.buildRuleSet(pkg);
	            	results.add(ruleSet);
	            //}
	        } while(true);
	        return results;
	    }
	    catch(RepositoryException e)
	    {
	        throw new RulesRepositoryException(e);
	    }
	}*/

    private PackageItem getCategorisablePackageItemItem(Node parentNode) {
    	CategorisablePackageItem cat = new CategorisablePackageItem(this.repository, parentNode);
    	Node node = cat.getNode();
    	return new PackageItem(this.repository, node);
    }

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
    public long checkinRuleSet(final String uuid, final String comment) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (comment == null || comment.isEmpty()) {
            throw new IllegalArgumentException("Rule set checkin comment cannot be null or empty");
        }

        try {
            PackageItem item = this.repository.loadPackageByUUID(uuid);
            item.updateDescription(item.getDescription());
            item.checkin(comment);
            item = this.repository.loadPackageByUUID(uuid);
            return item.getVersionNumber();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Checkin rule set failed: uuid=" + uuid, e);
        }
    }

    private void setCustomRuleSetProperties(PackageItem pkg, RuleSet ruleSet) 
    	throws RuleEngineRepositoryException {
        try {
	    	//pkg.getNode().setProperty(KUALI_PACKAGE_TAG_PROPERTY_NAME, ruleSet.getTag());
            CategorisablePackageItem item = new CategorisablePackageItem(this.repository, pkg.getNode());
            for(Category category : ruleSet.getCategories()) {
            	item.addCategory(category.getName());
            }
	        pkg.getNode().setProperty(KUALI_PACKAGE_EFFECTIVE_DATE_PROPERTY_NAME, ruleSet.getEffectiveDate());
	        pkg.getNode().setProperty(KUALI_PACKAGE_EXPIRY_DATE_PROPERTY_NAME, ruleSet.getExpiryDate());
        } catch (RepositoryException e ) {
            throw new RuleEngineRepositoryException("Setting package property failed", e);
        }
    }
    /**
     * Updates a rule set in the repository and returns an updated rule set.
     *  
     * @param ruleSet A rule set to update
     * @return An updated rule set
     * @throws RuleEngineRepositoryException Thrown if updating rule set fails
     */
    public RuleSet updateRuleSet(final RuleSet ruleSet) {
        if (ruleSet == null) {
            throw new IllegalArgumentException("ruleSet cannot be null or empty");
        } else if (ruleSet.getUUID() == null || ruleSet.getUUID().isEmpty()) {
            throw new IllegalArgumentException("Rule set UUID cannot be null or empty");
        } else if (ruleSet.getDescription() == null || ruleSet.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Rule set description cannot be null or empty");
        } else if (ruleSet.getFormat() == null || ruleSet.getFormat().isEmpty()) {
            throw new IllegalArgumentException("Rule set format cannot be null or empty");
        }
        
        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSet.getUUID());
            pkg.updateDescription(ruleSet.getDescription());
            pkg.updateFormat(ruleSet.getFormat());

            // Drools 5.0 - Header fix
            droolsUtil.updateDroolsHeader(pkg, ruleSet.getHeader());

            pkg.updateState(ruleSet.getStatus());
            createOrUpdateRule(pkg, ruleSet);
            setCustomRuleSetProperties(pkg, ruleSet);
            this.repository.save();
            return droolsUtil.buildRuleSet(pkg);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Checkin rule set failed: uuid=" + ruleSet.getUUID(), e);
        }
    }

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
    public long checkinRule(final String uuid, final String comment) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        try {
            AssetItem item = this.repository.loadAssetByUUID(uuid);
            item.updateDateEffective(item.getDateEffective());
            item.updateDateExpired(item.getDateExpired());
            item.checkin(comment);
            item = this.repository.loadAssetByUUID(uuid);
            return item.getVersionNumber();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Checkin rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Updates a rule in the repository and returns an updated rule.
     * 
     * @param rule A rule to update
     * @return An updated rule
     * @throws RuleEngineRepositoryException Thrown if updating rule fails
     */
    public Rule updateRule(final Rule rule) {
        if (rule.getUUID() == null || rule.getUUID().trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (rule.getDescription() == null || rule.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Rule description cannot be null or empty");
        } else if (rule.getFormat() == null || rule.getFormat().isEmpty()) {
            throw new IllegalArgumentException("Rule format cannot be null or empty");
        }

        AssetItem item = this.repository.loadAssetByUUID(rule.getUUID());
        return updateRule(item, rule);
    }
    
    /**
     * Updates a <code>rule</code> as an <code>item</code> in the repository.
     * 
     * @param item Item to be updated
     * @param rule Rule to update item with
     * @return An updated rule
     * @throws RuleEngineRepositoryException Thrown if updating rule fails
     */
    private Rule updateRule(final AssetItem item, final Rule rule) {
        try {
            item.updateContent(rule.getContent());
            item.updateDescription(rule.getDescription());
            item.updateFormat(rule.getFormat());
            item.updateState(rule.getStatus());
            item.archiveItem(rule.isArchived());
            item.updateDateEffective(rule.getEffectiveDate());
            item.updateDateExpired(rule.getExpiryDate());
            this.repository.save();
            return droolsUtil.buildRule(item);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Update rule failed: name=" + 
                    rule.getName() + ", uuid=" + rule.getUUID(), e);
        }
    }

    /**
     * Deletes a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @throws RuleEngineRepositoryException Thrown if removing rule fails
     */
    public void removeRule(final String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
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
     * Deletes a rule set by uuid.
     * 
     * @param uuid Rule set uuid
     * @throws RuleEngineRepositoryException Thrown if removing rule set fails
     */
    public void removeRuleSet(final String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        }

        try {
            Node rulePackageNode = this.repository.getSession().getNodeByUUID(uuid);
            rulePackageNode.remove();
            this.repository.save();
        } catch (ItemNotFoundException e) {
            throw new RuleEngineRepositoryException("Rule set not found: uuid=" + uuid, e);
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Removing rule set failed: uuid=" + uuid, e);
        }
    }

    /**
     * Loads a rule's history.
     * 
     * @param uuid
     *            Rule uuid
     * @return List of history rules
     * @throws RuleEngineRepositoryException Thrown if loading rule history fails
     */
    public List<Rule> loadRuleHistory(final String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        }

        try {
            AssetItem item = this.repository.loadAssetByUUID(uuid);
            AssetHistoryIterator it = item.getHistory();
            List<Rule> list = new ArrayList<Rule>();
            while (it != null && it.hasNext()) {
                AssetItem historyItem = (AssetItem) it.next();
                long versionNumber = historyItem.getVersionNumber();
                if (versionNumber != 0 && versionNumber != item.getVersionNumber()) {
                    Rule rule = droolsUtil.buildHistoricalRule(historyItem);
                    list.add(rule);
                }
            }
            return list;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule history failed: uuid=" + uuid, e);
        }
    }

    /**
     * Loads a rule set history
     * 
     * @param uuid Rule set uuid
     * @return A list of <code>RuleSet</code>s
     * @throws RuleEngineRepositoryException Thrown because method is not yet implemented due to a Drools bug
     */
    public List<RuleSet> loadRuleSetHistory(final String uuid) {
        throw new RuleEngineRepositoryException("Not implemented - Drools repository bug? - Cannot load rule sets history");

        // XXX This doesn't work properly - Drools bug???
        /*if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
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
                    RuleSet ruleset = droolsUtil.buildHistoricalRuleSet(historyItem);
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
     * @throws RuleEngineRepositoryException Thrown because method is not yet implemented due to a Drools bug
     */
    public List<RuleSet> loadArchivedRuleSets() {
        throw new RuleEngineRepositoryException("Not implemented - Drools repository bug - Cannot load archived rule sets");

        // XXX This doesn't work properly - Drools bug???
        /*try { 
            System.out.println( "\n\n****************** loadArchivedRuleSets *****************\n\n" ); 
            Iterator<PackageItem> it = findArchivedPackages(); 
            List<RuleSet> list = new ArrayList<RuleSet>(); 
            while( it.hasNext() ) { 
                PackageItem item = it.next(); 
                System.out.println( "***** item=" +item.getUUID() + ", name=" +item.getName() + ", isArchived=" + item.isArchived() ); 
                RuleSet ruleSet = droolsUtil.buildRuleSet( item ); 
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
     * @throws RuleEngineRepositoryException Thrown if archived packages (rule sets) fails
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

            // Drools repository does not use generics so we have to suppress warnings
            @SuppressWarnings("unchecked") Iterator<PackageItem> it = new PackageIterator(this.repository, res.getNodes());
            return it;
        } catch (RepositoryException e) {
            throw new RulesRepositoryException("Finding archived rule sets failed", e);
        }
    }

    /**
     * Loads all archived rules.
     * 
     * @return List of archived rules
     * @throws RuleEngineRepositoryException Thrown if loading archived rules fails
     */
    public List<Rule> loadArchivedRules() {
        try {
            AssetItemIterator it = this.repository.findArchivedAssets();
            List<Rule> list = new ArrayList<Rule>();
            while (it.hasNext()) {
                AssetItem item = (AssetItem) it.next();
                Rule rule = droolsUtil.buildRule(item);
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
     * @param oldVersionUUID
     *            Old version UUID
     * @param newVersionUUID
     *            New version UUID
     * @param comment
     *            Comments of why a version was restored
     * @throws RuleEngineRepositoryException Thrown if restoring version fails
     */
    public void restoreVersion(final String oldVersionUUID, final String newVersionUUID, final String comment) {
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
     * @throws RuleEngineRepositoryException Thrown if exporting rules repository fails
     */
    public ByteArrayOutputStream exportRulesRepositoryAsZip(final String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("filename cannot be null or empty");
        }

        ByteArrayOutputStream bout = null;
        ZipOutputStream zout = null;
        
        try {
            bout = new ByteArrayOutputStream();
            zout = new ZipOutputStream(bout);
            zout.putNextEntry(new ZipEntry(filename));
            zout.write(exportRulesRepositoryAsXml());
            zout.closeEntry();
            zout.finish();
            return bout;
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
        }
        finally {
            if ( bout != null ) {
                try { 
                    bout.close(); 
                } 
                catch( IOException e ) {
                    throw new RuleEngineRepositoryException( "Exporting repository failed", e );
                }
            }
            if ( zout != null ) {
                try { 
                    zout.close();
                } 
                catch( IOException e ) {
                    throw new RuleEngineRepositoryException( "Exporting repository failed", e );
                }
            }
        }
    }

    /**
     * Exports the rules repository as XML.
     * 
     * @return XML content as a byte array
     * @throws RuleEngineRepositoryException Thrown if exporting rules repository fails
     */
    public byte[] exportRulesRepositoryAsXml() {
//        try {
        	ByteArrayOutputStream bout = new ByteArrayOutputStream();
        	this.repository.exportRulesRepositoryToStream(bout);
        	return bout.toByteArray();
//        } catch (IOException e) {
//            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
//        } catch (PathNotFoundException e) {
//            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
//        } catch (RepositoryException e) {
//            throw new RuleEngineRepositoryException("Exporting repository failed: " + e.getMessage(), e);
//        }
    }

    /**
     * Imports an XML rules repository file.
     * 
     * @param byteArray
     *            Byte array (XML file) - E.g. an repository_export.xml
     * @throws RuleEngineRepositoryException Thrown if importing rules repository fails
     */
    public void importRulesRepositoryAsXml(final byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            throw new IllegalArgumentException("byteArray cannot be null or empty");
        }

        try {
        	ByteArrayInputStream bin = new ByteArrayInputStream(byteArray);
        	this.repository.importRulesRepositoryFromStream(bin);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Importing rules repository failed", e);
        }
    }

    /**
     * Loads a rule set (and rules) for a specific uuid.
     * 
     * @param uuid
     *            Rule set uuid
     * @return A rule set
     * @throws RuleEngineRepositoryException Thrown if loading rule set fails
     */
    public RuleSet loadRuleSet(final String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(uuid);
            RuleSet ruleSet = droolsUtil.buildRuleSet(pkg);
            return ruleSet;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set failed: uuid=" + uuid, e);
        }
    }

    /**
     * 
     * Loads a rule set (and rules) for a specific rule set name.
     * 
     * @param ruleSetName rule set name
     * @return A rule set
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    public RuleSet loadRuleSetByName(final String ruleSetName) {
        if (ruleSetName == null || ruleSetName.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackage(ruleSetName);
            RuleSet ruleSet = droolsUtil.buildRuleSet(pkg);
            return ruleSet;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set failed: ruleSetName=" + ruleSetName, e);
        }
    }

    /**
     * Returns true if the repository contains the specified 
     * <code>ruleSetUUID</code> otherwise false.
     * 
     * @param ruleSetUUID Rule set UUID
     * @return True if contains <code>ruleSetUUID</code> otherwise false
     */
    public boolean containsRuleSet(final String ruleSetUUID) {
        if (ruleSetUUID == null || ruleSetUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetUUID cannot be null or empty");
        }

        try {
			Node packageNode = this.repository.getSession().getNodeByUUID( ruleSetUUID );
			return (packageNode == null ? false : true);
		} catch (ItemNotFoundException e) {
            return false;
		} catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set failed: ruleSetUUID=" + ruleSetUUID, e);
		}
    }

    /**
     * Returns true if the repository contains the specified 
     * <code>ruleSetName</code> otherwise false.
     * 
     * @param ruleSetName Rule set name
     * @return True if contains <code>ruleSetName</code> otherwise false
     */
    public boolean containsRuleSetByName(final String ruleSetName) {
        if (ruleSetName == null || ruleSetName.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        }

        try {
            return this.repository.containsPackage(ruleSetName);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set failed: ruleSetName=" + ruleSetName, e);
        }
    }

    /**
     * Loads a rule by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @return A rule
     * @throws RuleEngineRepositoryException Thrown if loading rule fails
     */
    public Rule loadRule(final String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        }

        try {
            AssetItem item = repository.loadAssetByUUID(uuid);
            Rule rule = droolsUtil.buildRule(item);

            return rule;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule failed: uuid=" + uuid, e);
        }
    }

    /**
     * Creates a new status if it doesn't already exists.
     * 
     * @param name Status name
     * @return New status uuid
     * @throws RuleEngineRepositoryException Thrown if creating status fails
     */
    public String createStatus(final String name) {
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
     * Removes a status fro the repositiory.
     * 
     * @param uuid Status uuid
     * @throws RuleEngineRepositoryException Thrown if removing status fails
     */
    public void removeStatus(final String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        }

        try {
			this.repository.getState(name).getNode().remove();
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("RulesRepositoryException. Removing status failed: name=" + name, e);
		} catch (VersionException e) {
            throw new RuleEngineRepositoryException("VersionException. Removing status failed: name=" + name, e);
		} catch (LockException e) {
            throw new RuleEngineRepositoryException("LockException. Removing status failed: name=" + name, e);
		} catch (ConstraintViolationException e) {
            throw new RuleEngineRepositoryException("ConstraintViolationException. Removing status failed: name=" + name, e);
		} catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("RepositoryException. Removing status failed: name=" + name, e);
		}
    }

    /**
     * Loads all states.
     * 
     * @return List of all states
     * @throws RuleEngineRepositoryException Thrown if loading states fails
     */
    public List<String> loadStates() {
        try {
            StateItem[] states = this.repository.listStates();
            List<String> result = new ArrayList<String>(states.length);
            for (int i = 0; i < states.length; i++) {
                result.add(states[i].getName());
            }
            return result;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading states failed", e);
        }
    }

    /**
     * Returns true if the repository contains the specified 
     * <code>status</code>; otherwise false.
     * 
     * @param status Status to check
     * @return True if repository contains the specified status; otherwise false
     */
    public boolean containsStatus(String status) {
    	return this.loadStates().contains(status);
    }
    
    /**
     * Changes rule status by uuid.
     * 
     * @param uuid
     *            Rule uuid
     * @param newState
     *            New rule status
     * @throws RuleEngineRepositoryException Thrown if changing rule status fails
     */
    public void changeRuleStatus(final String uuid, final String newState) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
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
     * @throws RuleEngineRepositoryException Thrown if changing rule set status fails
     */
    public void changeRuleSetStatus(final String uuid, final String newState) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
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
    public RuleSet createRuleSetSnapshot(final String ruleSetName, final String snapshotName, final String comment) {
        return createRuleSetSnapshot(ruleSetName, snapshotName, false, comment);
    }

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
     * @return New rule set
     * @throws RuleEngineRepositoryException 
     *            Thrown if rule set fails to compile or any other errors occur
     */
    public void rebuildRuleSetSnapshot(final String ruleSetName, 
            final String snapshotName) {
        PackageItem snapshotItem = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);
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
    
    /**
     * Replaces a rule set snapshot.
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @param comment Snapshot comments
     * @return
     */
    public RuleSet replaceRuleSetSnapshot(final String ruleSetName, 
            final String snapshotName, final String comment) {
        return createRuleSetSnapshot(ruleSetName, snapshotName, true, comment);
    }
    
    /**
     * Creates or replaces a rule set snapshot for deployment. 
     * Creates a copy of the rule set for deployment.
     * If the rule set fails to compile, an exception will be thrown and
     * a snapshot will not be created. 
     * 
     * @param ruleSetName
     *            Rule set name
     * @param snapshotName
     *            Snapshot name
     * @param replaceExisting
     *            Replace existing snapshot
     * @param comment
     *            Comments for creating the snapshot
     * @return New rule set
     * @throws RuleEngineRepositoryException 
     *            Thrown if rule set fails to compile or any other errors occur
     */
    private RuleSet createRuleSetSnapshot(
            final String ruleSetName, final String snapshotName, 
            final boolean replaceExisting, final String comment) {
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
            PackageItem pkg = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);

            try {
                CompilerResultList result = droolsUtil.compile(pkg);
    
                if (result != null && !result.isEmpty()) {
                    throw new RuleEngineRepositoryException("Compiling rule set failed: " + result);
                }
                KnowledgePackage compiledknowledge = result.getKnowledgePackage();
                pkg.updateCompiledPackage(droolsUtil.getBinaryKnowledgePackage(compiledknowledge));
            } catch (IOException e) {
                throw new RuleEngineRepositoryException("Compiling rule set failed", e);
            } catch (DroolsParserException e) {
                throw new RuleEngineRepositoryException("Compiling and parsing rule set failed", e);
            }

            pkg.checkin(comment);
            this.repository.save();
            return droolsUtil.buildRuleSet(pkg);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Creating rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
    }

    /**
     * 
     * Removes a rule set snapshot. 
     * 
     * @param ruleSetName Rule set name
     * @param snapshotName Snapshot name
     * @throws RuleEngineRepositoryException Thrown if snapshot fails to be removed or any other errors occur
     */
    public void removeRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
        if (ruleSetName == null || ruleSetName.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        } else if (snapshotName == null || snapshotName.trim().isEmpty()) {
            throw new IllegalArgumentException("snapshotName cannot be null or empty");
        }

        try {
            this.repository.removePackageSnapshot(ruleSetName, snapshotName);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException(
                    "Removing rule set snapshot failed: " + "ruleSetName="
                            + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
        this.repository.save();
    }

    /**
     * Removes a category.
     * 
     * @param categoryPath
     *            Category path
     * @throws RuleEngineRepositoryException Thrown if removing a category fails
     */
    public void removeCategory(final String categoryPath) {
        if (categoryPath == null || categoryPath.trim().isEmpty()) {
            throw new IllegalArgumentException("categoryPath cannot be null or empty");
        }

        try {
            this.repository.loadCategory(categoryPath).remove();
            this.repository.save();
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Removing category failed: " + "categoryPath=" + categoryPath, e);
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
     * @throws RuleEngineRepositoryException Thrown if renaming a rule fails
     */
    public String renameRule(final String uuid, final String newName) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("newName cannot be null or empty");
        }

        try {
            return this.repository.renameAsset(uuid, newName);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Renaming rule failed: " + "uuid=" + uuid, e);
        }
    }

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException Thrown if archiving a rule fails
     */
    public void archiveRule(final String uuid, final String comment) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        archiveRule(uuid, comment, true);
    }

    /**
     * Unarchives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException Thrown if unarchiving a rule fails
     */
    public void unArchiveRule(final String uuid, final String comment) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        archiveRule(uuid, comment, false);
    }

    /**
     * Archives a rule.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @param archive
     *            If true archive rule, otherwise unarchive rule
     * @throws RuleEngineRepositoryException Thrown if archiving a rule fails
     */
    private void archiveRule(String uuid, String comment, boolean archive) {
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
     * Archives a rule set.
     * 
     * @param uuid
     *            Rule uuid
     * @param comment
     *            Rule comment
     * @throws RuleEngineRepositoryException Thrown if archiving a rule set fails
     */
    public void archiveRuleSet(final String uuid, final String comment) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        archiveRuleSet(uuid, comment, true);
    }

    /**
     * Unarchives a rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @throws RuleEngineRepositoryException Thrown if unarchiving a rule set fails
     */
    public void unArchiveRuleSet(final String uuid, final String comment) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        archiveRuleSet(uuid, comment, false);
    }

    /**
     * Archives a rule set.
     * 
     * @param uuid
     *            Rule set uuid
     * @param comment
     *            Rule set comment
     * @param archive
     *            If true archive rule set, otherwise unarchive rule set
     * @throws RuleEngineRepositoryException Thrown if archiving a rule set fails
     */
    private void archiveRuleSet(final String uuid, final String comment, boolean archive) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
        } else if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("comment cannot be null or empty");
        }

        try {
            PackageItem item = this.repository.loadPackageByUUID(uuid);
            item.archiveItem(archive);
            //if (comment == null) {
            //    if (archive) {
            //        comment = "Archived";
            //    } else {
            //        comment = "Unarchived";
            //    }
            //}
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
     * @throws RuleEngineRepositoryException Thrown if renaming a rule set fails
     */
    public String renameRuleSet(final String uuid, final String newName) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new IllegalArgumentException("uuid cannot be null or empty");
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
     * @return Any rule set compilation errors otherwise null
     * @throws RuleEngineRepositoryException Thrown if compiling a rule set fails
     */
    public CompilerResultList compileRuleSet(final String ruleSetUUID) {
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
            CompilerResultList result = droolsUtil.compile(item);

            if (result != null && !result.isEmpty()) {
                return result;
            } else {
                KnowledgePackage compiledknowledge = result.getKnowledgePackage();
                item.updateCompiledPackage(droolsUtil.getBinaryKnowledgePackage(compiledknowledge));
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
     * @throws RuleEngineRepositoryException Thrown if compiling a rule set fails
     */
    public String compileRuleSetSource(final String ruleSetUUID) {
        if (ruleSetUUID == null || ruleSetUUID.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetUUID cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageByUUID(ruleSetUUID);

            CompilerResultList result = droolsUtil.compile(pkg);

            if (result != null && result.size() > 0) {
                throw new RuleEngineRepositoryException("Compiling rule set failed: " + result);
            }
            return droolsUtil.getDRL(pkg);
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed: ", e);
        } catch (DroolsParserException e) {
            throw new RuleEngineRepositoryException("Compiling and parsing rule set failed", e);
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed: " + "ruleSetUUID=" + ruleSetUUID, e);
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
     * @throws RuleEngineRepositoryException Thrown if loading a snapshot fails
     */
    public RuleSet loadRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
        if (ruleSetName == null || ruleSetName.trim().isEmpty()) {
            throw new IllegalArgumentException("ruleSetName cannot be null or empty");
        } else if (snapshotName == null || snapshotName.trim().isEmpty()) {
            throw new IllegalArgumentException("snapshotName cannot be null or empty");
        }

        try {
            PackageItem pkg = this.repository.loadPackageSnapshot(ruleSetName, snapshotName);
            RuleSet ruleSet = droolsUtil.buildRuleSet(pkg);
            return ruleSet;
        } catch (RulesRepositoryException e) {
            throw new RuleEngineRepositoryException("Loading rule set snapshot failed: " + "ruleSetName=" + ruleSetName + ", snapshotName=" + snapshotName, e);
        }
    }

    /**
     * Rebuilds all snapshots in the repository.
     * 
     * @throws RuleEngineRepositoryException Thrown if rebuilding snapshots fails
     */
    public void rebuildAllSnapshots() {
        try {
        	// Drools repository does not use generics so we have to suppress warnings
        	@SuppressWarnings("unchecked") Iterator<PackageItem> it = repository.listPackages();
            while (it.hasNext()) {
                PackageItem pkg = it.next();
                String[] snapshots = this.repository.listPackageSnapshots(pkg.getName());
                for (String snapshotName : snapshots) {
                    /*PackageItem snapshotItem = this.repository.loadPackageSnapshot(pkg.getName(), snapshotName);
                    CompilerResultList result = this.compileRuleSet(snapshotItem.getUUID());
                    if (result != null) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < result.size(); i++) {
                            sb.append('\n');
                            sb.append(result.get(i).toString());
                        }
                        sb.append('\n');
                        throw new RuleEngineRepositoryException("Rebuilding snapshot failed: snapshotName=" + snapshotName + sb.toString());
                    }*/
                	rebuildRuleSetSnapshot(pkg.getName(), snapshotName);
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
     * @param source
     *            DRL file to compile
     * @return A drools package (<code>org.drools.rule.Package</code>)
     * @throws RuleEngineRepositoryException
     *             Thrown if compilation fails
     */
    public Object compileSource(final Reader source) {
        if (source == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }
        
        try {
	        return droolsUtil.buildKnowledgePackage(source);
        } catch (Exception e) {
            throw new RuleEngineRepositoryException(e);
        }
    }

    /**
     * Creates and compiles a rule set.
     * 
     * @param ruleSet
     *            Rule set to create
     * @return A new rule set (containing a uuid)
     * @throws RuleExistsException Thrown if a rule within the rule set already exists
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException Throws if compiling a rule set fails
     * @throws IllegalArgumentException Thrown if <code>ruleSet</code> is null, name is null or empty or description is null
     */
    public RuleSet createRuleSet(final RuleSet ruleSet) 
    {
        if (ruleSet == null) {
            throw new IllegalArgumentException("ruleSet");
        } else if (ruleSet.getName() == null || ruleSet.getName().isEmpty()) {
            throw new IllegalArgumentException("Rule set name cannot be null or empty");
        } else if (ruleSet.getDescription() == null) {
            throw new IllegalArgumentException("Rule set description cannot be null");
        }

        PackageItem pkg = createPackage(ruleSet);
        createRule(pkg, ruleSet);

        try {
            CompilerResultList result = droolsUtil.compile(pkg);

            if (result != null && !result.isEmpty()) {
                throw new RuleEngineRepositoryException("Compiling rule set failed: " + result);
            }
            KnowledgePackage compiledknowledge = result.getKnowledgePackage();
            pkg.updateCompiledPackage(droolsUtil.getBinaryKnowledgePackage(compiledknowledge));
            setCustomRuleSetProperties(pkg, ruleSet);
        } catch (IOException e) {
            throw new RuleEngineRepositoryException("Compiling rule set failed", e);
        } catch (DroolsParserException e) {
            throw new RuleEngineRepositoryException("Compiling and parsing rule set failed", e);
        }

        this.repository.save();
        return droolsUtil.buildRuleSet(pkg);
    }

    /**
     * Creates a drools repository <code>PackageItem</code>
     * 
     * @param ruleSet A rule set
     * @return A Drools package item
     * @throws RuleSetExistsException Thrown if rule set already exists
     * @throws RuleEngineRepositoryException Thrown if creating a rule set fails
     */
    private PackageItem createPackage(final RuleSet ruleSet) 
        throws RuleSetExistsException
    {
        try {
            PackageItem pkg = this.repository.createPackage(ruleSet.getName(), ruleSet.getDescription());
            // Drools 5.0 header fix - null category
            droolsUtil.addDroolsHeader(pkg, ruleSet.getHeader());
            this.repository.save();
            return pkg;
        } catch (RulesRepositoryException e) {
            if (e.getCause() instanceof ItemExistsException) {
                throw new RuleSetExistsException("Creating new rule set failed - " + 
                        "Rule set already exists: rule name=" + ruleSet.getName(), e);
            } else {
                throw new RuleEngineRepositoryException("Creating new rule set failed: " + 
                        "rule set name=" + ruleSet.getName(), e);
            }
        }
    }
    
    /**
     * Creates a <code>Rule</code> in a <code>PackageItem</code> 
     * if it does not already exist.
     * 
     * @param pkg A Drools package item
     * @param rule A kuali rule
     * @throws RuleExistsException Throws if a rule already exists
     * @throws RuleEngineRepositoryException Thrown if creating a rule fails
     */
    private void createRule(final PackageItem pkg, final RuleSet ruleSet) 
        throws RuleExistsException
    {
        final List<Rule> rules = ruleSet.getRules();

        for(Rule rule : rules) {
            if (rule.getName() == null || rule.getName().isEmpty()) {
                throw new IllegalArgumentException("Rule name cannot be null or empty");
            } else if (rule.getDescription() == null || rule.getDescription().isEmpty()) {
                throw new IllegalArgumentException("Rule description cannot be null or empty");
            } else if (rule.getFormat() == null || rule.getFormat().isEmpty()) {
                throw new IllegalArgumentException("Rule format cannot be null or empty");
            }
    
            try {
                AssetItem asset = pkg.addAsset(rule.getName(), 
                        rule.getDescription(), null, rule.getFormat());
                asset.updateContent(rule.getContent());
                asset.updateDescription(rule.getDescription());
                String categories[] = rule.getCategoryNames().toArray(new String[]{});
                asset.updateCategoryList(categories);
            } catch (RulesRepositoryException e) {
                if (e.getCause() instanceof ItemExistsException) {
                    throw new RuleExistsException("Creating new rule failed - " + 
                            "Rule already exists: ruleName=" + rule.getName(), e);
                } else {
                    throw new RuleEngineRepositoryException("Creating new rule failed: " + 
                            "ruleName=" + rule.getName(), e);
                }
            }
        }
    }

    /**
     * Updates rules in a PackageItem.
     * 
     * @param pkg Package to update rules in
     * @param ruleSet Ruleset container the rules to be updated
     * @throws RuleExistsException Throws if a rule already exists
     * @throws RuleEngineRepositoryException Thrown if creating or updating a rule fails
     */
    private void createOrUpdateRule(final PackageItem pkg, final RuleSet ruleSet) 
        throws RuleExistsException
    {
        final List<Rule> rules = ruleSet.getRules();
    
        for(Rule rule : rules) {
            if (rule.getName() == null || rule.getName().isEmpty()) {
                throw new IllegalArgumentException("Rule name cannot be null or empty");
            } else if (rule.getDescription() == null || rule.getDescription().isEmpty()) {
                throw new IllegalArgumentException("Rule description cannot be null or empty");
            } else if (rule.getFormat() == null || rule.getFormat().isEmpty()) {
                throw new IllegalArgumentException("Rule format cannot be null or empty");
            }

            try {
                //if (rule.getUUID() != null && pkg.containsAsset(rule.getName())) {
                if (pkg.containsAsset(rule.getName())) {
                    AssetItem item = pkg.loadAsset(rule.getName());
                    updateRule(item, rule);
                } else {
                	AssetItem asset = pkg.addAsset(rule.getName(), 
                            rule.getDescription(), null, rule.getFormat());
                    asset.updateContent(rule.getContent());
                    asset.updateDescription(rule.getDescription());
                    String categories[] = rule.getCategoryNames().toArray(new String[]{});
                    asset.updateCategoryList(categories);
                }
            } catch (RulesRepositoryException e) {
                if (e.getCause() instanceof ItemExistsException) {
                    throw new RuleExistsException("Creating new rule failed - " + 
                            "Rule already exists: ruleName=" + rule.getName(), e);
                } else {
                    throw new RuleEngineRepositoryException("Creating or updating rule failed: " + 
                            "ruleName=" + rule.getName(), e);
                }
            }
        }
    }

    /**
     * <p>Loads all rules in a specific rule category.</p>
     * <p>This is a dynamic rule set which is not stored in the repository
     * and therefore has no UUID or version. The rule set's name is the same 
     * as the <code>category</name> name.</p> 
     * 
     * @param category Category rules belong to
     * @return A dynamic rule set
     */
    public RuleSet loadRuleSetByRuleCategory(final String category) {
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet(
                category, "A dynamic rule set", DroolsConstants.FORMAT_DRL);
        // Load all rules in a specific category
    	// Drools 5.0 - findAssetsByCategory
        List<AssetItem> items = this.repository.findAssetsByCategory(category, 0, -1).assets;
        for(AssetItem item : items) {
        	// Drools 5.0 - getDroolsHeader
            String header = droolsUtil.getDroolsHeader(item.getPackage());
            droolsUtil.addRuleSetHeader(ruleSet,header);
            Rule rule = droolsUtil.buildRule(item);
            // Drools 5.0 - filter out null rule 
            if (rule != null) {
	            ruleSet.addRule(rule);
            }
        }
        return ruleSet;
    }
}
