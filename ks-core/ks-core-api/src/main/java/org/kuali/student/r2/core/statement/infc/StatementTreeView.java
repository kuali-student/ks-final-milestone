/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.statement.infc;

import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.core.statement.dto.StatementOperator;

import java.util.List;

/**
 * Statement Tree View
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface StatementTreeView extends IdEntity {

    /**
     * Statement operator
     *
     * @name Operator
     * @required
     */
    StatementOperator getOperator();


    /**
     * @name Statements
     * @required
     */
    List<? extends StatementTreeView> getStatements();

    /**
     * Required components
     *
     * @name Req Components
     */
    List<? extends ReqComponent> getReqComponents();
}
