package org.kuali.student.cm.proposal.controller;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.student.cm.proposal.controller.ProposalController;

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
public interface ProposalControllerTransactionHelper {

    public void performWorkflowActionSuper(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData, ProposalController proposalController);

    public void performCustomWorkflowActionSuper(DocumentFormBase form, String action, ProposalController proposalController);

}
