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
package org.kuali.student.rules.brms.repository.rule;

import java.util.Calendar;

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
     * Gets the category the rule belongs to.
     * 
     * @return Rule category
     */
    public String getCategory();
    
    /**
     * Return the date the rule becomes effective.
     * 
     * @return Date the rule becomes effective
     */
    public Calendar getEffectiveDate();

    /**
     * Returns the rule expiry date.
     * 
     * @return Date the rule expires
     */
    public Calendar getExpiryDate();
}
