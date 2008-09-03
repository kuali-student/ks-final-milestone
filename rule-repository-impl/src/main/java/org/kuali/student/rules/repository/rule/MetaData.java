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

/**
 * This class holds rule and rule set meta data. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public interface MetaData {
    /**
     * Returns the rule name.
     * 
     * @return Rule name
     */
    public String getName();

    /**
     * Returns the rule description.
     * 
     * @return Rule description
     */
    public String getDescription();

    /**
     * Returns the rule status. E.g. Active, Inactive, Archived etc.
     * 
     * @return Rule status
     */
    public String getStatus();

    /**
     * Returns rule format. E.g. for Drools, FUNCTION, DSL, DECISION_SPREADSHEET_XLS, ENUMERATION
     * 
     * @return Rule format
     */
    public String getFormat();

    /**
     * Gets the asset/rule creator.
     * 
     * @return Creator
     */
    public String getCreator();

    /**
     * Gets the asset/rule subject.
     * 
     * @return Subject
     */
    public String getSubject();

    /**
     * Get the asset/rule pulisher.
     * 
     * @return Publisher
     */
    public String getPublisher();

    /**
     * Returns the checkin comments.
     * 
     * @return Checkin comments
     */
    public String getCheckinComment();

    /**
     * Returns the rule effective date.
     * 
     * @return Effective date
     */
    public Calendar getEffectiveDate();

    /**
     * Returns the rule expiry date.
     * 
     * @return Expiry date
     */
    public Calendar getExpiredDate();

    /**
     * Returns the last date the rule was modified.
     * 
     * @return Last modified date
     */
    public Calendar getLastModificationDate();

    /**
     * Returns the version number.
     * 
     * @return Version number
     */
    public long getVersionNumber();

    /**
     * Gets all categories for the rule.
     * 
     * @return Array of categories
     */
    public String[] getCategories();

    /**
     * Removes a category.
     * 
     * @param index
     *            The index of the cat to remove.
     */
    public void removeCategory(int index);

    /**
     * Adds a category to the end of the category list.
     */
    public void addCategory(String category);
}
