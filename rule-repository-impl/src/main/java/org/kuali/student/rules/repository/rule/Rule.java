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
package org.kuali.student.rules.repository.rule;

import java.util.Calendar;
import java.util.List;

/**
 * This is the <code>Rule</code> interface. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public interface Rule extends Item {
    /**
     * Returns the compiled binary content as a byte array.
     * 
     * @return Compiled byte array
     */
    public byte[] getBinaryContent();

    /**
     * Returns the rule source string. 
     * If this is a binary asset, this will return null (use #getBinaryContent instead).
     * 
     * @return Rule source code
     */
    public String getContent();

    /**
     * Sets the rule source code
     * 
     * @param content Rule source code
     */
    public void setContent(String content);

    /**
     * Adds a category to the rule.
     * 
     * @param name Category name
     * @param path Category path
     */
    public void addCategoryName(String name);

    /**
     * Sets a list of category names.
     * 
     * @param categories A list of categories
     */
    public void setCategoryNames(List<String> categoryNames);
    
    /**
     * Gets the category the rule belongs to.
     * 
     * @return
     */
    public List<String> getCategoryNames();

    /**
     * Gets the categories the rule belongs to.
     * 
     * @return List of categories
     */
    public List<Category> getCategories();
    
    /**
     * Sets the rule effective date.
     * 
     * @param effectiveDate Rule effective date
     */
    public void setEffectiveDate(Calendar effectiveDate);

    /**
     * Return the date the rule becomes effective.
     * 
     * @return Date the rule becomes effective
     */
    public Calendar getEffectiveDate();

    /**
     * Sets the rule expiry date.
     * 
     * @param expiryDate Rule expiry date
     */
    public void setExpiryDate(Calendar expiryDate);

    /**
     * Returns the rule expiry date.
     * 
     * @return Date the rule expires
     */
    public Calendar getExpiryDate();
    
    /**
     * Returns the parent rule set's uuid;
     * 
     * @return Rule set UUID
     */
    public String getRuleSetUUID();

    /**
     * Returns the parent rule set's name;
     * 
     * @return Rule set name
     */
    public String getRuleSetName();
}
