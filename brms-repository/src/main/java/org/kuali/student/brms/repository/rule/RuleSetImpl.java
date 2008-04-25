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
package org.kuali.student.brms.repository.rule;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of a <code>RuleSet</code>. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleSetImpl 
    extends ItemImpl 
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
    RuleSetImpl(String name) {
        super(name);
    }

    /**
     * Constructs a new rule.
     * 
     * @param uuid Rule UUID - This is created by the repository
     * @param name Rule name
     */
    RuleSetImpl(String uuid, String name) {
        super(uuid, name);
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#addRule(org.kuali.student.brms.repository.rule.Rule)
     */
    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getRules()
     */
    public List<Rule> getRules() {
        return this.rules;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#setRules(java.util.List)
     */
    public void setRules(List<Rule> rules) {
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
    public void addHeader(String header) {
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
     * @see org.kuali.student.brms.repository.rule.RuleSet#setHeaderList(java.util.List)
     */
    public void setHeaderList(List<String> header) {
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
     * @see org.kuali.student.brms.repository.rule.RuleSet#setCompiledRuleSet(byte[])
     */
    public void setCompiledRuleSet(byte[] compiledRuleSet) {
        this.compiledRuleSet = compiledRuleSet;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#getCompiledRuleSetObject()
     */
    public Object getCompiledRuleSetObject() {
        return this.compiledRuleSetObject;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#setCompiledRuleSetObject(java.lang.Object)
     */
    public void setCompiledRuleSetObject(Object compiledRuleSetObject) {
        this.compiledRuleSetObject = compiledRuleSetObject;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#isSnapshot()
     */
    public boolean isSnapshot() {
        return this.snapshot;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.RuleSet#setSnapshot(boolean)
     */
    public void setSnapshot(boolean snapshot) {
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
    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

}
