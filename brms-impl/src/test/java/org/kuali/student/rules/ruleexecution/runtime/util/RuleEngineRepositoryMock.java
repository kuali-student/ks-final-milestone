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
package org.kuali.student.rules.ruleexecution.util;

import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.repository.drools.rule.DroolsRuleSetImpl;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.exceptions.CategoryExistsException;
import org.kuali.student.rules.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.rules.repository.exceptions.RuleExistsException;
import org.kuali.student.rules.repository.exceptions.RuleSetExistsException;
import org.kuali.student.rules.repository.rule.CompilerResultList;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;

public class RuleEngineRepositoryMock implements RuleEngineRepository {

	private Package pkg;
	private DroolsRuleSetImpl ruleSet;
    
    public RuleEngineRepositoryMock() {
    	this.ruleSet = (DroolsRuleSetImpl) RuleSetFactory.getInstance().createRuleSet("RuleSet1", "A rule set", "DRL");
        this.pkg = buildPackage(getSimplePackage());
        this.ruleSet.setCompiledRuleSetObject(this.pkg);
    }

    public RuleEngineRepositoryMock(Reader source) {
        this.pkg = buildPackage(source);
    	this.ruleSet.setCompiledRuleSetObject(this.pkg);
    }

    public void setSource(Reader source) {
        this.pkg = buildPackage(source);
    	this.ruleSet.setCompiledRuleSetObject(this.pkg);
    }

    /**
     * Gets a simple rule. 
     * First rule determines whether the minutes of the hour is even.
     * Rule takes <code>java.util.Calendar</code> as fact (package import).
     *  
     * @return a Drools' rule
     */
    private String getSimpleRule1() {
        return 
            "rule \"HelloDroolsEven\"" +
            "     when" +
            "          now: Calendar()" +
            "          eval ( ( now.get(Calendar.MINUTE) % 2 == 0 ) )" +
            "     then" +
            "          insert( \"Minute is even: \" + now.get(Calendar.MINUTE) );" +
            "end";
    }
    
    /**
     * Gets a simple rule. 
     * Rule determines whether the minutes of the hour is odd.
     * Rule takes <code>java.util.Calendar</code> as fact (package import).
     *  
     * @return a Drools' rule
     */
    private String getSimpleRule2() {
        return 
            "rule \"HelloDroolsOdd\"" +
            "     when" +
            "          now: Calendar()" +
            "          eval ( ( now.get(Calendar.MINUTE) % 2 == 1 ) )" +
            "     then" +
            "          insert( \"Minute is odd: \" + now.get(Calendar.MINUTE)  );" +
            "end";
    }

    /**
     * Return a Drools DRL file which includes 
     * <code>getSimpleRule1</code> and <code>getSimpleRule2</code>.
     * 
     * @param packageName Drools package name (ruleset)
     * @return Drools DRL
     */
    private Reader getSimplePackage() {
        return new StringReader(
            "package testpackage" +
            "\n" +
            "import java.util.Calendar; " +
            "\n\n" +
            getSimpleRule1() +
            "\n" +
            getSimpleRule2() +
            "\n");
    }

    /**
     * Build a simple Drools package
     * 
     * @return A drools package
     * @throws Exception Any errors building package
     */
    private Package buildPackage(Reader source) {
        try {
            PackageBuilder builder = new PackageBuilder();
            builder.addPackageFromDrl( source );
            return builder.getPackage();
        } catch( Exception e ) {
            throw new RuleEngineRepositoryException( "Building rule set failed", e );
        }
    }
    
    @Override
    public void archiveRule(String uuid, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void archiveRuleSet(String uuid, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void changeRuleSetStatus(String uuid, String newState) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void changeRuleStatus(String uuid, String newState) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public long checkinRule(String uuid, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public long checkinRuleSet(String uuid, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public CompilerResultList compileRuleSet(String ruleSetUUID) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public String compileRuleSetSource(String ruleSetUUID) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public Object compileSource(Reader source) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public Boolean createCategory(String path, String name, String description) throws CategoryExistsException {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public RuleSet createRuleSet(RuleSet ruleSet) throws RuleSetExistsException, RuleExistsException {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public RuleSet createRuleSetSnapshot(String ruleSetName, String snapshotName, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public RuleSet replaceRuleSetSnapshot(String ruleSetName, String snapshotName, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void rebuildRuleSetSnapshot(String ruleSetName, String snapshotName) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    public void removeRuleSetSnapshot(final String ruleSetName, final String snapshotName) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public String createStatus(String name) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
	public void removeStatus(String name) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
	}

	@Override
	public boolean containsStatus(String status) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
	}

    @Override
    public byte[] exportRulesRepositoryAsXml() {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public ByteArrayOutputStream exportRulesRepositoryAsZip(String filename) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void importRulesRepositoryAsXml(byte[] byteArray) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public List<Rule> loadArchivedRules() {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public List<RuleSet> loadArchivedRuleSets() {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public List<String> loadChildCategories(String categoryPath) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public Rule loadRule(String uuid) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public List<Rule> loadRuleHistory(String uuid) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public List<RuleSet> loadRuleSetHistory( String uuid ) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }
    
    @Override
    public RuleSet loadRuleSet(String uuid) {
        //throw new RuleEngineRepositoryException("Method Not Implemented");
    	return this.ruleSet;
    }

    @Override
    public RuleSet loadRuleSetSnapshot(String ruleSetName, String snapshotName) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public List<String> loadStates() {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void rebuildAllSnapshots() {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void removeCategory(String categoryPath) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void removeRule(String uuid) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void removeRuleSet(String uuid) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public String renameRule(String uuid, String newName) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public String renameRuleSet(String uuid, String newName) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void restoreVersion(String versionUUID, String assetUUID, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void unArchiveRule(String uuid, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public void unArchiveRuleSet(String uuid, String comment) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public Rule updateRule(Rule rule) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public RuleSet updateRuleSet(RuleSet ruleSet) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }
    
    @Override
    public RuleSet loadRuleSetByName(String ruleSetName) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }
    
    @Override
    public boolean containsRuleSetByName(final String ruleSetName) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

    @Override
    public boolean containsRuleSet(final String ruleSetUUID) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

	@Override
	public List<RuleSet> loadRuleSetsByCategory(String category) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
	}

	@Override
    public List<RuleSet> loadRuleSetSnapshotsByCategory(String category) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
    }

	@Override
	public RuleSet loadRuleSetByRuleCategory(String category) {
        throw new RuleEngineRepositoryException("Method Not Implemented");
	}
}
