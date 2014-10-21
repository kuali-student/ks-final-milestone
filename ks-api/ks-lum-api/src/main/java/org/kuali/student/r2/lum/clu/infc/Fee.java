/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.r2.lum.clu.infc;

import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasKey;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

/**
 * Information about a fee related to an learning unit
 */
public interface Fee extends HasKey, HasAttributesAndMeta {

    /**
     * A code that identifies the type of the fee. For example: Lab Fee or
     * Tuition Fee or CMF for Course Materials Fee. From a program perspective
     * it may be an application fee.
     *
     * @name: Fee Type
     */
    public String getFeeType();

    /**
     * Indicates the structure and interpretation of the fee amounts, i.e.
     * Fixed, Variable, Multiple.
     *
     * @name Rate Type
     */
    public String getRateType();

    /**
     * The amount or amounts associated with the fee. The number fee amounts and
     * interpretation depends on the rate type.
     *
     * @name Fee Amounts
     */
    public List<? extends CurrencyAmount> getFeeAmounts();

    /**
     * Narrative description of the Fee.
     *
     * @name Description
     */
    public RichText getDescr();
}