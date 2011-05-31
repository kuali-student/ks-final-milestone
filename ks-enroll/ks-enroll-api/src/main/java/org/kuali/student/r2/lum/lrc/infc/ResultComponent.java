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
import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;


public interface ResultComponent extends IdEntity {


    /**
     * Contains the list of discrete results value ids in this group.
     * @name Result Value Ids
     */
    public List<String> getResultValueIds();

    /**
     * The  range contained within this result value group. This is
     * optional and might not be present for some Result Components
     * 
     * @name Result Value Range
     */
    public ResultValueRange getResultValueRange();
        
	/**
	 * Date and time that this result component became effective. This is a
	 * similar concept to the effective date on enumerated values. When an
	 * expiration date has been specified, this field must be less than or equal
	 * to the expiration date.
	 * @name Effective Date
	 */
	public Date getEffectiveDate();

	/**
	 * Date and time that this result component expires. This is a similar
	 * concept to the expiration date on enumerated values. If specified, this
	 * should be greater than or equal to the effective date. If this field is
	 * not specified, then no expiration date has been currently defined and
	 * should automatically be considered greater than the effective date.
	 * @name Expiration Date
	 */
	public Date getExpirationDate();

}
