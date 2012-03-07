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
package org.kuali.student.r2.common.datadictionary;

import org.kuali.rice.krad.datadictionary.validation.constraint.ValidCharactersConstraint;
import org.kuali.student.r2.common.datadictionary.dto.ValidCharactersConstraintInfo;

/**
 *
 * @author nwright
 */
public class Rice2StudentValidCharactersConstraintConverter {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Rice2StudentValidCharactersConstraintConverter.class);

    public ValidCharactersConstraintInfo convert(ValidCharactersConstraint rice) {
       ValidCharactersConstraintInfo.Builder bldr = new ValidCharactersConstraintInfo.Builder();
       bldr.setApplyClientSide(rice.getApplyClientSide());
//       bldr.setJsValue(rice.getJsValue());
       bldr.setLabelKey(rice.getLabelKey());
       bldr.setValue (rice.getValue());
       return bldr.build();
    }
}
