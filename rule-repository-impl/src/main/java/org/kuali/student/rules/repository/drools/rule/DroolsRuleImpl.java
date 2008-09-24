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
package org.kuali.student.rules.repository.drools.rule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.drools.repository.StateItem;
import org.kuali.student.rules.repository.rule.AbstractItem;
import org.kuali.student.rules.repository.rule.Category;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.util.ObjectUtil;

/**
 * This is the implementation of a <code>Rule</code>. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class DroolsRuleImpl 
    extends AbstractItem 
    implements Rule, java.io.Serializable {

    /** Class serial version uid */
    private static final long serialVersionUID = 1L;
    
    /** Binary content of this rule */
    private byte[] binaryContent;
    /** Source code content of this rule */
    private String content;
    /** List of category names */
    private List<String> categoryNameList = new ArrayList<String>();
    /** List of categories */
    private List<Category> categoryList = new ArrayList<Category>();
    /** Date this rule becomes effective */
    private Calendar effectiveDate;
    /** Date this rule expires */
    private Calendar expiryDate;
    /** Rule's parent rule set's uuid */
    private String ruleSetUUID;
    /** Rule's parent rule set's name */
    private String ruleSetName;
    /** Object utility class */
    private ObjectUtil objectUtil = ObjectUtil.getInstance();
    
    /**
     * Constructs a new rule.
     * 
     * @param name Rule name
     */
    public DroolsRuleImpl(final String name, 
                          final String description,
                          final String content) {
        super(name, description, DroolsConstants.FORMAT_DRL);
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("content cannot be null or empty");
        }
        this.content = content;
        this.setStatus(StateItem.DRAFT_STATE_NAME);
    }

    /**
     * Constructs a new rule.
     * 
     * @param name Rule name
     */
    public DroolsRuleImpl(final String name, 
                          final String description, 
                          final String initialCategory, 
                          final String content, 
                          final String format) {
        super(name, description, format);
        if(initialCategory != null) {
            this.categoryNameList.add(initialCategory);
        }
        this.content = content;
        this.setStatus(StateItem.DRAFT_STATE_NAME);
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
    DroolsRuleImpl(final String uuid, 
    			   final String name, 
    			   final long versionNumber, 
    			   final String ruleSetUUID, 
    			   final String ruleSetName) {
        super(uuid, name, versionNumber);
        this.ruleSetUUID = ruleSetUUID;
        this.ruleSetName = ruleSetName;
    }

    /**
     * Creates a copy of this rule.
     * 
     * @return A new copy of this rule
     */
    /*public Rule copy() {
        DroolsRuleImpl rule = null;

        if ( getUUID() == null ) {
            rule = new DroolsRuleImpl( getName(), getDescription(), getContent() );
        } else {
            rule = new DroolsRuleImpl( getUUID(), getName(), getVersionNumber() );
        }
        
        rule.setArchived(isArchived());
        rule.setBinaryContent(getBinaryContent());
        rule.setCategory(getCategory());
        rule.setCheckinComment(getCheckinComment());
        rule.setContent(getContent());
        rule.setCreatedDate(getCreatedDate());
        rule.setDescription(getDescription());
        rule.setEffectiveDate(getEffectiveDate());
        rule.setExpiryDate(getExpiryDate());
        rule.setFormat(getFormat());
        rule.setHistorical(isHistorical());
        rule.setLastModifiedDate(getLastModifiedDate());
        rule.setStatus(getStatus());
        rule.setVersionSnapshotUUID(getVersionSnapshotUUID());
        return rule;
    }*/

    /**
     * Returns a copy of the binary content.
     * 
     * @see org.kuali.student.rules.repository.rule.Rule#getBinaryContent()
     */
    public byte[] getBinaryContent() {
        return objectUtil.arrayCopy( this.binaryContent );
    }

    /**
     * Sets the compiled binary content of the rule.
     * This method makes a copy of the compiled rules.
     * 
     * @param binaryContent Compiled byte array
     */
    public void setBinaryContent(final byte[] binaryContent) {
        this.binaryContent = objectUtil.arrayCopy( binaryContent );
    }

    /**
     * @see org.kuali.student.rules.repository.rule.Rule#setContent(java.lang.String)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @see org.kuali.student.rules.repository.rule.Rule#getContent()
     */
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
    public void setCategories(List<Category> categories) {
        this.categoryList = categories;
    }

    /**
     * Gets a list of categories.
     * 
     * @see org.kuali.student.rules.repository.rule.Rule#getCategories()
     */
    public List<Category> getCategories() {
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

    /**
     * @see org.kuali.student.rules.repository.rule.Rule#getEffectiveDate()
     */
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

    /**
     * @see org.kuali.student.rules.repository.rule.Rule#getExpiryDate()
     */
    public Calendar getExpiryDate() {
        return this.expiryDate;
    }

    /**
     * @see org.kuali.student.rules.repository.rule.Rule#getRuleSetUUID()
     */
    public String getRuleSetUUID() {
        return this.ruleSetUUID;
    }

    /**
     * @see org.kuali.student.rules.repository.rule.Rule#getRuleSetName()
     */
    public String getRuleSetName() {
        return this.ruleSetName;
    }

    public String toString() {
        return "Rule UUID=" + getUUID() + ", name=" + getName() + ", versionNumber=" + getVersionNumber();
    }
    /**
     * Overrides hashCode.
     * 
     * @see org.kuali.student.rules.repository.rule.AbstractItem#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + super.hashCode();
        result = prime * result + ( this.getContent() == null ? 0 : this.getContent().hashCode());
        return result;
    }

    /**
     * Overrides equals
     * 
     * @see org.kuali.student.rules.repository.rule.AbstractItem#equals(java.lang.Object)
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

        final DroolsRuleImpl rule = (DroolsRuleImpl) obj;

        if ( rule.getName() == null ) {
            return false;
        }
        
        if ( !getName().equals( rule.getName() ) ) {
            return false;
        }
        if ( getContent() != null && !getContent().equals( rule.getContent() ) ) {
            return false;
        }

        if ( rule.getUUID() != null && !rule.getUUID().equals( this.getUUID() ) ) {
            return false;
        }
        
        if ( rule.getVersionNumber() != this.getVersionNumber() ) {
            return false;
        }
        
        return true;
    }
}