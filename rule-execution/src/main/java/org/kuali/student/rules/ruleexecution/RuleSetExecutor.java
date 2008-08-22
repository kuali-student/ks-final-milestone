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
package org.kuali.student.rules.ruleexecution;

import java.util.List;

import org.kuali.student.rules.brms.agenda.entity.Agenda;

public interface RuleSetExecutor {

    /**
     * Executes an <code>agenda</code> with <code>fact</code>
     * 
     * @param agenda Agenda to execute
     * @param facts List of Facts for the <code>agenda</code>
     * @return Result of executing the <code>agenda</code>
     */
    public Object execute( Agenda agenda, List<Object> facts );
}
