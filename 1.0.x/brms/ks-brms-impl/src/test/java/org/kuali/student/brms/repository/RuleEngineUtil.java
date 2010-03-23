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
package org.kuali.student.brms.repository;

import org.kuali.student.brms.repository.rule.CompilerResultList;

/**
 * This is a rule engine test utility class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class RuleEngineUtil {

    /**
     * Private constructor.
     */
    private RuleEngineUtil() {
    }

    /**
     * Returns a new instance of <code>RuleEngineUtil</code>
     * 
     * @return A new instance of <code>RuleEngineUtil</code>
     */
    public static RuleEngineUtil getInstance() {
        return new RuleEngineUtil();
    }
    /**
     * Return a messages of the <code>result</code> as a string.
     * 
     * @param result
     *            Builder result list
     * @return Errors messages
     */
    public String getErrorMessage(CompilerResultList result) {
        return (result == null ? "Null Error Message" : result.toString());
    }

}
