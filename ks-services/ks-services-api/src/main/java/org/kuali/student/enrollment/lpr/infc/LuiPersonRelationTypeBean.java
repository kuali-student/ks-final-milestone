/*
* Copyright 2011 The Kuali Foundation
*
* Licensed under the Educational Community License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may	obtain a copy of the License at
*
* 	http://www.osedu.org/licenses/ECL-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.kuali.student.enrollment.lpr.infc;

import org.kuali.student.common.infc.AttributeInfc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LuiPersonRelationTypeBean
        implements LuiPersonRelationTypeInfc, Serializable {

    private static final long serialVersionUID = 1L;
    private String name;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public String getName() {
        return this.name;
    }

    private String descr;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public String getDescr() {
        return this.descr;
    }

    private Date effectiveDate;

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    @Override
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    @Override
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    private Date expirationDate;

    /**
     * Set ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    @Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Get ????
     * <p/>
     * Type: Date
     * <p/>
     * ???
     */
    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    private List<AttributeInfc> attributes;

    /**
     * Set ????
     * <p/>
     * Type: List<AttributeInfc>
     * <p/>
     * ???
     */
    @Override
    public void setAttributes(List<AttributeInfc> attributes) {
        this.attributes = attributes;
    }

    /**
     * Get ????
     * <p/>
     * Type: List<AttributeInfc>
     * <p/>
     * ???
     */
    @Override
    public List<AttributeInfc> getAttributes() {
        return this.attributes;
    }

    private String key;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    @Override
    public String getKey() {
        return this.key;
    }
}

