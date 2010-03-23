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
package org.kuali.student.brms.internal.common.entity;

/**
 * Defines possible types of Rule Element
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
public enum RuleElementType {

    AND("AND", "1"), OR("OR", "2"), NOT("NOT", "4"), LPAREN("(", "5"), RPAREN(")", "6"), PROPOSITION(
            "PROPOSITION", "7");

    private String name;
    private String id;

    /**
     * Sets up a RuleElementType instance.
     */
    private RuleElementType(String name, String id) {
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
