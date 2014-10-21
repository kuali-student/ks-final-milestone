/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by delyea on 3/19/14
 */
package org.kuali.student.cm.proposal.controller;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This method is a round-about way to provide a new transaction when calling MaintenanceDocumentController.route()
 *
 * In krad-base-servlet.xml lives this gem:
 *   <aop:config>
 *     <aop:pointcut id="controllerTransaction" expression="execution(* org.kuali.rice.krad.web.controller.UifControllerBase+.*(..))"/>
 *     <aop:advisor advice-ref="txAdvice" pointcut-ref="controllerTransaction"/>
 *   </aop:config>
 *
 * Which was proxying all UifControllerBase in a transaction. This is bad if you want to handle exceptions and display
 * messages to the user without a stacktrace. To get around this, we created this indirect class that can be
 * transactionally proxied. (Note @Transactional annotation). The propagation is set to requires new, which means that
 * it will be in a different transaction than the  Controller's transaction and any rollbacks will stop at the proxy.
 * Then in the controller we can catch exceptions without having our initial transaction rolling back.
 *
 * @author Kuali Student Team
 */
public class ProposalControllerTransactionHelperImpl implements ProposalControllerTransactionHelper {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void performWorkflowActionSuper(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData, ProposalController proposalController) {
        proposalController.performWorkflowActionSuper(form,action,checkSensitiveData);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void performCustomWorkflowActionSuper(DocumentFormBase form, String action, ProposalController proposalController) {
        proposalController.performCustomWorkflowActionSuper(form, action);
    }
}
