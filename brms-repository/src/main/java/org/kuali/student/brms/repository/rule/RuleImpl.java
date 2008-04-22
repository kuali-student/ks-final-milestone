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

import java.util.Calendar;

public class RuleImpl extends ItemImpl implements Rule {
    private byte[] binaryContent = null;
    private String content = null;
    private String category;

    private Calendar effectiveDate = null;
    private Calendar expiryDate = null;

    /**
     * 
     * Constructs a new rule
     * 
     * @param name Rule name
     */
    public RuleImpl(String name) {
        super(name);
    }

    /**
     * @param name
     *            Asset's name
     * @param content
     *            Rule source
     * @param binaryContent
     *            Binary content
     * @param effectiveDate
     *            Date asset becomes effective
     * @param expiryDate
     *            Date asset expires
     */
    public RuleImpl(String uuid, String name) {
        super(uuid, name);
    }

    /**
     * Returns the binary data as a byte array.
     * 
     * @return Binary data as a byte array
     */
    public byte[] getBinaryContent() {
        return this.binaryContent;
    }

    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the rule source string. If this is a binary asset, this will return null (use #getBinaryContent instead).
     * 
     * @return Rule source
     */
    public String getContent() {
        return this.content;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getCategory()
    {
        return this.category;
    }
    
    public void setEffectiveDate(Calendar effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Return the date the rule becomes effective.
     * 
     * @return Date the rule becomes effective
     */
    public Calendar getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setExpiryDate(Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Returns the date the rule expires.
     * 
     * @return Date the rule expires
     */
    public Calendar getExpiryDate() {
        return this.expiryDate;
    }

}