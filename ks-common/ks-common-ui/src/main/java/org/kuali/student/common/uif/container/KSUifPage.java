/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by David Yin on 1/23/13
 */
package org.kuali.student.common.uif.container;

import org.kuali.rice.krad.uif.container.PageGroup;
import org.kuali.rice.krad.uif.service.ExpressionEvaluatorService;

/**
 * KS page class that extends PageGroup to perform the customized breadcrumb generation.
 *
 * @author Kuali Student Team
 */
public class KSUifPage extends PageGroup {
    private transient ExpressionEvaluatorService expressionEvaluatorService;

    public KSUifPage() {
    }


}
