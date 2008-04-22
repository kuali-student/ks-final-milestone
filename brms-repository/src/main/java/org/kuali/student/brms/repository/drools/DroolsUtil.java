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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.jcr.RepositoryException;

import org.drools.common.DroolsObjectInputStream;
import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.compiler.PackageBuilderErrors;
import org.drools.lang.descr.PackageDescr;
import org.drools.repository.AssetItem;
import org.drools.repository.PackageItem;
import org.drools.util.ChainedProperties;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.BuilderResult;
import org.kuali.student.brms.repository.rule.BuilderResultList;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleImpl;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.repository.rule.RuleSetImpl;

public class DroolsUtil {
    /**
     * Build a rule from an Drools repository item.
     * 
     * @param item
     *            Drools repository item
     * @return A rule
     * @throws RuleEngineRepositoryException
     */
    public static RuleImpl buildRule(AssetItem item) throws RuleEngineRepositoryException {
        RuleImpl rule = new RuleImpl(item.getUUID(), item.getName());
        rule.setContent(item.getContent());
        rule.setBinaryContent(item.getBinaryContentAsBytes());
        rule.setFormat(item.getFormat());
        rule.setVersionNumber(item.getVersionNumber());
        rule.setStatus((item.getState() == null ? "Draft" : item.getState().getName()));
        rule.setDescription(item.getDescription());
        rule.setCheckinComment(item.getCheckinComment());
        rule.setEffectiveDate(item.getDateEffective());
        rule.setExpiryDate(item.getDateExpired());
        rule.setCreatedDate(item.getCreatedDate());
        rule.setLastModifiedDate(item.getLastModified());
        rule.setArchived(item.isArchived());

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
     * Build a rule from from history from an Drools repository item. Note: AssetItem.getFormat(),
     * AssetItem.getLastModified(), AssetItem.isArchived() throws exception when creating from history
     * 
     * @param item
     *            Drools repository history item
     * @return A history rule
     * @throws RuleEngineRepositoryException
     */
    public static Rule buildHistoricalRule(AssetItem item) throws RuleEngineRepositoryException {
        RuleImpl rule = new RuleImpl(item.getUUID(), item.getName());
        rule.setContent(item.getContent());
        rule.setBinaryContent(item.getBinaryContentAsBytes());
        // item.getFormat() throws exception when creating from history
        rule.setFormat(null);
        rule.setVersionNumber(item.getVersionNumber());
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

        try {
            rule.setHistorical(item.isHistoricalVersion());
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Unable to set rule historical version", e);
        }

        return rule;
    }

    /*
     * public static RuleSet buildHistoricalRuleSet( AssetItem item ) throws RuleEngineRepositoryException { RuleSetImpl ruleSet =
     * new RuleSetImpl( item.getUUID(), item.getName() ); //ruleSet.setContent( item.getContent() );
     * //ruleSet.setBinaryContent( item.getBinaryContentAsBytes() ); // item.getFormat() throws exception when creating from
     * history //ruleSet.setFormat( null ); ruleSet.setVersionNumber( item.getVersionNumber() ); ruleSet.setStatus( (
     * item.getState() == null ? "Draft" : item.getState().getName() ) ); ruleSet.setDescription( item.getDescription() );
     * ruleSet.setCheckinComment( item.getCheckinComment() ); //ruleSet.setEffectiveDate( item.getDateEffective() );
     * //ruleSet.setExpiryDate( item.getDateExpired() ); ruleSet.setCreatedDate( item.getCreatedDate() ); //
     * item.getLastModified() throws exception when creating from history ruleSet.setLastModifiedDate( null ); //
     * item.isArchived() throws exception when creating from history ruleSet.setArchived( false );
     * ruleSet.setVersionSnapshotUUID( item.getVersionSnapshotUUID() ); try { ruleSet.setHistorical(
     * item.isHistoricalVersion() ); } catch( RepositoryException e ) { throw new RuleEngineRepositoryException( "Unable to set
     * ruleset historical version", e ); } return ruleSet; }
     */

    /**
     * Build a rule set from an Drools repository item.
     * 
     * @param item
     *            Drools repository package item
     * @return A rule set
     * @throws RuleEngineRepositoryException
     */
    public static RuleSet buildRuleSet(PackageItem pkg) throws RuleEngineRepositoryException {
        RuleSetImpl ruleSet = new RuleSetImpl(pkg.getUUID(), pkg.getName());
        ruleSet.setVersionNumber(pkg.getVersionNumber());
        ruleSet.setStatus((pkg.getState() == null ? "Draft" : pkg.getState().getName()));
        ruleSet.setDescription(pkg.getDescription());
        ruleSet.setCheckinComment(pkg.getCheckinComment());
        ruleSet.setCreatedDate(pkg.getCreatedDate());
        ruleSet.setLastModifiedDate(pkg.getLastModified());
        ruleSet.setArchived(pkg.isArchived());
        ruleSet.setSnapshot(pkg.isSnapshot());
        String[] headerLine = pkg.getHeader().split(" ");
        for(int i=0; i<headerLine.length; i++) {
            ruleSet.addHeader(headerLine[i]);
        }

        ruleSet.setCompiledRuleSet(pkg.getCompiledPackageBytes());
        org.drools.rule.Package p = getPackage(pkg.getCompiledPackageBytes());
        ruleSet.setCompiledRuleSetObject(p);

        try {
            ruleSet.setHistorical(pkg.isHistoricalVersion());
        } catch (RepositoryException e) {
            throw new RuleEngineRepositoryException("Unable to set rule set historical version", e);
        }

        List<Rule> list = new ArrayList<Rule>();
        for (Iterator<AssetItem> it = pkg.getAssets(); it.hasNext();) {
            AssetItem item = it.next();
            Rule rule = buildRule(item);
            list.add(rule);
        }
        ruleSet.setRules(list);

        return ruleSet;
    }
    
    /**
     * Gets a Drools <code>org.drools.rule.Package</code> from a byte array.
     * 
     * @param binPackage
     *            A byte array
     * @return Drools Package
     * @throws Exception
     */
    public static org.drools.rule.Package getPackage(byte[] binPackage) throws RuleEngineRepositoryException {
        if (binPackage == null) {
            return null;
        }

        ByteArrayInputStream bin = null;
        ObjectInputStream in = null;

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
                if (in != null)
                    in.close();
                if (bin != null)
                    bin.close();
            } catch (IOException e) {
                throw new RuleEngineRepositoryException("Loading rule set failed", e);
            }
        }
    }

    public static List<BuilderResult> generateBuilderResults(PackageBuilderErrors errors, org.drools.repository.VersionableItem item) {
        List<BuilderResult> result = new ArrayList<BuilderResult>();
        DroolsError[] dr = errors.getErrors();
        for (int i = 0; i < dr.length; i++) {
            String uuid = item.getUUID();
            String name = item.getName();
            String format = item.getFormat();
            String message = dr[i].getMessage();
            BuilderResult br = new BuilderResult(uuid, name, format, message);
            result.add(br);
        }
        return result;
    }

    public static InputStream getBinaryPackage(org.drools.rule.Package pkg) throws IOException {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(pkg);
            return new ByteArrayInputStream(baos.toByteArray());
        } finally {
            if (oos != null)
                oos.flush();
            if (oos != null)
                oos.close();
        }
    }

    public static BuilderResultList compile(PackageItem pkg) throws IOException, DroolsParserException {
        PackageBuilder builder = createPackageBuilder();
        builder.addPackage(new PackageDescr(pkg.getName()));
        String drl = pkg.getHeader();
        builder.addPackageFromDrl(new StringReader(drl));

        List<BuilderResult> errors = new ArrayList<BuilderResult>();

        if (builder.hasErrors()) {
            List<BuilderResult> l = generateBuilderResults(builder.getErrors(), pkg);
            errors.addAll(l);
        }

        for (Iterator<AssetItem> it = pkg.getAssets(); it.hasNext();) {
            AssetItem item = it.next();
            builder.addPackageFromDrl(new StringReader(item.getContent()));
            if (builder.hasErrors()) {
                List<BuilderResult> l = generateBuilderResults(builder.getErrors(), item);
                errors.addAll(l);
            }
        }
        BuilderResultList result = new BuilderResultList(builder.getPackage());
        result.addAll(errors);
        return result;
    }

    public static PackageBuilder createPackageBuilder() {
        ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
        ChainedProperties chainedProperties = new ChainedProperties(RuleEngineRepositoryDroolsImpl.class.getClassLoader(), // pass
                                                                                                                        // this
                                                                                                                        // as
                                                                                                                        // it
                                                                                                                        // searches
                                                                                                                        // currentThread
                                                                                                                        // anyway
                "packagebuilder.conf", false); // false means it ignores any default values
        // the default compiler. This is nominally JANINO but can be overridden by setting drools.dialect.java.compiler to
        // ECLIPSE
        Properties properties = new Properties();
        properties.setProperty("drools.dialect.java.compiler", chainedProperties.getProperty("drools.dialect.java.compiler", "JANINO"));
        PackageBuilderConfiguration pkgConf = new PackageBuilderConfiguration(properties);
        pkgConf.setClassLoader(parentClassLoader);
        PackageBuilder builder = new PackageBuilder(pkgConf);
        return builder;
    }

    public static String getDRL(PackageItem pkg) {
        StringBuilder sb = new StringBuilder();
        sb.append("package ");
        sb.append(pkg.getName());
        sb.append("\n");
        sb.append(pkg.getHeader());
        sb.append("\n\n");

        for (Iterator<AssetItem> it = pkg.getAssets(); it.hasNext();) {
            sb.append(it.next().getContent());
            sb.append("\n");
        }

        return sb.toString();
    }

}
