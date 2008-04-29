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
package org.kuali.student.brms.repository.drools.rule;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.brms.repository.rule.AbstractItem;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleSet;

/**
 * This is the implementation of a <code>RuleSet</code>. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class DroolsRuleSetImpl 
    extends AbstractItem 
    implements java.io.Serializable, RuleSet {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** List of rules in this rule set */
    private List<Rule> rules = new ArrayList<Rule>();
    /** Compiled rule set bye array */
    private byte[] compiledRuleSet;
    /** Compiled rule set object */
    private Object compiledRuleSetObject;
    /** List of header items */
    private List<String> header = new ArrayList<String>();
    /** true if this rule set is a snapshot, otherwise false */
    private boolean snapshot = false;
    /** snapshot name */
    private String snapshotName;

    /**
     * Constructs a new rule.
     * 
     * @param name Rule name
     */
    DroolsRuleSetImpl(final String name) {
        super(name);
    }

    /**
     * Constructs a new rule.
     * 
     * @param uuid Rule UUID - This is created by the repository
     * @param name Rule name
     * @param versionNumber Rule version number
     */
    DroolsRuleSetImpl(final String uuid, final String name, final long versionNumber) {
        super(uuid, name, versionNumber);
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#addRule(org.kuali.student.brms.repository.rule.Rule)
     */
    public void addRule(final Rule rule) {
        this.rules.add(rule);
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getRules()
     */
    public List<Rule> getRules() {
        return this.rules;
    }

    /**
     * Sets a list of <code>org.kuali.student.brms.repository.rule.Rule</code> to this rule set.
     *  
     * @param rules List of rules
     */
    public void setRules(final List<Rule> rules) {
        this.rules = rules;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getHeader(java.lang.String)
     */
    private String getHeader( String header ) {
        return (header.endsWith(";") ? header : header.trim() + ";");
    }
    
    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#addHeader(java.lang.String)
     */
    public void addHeader(final String header) {
        this.header.add(getHeader(header));
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getHeader()
     */
    public String getHeader() {
        StringBuilder sb = new StringBuilder();
        for( int i=0; i<this.header.size(); i++) {
            sb.append( getHeader(this.header.get(i)) );
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Sets a list of rule set headers.
     * 
     * @param header Rule set header
     */
    public void setHeaderList(final List<String> header) {
        this.header = header;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getHeaderList()
     */
    public List<String> getHeaderList() {
        List<String> list = new ArrayList<String>();
        for(int i=0; i<this.header.size(); i++) {
            list.add(getHeader(this.header.get(i)));
        }
        return list;
    }

    /**
     * Returns a copy of the compiled rule set binary;
     * 
     * @see org.kuali.student.brms.repository.rule.RuleSet#getCompiledRuleSet()
     */
    public byte[] getCompiledRuleSet() {
        int size = this.compiledRuleSet.length;
        byte[] temp = new byte[size];
        System.arraycopy(this.compiledRuleSet, 0, temp, 0, size);
        return temp;
    }

    /**
     * Sets a compiled rule set byte array
     * 
     * @param compiledRuleSet Compiled rule set byte array
     */
    public void setCompiledRuleSet(final byte[] compiledRuleSet) {
        this.compiledRuleSet = compiledRuleSet;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getCompiledRuleSetObject()
     */
    public Object getCompiledRuleSetObject() {
        return this.compiledRuleSetObject;
    }

    /**
     * Sets a compiled rule set object. E.g. A Drools a <code>org.drools.rule.Package</code> 
     * 
     * @param compiledRuleSetObject A compiled rule set object
     */
    public void setCompiledRuleSetObject(final Object compiledRuleSetObject) {
        this.compiledRuleSetObject = compiledRuleSetObject;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#isSnapshot()
     */
    public boolean isSnapshot() {
        return this.snapshot;
    }

    /**
     * Sets whether this rule set is a snapshot.
     * If <code>snapshot</code> is set to true then this rule set is a snapshot,
     * otherwise this rule set is not a snapshot.
     * 
     * @param snapshot True if this rule set is snapshot, otherwise false 
     */
    public void setSnapshot(final boolean snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getContent()
     */
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ");
        sb.append(super.getName());
        sb.append("\n");
        for(int i=0; i<this.header.size(); i++) {
            sb.append(this.header.get(i));
            sb.append("\n");
        }

        for(int i=0; i<this.rules.size(); i++) {
            sb.append(this.rules.get(i).getContent());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getSnapshotName()
     */
    public String getSnapshotName() {
        return this.snapshotName;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#setSnapshotName(java.lang.String)
     */
    public void setSnapshotName(final String snapshotName) {
        this.snapshotName = snapshotName;
    }

    /**
     * Overrides hashCode
     * 
     * @see org.kuali.student.brms.repository.rule.AbstractItem#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( getName() == null ? 0 : getName().hashCode() );
        result = prime * result + ( getUUID() == null ? 0 : getUUID().hashCode() );
        result = prime * result + ( header == null ? 0 : header.hashCode() );
        result = prime * result + ( rules == null ? 0 : rules.hashCode() );
        return result;
    }

    /**
     * Overrides equals
     * 
     * @see org.kuali.student.brms.repository.rule.AbstractItem#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( this.getClass() != obj.getClass() ) {
            return false;
        }
        if ( this.getName() == null ) {
            return false;
        }
        
        final DroolsRuleSetImpl ruleSet = (DroolsRuleSetImpl) obj;
        
        if ( ruleSet.getName() == null ) {
            return false;
        }
        if ( header == null && ruleSet.header != null  ) {
            return false;
        } else if ( !header.equals( ruleSet.header ) ) {
            return false;
        }
        
        if ( rules == null && ruleSet.rules != null ) {
            return false;
        } else if ( !rules.equals( ruleSet.rules ) ) {
            return false;
        }
        
        if ( !getName().equals( ruleSet.getName() ) ) {
            return false;
        }
        if ( ruleSet.getUUID() != null && !ruleSet.getUUID().equals( this.getUUID() ) ) {
            return false;
        }
        if ( ruleSet.getVersionNumber() != this.getVersionNumber() ) {
            return false;
        }
        
        return true;
    }

}
