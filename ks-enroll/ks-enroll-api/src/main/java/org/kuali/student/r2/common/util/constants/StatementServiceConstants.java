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

package org.kuali.student.r2.common.util.constants;

import org.kuali.student.r2.core.statement.dto.NlUsageTypeInfo;
import org.kuali.student.r2.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementInfo;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;

/**
 * This class holds the constants used by the Statement service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public class StatementServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "statement";
    public static final String REF_OBJECT_URI_STATEMENT = NAMESPACE + "/" + StatementInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_REF_STATEMENT_RELATION = NAMESPACE + "/" + RefStatementRelationInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_REQ_COMPONENT=NAMESPACE + "/" + ReqComponentInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_STATEMENT_TREE_VIEW=NAMESPACE + "/" + StatementTreeViewInfo.class.getSimpleName();
    public static final String REF_OBJECT_URI_NL_USAGE_TYPE=NAMESPACE + "/" + NlUsageTypeInfo.class.getSimpleName();
}
