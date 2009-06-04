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
package org.kuali.student.brms.repository.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class RuleInfo extends AbstractItemInfo implements java.io.Serializable {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Binary content of this rule */
	@XmlElement
    private byte[] binaryContent;
    /** Source code content of this rule */
	@XmlElement
    private String content;
    /** List of category names */
	@XmlElement
    private List<String> categoryNameList = new ArrayList<String>();
    /** List of categories */
	@XmlElement
    private List<CategoryInfo> categoryList = new ArrayList<CategoryInfo>();
    /** Date this rule becomes effective */
	@XmlElement
    private Calendar effectiveDate;
    /** Date this rule expires */
	@XmlElement
    private Calendar expiryDate;
    /** Rule's parent rule set's uuid */
	@XmlElement
    private String ruleSetUUID;
    /** Rule's parent rule set's name */
	@XmlElement
    private String ruleSetName;
    
    public RuleInfo() {}
    
    /**
     * Constructs a new rule.
     * 
     * @param name Rule name
     */
    public RuleInfo(final String name, 
                   final String description,
                   final String content,
                   final String format) {
        super(name, description, format);
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("content cannot be null or empty");
        }
        this.content = content;
    }

    /**
     * Constructs a new rule.
     * 
     * @param name Rule name
     */
    public RuleInfo(final String name, 
                   final String description, 
                   final String initialCategory, 
                   final String content, 
                   final String format) {
        super(name, description, format);
        if(initialCategory != null) {
            this.categoryNameList.add(initialCategory);
        }
        this.content = content;
    }

    /**
     * <p>Constructs a new rule.</p>
     * <p>Internal use only. This is set by the repository when persisting a rule set.</p>
     * 
     * @param uuid Rule UUID
     * @param name Rule name
     * @param versionNumber Rule version number
     * @param ruleSetUUID Parent rule set's uuid
     * @param ruleSetName Parent's rule set's name
     */
    public RuleInfo(final String uuid, 
    		final String name, 
    		final long versionNumber, 
    		final String ruleSetUUID, 
    		final String ruleSetName) {
        super(uuid, name, versionNumber);
        this.ruleSetUUID = ruleSetUUID;
        this.ruleSetName = ruleSetName;
    }

    public String getRuleSetUUID() {
        return this.ruleSetUUID;
    }

    public String getRuleSetName() {
        return this.ruleSetName;
    }

    /**
     * Returns a copy of the binary content.
     * 
     * @see org.kuali.student.brms.repository.rule.Rule#getBinaryContent()
     */
    public byte[] getBinaryContent() {
        return this.binaryContent;
    }

    /**
     * Sets the compiled binary content of the rule.
     * This method makes a copy of the compiled rules.
     * 
     * @param binaryContent Compiled byte array
     */
    public void setBinaryContent(final byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    /**
     * Adds a category to the rule.
     * 
     * @param name Category name
     * @param path Category path
     */
    public void addCategoryName(String name) {
        if (name != null ) {
            this.categoryNameList.add(name);
        }
    }

    /**
     * Sets a list of category names.
     * 
     * @param categories A list of categories
     */
    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNameList = categoryNames;
    }

    /**
     * Gets the category the rule belongs to.
     * 
     * @return
     */
    public List<String> getCategoryNames() {
        return this.categoryNameList;
    }

    /**
     * Sets a list of categories.
     * 
     * @param categories A list of categories
     */
    public void setCategories(List<CategoryInfo> categories) {
        this.categoryList = categories;
    }

    /**
     * Gets a list of categories.
     * 
     * @see org.kuali.student.brms.repository.rule.Rule#getCategories()
     */
    public List<CategoryInfo> getCategories() {
        return this.categoryList;
    }
    
    /**
     * Sets the rule effective date.
     * 
     * @param effectiveDate Rule effective date
     */
    public void setEffectiveDate(Calendar effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Calendar getEffectiveDate() {
        return this.effectiveDate;
    }

    /**
     * Sets the rule expiry date.
     * 
     * @param expiryDate Rule expiry date
     */
    public void setExpiryDate(Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Calendar getExpiryDate() {
        return this.expiryDate;
    }

    public String toString() {
        return "RuleInfo UUID=" + getUUID() + ", name=" + getName() + ", versionNumber=" + getVersionNumber();
    }

}
