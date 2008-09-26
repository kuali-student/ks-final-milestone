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
package org.kuali.student.rules.repository.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.rules.repository.service.jaxws.adapter.RuleMapAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class RuleSetDTO extends AbstractItemDTO implements java.io.Serializable {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** List of rules in this rule set */
    @XmlElement
    @XmlJavaTypeAdapter(RuleMapAdapter.class)
    private Map<String,RuleDTO> rules = new LinkedHashMap<String,RuleDTO>();
    /** Compiled rule set bye array */
    //@XmlMimeType("application/octet-stream")
    private byte[] compiledRuleSet;
    /** List of header items */
    @XmlElement
    private List<String> header = new ArrayList<String>();
    /** true if this rule set is a snapshot, otherwise false */
    @XmlElement
    private boolean snapshot = false;
    /** snapshot name */
    @XmlElement
    private String snapshotName;
    /** Rule set source */
    private String sourceContent;

    public RuleSetDTO() {}

    /**
     * Constructs a new rule set.
     * 
     * @param name Rule name
     */
    /*public RuleSetDTO(final String name, final String description) {
        super(name, description, DroolsConstants.FORMAT_DRL);
    }*/

    public RuleSetDTO(final String name, final String description, final String format) {
        super(name, description, format);
    }

    /**
     * <p>Constructs a new rule set.<p/>
     * <p>Internal use only. This is set by the repository when persisting a rule set.</p>
     * 
     * @param uuid Rule UUID - This is created by the repository
     * @param name Rule name
     * @param versionNumber Rule version number
     */
    public RuleSetDTO(final String uuid, final String name, final long versionNumber) {
        super(uuid, name, versionNumber);
    }

    public void addRule(final RuleDTO rule) {
        if ( rule == null ) {
            return;
        }
        this.rules.put(rule.getName(), rule);
    }

    public RuleDTO removeRule(final String ruleName) {
        return this.rules.remove( ruleName );
    }

    public void setRules(Map<String,RuleDTO> rules) {
    	this.rules = rules;
    }
    
    public Map<String,RuleDTO> getRules() {
    	return this.rules;
    }
    
    public void addHeader(final String header) {
        this.header.add(header);
    }

    public boolean removeHeader(final String header) {
        return this.header.remove(header);
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<String> getHeader() {
        return this.header;
    }

    /**
     * Returns a copy of the compiled rule set binary;
     */
    public byte[] getCompiledRuleSet() {
        return this.compiledRuleSet;
    }

    /**
     * Sets a compiled rule set byte array. 
     * This method makes a copy of the compiled rules set.
     * 
     * @param compiledRuleSet Compiled rule set byte array
     */
    public void setCompiledRuleSet(final byte[] compiledRuleSet) {
        this.compiledRuleSet = compiledRuleSet;
    }

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

    public void setContent(final String sourceContent) {
        this.sourceContent = sourceContent;
    }

    public String getContent() {
        return this.sourceContent;
    }

    public String getSnapshotName() {
        return this.snapshotName;
    }

    public void setSnapshotName(final String snapshotName) {
        this.snapshotName = snapshotName;
    }
    
    public String toString() {
        return "RuleSetDTO UUID=" + getUUID() + ", name=" + getName() + ", versionNumber=" + getVersionNumber();
    }

    /**
     * Builds the rule source from this rule sets rules and headers.
     * 
     * @return Rule source code
     */
    public String buildContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("package ");
        sb.append(super.getName());
        sb.append("\n");
        for(String header : this.header) {
            sb.append(header);
            sb.append("\n");
        }

        for(RuleDTO rule : this.rules.values()) {
            sb.append(rule.getContent());
            sb.append("\n");
        }

        return sb.toString();
    }

}
