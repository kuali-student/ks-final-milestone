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

/**
 * This is the implementation of a <code>Rule</code>. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleImpl 
    extends ItemImpl 
    implements java.io.Serializable, Rule {

    private static final long serialVersionUID = 1L;
    
    /** Binary content of this rule */
    private byte[] binaryContent;
    /** Source code content of this rule */
    private String content;
    /** Category this rule belongs to */
    private String category;
    /** Date this rule becomes effective */
    private Calendar effectiveDate;
    /** Date this rule expires */
    private Calendar expiryDate;

    /**
     * Constructs a new rule
     * 
     * @param name Rule name
     */
    public RuleImpl(String name) {
        super(name);
    }

    /**
     * Constructs a new rule
     * 
     * @param uuid Rule UUID
     * @param name Rule name
     */
    public RuleImpl(String uuid, String name) {
        super(uuid, name);
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#getBinaryContent()
     */
    public byte[] getBinaryContent() {
        int size = this.binaryContent.length;
        byte[] temp = new byte[size];
        System.arraycopy(this.binaryContent, 0, temp, 0, size);
        return temp;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#setBinaryContent(byte[])
     */
    public void setBinaryContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#setContent(java.lang.String)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#getContent()
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#setCategory(java.lang.String)
     */
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    /**
     * @see org.kuali.student.brms.repository.rule.Rule#getCategory()
     */
    public String getCategory()
    {
        return this.category;
    }
    
    /**
     * @see org.kuali.student.brms.repository.rule.Rule#setEffectiveDate(java.util.Calendar)
     */
    public void setEffectiveDate(Calendar effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#getEffectiveDate()
     */
    public Calendar getEffectiveDate() {
        return this.effectiveDate;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#setExpiryDate(java.util.Calendar)
     */
    public void setExpiryDate(Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @see org.kuali.student.brms.repository.rule.Rule#getExpiryDate()
     */
    public Calendar getExpiryDate() {
        return this.expiryDate;
    }

}