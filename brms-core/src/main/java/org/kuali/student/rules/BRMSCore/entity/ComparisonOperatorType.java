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
package org.kuali.student.rules.BRMSCore.entity;

/**
 * Defines comparison operator types.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public enum ComparisonOperatorType {

    EQUAL_TO_TYPE("EQUAL_TO", "1"), NOT_EQUAL_TO_TYPE("NOT_EQUAL_TO", "2"), GREATER_THAN_TYPE("GREATER_THAN", "3"), LESS_THAN_TYPE("LESS_THAN", "4"), GREATER_THAN_OR_EQUAL_TO_TYPE("GREATER_THAN_OR_EQUAL_TO", "5"), LESS_THAN_OR_EQUAL_TO_TYPE("LESS_THAN_OR_EQUAL_TO", "6");

    private String name;
    private String id;

    /**
     * Sets up a ComparisonOperatorType instance.
     */
    private ComparisonOperatorType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the validateTypeId
     */
    public String getRuleElementTypeId() {
        return id;
    }
}
