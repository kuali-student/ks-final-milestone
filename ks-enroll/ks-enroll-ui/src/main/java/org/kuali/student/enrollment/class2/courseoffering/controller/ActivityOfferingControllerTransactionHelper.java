package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 7/23/13
 * Time: 10:54 AM
 * This method is a round-about way to provide a new transaction when calling MaintenanceDocumentController.route()
 *
 * In krad-base-servlet.xml lives this gem:
 *   <aop:config>
         <aop:pointcut id="controllerTransaction"
            expression="execution(* org.kuali.rice.krad.web.controller.UifControllerBase+.*(..))"/>
         <aop:advisor advice-ref="txAdvice" pointcut-ref="controllerTransaction"/>
     </aop:config>
 *
 * Which was proxying all UifControllerBase in a transaction. This is bad if you want to handle exceptions and display
 * messages to the user without a stacktrace. To get around this, we created this indirect class that can be
 * transactionally proxied. (Note @Transactional annotation). The propagation is set to requires new, which means that
 * it will be in a different transaction than the  Controller's transaction and any rollbacks will stop at the proxy.
 * Then in the controller we can catch exceptions without having our initial transaction rolling back.
 *
 */
public class ActivityOfferingControllerTransactionHelper {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ModelAndView routeSuper(DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response, ActivityOfferingController activityOfferingController) {
        return activityOfferingController.routeSuper(form, result, request, response);
    }

}
