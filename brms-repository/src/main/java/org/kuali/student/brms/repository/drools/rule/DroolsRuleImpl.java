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
package org.kuali.student.brms.repository.drools.rule;

import java.util.Calendar;

import org.kuali.student.brms.repository.rule.AbstractItem;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.util.ObjectUtil;

/**
 * This is the implementation of a <code>Rule</code>. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class DroolsRuleImpl 
    extends AbstractItem 
    implements java.io.Serializable, Rule {

    /** Class serial version uid */
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
    /** Object utility class */
    private ObjectUtil objectUtil = ObjectUtil.getInstance();
    
    /**
     * Constructs a new rule
     * 
     * @param name Rule name
     */
    DroolsRuleImpl(final String name) {
        super(name);
    }

    /**
     * Constructs a new rule
     * 
     * @param uuid Rule UUID
     * @param name Rule name
     * @param versionNumber Rule version number
     */
    DroolsRuleImpl(final String uuid, final String name, final long versionNumber) {
        super(uuid, name, versionNumber);
    }

    /**
     * Returns a copy of the binary content;
     * 
     * @see org.kuali.student.brms.repository.rule.Rule#getBinaryContent()
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
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * @see org.kuali.student.brms.repository.rule.Rule#getCategory()
     */
    public String getCategory() {
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

    /**
     * Overrides hashCode
     * 
     * @see org.kuali.student.brms.repository.rule.AbstractItem#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + super.hashCode();
        result = prime * result + ( getContent() == null ? 0 : getContent().hashCode());
        return result;
    }

    /**
     * Overrides equals
     * 
     * @see org.kuali.student.brms.repository.rule.AbstractItem#equals(java.lang.Object)
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