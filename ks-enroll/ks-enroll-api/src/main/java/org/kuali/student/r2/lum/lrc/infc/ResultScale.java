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
package org.kuali.student.r2.lum.lrc.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.RichText;

/**
 * @author sambit
 */

public interface ResultScale extends HasAttributesAndMeta {
	   
    /**
     * Name: Key
     *
     * Unique key to this object. Unlike an Id this key can be explicitly set by the
     * application and is intended to be "somewhat" readable by a human.
     *
     * A Key:<ul>
     * <li>A Key is used when the actual value is important
     * <li>A Key value might be kuali.org.School
     * <li>A Key on occasion may be used or seen by an end user.
     * <li>Keys are assumed to have the same values in different KS implementations
     * <li>Key values are defined in configuration
     * <li>Key values have significance in that they are referenced in Configuration
     * <li>Key values are expected to be occasionally used in application code
     * </ul>
     */
    public String getKey();
    
    /**
     * Name: Name
     * A display name for this entity.
     *  
     * @return the entity name
     */

    public String getName();


    /**
     * Name: Description
     * A description of the entity.
     *
     * @return the entity description
     */

    public RichText getDescr();
    
	/**
	 * Effective Date dateTime Date and time that this result value became
	 * effective. This is a similar concept to the effective date on enumerated
	 * values. When an expiration date has been specified, this field must be
	 * less than or equal to the expiration date.
	 * @name Effective Date
	 */
	public Date getEffectiveDate();

	/**
	 * Expiration Date dateTime Date and time that this result value expires.
	 * This is a similar concept to the expiration date on enumerated values. If
	 * specified, this should be greater than or equal to the effective date. If
	 * this field is not specified, then no expiration date has been currently
	 * defined and should automatically be considered greater than the effective
	 * date.
	 * @name Expiration Date
	 */
	public Date getExpirationDate();	
}
