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
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LeftHandSideDTO implements Serializable{

    @XmlElement
    private YieldValueFunctionDTO yieldValueFunction;
    
    /**
     * @return the yieldValueFunction
     */
    public YieldValueFunctionDTO getYieldValueFunction() {
        return yieldValueFunction;
    }

    /**
     * @param yieldValueFunction the yieldValueFunction to set
     */
    public void setYieldValueFunction(YieldValueFunctionDTO yieldValueFunction) {
        this.yieldValueFunction = yieldValueFunction;
    }
}
