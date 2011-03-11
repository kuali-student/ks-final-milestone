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

package org.kuali.student.common.infc;

import java.util.Date;

import org.kuali.student.common.dto.HasAttributes;


public interface StateInfc extends HasAttributes {
    
    /**
     * @return the key
     */
    public String getKey();

    /**
     * @param key the key to set
     */
    public void setKey(String key);
    
    /**
     * @return the name
     */
    public String getName();

    /**
     * @param name the name to set
     */
    public void setName(String name);

    /**
     * @return the descr
     */
    public String getDescr();

    /**
     * @param descr the descr to set
     */
    public void setDescr(String descr);

    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate();

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate);

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate();
    
    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate);
    

}
