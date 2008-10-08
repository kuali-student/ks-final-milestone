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
package org.kuali.student.rules.repository.drools.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.drools.common.DroolsObjectInputStream;
import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.lang.descr.PackageDescr;
import org.drools.repository.AssetItem;
import org.drools.repository.CategoryItem;
import org.drools.repository.PackageItem;
import org.drools.repository.VersionableItem;
import org.drools.rule.Package;
import org.drools.util.ChainedProperties;
import org.kuali.student.rules.repository.drools.RuleEngineRepositoryDroolsImpl;
import org.kuali.student.rules.repository.drools.rule.CategoryFactory;
import org.kuali.student.rules.repository.drools.rule.DroolsRuleImpl;
import org.kuali.student.rules.repository.drools.rule.DroolsRuleSetImpl;
import org.kuali.student.rules.repository.drools.rule.RuleFactory;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.rule.Category;
import org.kuali.student.rules.repository.rule.CompilerResult;
import org.kuali.student.rules.repository.rule.CompilerResultList;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a general Drools utility class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class DroolsUtil {
    
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(DroolsUtil.class);

    /**
     * Private Constructor.
     *
     */
    private DroolsUtil() {
    }
    
    /**
     * Returns a new instance of <code>DroolsUtil</code>
     * 
     * @return A new instance of <code>DroolsUtil</code>
     */
    public static DroolsUtil getInstance() {
        return new DroolsUtil();
    }
    
    /**
     * Builds a rule from an Drools repository item.
     * 
     * @param item
     *            Drools repository item
     * @return A rule
     * @throws RuleEngineRepositoryException
     */
    public Rule buildRule(final AssetItem item) throws RuleEngineRepositoryException {
        DroolsRuleImpl rule = RuleFactory.getInstance().createDroolsRule(
                item.getUUID(), item.getName(), item.getVersionNumber(), 
                item.getPackage().getUUID(), item.getPackageName());
        rule.setContent(item.getContent());
        rule.setBinaryContent(item.getBinaryContentAsBytes());
        rule.setFormat(item.getFormat());
        rule.setStatus((item.getState() == null ? "Draft" : item.getState().getName()));
        rule.setDescription(item.getDescription());
        rule.setCheckinComment(item.getCheckinComment());
        rule.setEffectiveDate(item.getDateEffective());
        rule.setExpiryDate(item.getDateExpired());
        rule.setCreatedDate(item.getCreatedDate());
        rule.setLastModifiedDate(item.getLastModified());
        rule.setArchived(item.isArchived());
        
        // Drools 4 does not use generics so we have to suppress warning
        @SuppressWarnings("unchecked")
        List<CategoryItem> categories = item.getCategories();
        setCategories(rule, categories);
        
        try {
            rule.setHistorical(item.isHistoricalVersion());
            if (item.isHistoricalVersion()) {
                rule.setVersionSnapshotUUID(item.getVersionSnapshotUUID());
            }
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Unable to set rule historical version", e);
        }

        return rule;
    }
    
    /**
     * Sets <code>categories</code> to a Drools <code>rule</code>.
     * 
     * @param rule Rule to add <code>categories</code> to
     * @param categories Categories to add to <code>rule</code>
     */
    private void setCategories(DroolsRuleImpl rule, List<CategoryItem> categories) {
        List<Category> categoryList = new ArrayList<Category>();
        List<String> categoryNameList = new ArrayList<String>();
        for(CategoryItem category : categories) {
            String name = category.getName();
            String path = category.getFullPath();
            Category cat = CategoryFactory.getInstance().createDroolsCategory(name, path);
            categoryList.add(cat);
            categoryNameList.add(name);
        }
        rule.setCategories(categoryList);
        rule.setCategoryNames(categoryNameList);
    }

    private void setCategories(DroolsRuleSetImpl ruleSet, List<CategoryItem> categories) {
        List<Category> categoryList = new ArrayList<Category>();
        //List<String> categoryNameList = new ArrayList<String>();
        for(CategoryItem category : categories) {
            String name = category.getName();
            String path = category.getFullPath();
            Category cat = CategoryFactory.getInstance().createDroolsCategory(name, path);
            categoryList.add(cat);
            //categoryNameList.add(name);
        }
        ruleSet.setCategories(categoryList);
        //rule.setCategoryNames(categoryNameList);
    }

    /**
     * Builds a rule from from history from an Drools repository item. Note: AssetItem.getFormat(),
     * AssetItem.getLastModified(), AssetItem.isArchived() throws exception when creating from history
     * 
     * @param item
     *            Drools repository history item
     * @return A history rule
     * @throws RuleEngineRepositoryException
     */
    public Rule buildHistoricalRule(final AssetItem item) throws RuleEngineRepositoryException {
        boolean isHistorical = false;

        try {
            isHistorical = item.isHistoricalVersion();
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Unable to set rule historical version", e);
        }

        String packageUUID = (isHistorical ? null : item.getPackage().getUUID());
        
        DroolsRuleImpl rule = RuleFactory.getInstance().createDroolsRule(
                item.getUUID(), item.getName(), item.getVersionNumber(),
                packageUUID, item.getPackageName());
        rule.setContent(item.getContent());
        rule.setBinaryContent(item.getBinaryContentAsBytes());
        // item.getFormat() throws exception when creating from history
        rule.setFormat(null);
        rule.setStatus((item.getState() == null ? "Draft" : item.getState().getName()));
        rule.setDescription(item.getDescription());
        rule.setCheckinComment(item.getCheckinComment());
        rule.setEffectiveDate(item.getDateEffective());
        rule.setExpiryDate(item.getDateExpired());
        rule.setCreatedDate(item.getCreatedDate());
        // item.getLastModified() throws exception when creating from history
        rule.setLastModifiedDate(null);
        // item.isArchived() throws exception when creating from history
        rule.setArchived(false);
        rule.setVersionSnapshotUUID(item.getVersionSnapshotUUID());
        
        // Drools 4 does not use generics so we have to suppress warning
        @SuppressWarnings("unchecked") 
        List<CategoryItem> categories = item.getCategories();
        setCategories(rule, categories);

        try {
            rule.setHistorical(item.isHistoricalVersion());
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Unable to set rule historical version", e);
        }

        return rule;
    }

    /*public RuleSet buildHistoricalRuleSet( AssetItem item ) throws RuleEngineRepositoryException { 
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet( item.getUUID(), item.getName() );
        //ruleSet.setContent( item.getContent() );
        //ruleSet.setBinaryContent( item.getBinaryContentAsBytes() ); 
        //item.getFormat() throws exception when creating from history 
        //ruleSet.setFormat( null ); 
        ruleSet.setVersionNumber( item.getVersionNumber() ); 
        ruleSet.setStatus( ( item.getState() == null ? "Draft" : item.getState().getName() ) ); 
        ruleSet.setDescription( item.getDescription() );
        ruleSet.setCheckinComment( item.getCheckinComment() ); 
        //ruleSet.setEffectiveDate( item.getDateEffective() );
        //ruleSet.setExpiryDate( item.getDateExpired() ); 
        ruleSet.setCreatedDate( item.getCreatedDate() ); 
        //item.getLastModified() throws exception when creating from history 
        ruleSet.setLastModifiedDate( null ); 
        //item.isArchived() throws exception when creating from history 
        ruleSet.setArchived( false );
        ruleSet.setVersionSnapshotUUID( item.getVersionSnapshotUUID() ); 
        try { 
            ruleSet.setHistorical( item.isHistoricalVersion() ); 
        } 
        catch( RepositoryException e ) { 
            throw new RuleEngineRepositoryException( "Unable to set ruleset historical version", e ); 
        } 
        return ruleSet; 
    }*/

    /**
     * Builds a rule set from an Drools repository item.
     * 
     * @param pkg
     *            Drools repository package item
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public RuleSet buildRuleSet(final PackageItem pkg) throws RuleEngineRepositoryException {
        DroolsRuleSetImpl ruleSet = RuleSetFactory.getInstance().createRuleSet( pkg.getUUID(), pkg.getName(), pkg.getVersionNumber() );
        ruleSet.setStatus((pkg.getState() == null ? "Draft" : pkg.getState().getName()));
        ruleSet.setDescription(pkg.getDescription());
        ruleSet.setFormat(pkg.getFormat());
        ruleSet.setCheckinComment(pkg.getCheckinComment());
        ruleSet.setCreatedDate(pkg.getCreatedDate());
        ruleSet.setLastModifiedDate(pkg.getLastModified());
        ruleSet.setArchived(pkg.isArchived());
        ruleSet.setSnapshot(pkg.isSnapshot());
        ruleSet.setSnapshotName(pkg.getSnapshotName());
        addRuleSetHeader(ruleSet, pkg.getHeader());

        ruleSet.setCompiledRuleSet(pkg.getCompiledPackageBytes());
        org.drools.rule.Package p = getPackage(pkg.getCompiledPackageBytes());
        ruleSet.setCompiledRuleSetObject(p);

        try {
            ruleSet.setHistorical(pkg.isHistoricalVersion());
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Unable to set rule set historical version", e);
        }

        try {
            // Tag name
        	Property propertyTag = pkg.getNode().getProperty(RuleEngineRepositoryDroolsImpl.KUALI_PACKAGE_TAG_PROPERTY_NAME);
            Value[] values = propertyTag.getValues();
            //String tagName = propertyTag.getValue().getString();
            // TODO: Fix this
            List<CategoryItem> categoryList = new ArrayList<CategoryItem>(values.length);
            for(Value value : values) {
            	String uuid = value.getString();
	            Node tagNode = pkg.getNode().getSession().getNodeByUUID(uuid);
	            CategoryItem tagItem = new CategoryItem(pkg.getRulesRepository(), tagNode);
	            categoryList.add(tagItem);
            }
            setCategories(ruleSet, categoryList);
        } catch (RepositoryException e) {
            if (e instanceof PathNotFoundException) {
            	logger.info("Property " + RuleEngineRepositoryDroolsImpl.KUALI_PACKAGE_TAG_PROPERTY_NAME + " not set");
            }
            else {
            	throw new RuleEngineRepositoryException("Unable to set rule set tag", e);
            }
        }

        try {
            // Effective date
        	Property propertyEffDate = pkg.getNode().getProperty(RuleEngineRepositoryDroolsImpl.KUALI_PACKAGE_EFFECTIVE_DATE_PROPERTY_NAME);
            Calendar EffDate = propertyEffDate.getValue().getDate();
            ruleSet.setEffectiveDate(EffDate);
        } catch (RepositoryException e) {
            if (e instanceof PathNotFoundException) {
            	logger.info("Property " + RuleEngineRepositoryDroolsImpl.KUALI_PACKAGE_EFFECTIVE_DATE_PROPERTY_NAME + " not set");
            }
            else {
            	throw new RuleEngineRepositoryException("Unable to set rule set tag", e);
            }
        }
        
        try {
            // Expiry date
        	Property propertyExpDate = pkg.getNode().getProperty(RuleEngineRepositoryDroolsImpl.KUALI_PACKAGE_EXPIRY_DATE_PROPERTY_NAME);
            Calendar ExpDate = propertyExpDate.getValue().getDate();
            ruleSet.setExpiryDate(ExpDate);
        } catch (RepositoryException e) {
            if (e instanceof PathNotFoundException) {
            	logger.info("Property " + RuleEngineRepositoryDroolsImpl.KUALI_PACKAGE_EXPIRY_DATE_PROPERTY_NAME + " not set");
            }
            else {
            	throw new RuleEngineRepositoryException("Unable to set rule set tag", e);
            }
        }
        
        // Drools 4 does not use generics so we have to suppress warning
        for (@SuppressWarnings("unchecked") Iterator<AssetItem> it = pkg.getAssets(); it.hasNext();) {
            AssetItem item = it.next();
            Rule rule = buildRule(item);
            ruleSet.addRule(rule);
        }

        return ruleSet;
    }
    
    /**
     * Adds a header to the rule set if it does not exists.
     * 
     * @param ruleSet Rule set to add <code>header</code> to
     * @param header Header to add to <code>ruleSet</code>
     */
    public void addRuleSetHeader(RuleSet ruleSet, String header ) {
        for(String headerLine : header.split(";")) {
            headerLine = headerLine.trim();
            if ( !headerLine.isEmpty() && !ruleSet.getHeaderList().contains(headerLine+";")) {
                ruleSet.addHeader(headerLine);
            }
        }
    }
    
    /**
     * Gets a Drools <code>org.drools.rule.Package</code> from a byte array.
     * 
     * @param binPackage
     *            A byte array
     * @return Drools Package
     * @throws Exception
     */
    public org.drools.rule.Package getPackage(final byte[] binPackage) throws RuleEngineRepositoryException {
        if (binPackage == null) {
            return null;
        }

        /*ByteArrayInputStream bin = null;
        ObjectInput in = null;

        try {
            bin = new ByteArrayInputStream(binPackage);
            in = new DroolsObjectInputStream(bin); 
            return (org.drools.rule.Package) in.readObject();
        } catch (IOException e) {
            throw new RuleEngineRepositoryException(e);
        } catch (ClassNotFoundException e) {
            throw new RuleEngineRepositoryException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (bin != null) {
                    bin.close();
                }
            } catch (IOException e) {
                throw new RuleEngineRepositoryException("Loading rule set failed", e);
            }
        }*/
        try {
        	return serialize(binPackage);
        } catch (IOException e) {
            throw new RuleEngineRepositoryException(e);
        } catch (ClassNotFoundException e) {
            throw new RuleEngineRepositoryException(e);
        }
    }

    /**
     * Generates and returns a list of compilation errors.
     * 
     * @param errors Package builder/compiler errors
     * @param item Item that contains the error 
     * @return List of compiler errors
     */
    public List<CompilerResult> generateCompilerResults(final PackageBuilderErrors errors, final VersionableItem item) {
        List<CompilerResult> result = new ArrayList<CompilerResult>();
        DroolsError[] dr = errors.getErrors();
        for (int i = 0; i < dr.length; i++) {
            String uuid = item.getUUID();
            String name = item.getName();
            String format = item.getFormat();
            String message = dr[i].getMessage();
            CompilerResult br = new CompilerResult(uuid, name, format, message);
            result.add(br);
        }
        return result;
    }

    /**
     * Gets the binary Drools <code>org.drools.rule.Package</code> as an
     * {@link InputStream}.
     * 
     * @param pkg A package
     * @return A {@link ByteArrayInputStream}
     * @throws IOException
     */
    public InputStream getBinaryPackage(final org.drools.rule.Package pkg) throws IOException {
        /*ObjectOutputStream oos = null;
        // TODO: Drools 5.0 - ObjectOutput is used instead in Drools 5.0.0
        //ObjectOutput oo = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            // TODO: Drools 5.0 - DroolsObjectOutputStream is used instead in Drools 5.0.0
            //oo = new DroolsObjectOutputStream(baos);
            oos.writeObject(pkg);
            return new ByteArrayInputStream(baos.toByteArray());
        } finally {
            if (oos != null) {
                oos.flush();
            }
            if (oos != null) {
                oos.close();
            }
        }*/
        try {
        	return new ByteArrayInputStream(deserialize(pkg));
        } catch (IOException e) {
            throw new RuleEngineRepositoryException(e);
        }
    }

    /**
     * Compiles a {@link PackageItem} and returns a {@link CompilerResultList} 
     * if there are any compilation errors. 
     * 
     * @param pkg
     * @return Compilation errors, if any
     * @throws IOException
     * @throws DroolsParserException
     */
    public CompilerResultList compile(final PackageItem pkg) throws IOException, DroolsParserException {
        PackageBuilder builder = createPackageBuilder();
        builder.addPackage(new PackageDescr(pkg.getName()));
        String drl = pkg.getHeader();
        builder.addPackageFromDrl(new StringReader(drl));

        List<CompilerResult> errors = new ArrayList<CompilerResult>();

        if (builder.hasErrors()) {
            List<CompilerResult> l = generateCompilerResults(builder.getErrors(), pkg);
            errors.addAll(l);
        }

        // Drools 4 does not use generics so we have to suppress warning
        for (@SuppressWarnings("unchecked") Iterator<AssetItem> it = pkg.getAssets(); it.hasNext();) {
            AssetItem item = it.next();
            builder.addPackageFromDrl(new StringReader(item.getContent()));
            if (builder.hasErrors()) {
                List<CompilerResult> l = generateCompilerResults(builder.getErrors(), item);
                errors.addAll(l);
            }
        }
        CompilerResultList result = new CompilerResultList(builder.getPackage());
        result.addAll(errors);
        return result;
    }

    /**
     * Creates a Drools {@link PackageBuilder}.
     * 
     * @return A {@link PackageBuilder}
     */
    public PackageBuilder createPackageBuilder() {
        ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
        ChainedProperties chainedProperties = new ChainedProperties(DroolsUtil.class.getClassLoader(), 
                "packagebuilder.conf", false); // false means it ignores any default values
        Properties properties = new Properties();
        // The default compiler. This is nominally JANINO 
        // but can be overridden by setting drools.dialect.java.compiler to
        // ECLIPSE
        //properties.setProperty("drools.dialect.java.compiler", chainedProperties.getProperty("drools.dialect.java.compiler", "JANINO"));
        properties.setProperty("drools.dialect.java.compiler", chainedProperties.getProperty("drools.dialect.java.compiler", "ECLIPSE"));
        PackageBuilderConfiguration pkgConf = new PackageBuilderConfiguration(properties);
        pkgConf.setClassLoader(parentClassLoader);
        PackageBuilder builder = new PackageBuilder(pkgConf);
        return builder;
    }

    /**
     * Gets the Drools DRL source code of a Drools 
     * <code>org.drools.repository.PackageItem</code>.
     * 
     * @param pkg A Drools Package
     * @return A Drools DRL source code
     */
    public String getDRL(final PackageItem pkg) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ");
        sb.append(pkg.getName());
        sb.append("\n");
        sb.append(pkg.getHeader());
        sb.append("\n\n");

        // Drools 4 does not use generics so we have to suppress warning
        for (@SuppressWarnings("unchecked") Iterator<AssetItem> it = pkg.getAssets(); it.hasNext();) {
            sb.append(it.next().getContent());
            sb.append("\n");
        }

        return sb.toString();
    }

	/**
	 * Serializes a byte array into a <code>org.drools.rule.Package</code>.
	 * 
	 * @param bytes Bytes to serialize
	 * @return A Drools Package
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Package serialize(final byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInput in = null;
		ByteArrayInputStream bis = null;
		Object obj = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			in = new DroolsObjectInputStream(bis);
	        obj = in.readObject();
		} finally {
	        if (in!= null) in.close();
	        if (bis!= null) bis.close();
		}
        return (Package) obj;
    }

	/**
	 * Deserializes an object.
	 * 
	 * @param obj Object deserialize
	 * @return A byte array
	 * @throws IOException
	 */
    public static byte[] deserialize(final Object obj) throws IOException {
    	ByteArrayOutputStream bos = null;
    	ObjectOutput out = null;
    	try {
    		// Serialize to a byte array
	        bos = new ByteArrayOutputStream();
            // TODO: Drools 5.0 - DroolsObjectOutputStream is used instead in Drools 5.0.0
            //oo = new DroolsObjectOutputStream(baos);
	        out = new ObjectOutputStream(bos);
	        out.writeObject(obj);
    	} finally {
    		if (bos != null) bos.close();
    		if (out != null) out.close();
    	}
        // Get the bytes of the serialized object
        final byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
