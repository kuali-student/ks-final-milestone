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

package org.kuali.student.brms.repository.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.brms.repository.service.jaxws.adapter.RuleMapAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class RuleSetInfo extends AbstractItemInfo implements java.io.Serializable {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

    /** List of rules in this rule set */
    @XmlElement
    @XmlJavaTypeAdapter(RuleMapAdapter.class)
    private Map<String,RuleInfo> rules = new LinkedHashMap<String,RuleInfo>();
    /** Compiled rule set bye array */
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
    /** Tag name */
    /** List of categories */
    @XmlElement
    private List<CategoryInfo> categoryList = new ArrayList<CategoryInfo>();
    /** Date rule set becomes effective */
    @XmlElement
    private Calendar effectiveDate;
    /** Date rule set expires */
    @XmlElement
    private Calendar expiryDate;
    /** Rule set source */
    @XmlElement
    private String sourceContent;

    /**
     * Constructor
     */
    public RuleSetInfo() {}

    /**
     * Constructs a new rule set.
     * 
     * @param name Rule name
     */
    /*public RuleSetInfo(final String name, final String description) {
        super(name, description, DroolsConstants.FORMAT_DRL);
    }*/

    public RuleSetInfo(final String name, final String description, final String format) {
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
    public RuleSetInfo(final String uuid, final String name, final long versionNumber) {
        super(uuid, name, versionNumber);
    }

    /**
     * Adds a rule to this rule set.
     * Null and duplicate rules are not allowed.
     * 
     * @param rule A rule
     */
    public void addRule(final RuleInfo rule) {
        if ( rule == null ) {
            return;
        }
        this.rules.put(rule.getName(), rule);
    }

    /**
     * Removes a rule from this rule set.
     * 
     * @param rule A rule
     * @return The rule that was removed
     */
    public RuleInfo removeRule(final String ruleName) {
        return this.rules.remove( ruleName );
    }

    /**
     * Sets a list of <code>org.kuali.student.brms.repository.dto.RuleInfo</code>.
     * 
     * @param rules
     */
    public void setRules(Map<String,RuleInfo> rules) {
    	this.rules = rules;
    }
    
    /**
     * Gets a list of <code>org.kuali.student.brms.repository.dto.RuleInfo</code> from this rule set.
     * 
     * @return List of rules
     */
    public Map<String,RuleInfo> getRules() {
    	return this.rules;
    }
    
    /**
     * Adds a header to this rule set. 
     * E.g. <code>ruleSet.addHeader("java.util.Calendar");</code>
     * Null and duplicate headers are not allowed.
     * 
     * @param header A rule set header
     */
    public void addHeader(final String header) {
        this.header.add(header);
    }

    /**
     * Removes a header from this rule set. 
     * E.g. <code>ruleSet.removeHeader("java.util.Calendar");</code>
     * 
     * @param header A rule set header
     * @return True if header was removed otherwise false
     */
    public boolean removeHeader(final String header) {
        return this.header.remove(header);
    }

    /**
     * Gets this rule set list of headers. 
     * 
     * @param header Rule set header list
     */
    public void setHeader(List<String> header) {
        this.header = header;
    }

    /**
     * Gets this rule set list of headers. 
     * 
     * @return Rule set header list
     */
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

    /**
     * Returns whether this rule set is a snashot or not.
     * 
     * @return True if this rule set is a snapshot, otherwise false
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
     * Sets the rule set's source code content.
     * 
     * @param sourceContent Source code content
     */
    public void setContent(final String sourceContent) {
        this.sourceContent = sourceContent;
    }

    /**
     * Gets the rule set's source code content.
     * 
     * @return Source code content
     */
    public String getContent() {
        return this.sourceContent;
    }

    /**
     * Gets the rule set's snapshot name.
     * 
     * @return Snapshot name
     */
    public String getSnapshotName() {
        return this.snapshotName;
    }

    /**
     * Sets the rule set's snapshot name.
     * 
     * @param snapshotName
     */
    public void setSnapshotName(final String snapshotName) {
        this.snapshotName = snapshotName;
    }
    
    /**
     * Adds a category to this rule set.
     * 
     * @param name Category name
     * @param path Category path
     */
    public void addCategory(String name, String path) {
    	CategoryInfo category = new CategoryInfo(name, path);
    	this.categoryList.add(category);
    }

    /**
     * Removes a category to this rule set.
     * 
     * @param name Category name
     * @param path Category path
     * @return True if category was removed otherwise false
     */
    public boolean removeCategory(String name, String path) {
    	CategoryInfo category = new CategoryInfo(name, path);
    	return this.categoryList.remove(category);
    }

    /**
     * Gets a list of categories this rule set belongs to.
     * 
     * @return List of categories
     */
	public List<CategoryInfo> getCategories() {
		return categoryList;
	}

	/**
     * Gets a list of categories this rule set belongs to.
	 * 
	 * @param categoryList List of categories
	 */
	public void setCategories(List<CategoryInfo> categoryList) {
		this.categoryList = categoryList;
	}
    
	/**
	 * Gets the rule set effective date.
	 * 
	 * @return Effective date
	 */
	public Calendar getEffectiveDate() {
		return this.effectiveDate;
	}

	/**
	 * Sets the rule set effective date.
	 * 
	 * @param effectiveDate Effective date
	 */
	public void setEffectiveDate(final Calendar effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

    /**
	 * Gets the rule set expiry date.
	 * 
	 * @return Expiry date
     */
	public Calendar getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * Sets the rule set expiry date.
	 * 
	 * @param expiryDate Expiry date
	 */
	public void setExpiryDate(final Calendar expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String toString() {
        return "RuleSet UUID=" + getUUID() + ", name=" + getName() + 
        ", versionNumber=" + getVersionNumber() + ", categoryList=" + getCategories();
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

        for(RuleInfo rule : this.rules.values()) {
            sb.append(rule.getContent());
            sb.append("\n");
        }

        return sb.toString();
    }

}
