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

import org.kuali.student.r2.common.infc.KeyEntity;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import java.util.List;

/**
 *  A ResultValuesGroup is used to relate a ResultScale to a learning
 *  unit or grading entry. A ResultValuesGroup may be identical to a
 *  ResultScale, or it may further contrict the valid values and
 *  ranges defined in the ResultScale.
 *
 *  The purpose of the ResultValuesGroup is to permit a subset of
 *  ResultValues and ranges to be applied without having to generate
 *  new ResultScales.
 */

public interface ResultValuesGroup extends KeyEntity, HasEffectiveDates {

    /**
     * The scale to which this group belongs. Result Values mapped to
     * a Result Values Group belong to a single Result Scale.
     *
     * @name Result Scale Key
     */
    public String getResultScaleKey();

    /**
     * Contains the list of discrete results value keys in this group.
     * @name Result Value Keys
     */
    public List<String> getResultValueKeys();

    /**
     * The  range contained within this result value group. This is
     * optional and might not be present for some Result Components
     * 
     * @name Result Value Range
     */
    public ResultValueRange getResultValueRange();
}
