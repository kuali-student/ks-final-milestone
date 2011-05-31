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

import org.kuali.student.r2.common.infc.Entity;
import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.RichText;

/**
 * @author sambit
 */

public interface ResultValue extends HasAttributesAndMeta {

	
	/**
	 * The unique identifier for this result value in the database
	 * @name Result Value Identifier
	 */
	public String getId();
	
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
	 * Result Value string Value of the result. Typically corresponds with the
	 * short coded form of the result(ex. "A", "4.0", "97.0", "B.S" etc.)
	 * scaleKey Scale Identifier scaleKey
	 * @name Result value
	 */
	public String getValue();
    
	/**
	 * Identifier of the scale for this result value. Can be null if no scale associated 
	 * with the resultValue
	 * 
	 * @name Scale Key
	 */
    public String getScaleKey();

    /**
     * Rank of the result value within the scale. Standards around uniqueness and meaning 
     * of value are described in the information about the scale.
     * 
     * @name Rank
     */
    public Float getRank();      
    
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
